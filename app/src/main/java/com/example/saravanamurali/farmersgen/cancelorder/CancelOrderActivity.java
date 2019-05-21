package com.example.saravanamurali.farmersgen.cancelorder;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.io.Serializable;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.models.CurrentUserDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseForCancelOrderDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseToFetchCancelOrderDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseToViewOrderedProductList;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonResponseToViewOrderedProductListDTO;
import com.example.saravanamurali.farmersgen.models.OrderID_DTO;

import com.example.saravanamurali.farmersgen.retrofitclient.APIClientOrderedProductListView;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToCancelOrderUsingOrderID;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToGetCancelOrderList;
import com.example.saravanamurali.farmersgen.tappedactivity.HomeActivity;
import com.example.saravanamurali.farmersgen.util.Network_config;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CancelOrderActivity extends AppCompatActivity implements CancelOrderAdapter.CancelOrderUsingOrderIDInterace, CancelOrderAdapter.ViewProductDetailUsingOrderIDInterface {

    private String NO_CURRENT_USER = "NO_CURRENT_USER";
    int o_Size;

    //For Cancel Order Snack Bar
    List<JSONResponseForCancelOrderDTO> jsonResponseForCancelOrderDTOSForSnakcerBar;


    List<JSONResponseForCancelOrderDTO> jsonResponseForCancelOrderDTOS;
    RecyclerView recyclerViewCancelOrder;

    CancelOrderAdapter cancelOrderAdapter;

    private Context context;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_order);

        context = CancelOrderActivity.this;
        dialog = new Dialog(context);

        recyclerViewCancelOrder = (RecyclerView) findViewById(R.id.cancelOrderRecyclerView);
        recyclerViewCancelOrder.setLayoutManager(new LinearLayoutManager(CancelOrderActivity.this));
        recyclerViewCancelOrder.setHasFixedSize(true);

        jsonResponseForCancelOrderDTOS = new ArrayList<JSONResponseForCancelOrderDTO>();

        cancelOrderAdapter = new CancelOrderAdapter(CancelOrderActivity.this, jsonResponseForCancelOrderDTOS);
        recyclerViewCancelOrder.setAdapter(cancelOrderAdapter);

        if (Network_config.is_Network_Connected_flag(context)) {
            loadCancelOrderList();
        } else {
            Network_config.customAlert(dialog, context, getResources().getString(R.string.app_name),
                    getResources().getString(R.string.connection_message));
        }

        cancelOrderAdapter.setCancelOrderUsingOrderIDInterace(CancelOrderActivity.this);

        cancelOrderAdapter.setViewProductDetailUsingOrderIDInterface(CancelOrderActivity.this);


    }

    private void loadCancelOrderList() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(CancelOrderActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api = APIClientToGetCancelOrderList.getApiInterfaceToGetCancelOrderList();

        SharedPreferences getCurrentUser = getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
        String curUserAtCancelOrderList = getCurrentUser.getString("CURRENTUSER", "NO_CURRENT_USER");

        CurrentUserDTO currentUserDTO = new CurrentUserDTO(curUserAtCancelOrderList);
        Call<JSONResponseToFetchCancelOrderDTO> call = api.getCancelOrderList(currentUserDTO);

        call.enqueue(new Callback<JSONResponseToFetchCancelOrderDTO>() {
            @Override
            public void onResponse(Call<JSONResponseToFetchCancelOrderDTO> call, Response<JSONResponseToFetchCancelOrderDTO> response) {
                if (response.isSuccessful()) {


                    JSONResponseToFetchCancelOrderDTO jsonResponseToFetchCancelOrderDTO = response.body();
                    jsonResponseForCancelOrderDTOS = jsonResponseToFetchCancelOrderDTO.getJsonResponseForCancelOrderDTO();

                    cancelOrderAdapter.setCancelOrderList(jsonResponseForCancelOrderDTOS);

                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }

                }
            }

            @Override
            public void onFailure(Call<JSONResponseToFetchCancelOrderDTO> call, Throwable t) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

            }
        });


    }

    //Cancel order Using Order Id when cangetCancelOrderUsingOrderIDcel button is pressed
    @Override
    public void getCancelOrderUsingOrderID(final String orderID) {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(CancelOrderActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);

        SharedPreferences getCurrentUser = getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
        String curUserToCancelOrder = getCurrentUser.getString("CURRENTUSER", "NO_CURRENT_USER");


        ApiInterface api = APIClientToCancelOrderUsingOrderID.getAPIInterfaceToCancelOrderUsingOrderID();

        OrderID_DTO order_ID = new OrderID_DTO(orderID, curUserToCancelOrder);

        Call<ResponseBody> call = api.getCancelOrder(order_ID);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }


                    Toast.makeText(CancelOrderActivity.this, "Your Order No" + " " + orderID + " " + "has been cancelled", Toast.LENGTH_LONG).show();

                    checkOrderListIsAvaliableOrNot();

                    // loadCancelOrderList();


                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

                Toast.makeText(CancelOrderActivity.this, "Please try again!!!", Toast.LENGTH_LONG).show();
            }
        });


    }

    private void checkOrderListIsAvaliableOrNot() {
        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(CancelOrderActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);

        ApiInterface api = APIClientToGetCancelOrderList.getApiInterfaceToGetCancelOrderList();

        SharedPreferences getCurrentUser = getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
        String curUserAtCancelOrderList = getCurrentUser.getString("CURRENTUSER", "NO_CURRENT_USER");

        CurrentUserDTO currentUserDTO = new CurrentUserDTO(curUserAtCancelOrderList);
        Call<JSONResponseToFetchCancelOrderDTO> call = api.getCancelOrderList(currentUserDTO);

        call.enqueue(new Callback<JSONResponseToFetchCancelOrderDTO>() {
            @Override
            public void onResponse(Call<JSONResponseToFetchCancelOrderDTO> call, Response<JSONResponseToFetchCancelOrderDTO> response) {
                if (response.isSuccessful()) {

                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }

                    JSONResponseToFetchCancelOrderDTO jsonResponseToFetchCancelOrderDTO = response.body();
                    jsonResponseForCancelOrderDTOS = jsonResponseToFetchCancelOrderDTO.getJsonResponseForCancelOrderDTO();

                    if (jsonResponseForCancelOrderDTOS != null) {
                        loadCancelOrderList();
                    } else {

                        Toast.makeText(CancelOrderActivity.this, "You dont have any orders to cancel", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(CancelOrderActivity.this, HomeActivity.class));
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponseToFetchCancelOrderDTO> call, Throwable t) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

            }
        });


    }


    //View ordered products using orderID when ordered# is pressed
    @Override
    public void getViewProductDetailUsingOrderID(String orderIDToViewProductDetails) {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(CancelOrderActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api = APIClientOrderedProductListView.getgetApiInterfaceOrderedProductListView();

        OrderID_DTO order_IDToViewProdcutList = new OrderID_DTO(orderIDToViewProductDetails);

        Call<JSONResponseToViewOrderedProductList> call = api.getViewCancelOrderProductList(order_IDToViewProdcutList);

        call.enqueue(new Callback<JSONResponseToViewOrderedProductList>() {
            @Override
            public void onResponse(Call<JSONResponseToViewOrderedProductList> call, Response<JSONResponseToViewOrderedProductList> response) {
                if (response.isSuccessful()) {

                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }


                    Toast.makeText(CancelOrderActivity.this, "OrderedProductListView", Toast.LENGTH_LONG).show();
                    Intent orderedProductListView = new Intent(CancelOrderActivity.this, OrderedProductListView.class);


                    JSONResponseToViewOrderedProductList jsonResponseToViewOrderedProductList = response.body();

                    String grandTotalOfOrderedProducts = jsonResponseToViewOrderedProductList.getGrandTotal();

                    String userName = jsonResponseToViewOrderedProductList.getOrderedProductUserName();

                    List<JsonResponseToViewOrderedProductListDTO> jsonResponseViewOrderedProducdtListDTO = jsonResponseToViewOrderedProductList.getJsonResponseToViewOrderedProductListDTOS();


                    orderedProductListView.putExtra("ORDERED_PRODUCT_LIST", (Serializable) jsonResponseViewOrderedProducdtListDTO);

                    orderedProductListView.putExtra("ORDERED_PRODUCT_GRANDTOTAL", grandTotalOfOrderedProducts);
                    orderedProductListView.putExtra("ORDERED_PRODUCT_USER_NAME", userName);
                    startActivity(orderedProductListView);


                 /* for (int i = 0; i < jsonResponseViewOrderedProducdtListDTO.size(); i++) {

                   String orderedProductName=jsonResponseViewOrderedProducdtListDTO.get(i).getOrderedProducName();
                     String orderedProductCount=jsonResponseViewOrderedProducdtListDTO.get(i).getOrderedCount();
                      String orderedProductTotalPrice=jsonResponseViewOrderedProducdtListDTO.get(i).getOrderedTotalPrice();

                      JsonResponseToViewOrderedProductListDTO setJsonResponseViewOrderedProductListDTO=new JsonResponseToViewOrderedProductListDTO(orderedProductName,orderedProductCount,orderedProductTotalPrice);


                      
                  }*/


                }
            }

            @Override
            public void onFailure(Call<JSONResponseToViewOrderedProductList> call, Throwable t) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }


            }
        });


    }
}
