package com.example.saravanamurali.farmersgen.recyclerviewadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.models.ReviewDTO;
import com.example.saravanamurali.farmersgen.models.ReviewDetailsDTO;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    Context mCtx;
    List<ReviewDetailsDTO> reviewDTOList;

    public ReviewAdapter(List<ReviewDetailsDTO> reviewDTOList,Context mCtx) {
        this.mCtx = mCtx;
        this.reviewDTOList = reviewDTOList;
    }

    public void setDataForReview(List<ReviewDetailsDTO> reviewDTOList){
        this.reviewDTOList=reviewDTOList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(mCtx);
        View view=layoutInflater.inflate(R.layout.review_adapter_view,viewGroup,false);
        ReviewViewHolder reviewViewHolder=new ReviewViewHolder(view);

        return reviewViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder reviewViewHolder, int i) {
        reviewViewHolder.reviewUser.setText(reviewDTOList.get(i).getReviewUser());
        reviewViewHolder.reviewMessage.setText(reviewDTOList.get(i).getReviewMessage());

    }

    @Override
    public int getItemCount() {
        return reviewDTOList.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder{

        TextView reviewUser;
        TextView reviewMessage;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);

            reviewUser=(TextView)itemView.findViewById(R.id.reviewUser);
            reviewMessage=(TextView)itemView.findViewById(R.id.reviewMessage);
        }
    }
}
