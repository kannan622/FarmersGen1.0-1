package com.example.saravanamurali.farmersgen.review;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.models.JsonResponseForBrandReview;
import com.example.saravanamurali.farmersgen.models.ReviewDTO;
import com.example.saravanamurali.farmersgen.models.ReviewDetailsDTO;
import com.example.saravanamurali.farmersgen.recyclerviewadapter.ReviewAdapter;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToGetReviews;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandReviewActivity extends AppCompatActivity {

    String brandID_For_Review;

    RecyclerView recyclerView_Review;
    List<ReviewDetailsDTO> reviewDTOList;

    ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_review);

        Intent get_BrandID = getIntent();

        brandID_For_Review = get_BrandID.getStringExtra("BRAND_ID_FOR_REVIEW");

        recyclerView_Review = (RecyclerView) findViewById(R.id.recyclerViewReview);
        recyclerView_Review.setHasFixedSize(true);
        recyclerView_Review.setLayoutManager(new LinearLayoutManager(BrandReviewActivity.this));

        reviewDTOList = new ArrayList<ReviewDetailsDTO>();

        reviewAdapter = new ReviewAdapter(reviewDTOList, BrandReviewActivity.this);

        recyclerView_Review.setAdapter(reviewAdapter);

        //Get List of Brands
        getReviewForBrand();

    }

    private void getReviewForBrand() {

        ApiInterface api = APIClientToGetReviews.getApiInterfaceToGetReviews();

        ReviewDTO reviewDTO = new ReviewDTO(brandID_For_Review);

        Call<JsonResponseForBrandReview> call = api.getBrandReviews(reviewDTO);

        call.enqueue(new Callback<JsonResponseForBrandReview>() {
            @Override
            public void onResponse(Call<JsonResponseForBrandReview> call, Response<JsonResponseForBrandReview> response) {
                if (response.isSuccessful()) {

                    JsonResponseForBrandReview jsonResponseForBrandReview = response.body();

                    reviewDTOList = jsonResponseForBrandReview.getReviewDetailsDTOS();

                    reviewAdapter.setData(reviewDTOList);


                }
            }

            @Override
            public void onFailure(Call<JsonResponseForBrandReview> call, Throwable t) {

            }
        });


    }
}
