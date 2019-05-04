package com.example.saravanamurali.farmersgen.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.address.ExistingAddressActivity_AtMenuAccFragment;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.cancelorder.CancelOrderActivity;
import com.example.saravanamurali.farmersgen.favourite.FavouriteListActivity;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonResponseForFavBrandsDTO;
import com.example.saravanamurali.farmersgen.models.CurrentUserDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseForCancelOrderDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseProfileEdit;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseToFetchCancelOrderDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseToGetPastOrderDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseToGetPastOrderDetails;
import com.example.saravanamurali.farmersgen.models.FavBrandDTO;
import com.example.saravanamurali.farmersgen.models.LogOutDeviceIDDTO;
import com.example.saravanamurali.farmersgen.pastorder.PastOrderListActivity;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientForProfileEdit;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientLogOutUsingDeviceID;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToGetCancelOrderList;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToGetPastOrderDetails;
import com.example.saravanamurali.farmersgen.retrofitclient.ApiClientToGetFavBrands;
import com.example.saravanamurali.farmersgen.signin.LoginActivity;
import com.example.saravanamurali.farmersgen.tappedactivity.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class MenuAccountFragment extends Fragment {

    private final String NO_CURRENT_USER = "null";


    //For Past Order Snack Bar
    //For Cancel Order Snack Bar
    List<JSONResponseForCancelOrderDTO> jsonResponseForCancelOrderDTOSForSnakcerBar;
    List<JSONResponseToGetPastOrderDTO> jsonResponseToGetPastOrderDTOListSnackBar;
    String orderID;

    //coordinator layout for past order
    //  CoordinatorLayout coordnatorLayoutFor_PastOrder;
    //coordinator layout for cancel order
    CoordinatorLayout coordinatorLayout;
    TextView profileLoggedInUserName;

    /*FragmentManager fragmentManagerAccount = myContext.getSupportFragmentManager();
    FragmentTransaction fragmentTransactionAccount;
*/
    TextView profileLoggedInMobile;
    TextView profileLoggedInMailTextView;
    TextView profileLoggedInEdit;
    TextView dot;
    View profileLine;
    TextView myAccount;
    TextView myAccDetail;
    Button btnLogin;
    Button btnMenuAccLogin;
    RelativeLayout relativeLayoutMenuAccountProfile;
    RelativeLayout relativeLayoutManageAddressBlock;
    RelativeLayout relativeLayoutFavouriteBlock;
    RelativeLayout relativeLayoutPastOrderBlock;
    RelativeLayout relativeLayoutCancelOrderBlock;
    RelativeLayout relativeLayoutLogoutBlock;
    RelativeLayout relativeLayoutProfileLoginBlock;
    Button logout;
    String currentUserId;
    //Share App
    Button share_App;
    private FragmentActivity myContext;

    //Favourite List Check
    List<FavBrandDTO> getCheck_FavList;


    public MenuAccountFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public MenuAccountFragment(String currentUserId) {
        this.currentUserId = currentUserId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_account, container, false);


        //
        //getCancelOrderDetailsToDisplaySnackerBar();

        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinatorLayout);
        //  coordnatorLayoutFor_PastOrder=(CoordinatorLayout)view.findViewById(R.id.coordnatorLayoutForPastOrder);


        profileLoggedInUserName = (TextView) view.findViewById(R.id.profileUserName);
        profileLoggedInMobile = (TextView) view.findViewById(R.id.profileMobileNumber);
        profileLoggedInMailTextView = (TextView) view.findViewById(R.id.profieMail);
        profileLoggedInEdit = (TextView) view.findViewById(R.id.editProfile);
        dot = (TextView) view.findViewById(R.id.dot);
        profileLine = (View) view.findViewById(R.id.profileLine);
        myAccount = (TextView) view.findViewById(R.id.myAccount);
        myAccDetail = (TextView) view.findViewById(R.id.myAccDetail);

        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        btnMenuAccLogin = (Button) view.findViewById(R.id.homeBtnAtMenuAccFragment);


        //Curent User hide and View
        relativeLayoutMenuAccountProfile = (RelativeLayout) view.findViewById(R.id.currentUserprofileBlock);

        //Login Screen hide and View
        relativeLayoutProfileLoginBlock = (RelativeLayout) view.findViewById(R.id.profileLoginBlock);


        relativeLayoutManageAddressBlock = (RelativeLayout) view.findViewById(R.id.profileMannageAddressBlock);
        relativeLayoutFavouriteBlock = (RelativeLayout) view.findViewById(R.id.prfileFavouriteBlock);
        relativeLayoutPastOrderBlock = (RelativeLayout) view.findViewById(R.id.profilePastOrderBlock);

        relativeLayoutCancelOrderBlock = (RelativeLayout) view.findViewById(R.id.cancelOrderBlock);

        relativeLayoutLogoutBlock = (RelativeLayout) view.findViewById(R.id.profileLogoutBlock);

        //Share App
        share_App = (Button) view.findViewById(R.id.shareApp);

        share_App.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareAppToOtherApp();
            }
        });

        final SharedPreferences getCurrentUser = this.getActivity().getSharedPreferences("CURRENT_USER", MODE_PRIVATE);


        currentUserId = getCurrentUser.getString("CURRENTUSER", NO_CURRENT_USER);

        System.out.println("Curent User in Menu Acount Fragment" + currentUserId);

        if (currentUserId != NO_CURRENT_USER) {
            System.out.println("User is there");

            relativeLayoutMenuAccountProfile.setVisibility(View.VISIBLE);

            relativeLayoutProfileLoginBlock.setVisibility(View.INVISIBLE);

            relativeLayoutLogoutBlock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    logout();

                }
            });

            //Get Name,mobile number and email
            getNameMobileMail();


            //Edit Name and Email
            profileLoggedInEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    callProfileEditFragment();

                }
            });

            //Favourite Block

            relativeLayoutFavouriteBlock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showFavouriteList();
                }
            });

            //Cancel Order List(Cancel order Block is pressed)
            relativeLayoutCancelOrderBlock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jsonResponseForCancelOrderDTOSForSnakcerBar = new ArrayList<JSONResponseForCancelOrderDTO>();
                    cancelOrder();
                }
            });

            //Past Order Block(Order History)

            relativeLayoutPastOrderBlock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jsonResponseToGetPastOrderDTOListSnackBar = new ArrayList<JSONResponseToGetPastOrderDTO>();
                    pastOrderAndOrderHistory();
                }
            });

            //Manage address at MenuCartFragment
            relativeLayoutManageAddressBlock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    manageAddressInMenuAccountFragment();
                }
            });


            Toast.makeText(getActivity(), "User is there", Toast.LENGTH_LONG).show();

        } else {
            System.out.println("User is not there");

            relativeLayoutMenuAccountProfile.setVisibility(View.INVISIBLE);

            relativeLayoutProfileLoginBlock.setVisibility(View.VISIBLE);

            btnMenuAccLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent homeActivity = new Intent(MenuAccountFragment.this.getActivity(), HomeActivity.class);
                    startActivity(homeActivity);
                }
            });


            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent loginIntenFrommenuAccFragment = new Intent(MenuAccountFragment.this.getActivity(), LoginActivity.class);
                    startActivity(loginIntenFrommenuAccFragment);

                }
            });


            Toast.makeText(getActivity(), "User is not there", Toast.LENGTH_LONG).show();


            relativeLayoutMenuAccountProfile.setVisibility(View.GONE);

        }

        return view;
    }

    private void showFavouriteList() {

        getCheck_FavList = new ArrayList<FavBrandDTO>();
        //Checking whether Favourite list there or not
        checkFavListIsThere();
    }

    private void checkFavListIsThere() {
        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(this.getActivity());
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);

        //Getting Current User
        SharedPreferences getCheckFavList = this.getActivity().getSharedPreferences("CURRENT_USER", Context.MODE_PRIVATE);
        String checFav = getCheckFavList.getString("CURRENTUSER", "NO_CURRENT_USER");

        ApiInterface api = ApiClientToGetFavBrands.getApiInterfaceToGetFavBrands();

        CurrentUserDTO currentUserDTO = new CurrentUserDTO(checFav);

        Call<JsonResponseForFavBrandsDTO> call = api.getFavouriteBrands(currentUserDTO);

        call.enqueue(new Callback<JsonResponseForFavBrandsDTO>() {
            @Override
            public void onResponse(Call<JsonResponseForFavBrandsDTO> call, Response<JsonResponseForFavBrandsDTO> response) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

                JsonResponseForFavBrandsDTO jsonResponseForFavBrandsDTO = response.body();
                getCheck_FavList = jsonResponseForFavBrandsDTO.getFavRecords();

                if (getCheck_FavList != null) {
                    Intent getFavouriteList = new Intent(getActivity(), FavouriteListActivity.class);
                    startActivity(getFavouriteList);
                }
                else {
                    callFavouriteListSnackBar();
                }

            }

            @Override
            public void onFailure(Call<JsonResponseForFavBrandsDTO> call, Throwable t) {

            }
        });


    }

    private void callFavouriteListSnackBar() {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "You haven't added any favourite list yet!!!", Snackbar.LENGTH_LONG);
        snackbar.show();

    }


    //Manage Address show,add,edit adress
    private void manageAddressInMenuAccountFragment() {

        Intent getExistingAddress = new Intent(this.getActivity(), ExistingAddressActivity_AtMenuAccFragment.class);
        startActivity(getExistingAddress);

    }

    //To get Past Order List
    private void pastOrderAndOrderHistory() {
        //Checking whether past order is placed or not
        getPastOrderDetailsToDisplaySnacker();


    }

    private void callPastOrderSnackBar() {

        Snackbar snackbar = Snackbar.make(coordinatorLayout, "You dont placed any order yet!!!", Snackbar.LENGTH_LONG);
        snackbar.show();


    }

    private void getPastOrderDetailsToDisplaySnacker() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(getActivity());
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api = APIClientToGetPastOrderDetails.getApiInterfaceToGetPastOrderDetails();

        SharedPreferences getCurrentUser = this.getActivity().getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
        String curUserForPastOrderDetails = getCurrentUser.getString("CURRENTUSER", "NO_CURRENT_USER");

        CurrentUserDTO currentUserDTO = new CurrentUserDTO(curUserForPastOrderDetails);

        Call<JSONResponseToGetPastOrderDetails> call = api.getPastOrderList(currentUserDTO);

        call.enqueue(new Callback<JSONResponseToGetPastOrderDetails>() {
            @Override
            public void onResponse(Call<JSONResponseToGetPastOrderDetails> call, Response<JSONResponseToGetPastOrderDetails> response) {
                if (response.isSuccessful()) {
                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }
                    JSONResponseToGetPastOrderDetails jsonResponseToGetPastOrderDetails = response.body();

                    jsonResponseToGetPastOrderDTOListSnackBar = jsonResponseToGetPastOrderDetails.getJsonResponseToGetPastOrderDTOList();


                    if (jsonResponseToGetPastOrderDTOListSnackBar != null) {
                        Intent pastOrderActivity = new Intent(getActivity(), PastOrderListActivity.class);
                        startActivity(pastOrderActivity);
                    } else {
                        callPastOrderSnackBar();
                    }


                }
            }

            @Override
            public void onFailure(Call<JSONResponseToGetPastOrderDetails> call, Throwable t) {
                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

            }
        });

    }

    //To get Cancel Order List
    private void cancelOrder() {

        //Checking whether previously order is placed or not
        getCancelOrderDetailsToDisplaySnackBar();


    }

    private void callCancelOrderSnackerBar() {


        Snackbar snackbar = Snackbar.make(coordinatorLayout, "You dont have any orders to cancel", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void getCancelOrderDetailsToDisplaySnackBar() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(getActivity());
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api = APIClientToGetCancelOrderList.getApiInterfaceToGetCancelOrderList();

        SharedPreferences getCurrentUser = this.getActivity().getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
        String curUserAtForSnackerBar = getCurrentUser.getString("CURRENTUSER", "NO_CURRENT_USER");

        CurrentUserDTO currentUserDTO = new CurrentUserDTO(curUserAtForSnackerBar);
        Call<JSONResponseToFetchCancelOrderDTO> call = api.getCancelOrderList(currentUserDTO);

        call.enqueue(new Callback<JSONResponseToFetchCancelOrderDTO>() {
            @Override
            public void onResponse(Call<JSONResponseToFetchCancelOrderDTO> call, Response<JSONResponseToFetchCancelOrderDTO> response) {

                if (response.isSuccessful()) {

                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }

                    JSONResponseToFetchCancelOrderDTO jsonResponseToFetchCancelOrderDTO = response.body();
                    jsonResponseForCancelOrderDTOSForSnakcerBar = jsonResponseToFetchCancelOrderDTO.getJsonResponseForCancelOrderDTO();


                    System.out.println();
                    if (jsonResponseForCancelOrderDTOSForSnakcerBar != null) {
                        Intent cancelOrder = new Intent(getActivity(), CancelOrderActivity.class);
                        startActivity(cancelOrder);
                    } else {

                        callCancelOrderSnackerBar();

                    }


                }
            }

            @Override
            public void onFailure(Call<JSONResponseToFetchCancelOrderDTO> call, Throwable t) {
                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }


    private void getNameMobileMail() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(getActivity());
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        SharedPreferences getCurUserNameMobEmail = this.getActivity().getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
        String getProfile = getCurUserNameMobEmail.getString("CURRENTUSER", NO_CURRENT_USER);

        ApiInterface api = APIClientForProfileEdit.getApiInterfaceToEditProfile();

        CurrentUserDTO currentUserDTO = new CurrentUserDTO(getProfile);

        Call<JSONResponseProfileEdit> call = api.editProfile(currentUserDTO);

        call.enqueue(new Callback<JSONResponseProfileEdit>() {
            @Override
            public void onResponse(Call<JSONResponseProfileEdit> call, Response<JSONResponseProfileEdit> response) {

                if (response.isSuccessful()) {
                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }

                    JSONResponseProfileEdit jsonResponseProfileEdit = response.body();

                    profileLoggedInUserName.setText(jsonResponseProfileEdit.getProfileName());
                    profileLoggedInMobile.setText(jsonResponseProfileEdit.getProfileMobileNo());
                    profileLoggedInMailTextView.setText(jsonResponseProfileEdit.getProfileEmail());


                }
            }

            @Override
            public void onFailure(Call<JSONResponseProfileEdit> call, Throwable t) {
                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

            }
        });


    }


    //Edit Name and Email
    private void callProfileEditFragment() {
        Intent i = new Intent(this.getActivity(), ProfileUpdateActivity.class);
        startActivity(i);
    }
    //End edit Name and Email


    private void logout() {


        SharedPreferences getCurrentUser_CouponID = this.getActivity().getSharedPreferences("CURRENT_COUPON_ID", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = getCurrentUser_CouponID.edit();
        editor1.remove("COUPONID");
        editor1.commit();


        //Remove Current User COUPON CODE From Shared Preferences
        SharedPreferences getCurrentUser_CouponCODE = this.getActivity().getSharedPreferences("CURRENT_COUPON_CODE", MODE_PRIVATE);
        SharedPreferences.Editor editorCode = getCurrentUser_CouponCODE.edit();
        editorCode.remove("COUPON_CODE");
        editorCode.commit();


        //Get Current User From Shared Preferences
        SharedPreferences deleteCartUsingCurrentUSer = this.getActivity().getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
        String deletecart = deleteCartUsingCurrentUSer.getString("CURRENTUSER", NO_CURRENT_USER);

        System.out.println(deletecart);

        //Remove Current User From Shared Preferences
        SharedPreferences getCurrentUser = this.getActivity().getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
        SharedPreferences.Editor editor = getCurrentUser.edit();
        editor.remove("CURRENTUSER");
        editor.commit();

        clearAllItemsAtAddCartTableUsingDeviceID();

        /*Intent loginIntent = new Intent(getActivity(), MenuAccountFragmentLoginActivity.class);
        startActivity(loginIntent);
*/

    }

    private void clearAllItemsAtAddCartTableUsingDeviceID() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(getActivity());
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api = APIClientLogOutUsingDeviceID.getApiInterfaceToLogOutUsingDeviceID();

        String ANDROID_MOBILE_ID = Settings.Secure.getString(this.getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);


        LogOutDeviceIDDTO logOutDeviceIDDTO = new LogOutDeviceIDDTO(ANDROID_MOBILE_ID);

        Call<ResponseBody> call = api.getLogOutUsingDeviceID(logOutDeviceIDDTO);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }

                    Intent homeActvity = new Intent(getActivity(), HomeActivity.class);
                    startActivity(homeActvity);
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


    //Share app to other APP
    private void shareAppToOtherApp() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        String shareBody = "Body of the message";
        String shareSub = "Share sub";

        shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);

        startActivity(Intent.createChooser(shareIntent, "Share Using"));

        /* shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Insert Subject here");
        String app_url = " https://play.google.com/store/apps/details?id=my.example.javatpoint";
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,app_url);*/

    }


}

