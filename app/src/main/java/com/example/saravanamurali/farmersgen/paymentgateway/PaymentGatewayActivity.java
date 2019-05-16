package com.example.saravanamurali.farmersgen.paymentgateway;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.models.AddCartDTO;
import com.example.saravanamurali.farmersgen.models.CardPaymentDTO;
import com.example.saravanamurali.farmersgen.models.CurrentUserDTO;
import com.example.saravanamurali.farmersgen.models.GetDeliveryAddressDTO;
import com.example.saravanamurali.farmersgen.models.GetOrdersUsingDeviceID_DTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseProfileEdit;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseViewCartListDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseViewCartOrdersatPaymentGateway;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonOrderResponse;
import com.example.saravanamurali.farmersgen.models.OrderDTO;
import com.example.saravanamurali.farmersgen.models.OrderDetailsDTO;
import com.example.saravanamurali.farmersgen.models.SendOrderConfirmationSMSDTO;
import com.example.saravanamurali.farmersgen.models.ViewCartDTO;
import com.example.saravanamurali.farmersgen.models.ViewCartPaymentGatewayDTO;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientForProfileEdit;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientForViewCart;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToGetExistingAddress;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToOrder;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToSendOrderConfirmationSMS;
import com.example.saravanamurali.farmersgen.retrofitclient.ApiClientForPaymentGateway;
import com.example.saravanamurali.farmersgen.sqllite.ProductAddInSqlLite;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentGatewayActivity extends AppCompatActivity {

    String addressID;
    String curUser;

    List<OrderDetailsDTO> orderDetailsList;

    //After amount deduction from offer
    String grandTotal = "";

    Button payButton;

    TextView cashOnDeliveryPay;

    private String NO_CURRENT_COUPON_ID = "NO_CURRENT_COUPON_ID";

    RadioGroup radioGroupForCOD;
    RadioButton radioButtonForCOD;

    RadioGroup radioGroupForCardPayment;
    RadioButton radioButtonForCardPayment;

    TextView codText;
    TextView netBankingText;
    Button cardPaymentProceedButton;

    String userName;
    String userMobileNo;
    String userEmail;

    //SQLLite
    SQLiteDatabase mSqLiteDatabaseInPaymentGateway;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);

        mSqLiteDatabaseInPaymentGateway = openOrCreateDatabase(ProductAddInSqlLite.DATABASE_NAME, MODE_PRIVATE, null);

        payButton = (Button) findViewById(R.id.payButton);

        cashOnDeliveryPay = (TextView) findViewById(R.id.grandTotalAtPaymentGateway);

        radioGroupForCOD = (RadioGroup) findViewById(R.id.radioGroupCOD);
        radioGroupForCardPayment = (RadioGroup) findViewById(R.id.radioGroupCardPayment);

        codText = (TextView) findViewById(R.id.codText);
        netBankingText = (TextView) findViewById(R.id.netBankingText);
        cardPaymentProceedButton = (Button) findViewById(R.id.netBankingProceedButton);

        getUserDetailsForPaymentGateway();

        cardPaymentProceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCardPayment();
            }
        });


        getAddressID();

        //If coupon is applied we need to get discount GrandTotal
        SharedPreferences getCouponID = getSharedPreferences("CURRENT_COUPON_ID", Context.MODE_PRIVATE);
        String curUser_CouponID_To_Get_GrandTotal = getCouponID.getString("COUPONID", "NO_CURRENT_COUPON_ID");

        if (curUser_CouponID_To_Get_GrandTotal.equals(NO_CURRENT_COUPON_ID)) {
            loadViewCartProductList();
        } else if (!curUser_CouponID_To_Get_GrandTotal.equals(NO_CURRENT_COUPON_ID)) {

            loadViewCartProductList_With_Dicount_Price();

        }


        orderDetailsList = new ArrayList<OrderDetailsDTO>();


    }

    //Get Current User Details
    private void getUserDetailsForPaymentGateway() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(PaymentGatewayActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        //Getting Current User
        SharedPreferences getCurrentUser = getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
        String curUser_ForPaymentGatway = getCurrentUser.getString("CURRENTUSER", "NO_CURRENT_USER");

        ApiInterface api = APIClientForProfileEdit.getApiInterfaceToEditProfile();

        CurrentUserDTO currentUserDTO = new CurrentUserDTO(curUser_ForPaymentGatway);

        Call<JSONResponseProfileEdit> call = api.editProfile(currentUserDTO);

        call.enqueue(new Callback<JSONResponseProfileEdit>() {
            @Override
            public void onResponse(Call<JSONResponseProfileEdit> call, Response<JSONResponseProfileEdit> response) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

                JSONResponseProfileEdit jsonResponseProfileEdit = response.body();

                userName = jsonResponseProfileEdit.getProfileName();
                userMobileNo = jsonResponseProfileEdit.getProfileMobileNo();
                userEmail = jsonResponseProfileEdit.getProfileEmail();
            }

            @Override
            public void onFailure(Call<JSONResponseProfileEdit> call, Throwable t) {
                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

            }
        });


    }

    //Proceed Card Payment Details
    private void loadCardPayment() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(PaymentGatewayActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api = ApiClientForPaymentGateway.getApiInterfaceForCardPayment();
        // int price=Integer.parseInt(grandTotal);
        CardPaymentDTO cardPaymentDTO = new CardPaymentDTO(grandTotal, userName, userEmail, userMobileNo);
        Call<ResponseBody> call = api.doCardPayment(cardPaymentDTO);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(csprogress.isShowing()){
                    csprogress.dismiss();
                }

                //Toast.makeText(PaymentGatewayActivity.this,"Success",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });



    }

    private void loadViewCartProductList_With_Dicount_Price() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(PaymentGatewayActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);

        SharedPreferences getCouponID = getSharedPreferences("CURRENT_COUPON_ID", Context.MODE_PRIVATE);
        String curUser_CouponID_ForPaymentGatway = getCouponID.getString("COUPONID", "NO_CURRENT_COUPON_ID");

        //Getting Current User
        SharedPreferences getCurrentUser = getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
        String curUserToGetOffer_ForPaymentGatway = getCurrentUser.getString("CURRENTUSER", "NO_CURRENT_USER");

        String ANDROID_MOBILE_ID = Settings.Secure.getString(PaymentGatewayActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        ApiInterface api = APIClientForViewCart.getApiInterfaceForViewCart();

        AddCartDTO loadViewCartWithCouponID_OfferPrice = new AddCartDTO(ANDROID_MOBILE_ID, curUser_CouponID_ForPaymentGatway, curUserToGetOffer_ForPaymentGatway);
        Call<JSONResponseViewCartListDTO> call = api.getViewCartWithCouponID(loadViewCartWithCouponID_OfferPrice);

        call.enqueue(new Callback<JSONResponseViewCartListDTO>() {
            @Override
            public void onResponse(Call<JSONResponseViewCartListDTO> call, Response<JSONResponseViewCartListDTO> response) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

                JSONResponseViewCartListDTO jsonResponseViewCartListDTO = response.body();


                List<ViewCartDTO> viewCartProductListDTO = jsonResponseViewCartListDTO.getViewCartListRecord();

                grandTotal = jsonResponseViewCartListDTO.getGrandTotal();


                String ANDROID_MOBILE_ID = Settings.Secure.getString(PaymentGatewayActivity.this.getContentResolver(),
                        Settings.Secure.ANDROID_ID);

                for (int i = 0; i < viewCartProductListDTO.size(); i++) {

                    String pay_ProductCode = viewCartProductListDTO.get(i).getProduct_Code();
                    String pay_ProductCount = viewCartProductListDTO.get(i).getCount();
                    String pay_ProductPrice = viewCartProductListDTO.get(i).getTotal_price();

                    OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(pay_ProductCode, pay_ProductCount, pay_ProductPrice, ANDROID_MOBILE_ID);
                    orderDetailsList.add(orderDetailsDTO);

                }

                cashOnDeliveryPay.setText(grandTotal);

            }

            @Override
            public void onFailure(Call<JSONResponseViewCartListDTO> call, Throwable t) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }
            }
        });


    }

    //Getting ordered items from addcart table using deviceID
    private void loadViewCartProductList() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(PaymentGatewayActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        String ANDROID_MOBILE_ID = Settings.Secure.getString(PaymentGatewayActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        ApiInterface api = APIClientForViewCart.getApiInterfaceForViewCart();
        GetOrdersUsingDeviceID_DTO loadFragment = new GetOrdersUsingDeviceID_DTO(ANDROID_MOBILE_ID);
        Call<JSONResponseViewCartOrdersatPaymentGateway> call = api.getViewCartOrdersatPaymentGateway(loadFragment);

        call.enqueue(new Callback<JSONResponseViewCartOrdersatPaymentGateway>() {
            @Override
            public void onResponse(Call<JSONResponseViewCartOrdersatPaymentGateway> call, Response<JSONResponseViewCartOrdersatPaymentGateway> response) {


                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }

                    JSONResponseViewCartOrdersatPaymentGateway jsonResponseViewCartListDTO = response.body();
                    List<ViewCartPaymentGatewayDTO> viewCartProductListDTO = jsonResponseViewCartListDTO.getViewCartListRecord();

                    grandTotal = jsonResponseViewCartListDTO.getGrandTotal();

                    String ANDROID_MOBILE_ID = Settings.Secure.getString(PaymentGatewayActivity.this.getContentResolver(),
                            Settings.Secure.ANDROID_ID);

                    for (int i = 0; i < viewCartProductListDTO.size(); i++) {

                        String pay_ProductCode = viewCartProductListDTO.get(i).getProduct_Code();
                        String pay_ProductCount = viewCartProductListDTO.get(i).getCount();
                        String pay_ProductPrice = viewCartProductListDTO.get(i).getTotal_price();

                        OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(pay_ProductCode, pay_ProductCount, pay_ProductPrice, ANDROID_MOBILE_ID);
                        orderDetailsList.add(orderDetailsDTO);

                    }



                cashOnDeliveryPay.setText(grandTotal);

            }

            @Override
            public void onFailure(Call<JSONResponseViewCartOrdersatPaymentGateway> call, Throwable t) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

            }
        });


    }



    //Cash On Delivery Payment
    public void payOnclick(View view) {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(PaymentGatewayActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api = APIClientToOrder.getAPIInterfaceToOrder();

        String type = "1";

        OrderDTO orderDTO = new OrderDTO(curUser, addressID, type, grandTotal, orderDetailsList);


        Call<JsonOrderResponse> call = api.order(orderDTO);

        call.enqueue(new Callback<JsonOrderResponse>() {
            @Override
            public void onResponse(Call<JsonOrderResponse> call, Response<JsonOrderResponse> response) {


                    if (csprogress.isShowing()) {
                        csprogress.dismiss();

                    }

                    JsonOrderResponse jsonOrderResponse = response.body();

                    if (jsonOrderResponse.getResponseCode() == 200) {

                        String orderIDToSendSMS = jsonOrderResponse.getOrderId();

                        //Remove Current User COUPON ID From Shared Preferences
                        SharedPreferences getCurrentUser_CouponID = getSharedPreferences("CURRENT_COUPON_ID", MODE_PRIVATE);
                        SharedPreferences.Editor editor = getCurrentUser_CouponID.edit();
                        editor.remove("COUPONID");
                        editor.commit();


                        //Remove Current User COUPON CODE From Shared Preferences
                        SharedPreferences getCurrentUser_CouponCODE = getSharedPreferences("CURRENT_COUPON_CODE", MODE_PRIVATE);
                        SharedPreferences.Editor editorCode = getCurrentUser_CouponCODE.edit();
                        editorCode.remove("COUPON_CODE");
                        editorCode.commit();



                        orderConfirmaationSMSToCustomer(orderIDToSendSMS);

                        clearAllItemsFromSQLDataBase();

                        Intent thanksActivity = new Intent(PaymentGatewayActivity.this, ThanksActivity.class);
                        startActivity(thanksActivity);
                        finish();


                    } else if (jsonOrderResponse.getResponseCode() == 500) {
                        //System.out.println("Order is not Confirmed");
                    }
                    //System.out.println("Order Confirmed");



            }

            @Override
            public void onFailure(Call<JsonOrderResponse> call, Throwable t) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

                System.out.println("Order is not Confirmed");

            }
        });
    }

    private void clearAllItemsFromSQLDataBase() {

        String delete_device_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        String delete = "delete from add_cart where device_id=? ";

        mSqLiteDatabaseInPaymentGateway.execSQL(delete, new String[]{delete_device_id});
    }


    public void checkRadioButtonForCashOnDelivery(View view) {

        int radioIdForCOD = radioGroupForCOD.getCheckedRadioButtonId();
        radioButtonForCOD = findViewById(radioIdForCOD);

        codText.setVisibility(View.VISIBLE);
        netBankingText.setVisibility(View.GONE);
        cardPaymentProceedButton.setVisibility(View.GONE);
        payButton.setVisibility(View.VISIBLE);


    }

    public void checkRadioButtonForCardPayment(View view) {

        int radioIDForCardPayment = radioGroupForCardPayment.getCheckedRadioButtonId();
        radioButtonForCardPayment = findViewById(radioIDForCardPayment);

        codText.setVisibility(View.GONE);
        netBankingText.setVisibility(View.VISIBLE);
        cardPaymentProceedButton.setVisibility(View.VISIBLE);
        payButton.setVisibility(View.GONE);
    }

    private void orderConfirmaationSMSToCustomer(String orderIDToSendSMS) {

        /*final ProgressDialog csprogress;
        csprogress = new ProgressDialog(PaymentGatewayActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);
*/

        ApiInterface api = APIClientToSendOrderConfirmationSMS.getApiInterfaceToSendOrderConfirmationSMS();

        SendOrderConfirmationSMSDTO sendOrderConfirmationSMSDTO = new SendOrderConfirmationSMSDTO(orderIDToSendSMS, curUser);

        Call<ResponseBody> call = api.sendOrderConfirmationSMS(sendOrderConfirmationSMSDTO);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

               /* if(csprogress.isShowing()){
                    csprogress.dismiss();
                }*/

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                /*if(csprogress.isShowing()){
                    csprogress.dismiss();
                }*/

            }
        });


    }

    //Getting addressId for that current user
    private void getAddressID() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(PaymentGatewayActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);

        ApiInterface api = APIClientToGetExistingAddress.getAPIInterfaceTOGetExistingAddress();

        SharedPreferences getCurrentUser = getSharedPreferences("CURRENT_USER", MODE_PRIVATE);

        curUser = getCurrentUser.getString("CURRENTUSER", "NO_CURRENT_USER");

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



            }

            @Override
            public void onFailure(Call<GetDeliveryAddressDTO> call, Throwable t) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

            }
        });

    }



}