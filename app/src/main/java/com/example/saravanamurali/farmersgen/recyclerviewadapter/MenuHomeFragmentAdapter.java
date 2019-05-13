package com.example.saravanamurali.farmersgen.recyclerviewadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.models.HomeProductDTO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MenuHomeFragmentAdapter extends RecyclerView.Adapter<MenuHomeFragmentAdapter.MenuHomeFragmentViewHolder> {

    Context mCtx;
    List<HomeProductDTO> homeProductDTOList;
    private OnItemClickListener mListener;

    public MenuHomeFragmentAdapter(Context mCtx, List<HomeProductDTO> homeProductDTOList) {
        this.mCtx = mCtx;
        this.homeProductDTOList = homeProductDTOList;
    }

   public void setData(List<HomeProductDTO> hoomeproduct){
        this.homeProductDTOList=hoomeproduct;
        notifyDataSetChanged();
    }

    public  interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }




    @NonNull
    @Override
    public MenuHomeFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater  inflater=LayoutInflater.from(mCtx);
        View view=inflater.inflate(R.layout.fragment_menu_home_products,parent,false);
        MenuHomeFragmentViewHolder menuHomeFragmentViewHolder=new MenuHomeFragmentViewHolder(view);

        return menuHomeFragmentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuHomeFragmentViewHolder holder, int position) {



        /*String brand_Id=homeProductDTOList.get(position).getBrandId();
        System.out.println("This is brand id of brand"+brand_Id);*/
        holder.animationContainer.setAnimation(AnimationUtils.loadAnimation(mCtx,R.anim.fade_scale_animation));

        holder.mtName.setText(homeProductDTOList.get(position).getProductName());
        holder.mDescription.setText(homeProductDTOList.get(position).getProductDesc());
        holder.mMinOrder.setText(homeProductDTOList.get(position).getProductMinOrder());
        holder.mRating.setText(homeProductDTOList.get(position).getProductRating());
        Picasso.with(mCtx).load(homeProductDTOList.get(position).getProductImage()).into(holder.mImage);

    }

    @Override
    public int getItemCount() {
        return homeProductDTOList.size();
    }

    class MenuHomeFragmentViewHolder extends  RecyclerView.ViewHolder{

        ImageView mImage;
        TextView mtName;
        TextView mDescription;
        TextView mMinOrder;
        TextView mRating;
        LinearLayout animationContainer;

        public MenuHomeFragmentViewHolder(@NonNull View itemView) {
            super(itemView);

            animationContainer=itemView.findViewById(R.id.animationContainer);
            mImage=itemView.findViewById(R.id.productImage);
            mtName=itemView.findViewById(R.id.productName);
            mDescription=itemView.findViewById(R.id.productDescription);
            mMinOrder=itemView.findViewById(R.id.minOrderProduct);
            mRating=itemView.findViewById(R.id.ratingProduct);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int position=getAdapterPosition();

                        if(position!=RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);

                        }

                    }
                }
            });


        }
    }


    //Starts
    public void updateList(List<HomeProductDTO>  newList){
        //homeProductDTOList=new ArrayList<>();
       homeProductDTOList.addAll(newList);
        notifyDataSetChanged();

    }
}
