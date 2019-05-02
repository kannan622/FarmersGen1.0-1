package com.example.saravanamurali.farmersgen.pastorder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonResponseToViewPastOrderedProductListDTO;

import java.util.List;

public class PastOrderedProductListViewAdapter extends RecyclerView.Adapter<PastOrderedProductListViewAdapter.PastOrderedProductListView_ViewHolder> {

    List<JsonResponseToViewPastOrderedProductListDTO> getPastOrderedProductListDTO;
    Context mContextPastOrderedProductList;

    public PastOrderedProductListViewAdapter(List<JsonResponseToViewPastOrderedProductListDTO> getPastOrderedProductListDTO, Context mContextPastOrderedProductList) {
        this.getPastOrderedProductListDTO = getPastOrderedProductListDTO;
        this.mContextPastOrderedProductList = mContextPastOrderedProductList;
    }


    public void setPastOrderedProductListView(List<JsonResponseToViewPastOrderedProductListDTO> getPastOrderedProductListDTO){
        this.getPastOrderedProductListDTO=getPastOrderedProductListDTO;
        notifyDataSetChanged();
    }




    @NonNull
    @Override
    public PastOrderedProductListView_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(mContextPastOrderedProductList);
        View view=inflater.inflate(R.layout.pastorderedproductlist_viewadapter,viewGroup,false);
        PastOrderedProductListView_ViewHolder pastOrderedProductListView_viewHolder=new PastOrderedProductListView_ViewHolder(view);
        return pastOrderedProductListView_viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PastOrderedProductListView_ViewHolder pastOrderedProductListView_viewHolder, int i) {

        pastOrderedProductListView_viewHolder.pastOrderedProrudctName.setText(getPastOrderedProductListDTO.get(i).getPastOrderedProducName());
        pastOrderedProductListView_viewHolder.pastOrderedProrudcCount.setText(getPastOrderedProductListDTO.get(i).getPastOrderedCount());
        pastOrderedProductListView_viewHolder.pastOrderedProrudcTotalPrice.setText(getPastOrderedProductListDTO.get(i).getPastOrderedTotalPrice());

    }

    @Override
    public int getItemCount() {
        return getPastOrderedProductListDTO.size();
    }

    class PastOrderedProductListView_ViewHolder extends RecyclerView.ViewHolder{

        TextView pastOrderedProrudctName;
        TextView pastOrderedProrudcCount;
        TextView pastOrderedProrudcTotalPrice;

        public PastOrderedProductListView_ViewHolder(@NonNull View itemView) {
            super(itemView);
            pastOrderedProrudctName=itemView.findViewById(R.id.p_ProductName);
            pastOrderedProrudcCount=itemView.findViewById(R.id.p_Count);
            pastOrderedProrudcTotalPrice=itemView.findViewById(R.id.p_totalPrice);

        }
    }
}
