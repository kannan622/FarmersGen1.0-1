package com.example.saravanamurali.farmersgen.coupon;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.fragment.ViewCartActivity;
import com.example.saravanamurali.farmersgen.models.ApplyCouponDTO;
import com.example.saravanamurali.farmersgen.models.CouponDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseApplyCouponDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseCouponDTO;
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

    ImageView leftArrow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);


        couponRecylerView=(RecyclerView)findViewById(R.id.recyclerViewCoupon);
        couponRecylerView.setLayoutManager(new LinearLayoutManager(CouponActivity.this));
        couponRecylerView.setHasFixedSize(true);

        //leftArrow=(ImageView)findViewById(R.id.leftCoupon);

        jsonResponseCouponDTO=new ArrayList<CouponDTO>();

        couponAdapter=new CouponAdapter(jsonResponseCouponDTO,CouponActivity.this);

        couponRecylerView.setAdapter(couponAdapter);



        couponAdapter.setShareCouponCode(CouponActivity.this);

        /*leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewCartActivity=new Intent(CouponActivity.this,ViewCartActivity.class);
                startActivity(viewCartActivity);
                finish();
            }
        });*/

        //Disply all coupon from DB
        loadCouponCode();

    }

    private void loadCouponCode() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(CouponActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api=APIClientToGetCoupon.getApiInterfaceToGetCoupon();
        Call<JSONResponseCouponDTO> call =api.getCouponCode();

        call.enqueue(new Callback<JSONResponseCouponDTO>() {
            @Override
            public void onResponse(Call<JSONResponseCouponDTO> call, Response<JSONResponseCouponDTO> response) {
                if(response.isSuccessful()){

                    if(csprogress.isShowing()){
                        csprogress.dismiss();
                    }

                    JSONResponseCouponDTO jsonResponseCouponDTO =response.body();

                    List<CouponDTO> couponDTO=jsonResponseCouponDTO.getJsonResponseCouponDTO();

                    couponAdapter.setCouponData(couponDTO);

                    couponAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<JSONResponseCouponDTO> call, Throwable t) {
                if(csprogress.isShowing()){
                    csprogress.dismiss();
                }

            }
        });


    }

    //Apply Coupon Code

    @Override
    public void applyCouponCode(String coupson_code,String couponID,String off_Price) {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(CouponActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCancelable(false);
        csprogress.setCanceledOnTouchOutside(false);


        SharedPreferences getCurrentUser = getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
        String curUser_CouponCode = getCurrentUser.getString("CURRENTUSER", "NO_CURRENT_USER");

        if (!curUser_CouponCode.equals(NO_CURRENT_USER)) {

            ApiInterface api=APIClientToApplyCode.getApiInterfaceToApplyCode();

            ApplyCouponDTO applyCouponDTO=new ApplyCouponDTO(curUser_CouponCode,coupson_code,couponID,off_Price);

            Call<JSONResponseApplyCouponDTO> call=api.applyCoupon(applyCouponDTO);

            call.enqueue(new Callback<JSONResponseApplyCouponDTO>() {
                @Override
                public void onResponse(Call<JSONResponseApplyCouponDTO> call, Response<JSONResponseApplyCouponDTO> response) {

                    if (response.isSuccessful()) {
                        if(csprogress.isShowing()){
                            csprogress.dismiss();
                        }


                        System.out.println("I am inside ");

                        JSONResponseApplyCouponDTO jsonResponseApplyCouponDTO = response.body();

                        if (jsonResponseApplyCouponDTO.getResponseCode() == 200) {

                            //Offer is avaliable can access offer

                            String applied_Coupon=jsonResponseApplyCouponDTO.getCoupon_ID();
                            String coupon_Code=jsonResponseApplyCouponDTO.getCoupon_Code();

                            //Storing couponID
                            SharedPreferences current_CouponID = getSharedPreferences("CURRENT_COUPON_ID", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editorID = current_CouponID.edit();
                            editorID.putString("COUPONID", applied_Coupon);
                            editorID.commit();

                            //Storing couponCODE
                            SharedPreferences current_Coupon_Code = getSharedPreferences("CURRENT_COUPON_CODE", Context.MODE_PRIVATE);
                            SharedPreferences.Editor c_CODE = current_Coupon_Code.edit();
                            c_CODE.putString("COUPON_CODE", coupon_Code);
                            c_CODE.commit();


                            System.out.println("I am inside A ");

                            Intent applyCoupon = new Intent(CouponActivity.this, ViewCartActivity.class);
                            applyCoupon.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(applyCoupon);
                            finish();

                        } else if (jsonResponseApplyCouponDTO.getResponseCode() == 500) {

                            Toast.makeText(CouponActivity.this, "You already applied this coupon,Try another one", Toast.LENGTH_LONG).show();

                        }


                    }
                }

                @Override
                public void onFailure(Call<JSONResponseApplyCouponDTO> call, Throwable t) {
                    if(csprogress.isShowing()){
                        csprogress.dismiss();
                    }

                    Toast.makeText(CouponActivity.this,"Error",Toast.LENGTH_LONG).show();

                }
            });



        }
        else {
            Toast.makeText(CouponActivity.this,"Please Login",Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void viewCouponDetails(String cou_Code, String coupon_id) {
        CouponBottomSheetDialog couponBottomSheetDialog=new CouponBottomSheetDialog(CouponActivity.this,cou_Code,coupon_id);

        couponBottomSheetDialog.show(getSupportFragmentManager(),"couponBottomSheet");
    }


}
