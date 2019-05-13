package com.example.saravanamurali.farmersgen.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseHomeBrandDTO;
import com.example.saravanamurali.farmersgen.models.HomeProductDTO;
import com.example.saravanamurali.farmersgen.recyclerviewadapter.MenuSearchAdapter;
import com.example.saravanamurali.farmersgen.recyclerviewadapter.Menuhome_Adapter;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientForBrand;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MenuSearchFragment extends Fragment implements MenuSearchAdapter.ContactsAdapterSearchListener {

    RecyclerView recyclerViewSearch;
    MenuSearchAdapter menuSearchAdapter;
    List<HomeProductDTO> homeSearchList;
    LinearLayoutManager linearLayoutManagerSearch;

    Toolbar toolbar;

    public MenuSearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu_search, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.toolBarForSearch);

        recyclerViewSearch = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerViewSearch.setHasFixedSize(true);
        linearLayoutManagerSearch=new LinearLayoutManager(this.getActivity());
        recyclerViewSearch.setLayoutManager(linearLayoutManagerSearch);

        homeSearchList = new ArrayList<HomeProductDTO>();

        menuSearchAdapter = new MenuSearchAdapter(getActivity(), homeSearchList, this);


        loadProductToSearchDisplay();



        return view;
    }

    private void loadProductToSearchDisplay() {

        ApiInterface api = APIClientForBrand.getApiInterfaceForBrand();

        Call<JSONResponseHomeBrandDTO> call = api.getAllBrands();

        call.enqueue(new Callback<JSONResponseHomeBrandDTO>() {
            @Override
            public void onResponse(Call<JSONResponseHomeBrandDTO> call, Response<JSONResponseHomeBrandDTO> response) {
                JSONResponseHomeBrandDTO jsonResponse = response.body();

                List<HomeProductDTO> homeProductDTOList = jsonResponse.getRecords();

                menuSearchAdapter.setDataSearchChanged(homeProductDTOList);



            }

            @Override
            public void onFailure(Call<JSONResponseHomeBrandDTO> call, Throwable t) {

            }
        });



    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        getActivity().getMenuInflater().inflate(R.menu.main, menu);

    }

    @Override
    public void onSearchItemSelected(HomeProductDTO search) {

    }
}
