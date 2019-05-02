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
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseMenuCartFragUpdateDTO;
import com.example.saravanamurali.farmersgen.models.MenuCartFragmentViewCartDTO;

import java.util.List;

public class MenuCartFragmentAdapter extends RecyclerView.Adapter<MenuCartFragmentAdapter.MenuCartFragmentViewHolder> {

    Context mCtx;
    List<MenuCartFragmentViewCartDTO> menuCartFragmentViewCartDTO;

    MenuCartFragmentUpdateInterface menuCartFragmentUpdateInterface;

    MenuCartFragmentDeleteInterface menuCartFragmentDeleteInterface;

    JSONResponseMenuCartFragUpdateDTO jsonResponseMenuCartFragUpdateDTO;

    public MenuCartFragmentAdapter(Context mCtx, List<MenuCartFragmentViewCartDTO> menuCartFragmentViewCartDTOList) {

        this.mCtx = mCtx;
        this.menuCartFragmentViewCartDTO = menuCartFragmentViewCartDTOList;

    }

    public void setTotalPriceToUpdateCart(JSONResponseMenuCartFragUpdateDTO jsonResponseMenuCartFragUpdateDTO) {
        this.jsonResponseMenuCartFragUpdateDTO = jsonResponseMenuCartFragUpdateDTO;

        for (MenuCartFragmentViewCartDTO cartFragmentViewCartDTO : menuCartFragmentViewCartDTO) {

            if (cartFragmentViewCartDTO.getmCart_Product_Code().equals(jsonResponseMenuCartFragUpdateDTO.getmCartUpdate_ProductCode())) {
                cartFragmentViewCartDTO.setmCart_Total_price(jsonResponseMenuCartFragUpdateDTO.getmCartUpdate_TotalPrice());
                break;
            }

        }

        notifyDataSetChanged();

    }

    public void setMenuCartFragmentDeleteInterface(MenuCartFragmentDeleteInterface menuCartFragmentDeleteInterface) {
        this.menuCartFragmentDeleteInterface = menuCartFragmentDeleteInterface;
        notifyDataSetChanged();

    }

    public void setMenuCartFragmentUpdateInterface(MenuCartFragmentUpdateInterface menuCartFragmentUpdateInterface) {

        this.menuCartFragmentUpdateInterface = menuCartFragmentUpdateInterface;
        notifyDataSetChanged();
    }

    public void setData(List<MenuCartFragmentViewCartDTO> menuCartFragmentViewCartDTO) {

        this.menuCartFragmentViewCartDTO = menuCartFragmentViewCartDTO;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MenuCartFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        View view = layoutInflater.inflate(R.layout.menucart_adapterview, viewGroup, false);
        MenuCartFragmentViewHolder menuCartFragmentViewHolder = new MenuCartFragmentViewHolder(view);

        return menuCartFragmentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuCartFragmentViewHolder menuCartFragmentViewHolder, int i) {

        menuCartFragmentViewHolder.homeCartProductName.setText(menuCartFragmentViewCartDTO.get(i).getmCart_Product_Name());

        menuCartFragmentViewHolder.homeCartCount.setText(menuCartFragmentViewCartDTO.get(i).getmCart_Count());

        menuCartFragmentViewHolder.homeCartPrice.setText("₹ " + menuCartFragmentViewCartDTO.get(i).getmCart_Price());

        int menuCartCount = 0;

        if (menuCartFragmentViewCartDTO.get(i).getmCart_Count() == null) {
            menuCartCount = 0;
        } else {
            menuCartCount = Integer.parseInt(menuCartFragmentViewCartDTO.get(i).getmCart_Count());
        }

        if (menuCartCount > 0) {

            menuCartFragmentViewHolder.homeCartCount.setText(menuCartFragmentViewCartDTO.get(i).getmCart_Count());

        } else {

            menuCartFragmentViewHolder.homeCartProductName.setVisibility(View.GONE);
            menuCartFragmentViewHolder.homeCartMinus.setVisibility(View.GONE);
            menuCartFragmentViewHolder.homeCartCount.setVisibility(View.GONE);
            menuCartFragmentViewHolder.homeCartPlus.setVisibility(View.GONE);
            menuCartFragmentViewHolder.homeCartPrice.setVisibility(View.GONE);

        }

    }

    @Override
    public int getItemCount() {

        return menuCartFragmentViewCartDTO.size();
    }

    public void removeItemAtMenuCart(int position) {
        menuCartFragmentViewCartDTO.remove(position);
        notifyItemRemoved(position);
    }

    public interface MenuCartFragmentDeleteInterface {

        void menuCartFragmentDelete(String productCode);

    }

    public interface MenuCartFragmentUpdateInterface {
        void menuCartFragementUpdate(int menuCartCount, String menuCartProductCode, String menuCartPrice);
    }

    class MenuCartFragmentViewHolder extends RecyclerView.ViewHolder {

        TextView homeCartProductName;
        ImageView homeCartMinus;
        TextView homeCartCount;
        ImageView homeCartPlus;
        TextView homeCartPrice;


        public MenuCartFragmentViewHolder(@NonNull View itemView) {
            super(itemView);

            homeCartProductName = itemView.findViewById(R.id.menuCartFragProductName);
            homeCartMinus = itemView.findViewById(R.id.menuCartFragDecCart);
            homeCartCount = itemView.findViewById(R.id.menuCartFragCount);
            homeCartPlus = itemView.findViewById(R.id.menuCartFragIncCart);
            homeCartPrice = itemView.findViewById(R.id.menuCartFragCartPrice);

            homeCartPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int homeCartAdapterPosition = getAdapterPosition();
                    MenuCartFragmentViewCartDTO menuCartFragmentDTO = menuCartFragmentViewCartDTO.get(homeCartAdapterPosition);

                    int menuCartCount = Integer.parseInt(menuCartFragmentDTO.getmCart_Count()) + 1;

                    String menuCartProdductCode = menuCartFragmentDTO.getmCart_Product_Code();
                    String menuCartPrice = menuCartFragmentDTO.getmCart_Price();
                    menuCartFragmentDTO.setmCart_Count(String.valueOf(menuCartCount));
                    notifyDataSetChanged();

                    menuCartFragmentUpdateInterface.menuCartFragementUpdate(menuCartCount, menuCartProdductCode, menuCartPrice);


                }
            });

           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int menuCartAdapterPosition = getAdapterPosition();

                    MenuCartFragmentViewCartDTO menuCartFragmentDTO = menuCartFragmentViewCartDTO.get(menuCartAdapterPosition);
                    int menuCartDecCount = Integer.parseInt(menuCartFragmentDTO.getmCart_Count()) - 1;
                    if (menuCartDecCount < 0) {
                        notifyItemRemoved(getAdapterPosition());

                        return;
                    }

                }
            });
*/
            homeCartMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int menuCartAdapterPosition = getAdapterPosition();
                    MenuCartFragmentViewCartDTO menuCartFragmentDTO = menuCartFragmentViewCartDTO.get(menuCartAdapterPosition);

                    int menuCartDecCount = Integer.parseInt(menuCartFragmentDTO.getmCart_Count()) - 1;

                    String menuCartProdductCode = menuCartFragmentDTO.getmCart_Product_Code();
                    String menuCartPrice = menuCartFragmentDTO.getmCart_Price();

                    menuCartFragmentDTO.setmCart_Count(String.valueOf(menuCartDecCount));
                    notifyDataSetChanged();

                    if (menuCartDecCount > 0) {
                        menuCartFragmentUpdateInterface.menuCartFragementUpdate(menuCartDecCount, menuCartProdductCode, menuCartPrice);
                        //notifyDataSetChanged();

                    } else if (menuCartDecCount == 0) {
                        removeItemAtMenuCart(menuCartAdapterPosition);
                        menuCartFragmentDeleteInterface.menuCartFragmentDelete(menuCartProdductCode);

                    } else if (menuCartDecCount < 0) {
                        notifyItemRemoved(menuCartAdapterPosition);
                        notifyDataSetChanged();

                        return;
                    }


                }
            });


        }
    }
}
