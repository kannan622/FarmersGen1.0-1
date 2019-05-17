package com.example.saravanamurali.farmersgen.fragment;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import com.crashlytics.android.Crashlytics;
import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseHomeBrandDTO;
import com.example.saravanamurali.farmersgen.models.HomeProductDTO;
import com.example.saravanamurali.farmersgen.recyclerviewadapter.MenuSearchAdapter;
import com.example.saravanamurali.farmersgen.recyclerviewadapter.Menuhome_Adapter;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientForBrand;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MenuSearchFragment extends Fragment implements MenuSearchAdapter.ContactsAdapterSearchListener {

    RecyclerView recyclerViewSearch;
    MenuSearchAdapter menuSearchAdapter;
    List<HomeProductDTO> homeSearchList;
    LinearLayoutManager linearLayoutManagerSearch;

    Toolbar toolbarSearch;
    private SearchView searchView;

    public MenuSearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu_search, container, false);

        //Fabric
        Fabric.with(getActivity(), new Crashlytics());

        toolbarSearch = (Toolbar) view.findViewById(R.id.toolBarForSearch);

        AppCompatActivity activitySearch = (AppCompatActivity) getActivity();
        activitySearch.setSupportActionBar(toolbarSearch);

        activitySearch.getSupportActionBar().setHomeButtonEnabled(true);
        setHasOptionsMenu(true);


        recyclerViewSearch = (RecyclerView) view.findViewById(R.id.recyclerViewSearch);
        recyclerViewSearch.setHasFixedSize(true);
        linearLayoutManagerSearch=new LinearLayoutManager(this.getActivity());
        recyclerViewSearch.setLayoutManager(linearLayoutManagerSearch);




        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        loadProductToSearchDisplay();

        homeSearchList = new ArrayList<HomeProductDTO>();

        menuSearchAdapter = new MenuSearchAdapter(getActivity(), homeSearchList, this);

        recyclerViewSearch.setAdapter(menuSearchAdapter);




    }

    private void loadProductToSearchDisplay() {

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

                if(csprogress.isShowing()){
                    csprogress.dismiss();
                }

                JSONResponseHomeBrandDTO jsonResponse = response.body();

                List<HomeProductDTO> homeProductDTOListSearch = jsonResponse.getRecords();

                menuSearchAdapter.setDataSearchChanged(homeProductDTOListSearch);



            }

            @Override
            public void onFailure(Call<JSONResponseHomeBrandDTO> call, Throwable t) {

            }
        });



    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);


        getActivity().getMenuInflater().inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.menu_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                menuSearchAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                menuSearchAdapter.getFilter().filter(s);
                return false;
            }
        });

    }



    @Override
    public void onSearchItemSelected(HomeProductDTO search) {

        Intent productListIntent = new Intent(this.getActivity(), Product_List_Activity.class);

        //productListIntent.putExtra("CURRENTUSER", currentUserId);

        productListIntent.putExtra("BRAND_ID", search.getBrandId());
        productListIntent.putExtra("BRAND_NAME", search.getProductName());
        productListIntent.putExtra("BRAND_RATING", search.getProductRating());
        productListIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(productListIntent);


    }
}
