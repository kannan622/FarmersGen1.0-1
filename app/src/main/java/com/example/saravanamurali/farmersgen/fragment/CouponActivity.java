package com.example.saravanamurali.farmersgen.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.models.ApplyCouponDTO;
import com.example.saravanamurali.farmersgen.models.CouponDTO;
import com.example.saravanamurali.farmersgen.models.JSONResponseApplyCouponDTO;
import com.example.saravanamurali.farmersgen.models.JSONResponseCouponDTO;
import com.example.saravanamurali.farmersgen.recyclerviewadapter.CouponAdapter;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToApplyCode;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToGetCoupon;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CouponActivity extends AppCompatActivity implements CouponAdapter.ShareCouponCode {


    private String NO_CURRENT_USER = "NO_CURRENT_USER";

    RecyclerView couponRecylerView;
    List<CouponDTO> jsonResponseCouponDTO;

    CouponAdapter couponAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);


        couponRecylerView=(RecyclerView)findViewById(R.id.recyclerViewCoupon);
        couponRecylerView.setLayoutManager(new LinearLayoutManager(CouponActivity.this));
        couponRecylerView.setHasFixedSize(true);

        jsonResponseCouponDTO=new ArrayList<CouponDTO>();

        couponAdapter=new CouponAdapter(jsonResponseCouponDTO,CouponActivity.this);

        couponRecylerView.setAdapter(couponAdapter);

        couponAdapter.setShareCouponCode(CouponActivity.this);

        //Disply all coupon from DB
        loadCouponCode();

    }

    private void loadCouponCode() {

        ApiInterface api=APIClientToGetCoupon.getApiInterfaceToGetCoupon();
        Call<JSONResponseCouponDTO> call =api.getCouponCode();

        call.enqueue(new Callback<JSONResponseCouponDTO>() {
            @Override
            public void onResponse(Call<JSONResponseCouponDTO> call, Response<JSONResponseCouponDTO> response) {
                if(response.isSuccessful()){

                    JSONResponseCouponDTO jsonResponseCouponDTO =response.body();

                    List<CouponDTO> couponDTO=jsonResponseCouponDTO.getJsonResponseCouponDTO();

                    couponAdapter.setCouponData(couponDTO);

                }
            }

            @Override
            public void onFailure(Call<JSONResponseCouponDTO> call, Throwable t) {

            }
        });


    }

    //Apply Coupon Code

    @Override
    public void applyCouponCode(String coupson_code,String couponID) {

        SharedPreferences getCurrentUser = getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
        String curUser_CouponCode = getCurrentUser.getString("CURRENTUSER", "NO_CURRENT_USER");

        if (!curUser_CouponCode.equals(NO_CURRENT_USER)) {

            ApiInterface api=APIClientToApplyCode.getApiInterfaceToApplyCode();

            ApplyCouponDTO applyCouponDTO=new ApplyCouponDTO(curUser_CouponCode,coupson_code,couponID);

            Call<JSONResponseApplyCouponDTO> call=api.applyCoupon(applyCouponDTO);

            call.enqueue(new Callback<JSONResponseApplyCouponDTO>() {
                @Override
                public void onResponse(Call<JSONResponseApplyCouponDTO> call, Response<JSONResponseApplyCouponDTO> response) {
                    if(response.isSuccessful()){

                        JSONResponseApplyCouponDTO jsonResponseApplyCouponDTO=response.body();

                        if(jsonResponseApplyCouponDTO.getResponseCode()=="200"){

                            Intent applyCoupon=new Intent(CouponActivity.this,ViewCartActivity.class);
                            applyCoupon.putExtra("COUPON_CODE",jsonResponseApplyCouponDTO.getCoupon_Code());
                            applyCoupon.putExtra("COUPON_ID",jsonResponseApplyCouponDTO.getCoupon_ID());
                            startActivity(applyCoupon);
                            finish();

                        }
                        else {

                            Toast.makeText(CouponActivity.this,"You already applied this coupon",Toast.LENGTH_LONG).show();

                        }
                    }
                }

                @Override
                public void onFailure(Call<JSONResponseApplyCouponDTO> call, Throwable t) {

                }
            });







        }
        else {
            Toast.makeText(CouponActivity.this,"Please Login",Toast.LENGTH_LONG).show();
        }


    }



}
