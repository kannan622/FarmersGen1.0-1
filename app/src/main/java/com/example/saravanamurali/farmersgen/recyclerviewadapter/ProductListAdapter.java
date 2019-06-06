package com.example.saravanamurali.farmersgen.recyclerviewadapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.fragment.Product_List_Activity;
import com.example.saravanamurali.farmersgen.models.AddCartDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseViewCartListDTO;
import com.example.saravanamurali.farmersgen.models.ProductListDTO;
import com.example.saravanamurali.farmersgen.models.ViewCartDTO;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientForViewCart;
import com.example.saravanamurali.farmersgen.sqllite.ProductAddInSqlLite;
import com.example.saravanamurali.farmersgen.util.Network_config;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListAdapterViewHolder> {

    Context mListContext;
    List<ProductListDTO> productListDTOList;

    AddCart addCartInDb;

    private Dialog dialog;


    public interface AddCart {

        public void addCartInSqlLite(int countNum, String product_Code, String productPrice);

        public void updateCartInSqlLite(String updateProductCode, int updateCount, String updateprouctPrice);

        public void deleteItemWhenCountBecomesZero(String product_Code);

        public void deleteItemWhenCountZeroInServer(String product_Code);

        public void showInFragment();

        void OnProductItemClick(int position);

        void onImageClick(String imageCode);

    }

    public ProductListAdapter(Context mListContext, List<ProductListDTO> productListDTOList) {
        this.mListContext = mListContext;
        this.productListDTOList = productListDTOList;
    }


    public void setAddCart(AddCart addCountInDb) {
        this.addCartInDb = addCountInDb;
    }

    public void setDataSetChanged(List<ProductListDTO> productListDTOList) {
        this.productListDTOList = productListDTOList;
        notifyDataSetChanged();

    }
    {

    }
   public void addCount(String count,String productCode,String Price){

        int p_count=Integer.parseInt(count);

       addCartInDb.addCartInSqlLite(p_count, productCode, Price);

    }



    public int getCartCount() {
        int count = 0;
        for (ProductListDTO product : productListDTOList) {
            if (product.getCount() != null && !product.getCount().isEmpty())
                count = count + Integer.parseInt(product.getCount());
        }
        return count;
    }


    @NonNull
    @Override
    public ProductListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mListContext);
        View view = inflater.inflate(R.layout.productlist_adapterview, parent, false);

        dialog=new Dialog(mListContext);

        ProductListAdapterViewHolder productListAdapterViewHolder = new ProductListAdapterViewHolder(view);

        return productListAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapterViewHolder holder, int position) {

        holder.productListName.setText(productListDTOList.get(position).getProductName());

        holder.productListPrice.setText("₹ " + productListDTOList.get(position).getProductPrice());
        holder.productQuantity.setText("" + productListDTOList.get(position).getProductQuantity());
        holder.list_actualprice.setText("₹" + productListDTOList.get(position).getAcutalPrice());

       /* final ProgressDialog csprogress;
        csprogress = new ProgressDialog(mListContext);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCancelable(false);
        csprogress.setCanceledOnTouchOutside(false);

*/
        Picasso.with(mListContext).load(productListDTOList.get(position).getProductImage()).into(holder.productListImage);

        /*if (csprogress.isShowing()){
            csprogress.dismiss();
        }*/

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

    private void removeItem(int adapterPosition) {

        productListDTOList.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
    }



    class ProductListAdapterViewHolder extends RecyclerView.ViewHolder {


        ImageView productListImage;
        TextView productListName;
        //  ImageView productListIndianRupee;
        TextView productListPrice, list_actualprice, productQuantity;

        Button addButton;
        ImageView incButton;
        TextView countShow;
        ImageView decButton;


        public ProductListAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            productQuantity = itemView.findViewById(R.id.product_quantity_avaliable);

            productListImage = itemView.findViewById(R.id.listProductImage);
            productListName = itemView.findViewById(R.id.listProductName);
            //  productListIndianRupee = itemView.findViewById(R.id.listProductindianRupee);
            productListPrice = itemView.findViewById(R.id.listProductprice);
            list_actualprice = itemView.findViewById(R.id.actualprice);

            Typeface roboto=Typeface.createFromAsset(mListContext.getAssets(),"fonts/Roboto-Medium.ttf");
            productListName.setTypeface(roboto);
            productListPrice.setTypeface(roboto);

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


                    addCartInDb.addCartInSqlLite(countAddInc, product_Code, prouctPrice);
                    notifyDataSetChanged();

                    // addCartInDb.addCart(countAddInc, product_Code, prouctPrice);

                    productListDTO.setCount(String.valueOf(countAddInc));
                    addCartInDb.showInFragment();


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


                    addCartInDb.updateCartInSqlLite(product_Code, countIncInc, prouctPrice);

                    notifyDataSetChanged();
                    //updateCartInAddCartInterface.updateCartInAddCart(product_Code, countIncInc, prouctPrice);

                    productListDTO.setCount(String.valueOf(countIncInc));

                    //notifyDataSetChanged();
                    addCartInDb.showInFragment();
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

                        addCartInDb.updateCartInSqlLite(product_Code, countDecDec, prouctPrice);

                        // updateCartInAddCartInterface.updateCartInAddCart(product_Code, countDecDec, prouctPrice);

                    } else if (countDecDec == 0) {

                        //removeItem(adapterPosition);

                        addCartInDb.deleteItemWhenCountBecomesZero(product_Code);

                        if (Network_config.is_Network_Connected_flag(mListContext)) {

                            addCartInDb.deleteItemWhenCountZeroInServer(product_Code);

                        }

                        else {
                            Network_config.customAlert(dialog, mListContext, mListContext.getResources().getString(R.string.app_name),
                                    mListContext.getResources().getString(R.string.connection_message));
                        }

                    } else if (countDecDec < 0) {
                        return;
                    }


                    productListDTO.setCount(String.valueOf(countDecDec));
                    notifyDataSetChanged();
                    addCartInDb.showInFragment();

                }
            });

            productListImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Network_config.is_Network_Connected_flag(mListContext)) {

                        int imageAdapterPosition = getAdapterPosition();
                        ProductListDTO productListDTO = productListDTOList.get(imageAdapterPosition);

                        String imageProductCode = productListDTO.getProductCode();

                        addCartInDb.onImageClick(imageProductCode);

                    }

                    else {
                        Network_config.customAlert(dialog, mListContext, mListContext.getResources().getString(R.string.app_name),
                                mListContext.getResources().getString(R.string.connection_message));
                    }
                }
            });


        }


    }



}
