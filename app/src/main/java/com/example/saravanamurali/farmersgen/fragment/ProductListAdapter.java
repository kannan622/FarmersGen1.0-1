package com.example.saravanamurali.farmersgen.fragment;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.DummyActivity;
import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.fragment.MenuHomeFragment;
import com.example.saravanamurali.farmersgen.fragment.ViewCartActivity;
import com.example.saravanamurali.farmersgen.models.AddCartDTO;
import com.example.saravanamurali.farmersgen.models.FramentListDTO;
import com.example.saravanamurali.farmersgen.models.JSONResponseViewCartListDTO;
import com.example.saravanamurali.farmersgen.models.ProductListDTO;
import com.example.saravanamurali.farmersgen.models.ViewCartDTO;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientForViewCart;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListAdapterViewHolder> {

    int fragmentCount;


    Context mListContext;
    List<ProductListDTO> productListDTOList;

    //OnBackPressed
    List<ViewCartDTO> viewCartOnBackPressedDTO;

    ShowDataInFragment showDataInFragment;

    AddCart addCartInDb;

    UpdateCartInAddCart updateCartInAddCartInterface;

    DeleteItemWhenCountZeroInterface deleteItemWhenCountZeroInterface;

    public int getCartCount() {
        int count = 0;
        for (ProductListDTO product : productListDTOList) {
            if (product.getCount() != null && !product.getCount().isEmpty())
                count = count + Integer.parseInt(product.getCount());
        }
        return count;
    }

    public interface DeleteItemWhenCountZeroInterface {
        public void deleteItemWhenCountZero(String produceCode);
    }

    public interface UpdateCartInAddCart {
        public void updateCartInAddCart(String updateProductCode, int updateCount, String prouctPrice);
    }

    public interface AddCart {
        public void addCart(int countNum, String product_Code, String productPrice);
    }

    public void setAddCart(AddCart addCountInDb) {
        this.addCartInDb = addCountInDb;
    }

    public void setUpdateCartInAddCart(UpdateCartInAddCart updateCartInAddCartInterface) {
        this.updateCartInAddCartInterface = updateCartInAddCartInterface;
        notifyDataSetChanged();

    }

    public void setDeleteItemWhenCountZero(DeleteItemWhenCountZeroInterface deleteItemWhenCountZeroInterface) {
        this.deleteItemWhenCountZeroInterface = deleteItemWhenCountZeroInterface;
        notifyDataSetChanged();
    }


    public interface ShowDataInFragment {

        public void showInFragment(int fragmentCount);

    }

    public void setShowDataInFragment(ShowDataInFragment showDataInFragmentListener) {
        this.showDataInFragment = showDataInFragmentListener;
        notifyDataSetChanged();

    }


    public ProductListAdapter() {

    }

    private OnProductItemClickListener mItemClickListener;

    public interface OnProductItemClickListener {

        void OnProductItemClick(int position);

    }


    public void setOnProductItemClickListener(OnProductItemClickListener clickListener) {
        mItemClickListener = clickListener;

    }

    public ProductListAdapter(Context mListContext, List<ProductListDTO> productListDTOList) {
        this.mListContext = mListContext;
        this.productListDTOList = productListDTOList;
    }

    public void setDataListChanged() {
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ProductListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mListContext);
        View view = inflater.inflate(R.layout.productlist_adapterview, parent, false);
        ProductListAdapterViewHolder productListAdapterViewHolder = new ProductListAdapterViewHolder(view);

        return productListAdapterViewHolder;
    }

    /*public void onBackPressedAtViewCart(List<ViewCartDTO> viewCartOnBackPressedDTO){

        this.viewCartOnBackPressedDTO=viewCartOnBackPressedDTO;

    }*/


    @Override
    public void onBindViewHolder(@NonNull ProductListAdapterViewHolder holder, int position) {

        holder.productListName.setText(productListDTOList.get(position).getProductName());
        holder.productListPrice.setText("" + productListDTOList.get(position).getProductPrice());
        Picasso.with(mListContext).load(productListDTOList.get(position).getProductImage()).into(holder.productListImage);

        int finalCount = 0;

        if (productListDTOList.get(position).getCount() == null) {
            finalCount = 0;

        } else {
            finalCount = Integer.parseInt(productListDTOList.get(position).getCount());
        }

        //if (productListDTOList.get(position).getCount()!=null)
        if (finalCount > 0) {

            holder.addButton.setVisibility(View.GONE);
            holder.incButton.setVisibility(View.VISIBLE);
            holder.decButton.setVisibility(View.VISIBLE);
            holder.countShow.setVisibility(View.VISIBLE);
            holder.countShow.setText(productListDTOList.get(position).getCount());

        } else {
            holder.addButton.setVisibility(View.VISIBLE);
            holder.incButton.setVisibility(View.GONE);
            holder.decButton.setVisibility(View.GONE);
            holder.countShow.setVisibility(View.GONE);
        }


    }


    @Override
    public int getItemCount() {
        return productListDTOList.size();
    }

    //get previous added count from DB

    /* private void getPreviousAddedCountFromDB(){

         String ANDROID_MOBILE_ID = Settings.Secure.getString(mListContext.getContentResolver(),
                 Settings.Secure.ANDROID_ID);

     }
 */
    class ProductListAdapterViewHolder extends RecyclerView.ViewHolder {


        ImageView productListImage;
        TextView productListName;
        ImageView productListIndianRupee;
        TextView productListPrice;

        Button addButton;
        ImageView incButton;
        TextView countShow;
        ImageView decButton;


        public ProductListAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            productListImage = itemView.findViewById(R.id.listProductImage);
            productListName = itemView.findViewById(R.id.listProductName);
            productListIndianRupee = itemView.findViewById(R.id.listProductindianRupee);
            productListPrice = itemView.findViewById(R.id.listProductprice);

            addButton = itemView.findViewById(R.id.addButton);
            incButton = itemView.findViewById(R.id.incButton);
            countShow = itemView.findViewById(R.id.countTextView);
            decButton = itemView.findViewById(R.id.decButton);

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int adapterPosition = getAdapterPosition();
                    ProductListDTO productListDTO = productListDTOList.get(adapterPosition);

                    int countAdd = 0;
                    int countAddInc = countAdd + 1;


                    String product_Code = productListDTO.getProductCode();
                    String prouctPrice = productListDTO.getProductPrice();

                    /*int totalPrice=countAddInc*Integer.parseInt(prouctPrice);

                    String add_total_Price=String.valueOf(totalPrice);
*/
                    addCartInDb.addCart(countAddInc, product_Code, prouctPrice);
                    int fragmentCount = loadForFragment();

                    productListDTO.setCount(String.valueOf(countAddInc));
                    notifyDataSetChanged();
                    showDataInFragment.showInFragment(fragmentCount);


                }
            });

            incButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    ProductListDTO productListDTO = productListDTOList.get(adapterPosition);
                    int countIncInc = Integer.parseInt(productListDTO.getCount()) + 1;

                    String product_Code = productListDTO.getProductCode();
                    String prouctPrice = productListDTO.getProductPrice();
                    System.out.println("I am First" + product_Code + "  " + countIncInc + "  " + prouctPrice);


                    /*int total_price=countIncInc*Integer.parseInt(prouctPrice);

                    String inc_total_price=String.valueOf(total_price);
*/
                    updateCartInAddCartInterface.updateCartInAddCart(product_Code, countIncInc, prouctPrice);
                    int fragmentCount = loadForFragment();
                    System.out.println("I am here" + product_Code + "  " + countIncInc + "  " + prouctPrice);
                    System.out.println("I am here" + product_Code + "  " + countIncInc + "  " + prouctPrice);
                    System.out.println("I am here" + product_Code + "  " + countIncInc + "  " + prouctPrice);

                    productListDTO.setCount(String.valueOf(countIncInc));
                    notifyDataSetChanged();
                    showDataInFragment.showInFragment(fragmentCount);
                }
            });

            decButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    ProductListDTO productListDTO = productListDTOList.get(adapterPosition);
                    int countDecDec = Integer.parseInt(productListDTO.getCount());


                    String product_Code = productListDTO.getProductCode();
                    String prouctPrice = productListDTO.getProductPrice();

                    countDecDec = countDecDec - 1;
                    if (countDecDec > 0) {

                        /*int total_price=countDecDec*Integer.parseInt(prouctPrice);
                        String dec_total_price=String.valueOf(total_price);
*/
                        updateCartInAddCartInterface.updateCartInAddCart(product_Code, countDecDec, prouctPrice);

                    } else if (countDecDec == 0) {
                        deleteItemWhenCountZeroInterface.deleteItemWhenCountZero(product_Code);

                    } else if (countDecDec < 0) {
                        return;
                    }


                    int fragmentCount = loadForFragment();
                    productListDTO.setCount(String.valueOf(countDecDec));
                    notifyDataSetChanged();
                    showDataInFragment.showInFragment(fragmentCount);

                }
            });


        }

        private int loadForFragment() {

            String ANDROID_MOBILE_ID = Settings.Secure.getString(mListContext.getContentResolver(),
                    Settings.Secure.ANDROID_ID);


            ApiInterface api = APIClientForViewCart.getApiInterfaceForViewCart();
            AddCartDTO loadFragment = new AddCartDTO(ANDROID_MOBILE_ID);
            Call<JSONResponseViewCartListDTO> call = api.getViewCart(loadFragment);

            call.enqueue(new Callback<JSONResponseViewCartListDTO>() {
                @Override
                public void onResponse(Call<JSONResponseViewCartListDTO> call, Response<JSONResponseViewCartListDTO> response) {
                    if (response.isSuccessful()) {

                        JSONResponseViewCartListDTO jsonResponseViewCartListDTO = response.body();
                        List<ViewCartDTO> viewCart = jsonResponseViewCartListDTO.getViewCartListRecord();

                        /*fragmentCount = 0;
                        for (int i = 0; i < viewCart.size(); i++) {
                            fragmentCount += Integer.parseInt(viewCart.get(i).getCount());
                        }
                        System.out.println("Fragment Total Count" + fragmentCount);*/

                    }

                }

                @Override
                public void onFailure(Call<JSONResponseViewCartListDTO> call, Throwable t) {

                    Toast.makeText(mListContext, t.getMessage(), Toast.LENGTH_LONG).show();

                }
            });

            // notifyDataSetChanged();

            return fragmentCount;

        }
    }


}
