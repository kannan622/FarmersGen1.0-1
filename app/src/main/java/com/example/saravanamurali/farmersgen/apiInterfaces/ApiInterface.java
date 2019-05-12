package com.example.saravanamurali.farmersgen.apiInterfaces;

import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonResponseForAddCartDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonResponseForAddFavourite;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonResponseForDeleteCartDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonResponseForDeleteFavDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonResponseForFavBrandsDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonResponseForUpdateCartDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonResponseFromServerDBDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonResponseToCheckFavourite;
import com.example.saravanamurali.farmersgen.models.ADDAddessDTO;
import com.example.saravanamurali.farmersgen.models.AddCartDTO;
import com.example.saravanamurali.farmersgen.models.AddFavouriteDTO;
import com.example.saravanamurali.farmersgen.models.ApplyCouponDTO;
import com.example.saravanamurali.farmersgen.models.CancelCouponDTO;
import com.example.saravanamurali.farmersgen.models.CardPaymentDTO;
import com.example.saravanamurali.farmersgen.models.CheckFavDTO;
import com.example.saravanamurali.farmersgen.models.CurrentUserDTO;
import com.example.saravanamurali.farmersgen.models.DeleteCountInCartDTO;
import com.example.saravanamurali.farmersgen.models.GetDataFromSqlLiteDTO;
import com.example.saravanamurali.farmersgen.models.GetDeliveryAddressDTO;
import com.example.saravanamurali.farmersgen.models.GetOrdersUsingDeviceID_DTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONOTPResponseFromOTPActivity;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseADDAddress;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseApplyCouponDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseCouponDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseDeleteCartDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseForNPasswordAndCPasswrod;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseMenuCartFragDeleteDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseMenuCartFragUpdateDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseProfileEdit;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseToFetchCancelOrderDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseToGetPastOrderDetails;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseToGetProductDescListDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseToSendMobileNoFromLoginForgetPasswordDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseToSendOTPFromForgetPasswordDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseToViewCartAtHomeMenuCartFragmentDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseToViewOrderedProductList;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseToViewPastOrderedProductList;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseUpdateCartDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseViewCartOrdersatPaymentGateway;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonOrderResponse;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonResponseForBannerDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonResponseForBrandReview;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonResponseForProductPostReviewDTO;
import com.example.saravanamurali.farmersgen.models.LogOutDeviceIDDTO;
import com.example.saravanamurali.farmersgen.models.MenuCartFragmentDTO;
import com.example.saravanamurali.farmersgen.models.MenuCartUpdateDTO;
import com.example.saravanamurali.farmersgen.models.NewPassAndConfirmPassDTO;
import com.example.saravanamurali.farmersgen.models.OTPSendToMobileDTOFrom_FP;
import com.example.saravanamurali.farmersgen.models.OTPandMobileNoDTO;
import com.example.saravanamurali.farmersgen.models.OrderDTO;
import com.example.saravanamurali.farmersgen.models.OrderID_DTO;
import com.example.saravanamurali.farmersgen.models.PostReviewDTO;
import com.example.saravanamurali.farmersgen.models.ProductDescDTO;
import com.example.saravanamurali.farmersgen.models.ReviewDTO;
import com.example.saravanamurali.farmersgen.models.SendOrderConfirmationSMSDTO;
import com.example.saravanamurali.farmersgen.models.SignUpJSONResponse;
import com.example.saravanamurali.farmersgen.models.SignedInJSONResponse;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseHomeBrandDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseProceedBtnDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseProductListDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseViewCartListDTO;
import com.example.saravanamurali.farmersgen.models.ProceedBtnCheckingDTO;
import com.example.saravanamurali.farmersgen.models.SignInDTO;
import com.example.saravanamurali.farmersgen.models.SignUpDTO;
import com.example.saravanamurali.farmersgen.models.UpdateAddressDTO;
import com.example.saravanamurali.farmersgen.models.UpdateCountInCartDTO;
import com.example.saravanamurali.farmersgen.models.UpdateNameEmailDTO;
import com.example.saravanamurali.farmersgen.models.ViewCartUpdateDTO;
import com.example.saravanamurali.farmersgen.models.ViewProductListDTO;

import java.util.List;

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

    //Get Banner Images
    @GET("fetch_banner.php")
    Call<JsonResponseForBannerDTO> getAllBannerImages();


    @POST("view-product.php")
    Call<JSONResponseProductListDTO> getSingleBrandProductList(@Body ViewProductListDTO viewProductListDTO);

    @POST("insert_view_cart.php")
    Call<JsonResponseFromServerDBDTO>moveSqlLiteDataToSever(@Body List<GetDataFromSqlLiteDTO> getDataFromSqlLiteDTO);

    //Get Reviews
    @POST("fetch_review.php")
    Call<JsonResponseForBrandReview> getBrandReviews(@Body ReviewDTO reviewDTO);


    //Add Review
    @POST("add_review.php")
    Call<JsonResponseForProductPostReviewDTO> postBrandReview(@Body PostReviewDTO reviewDTO);


    @POST("add-cart.php")
    Call<JsonResponseForAddCartDTO> addCart(@Body AddCartDTO addCartDTO);


    //Update Count At ADD Cart Screen
    @POST("update_cart.php")
    Call<JsonResponseForUpdateCartDTO> updateCountatAddCart(@Body UpdateCountInCartDTO updateCountInAddCart);

    //Delete items from Cart Activity
    @POST("delete-cart.php")
    Call<JsonResponseForDeleteCartDTO> deleteItemFromCart(@Body DeleteCountInCartDTO deleteItemFromCart);


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
    @POST("view_cart_with_offer.php")
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


    //To Send Sms for Order confirmation
    @POST("order_sms.php")
    Call<ResponseBody> sendOrderConfirmationSMS(@Body SendOrderConfirmationSMSDTO  sendOrderConfirmationSMSDTO);


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

    @POST("fetch_product_description.php")
    Call<JSONResponseToGetProductDescListDTO> getProductDesc(@Body ProductDescDTO productDescDTO);

    //Favourite

    //Add Favourite
    @POST("add_favorite.php")
    Call<JsonResponseForAddFavourite>addFavourite(@Body AddFavouriteDTO addFavouriteDTO);

    //Fetch Favourite Brands
    @POST("fetch_favorite.php")
    Call<JsonResponseForFavBrandsDTO> getFavouriteBrands(@Body CurrentUserDTO currentUserDTO);

    //To Check Favourite
    @POST("view_favorite_for_single_brand.php")
    Call<JsonResponseToCheckFavourite> checkFavList(@Body CheckFavDTO checkFavDTO);

    @POST("delete_favorite.php")
    Call<JsonResponseForDeleteFavDTO> removeFavBrand(@Body CheckFavDTO checkFavDTO);


    @POST("payment.php")
    Call<ResponseBody> doCardPayment(@Body CardPaymentDTO cardPaymentDTO);


}
