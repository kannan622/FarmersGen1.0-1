package com.example.saravanamurali.farmersgen.fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonResponseFromServerDBDTO;
import com.example.saravanamurali.farmersgen.models.GetDataFromSqlLiteDTO;
import com.example.saravanamurali.farmersgen.retrofitclient.ApiClientToMoveDataFromSqlLiteToServerDB;
import com.example.saravanamurali.farmersgen.sqllite.ProductAddInSqlLite;
import com.example.saravanamurali.farmersgen.tappedactivity.HomeActivity;
import com.example.saravanamurali.farmersgen.util.Network_config;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

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

    private Dialog dialog;

    //SQLLite
    SQLiteDatabase mSqLiteDatabaseInLogout;


    public Count_Price_Show_Fragment() {

    }


    public void getCountPrice() {


    }

    TextView textViewName;
    TextView totalItem;
    TextView totalPrice;
    SQLiteDatabase mSqLiteDatabase;
    List<GetDataFromSqlLiteDTO> getDataFromSqlLiteDTOS;
    GetDataFromSqlLiteDTO getDataFromSqlLiteDTO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_count__price__show, container, false);

        mSqLiteDatabaseInLogout = getActivity().openOrCreateDatabase(ProductAddInSqlLite.DATABASE_NAME, MODE_PRIVATE, null);

        dialog = new Dialog(getActivity());


        mSqLiteDatabase = getActivity().openOrCreateDatabase(ProductAddInSqlLite.DATABASE_NAME, MODE_PRIVATE, null);
        //loadViewCartProductList();

        getDataFromSqlLiteDTOS = new ArrayList<GetDataFromSqlLiteDTO>();

        textViewName = (TextView) view.findViewById(R.id.viewCart);
        totalItem = (TextView) view.findViewById(R.id.totalItem);
        totalPrice = (TextView) view.findViewById(R.id.totalPrice);
        frameLayout = (FrameLayout) view.findViewById(R.id.fragmeLayout);


        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getAllDataFromSQLLiteDataBase();


            }
        });


        return view;
    }


    private void getAllDataFromSQLLiteDataBase() {

        Product_List_Activity product_list_activity = new Product_List_Activity();


        //DB connection and select query goes here
        loadProductListDataFromSqlLite();


    }

    private void loadProductListDataFromSqlLite() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(getActivity());
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);

        String ANDROID_MOBILE_ID = Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Cursor cursor = mSqLiteDatabase.rawQuery("select product_code,count,total_price,device_id from add_cart where device_id=?", new String[]{ANDROID_MOBILE_ID});

        if (cursor.moveToFirst()) {

            do {


                getDataFromSqlLiteDTO = new GetDataFromSqlLiteDTO(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));

                /*System.out.println("SQL LISTE SELECT count priceeeeeee>>>>>>>>>>>>");
                System.out.println("productCode"+getDataFromSqlLiteDTO.getProduct_code());
                System.out.println("count"+getDataFromSqlLiteDTO.getCount());
                System.out.println("price"+getDataFromSqlLiteDTO.getTotal_price());
                System.out.println("deviceID"+getDataFromSqlLiteDTO.getDevice_ID());*/

                getDataFromSqlLiteDTOS.add(getDataFromSqlLiteDTO);


            }
            while (cursor.moveToNext());


        }

        if (csprogress.isShowing()) {
            csprogress.dismiss();
        }

        if (Network_config.is_Network_Connected_flag(getActivity())) {

            if (getDataFromSqlLiteDTOS.size()==0) {

                deleteAllDataFromSQLLite();

                startActivity(new Intent(this.getActivity(), HomeActivity.class));
                Toast.makeText(this.getActivity(), "You havent ordered any item in cart", Toast.LENGTH_LONG).show();


            } else if (getDataFromSqlLiteDTOS.size()>0) {
                moveDataFromSqlLiteToServerDB();

            }

        } else {
            Network_config.customAlert(dialog, getActivity(), getResources().getString(R.string.app_name),
                    getResources().getString(R.string.connection_message));
        }
    }

    private void deleteAllDataFromSQLLite() {

        String delete_device_id = Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        String delete = "delete from add_cart where device_id=? ";

        mSqLiteDatabaseInLogout.execSQL(delete, new String[]{delete_device_id});
    }

    private void moveDataFromSqlLiteToServerDB() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(getActivity());
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);

        ApiInterface api = ApiClientToMoveDataFromSqlLiteToServerDB.getAPIInterfaceToMoveDataFromSqlLiteToServerDB();

        Call<JsonResponseFromServerDBDTO> call = api.moveSqlLiteDataToSever(getDataFromSqlLiteDTOS);

        call.enqueue(new Callback<JsonResponseFromServerDBDTO>() {
            @Override
            public void onResponse(Call<JsonResponseFromServerDBDTO> call, Response<JsonResponseFromServerDBDTO> response) {

                JsonResponseFromServerDBDTO jsonResponseFromServerDBDTO = response.body();


                if (jsonResponseFromServerDBDTO.getStatus() == 200) {


                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }

                    startActivity(new Intent(getActivity(), ViewCartActivity.class));
                } else if (jsonResponseFromServerDBDTO.getStatus() == 500) {
                    //Log.d("respo2222", "22");
                } else {
                    //Log.d("end", "end");
                }
            }

            @Override
            public void onFailure(Call<JsonResponseFromServerDBDTO> call, Throwable t) {
                //Log.d("lass", "last");
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


