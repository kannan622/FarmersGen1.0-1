package com.example.saravanamurali.farmersgen.coupon;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.modeljsonresponse.CouponDetailResponse;
import com.example.saravanamurali.farmersgen.models.CouponDTO;
import com.example.saravanamurali.farmersgen.productdescription.ProductDescriptionActivity;
import com.example.saravanamurali.farmersgen.retrofitclient.ApiClientForCouponDetail;
import com.squareup.picasso.Picasso;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class CouponBottomSheetDialog extends BottomSheetDialogFragment {

    Context context;
    String cou_code;
    String coupon_id;

    ImageView couponImage;
    TextView darkText;
    TextView normalText;
    TextView t1, t2, t3, t4, t5;


    @SuppressLint("ValidFragment")
    public CouponBottomSheetDialog(Context context, String cou_code, String coupon_id) {
        this.context = context;
        this.cou_code = cou_code;
        this.coupon_id = coupon_id;

        //Toast.makeText(getActivity(),cou_code+" "+coupon_id,Toast.LENGTH_LONG).show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.coupon_sheet_layout, container, false);

        Fabric.with(getActivity(), new Crashlytics());

        couponImage = (ImageView) view.findViewById(R.id.sheetImage);
        darkText = (TextView) view.findViewById(R.id.sheetBold);
        normalText = (TextView) view.findViewById(R.id.sheetNormal);

        t1 = (TextView) view.findViewById(R.id.sheetMiniumOrder);
        t2 = (TextView) view.findViewById(R.id.t2);
        t3 = (TextView) view.findViewById(R.id.t3);
        t4 = (TextView) view.findViewById(R.id.sheetDiscountAmount);
        t5 = (TextView) view.findViewById(R.id.sheetValid);

        Typeface roboto = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");

        t1.setTypeface(roboto);
        t2.setTypeface(roboto);
        t3.setTypeface(roboto);
        t4.setTypeface(roboto);
        t5.setTypeface(roboto);

        loadCouponDetail();

        return view;
    }

    private void loadCouponDetail() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(context);
        csprogress.setMessage("Loading...");
        csprogress.setCancelable(false);
        csprogress.setCanceledOnTouchOutside(false);
        csprogress.show();


        ApiInterface apiInterface = ApiClientForCouponDetail.getApiInterfaceForCouponDetail();

        CouponDTO couponDTO = new CouponDTO(coupon_id);

        Call<CouponDetailResponse> call = apiInterface.viewCouponDetails(couponDTO);

        call.enqueue(new Callback<CouponDetailResponse>() {
            @Override
            public void onResponse(Call<CouponDetailResponse> call, Response<CouponDetailResponse> response) {

                CouponDetailResponse couponDetailResponse = response.body();

                if (couponDetailResponse.getResponseCode() == 200) {

                    Picasso.with(context).load(couponDetailResponse.getCoupon_image()).into(couponImage);
                    darkText.setText(couponDetailResponse.getCoupon_Description1());
                    normalText.setText(couponDetailResponse.getCoupon_Description2());

                    t1.setText(couponDetailResponse.getCondition1());
                    t2.setText(couponDetailResponse.getCondition2());
                    t3.setText(couponDetailResponse.getCondition3());
                    t4.setText(couponDetailResponse.getCondition4());
                    t5.setText(couponDetailResponse.getCondition5());

                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }


                } else if (couponDetailResponse.getResponseCode() == 500) {
                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<CouponDetailResponse> call, Throwable t) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

            }
        });


    }
}
