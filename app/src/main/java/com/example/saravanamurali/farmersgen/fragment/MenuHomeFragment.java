package com.example.saravanamurali.farmersgen.fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.models.BannerDTO;
import com.example.saravanamurali.farmersgen.models.HomeProductDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseHomeBrandDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonResponseForBannerDTO;
import com.example.saravanamurali.farmersgen.recyclerviewadapter.MenuBannerAdapter;
import com.example.saravanamurali.farmersgen.recyclerviewadapter.Menuhome_Adapter;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientForBannerImages;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientForBrand;
import com.example.saravanamurali.farmersgen.util.Network_config;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MenuHomeFragment extends Fragment implements Menuhome_Adapter.OnItemClickListener, SearchView.OnQueryTextListener, Menuhome_Adapter.ContactsAdapterListener {

    RecyclerView recyclerView;
    Menuhome_Adapter menuHomeFragmentAdapter;
    List<HomeProductDTO> homeProductDTOSList;
    LinearLayoutManager linearLayoutManager;
    String brandId;

    ProgressBar progressBar;

    Toolbar toolbar;

    String currentUserId;
    Dialog dialog;
    private SearchView searchView;

    //Banner Horizontal
    RecyclerView recyclerViewHorizontal;
    MenuBannerAdapter menuBannerAdapter;
    List<BannerDTO> menuBannerDTOList;

    //Pagination
    private boolean isLoading=true;
    private int pastVisibleItems,visibleItemCount,totalItemCount,previous_total=0;
    private int view_threshold=10;

    private int page_number=1;
    private int item_count=10;



    /* @SuppressLint("ValidFragment")
     public MenuHomeFragment(String currentUserId){
     this.currentUserId=currentUserId;
    }*/

    public MenuHomeFragment() {

    }


    //EditText searchEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu_home, container, false);

        dialog = new Dialog(getActivity());

        toolbar = (Toolbar) view.findViewById(R.id.toolBar);

        System.out.println("Current User" + currentUserId);

       /* ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        //((AppCompatActivity)getContext()).getSupportActionBar().setHomeButtonEnabled(true);
*/
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        // activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        activity.getSupportActionBar().setHomeButtonEnabled(true);
        setHasOptionsMenu(true);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        progressBar=(ProgressBar)view.findViewById(R.id.progressBar);

        //Banner Horizontal
        recyclerViewHorizontal=(RecyclerView)view.findViewById(R.id.recyclerViewHorizonal);
        recyclerViewHorizontal.setHasFixedSize(true);
        recyclerViewHorizontal.setLayoutManager(new LinearLayoutManager(this.getActivity(),LinearLayoutManager.HORIZONTAL,true));

        if (Network_config.is_Network_Connected_flag(getActivity())) {


            loadRetrofitforProductDisplay();
            

            homeProductDTOSList = new ArrayList<HomeProductDTO>();

            menuHomeFragmentAdapter = new Menuhome_Adapter(getActivity(), homeProductDTOSList, this);
            recyclerView.setAdapter(menuHomeFragmentAdapter);
            menuHomeFragmentAdapter.setOnItemClickListener(MenuHomeFragment.this);

            //Banner Images

            loadBannerImages();

            menuBannerDTOList=new ArrayList<BannerDTO>();
            menuBannerAdapter=new MenuBannerAdapter(this.getActivity(),menuBannerDTOList);
            recyclerViewHorizontal.setAdapter(menuBannerAdapter);

           // menuBannerAdapter.setOnBannerImageClick(MenuHomeFragment.this);





        } else {
            Network_config.customAlert(dialog, getActivity(), getResources().getString(R.string.app_name),
                    getResources().getString(R.string.connection_message));
        }

        //System.out.println(" Hi I am here MenuHomeFragment");


        //productListAdapter.setBackToShowExistingCount(MenuHomeFragment.this);

        // doSearch(searchWord);


        return view;
    }


    //Display All Banner Images
    private void loadBannerImages() {



        ApiInterface api=APIClientForBannerImages.getApiInterfaceForBannerImages();
        Call<JsonResponseForBannerDTO> call=api.getAllBannerImages();

        call.enqueue(new Callback<JsonResponseForBannerDTO>() {
            @Override
            public void onResponse(Call<JsonResponseForBannerDTO> call, Response<JsonResponseForBannerDTO> response) {
                if(response.isSuccessful()){

                    JsonResponseForBannerDTO jsonBannerResponse = response.body();

                    List<BannerDTO> bannerList = jsonBannerResponse.getRecords();

                    menuBannerAdapter.setBannerImages(bannerList);
                    menuBannerAdapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onFailure(Call<JsonResponseForBannerDTO> call, Throwable t) {

            }
        });


    }



    //Display All Brands
    private void loadRetrofitforProductDisplay() {
        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(getActivity());
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);

        ApiInterface api = APIClientForBrand.getApiInterfaceForBrand();

        Call<JSONResponseHomeBrandDTO> call = api.getAllBrands();

        call.enqueue(new Callback<JSONResponseHomeBrandDTO>() {
            @Override
            public void onResponse(Call<JSONResponseHomeBrandDTO> call, Response<JSONResponseHomeBrandDTO> response) {

                if (response.isSuccessful()) {
                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }


                    JSONResponseHomeBrandDTO jsonResponse = response.body();

                List<HomeProductDTO> homeProductDTOList = jsonResponse.getRecords();

                for (int i = 0; i < homeProductDTOList.size(); i++) {

                    brandId = homeProductDTOList.get(i).getBrandId();
                    String brandImage = homeProductDTOList.get(i).getProductImage();
                    String brandName = homeProductDTOList.get(i).getProductName();
                    String brandDesc = homeProductDTOList.get(i).getProductDesc();
                    String brandRating = homeProductDTOList.get(i).getProductRating();
                    String minOrder = homeProductDTOList.get(i).getProductMinOrder();

                    HomeProductDTO homeProductDTO = new HomeProductDTO(brandId, brandImage, brandName, brandDesc, brandRating, minOrder);
                    homeProductDTOSList.add(homeProductDTO);

                }


                    menuHomeFragmentAdapter.setData(homeProductDTOList);
                    //  Toast.makeText(getActivity(), "Sucesss Running", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JSONResponseHomeBrandDTO> call, Throwable t) {
                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

         recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
             @Override
             public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                 super.onScrolled(recyclerView, dx, dy);
                 visibleItemCount=linearLayoutManager.getChildCount();
                 totalItemCount=linearLayoutManager.getItemCount();
                 pastVisibleItems=linearLayoutManager.findFirstVisibleItemPosition();

                 if(dy>0){
                     if(isLoading){
                         if(totalItemCount>previous_total){
                             isLoading=false;
                             previous_total=totalItemCount;
                         }
                     }
                     if(!isLoading && (totalItemCount-visibleItemCount)<=(pastVisibleItems+view_threshold)){

                         page_number++;
                         loadRetrofitforProductDisplay();
                         isLoading=true;

                     }


                     }


             }
         });

    }




    @Override
    public void onItemClick(int position) {

        if (Network_config.is_Network_Connected_flag(getActivity())) {
            Intent productListIntent = new Intent(this.getActivity(), Product_List_Activity.class);
            HomeProductDTO clickedBrand = homeProductDTOSList.get(position);


            productListIntent.putExtra("CURRENTUSER", currentUserId);

            productListIntent.putExtra("BRAND_ID", clickedBrand.getBrandId());
            productListIntent.putExtra("BRAND_NAME", clickedBrand.getProductName());
            productListIntent.putExtra("BRAND_RATING", clickedBrand.getProductRating());
            productListIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(productListIntent);
        } else {
            Network_config.customAlert(dialog, getActivity(), getResources().getString(R.string.app_name),
                    getResources().getString(R.string.connection_message));
        }


    }

   /* @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_menu,menu);

        }*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

      /*  inflater.inflate(R.menu.menu_items, menu);

        MenuItem menuItem = menu.findItem(R.id.action_searchm);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(MenuHomeFragment.this);*/



      /*  getActivity().getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService( Context.SEARCH_SERVICE );
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_item_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);*/


        getActivity().getMenuInflater().inflate(R.menu.main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                menuHomeFragmentAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                menuHomeFragmentAdapter.getFilter().filter(query);
                return false;
            }
        });


    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        menuHomeFragmentAdapter.getFilter().filter(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        menuHomeFragmentAdapter.getFilter().filter(s);
        return false;

        // menuHomeFragmentAdapter.updateList(filteredList);


    }


    @Override
    public void onContactSelected(HomeProductDTO contact) {
        Intent productListIntent = new Intent(this.getActivity(), Product_List_Activity.class);


        productListIntent.putExtra("CURRENTUSER", currentUserId);

        productListIntent.putExtra("BRAND_ID", contact.getBrandId());
        productListIntent.putExtra("BRAND_NAME", contact.getProductName());
        productListIntent.putExtra("BRAND_RATING", contact.getProductRating());
        productListIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(productListIntent);

        //  Toast.makeText(getActivity(), "Selected: " + contact.getProductName() + ", " + contact.getProductDesc(), Toast.LENGTH_LONG).show();

    }


    /*//Banner Images Clicked
    @Override
    public void bannerImageClick(String brandID, String brandName, String brandRating) {

        Intent productListIntent = new Intent(this.getActivity(), Product_List_Activity.class);


        productListIntent.putExtra("CURRENTUSER", currentUserId);

        productListIntent.putExtra("BRAND_ID", brandID);
        productListIntent.putExtra("BRAND_NAME", brandName);
        productListIntent.putExtra("BRAND_RATING", brandRating);
        startActivity(productListIntent);



    }*/


}
