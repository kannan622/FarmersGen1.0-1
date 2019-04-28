package com.example.saravanamurali.farmersgen.productdescription;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.models.JSONResponseToGetProductDescDTO;
import com.example.saravanamurali.farmersgen.models.ProductDescDTO;
import com.example.saravanamurali.farmersgen.retrofitclient.ApiClientForProductDescription;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDescriptionActivity extends AppCompatActivity {

    String brandIDForProductDesc;
    String productCodeForProductDesc;

    ImageView productDescImage;
    TextView productDescBrandName;
    TextView productDescProductName;
    ImageView productDescPlus;
    ImageView productDescMinus;
    TextView descofProductAndPacking;

    TextView productDesc_productName;
    TextView productDesc_productQuantity;
    TextView productDesc_productActualPrice;
    TextView productDesc_productPrice;
    TextView productDesc_productRating;


    ImageView plusInIngredients, minusIngredients;
    TextView ingredientsUsedText;

    ImageView plusBenefits, minusBenefits;
    TextView benefitsText;

    TextView manufacturingVideo;
    TextView fbLink, instaLink, youTubeLink;

    RatingBar ratingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);

        //Input from ProductListActivity
        Intent intent = getIntent();
        brandIDForProductDesc = intent.getStringExtra("BRANDID_FOR_PRODUCT_DESC");
        productCodeForProductDesc = intent.getStringExtra("PRODUCT_CODE");

        //Product and Packaging
        productDescImage = (ImageView) findViewById(R.id.poductDescImage);
        productDescBrandName = (TextView) findViewById(R.id.productDescBrand);
        productDescProductName = (TextView) findViewById(R.id.productDescProductName);
        productDescPlus = (ImageView) findViewById(R.id.plusInProductDesc);
        productDescMinus = (ImageView) findViewById(R.id.minusInProductDesc);
        descofProductAndPacking = (TextView) findViewById(R.id.productAndPackText);
        //End of Product and Packaging

        productDesc_productName=(TextView)findViewById(R.id.productDesName);
        productDesc_productQuantity=(TextView)findViewById(R.id.productDescQuantity);
        productDesc_productActualPrice=(TextView)findViewById(R.id.productDescActualPrice);
        productDesc_productPrice=(TextView)findViewById(R.id.productDescOfferPrice);
        productDesc_productRating=(TextView)findViewById(R.id.productDescRating);


        //Ingredients Used
        plusInIngredients = (ImageView) findViewById(R.id.plusInIngredientsUsed);
        minusIngredients = (ImageView) findViewById(R.id.minusInIngredientsUsed);
        ingredientsUsedText = (TextView) findViewById(R.id.ingredientsUsedText);
        //End of Ingredients Used

        //Benefits
        plusBenefits = (ImageView) findViewById(R.id.plusBenefits);
        minusBenefits = (ImageView) findViewById(R.id.minusBenefits);
        benefitsText = (TextView) findViewById(R.id.benefitsText);
        //End of Benefits

        //Fb,Insta,Youtube
        fbLink = (TextView) findViewById(R.id.fbLink);
        instaLink = (TextView) findViewById(R.id.instaLink);
        youTubeLink = (TextView) findViewById(R.id.youtubeLink);

        ratingBar=(RatingBar)findViewById(R.id.rating);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(ProductDescriptionActivity.this,"You have Rated me" +ratingBar.getRating()+"Thank you",Toast.LENGTH_LONG).show();
            }
        });

        loadProductDescription();


        //Product and Packaging
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
        //End of Product and Packaging
//Ingredients Used
        plusInIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plusInIngredients.setVisibility(View.GONE);
                minusIngredients.setVisibility(View.VISIBLE);
                ingredientsUsedText.setMaxLines(Integer.MAX_VALUE);
            }
        });

        minusIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minusIngredients.setVisibility(View.GONE);
                plusInIngredients.setVisibility(View.VISIBLE);
                ingredientsUsedText.setMaxLines(2);

            }
        });
        //End of Ingredients Used

        //Benefits
        plusBenefits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plusBenefits.setVisibility(View.GONE);
                minusBenefits.setVisibility(View.VISIBLE);
                benefitsText.setMaxLines(Integer.MAX_VALUE);
            }
        });

        minusBenefits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minusBenefits.setVisibility(View.GONE);
                plusBenefits.setVisibility(View.VISIBLE);
                benefitsText.setMaxLines(2);

            }
        });
        //End Benefits


    }

    private void loadProductDescription() {

        ApiInterface apiInterface = ApiClientForProductDescription.getApiInterfaceForProdictDesctiption();

        ProductDescDTO productDescDTO = new ProductDescDTO(brandIDForProductDesc, productCodeForProductDesc);

        Call<JSONResponseToGetProductDescDTO> call = apiInterface.getProductDesc(productDescDTO);

        call.enqueue(new Callback<JSONResponseToGetProductDescDTO>() {
            @Override
            public void onResponse(Call<JSONResponseToGetProductDescDTO> call, Response<JSONResponseToGetProductDescDTO> response) {

                JSONResponseToGetProductDescDTO jsonResponseToGetProductDescDTO = response.body();

                setProductDescValues(jsonResponseToGetProductDescDTO);


            }

            @Override
            public void onFailure(Call<JSONResponseToGetProductDescDTO> call, Throwable t) {

            }
        });

    }

    private void setProductDescValues(JSONResponseToGetProductDescDTO jsonResponseToGetProductDescDTO) {

        Picasso.with(ProductDescriptionActivity.this).load(jsonResponseToGetProductDescDTO.getProductImage()).into(productDescImage);
        productDescBrandName.setText(jsonResponseToGetProductDescDTO.getBrandName());
        productDesc_productName.setText(jsonResponseToGetProductDescDTO.getProductName());
        productDesc_productQuantity.setText(jsonResponseToGetProductDescDTO.getProductQuantity());

        productDesc_productActualPrice.setText(jsonResponseToGetProductDescDTO.getProductActualPrice());
        productDesc_productPrice.setText(jsonResponseToGetProductDescDTO.getProductPrice());
        productDesc_productRating.setText(jsonResponseToGetProductDescDTO.getProudctRating());


        descofProductAndPacking.setText(jsonResponseToGetProductDescDTO.getProductAndPackagingText());
        ingredientsUsedText.setText(jsonResponseToGetProductDescDTO.getIngredientUsed());
        benefitsText.setText(jsonResponseToGetProductDescDTO.getUsage_benefits());
        fbLink.setText(jsonResponseToGetProductDescDTO.getFbLink());
        instaLink.setText(jsonResponseToGetProductDescDTO.getInstaLink());
        youTubeLink.setText(jsonResponseToGetProductDescDTO.getYoutubeLink());

    }
}
