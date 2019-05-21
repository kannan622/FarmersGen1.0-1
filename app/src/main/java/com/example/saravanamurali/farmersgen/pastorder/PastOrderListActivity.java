package com.example.saravanamurali.farmersgen.pastorder;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.models.CurrentUserDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseToGetPastOrderDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseToGetPastOrderDetails;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseToViewPastOrderedProductList;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonResponseToViewPastOrderedProductListDTO;
import com.example.saravanamurali.farmersgen.models.OrderID_DTO;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientPastOrderedProductListView;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToGetPastOrderDetails;
import com.example.saravanamurali.farmersgen.util.Network_config;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PastOrderListActivity extends AppCompatActivity implements PastOrderListAdapter.PastOrderIdInterface{

    RecyclerView pastOrderRecyclerView;
    List<JSONResponseToGetPastOrderDTO> jsonResponseToGetPastOrderDTOList;

    PastOrderListAdapter pastOrderListAdapter;

    Dialog dialog;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_order_list);

        mContext=PastOrderListActivity.this;

        dialog=new Dialog(mContext);


        pastOrderRecyclerView=(RecyclerView)findViewById(R.id.pastOrderRecyclerView);
        pastOrderRecyclerView.setLayoutManager(new LinearLayoutManager(PastOrderListActivity.this));
        pastOrderRecyclerView.setHasFixedSize(true);

        jsonResponseToGetPastOrderDTOList=new ArrayList<JSONResponseToGetPastOrderDTO>();

        pastOrderListAdapter=new PastOrderListAdapter(PastOrderListActivity.this,jsonResponseToGetPastOrderDTOList);

        pastOrderListAdapter.setPastOrderIdInterface(PastOrderListActivity.this);

        pastOrderRecyclerView.setAdapter(pastOrderListAdapter);


        if (Network_config.is_Network_Connected_flag(mContext)) {
            loastPastOrderList();
        }

        else {
            Network_config.customAlert(dialog, mContext, getResources().getString(R.string.app_name),
                    getResources().getString(R.string.connection_message));
        }


    }

    private void loastPastOrderList() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(PastOrderListActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api=APIClientToGetPastOrderDetails.getApiInterfaceToGetPastOrderDetails();

        SharedPreferences getCurrentUser = getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
        String curUserForPastOrderDetails = getCurrentUser.getString("CURRENTUSER", "NO_CURRENT_USER");

        CurrentUserDTO currentUserDTO = new CurrentUserDTO(curUserForPastOrderDetails);

        Call<JSONResponseToGetPastOrderDetails> call=  api.getPastOrderList(currentUserDTO);

        call.enqueue(new Callback<JSONResponseToGetPastOrderDetails>() {
            @Override
            public void onResponse(Call<JSONResponseToGetPastOrderDetails> call, Response<JSONResponseToGetPastOrderDetails> response) {
                if(response.isSuccessful()){

                    if(csprogress.isShowing()){
                        csprogress.dismiss();
                    }

                    JSONResponseToGetPastOrderDetails jsonResponseToGetPastOrderDetails   =response.body();
                    jsonResponseToGetPastOrderDTOList =jsonResponseToGetPastOrderDetails.getJsonResponseToGetPastOrderDTOList();

                    pastOrderListAdapter.setPastOrderList(jsonResponseToGetPastOrderDTOList);

                }
            }

            @Override
            public void onFailure(Call<JSONResponseToGetPastOrderDetails> call, Throwable t) {

                if(csprogress.isShowing()){
                    csprogress.dismiss();
                }

            }
        });



    }

    @Override
    public void pastOrderID(String pastOrderID) {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(PastOrderListActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api=APIClientPastOrderedProductListView.getApiInterfacePastOrderedProductListView();

        OrderID_DTO order_IDToViewProdcutList=new OrderID_DTO(pastOrderID);

     Call<JSONResponseToViewPastOrderedProductList> call = api.getViewPastOrderProductList(order_IDToViewProdcutList);

     call.enqueue(new Callback<JSONResponseToViewPastOrderedProductList>() {
         @Override
         public void onResponse(Call<JSONResponseToViewPastOrderedProductList> call, Response<JSONResponseToViewPastOrderedProductList> response) {

             if(response.isSuccessful()){

                 if(csprogress.isShowing()){
                     csprogress.dismiss();
                 }


                 Intent orderedProductListView=new Intent(PastOrderListActivity.this,PastOrderedProductListView.class);

                 JSONResponseToViewPastOrderedProductList jsonResponseToViewPastOrderedProductList=response.body();
               String pastOrderGrandTotal= jsonResponseToViewPastOrderedProductList.getPastOrderGrandTotal();
             String pastOrderedUserName=jsonResponseToViewPastOrderedProductList.getPastOrderedProductUserName();

                 List<JsonResponseToViewPastOrderedProductListDTO> jsonResponseToViewPastOrderedProductListDTO=jsonResponseToViewPastOrderedProductList.getJsonResponseToViewPastOrderedProductListDTO();

                 orderedProductListView.putExtra("PAST_ORDERED_PRODUCT_LIST", (Serializable) jsonResponseToViewPastOrderedProductListDTO);

                 orderedProductListView.putExtra("PAST_ORDERED_PRODUCT_GRANDTOTAL",pastOrderGrandTotal);
                 orderedProductListView.putExtra("PAST_ORDERED_PRODUCT_USER_NAME",pastOrderedUserName);
                 startActivity(orderedProductListView);



             }
         }

         @Override
         public void onFailure(Call<JSONResponseToViewPastOrderedProductList> call, Throwable t) {

             if(csprogress.isShowing()){
                 csprogress.dismiss();
             }

         }
     });

    }
}
