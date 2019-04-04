package com.example.saravanamurali.farmersgen.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.models.CurrentUserDTO;
import com.example.saravanamurali.farmersgen.models.GetDeliveryAddressDTO;
import com.example.saravanamurali.farmersgen.models.GetOrdersUsingDeviceID_DTO;
import com.example.saravanamurali.farmersgen.models.JSONResponseViewCartOrdersatPaymentGateway;
import com.example.saravanamurali.farmersgen.models.JsonOrderResponse;
import com.example.saravanamurali.farmersgen.models.OrderDTO;
import com.example.saravanamurali.farmersgen.models.OrderDetailsDTO;
import com.example.saravanamurali.farmersgen.models.ViewCartPaymentGatewayDTO;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientForViewCart;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToGetExistingAddress;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToOrder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentGatewayActivity extends AppCompatActivity {

    String addressID;
    String curUser;

    List<OrderDetailsDTO> orderDetailsList;

    //After amount deduction from offer
    String grandTotal="";

    Button payButton;

    TextView cashOnDeliveryPay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);

        payButton=(Button)findViewById(R.id.payButton);

        cashOnDeliveryPay=(TextView)findViewById(R.id.grandTotalAtPaymentGateway);

        getAddressID();

        loadViewCartProductList();

        orderDetailsList=new ArrayList<OrderDetailsDTO>();


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
                if (response.isSuccessful()) {

                    if(csprogress.isShowing()){
                        csprogress.dismiss();
                    }

                    JSONResponseViewCartOrdersatPaymentGateway jsonResponseViewCartListDTO = response.body();
                    List<ViewCartPaymentGatewayDTO> viewCartProductListDTO = jsonResponseViewCartListDTO.getViewCartListRecord();

                    grandTotal=jsonResponseViewCartListDTO.getGrandTotal();

                    String ANDROID_MOBILE_ID = Settings.Secure.getString(PaymentGatewayActivity.this.getContentResolver(),
                            Settings.Secure.ANDROID_ID);

                    for (int i = 0; i < viewCartProductListDTO.size(); i++) {

                        String pay_ProductCode = viewCartProductListDTO.get(i).getProduct_Code();
                        String pay_ProductCount = viewCartProductListDTO.get(i).getCount();
                        String pay_ProductPrice = viewCartProductListDTO.get(i).getTotal_price();

                        OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(pay_ProductCode, pay_ProductCount, pay_ProductPrice, ANDROID_MOBILE_ID);
                        orderDetailsList.add(orderDetailsDTO);

                    }

                }

                cashOnDeliveryPay.setText(grandTotal);

            }

            @Override
            public void onFailure(Call<JSONResponseViewCartOrdersatPaymentGateway> call, Throwable t) {

                if(csprogress.isShowing()){
                    csprogress.dismiss();
                }

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
                if (response.isSuccessful()) {

                    if(csprogress.isShowing()){
                        csprogress.dismiss();
                    }

                    GetDeliveryAddressDTO getDeliveryAddressDTO = response.body();

                    addressID = getDeliveryAddressDTO.getAddressID();


                }
            }

            @Override
            public void onFailure(Call<GetDeliveryAddressDTO> call, Throwable t) {

                if(csprogress.isShowing()){
                    csprogress.dismiss();
                }

            }
        });

    }

    public void payOnclick(View view) {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(PaymentGatewayActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api = APIClientToOrder.getAPIInterfaceToOrder();

        String type="1";

        OrderDTO  orderDTO=new OrderDTO(curUser,addressID,type,grandTotal,orderDetailsList);



        Call<JsonOrderResponse> call=api.order(orderDTO);

        call.enqueue(new Callback<JsonOrderResponse>() {
            @Override
            public void onResponse(Call<JsonOrderResponse> call, Response<JsonOrderResponse> response) {
                if(response.isSuccessful()){

                    if(csprogress.isShowing()){
                        csprogress.dismiss();
                    }

                    JsonOrderResponse jsonOrderResponse=response.body();

                    Intent thanksActivity=new Intent(PaymentGatewayActivity.this,ThanksActivity.class);
                    startActivity(thanksActivity);
                    finish();

                    System.out.println("Order Confirmed");

                    Toast.makeText(PaymentGatewayActivity.this,"Order Confirmed",Toast.LENGTH_LONG).show();


                                    }
            }

            @Override
            public void onFailure(Call<JsonOrderResponse> call, Throwable t) {

                if(csprogress.isShowing()){
                    csprogress.dismiss();
                }

            }
        });
    }



}