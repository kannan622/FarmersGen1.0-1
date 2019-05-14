package com.example.saravanamurali.farmersgen.recyclerviewadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.fragment.MenuSearchFragment;
import com.example.saravanamurali.farmersgen.models.HomeProductDTO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MenuSearchAdapter extends RecyclerView.Adapter<MenuSearchAdapter.SearchViewHolder> implements Filterable {


    Context context;
    private List<HomeProductDTO> searchList;
    private List<HomeProductDTO> searchListFilted;

    private ContactsAdapterSearchListener searchListener;

    public MenuSearchAdapter(Context context, List<HomeProductDTO> searchList, ContactsAdapterSearchListener searchListener) {
        this.context = context;
        this.searchListFilted=searchList;
        this.searchList = searchList;
        this.searchListener = searchListener;
    }

    public interface ContactsAdapterSearchListener {
        void onSearchItemSelected(HomeProductDTO search);
    }

    public void setDataSearchChanged(List<HomeProductDTO> searchList) {

        this.searchList = searchList;
        notifyDataSetChanged();

    }


    //searchList comes from DB





    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_menu_search_product, viewGroup, false);

        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder, int i) {

        searchViewHolder.mtNameSearch.setText(searchList.get(i).getProductName());
        searchViewHolder.mDescriptionSearch.setText(searchList.get(i).getProductDesc());
        searchViewHolder.mRatingSearch.setText(searchList.get(i).getProductRating());
        Picasso.with(context).load(searchList.get(i).getProductImage()).into(searchViewHolder.mImageSearch);

    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    searchList = searchListFilted;
                } else {
                    List<HomeProductDTO> searchFilteredList = new ArrayList<>();
                    for (HomeProductDTO row : searchListFilted) {

                        if (row.getProductName().toLowerCase().contains(charString.toLowerCase())) {
                            searchFilteredList.add(row);
                        }
                    }

                    searchList = searchFilteredList;


                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = searchList;
                return filterResults;


            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                searchList = (ArrayList<HomeProductDTO>) results.values;
                notifyDataSetChanged();

            }
        };
    }



    class SearchViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageSearch;
        TextView mtNameSearch;
        TextView mDescriptionSearch;
        TextView mMinOrderSearch;
        TextView mRatingSearch;


        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageSearch = itemView.findViewById(R.id.productImageSearch);
            mtNameSearch = itemView.findViewById(R.id.productNameSearch);
            mDescriptionSearch = itemView.findViewById(R.id.productDescriptionSearch);
            mMinOrderSearch = itemView.findViewById(R.id.minOrderProductSearch);
            mRatingSearch = itemView.findViewById(R.id.ratingProductSearch);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int searchPosition=getAdapterPosition();
                    HomeProductDTO searchDTO=searchList.get(searchPosition);

                    searchListener.onSearchItemSelected(searchDTO);
                }
            });




        }
    }
}
