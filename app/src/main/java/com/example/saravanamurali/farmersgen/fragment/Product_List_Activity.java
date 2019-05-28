package com.example.saravanamurali.farmersgen.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonResponseForAddCartDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonResponseForAddFavourite;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonResponseForDeleteCartDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonResponseForDeleteFavDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonResponseForUpdateCartDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonResponseToCheckFavourite;
import com.example.saravanamurali.farmersgen.models.AddCartDTO;
import com.example.saravanamurali.farmersgen.models.AddFavouriteDTO;
import com.example.saravanamurali.farmersgen.models.CheckFavDTO;
import com.example.saravanamurali.farmersgen.models.DeleteCountInCartDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseProductListDTO;
import com.example.saravanamurali.farmersgen.models.GetDataFromSqlLiteDTO;
import com.example.saravanamurali.farmersgen.models.GetOrdersUsingDeviceID_DTO;
import com.example.saravanamurali.farmersgen.models.LogOutDeviceIDDTO;
import com.example.saravanamurali.farmersgen.models.ProductListDTO;
import com.example.saravanamurali.farmersgen.models.UpdateCountInCartDTO;
import com.example.saravanamurali.farmersgen.models.ViewProductListDTO;
import com.example.saravanamurali.farmersgen.productdescription.ProductDescriptionActivity;
import com.example.saravanamurali.farmersgen.recyclerviewadapter.ProductListAdapter;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientForBrand;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientForCart;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientForDeleteItemInCart;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientForUpdateCountInCart;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientLogOutUsingDeviceID;
import com.example.saravanamurali.farmersgen.retrofitclient.ApiClientToAddFavouriteItems;
import com.example.saravanamurali.farmersgen.retrofitclient.ApiClientToCheckFavourite;
import com.example.saravanamurali.farmersgen.retrofitclient.ApiClientToRemoveFav;
import com.example.saravanamurali.farmersgen.review.BrandReviewActivity;
import com.example.saravanamurali.farmersgen.sqllite.ProductAddInSqlLite;
import com.example.saravanamurali.farmersgen.tappedactivity.HomeActivity;
import com.example.saravanamurali.farmersgen.util.FavStatus;
import com.example.saravanamurali.farmersgen.util.Network_config;
import com.google.gson.JsonObject;
import com.like.LikeButton;
import com.like.OnLikeListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product_List_Activity extends AppCompatActivity implements ProductListAdapter.AddCart {

    private String NO_CURRENT_USER_FOR_FAV_LIST = "NO_CURRENT_USER_FOR_FAV_LIST";
    private final String NO_CURRENT_USER = "null";
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction;

    String brand_ID_For_ProductList;
    String brand_Name_For_ProductList;
    String brand_Name_For_ProductRating;
    String curentUser = "";

    //Current User From Shared Preferences
    String currentUserIdFromSharedPreferences;

    RecyclerView recyclerView;
    List<ProductListDTO> productListDTOList;
    ProductListAdapter productListAdapter;

    TextView textViewProductName;
    TextView productRating;


    //Favourite List
    LikeButton likeButton;
    CoordinatorLayout coordinatorLayoutForFav;
    String curUser_Favourite_check;
    int favStatus;
    //End of Favourite List

    ImageView imageView;

    //SQLLITE DATABASE
    SQLiteDatabase mSqLiteDatabase;
    List<GetDataFromSqlLiteDTO> getDataFromSqlLiteDTOS;

    private Dialog dialog;
    private Context m_Context;

    List<ProductListDTO> productListDTO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__list_);

        getDataFromSqlLiteDTOS = new ArrayList<GetDataFromSqlLiteDTO>();
        productListDTO = new ArrayList<ProductListDTO>();

        m_Context = Product_List_Activity.this;
        dialog = new Dialog(m_Context);

        //SQLLITE DATABASE
        mSqLiteDatabase = openOrCreateDatabase(ProductAddInSqlLite.DATABASE_NAME, MODE_PRIVATE, null);
        createTable();


        imageView = (ImageView) findViewById(R.id.leftArrow);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Product_List_Activity.this, HomeActivity.class));
            }
        });

        productRating = (TextView) findViewById(R.id.proListRating);


        Intent intent = getIntent();
        curentUser = intent.getStringExtra("CURRENTUSER");


        //Brand Details
        brand_ID_For_ProductList = intent.getStringExtra("BRAND_ID");
        brand_Name_For_ProductList = intent.getStringExtra("BRAND_NAME");
        brand_Name_For_ProductRating = intent.getStringExtra("BRAND_RATING");
        textViewProductName = (TextView) findViewById(R.id.proListBrandName);
        // textViewShortDesc = (TextView) findViewById(R.id.proListShortDesc);
        textViewProductName.setText(brand_Name_For_ProductList);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_activity_product_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(Product_List_Activity.this, 2));
        //End of Brand Details

        productRating.setText(brand_Name_For_ProductRating);

        //Favourite List
        likeButton = (LikeButton) findViewById(R.id.favIcon);
        coordinatorLayoutForFav = (CoordinatorLayout) findViewById(R.id.coordnatorLayoutForFav);
        //End of Favourite List

        //Favourite Check Block
        SharedPreferences getCurrentUserForFavCheck = getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
        curUser_Favourite_check = getCurrentUserForFavCheck.getString("CURRENTUSER", "NO_CURRENT_USER_FOR_FAV_LIST");

        if (curUser_Favourite_check.equals(NO_CURRENT_USER_FOR_FAV_LIST)) {
            return;
        } else if (!curUser_Favourite_check.equals(NO_CURRENT_USER_FOR_FAV_LIST)) {

            if (Network_config.is_Network_Connected_flag(m_Context)) {

                checkFavouriteForThisBrand();

            } else {
                Network_config.customAlert(dialog, m_Context, getResources().getString(R.string.app_name),
                        getResources().getString(R.string.connection_message));
            }
        }
        //End of Favourite Check Block


        //If user is logged favourite should add otherwise should toast like please login to add favorite
        SharedPreferences getCurrentUserForFav = getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
        final String curUser_Favourite = getCurrentUserForFav.getString("CURRENTUSER", "NO_CURRENT_USER_FOR_FAV_LIST");

        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                if (curUser_Favourite.equals(NO_CURRENT_USER_FOR_FAV_LIST)) {
                    Toast.makeText(Product_List_Activity.this, "Please Login To Add Favourite Items", Toast.LENGTH_LONG).show();
                } else if (!curUser_Favourite.equals(NO_CURRENT_USER_FOR_FAV_LIST)) {

                    new android.os.Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            //Can do some process here


                        }
                    }, 1000);

                    if (Network_config.is_Network_Connected_flag(m_Context)) {
                        addFavouriteItems(curUser_Favourite);
                    } else {
                        Network_config.customAlert(dialog, m_Context, getResources().getString(R.string.app_name),
                                getResources().getString(R.string.connection_message));
                    }

                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {

                new android.os.Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        //Can do some process here


                    }
                }, 1000);

                if (Network_config.is_Network_Connected_flag(m_Context)) {
                    removeFavouriteItems(curUser_Favourite);
                } else {
                    Network_config.customAlert(dialog, m_Context, getResources().getString(R.string.app_name),
                            getResources().getString(R.string.connection_message));
                }


            }
        });


        SharedPreferences getCurrentUser = getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
        currentUserIdFromSharedPreferences = getCurrentUser.getString("CURRENTUSER", NO_CURRENT_USER);


    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS add_cart (\n" +
                "    id INTEGER NOT NULL CONSTRAINT add_cart_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    product_code varchar(200) NOT NULL,\n" +
                "    count tinyint(4) NOT NULL,\n" +
                "    total_price varchar(2000) NOT NULL,\n" +
                "    device_id varchar(200) NOT NULL\n" +
                ");";

        mSqLiteDatabase.execSQL(sql);
    }

    //Checking to enable favourite icon for this user
    private void checkFavouriteForThisBrand() {

        ApiInterface api = ApiClientToCheckFavourite.getApiInterfaceToCheckFavourite();

        CheckFavDTO checkFavDTO = new CheckFavDTO(brand_ID_For_ProductList, curUser_Favourite_check);

        Call<JsonResponseToCheckFavourite> call = api.checkFavList(checkFavDTO);

        call.enqueue(new Callback<JsonResponseToCheckFavourite>() {
            @Override
            public void onResponse(Call<JsonResponseToCheckFavourite> call, Response<JsonResponseToCheckFavourite> response) {

                JsonResponseToCheckFavourite jsonResponseToCheckFavourite = response.body();

                if (jsonResponseToCheckFavourite.getResponseCode() == 200 && jsonResponseToCheckFavourite.getCheckStatus() == 1) {

                    likeButton.setLiked(true);
                } else {

                    likeButton.setLiked(false);

                }
            }

            @Override
            public void onFailure(Call<JsonResponseToCheckFavourite> call, Throwable t) {

            }
        });

    }

    //Add Favourite Items
    private void addFavouriteItems(String curent_User) {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(Product_List_Activity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api = ApiClientToAddFavouriteItems.getApiInterfaceAddFavouriteItem();

        AddFavouriteDTO addFavouriteDTO = new AddFavouriteDTO(curent_User, brand_ID_For_ProductList, FavStatus.ADD_FAV_STATUS);

        Call<JsonResponseForAddFavourite> call = api.addFavourite(addFavouriteDTO);

        call.enqueue(new Callback<JsonResponseForAddFavourite>() {
            @Override
            public void onResponse(Call<JsonResponseForAddFavourite> call, Response<JsonResponseForAddFavourite> response) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

                Snackbar snackbar = Snackbar.make(coordinatorLayoutForFav, "Added to your Favourite List", Snackbar.LENGTH_SHORT);
                snackbar.show();

            }

            @Override
            public void onFailure(Call<JsonResponseForAddFavourite> call, Throwable t) {
                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

            }
        });

    }


    //Remove Favourite Items
    private void removeFavouriteItems(String cur_User) {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(Product_List_Activity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface apiInterface = ApiClientToRemoveFav.getApiInterfaceToRemoveBrands();

        CheckFavDTO checkFavDTO = new CheckFavDTO(brand_ID_For_ProductList, cur_User);

        Call<JsonResponseForDeleteFavDTO> call = apiInterface.removeFavBrand(checkFavDTO);

        call.enqueue(new Callback<JsonResponseForDeleteFavDTO>() {
            @Override
            public void onResponse(Call<JsonResponseForDeleteFavDTO> call, Response<JsonResponseForDeleteFavDTO> response) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

                Snackbar snackbar = Snackbar.make(coordinatorLayoutForFav, "Removed from Favourite List", Snackbar.LENGTH_SHORT);
                snackbar.show();


            }

            @Override
            public void onFailure(Call<JsonResponseForDeleteFavDTO> call, Throwable t) {
                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

            }
        });


    }


   /* @Override
    protected void onStart() {
        super.onStart();

        loadProductListDataFromSqlLite();
    }*/

    @Override
    protected void onResume() {
        super.onResume();

        // refreshSqlliteDatabase();


        if (Network_config.is_Network_Connected_flag(m_Context)) {
            loadProductListDataFromSqlLite();
        } else {
            Network_config.customAlert(dialog, m_Context, getResources().getString(R.string.app_name),
                    getResources().getString(R.string.connection_message));
        }

        if (Network_config.is_Network_Connected_flag(m_Context)) {
            //Guest User
            //Display all products list from Brand
            loadRetrofitProductList();
        } else {
            Network_config.customAlert(dialog, m_Context, getResources().getString(R.string.app_name),
                    getResources().getString(R.string.connection_message));
        }

        productListDTOList = new ArrayList<ProductListDTO>();

        productListAdapter = new ProductListAdapter(Product_List_Activity.this, productListDTOList);
        recyclerView.setAdapter(productListAdapter);
        // productListAdapter.setOnProductItemClickListener(Product_List_Activity.this);


        productListAdapter.setAddCart(Product_List_Activity.this);


    }

   /* private void refreshSqlliteDatabase() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(Product_List_Activity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);

        String ANDROID_MOBILE_ID = Settings.Secure.getString(Product_List_Activity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Cursor cursor = mSqLiteDatabase.rawQuery("select product_code,count,total_price,device_id from add_cart where device_id=?", new String[]{ANDROID_MOBILE_ID});

        if (cursor.moveToFirst()) {

            do {

                GetDataFromSqlLiteDTO refresh = new GetDataFromSqlLiteDTO(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));

            }
            while (cursor.moveToNext());

        }

        if (csprogress.isShowing()) {
            csprogress.dismiss();
        }


    }*/

    public void loadProductListDataFromSqlLite() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(Product_List_Activity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);

        String ANDROID_MOBILE_ID = Settings.Secure.getString(Product_List_Activity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Cursor cursor = mSqLiteDatabase.rawQuery("select product_code,count,total_price,device_id from add_cart where device_id=?", new String[]{ANDROID_MOBILE_ID});

        if (cursor.moveToFirst()) {

            do {

                GetDataFromSqlLiteDTO getDataFromSqlLiteDTO = new GetDataFromSqlLiteDTO(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));


                /*System.out.println(getDataFromSqlLiteDTO.getCount());
                System.out.println(getDataFromSqlLiteDTO.getProduct_code());
                System.out.println(getDataFromSqlLiteDTO.getTotal_price());
                System.out.println(getDataFromSqlLiteDTO.getDevice_ID());*/
                getDataFromSqlLiteDTOS.add(getDataFromSqlLiteDTO);

            }
            while (cursor.moveToNext());


        }

        if (csprogress.isShowing()) {
            csprogress.dismiss();
        }


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


                for (int i = 0; i < productListDTO.size(); i++) {

                    if (getDataFromSqlLiteDTOS.size() == 0 && productListDTO.get(i).getCount() != null) {


                        productListAdapter.addCount(productListDTO.get(i).getCount(), productListDTO.get(i).getProductCode(), productListDTO.get(i).getProductPrice());

                    }
                }

                loadProductListDataFromSqlLite();


                //productListAdapter.notifyDataSetChanged();

                int totalCount = 0;

                for (int i = 0; i < productListDTO.size(); i++) {


                    for (int j = 0; j < getDataFromSqlLiteDTOS.size(); j++) {

                        if (productListDTO.get(i).getProductCode().equals(getDataFromSqlLiteDTOS.get(j).getProduct_code())) {
                            productListDTO.remove(productListDTO.get(i).getCount());

                            productListDTO.get(i).setCount(getDataFromSqlLiteDTOS.get(j).getCount());
                            totalCount = totalCount + Integer.parseInt(productListDTO.get(i).getCount());


                        }
                    }


                }

                productListAdapter.setDataSetChanged(productListDTO);
                productListAdapter.notifyDataSetChanged();

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }


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


            }

            @Override
            public void onFailure(Call<JSONResponseProductListDTO> call, Throwable t) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

            }
        });


    }


    private void clearAllItemsAtAddCartTableUsingDeviceID() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(Product_List_Activity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);

        ApiInterface api = APIClientLogOutUsingDeviceID.getApiInterfaceToLogOutUsingDeviceID();

        String ANDROID_MOBILE_ID = Settings.Secure.getString(Product_List_Activity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);


        LogOutDeviceIDDTO logOutDeviceIDDTO = new LogOutDeviceIDDTO(ANDROID_MOBILE_ID);

        Call<ResponseBody> call = api.getLogOutUsingDeviceID(logOutDeviceIDDTO);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    productListAdapter.notifyDataSetChanged();

                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

            }
        });


    }

    private void deleteAllDataInSqlLite() {

        String delete_device_id = Settings.Secure.getString(Product_List_Activity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        String delete = "delete from add_cart where device_id=?";

        mSqLiteDatabase.execSQL(delete, new String[]{delete_device_id});

    }


    @Override
    public void addCartInSqlLite(int countNum, String product_Code, String productPrice) {

        String ANDROID_MOBILE_ID = Settings.Secure.getString(Product_List_Activity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        String countVal = String.valueOf(countNum);

        String insertVal = "INSERT INTO add_cart \n" +
                "(product_code, count, total_price, device_id)\n" +
                "VALUES \n" +
                "(?,?,?,?);";

        mSqLiteDatabase.execSQL(insertVal, new String[]{product_Code, countVal, productPrice, ANDROID_MOBILE_ID});


        //System.out.println("SQL LITE" + product_Code + " " + countVal + " " + productPrice);

        //Toast.makeText(Product_List_Activity.this, " Added", Toast.LENGTH_LONG).show();


    }

    @Override
    public void updateCartInSqlLite(String updateProductCode, int updateCount, String updateprouctPrice) {

        String device_id = Settings.Secure.getString(Product_List_Activity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        String u_Count = String.valueOf(updateCount);

        int u_ProductPrice = Integer.parseInt(updateprouctPrice);
        int productMulPrice = updateCount * u_ProductPrice;
        String u_totalPrice = String.valueOf(productMulPrice);

        String u_query = "UPDATE add_cart SET count=?, total_price=? where product_code=? and device_id =? ";

        mSqLiteDatabase.execSQL(u_query, new String[]{u_Count, u_totalPrice, updateProductCode, device_id});

        //System.out.println("Update" + u_Count + u_totalPrice + updateProductCode);

        //Toast.makeText(Product_List_Activity.this, "Updated", Toast.LENGTH_LONG).show();

    }

    @Override
    public void deleteItemWhenCountBecomesZero(String product_Code) {

        String delete_device_id = Settings.Secure.getString(Product_List_Activity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);


        String delete = "delete from add_cart where product_code=? and device_id=? ";

        mSqLiteDatabase.execSQL(delete, new String[]{product_Code, delete_device_id});

        //Toast.makeText(Product_List_Activity.this, "Deleted", Toast.LENGTH_LONG).show();


    }


    //Delete item from Cart when Count is Zero
    @Override
    public void deleteItemWhenCountZeroInServer(String produceCode) {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(Product_List_Activity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        String ANDROID_MOBILE_ID = Settings.Secure.getString(Product_List_Activity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        ApiInterface api = APIClientForDeleteItemInCart.getApiInterfaceForDeleteItemFromCart();

        DeleteCountInCartDTO deleteItemFromCart = new DeleteCountInCartDTO(ANDROID_MOBILE_ID, produceCode);

        Call<JsonResponseForDeleteCartDTO> call = api.deleteItemFromCart(deleteItemFromCart);

        call.enqueue(new Callback<JsonResponseForDeleteCartDTO>() {
            @Override
            public void onResponse(Call<JsonResponseForDeleteCartDTO> call, Response<JsonResponseForDeleteCartDTO> response) {

                JsonResponseForDeleteCartDTO jsonResponseForDeleteCartDTO = response.body();

                if (jsonResponseForDeleteCartDTO.getDeleteResponseCode() == 200) {

                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }

                    productListAdapter.notifyDataSetChanged();

                } else {

                    Toast.makeText(Product_List_Activity.this, "Please check your internet connection", Toast.LENGTH_LONG).show();

                }

            }


            @Override
            public void onFailure(Call<JsonResponseForDeleteCartDTO> call, Throwable t) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }
                if (t.getMessage() != null) {

                    loadRetrofitProductList();
                }
                /* Toast.makeText(Product_List_Activity.this,"I am here delete"+t.getMessage(), Toast.LENGTH_LONG).show();*/

            }
        });

    } //End of Delete Count


    //Show Fragment when customer adds item
    @Override
    public void showInFragment() {
        if (productListAdapter.getCartCount() > 0) {

            Count_Price_Show_Fragment count_price_show_fragment = new Count_Price_Show_Fragment();
            count_price_show_fragment.getCountPrice();
            fragmentManager.beginTransaction().replace(R.id.countPriceShow, count_price_show_fragment)
                    .show(count_price_show_fragment)
                    .commit();
        } else {
            Fragment cartView = fragmentManager.findFragmentById(R.id.countPriceShow);
            if (cartView != null)
                fragmentManager.beginTransaction().remove(cartView).commit();
        }

    }


    //To Display Each Product Description
    @Override
    public void onImageClick(String imageCode) {

        Intent productDescription = new Intent(Product_List_Activity.this, ProductDescriptionActivity.class);
        productDescription.putExtra("BRANDID_FOR_PRODUCT_DESC", brand_ID_For_ProductList);
        productDescription.putExtra("PRODUCT_CODE", imageCode);
        startActivity(productDescription);


    } //End of Each Product Description


    @Override
    public void OnProductItemClick(int position) {

    }

}



