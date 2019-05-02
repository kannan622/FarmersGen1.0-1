package com.example.saravanamurali.farmersgen.review;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonResponseForProductPostReviewDTO;
import com.example.saravanamurali.farmersgen.models.PostReviewDTO;
import com.example.saravanamurali.farmersgen.retrofitclient.ApiClientToPostReview;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewPostActivity extends AppCompatActivity {

    private EditText postMessage;
    private Button postReview;

    private String NO_CURRENT_USER = "NO_CURRENT_USER";

    private String reviewText;

    private String brand_ID_To_Post_Review;
    private String product_Code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_post);

        postMessage = (EditText) findViewById(R.id.reviewTextArea);
        postReview = (Button) findViewById(R.id.postButton);

        Intent getData = getIntent();
        brand_ID_To_Post_Review = getData.getStringExtra("BRANDID_TO_POST_REVIEW");
        product_Code = getData.getStringExtra("PRODUCT_CODE_TO_POST_REVIEW");

        postReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postReview();
            }
        });


    }

    private void postReview() {
        reviewText = postMessage.getText().toString().trim();
        if (reviewText.isEmpty()) {
            Toast.makeText(ReviewPostActivity.this, "Please enter Review about this product", Toast.LENGTH_LONG).show();

        } else {

            addReviewAboutBrand();

        }
    }

    private void addReviewAboutBrand() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(ReviewPostActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api = ApiClientToPostReview.getApiInterfaceToPostReview();

        SharedPreferences getCurrentUser = getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
        String curUserForReview = getCurrentUser.getString("CURRENTUSER", "NO_CURRENT_USER");

        PostReviewDTO postReviewDTO = new PostReviewDTO(curUserForReview, brand_ID_To_Post_Review, product_Code, reviewText);

        Call<JsonResponseForProductPostReviewDTO> call = api.postBrandReview(postReviewDTO);

        call.enqueue(new Callback<JsonResponseForProductPostReviewDTO>() {
            @Override
            public void onResponse(Call<JsonResponseForProductPostReviewDTO> call, Response<JsonResponseForProductPostReviewDTO> response) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();

                    JsonResponseForProductPostReviewDTO jsonResponseForProductPostReviewDTO = response.body();

                    if (jsonResponseForProductPostReviewDTO.getStatusCode() == 200) {
                        Toast.makeText(ReviewPostActivity.this, "Thanks for the review", Toast.LENGTH_LONG).show();
                    }

                    else if(jsonResponseForProductPostReviewDTO.getStatusCode() == 500){
                        Toast.makeText(ReviewPostActivity.this, "You haven't purchased this product to Post review", Toast.LENGTH_LONG).show();
                    }


                }

            }

            @Override
            public void onFailure(Call<JsonResponseForProductPostReviewDTO> call, Throwable t) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

            }
        });
    }
}
