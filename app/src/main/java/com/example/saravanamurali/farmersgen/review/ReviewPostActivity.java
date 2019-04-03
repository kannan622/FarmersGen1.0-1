package com.example.saravanamurali.farmersgen.review;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;

public class ReviewPostActivity extends AppCompatActivity {

    private EditText postMessage;
    private Button postReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_post);

        postMessage=(EditText) findViewById(R.id.reviewTextArea);
        postReview=(Button) findViewById(R.id.postButton );

        postReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postReview();
            }
        });




    }

    private void postReview() {
        String reviewText=postMessage.getText().toString().trim();
        if(reviewText.isEmpty()){
            Toast.makeText(ReviewPostActivity.this,"Please enter Review about this product",Toast.LENGTH_LONG).show();

        }
        else {

        }
    }
}
