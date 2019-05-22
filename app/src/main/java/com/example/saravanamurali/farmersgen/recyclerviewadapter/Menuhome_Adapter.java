package com.example.saravanamurali.farmersgen.recyclerviewadapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.models.HomeProductDTO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ravi on 16/11/17.
 */

public class Menuhome_Adapter extends RecyclerView.Adapter<Menuhome_Adapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<HomeProductDTO> contactList;
    private List<HomeProductDTO> contactListFiltered;
    private ContactsAdapterListener listener;
    private Menuhome_Adapter.OnItemClickListener mListener;

    public Menuhome_Adapter(Context context, List<HomeProductDTO> contactList, ContactsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }

    //contactListFiltered from DB

    public void setData(List<HomeProductDTO> hoomeproduct) {
        this.contactListFiltered = hoomeproduct;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(Menuhome_Adapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_menu_home_products, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final HomeProductDTO contact = contactListFiltered.get(position);

        holder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));

        String brand_Id = contactListFiltered.get(position).getBrandId();
        System.out.println("This is brand id of brand" + brand_Id);
        holder.mtName.setText(contactListFiltered.get(position).getProductName());
        holder.mDescription.setText(contactListFiltered.get(position).getProductDesc());
        //holder.mMinOrder.setText(contactListFiltered.get(position).getProductMinOrder());
        holder.mRating.setText(contactListFiltered.get(position).getProductRating());
        Picasso.with(context).load(contactListFiltered.get(position).getProductImage()).into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                } else {
                    List<HomeProductDTO> filteredList = new ArrayList<>();
                    for (HomeProductDTO row : contactList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getProductName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<HomeProductDTO>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    public interface ContactsAdapterListener {
        void onContactSelected(HomeProductDTO contact);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView mImage;
        TextView mtName;
        TextView mDescription;
        TextView mMinOrder;
        TextView mRating;
        LinearLayout container;

        public MyViewHolder(View view) {
            super(view);

            mImage = view.findViewById(R.id.productImage);
            mtName = view.findViewById(R.id.productName);
            mDescription = view.findViewById(R.id.productDescription);
            mMinOrder = view.findViewById(R.id.minOrderProduct);
            mRating = view.findViewById(R.id.ratingProduct);

            Typeface roboto=Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Medium.ttf");
            mtName.setTypeface(roboto);



            container = view.findViewById(R.id.animationContainer);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    if (mListener != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);

                        }

                    }
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));


                  /*  Intent productListIntent = new Intent(context, Product_List_Activity.class);
                    HomeProductDTO clickedBrand = contactListFiltered.get(position);


                    productListIntent.putExtra("CURRENTUSER", currentUserId);

                    productListIntent.putExtra("BRAND_ID", clickedBrand.getBrandId());
                    productListIntent.putExtra("BRAND_NAME", clickedBrand.getProductName());
                    productListIntent.putExtra("BRAND_RATING", clickedBrand.getProductRating());
                    productListIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(productListIntent);*/
                }
            });
        }
    }
}
