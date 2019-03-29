package com.example.saravanamurali.farmersgen.recyclerviewadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.fragment.CancelOrderActivity;
import com.example.saravanamurali.farmersgen.models.JSONResponseForCancelOrderDTO;
import com.example.saravanamurali.farmersgen.models.ViewCartDTO;

import java.util.List;

public class CancelOrderAdapter extends RecyclerView.Adapter<CancelOrderAdapter.CancelOrderViewHolder> {

    List<JSONResponseForCancelOrderDTO> cancelOrderDTO;
    Context cancelOrderContext;

    CancelOrderUsingOrderIDInterace cancelOrderUsingOrderID_Interface;

    ViewProductDetailUsingOrderIDInterface viewProductDetailUsingOrderIDInterface;

    //Cancel order using orderID
    public interface  CancelOrderUsingOrderIDInterace{

        void getCancelOrderUsingOrderID(String orderID);
    }

    public void setCancelOrderUsingOrderIDInterace( CancelOrderUsingOrderIDInterace cancelOrderUsingOrderID_Interface){
        this.cancelOrderUsingOrderID_Interface=cancelOrderUsingOrderID_Interface;
        notifyDataSetChanged();
    } //End Cancel order using orderID

    //View Product Detail using ORDERID
    public interface ViewProductDetailUsingOrderIDInterface{

        void getViewProductDetailUsingOrderID(String orderID);

    }

    public void setViewProductDetailUsingOrderIDInterface( ViewProductDetailUsingOrderIDInterface viewProductDetailUsingOrderIDInterface){
        this.viewProductDetailUsingOrderIDInterface=viewProductDetailUsingOrderIDInterface;
        notifyDataSetChanged();
    }

    public CancelOrderAdapter(Context cancelOrderActivity, List<JSONResponseForCancelOrderDTO> jsonResponseForCancelOrderDTOS) {
        this.cancelOrderContext=cancelOrderActivity;
        this.cancelOrderDTO=jsonResponseForCancelOrderDTOS;
    }

    public void setCancelOrderList(List<JSONResponseForCancelOrderDTO> jsonResponseForCancelOrderDTOS){
        this.cancelOrderDTO=jsonResponseForCancelOrderDTOS;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CancelOrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(cancelOrderContext);
        View view = layoutInflater.inflate(R.layout.cancel_order_adapterview, viewGroup, false);
        CancelOrderViewHolder cancelOrderViewHolder = new CancelOrderViewHolder(view);
        return cancelOrderViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CancelOrderViewHolder cancelOrderViewHolder, int i) {
        cancelOrderViewHolder.orderCancelNo.setText(cancelOrderDTO.get(i).getOrderId());
        cancelOrderViewHolder.orderCancelDate.setText(cancelOrderDTO.get(i).getDate());
        cancelOrderViewHolder.orderCancelGrandTotal.setText(cancelOrderDTO.get(i).getGrandTotal());

    }

    @Override
    public int getItemCount() {
        return cancelOrderDTO.size();
    }


    class CancelOrderViewHolder extends RecyclerView.ViewHolder {

        TextView orderCancelNo;
        TextView orderCancelDate;
        TextView orderCancelGrandTotal;
        ImageView orderCancelButton;

        public CancelOrderViewHolder(@NonNull View itemView) {
            super(itemView);

            orderCancelNo=(TextView)itemView.findViewById(R.id.orderNoAdapterView);
            orderCancelDate=(TextView)itemView.findViewById(R.id.orderDateAdapterView);
            orderCancelGrandTotal=(TextView)itemView.findViewById(R.id.orderGrandTotalAdapterView);
            orderCancelButton=(ImageView) itemView.findViewById(R.id.ordercancelAdapterView);

            //Cancel Order Button Pressed
            orderCancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int getCancelOrderAdapterPosition=getAdapterPosition();
                    JSONResponseForCancelOrderDTO  getJsonResponseForCancelOrderDTO  =cancelOrderDTO.get(getCancelOrderAdapterPosition);
                    String get_OrderID=getJsonResponseForCancelOrderDTO.getOrderId();

                    cancelOrderUsingOrderID_Interface.getCancelOrderUsingOrderID(get_OrderID);
                    System.out.println(get_OrderID);


                }
            });

            orderCancelNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int getOrderNumberAdapterPosition=getAdapterPosition();
                    JSONResponseForCancelOrderDTO getOrderedProductDetail=cancelOrderDTO.get(getOrderNumberAdapterPosition);
                    String getProductDetail=getOrderedProductDetail.getOrderId();

                    viewProductDetailUsingOrderIDInterface.getViewProductDetailUsingOrderID(getProductDetail);

                }
            });
        }
    }
}