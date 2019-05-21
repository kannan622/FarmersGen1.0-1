package com.example.saravanamurali.farmersgen.favourite;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.fragment.Product_List_Activity;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonResponseForFavBrandsDTO;
import com.example.saravanamurali.farmersgen.models.CurrentUserDTO;
import com.example.saravanamurali.farmersgen.models.FavBrandDTO;
import com.example.saravanamurali.farmersgen.models.HomeProductDTO;
import com.example.saravanamurali.farmersgen.recyclerviewadapter.FavouriteListAdapter;
import com.example.saravanamurali.farmersgen.retrofitclient.ApiClientToGetFavBrands;
import com.example.saravanamurali.farmersgen.util.Network_config;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteListActivity extends AppCompatActivity implements FavouriteListAdapter.OnFavClickListener {

    RecyclerView favrecyclerView;
    FavouriteListAdapter favouriteListAdapter;
    List<FavBrandDTO> favBrandDTOS;

    String curUserForFav;

    Toolbar toolbarFav;

    Context mContext;
    Dialog dialog;

    CollapsingToolbarLayout collapsingToolbarLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_list);

        mContext=FavouriteListActivity.this;
        dialog=new Dialog(mContext);

        favrecyclerView = (RecyclerView) findViewById(R.id.recyclerViewFavourite);
        favrecyclerView.setHasFixedSize(true);
        favrecyclerView.setLayoutManager(new LinearLayoutManager(FavouriteListActivity.this));

        toolbarFav = (Toolbar) findViewById(R.id.toolbarFavourite);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_favourite);

        setSupportActionBar(toolbarFav);
        /*if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }*/



        favBrandDTOS = new ArrayList<FavBrandDTO>();

        if (Network_config.is_Network_Connected_flag(mContext)) {

            loadFavouriteBrands();

        }

        else {
            Network_config.customAlert(dialog, mContext, getResources().getString(R.string.app_name),
                    getResources().getString(R.string.connection_message));
        }

        favouriteListAdapter = new FavouriteListAdapter(FavouriteListActivity.this, favBrandDTOS);
        favrecyclerView.setAdapter(favouriteListAdapter);
        favouriteListAdapter.setOnFavClickListener(FavouriteListActivity.this);

        ToolbarCollapsingAppearanceWhenCollapsed();
        ToolbarCollapsingAppearanceWhenExpanded();
    }

    private void ToolbarCollapsingAppearanceWhenExpanded() {


        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedtoolbar);
       /* toolbarFav.setTitle(" ");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
*/
    }

    private void ToolbarCollapsingAppearanceWhenCollapsed() {

        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedtoolbar);
        /*getSupportActionBar().setTitle("Favouritee");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        */
    }

    /*private void ToolbarCollapsingAppearance() {
        collapsingToolbarLayout.setStatusBarScrimColor(Color.parseColor("#FFFFFF"));
    }*/


    private void loadFavouriteBrands() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(FavouriteListActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        //Getting Current User
        SharedPreferences getCurrentUser = getSharedPreferences("CURRENT_USER", Context.MODE_PRIVATE);
        curUserForFav = getCurrentUser.getString("CURRENTUSER", "NO_CURRENT_USER");

        ApiInterface api = ApiClientToGetFavBrands.getApiInterfaceToGetFavBrands();

        CurrentUserDTO currentUserDTO = new CurrentUserDTO(curUserForFav);

        Call<JsonResponseForFavBrandsDTO> call = api.getFavouriteBrands(currentUserDTO);

        call.enqueue(new Callback<JsonResponseForFavBrandsDTO>() {
            @Override
            public void onResponse(Call<JsonResponseForFavBrandsDTO> call, Response<JsonResponseForFavBrandsDTO> response) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

                JsonResponseForFavBrandsDTO jsonResponseForFavBrandsDTO = response.body();
                favBrandDTOS = jsonResponseForFavBrandsDTO.getFavRecords();

                favouriteListAdapter.setFavData(favBrandDTOS);


            }

            @Override
            public void onFailure(Call<JsonResponseForFavBrandsDTO> call, Throwable t) {
                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

            }
        });


    }

    @Override
    public void onFavItemClick(int position) {

        Intent productListIntent = new Intent(FavouriteListActivity.this, Product_List_Activity.class);
        FavBrandDTO favBrandClicked = favBrandDTOS.get(position);


        productListIntent.putExtra("CURRENTUSER", curUserForFav);

        productListIntent.putExtra("BRAND_ID", favBrandClicked.getFav_brandId());
        productListIntent.putExtra("BRAND_NAME", favBrandClicked.getFav_productName());
        productListIntent.putExtra("BRAND_RATING", favBrandClicked.getFav_productRating());
        productListIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(productListIntent);

    }
}
