package com.example.saravanamurali.farmersgen.fragment;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseViewCartListDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonResponseFromServerDBDTO;
import com.example.saravanamurali.farmersgen.models.AddCartDTO;
import com.example.saravanamurali.farmersgen.models.GetDataFromSqlLiteDTO;
import com.example.saravanamurali.farmersgen.models.ViewCartDTO;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientForViewCart;
import com.example.saravanamurali.farmersgen.retrofitclient.ApiClientToMoveDataFromSqlLiteToServerDB;
import com.example.saravanamurali.farmersgen.util.Network_config;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Count_Price_Show_Fragment extends Fragment {

    int fragment_productCount;

    String currentUser;
    String brand_ID_For_ProductList;
    String brand_Name_For_ProductList;
    String brand_Name_For_ProductRating;

    FrameLayout frameLayout;

    public Count_Price_Show_Fragment() {

    }


    public void getCountPrice() {


    }

    TextView textViewName;
    TextView totalItem;
    TextView totalPrice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_count__price__show, container, false);

        //loadViewCartProductList();

        textViewName = (TextView) view.findViewById(R.id.viewCart);
        totalItem = (TextView) view.findViewById(R.id.totalItem);
        totalPrice = (TextView) view.findViewById(R.id.totalPrice);
        frameLayout = (FrameLayout) view.findViewById(R.id.fragmeLayout);


        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getAllDataFromSQLLiteDataBase();
                
                moveDataFromSqlLiteToServerDB();


            }
        });


        return view;
    }



    private void getAllDataFromSQLLiteDataBase() {



    }

    private void moveDataFromSqlLiteToServerDB() {

        ApiInterface api=ApiClientToMoveDataFromSqlLiteToServerDB.getAPIInterfaceToMoveDataFromSqlLiteToServerDB();

        Call<JsonResponseFromServerDBDTO> call=api.moveSqlLiteDataToSever();

       call.enqueue(new Callback<JsonResponseFromServerDBDTO>() {
           @Override
           public void onResponse(Call<JsonResponseFromServerDBDTO> call, Response<JsonResponseFromServerDBDTO> response) {

               JsonResponseFromServerDBDTO jsonResponseFromServerDBDTO=response.body();
               if(jsonResponseFromServerDBDTO.getStatus()==200){

                   startActivity(new Intent(getActivity(),ViewCartActivity.class));
               }
               else if(jsonResponseFromServerDBDTO.getStatus()==500){

               }


           }

           @Override
           public void onFailure(Call<JsonResponseFromServerDBDTO> call, Throwable t) {

           }
       });

    }


   /* private void loadViewCartProductList() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(this.getActivity());
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        String ANDROID_MOBILE_ID = Settings.Secure.getString(this.getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);


        ApiInterface api = APIClientForViewCart.getApiInterfaceForViewCart();
        AddCartDTO loadFragment = new AddCartDTO(ANDROID_MOBILE_ID);
        Call<JSONResponseViewCartListDTO> call = api.getViewCart(loadFragment);

        call.enqueue(new Callback<JSONResponseViewCartListDTO>() {
            @Override
            public void onResponse(Call<JSONResponseViewCartListDTO> call, Response<JSONResponseViewCartListDTO> response) {

                if(csprogress.isShowing()){
                    csprogress.dismiss();
                }


                JSONResponseViewCartListDTO jsonResponseViewCartListDTO = response.body();
                List<ViewCartDTO> viewCartProductListDTO = jsonResponseViewCartListDTO.getViewCartListRecord();

                String GrandTotal = jsonResponseViewCartListDTO.getGrandTotal();
                String count = viewCartProductListDTO.get(0).getCount();
                System.out.println("GRANDTOTAL" + GrandTotal);
                int c = 0;
                for (int i = 0; i < viewCartProductListDTO.size(); i++) {

                    c = c + Integer.parseInt(viewCartProductListDTO.get(i).getCount());

                }

                String cd=String.valueOf(c);
                if(GrandTotal!=null && cd!=null ) {

                    totalPrice.setText(GrandTotal);
                    totalItem.setText(cd);
                }

                else{
                    totalItem.setText("");
                    totalPrice.setText("");
                }
            }


            @Override
            public void onFailure(Call<JSONResponseViewCartListDTO> call, Throwable t) {


            }
        });

    }*/

}


