package com.example.saravanamurali.farmersgen.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.models.AddCartDTO;
import com.example.saravanamurali.farmersgen.models.DeleteCountInCartDTO;
import com.example.saravanamurali.farmersgen.models.JSONResponseProductListDTO;
import com.example.saravanamurali.farmersgen.models.ProductListDTO;
import com.example.saravanamurali.farmersgen.models.UpdateCountInCartDTO;
import com.example.saravanamurali.farmersgen.models.ViewProductListDTO;
import com.example.saravanamurali.farmersgen.recyclerviewadapter.ProductListAdapter;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientForBrand;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientForCart;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientForDeleteItemInCart;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientForUpdateCountInCart;
import com.example.saravanamurali.farmersgen.review.BrandReviewActivity;
import com.example.saravanamurali.farmersgen.util.ProgressThread;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product_List_Activity extends AppCompatActivity implements ProductListAdapter.ShowDataInFragment, ProductListAdapter.AddCart, ProductListAdapter.UpdateCartInAddCart, ProductListAdapter.DeleteItemWhenCountZeroInterface {


    private final String NO_CURRENT_USER = "null";
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction;
    String brand_ID_For_ProductList;
    String brand_Name_For_ProductList;
    String brand_Name_For_ProductRating;
    String android_id = null;
    //Current User From Login Page
    String curentUser = "";
    //Current User From Shared Preferences
    String currentUserIdFromSharedPreferences;
    RecyclerView recyclerView;
    List<ProductListDTO> productListDTOList;
    ProductListAdapter productListAdapter;

    TextView textViewProductName;
    TextView textViewShortDesc;

    //Brand Review
    TextView brand_Review;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__list_);

        Intent intent = getIntent();

        curentUser = intent.getStringExtra("CURRENTUSER");

        System.out.println("Current User In Product List Activity" + curentUser);

        // ViewCartActivity viewCartActivity=new ViewCartActivity(curentUser);


        brand_ID_For_ProductList = intent.getStringExtra("BRAND_ID");

        brand_Name_For_ProductList = intent.getStringExtra("BRAND_NAME");
        brand_Name_For_ProductRating = intent.getStringExtra("BRAND_RATING");


        textViewProductName = (TextView) findViewById(R.id.proListBrandName);
        textViewShortDesc = (TextView) findViewById(R.id.proListShortDesc);
        textViewProductName.setText(brand_Name_For_ProductList);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_activity_product_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(Product_List_Activity.this, 2));

        //Brand Review
        brand_Review=(TextView)findViewById(R.id.brand_Review);

        brand_Review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callReviewDisplayActivity();
            }
        });


        SharedPreferences getCurrentUser = getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
        currentUserIdFromSharedPreferences = getCurrentUser.getString("CURRENTUSER", NO_CURRENT_USER);


    }

    //Review Display method
    private void callReviewDisplayActivity() {

        Intent brandReviewActivity=new Intent(Product_List_Activity.this,BrandReviewActivity.class);
        brandReviewActivity.putExtra("BRAND_ID_FOR_REVIEW",brand_ID_For_ProductList);
        startActivity(brandReviewActivity);



    }

    @Override
    protected void onResume() {
        super.onResume();

        //Guest User
        //Display all products list from Brand
        loadRetrofitProductList();

        productListDTOList = new ArrayList<ProductListDTO>();

        productListAdapter = new ProductListAdapter(Product_List_Activity.this, productListDTOList);
        recyclerView.setAdapter(productListAdapter);
        // productListAdapter.setOnProductItemClickListener(Product_List_Activity.this);

        productListAdapter.setShowDataInFragment(Product_List_Activity.this);

        productListAdapter.setAddCart(Product_List_Activity.this);

        productListAdapter.setUpdateCartInAddCart(Product_List_Activity.this);

        productListAdapter.setDeleteItemWhenCountZero(Product_List_Activity.this);

    }

    //Display all products list from Brand
    private void loadRetrofitProductList() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(Product_List_Activity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface apiInterface = APIClientForBrand.getApiInterfaceForBrand();

        String ANDROID_MOBILE_ID = Settings.Secure.getString(Product_List_Activity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        ViewProductListDTO viewProductListDTO = new ViewProductListDTO(brand_ID_For_ProductList, ANDROID_MOBILE_ID);

        Call<JSONResponseProductListDTO> call = apiInterface.getSingleBrandProductList(viewProductListDTO);

        call.enqueue(new Callback<JSONResponseProductListDTO>() {
            @Override
            public void onResponse(Call<JSONResponseProductListDTO> call, Response<JSONResponseProductListDTO> response) {

                JSONResponseProductListDTO jsonResponseProductListDTO = response.body();

                List<ProductListDTO> productListDTO = jsonResponseProductListDTO.getProductListRecord();

                int totalCount = 0;


                for (int i = 0; i < productListDTO.size(); i++) {
                    String product_Name = productListDTO.get(i).getProductName();

                    String pro_Code = String.valueOf(productListDTO.get(i).getProductCode());

                    String product_Image = productListDTO.get(i).getProductImage();
                    String product_Price = productListDTO.get(i).getProductPrice();
                    String count = productListDTO.get(i).getCount();
                    String productQuatity=productListDTO.get(i).getProductQuantity();
                    String productActualPrice=productListDTO.get(i).getAcutalPrice();

                    if (count != null && !count.isEmpty())
                        totalCount = totalCount + Integer.parseInt(count);

                    ProductListDTO productList = new ProductListDTO(pro_Code, count, product_Name, product_Image, product_Price,productQuatity,productActualPrice);
                    productListDTOList.add(productList);
                    // System.out.println("Product Name of Every Product" + product_Name);
                }

                if (response.isSuccessful()) {
                    if(csprogress.isShowing()){
                        csprogress.dismiss();
                    }
                    productListAdapter.notifyDataSetChanged();
                    // Toast.makeText(Product_List_Activity.this, "Success", Toast.LENGTH_SHORT).show();

                    if (totalCount > 0) {
                        try {
                            Count_Price_Show_Fragment count_price_show_fragment = new Count_Price_Show_Fragment();
                            fragmentManager.beginTransaction().replace(R.id.countPriceShow, count_price_show_fragment).show(count_price_show_fragment).commit();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else {
                        Fragment cartView = fragmentManager.findFragmentById(R.id.countPriceShow);
                        if (cartView != null)
                            fragmentManager.beginTransaction().remove(cartView).commit();
                    }


                } else {

                    // Toast.makeText(Product_List_Activity.this, "Error", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<JSONResponseProductListDTO> call, Throwable t) {

                if(csprogress.isShowing()){
                    csprogress.dismiss();
                }

                Toast.makeText(Product_List_Activity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }


    //Adding items to Cart
    @Override
    public void addCart(int countNum, String product_Code, String productPrice) {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(Product_List_Activity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        android_id = Settings.Secure.getString(Product_List_Activity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        //Need to Change here.
        ApiInterface apiInterface = APIClientForCart.getApiInterfaceForCount();

        System.out.println("LIST OF YOUR ORDERED PORUDCT" + "     " + product_Code + "      " + countNum + "       " + android_id);

        String addCount = String.valueOf(countNum);

        AddCartDTO addCartDTO = new AddCartDTO(product_Code, addCount, productPrice, android_id);

        Call<ResponseBody> call = apiInterface.addCart(addCartDTO);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {

                    //Thread to slow the process
                    ProgressThread progressThread=new ProgressThread();
                    progressThread.run();

                    if(csprogress.isShowing()){
                        csprogress.dismiss();
                    }

                    // Toast.makeText(Product_List_Activity.this,"Success",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Product_List_Activity.this, "Error", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                if(csprogress.isShowing()){
                    csprogress.dismiss();
                }

                Toast.makeText(Product_List_Activity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                Toast.makeText(Product_List_Activity.this,"I am here"+t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }


    //Updating Count to AddCart Table
    @Override
    public void updateCartInAddCart(String updateProductCode, int updateCount, String prouctPrice) {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(Product_List_Activity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        System.out.println("I am inside updateCartInAddCart");

        String ANDROID_MOBILE_ID = Settings.Secure.getString(Product_List_Activity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        ApiInterface apiInterface = APIClientForUpdateCountInCart.getApiInterfaceForUpdateCountInAddCart();

        String upCount = String.valueOf(updateCount);

        UpdateCountInCartDTO upateCountInAddCart = new UpdateCountInCartDTO(upCount, updateProductCode, prouctPrice, ANDROID_MOBILE_ID);

        Call<ResponseBody> call = apiInterface.updateCountatAddCart(upateCountInAddCart);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    //Thread to slow the process
                    ProgressThread progressThread=new ProgressThread();
                    progressThread.run();


                    if(csprogress.isShowing()){
                        csprogress.dismiss();
                    }



                    // Toast.makeText(Product_List_Activity.this,"Success",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Product_List_Activity.this, "Error", Toast.LENGTH_LONG).show();
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


    //Delete item from Cart when Count is Zero
    @Override
    public void deleteItemWhenCountZero(String produceCode) {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(Product_List_Activity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        String ANDROID_MOBILE_ID = Settings.Secure.getString(Product_List_Activity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        ApiInterface api = APIClientForDeleteItemInCart.getApiInterfaceForDeleteItemFromCart();

        DeleteCountInCartDTO deleteItemFromCart = new DeleteCountInCartDTO(ANDROID_MOBILE_ID, produceCode);

        Call<ResponseBody> call = api.deleteItemFromCart(deleteItemFromCart);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    //Thread to slow the process
                    ProgressThread progressThread=new ProgressThread();
                    progressThread.run();


                    if(csprogress.isShowing()){
                        csprogress.dismiss();
                    }


                    // Toast.makeText(Product_List_Activity.this,"Deleted",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    } //End of Delete Count


    //Show Fragment when customer adds item
    @Override
    public void showInFragment(int fragmentCount) {
        if (productListAdapter.getCartCount() > 0) {
            Count_Price_Show_Fragment currentUser = new Count_Price_Show_Fragment(curentUser);
            Count_Price_Show_Fragment count_price_show_fragment = new Count_Price_Show_Fragment();
            count_price_show_fragment.getCountPrice(fragmentCount);
            fragmentManager.beginTransaction().replace(R.id.countPriceShow, count_price_show_fragment)
                    .show(count_price_show_fragment)
                    .commit();
        } else {
            Fragment cartView = fragmentManager.findFragmentById(R.id.countPriceShow);
            if (cartView != null)
                fragmentManager.beginTransaction().remove(cartView).commit();
        }

    }
}
