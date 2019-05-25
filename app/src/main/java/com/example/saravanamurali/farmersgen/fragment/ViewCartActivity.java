package com.example.saravanamurali.farmersgen.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.crashlytics.android.Crashlytics;
import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.address.Add_Address_Activity;
import com.example.saravanamurali.farmersgen.address.ExistingAddressActivity;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.coupon.CouponActivity;
import com.example.saravanamurali.farmersgen.models.AddCartDTO;
import com.example.saravanamurali.farmersgen.models.CancelCouponDTO;
import com.example.saravanamurali.farmersgen.models.CurrentUserDTO;
import com.example.saravanamurali.farmersgen.models.GetDeliveryAddressDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseDeleteCartDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseUpdateCartDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseViewCartListDTO;
import com.example.saravanamurali.farmersgen.models.ViewCartDTO;
import com.example.saravanamurali.farmersgen.models.ViewCartUpdateDTO;
import com.example.saravanamurali.farmersgen.recyclerviewadapter.ViewCartAdapter;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientForDeleteItemInViewCart;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientForUpdateCountInViewCart;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientForViewCart;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToCancelCouponCode;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToGetExistingAddress;
import com.example.saravanamurali.farmersgen.signin.LoginActivityForViewCart;
import com.example.saravanamurali.farmersgen.sqllite.ProductAddInSqlLite;
import com.example.saravanamurali.farmersgen.tappedactivity.HomeActivity;
import com.example.saravanamurali.farmersgen.util.Network_config;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewCartActivity extends AppCompatActivity implements ViewCartAdapter.ViewCartUpdateInterface, ViewCartAdapter.ViewCartDeleteInterface {
    // Implements swipe interface
    //, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener

    static TextView toPayAmountTextView;
    // TextView toPayT;
    static String GrandTotal;
    static ViewCartAdapter viewCartAdapter;
    private static Context context = null;
    TextView proceedButton;


    RelativeLayout bottomView;

    // JSONResponseUpdateCartDTO jsonResponseUpdateCartDTO;
    //Coupon
    RelativeLayout showCouponLayout;

    Dialog dialog;

    //Coupon Applied
    RelativeLayout couponAppliedBlock;
    TextView couponCodeApplied;
    ImageView cancelCoupon;

    TextView applyCoupon;


    String addressID;
    String checked_MobileID;
    String checked_MobileNumber;
    String checked_Address;
    List<ViewCartDTO> viewCartDTOList;
    RecyclerView recyclerViewViewCart;
    String brand_ID_For_ProductList;
    String brand_Name_For_ProductList;
    String brand_Name_For_ProductRating;
    ImageView emptViewCartImage;

    CoordinatorLayout coordinatorLayout;
    int totalAmount = 0;
    String currentUser;

    private String NO_CURRENT_USER = "NO_CURRENT_USER";

    private String NO_CURRENT_COUPON_ID = "NO_CURRENT_COUPON_ID";

    private String NO_CURRENT_COUPON_CODE = "NO_CURRENT_COUPON_CODE";

    public ViewCartActivity(String curentUser) {
        this.currentUser = curentUser;

    }

    Toolbar toolbar;

    //SQLLite
    SQLiteDatabase mSqLiteDatabaseInViewCart;


    public ViewCartActivity() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        Fabric.with(ViewCartActivity.this, new Crashlytics());

        mSqLiteDatabaseInViewCart = openOrCreateDatabase(ProductAddInSqlLite.DATABASE_NAME, MODE_PRIVATE, null);

        //getSupportActionBar().hide();

        toolbar = (Toolbar) findViewById(R.id.viewCartToolBar);
        // toolbar.setTitle("Your Basket");

        setSupportActionBar(toolbar);


        //getSupportActionBar().setTitle("Your Basket");


        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
         getSupportActionBar().hide();
*/

        //viewcart_adapterview.xml
        context = getApplicationContext();
        dialog = new Dialog(context);

        //System.out.println("Current User at ViewCart Acitivy" + currentUser);
        Intent intent = getIntent();
        brand_ID_For_ProductList = intent.getStringExtra("BRAND_ID");
        brand_Name_For_ProductList = intent.getStringExtra("BRAND_NAME");
        brand_Name_For_ProductRating = intent.getStringExtra("BRAND_RATING");

        recyclerViewViewCart = (RecyclerView) findViewById(R.id.recyclerView_ViewCart);
        recyclerViewViewCart.setLayoutManager(new LinearLayoutManager(ViewCartActivity.this));
        recyclerViewViewCart.setHasFixedSize(true);

        proceedButton = (TextView) findViewById(R.id.viewCartProceed);
        toPayAmountTextView = (TextView) findViewById(R.id.toPayAmount);
        //coordinatorLayout = findViewById(R.id.coordinator_layout);

        bottomView = (RelativeLayout) findViewById(R.id.bottom);

        //toPayT=(TextView)findViewById(R.id.toPay);
        emptViewCartImage = (ImageView) findViewById(R.id.emptyViewCartImage);

        showCouponLayout = (RelativeLayout) findViewById(R.id.coupon);

        applyCoupon = (TextView) findViewById(R.id.applyCouponText);

        //Coupon Code Applied
        couponAppliedBlock = (RelativeLayout) findViewById(R.id.couponAppliedBlock);
        couponCodeApplied = (TextView) findViewById(R.id.couponCode);
        cancelCoupon = (ImageView) findViewById(R.id.couponCodeCancel);


        cancelCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Network_config.is_Network_Connected_flag(context)) {
                    deleteCouponCode();
                } else {
                    Network_config.customAlert(dialog, context, getResources().getString(R.string.app_name),
                            getResources().getString(R.string.connection_message));
                }
            }
        });


        viewCartDTOList = new ArrayList<ViewCartDTO>();

        viewCartAdapter = new ViewCartAdapter(ViewCartActivity.this, viewCartDTOList);
        recyclerViewViewCart.setAdapter(viewCartAdapter);

        viewCartAdapter.setViewCartUpdateInterface(ViewCartActivity.this);
        viewCartAdapter.setViewCartDeleteInterface(ViewCartActivity.this);

        //viewCartAdapter.setUpdateTotalPrice();

        // System.out.println("I am inside ViewCartActivity");

        //loadViewCartProductList();


        SharedPreferences getCouponID = getSharedPreferences("CURRENT_COUPON_ID", Context.MODE_PRIVATE);
        String curUser_CouponID = getCouponID.getString("COUPONID", "NO_CURRENT_COUPON_ID");

        SharedPreferences getCouponCODE = getSharedPreferences("CURRENT_COUPON_CODE", Context.MODE_PRIVATE);
        String curUser_Coupon_CODE = getCouponCODE.getString("COUPON_CODE", "NO_CURRENT_COUPON_CODE");

        if (curUser_CouponID.equals(NO_CURRENT_COUPON_ID) && curUser_Coupon_CODE.equals(NO_CURRENT_COUPON_CODE)) {
            showCouponLayout.setVisibility(View.VISIBLE);
            couponAppliedBlock.setVisibility(View.GONE);
            applyCoupon.setVisibility(View.VISIBLE);

        }


        if (curUser_CouponID.equals(NO_CURRENT_COUPON_ID)) {

            if (Network_config.is_Network_Connected_flag(context)) {

                loadViewCartProductList();
                showCouponLayout.setVisibility(View.VISIBLE);
                couponAppliedBlock.setVisibility(View.GONE);


            } else {
                Network_config.customAlert(dialog, context, getResources().getString(R.string.app_name),
                        getResources().getString(R.string.connection_message));
            }


        } else if (!curUser_CouponID.equals(NO_CURRENT_COUPON_ID)) {

            if (Network_config.is_Network_Connected_flag(context)) {

                loadViewCartProductListWithCouponID();


                showCouponLayout.setVisibility(View.GONE);
                couponAppliedBlock.setVisibility(View.VISIBLE);
                couponCodeApplied.setText(curUser_Coupon_CODE);
            } else {
                Network_config.customAlert(dialog, context, getResources().getString(R.string.app_name),
                        getResources().getString(R.string.connection_message));
            }

        }

        if (Network_config.is_Network_Connected_flag(context)) {

            //Get addressID for Existing User
            getAddressID();

        } else {
            Network_config.customAlert(dialog, context, getResources().getString(R.string.app_name),
                    getResources().getString(R.string.connection_message));
        }

        //offer block cliked
        showCouponLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Network_config.is_Network_Connected_flag(context)) {
                    proceedOffers();
                } else {
                    Network_config.customAlert(dialog, context, getResources().getString(R.string.app_name),
                            getResources().getString(R.string.connection_message));
                }
            }
        });


        //PROCEED Button Pressed in View Cart Activity
        bottomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog csprogress;
                csprogress = new ProgressDialog(ViewCartActivity.this);
                csprogress.setMessage("Loading...");
                csprogress.show();
                csprogress.setCancelable(false);
                csprogress.setCanceledOnTouchOutside(false);


                // Toast.makeText(ViewCartActivity.this, "Checking Proceed Button", Toast.LENGTH_LONG).show();

                /*String DEVICE_ID_AT_VIEWCART = Settings.Secure.getString(ViewCartActivity.this.getContentResolver(),
                        Settings.Secure.ANDROID_ID);*/

                /*SharedPreferences getCurrentUser=getSharedPreferences("CURRENT_USER",MODE_PRIVATE);
                SharedPreferences.Editor removeEditor = getCurrentUser.edit();
                removeEditor.remove("CURRENTUSER");
                removeEditor.commit();*/

                //Getting Current User
                SharedPreferences getCurrentUser = getSharedPreferences("CURRENT_USER", Context.MODE_PRIVATE);
                String curUser = getCurrentUser.getString("CURRENTUSER", "NO_CURRENT_USER");
                System.out.println("Current User at ViewCart Activity" + curUser);

                //Getting Address ID

                //User not loggedIN yet
                if (curUser.equals(NO_CURRENT_USER)) {

                    // Toast.makeText(ViewCartActivity.this, "No Current Cuser", Toast.LENGTH_LONG).show();

                    Intent registerUserAtViewCartActivity = new Intent(ViewCartActivity.this, LoginActivityForViewCart.class);
                    startActivity(registerUserAtViewCartActivity);
                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }

                } else if (!curUser.equals(NO_CURRENT_USER) && addressID != null) {

                    //Toast.makeText(ViewCartActivity.this, "He is Current Cuser", Toast.LENGTH_LONG).show();

                    Intent deliveryAddressActivity = new Intent(ViewCartActivity.this, ExistingAddressActivity.class);
                    deliveryAddressActivity.putExtra("CURRENTUSER", curUser);
                    startActivity(deliveryAddressActivity);
                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }
                } else if (!curUser.equals(NO_CURRENT_USER) && addressID == null) {

                    // Toast.makeText(ViewCartActivity.this, "He is Current Cuser", Toast.LENGTH_LONG).show();

                    Intent addAddressActivity = new Intent(ViewCartActivity.this, Add_Address_Activity.class);
                    startActivity(addAddressActivity);
                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }
                }


            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                //int swipeDeleteAdapterPosition=viewHolder.getAdapterPosition();

                //ViewCartDTO swipteDeleteViewCartDTO  =viewCartDTOList.get(swipeDeleteAdapterPosition);
              // String productCode= swipteDeleteViewCartDTO.getProduct_Code();

                viewCartAdapter.removeItem1(viewHolder.getAdapterPosition());



            }
        }).attachToRecyclerView(recyclerViewViewCart);

        /*ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerViewViewCart);


        // making http call and fetching menu json


        ItemTouchHelper.SimpleCallback itemTouchHelperCallback1 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Row is swiped from recycler view
                // remove it from adapter
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        // attaching the touch helper to recycler view
        new ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(recyclerViewViewCart);
*/
    }


    //Cancel coupon Code
    private void deleteCouponCode() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(ViewCartActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api = APIClientToCancelCouponCode.getApiInterfaceToCancelCouponCode();

        //Current User
        SharedPreferences getCurrentUser_ForDeleteCoupon = getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
        String curUserID_ForDeleteCoupon = getCurrentUser_ForDeleteCoupon.getString("CURRENTUSER", "NO_CURRENT_USER");

        //Current Coupon ID
        SharedPreferences getCouponID = getSharedPreferences("CURRENT_COUPON_ID", MODE_PRIVATE);
        String curUserCouponID = getCouponID.getString("COUPONID", "NO_CURRENT_COUPON_ID");


        //Current Coupon CODE
        SharedPreferences getCouponCODE = getSharedPreferences("CURRENT_COUPON_CODE", MODE_PRIVATE);
        String curUserCouponCODE = getCouponCODE.getString("COUPON_CODE", "NO_CURRENT_COUPON_CODE");


        CancelCouponDTO cancelCouponDTO = new CancelCouponDTO(curUserID_ForDeleteCoupon, curUserCouponID);

        Call<ResponseBody> call = api.cancelCoupon(cancelCouponDTO);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    //Thread to slow the process
                        /*ProgressThread progressThread=new ProgressThread();
                        progressThread.run();
*/

                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }

                    //Remove Current User COUPON ID From Shared Preferences
                    SharedPreferences getCurrentUser_CouponID = getSharedPreferences("CURRENT_COUPON_ID", context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = getCurrentUser_CouponID.edit();
                    editor.remove("COUPONID");
                    editor.commit();


                    //Remove Current User COUPON CODE From Shared Preferences
                    SharedPreferences getCurrentUser_CouponCODE = getSharedPreferences("CURRENT_COUPON_CODE", context.MODE_PRIVATE);
                    SharedPreferences.Editor editorCode = getCurrentUser_CouponCODE.edit();
                    editorCode.remove("COUPON_CODE");
                    editorCode.commit();

                    showCouponLayout.setVisibility(View.VISIBLE);
                    couponAppliedBlock.setVisibility(View.GONE);

                    loadViewCartProductList();


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

    private void proceedOffers() {

        //Getting Current User
        SharedPreferences getCurrentUser = getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
        String curUserToCheckOffer = getCurrentUser.getString("CURRENTUSER", "NO_CURRENT_USER");

        if (!curUserToCheckOffer.equals(NO_CURRENT_USER)) {

            Intent coupon = new Intent(ViewCartActivity.this, CouponActivity.class);
            startActivity(coupon);
        } else {

            Toast toast = Toast.makeText(ViewCartActivity.this, "Please login to get offer. To login Click on  CheckOut Button", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER, 0, 0);
            toast.show();

        }

    }


    //To Display list of ordered items in ViewCart Avtivity From ProductList Activity without COUPONID
    public void loadViewCartProductList() {


        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(ViewCartActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        String ANDROID_MOBILE_ID = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        // Toast.makeText(ViewCartActivity.this, "ViewCartResponseSuccessFirst", Toast.LENGTH_LONG).show();

        // System.out.println("I am Here" + ANDROID_MOBILE_ID);

        ApiInterface api = APIClientForViewCart.getApiInterfaceForViewCart();
        AddCartDTO loadFragment = new AddCartDTO(ANDROID_MOBILE_ID);
        Call<JSONResponseViewCartListDTO> call = api.getViewCart(loadFragment);

        call.enqueue(new Callback<JSONResponseViewCartListDTO>() {
            @Override
            public void onResponse(Call<JSONResponseViewCartListDTO> call, Response<JSONResponseViewCartListDTO> response) {
                // System.out.println("Null Values");

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }


                JSONResponseViewCartListDTO jsonResponseViewCartListDTO = response.body();
                List<ViewCartDTO> viewCartProductListDTO = jsonResponseViewCartListDTO.getViewCartListRecord();

                if (viewCartProductListDTO != null) {

                    GrandTotal = jsonResponseViewCartListDTO.getGrandTotal();
                    System.out.println("GRANDTOTAL" + GrandTotal);


                    viewCartAdapter.setData(viewCartProductListDTO);

                    viewCartAdapter.notifyDataSetChanged();


                    toPayAmountTextView.setText(GrandTotal);


                } else if (viewCartProductListDTO == null) {
                    MenuAccountFragment menuAccountFragment = new MenuAccountFragment();
                    menuAccountFragment.clearAllItemFromSQLDataBase();
                    startActivity(new Intent(ViewCartActivity.this, HomeActivity.class));
                    Toast.makeText(ViewCartActivity.this, "You havent ordered any item in cart", Toast.LENGTH_LONG).show();
                    finish();
                }
            }


            @Override
            public void onFailure(Call<JSONResponseViewCartListDTO> call, Throwable t) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }


            }
        });


    }

    //To Display list of ordered items in ViewCart Avtivity From ProductList Activity with COUPONID
    private void loadViewCartProductListWithCouponID() {


        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(ViewCartActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);

        SharedPreferences getCouponID = getSharedPreferences("CURRENT_COUPON_ID", Context.MODE_PRIVATE);
        String curUser_CouponID = getCouponID.getString("COUPONID", "NO_CURRENT_COUPON_ID");

        //Getting Current User
        SharedPreferences getCurrentUser = getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
        String curUserToGetOffer = getCurrentUser.getString("CURRENTUSER", "NO_CURRENT_USER");


        String ANDROID_MOBILE_ID = Settings.Secure.getString(ViewCartActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        ApiInterface api = APIClientForViewCart.getApiInterfaceForViewCart();

        AddCartDTO loadViewCartWithCouponID = new AddCartDTO(ANDROID_MOBILE_ID, curUser_CouponID, curUserToGetOffer);
        Call<JSONResponseViewCartListDTO> call = api.getViewCartWithCouponID(loadViewCartWithCouponID);

        call.enqueue(new Callback<JSONResponseViewCartListDTO>() {
            @Override
            public void onResponse(Call<JSONResponseViewCartListDTO> call, Response<JSONResponseViewCartListDTO> response) {


                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

                JSONResponseViewCartListDTO jsonResponseViewCartListDTO = response.body();
                List<ViewCartDTO> viewCartProductListDTO = jsonResponseViewCartListDTO.getViewCartListRecord();

                GrandTotal = jsonResponseViewCartListDTO.getGrandTotal();
                System.out.println("GRANDTOTAL" + GrandTotal);


                viewCartAdapter.setData(viewCartProductListDTO);

                viewCartAdapter.notifyDataSetChanged();


                toPayAmountTextView.setText(GrandTotal);


            }

            @Override
            public void onFailure(Call<JSONResponseViewCartListDTO> call, Throwable t) {

                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

            }
        });


    }

    //Method 1
//Okhttp3
    @Override
    public void viewCartUpdateInterface(int updateCount, String updateProductCode, String prouctPrice) {
        String ANDROID_MOBILE_ID = Settings.Secure.getString(ViewCartActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);


        OkHttpClient client = new OkHttpClient();
        OkHttpClient httpClient = new OkHttpClient();

        String url = "http://farmersgen.com/service/cart/update_cart.php";

        MediaType json = MediaType.parse("application/json;charset=utf-8");
        JSONObject actualData = new JSONObject();
        String s = String.valueOf(updateCount);
        try {
            actualData.put("product_code", updateProductCode);
            actualData.put("count", s);
            actualData.put("product_price", prouctPrice);
            actualData.put("device_id", ANDROID_MOBILE_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        RequestBody requestBody = RequestBody.create(json, actualData.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .header("Content-Type", "application/json")
                .header("Connection", "close")
                .build();

        okhttp3.Response response = null;

        try {
            response = httpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                Log.e("", "Got response from server for JSON post using OkHttp ");

                //Log.d("res", response.body().string().toString());

                String result_response = response.body().string().toString();
                try {


                    JSONObject myjson = new JSONObject(result_response);

                    int status = myjson.getInt("responsecode");
                    String msg = myjson.getString("message");
                    if (status == 200) {

                        String producode = myjson.getString("product_code");
                        String count = myjson.getString("count");
                        String totalPRice = myjson.getString("total_price");
                        String grandTotal = myjson.getString("grand_total");

                        for (int i = 0; i < 1; i++) {

                            JSONResponseUpdateCartDTO jsonResponseUpdateCartDTO = new JSONResponseUpdateCartDTO(producode, count, totalPRice, ANDROID_MOBILE_ID, grandTotal);
                            viewCartAdapter.setUpdateTotalPrice(jsonResponseUpdateCartDTO);

                        }


                        GrandTotal = grandTotal;

                        toPayAmountTextView.setText(GrandTotal);
                        viewCartAdapter.notifyDataSetChanged();


                        System.out.println("UpdateOKHTTP" + producode + " " + count + " " + totalPRice + " " + grandTotal);

                        Log.d("okk", "" + producode + " " + count + " " + totalPRice + " " + grandTotal);

                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(getApplicationContext(), "failed to get response", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            Log.e("", "error in getting response for json post request okhttp");
        }


    }

    //Update Count in ViewCart
    /*@Override
    public void viewCartUpdateInterface(int viewCartCount, String viewCartProductCode, String
            viewCartProductPrice) {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(ViewCartActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);

        String ANDROID_MOBILE_ID = Settings.Secure.getString(ViewCartActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        String count = String.valueOf(viewCartCount);
        ViewCartUpdateDTO viewCartUpdateDTO = new ViewCartUpdateDTO(count, viewCartProductCode, viewCartProductPrice, ANDROID_MOBILE_ID);

        ApiInterface api = APIClientForUpdateCountInViewCart.getAPIClientForUpdateCountInViewCartInterface();

        Call<JSONResponseUpdateCartDTO> call = api.updateCountatAtViewCart(viewCartUpdateDTO);

        call.enqueue(new Callback<JSONResponseUpdateCartDTO>() {
            @Override
            public void onResponse(Call<JSONResponseUpdateCartDTO> call, Response<JSONResponseUpdateCartDTO> response) {


                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }
                JSONResponseUpdateCartDTO jsonResponseUpdateCartDTO = response.body();

                if (jsonResponseUpdateCartDTO.getStatus() == 200) {

                    viewCartAdapter.setUpdateTotalPrice(jsonResponseUpdateCartDTO);

                    GrandTotal = jsonResponseUpdateCartDTO.getGrandTotal();

                    toPayAmountTextView.setText(GrandTotal);
                    viewCartAdapter.notifyDataSetChanged();


                } else if (jsonResponseUpdateCartDTO.getStatus() == 500) {
                    Toast.makeText(ViewCartActivity.this, "Cart was not Updated", Toast.LENGTH_LONG).show();
                }


            }


            @Override
            public void onFailure(Call<JSONResponseUpdateCartDTO> call, Throwable t) {
                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

                //Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }//// End of Update Count in ViewCart
*/
    @Override
    public void viewCartUpdateInterfaceSqlLite(int viewCartCount, String viewCartProductCode, String viewCart_Price) {
        String device_id = Settings.Secure.getString(ViewCartActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        String u_View_Count = String.valueOf(viewCartCount);

        int u_View_ProductPrice = Integer.parseInt(viewCart_Price);
        int productMulPrice = viewCartCount * u_View_ProductPrice;
        String u_ViewCart_totalPrice = String.valueOf(productMulPrice);

        String u_query = "UPDATE add_cart SET count=?, total_price=? where product_code=? and device_id =? ";

        mSqLiteDatabaseInViewCart.execSQL(u_query, new String[]{u_View_Count, u_ViewCart_totalPrice, viewCartProductCode, device_id});

        //Toast.makeText(ViewCartActivity.this, " ViewCart Updated", Toast.LENGTH_LONG).show();


    }

    //Delete Count in ViewCart when it reaches zero
    @Override
    public void viewCartDeleteInterface(String produceDecCode) {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(ViewCartActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);
        String ANDROID_MOBILE_ID = Settings.Secure.getString(ViewCartActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        ApiInterface api = APIClientForDeleteItemInViewCart.getApiInterfaceForDeleteItemFromViewCart();

        ViewCartUpdateDTO viewCartUpdateDTO = new ViewCartUpdateDTO(produceDecCode, ANDROID_MOBILE_ID);

        Call<JSONResponseDeleteCartDTO> call = api.deleteItemFromViewCart(viewCartUpdateDTO);

        call.enqueue(new Callback<JSONResponseDeleteCartDTO>() {
            @Override
            public void onResponse(Call<JSONResponseDeleteCartDTO> call, Response<JSONResponseDeleteCartDTO> response) {


                JSONResponseDeleteCartDTO jsonResponseDeleteCartDTO = response.body();


                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }


                GrandTotal = jsonResponseDeleteCartDTO.getDeleteGrandTotal();

                int toPayCheck = 0;
                if (GrandTotal == null) {
                    toPayCheck = 0;
                } else {
                    toPayCheck = Integer.parseInt(GrandTotal);
                }
                if (toPayCheck > 0) {

                    toPayAmountTextView.setText(GrandTotal);
                    bottomView.setVisibility(View.VISIBLE);
                } else {
                    toPayAmountTextView.setText("");
                    // toPayT.setVisibility(View.GONE);
                    showCouponLayout.setVisibility(View.GONE);
                    emptViewCartImage.setVisibility(View.VISIBLE);
                    proceedButton.setVisibility(View.GONE);
                    bottomView.setVisibility(View.GONE);
                    couponAppliedBlock.setVisibility(View.GONE);
                    toolbar.setVisibility(View.GONE);

                    viewCartAdapter.notifyDataSetChanged();
                    //Removes the couponID

                    //Remove Current User COUPON ID From Shared Preferences
                    SharedPreferences getCurrentUser_CouponID = getSharedPreferences("CURRENT_COUPON_ID", context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = getCurrentUser_CouponID.edit();
                    editor.remove("COUPONID");
                    editor.commit();


                    //Remove Current User COUPON CODE From Shared Preferences
                    SharedPreferences getCurrentUser_CouponCODE = getSharedPreferences("CURRENT_COUPON_CODE", context.MODE_PRIVATE);
                    SharedPreferences.Editor editorCode = getCurrentUser_CouponCODE.edit();
                    editorCode.remove("COUPON_CODE");
                    editorCode.commit();


                }

                viewCartAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<JSONResponseDeleteCartDTO> call, Throwable t) {
                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

                // Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    } //End of Delete Count in ViewCart when it reaches zero

    @Override
    public void viewCartDeleteInterfaceSqlLite(String viewCartDecProductCode) {

        String delete_device_id = Settings.Secure.getString(ViewCartActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);


        String delete = "delete from add_cart where product_code=? and device_id=? ";

        mSqLiteDatabaseInViewCart.execSQL(delete, new String[]{viewCartDecProductCode, delete_device_id});

        //Toast.makeText(ViewCartActivity.this, "Deleted", Toast.LENGTH_LONG).show();


    }

    //Get AddressID from Existing User
    private void getAddressID() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(ViewCartActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api = APIClientToGetExistingAddress.getAPIInterfaceTOGetExistingAddress();

        SharedPreferences getCurrentUser = getSharedPreferences("CURRENT_USER", MODE_PRIVATE);

        String curUser = getCurrentUser.getString("CURRENTUSER", "NO_CURRENT_USER");

        CurrentUserDTO currentUserDTO = new CurrentUserDTO(curUser);

        Call<GetDeliveryAddressDTO> call = api.getExistingAddress(currentUserDTO);

        call.enqueue(new Callback<GetDeliveryAddressDTO>() {
            @Override
            public void onResponse(Call<GetDeliveryAddressDTO> call, Response<GetDeliveryAddressDTO> response) {


                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

                GetDeliveryAddressDTO getDeliveryAddressDTO = response.body();

                addressID = getDeliveryAddressDTO.getAddressID();

                SharedPreferences sharedPreferences = getSharedPreferences("ADDRESS_ID", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("ADDRESSID", addressID);
                editor.commit();


                //System.out.println("CUreent user Address ID" + addressID);


            }

            @Override
            public void onFailure(Call<GetDeliveryAddressDTO> call, Throwable t) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

            }
        });

    }


    /* public void offers(View view) {
        Toast.makeText(ViewCartActivity.this, "Offers Clicked", Toast.LENGTH_LONG).show();
    }*/

   /* @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof ViewCartAdapter.ViewCartHolder) {
            // get the removed item name to display it in snack bar
            // String name = viewCartDTOList.get(viewHolder.getAdapterPosition()).getProduct_Name();

            // backup of removed item for undo purpose
          *//*  final ViewCartDTO deletedItem = viewCartDTOList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();*//*

            // remove the item from recycler view

            Log.d("POS1", "" + viewHolder.getAdapterPosition());
            viewCartAdapter.removeItem1(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
           *//* Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);*//*
     *//*  snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    viewCartAdapter.restoreItem(deletedItem, deletedIndex);
                }
            });*//*
     *//*snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();*//*
        }
    }*/
}

