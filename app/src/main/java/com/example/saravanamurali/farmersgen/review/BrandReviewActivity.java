package com.example.saravanamurali.farmersgen.review;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonResponseForBrandReview;
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

    private String NO_CURRENT_USER = "NO_CURRENT_USER";

    String brandID_For_Review;

    TextView post_Review;

    RecyclerView recyclerView_Review;
    List<ReviewDetailsDTO> reviewDTOList;

    ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_review);

        Intent get_BrandID = getIntent();

        post_Review=(TextView)findViewById(R.id.postReview);

        brandID_For_Review = get_BrandID.getStringExtra("BRAND_ID_FOR_REVIEW");

        recyclerView_Review = (RecyclerView) findViewById(R.id.recyclerViewReview);
        recyclerView_Review.setHasFixedSize(true);
        recyclerView_Review.setLayoutManager(new LinearLayoutManager(BrandReviewActivity.this));

        reviewDTOList = new ArrayList<ReviewDetailsDTO>();

        reviewAdapter = new ReviewAdapter(reviewDTOList, BrandReviewActivity.this);

        recyclerView_Review.setAdapter(reviewAdapter);

        //Get List of Brands
        getReviewForBrand();

        post_Review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postYourReview();
            }
        });

    }

    private void postYourReview() {

        SharedPreferences getCurrentUser = getSharedPreferences("CURRENT_USER", Context.MODE_PRIVATE);
        String curUserForReview = getCurrentUser.getString("CURRENTUSER", "NO_CURRENT_USER");

        if(curUserForReview.equals(NO_CURRENT_USER)){
            Toast toast=Toast.makeText(BrandReviewActivity.this," Please Login and purchase product to post your review",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER, 0, 0);
            toast.show();

        }

        else{

           /* Intent reviewPostIntent=new Intent(BrandReviewActivity.this,ReviewPostActivity.class);
            reviewPostIntent.putExtra("BRANDID_FOR_REVIEW_POST",brandID_For_Review);
            startActivity(reviewPostIntent);
*/
        }

    }

    private void getReviewForBrand() {
        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(BrandReviewActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api = APIClientToGetReviews.getApiInterfaceToGetReviews();

        ReviewDTO reviewDTO = new ReviewDTO(brandID_For_Review);

        Call<JsonResponseForBrandReview> call = api.getBrandReviews(reviewDTO);

        call.enqueue(new Callback<JsonResponseForBrandReview>() {
            @Override
            public void onResponse(Call<JsonResponseForBrandReview> call, Response<JsonResponseForBrandReview> response) {
                if (response.isSuccessful()) {
                    if(csprogress.isShowing()){
                        csprogress.dismiss();
                    }

                    JsonResponseForBrandReview jsonResponseForBrandReview = response.body();

                    reviewDTOList = jsonResponseForBrandReview.getReviewDetailsDTOS();

                    reviewAdapter.setDataForReview(reviewDTOList);


                }
            }

            @Override
            public void onFailure(Call<JsonResponseForBrandReview> call, Throwable t) {

                if(csprogress.isShowing()){
                    csprogress.dismiss();
                }

            }
        });


    }
}
