package com.example.saravanamurali.farmersgen.recyclerviewadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.models.CouponDTO;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.CouponViewHolder> {

    List<CouponDTO> jsonResponseCouponDTOList;
    Context mCouponContext;


    ShareCouponCode shareCouponCode;

    public interface ShareCouponCode{

        public void applyCouponCode(String coupson_code, String couponID,String offer_price);
        public void viewCouponDetails(String cou_Code,String  coupon_id);

    }

    public  void setShareCouponCode(ShareCouponCode shareCouponCode){
        this.shareCouponCode=shareCouponCode;
        notifyDataSetChanged();
    }


    public CouponAdapter(List<CouponDTO> jsonResponseCouponDTOList, Context mCouponContext) {
        this.jsonResponseCouponDTOList = jsonResponseCouponDTOList;
        this.mCouponContext = mCouponContext;
    }

    public void setCouponData(List<CouponDTO> jsonResponseCouponDTOList) {

        this.jsonResponseCouponDTOList=jsonResponseCouponDTOList;
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public CouponViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater=LayoutInflater.from(mCouponContext);
        View view=inflater.inflate(R.layout.coupon_adapter_view,viewGroup,false);

        CouponViewHolder couponViewHolder=new CouponViewHolder(view);
        return couponViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CouponViewHolder couponViewHolder, int i) {

        Picasso.with(mCouponContext).load(jsonResponseCouponDTOList.get(i).getCouponImage()).into(couponViewHolder.couponImage);
        couponViewHolder.desc1.setText(jsonResponseCouponDTOList.get(i).getDesc1());
        couponViewHolder.desc2.setText(jsonResponseCouponDTOList.get(i).getDesc2());
    }

    @Override
    public int getItemCount() {
        return jsonResponseCouponDTOList.size();
    }

    class CouponViewHolder extends RecyclerView.ViewHolder{

        ImageView couponImage;
        TextView applyCoupon;
        TextView desc1;
        TextView desc2;

        public CouponViewHolder(@NonNull View itemView) {
            super(itemView);

            couponImage=(ImageView)itemView.findViewById(R.id.couponImage);
            applyCoupon=(TextView)itemView.findViewById(R.id.apply);
            desc1=(TextView)itemView.findViewById(R.id.getCouponText);
            desc2=(TextView)itemView.findViewById(R.id.couponDesc);

            applyCoupon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int getCouponAdapterPosition=getAdapterPosition();
                    CouponDTO  couponDTO=jsonResponseCouponDTOList.get(getCouponAdapterPosition);
                    String couponCode=couponDTO.getCouponCode();
                    String coupID=couponDTO.getCouponId();
                    String offerPrice=couponDTO.getCoupon_OffPrice();


                    shareCouponCode.applyCouponCode(couponCode,coupID,offerPrice);

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int getCoupon_AdapterPosition=getAdapterPosition();
                    CouponDTO couponDTO=jsonResponseCouponDTOList.get(getCoupon_AdapterPosition);

                    String coupon_Code=couponDTO.getCouponCode();
                    String coupon_ID=couponDTO.getCouponId();

                    shareCouponCode.viewCouponDetails(coupon_Code,coupon_ID);
                }
            });

        }
    }
}
