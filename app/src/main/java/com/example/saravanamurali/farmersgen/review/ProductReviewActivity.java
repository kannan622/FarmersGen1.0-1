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

public class ProductReviewActivity extends AppCompatActivity {

    private String NO_CURRENT_USER = "NO_CURRENT_USER";

    private TextView product_Post_Review;

    private String brandID;
    private String productCode;

    RecyclerView recyclerView_Review;
    List<ReviewDetailsDTO> reviewDTOList;

    ReviewAdapter reviewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_review);

        product_Post_Review = (TextView) findViewById(R.id.postReviewForProduct);

        recyclerView_Review = (RecyclerView) findViewById(R.id.recyclerViewReviewForProduct);
        recyclerView_Review.setHasFixedSize(true);
        recyclerView_Review.setLayoutManager(new LinearLayoutManager(ProductReviewActivity.this));

        reviewDTOList = new ArrayList<ReviewDetailsDTO>();

        reviewAdapter = new ReviewAdapter(reviewDTOList, ProductReviewActivity.this);

        recyclerView_Review.setAdapter(reviewAdapter);



        Intent getData = getIntent();
        brandID = getData.getStringExtra("BRAND_ID");
        productCode = getData.getStringExtra("PRODUCT_CODE");

        //Get List of Brands
        getProductReview();


        product_Post_Review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences getCurrentUser = getSharedPreferences("CURRENT_USER", Context.MODE_PRIVATE);
                String curUserToPostReview = getCurrentUser.getString("CURRENTUSER", "NO_CURRENT_USER");

                if (curUserToPostReview.equals(NO_CURRENT_USER)) {
                    Toast toast = Toast.makeText(ProductReviewActivity.this, " Please Login and purchase product to post your review", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER, 0, 0);
                    toast.show();

                } else {

                    Intent reviewPostIntent = new Intent(ProductReviewActivity.this, ReviewPostActivity.class);
                    reviewPostIntent.putExtra("BRANDID_TO_POST_REVIEW", brandID);
                    reviewPostIntent.putExtra("PRODUCT_CODE_TO_POST_REVIEW", productCode);
                    startActivity(reviewPostIntent);

                }

            }
        });

    }

    private void getProductReview() {
        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(ProductReviewActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);

        ApiInterface api = APIClientToGetReviews.getApiInterfaceToGetReviews();

        ReviewDTO reviewDTO = new ReviewDTO(productCode);

        Call<JsonResponseForBrandReview> call = api.getBrandReviews(reviewDTO);

        call.enqueue(new Callback<JsonResponseForBrandReview>() {
            @Override
            public void onResponse(Call<JsonResponseForBrandReview> call, Response<JsonResponseForBrandReview> response) {
                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

                JsonResponseForBrandReview jsonResponseForBrandReview = response.body();

                reviewDTOList = jsonResponseForBrandReview.getReviewDetailsDTOS();

                reviewAdapter.setDataForReview(reviewDTOList);

            }

            @Override
            public void onFailure(Call<JsonResponseForBrandReview> call, Throwable t) {

            }
        });


    }
}
