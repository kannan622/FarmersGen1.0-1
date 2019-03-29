package com.example.saravanamurali.farmersgen.apiInterfaces;

import com.example.saravanamurali.farmersgen.models.ADDAddessDTO;
import com.example.saravanamurali.farmersgen.models.AddCartDTO;
import com.example.saravanamurali.farmersgen.models.ApplyCouponDTO;
import com.example.saravanamurali.farmersgen.models.CancelCouponDTO;
import com.example.saravanamurali.farmersgen.models.CurrentUserDTO;
import com.example.saravanamurali.farmersgen.models.DeleteCountInCartDTO;
import com.example.saravanamurali.farmersgen.models.GetDeliveryAddressDTO;
import com.example.saravanamurali.farmersgen.models.GetOrdersUsingDeviceID_DTO;
import com.example.saravanamurali.farmersgen.models.JSONOTPResponseFromOTPActivity;
import com.example.saravanamurali.farmersgen.models.JSONResponseADDAddress;
import com.example.saravanamurali.farmersgen.models.JSONResponseApplyCouponDTO;
import com.example.saravanamurali.farmersgen.models.JSONResponseCouponDTO;
import com.example.saravanamurali.farmersgen.models.JSONResponseDeleteCartDTO;
import com.example.saravanamurali.farmersgen.models.JSONResponseForCancelOrderDTO;
import com.example.saravanamurali.farmersgen.models.JSONResponseForNPasswordAndCPasswrod;
import com.example.saravanamurali.farmersgen.models.JSONResponseMenuCartFragDeleteDTO;
import com.example.saravanamurali.farmersgen.models.JSONResponseMenuCartFragUpdateDTO;
import com.example.saravanamurali.farmersgen.models.JSONResponseProfileEdit;
import com.example.saravanamurali.farmersgen.models.JSONResponseToFetchCancelOrderDTO;
import com.example.saravanamurali.farmersgen.models.JSONResponseToGetPastOrderDetails;
import com.example.saravanamurali.farmersgen.models.JSONResponseToSendMobileNoFromLoginForgetPasswordDTO;
import com.example.saravanamurali.farmersgen.models.JSONResponseToSendOTPFromForgetPasswordDTO;
import com.example.saravanamurali.farmersgen.models.JSONResponseToViewCartAtHomeMenuCartFragmentDTO;
import com.example.saravanamurali.farmersgen.models.JSONResponseToViewOrderedProductList;
import com.example.saravanamurali.farmersgen.models.JSONResponseToViewPastOrderedProductList;
import com.example.saravanamurali.farmersgen.models.JSONResponseUpdateCartDTO;
import com.example.saravanamurali.farmersgen.models.JSONResponseViewCartOrdersatPaymentGateway;
import com.example.saravanamurali.farmersgen.models.JsonOrderResponse;
import com.example.saravanamurali.farmersgen.models.LogOutDeviceIDDTO;
import com.example.saravanamurali.farmersgen.models.MenuCartFragmentDTO;
import com.example.saravanamurali.farmersgen.models.MenuCartUpdateDTO;
import com.example.saravanamurali.farmersgen.models.NewPassAndConfirmPassDTO;
import com.example.saravanamurali.farmersgen.models.OTPSendToMobileDTOFrom_FP;
import com.example.saravanamurali.farmersgen.models.OTPandMobileNoDTO;
import com.example.saravanamurali.farmersgen.models.OrderDTO;
import com.example.saravanamurali.farmersgen.models.OrderID_DTO;
import com.example.saravanamurali.farmersgen.models.SignUpJSONResponse;
import com.example.saravanamurali.farmersgen.models.SignedInJSONResponse;
import com.example.saravanamurali.farmersgen.models.JSONResponseHomeBrandDTO;
import com.example.saravanamurali.farmersgen.models.JSONResponseProceedBtnDTO;
import com.example.saravanamurali.farmersgen.models.JSONResponseProductListDTO;
import com.example.saravanamurali.farmersgen.models.JSONResponseViewCartListDTO;
import com.example.saravanamurali.farmersgen.models.ProceedBtnCheckingDTO;
import com.example.saravanamurali.farmersgen.models.SignInDTO;
import com.example.saravanamurali.farmersgen.models.SignUpDTO;
import com.example.saravanamurali.farmersgen.models.UpdateAddressDTO;
import com.example.saravanamurali.farmersgen.models.UpdateCountInCartDTO;
import com.example.saravanamurali.farmersgen.models.UpdateNameEmailDTO;
import com.example.saravanamurali.farmersgen.models.ViewCartUpdateDTO;
import com.example.saravanamurali.farmersgen.models.ViewProductListDTO;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {


    //Register User
    @POST("register.php")
    Call<SignUpJSONResponse> registerUser(@Body SignUpDTO signUpDTO);


    //Login
    @POST("get_single_user.php")
    Call<SignedInJSONResponse> getLoginUser(@Body SignInDTO signInDTO);


    @GET("view-brand.php")
    Call<JSONResponseHomeBrandDTO> getAllBrands();


    @POST("view-product.php")
    Call<JSONResponseProductListDTO> getSingleBrandProductList(@Body ViewProductListDTO viewProductListDTO);


    @POST("add-cart.php")
    Call<ResponseBody> addCart(@Body AddCartDTO addCartDTO);


    //Update Count At ADD Cart Screen
    @POST("update_cart.php")
    Call<ResponseBody> updateCountatAddCart(@Body UpdateCountInCartDTO updateCountInAddCart);

    //Delete items from Cart Activity
    @POST("delete-cart.php")
    Call<ResponseBody> deleteItemFromCart(@Body DeleteCountInCartDTO deleteItemFromCart);


    //Delete items from View Cart Activity
    @POST("delete-cart.php")
    Call<JSONResponseDeleteCartDTO> deleteItemFromViewCart(@Body ViewCartUpdateDTO deleteItemFromViewCart);

   //Update Count at View Cart Screen
   @POST("update_cart.php")
   Call<JSONResponseUpdateCartDTO> updateCountatAtViewCart(@Body ViewCartUpdateDTO updateCountInAddCart);

    //Update Count in Menu Cart Fragment
    @POST("update_cart.php")
    Call<JSONResponseMenuCartFragUpdateDTO> updateCountatInMenuCartFragment(@Body MenuCartUpdateDTO menuCartUpdateDTO);

    //Delete Count in Menu Cart Fragment
    @POST("delete-cart.php")
    Call<JSONResponseMenuCartFragDeleteDTO> deleteCountInMenuCartFragment(@Body MenuCartUpdateDTO deleteCartDTO);



    //View Cart To Show in Fragment
    @POST("view-cart.php")
    Call<JSONResponseViewCartListDTO> getViewCart(@Body AddCartDTO addCartDTO);


    //View Cart with deviceID and couponID
    //View Cart To Show in Fragment
    @POST("view-cart.php")
    Call<JSONResponseViewCartListDTO> getViewCartWithCouponID(@Body AddCartDTO addCartDTO);



    @POST("view-cart.php")
    Call<JSONResponseViewCartOrdersatPaymentGateway> getViewCartOrdersatPaymentGateway(@Body GetOrdersUsingDeviceID_DTO getOrdersUsingDeviceID_dto);

    //View Cart For Home MenuCartFragment
    @POST("view-cart.php")
    Call<JSONResponseToViewCartAtHomeMenuCartFragmentDTO> getViewCartForHomeMenuCartFragment(@Body MenuCartFragmentDTO menuCartFragmentDTO);

    @POST("view-cart.php")
    Call<JSONResponseViewCartListDTO> getDataFromViewCartWhenOnBackPressed(@Body AddCartDTO onBackPressed);

    @POST("proceed-cart.php")
    Call<JSONResponseProceedBtnDTO> ProceedBtnInViewCart(@Body ProceedBtnCheckingDTO proceedBtnCheckingDTO);

    //Send OTP to Registered Mobile Number
    @POST()
    Call<ResponseBody> sendOTP();

    //Get Existing Address
    @POST("get_delivery_address.php")
    Call<GetDeliveryAddressDTO> getExistingAddress(@Body CurrentUserDTO getExistingAddressDTO);

    //ADD Address
    @POST("add_address.php")
    Call<JSONResponseADDAddress>  addAddress(@Body ADDAddessDTO addAddessDTO);

    //Update Address
    @POST("update_address.php")
    Call<ResponseBody> updateAddress(@Body UpdateAddressDTO updateAddressDTO);

    //Get OTP From Forget Password
    @POST("forget_password.php")
    Call<JSONResponseToSendOTPFromForgetPasswordDTO> getOTPTOForgetPassword(@Body OTPSendToMobileDTOFrom_FP mobileNumber);

    //Send Mobile Number From Login Forget Password
    @POST("forget_password.php")
    Call<JSONResponseToSendMobileNoFromLoginForgetPasswordDTO> sendMobileNumberFromLoginForgetPassword(@Body OTPSendToMobileDTOFrom_FP mobileNumber);


    //Send OTP and Mobile Number From OTP Activity
    @POST("validate_otp.php")
    Call<JSONOTPResponseFromOTPActivity> sendMobileNoandOTPFromOTPActivity(@Body OTPandMobileNoDTO otPandMobileNoDTO);

    //Send OTP and Mobile Number From Login Foget Password Activity
    @POST("validate_otp.php")
    Call<JSONOTPResponseFromOTPActivity> sendMobileNoandOTPFromLoginForgetPasswordActivity(@Body OTPandMobileNoDTO otPandMobileNoDTO);



    //New Password and Confirm Password
    @POST("change_password.php")
    Call<JSONResponseForNPasswordAndCPasswrod> newPasswordAndConfirmPassword(@Body NewPassAndConfirmPassDTO newPassAndConfirmPassDTO);

    //New Password and Confirm Password From Login Forget Password
    @POST("change_password.php")
    Call<JSONResponseForNPasswordAndCPasswrod> newPasswordAndConfirmPasswordFromLoginPassword(@Body NewPassAndConfirmPassDTO newPassAndConfirmPassDTO);


    @POST("checkout.php")
    Call<JsonOrderResponse> order(@Body OrderDTO orderDTO);

    //Edit Profile get User Details using user ID
    @POST("profile_update_single_user.php")
    Call<JSONResponseProfileEdit> editProfile(@Body CurrentUserDTO currentUserDTO);

    //Update Name and Email
    @POST("profile_update.php")
    Call<ResponseBody> getUpdateNameAndMail(@Body UpdateNameEmailDTO updateNameEmailDTO);


    //Logout Using DeviceID
    @POST("log_out_delete_cart.php")
    Call<ResponseBody> getLogOutUsingDeviceID(@Body LogOutDeviceIDDTO logOutDeviceIDDTO);


    //Cancel Order List
    @POST("to_delivery.php")
    Call<JSONResponseToFetchCancelOrderDTO> getCancelOrderList(@Body CurrentUserDTO currentUserDTO);

    //Cancel Order using OrderID
    @POST("clear.php")
    Call<ResponseBody> getCancelOrder(@Body OrderID_DTO orderDTO);

    //Cancel Order View Prodcuts
    @POST("fetch_ordered_products_details.php")
    Call<JSONResponseToViewOrderedProductList> getViewCancelOrderProductList(@Body OrderID_DTO orderDTO);


    //Past Order List
    @POST("past_order.php")
    Call<JSONResponseToGetPastOrderDetails> getPastOrderList(@Body CurrentUserDTO currentUserToGetPastOrder);

    //Past Order View Prodcuts
    @POST("fetch_ordered_products_details.php")
    Call<JSONResponseToViewPastOrderedProductList> getViewPastOrderProductList(@Body OrderID_DTO orderDTO);

    //Get Coupon Code
    @GET("fetch_offer.php")
    Call<JSONResponseCouponDTO> getCouponCode();

    //Apply Code
    @POST("fetch_single_offer.php")
    Call<JSONResponseApplyCouponDTO> applyCoupon(@Body ApplyCouponDTO applyCouponDTO);

    //Apply Code
    @POST("delete_offer.php")
    Call<ResponseBody> cancelCoupon(@Body CancelCouponDTO cancelCouponDTO);



    @GET("get_auth_users.php")
    Call<SignedInJSONResponse> getAllUser();


}
