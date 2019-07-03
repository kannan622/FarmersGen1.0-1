package com.example.saravanamurali.farmersgen.baseurl;

public interface BaseUrl {

    String DOMAIN_NAME="http://farmersgen.com/";
    String SUB_PATH="service/";
    //String SUB_PATH="ke/";

     static final String BASE_URL=DOMAIN_NAME+SUB_PATH;

     static final String ROOT_URL_FOR_SIGNUP=DOMAIN_NAME+SUB_PATH+"web_apis/";

    public static final String ROOT_URL_FOR_LOGIN =DOMAIN_NAME+SUB_PATH+"auth/";

    //To view Banner Images
    public static final String ROOT_URL_FOR_BANNER_IMAGES =DOMAIN_NAME+SUB_PATH+"product/";


    //To view Brand Prodcut+To view product List
    public static final String ROOT_URL_BRAND_PRODUCTS =DOMAIN_NAME+SUB_PATH+"product/";

    //To view Brand Review
    public static final String ROOT_URL_FOR_BRAND_REVIEW =DOMAIN_NAME+SUB_PATH+"review/";

    //To Post Brand Review
    public static final String ROOT_URL_TO_POST_BRAND_REVIEW =DOMAIN_NAME+SUB_PATH+"review/";

    //MoveDataFrom Sql Lite to Server DB
    public static final String ROOT_URL_TO_MOVE_SQLLITE_TO_SERVER_DB =DOMAIN_NAME+SUB_PATH+"cart/";



    //Add Cart
    public static final String ROOT_URL_FOR_ADDCART =DOMAIN_NAME+SUB_PATH+"cart/";

    //Update Count in Cart(+ Button)
    public static final String ROOT_URL_FOR_UPDATE_COUNT_IN_ADDCART =DOMAIN_NAME+SUB_PATH+"cart/";

    //View Cart Activity
    public static final String ROOT_URL_FOR_VIEWCART =DOMAIN_NAME+SUB_PATH+"cart/";

    //View Cart For Home MenuCartFragment
    public static final String ROOT_URL_TO_VIEWCART_AT_MENU_CART_FRAGMENT=DOMAIN_NAME+SUB_PATH+"cart/";

    //Delete Count in Cart(-Button)
    public static final String ROOT_URL_FOR_DELETE_ITEM_FROM_CART =DOMAIN_NAME+SUB_PATH+"cart/";


    //On Back Pressed at ViewCart
    public static final String ROOT_URL_FOR_ON_BACK_PRESSED_AT_VIEWCART=DOMAIN_NAME+SUB_PATH+"cart/";

    //Proceed Button in ViewCart Activity
    public static final String ROOT_URL_FOR_PROCEED_BTN_VIEWCART="";

    //To Send OTP
    public static final String ROOT_URL_TO_SEND_OTP ="http://farmersgen.com/api/auth/";

    //New Password and Confirm Password
    public static final String ROOT_URL_FOR_NEWPASS_CONFIRMPASS=DOMAIN_NAME+SUB_PATH+"web_apis/";

    //Send OTP and Mobile Number From OTP Activity
    public static final String ROOT_URL_TO_SEND_MOBILENO_AND_OTP=DOMAIN_NAME+SUB_PATH+"web_apis/";

    //Send OTP From Forget Password
    public static final  String ROOT_URL_TO_SEND_OTP_FROM_FORGET_PASSWORD=DOMAIN_NAME+SUB_PATH+"web_apis/";

    //ADD ADDRESS
    public static final String ROOT_URL_TO_ADD_ADDRESS=DOMAIN_NAME+SUB_PATH+"address/";

    //UPDATE ADDRESS
    public static final String ROOT_URL_TO_UPDATE_ADDRESS=DOMAIN_NAME+SUB_PATH+"address/";

    //Update Count In Menu Cart Fragment
    public static final String ROOT_URL_TO_UPDATE_COUNT_IN_MENU_CART_FRAGMENT=DOMAIN_NAME+SUB_PATH+"cart/";

    //Delete Count in MenuCartFragment(-Button)
    public static final String ROOT_URL_TO_DELETE_COUNT_IN_MENU_CART_FRAGMENT =DOMAIN_NAME+SUB_PATH+"cart/";

    //TO Order
    public  static final  String ROOT_URL_TO_ORDER=DOMAIN_NAME+SUB_PATH+"order/";

    //TO Send SMS for Order Confirmation
    public  static final  String ROOT_URL_TO_SEND_ORDER_CONFIRMATION_SMS=DOMAIN_NAME+SUB_PATH+"order/";


    //Cancel Order List
    public  static final  String ROOT_URL_TO_GET_CANCEL_ORDER_LIST=DOMAIN_NAME+SUB_PATH+"order/";

    //Cancel Order
    public  static final  String ROOT_URL_TO_GET_CANCEL_ORDER=DOMAIN_NAME+SUB_PATH+"order/";

    //Cancel Order Products List View
    public  static final  String ROOT_URL_TO_FETCH_ORDERED_PRODUCT_DETAILS=DOMAIN_NAME+SUB_PATH+"order/";

    //Past Order List
    public  static final  String ROOT_URL_TO_FETCH_PAST_ORDER=DOMAIN_NAME+SUB_PATH+"order/";

    //Past Order Products List View
    public  static final  String ROOT_URL_TO_FETCH_PAST_ORDERED_PRODUCT_DETAILS=DOMAIN_NAME+SUB_PATH+"order/";



    //To Get Delivery Address for Registerd User
    public static final String ROOT_URL_TO_GET_EXISTING_ADDRESS=DOMAIN_NAME+SUB_PATH+"address/";


    //Profile Edit
    public  static final String ROOT_URL_TO_EDIT_PROFILE=DOMAIN_NAME+SUB_PATH+"auth/";

    //Profile Edit
    public  static final String ROOT_URL_TO_UPDATE_NAME_URL=DOMAIN_NAME+SUB_PATH+"web_apis/";

    //To Get Coupon
    public  static final String ROOT_URL_TO_GET_COUPON=DOMAIN_NAME+SUB_PATH+"offers/";

    //Send FCM Token to Server
    public  static final String ROOT_URL_TO_SEND_FCM_TOKEN_TO_SERVER=DOMAIN_NAME+SUB_PATH+"notification/";


    //Apply Copuon
    public  static final String ROOT_URL_TO_APPLY_COUPON=DOMAIN_NAME+SUB_PATH+"offers/";

    //View Coupon Details
    public  static final String ROOT_URL_TO_VIEW_COUPON_DETAILS=DOMAIN_NAME+SUB_PATH+"offers/";


    //Cancel Copuon
    public  static final String ROOT_URL_TO_CANCEL_COUPON=DOMAIN_NAME+SUB_PATH+"offers/";

    //Favourite

    //Add Favourite Item
    public  static final String ROOT_URL_TO_ADD_FAVOURITE=DOMAIN_NAME+SUB_PATH+"favorite/";

    //Fetch All Favourites for user
    public  static final String ROOT_URL_TO_GET_FAVOURITE=DOMAIN_NAME+SUB_PATH+"favorite/";

    //Api Client To Check Favourite
    public  static final String ROOT_URL_TO_CHECK_FAVOURITE=DOMAIN_NAME+SUB_PATH+"favorite/";

    //Api Client To Remove Favourite
    public  static final String ROOT_URL_TO_REMOVE_FAVOURITE=DOMAIN_NAME+SUB_PATH+"favorite/";


    //LogOut using deviceID(clears all items from addCart Table)
    public static final String ROOT_URL_TO_LOGOUT_USING_DEVICEID=DOMAIN_NAME+SUB_PATH+"web_apis/";

    public static final String ROOT_URL_FOR_PRODUCT_DESC=DOMAIN_NAME+SUB_PATH+"product/";

    public static final String ROOT_URL_FOR_CARD_PAYMENT=DOMAIN_NAME+SUB_PATH+"payment/";





}
