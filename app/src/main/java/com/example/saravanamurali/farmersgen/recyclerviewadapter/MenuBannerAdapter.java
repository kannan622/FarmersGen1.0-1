package com.example.saravanamurali.farmersgen.recyclerviewadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.models.HomeProductDTO;
import com.example.saravanamurali.farmersgen.models.MenuBannerDTO;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MenuBannerAdapter extends RecyclerView.Adapter<MenuBannerAdapter.MenuBAnner_ViewHolder> {

    private Context m_context;
    private List<MenuBannerDTO> menuBannerDTOList;

    public MenuBannerAdapter(Context context, List<MenuBannerDTO> menuBannerDTOList) {
        this.m_context = context;
        this.menuBannerDTOList = menuBannerDTOList;
    }

    public void setBannerImages(List<MenuBannerDTO> menuBannerDTOList){

        this.menuBannerDTOList=menuBannerDTOList;
        notifyDataSetChanged();

    }


    @NonNull
    @Override
    public MenuBAnner_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater=LayoutInflater.from(m_context);
        View view=layoutInflater.inflate(R.layout.menubanner_adapterview,viewGroup,false);

        MenuBAnner_ViewHolder menuBAnner_viewHolder=new MenuBAnner_ViewHolder(view);
        return menuBAnner_viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuBAnner_ViewHolder menuBAnner_viewHolder, int i) {

       Picasso.with(m_context).load(menuBannerDTOList.get(i).getBannerImages()).into(menuBAnner_viewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return menuBannerDTOList.size();
    }

    public class MenuBAnner_ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public MenuBAnner_ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=(ImageView)itemView.findViewById(R.id.banner_Images);
        }
    }


}
