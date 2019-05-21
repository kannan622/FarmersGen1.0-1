package com.example.saravanamurali.farmersgen.pastorder;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseToGetPastOrderDTO;
import com.example.saravanamurali.farmersgen.util.Network_config;

import java.util.List;

public class PastOrderListAdapter extends RecyclerView.Adapter<PastOrderListAdapter.PastOrderListAdapterViewHolder> {

    PastOrderIdInterface pastOrderIdInterface;

    Context mPastOrderContext;
    Dialog dialog;
    List<JSONResponseToGetPastOrderDTO> jsonResponseToGetPastOrderDTOList;

    public PastOrderListAdapter(Context mPastOrderContext, List<JSONResponseToGetPastOrderDTO> jsonResponseToGetPastOrderDTOList) {
        this.mPastOrderContext = mPastOrderContext;
        this.jsonResponseToGetPastOrderDTOList = jsonResponseToGetPastOrderDTOList;
    }

    public void setPastOrderList(List<JSONResponseToGetPastOrderDTO> jsonResponseToGetPastOrderDTOList) {
        this.jsonResponseToGetPastOrderDTOList = jsonResponseToGetPastOrderDTOList;
        notifyDataSetChanged();

    }

    public interface PastOrderIdInterface {
        void pastOrderID(String pastOrderID);
    }

    public void setPastOrderIdInterface(PastOrderIdInterface pastOrderIdInterface) {
        this.pastOrderIdInterface = pastOrderIdInterface;
        notifyDataSetChanged();
    }

   /* void setPastOrderData(List<JSONResponseToGetPastOrderDTO> jsonResponseToGetPastOrderDTOList){
        this.jsonResponseToGetPastOrderDTOList=jsonResponseToGetPastOrderDTOList;
        notifyDataSetChanged();
    }*/


    @NonNull
    @Override
    public PastOrderListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(mPastOrderContext);
        View view = inflater.inflate(R.layout.pastorderlist_adapterview, viewGroup, false);

        dialog = new Dialog(mPastOrderContext);

        PastOrderListAdapterViewHolder pastOrderListAdapterViewHolder = new PastOrderListAdapterViewHolder(view);

        return pastOrderListAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PastOrderListAdapterViewHolder pastOrderListAdapterViewHolder, int i) {

        pastOrderListAdapterViewHolder.pastOrderNumber.setText(jsonResponseToGetPastOrderDTOList.get(i).getPastOrderID());
        pastOrderListAdapterViewHolder.pastOrderDate.setText(jsonResponseToGetPastOrderDTOList.get(i).getPastOrderDate());
        pastOrderListAdapterViewHolder.pastOrderGrandTotal.setText(jsonResponseToGetPastOrderDTOList.get(i).getPastOrderGrandTotal());

    }

    @Override
    public int getItemCount() {
        return jsonResponseToGetPastOrderDTOList.size();
    }

    class PastOrderListAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView pastOrderNumber;
        TextView pastOrderDate;
        TextView pastOrderGrandTotal;

        public PastOrderListAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            pastOrderNumber = itemView.findViewById(R.id.pastOrderNoAdapterView);
            pastOrderDate = itemView.findViewById(R.id.pastOrderDateAdapterView);
            pastOrderGrandTotal = itemView.findViewById(R.id.pastOrderGrandTotalAdapterView);


            pastOrderNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Network_config.is_Network_Connected_flag(mPastOrderContext)) {
                        int getAdapterPastOrderNumber = getAdapterPosition();
                        JSONResponseToGetPastOrderDTO jsonResponseToGetPastOrderDTO = jsonResponseToGetPastOrderDTOList.get(getAdapterPastOrderNumber);

                        String pastOrderId = jsonResponseToGetPastOrderDTO.getPastOrderID();
                        pastOrderIdInterface.pastOrderID(pastOrderId);

                    } else {
                        Network_config.customAlert(dialog, mPastOrderContext, mPastOrderContext.getResources().getString(R.string.app_name),
                                mPastOrderContext.getResources().getString(R.string.connection_message));
                    }


                }
            });
        }
    }
}
