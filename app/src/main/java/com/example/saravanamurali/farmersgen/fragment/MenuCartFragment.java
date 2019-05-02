package com.example.saravanamurali.farmersgen.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.address.Add_Address_Activity;
import com.example.saravanamurali.farmersgen.address.ExistingAddressActivity;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.coupon.CouponActivity;
import com.example.saravanamurali.farmersgen.models.CurrentUserDTO;
import com.example.saravanamurali.farmersgen.models.GetDeliveryAddressDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseMenuCartFragDeleteDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseMenuCartFragUpdateDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseToViewCartAtHomeMenuCartFragmentDTO;
import com.example.saravanamurali.farmersgen.models.MenuCartFragmentDTO;
import com.example.saravanamurali.farmersgen.models.MenuCartFragmentViewCartDTO;
import com.example.saravanamurali.farmersgen.models.MenuCartUpdateDTO;
import com.example.saravanamurali.farmersgen.recyclerviewadapter.MenuCartFragmentAdapter;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientForDeleteItemInMenuCartFragment;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientForUpdateCountInMenuCartFragment;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToGetExistingAddress;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToViewCartFromMenuCartFragment;
import com.example.saravanamurali.farmersgen.signin.LoginActivityForViewCart;
import com.example.saravanamurali.farmersgen.util.Network_config;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class MenuCartFragment extends Fragment implements MenuCartFragmentAdapter.MenuCartFragmentUpdateInterface, MenuCartFragmentAdapter.MenuCartFragmentDeleteInterface {

    TextView menuCartCheckout;
    String menuCartGrandTotal;

    RelativeLayout bottomMenuCart;

    //RelativeLayout showCoupon_MenuCart;

    //Coupon Applied
    /*RelativeLayout couponAppliedBlock_MenuCart;
    TextView couponCodeApplied_MenuCart;
    ImageView cancelCoupon_MenuCart;
*/

    String menuCartAddressID;
    TextView menuCart_ToPayAmountTextView;
    TextView mauCart_Topay;
    ImageView imageView;
    Dialog dialog;
    int cartTotalAmount = 0;
    RecyclerView menuCartRecyclerView;
    MenuCartFragmentAdapter menuCartFragmentAdapter;
    List<MenuCartFragmentViewCartDTO> menuCartFragmentViewCartDTOList;
    String currentUserId;
    private String NO_CURRENT_USER = "NO_CURRENT_USER";

    private String NO_CURRENT_COUPON_ID = "NO_CURRENT_COUPON_ID";

    private String NO_CURRENT_COUPON_CODE = "NO_CURRENT_COUPON_CODE";


    /* @SuppressLint("ValidFragment")
     public MenuCartFragment(String currentUserId) {
         this.currentUserId = currentUserId;
     }
 */
    public MenuCartFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_cart, container, false);


        dialog = new Dialog(getActivity());

        //Checkout Button
        menuCartCheckout = (TextView) view.findViewById(R.id.CheckOut_MenuCart);
        mauCart_Topay = (TextView) view.findViewById(R.id.m_CartToPayAmount);

        menuCartRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_MenuCart);
        menuCartRecyclerView.setHasFixedSize(true);
        menuCartRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        imageView = (ImageView) view.findViewById(R.id.emptyMenuCartImage);

        bottomMenuCart=(RelativeLayout)view.findViewById(R.id.bottom_menuCart);

       /* showCoupon_MenuCart=(RelativeLayout)view.findViewById(R.id.coupon_MenuCart);

        //Coupon Code Applied
        couponAppliedBlock_MenuCart = (RelativeLayout)view.findViewById(R.id.couponAppliedBlock_MenuCart);
        couponCodeApplied_MenuCart = (TextView)view.findViewById(R.id.couponCode_MenuCart);
        cancelCoupon_MenuCart = (ImageView)view.findViewById(R.id.couponCodeCancelMenuCart);

        cancelCoupon_MenuCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCouponCode_MenuCart();
            }
        });
*/
        //offer block cliked
        /*showCoupon_MenuCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                proceedOffers();
            }
        });
*/


        /*menuCartFragmentViewCartDTOList = new ArrayList<MenuCartFragmentViewCartDTO>();

        menuCartFragmentAdapter = new MenuCartFragmentAdapter(this.getActivity(), menuCartFragmentViewCartDTOList);

        menuCartRecyclerView.setAdapter(menuCartFragmentAdapter);

        menuCartFragmentAdapter.setMenuCartFragmentUpdateInterface(MenuCartFragment.this);
        menuCartFragmentAdapter.setMenuCartFragmentDeleteInterface(MenuCartFragment.this);
*/


       // menuCart_ToPayAmountTextView = (TextView) view.findViewById(R.id.m_CartToPayAmount);




        //loadViewCartProductList();


        //Get addressID for Existing User
        getAddressID();


        menuCartCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Network_config.is_Network_Connected_flag(getActivity())) {
                    Toast.makeText(getActivity(), "Checking Menu Cart Button", Toast.LENGTH_LONG).show();

                    checkoutButtonPressedInMenuCartFragment();
                } else {
                    Network_config.customAlert(dialog, getActivity(), getResources().getString(R.string.app_name),
                            getResources().getString(R.string.connection_message));
                }

            }
        });


        return view;
    }

    //Check whether user is logged in or not
    private void proceedOffers() {

        //Getting Current User
        SharedPreferences getCurrentUser = this.getActivity().getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
        String curUserToCheckOffer = getCurrentUser.getString("CURRENTUSER", "NO_CURRENT_USER");

        if (!curUserToCheckOffer.equals(NO_CURRENT_USER)) {

            Intent coupon = new Intent(this.getActivity(), CouponActivity.class);
            startActivity(coupon);
        } else {

            Toast toast = Toast.makeText(this.getActivity(), "Please login to avil offer. To login Click on  CheckOut Button", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER, 0, 0);
            toast.show();

        }



    }



    private void removeCouponCodeAndCouponID() {

        //Remove Current User COUPON ID From Shared Preferences
        SharedPreferences getCurrentUser_CouponID =this.getActivity().getSharedPreferences("CURRENT_COUPON_ID", MODE_PRIVATE);
        SharedPreferences.Editor editor = getCurrentUser_CouponID.edit();
        editor.remove("COUPONID");
        editor.commit();


        //Remove Current User COUPON CODE From Shared Preferences
        SharedPreferences getCurrentUser_CouponCODE =this.getActivity().getSharedPreferences("CURRENT_COUPON_CODE", MODE_PRIVATE);
        SharedPreferences.Editor editorCode = getCurrentUser_CouponCODE.edit();
        editorCode.remove("COUPON_CODE");
        editorCode.commit();

    }

    @Override
    public void onResume() {
        super.onResume();

        if (Network_config.is_Network_Connected_flag(getActivity())) {

            //Display all ordered products from product list activity
            loadViewCartProductList();

            menuCartFragmentViewCartDTOList = new ArrayList<MenuCartFragmentViewCartDTO>();

            menuCartFragmentAdapter = new MenuCartFragmentAdapter(this.getActivity(), menuCartFragmentViewCartDTOList);

            menuCartRecyclerView.setAdapter(menuCartFragmentAdapter);

            menuCartFragmentAdapter.setMenuCartFragmentUpdateInterface(MenuCartFragment.this);
            menuCartFragmentAdapter.setMenuCartFragmentDeleteInterface(MenuCartFragment.this);
        } else {
            Network_config.customAlert(dialog, getActivity(), getResources().getString(R.string.app_name),
                    getResources().getString(R.string.connection_message));
        }


    }


    private void storeCurrentUserAddressID() {

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("ADDRESS_ID", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ADDRESSID", menuCartAddressID);
        editor.commit();
    }

    //Checkout Button Pressed in MenuCartFragment
    private void checkoutButtonPressedInMenuCartFragment() {

        SharedPreferences getCurUserAtMenuCart = this.getActivity().getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
        String curUserAtMenuCart = getCurUserAtMenuCart.getString("CURRENTUSER", NO_CURRENT_USER);

        //User not loggedIN yet
        //Guest User Not yet Logged In
        if (curUserAtMenuCart.equals(NO_CURRENT_USER)) {

            //Toast.makeText(this.getActivity(), "No Current Cuser", Toast.LENGTH_LONG).show();

            Intent registerUserAtMenuCart = new Intent(this.getActivity(), LoginActivityForViewCart.class);
            startActivity(registerUserAtMenuCart);

        }

        //ExistingUser
        else if (!curUserAtMenuCart.equals(NO_CURRENT_USER) && menuCartAddressID != null) {

            //     Toast.makeText(this.getActivity(), "He is Current Cuser", Toast.LENGTH_LONG).show();

            Intent deliveryAddressActivity = new Intent(this.getActivity(), ExistingAddressActivity.class);
            // deliveryAddressActivity.putExtra("CURRENTUSER", curUserAtMenuCart);
            startActivity(deliveryAddressActivity);
        }

        //First Time Login User
        else if (!curUserAtMenuCart.equals(NO_CURRENT_USER) && menuCartAddressID == null) {

            //  Toast.makeText(this.getActivity(), "He is Current Cuser", Toast.LENGTH_LONG).show();

            Intent addAddressActivity = new Intent(this.getActivity(), Add_Address_Activity.class);
            startActivity(addAddressActivity);
        }

    }

    //User not logged in yet fetching data using deviceID
    private void loadViewCartProductList() {

        String ANDROID_MOBILE_ID = Settings.Secure.getString(this.getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        ApiInterface api = APIClientToViewCartFromMenuCartFragment.getApiInterfaceToViewCartFromMenuCartFragment();

        MenuCartFragmentDTO menuCartFragmentViewCartDTO = new MenuCartFragmentDTO(ANDROID_MOBILE_ID);

        Call<JSONResponseToViewCartAtHomeMenuCartFragmentDTO> call = api.getViewCartForHomeMenuCartFragment(menuCartFragmentViewCartDTO);

        call.enqueue(new Callback<JSONResponseToViewCartAtHomeMenuCartFragmentDTO>() {
            @Override
            public void onResponse(Call<JSONResponseToViewCartAtHomeMenuCartFragmentDTO> call, Response<JSONResponseToViewCartAtHomeMenuCartFragmentDTO> response) {

                if (response.isSuccessful()) {

                    JSONResponseToViewCartAtHomeMenuCartFragmentDTO jsonResponseToViewCartAtHomeMenuCartFragmentDTO = response.body();

                    List<MenuCartFragmentViewCartDTO> viewCartAtCartFragment = jsonResponseToViewCartAtHomeMenuCartFragmentDTO.getViewCartListRecord();

                    menuCartGrandTotal = jsonResponseToViewCartAtHomeMenuCartFragmentDTO.getGrandTotal();

                    int gTotal = 0;
                    if (menuCartGrandTotal == null) {

                        gTotal = 0;
                    } else if (menuCartGrandTotal != null) {

                        gTotal = Integer.parseInt(menuCartGrandTotal);
                    }

                    if (gTotal > 0) {
                        imageView.setVisibility(View.GONE);
                        menuCartRecyclerView.setVisibility(View.VISIBLE);
                        mauCart_Topay.setVisibility(View.VISIBLE);
                        bottomMenuCart.setVisibility(View.VISIBLE);

                        //loadViewCartProductList();

                    } else {

                        menuCartRecyclerView.setVisibility(View.GONE);
                        mauCart_Topay.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                        menuCartCheckout.setVisibility(View.GONE);
                        bottomMenuCart.setVisibility(View.GONE);
                        bottomMenuCart.setVisibility(View.GONE);


                    }

                    menuCartFragmentAdapter.setData(viewCartAtCartFragment);
                    menuCartFragmentAdapter.notifyDataSetChanged();

                }
                mauCart_Topay.setText(menuCartGrandTotal);



            }

            @Override
            public void onFailure(Call<JSONResponseToViewCartAtHomeMenuCartFragmentDTO> call, Throwable t) {

            }
        });


    }


    @Override
    public void menuCartFragementUpdate(int menuCartCount, String menuCartProductCode, String menuCartTotalPrice) {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(getActivity());
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);
        String ANDROID_MOBILE_ID = Settings.Secure.getString(this.getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        ApiInterface api = APIClientForUpdateCountInMenuCartFragment.getAPIInterfaceToUpdateCountInMenuCartFragment();

        String m_Cartcount = String.valueOf(menuCartCount);
        MenuCartUpdateDTO menuCartUpdateDTO = new MenuCartUpdateDTO(m_Cartcount, menuCartProductCode, menuCartTotalPrice, ANDROID_MOBILE_ID);

        Call<JSONResponseMenuCartFragUpdateDTO> call = api.updateCountatInMenuCartFragment(menuCartUpdateDTO);

        call.enqueue(new Callback<JSONResponseMenuCartFragUpdateDTO>() {
            @Override
            public void onResponse(Call<JSONResponseMenuCartFragUpdateDTO> call, Response<JSONResponseMenuCartFragUpdateDTO> response) {

                if (response.isSuccessful()) {
                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }
                    JSONResponseMenuCartFragUpdateDTO jsonResponseMenuCartFragUpdateDTO = response.body();

                    menuCartFragmentAdapter.setTotalPriceToUpdateCart(jsonResponseMenuCartFragUpdateDTO);

                    menuCartGrandTotal = jsonResponseMenuCartFragUpdateDTO.getmCartUpdate_GrandTotal();


                }

                mauCart_Topay.setText(menuCartGrandTotal);

            }

            @Override
            public void onFailure(Call<JSONResponseMenuCartFragUpdateDTO> call, Throwable t) {
                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }
            }
        });


    }

    @Override
    public void menuCartFragmentDelete(String productCode) {
        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(getActivity());
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);
        String ANDROID_MOBILE_ID = Settings.Secure.getString(this.getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        ApiInterface api = APIClientForDeleteItemInMenuCartFragment.getAPIInterfaceToDeleteItemInMenuCartFragment();
        MenuCartUpdateDTO menuCartDeleteDTO = new MenuCartUpdateDTO(productCode, ANDROID_MOBILE_ID);

        Call<JSONResponseMenuCartFragDeleteDTO> call = api.deleteCountInMenuCartFragment(menuCartDeleteDTO);

        call.enqueue(new Callback<JSONResponseMenuCartFragDeleteDTO>() {
            @Override
            public void onResponse(Call<JSONResponseMenuCartFragDeleteDTO> call, Response<JSONResponseMenuCartFragDeleteDTO> response) {

                if (response.isSuccessful()) {

                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }

                    JSONResponseMenuCartFragDeleteDTO jsonResponseMenuCartFragDeleteDTO = response.body();

                    menuCartGrandTotal = jsonResponseMenuCartFragDeleteDTO.getDeleteMenuCartGrandTotal();

                    int toPayDeleteCheck = 0;
                    if (menuCartGrandTotal == null) {
                        toPayDeleteCheck = 0;
                    } else {
                        toPayDeleteCheck = Integer.parseInt(menuCartGrandTotal);
                    }
                    if (toPayDeleteCheck > 0) {

                        mauCart_Topay.setText(menuCartGrandTotal);
                        bottomMenuCart.setVisibility(View.VISIBLE);
                    } else {
                        mauCart_Topay.setText("");
                        mauCart_Topay.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                        menuCartCheckout.setVisibility(View.GONE);
                        bottomMenuCart.setVisibility(View.GONE);

                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponseMenuCartFragDeleteDTO> call, Throwable t) {
                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }
            }
        });


    }

    //Get Address Id at MenuCartFragment
    private void getAddressID() {
        ApiInterface api = APIClientToGetExistingAddress.getAPIInterfaceTOGetExistingAddress();

        SharedPreferences getCurUserAddressID = this.getActivity().getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
        String curUserAtAddressId = getCurUserAddressID.getString("CURRENTUSER", NO_CURRENT_USER);

        CurrentUserDTO currentUserDTO = new CurrentUserDTO(curUserAtAddressId);

        Call<GetDeliveryAddressDTO> call = api.getExistingAddress(currentUserDTO);

        call.enqueue(new Callback<GetDeliveryAddressDTO>() {
            @Override
            public void onResponse(Call<GetDeliveryAddressDTO> call, Response<GetDeliveryAddressDTO> response) {
                if (response.isSuccessful()) {

                    GetDeliveryAddressDTO getDeliveryAddressDTO = response.body();
                    menuCartAddressID = getDeliveryAddressDTO.getAddressID();
                    storeCurrentUserAddressID();

                }
            }

            @Override
            public void onFailure(Call<GetDeliveryAddressDTO> call, Throwable t) {

            }
        });

    }



   /* private void deleteCouponCode_MenuCart() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(this.getActivity());
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api = APIClientToCancelCouponCode.getApiInterfaceToCancelCouponCode();

        //Current User
        SharedPreferences getCurrentUser_ForDeleteCoupon = this.getActivity().getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
        String curUserID_ForDeleteCoupon_MenuCart = getCurrentUser_ForDeleteCoupon.getString("CURRENTUSER", "NO_CURRENT_USER");

        //Current Coupon ID
        SharedPreferences getCouponID = this.getActivity().getSharedPreferences("CURRENT_COUPON_ID", MODE_PRIVATE);
        String curUserCouponID_MenuCart = getCouponID.getString("COUPONID", "NO_CURRENT_COUPON_ID");

        CancelCouponDTO cancelCouponDTO_MenuCart = new CancelCouponDTO(curUserID_ForDeleteCoupon_MenuCart, curUserCouponID_MenuCart);

        Call<ResponseBody> call = api.cancelCoupon(cancelCouponDTO_MenuCart);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                removeCouponCodeAndCouponID();

                showCoupon_MenuCart.setVisibility(View.VISIBLE);
                couponAppliedBlock_MenuCart.setVisibility(View.GONE);

                loadViewCartProductList();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });



    }*/

}
