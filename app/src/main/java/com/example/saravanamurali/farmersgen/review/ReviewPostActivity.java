package com.example.saravanamurali.farmersgen.review;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.models.PostReviewDTO;
import com.example.saravanamurali.farmersgen.retrofitclient.ApiClientToPostReview;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewPostActivity extends AppCompatActivity {

    private EditText postMessage;
    private Button postReview;

    private String NO_CURRENT_USER = "NO_CURRENT_USER";

    String reviewText;

    String brand_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_post);

        postMessage=(EditText) findViewById(R.id.reviewTextArea);
        postReview=(Button) findViewById(R.id.postButton );

        Intent getBrandID=getIntent();
       brand_ID= getBrandID.getStringExtra("BRANDID_FOR_REVIEW_POST");

        postReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postReview();
            }
        });




    }

    private void postReview() {
        reviewText=postMessage.getText().toString().trim();
        if(reviewText.isEmpty()){
            Toast.makeText(ReviewPostActivity.this,"Please enter Review about this product",Toast.LENGTH_LONG).show();

        }
        else {

            addReviewAboutBrand();

        }
    }

    private void addReviewAboutBrand() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(ReviewPostActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api=ApiClientToPostReview.getApiInterfaceToPostReview();

        SharedPreferences getCurrentUser = getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
        String curUserForReview = getCurrentUser.getString("CURRENTUSER", "NO_CURRENT_USER");

        PostReviewDTO postReviewDTO=new PostReviewDTO(curUserForReview,brand_ID,reviewText);

        Call<ResponseBody>  call=api.postBrandReview(postReviewDTO);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    if(csprogress.isShowing()){
                        csprogress.dismiss();
                    }
                    Toast.makeText(ReviewPostActivity.this,"Thanks for the feed back about our product",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                if(csprogress.isShowing()){
                    csprogress.dismiss();
                }

            }
        });
    }
}
