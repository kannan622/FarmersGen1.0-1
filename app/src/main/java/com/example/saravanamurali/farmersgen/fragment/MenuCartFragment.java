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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.models.CurrentUserDTO;
import com.example.saravanamurali.farmersgen.models.GetDeliveryAddressDTO;
import com.example.saravanamurali.farmersgen.models.JSONResponseMenuCartFragDeleteDTO;
import com.example.saravanamurali.farmersgen.models.JSONResponseMenuCartFragUpdateDTO;
import com.example.saravanamurali.farmersgen.models.JSONResponseToViewCartAtHomeMenuCartFragmentDTO;
import com.example.saravanamurali.farmersgen.models.MenuCartFragmentDTO;
import com.example.saravanamurali.farmersgen.models.MenuCartFragmentViewCartDTO;
import com.example.saravanamurali.farmersgen.models.MenuCartUpdateDTO;
import com.example.saravanamurali.farmersgen.recyclerviewadapter.MenuCartFragmentAdapter;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientForDeleteItemInMenuCartFragment;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientForUpdateCountInMenuCartFragment;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToGetExistingAddress;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToViewCartFromMenuCartFragment;
import com.example.saravanamurali.farmersgen.signup.SignupActivity;
import com.example.saravanamurali.farmersgen.util.Network_config;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class MenuCartFragment extends Fragment implements MenuCartFragmentAdapter.MenuCartFragmentUpdateInterface, MenuCartFragmentAdapter.MenuCartFragmentDeleteInterface {

    Button menuCartCheckout;
    String menuCartGrandTotal;
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
        menuCartCheckout = (Button) view.findViewById(R.id.menuCartCheckOut);

        menuCartRecyclerView = (RecyclerView) view.findViewById(R.id.menuCartRecyclerView);
        menuCartRecyclerView.setHasFixedSize(true);
        menuCartRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        /*menuCartFragmentViewCartDTOList = new ArrayList<MenuCartFragmentViewCartDTO>();

        menuCartFragmentAdapter = new MenuCartFragmentAdapter(this.getActivity(), menuCartFragmentViewCartDTOList);

        menuCartRecyclerView.setAdapter(menuCartFragmentAdapter);

        menuCartFragmentAdapter.setMenuCartFragmentUpdateInterface(MenuCartFragment.this);
        menuCartFragmentAdapter.setMenuCartFragmentDeleteInterface(MenuCartFragment.this);
*/
        imageView = (ImageView) view.findViewById(R.id.emptyCartImage);

        menuCart_ToPayAmountTextView = (TextView) view.findViewById(R.id.m_CartToPayAmount);

        mauCart_Topay = (TextView) view.findViewById(R.id.m_CartToPay);


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

    @Override
    public void onResume() {
        super.onResume();

        if (Network_config.is_Network_Connected_flag(getActivity())) {
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

            Intent registerUserAtMenuCart = new Intent(this.getActivity(), SignupActivity.class);
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
    public void loadViewCartProductList() {

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

                        //loadViewCartProductList();

                    } else {

                        menuCartRecyclerView.setVisibility(View.GONE);
                        mauCart_Topay.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                        menuCartCheckout.setVisibility(View.GONE);


                    }

                    menuCartFragmentAdapter.setData(viewCartAtCartFragment);

                }
                menuCart_ToPayAmountTextView.setText(menuCartGrandTotal);

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

                menuCart_ToPayAmountTextView.setText(menuCartGrandTotal);

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

                        menuCart_ToPayAmountTextView.setText(menuCartGrandTotal);
                    } else {
                        menuCart_ToPayAmountTextView.setText("");
                        mauCart_Topay.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                        menuCartCheckout.setVisibility(View.GONE);

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


}
