package com.example.saravanamurali.farmersgen.recyclerviewadapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.models.FavBrandDTO;
import com.example.saravanamurali.farmersgen.util.Network_config;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavouriteListAdapter extends RecyclerView.Adapter<FavouriteListAdapter.FavouriteListViewHolder> {

    Context favContext;
    List<FavBrandDTO> favBrandDTOList;

    OnFavClickListener onFavClickListener;

    Dialog dialog;

    public interface OnFavClickListener {
        void onFavItemClick(int position);
    }

    public void setOnFavClickListener(OnFavClickListener onFavClickListener) {
        this.onFavClickListener = onFavClickListener;
    }

    public FavouriteListAdapter(Context favContext, List<FavBrandDTO> favBrandDTOList) {
        this.favContext = favContext;
        this.favBrandDTOList = favBrandDTOList;
    }

    public void setFavData(List<FavBrandDTO> favBrandDTOList){
        this.favBrandDTOList=favBrandDTOList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavouriteListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(favContext);
        View view = layoutInflater.inflate(R.layout.favourite_list_adapter_view, viewGroup, false);

        dialog=new Dialog(favContext);

        FavouriteListViewHolder favouriteListViewHolder = new FavouriteListViewHolder(view);

        return favouriteListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteListViewHolder favouriteListViewHolder, int i) {

        favouriteListViewHolder.fav_mtName.setText(favBrandDTOList.get(i).getFav_productName());
        favouriteListViewHolder.fav_mDescription.setText(favBrandDTOList.get(i).getFav_productDesc());
        favouriteListViewHolder.fav_mRating.setText(favBrandDTOList.get(i).getFav_productRating());
        Picasso.with(favContext).load(favBrandDTOList.get(i).getFav_productImage()).into(favouriteListViewHolder.fav_mImage);

    }

    @Override
    public int getItemCount() {
        return favBrandDTOList.size();
    }

    class FavouriteListViewHolder extends RecyclerView.ViewHolder {

        ImageView fav_mImage;
        TextView fav_mtName;
        TextView fav_mDescription;
        TextView fav_mMinOrder;
        TextView fav_mRating;


        public FavouriteListViewHolder(@NonNull View itemView) {
            super(itemView);

            fav_mImage = itemView.findViewById(R.id.favProductImage);
            fav_mtName = itemView.findViewById(R.id.favProductName);
            fav_mDescription = itemView.findViewById(R.id.favProductDescription);
            fav_mMinOrder = itemView.findViewById(R.id.favMinOrderProduct);
            fav_mRating = itemView.findViewById(R.id.favRatingProduct);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Network_config.is_Network_Connected_flag(favContext)) {

                        int favAdapterPosition = getAdapterPosition();
                        //FavBrandDTO favBrandDTO =favBrandDTOList.get(favAdapterPosition);

                        //String getFavBrandId=favBrandDTO.getFav_brandId();

                        onFavClickListener.onFavItemClick(favAdapterPosition);
                    }
                    else {
                        Network_config.customAlert(dialog, favContext, favContext.getResources().getString(R.string.app_name),
                                favContext.getResources().getString(R.string.connection_message));
                    }

                }
            });

        }
    }
}
