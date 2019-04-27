package com.example.saravanamurali.farmersgen.productdescription;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saravanamurali.farmersgen.R;

public class ProductDescriptionActivity extends AppCompatActivity {

    String brandIDForProductDesc;;
    String productCodeForProductDesc;

    ImageView productDescImage;
    TextView productDescBrandName;
    TextView productDescProductName;
    ImageView productDescPlus;
    ImageView productDescMinus;
    TextView descofProductAndPacking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);

        //Input from ProductListActivity
        Intent intent=getIntent();
        brandIDForProductDesc=intent.getStringExtra("BRANDID_FOR_PRODUCT_DESC");
        productCodeForProductDesc=intent.getStringExtra("PRODUCT_CODE");

        productDescImage=(ImageView)findViewById(R.id.poductDescImage);
        productDescBrandName=(TextView)findViewById(R.id.productDescBrand);
        productDescProductName=(TextView)findViewById(R.id.productDescProductName);
        productDescPlus=(ImageView)findViewById(R.id.plusInProductDesc);
        productDescMinus=(ImageView)findViewById(R.id.minusInProductDesc);
        descofProductAndPacking=(TextView)findViewById(R.id.productAndPackText);

        productDescPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productDescPlus.setVisibility(View.GONE);
                productDescMinus.setVisibility(View.VISIBLE);
                descofProductAndPacking.setMaxLines(Integer.MAX_VALUE);
            }
        });

        productDescMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productDescMinus.setVisibility(View.GONE);
                productDescPlus.setVisibility(View.VISIBLE);
                descofProductAndPacking.setMaxLines(2);
            }
        });

    }
}
