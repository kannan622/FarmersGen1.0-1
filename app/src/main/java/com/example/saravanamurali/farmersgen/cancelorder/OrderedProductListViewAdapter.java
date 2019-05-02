package com.example.saravanamurali.farmersgen.cancelorder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonResponseToViewOrderedProductListDTO;

import java.util.List;

public class OrderedProductListViewAdapter extends RecyclerView.Adapter<OrderedProductListViewAdapter.OrderedProductListViewAdapterViewHolder> {

    Context orderProductMContext;
    List<JsonResponseToViewOrderedProductListDTO> getOrderedProductDetails;

    public OrderedProductListViewAdapter(Context orderProductMContext, List<JsonResponseToViewOrderedProductListDTO> getOrderedProductDetails) {
        this.orderProductMContext = orderProductMContext;
        this.getOrderedProductDetails = getOrderedProductDetails;
    }

    public void setOrderedPrdductList(List<JsonResponseToViewOrderedProductListDTO> getOrderedProductDetails){
        this.getOrderedProductDetails=getOrderedProductDetails;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderedProductListViewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(orderProductMContext);
        View view=inflater.inflate(R.layout.orderedproductlist_viewadapter,viewGroup,false);
        OrderedProductListViewAdapterViewHolder orderProductListViewHolder=new OrderedProductListViewAdapterViewHolder(view);

        return orderProductListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderedProductListViewAdapterViewHolder orderedProductListViewAdapterViewHolder, int i) {

        orderedProductListViewAdapterViewHolder.orderedProductList_Name.setText(getOrderedProductDetails.get(i).getOrderedProducName());
        orderedProductListViewAdapterViewHolder.orderedProductList_Count.setText(getOrderedProductDetails.get(i).getOrderedCount());
        orderedProductListViewAdapterViewHolder.orderedProductList_TotalPrice.setText(getOrderedProductDetails.get(i).getOrderedTotalPrice());

    }

    @Override
    public int getItemCount() {
        return getOrderedProductDetails.size();
    }

    public class OrderedProductListViewAdapterViewHolder extends RecyclerView.ViewHolder{

        TextView orderedProductList_Name;
        TextView orderedProductList_Count;
        TextView orderedProductList_TotalPrice;

        public OrderedProductListViewAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            orderedProductList_Name=(TextView)itemView.findViewById(R.id.o_ProductName);
            orderedProductList_Count=(TextView)itemView.findViewById(R.id.o_Count);
            orderedProductList_TotalPrice=(TextView)itemView.findViewById(R.id.o_totalPrice);
        }
    }
    }
