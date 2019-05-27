package com.example.saravanamurali.farmersgen.coupon;

import android.annotation.SuppressLint;
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

import com.example.saravanamurali.farmersgen.R;

@SuppressLint("ValidFragment")
public class CouponBottomSheetDialog extends BottomSheetDialogFragment {

    Context context;
    String cou_code;
    String coupon_id;

    ImageView couponImage;
    TextView darkText;
    TextView normalText;
    TextView t1,t2,t3,t4,t5;


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

        couponImage=(ImageView) view.findViewById(R.id.sheetImage);
        darkText=(TextView)view.findViewById(R.id.sheetBold);
        normalText=(TextView)view.findViewById(R.id.sheetNormal);

        t1=(TextView)view.findViewById(R.id.sheetMiniumOrder);
        t2=(TextView)view.findViewById(R.id.t2);
        t3=(TextView)view.findViewById(R.id.t3);
        t4=(TextView)view.findViewById(R.id.sheetDiscountAmount);
        t5=(TextView)view.findViewById(R.id.sheetValid);

        Typeface roboto=Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Regular.ttf");

        t1.setTypeface(roboto);
        t2.setTypeface(roboto);
        t3.setTypeface(roboto);
        t4.setTypeface(roboto);
        t5.setTypeface(roboto);

        return view;
    }
}
