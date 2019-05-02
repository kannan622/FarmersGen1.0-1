package com.example.saravanamurali.farmersgen.recyclerviewadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseUpdateCartDTO;
import com.example.saravanamurali.farmersgen.models.ViewCartDTO;

import java.util.List;

public class ViewCartAdapter extends RecyclerView.Adapter<ViewCartAdapter.ViewCartHolder> {

    List<ViewCartDTO> viewCartProductListDTO;
    Context viewCartContext;

    JSONResponseUpdateCartDTO jsonResponseUpdateCartDTO;


    ViewCartUpdateInterface viewCartUpdateInterface;

    ViewCartDeleteInterface viewCartDeleteInterface;

    int totalAmount;


    public ViewCartAdapter(Context viewCartContext, List<ViewCartDTO> viewCartProductListDTO) {
        this.viewCartContext = viewCartContext;
        this.viewCartProductListDTO = viewCartProductListDTO;

    }

    public void setViewCartUpdateInterface(ViewCartUpdateInterface viewCartUpdateInterface) {
        this.viewCartUpdateInterface = viewCartUpdateInterface;
        notifyDataSetChanged();

    }//End Update ViewCart Count

    public void setViewCartDeleteInterface(ViewCartDeleteInterface viewCartDeleteInterface) {
        this.viewCartDeleteInterface = viewCartDeleteInterface;
        notifyDataSetChanged();

    } //End Delete ViewCart Count

    public void setUpdateTotalPrice(JSONResponseUpdateCartDTO jsonResponseUpdateCartDTO) {

        this.jsonResponseUpdateCartDTO = jsonResponseUpdateCartDTO;
        for (ViewCartDTO viewCartDTO : viewCartProductListDTO) {
            if (viewCartDTO.getProduct_Code().equals(jsonResponseUpdateCartDTO.getUpdateProductCode())) {
                viewCartDTO.setTotal_price(jsonResponseUpdateCartDTO.getUpdateTotalPrice());
                break;
            }

        }

        notifyDataSetChanged();

    }

    //Display List of Ordered items At ViewCart
    public void setData(List<ViewCartDTO> viewCartSetDataListDTO) {
        this.viewCartProductListDTO = viewCartSetDataListDTO;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewCartHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewCartContext);
        View view = layoutInflater.inflate(R.layout.viewcart_adapterview, viewGroup, false);
        ViewCartHolder viewCartHolder = new ViewCartHolder(view);

        return viewCartHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewCartHolder holder, int i) {

        holder.cartProductName.setText(viewCartProductListDTO.get(i).getProduct_Name());
        holder.cartCount.setText("" + viewCartProductListDTO.get(i).getCount());
        holder.totalPrice.setText("₹ " + viewCartProductListDTO.get(i).getTotal_price());

        // holder.cartPrice.setText("" + viewCartProductListDTO.get(i).getPrice());


        //ADD,DEC Button Pressed in View Cart Activity

        int finalViewCartCount = 0;
        if (viewCartProductListDTO.get(i).getCount() == null) {
            finalViewCartCount = 0;
        } else {
            finalViewCartCount = Integer.parseInt(viewCartProductListDTO.get(i).getCount());
        }


        if (finalViewCartCount > 0) {
            holder.cartCount.setText("" + viewCartProductListDTO.get(i).getCount());
        } else {

           /* viewCartProductListDTO.remove(i);
            notifyDataSetChanged();
            holder.cartProductName.setVisibility(View.GONE);
            holder.cartCount.setVisibility(View.GONE);
            holder.incCart.setVisibility(View.GONE);
            holder.decCart.setVisibility(View.GONE);
            holder.totalPrice.setVisibility(View.GONE);
            holder.rel.setVisibility(View.GONE);*/

        } //End of ADD,DEC Button Pressed in View Cart Activity


    }

    @Override
    public int getItemCount() {
        return viewCartProductListDTO.size();
    }

    private void getTotalPrice() {


    }

    public void removeItem(int position) {
        viewCartProductListDTO.remove(position);
        notifyItemRemoved(position);
    }

    public void removeItem1(int position) {

        Log.d("POS", "" + position);
        ViewCartDTO viewCartDTODec;

        if (position == 0) {

            viewCartDTODec = viewCartProductListDTO.get(position);
            viewCartProductListDTO.remove(position);


        } else {

            viewCartDTODec = viewCartProductListDTO.get(position);
            viewCartProductListDTO.remove(position);


        }
        String viewCartDecProductCode = viewCartDTODec.getProduct_Code();

        viewCartDeleteInterface.viewCartDeleteInterface(viewCartDecProductCode);

        notifyItemRemoved(position);


    }

    //Update ViewCart Count
    public interface ViewCartUpdateInterface {
        public void viewCartUpdateInterface(int viewCartCount, String viewCartProductCode, String viewCart_Price);

    }

    //Delete ViewCart Count
    public interface ViewCartDeleteInterface {
        public void viewCartDeleteInterface(String viewCartDecProductCode);
    }

    public class ViewCartHolder extends RecyclerView.ViewHolder {

        public RelativeLayout viewBackground, viewForeground;
        TextView cartProductName;
        ImageView decCart;
        ImageView incCart;
        TextView cartCount;
        TextView totalPrice;
        RelativeLayout rel;

        public ViewCartHolder(@NonNull final View itemView) {
            super(itemView);

            cartProductName = itemView.findViewById(R.id.cartProductName);
            incCart = itemView.findViewById(R.id.incCart);
            decCart = itemView.findViewById(R.id.decCart);
            cartCount = itemView.findViewById(R.id.cartCount);
            totalPrice = itemView.findViewById(R.id.cartPrice);
            rel = itemView.findViewById(R.id.rell);

            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);

            //Updating(Adding) Count at View Cart
            incCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int viewCartAdapterPosition = getAdapterPosition();

                    ViewCartDTO viewCartDTO = viewCartProductListDTO.get(viewCartAdapterPosition);

                    int viewCartCount = Integer.parseInt(viewCartDTO.getCount()) + 1;

                    String viewCart_productCode = viewCartDTO.getProduct_Code();
                    String viewCart_Price = viewCartDTO.getPrice();

                   /* int total_price = viewCartCount * Integer.parseInt(viewCart_Price);

                    String inc_total_price = String.valueOf(total_price);

                    System.out.println("View Cart Count" + viewCartCount);

                    System.out.println("View Cart Product Code" + viewCart_productCoe);
*/
                    viewCartDTO.setCount(String.valueOf(viewCartCount));
                    notifyDataSetChanged();

                    viewCartUpdateInterface.viewCartUpdateInterface(viewCartCount, viewCart_productCode, viewCart_Price);


                    //jsonResponseUpdateCartDTO=new JSONResponseUpdateCartDTO();
                    //viewCartDTO.setTotal_price(jsonResponseUpdateCartDTO.getUpdateTotalPrice());


                }
            }); // End of increment count in ViewCart


            decCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int decrementAdapterPosition = getAdapterPosition();
                    ViewCartDTO viewCartDTODec = viewCartProductListDTO.get(getAdapterPosition());

                    int viewCartDecCount = Integer.parseInt(viewCartDTODec.getCount());

                    viewCartDecCount = viewCartDecCount - 1;


                    Log.d("countt", "" + viewCartDecCount);


                    String viewCartDecProductCode = viewCartDTODec.getProduct_Code();
                    String viewCartDecPrice = viewCartDTODec.getPrice();


                    if (viewCartDecCount > 0) {

                        /*int total_price = viewCartDecCount * Integer.parseInt(viewCartDecPrice);

                        String dec_total_price = String.valueOf(total_price);
*/
                        viewCartUpdateInterface.viewCartUpdateInterface(viewCartDecCount, viewCartDecProductCode, viewCartDecPrice);
                        notifyDataSetChanged();

                    } else if (viewCartDecCount == 0) {

                        removeItem(decrementAdapterPosition);

                        // loadViewCartProductList();
                        viewCartDeleteInterface.viewCartDeleteInterface(viewCartDecProductCode);
                        // notifyItemRemoved(decrementAdapterPosition);
                        // notifyDataSetChanged();


                    } else if (viewCartDecCount < 0) {

                        notifyItemRemoved(decrementAdapterPosition);
                        notifyDataSetChanged();
                    }

                    viewCartDTODec.setCount(String.valueOf(viewCartDecCount));
                    // viewCartDTODec.setTotal_price(jsonResponseUpdateCartDTO.getUpdateTotalPrice());

                    notifyDataSetChanged();
                }
            });

        }
    }


}
