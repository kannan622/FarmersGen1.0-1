package com.example.saravanamurali.farmersgen.cancelorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonResponseToViewOrderedProductListDTO;


import java.util.List;

public class OrderedProductListView extends AppCompatActivity {

    TextView u_Name;
    TextView g_Total;

    RecyclerView orderedProductListRecyclerView;
    List<JsonResponseToViewOrderedProductListDTO> getIntent;

    OrderedProductListViewAdapter orderedProductListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordered_product_list_view);

        u_Name=(TextView)findViewById(R.id.orderedProductUserName);
        g_Total=(TextView)findViewById(R.id.orderedProductListGrandTotal);


        orderedProductListRecyclerView=(RecyclerView)findViewById(R.id.orderedProductListViewR_View);
        orderedProductListRecyclerView.setLayoutManager(new LinearLayoutManager(OrderedProductListView.this));
        orderedProductListRecyclerView.setHasFixedSize(true);

        orderedProductListViewAdapter=new OrderedProductListViewAdapter(OrderedProductListView.this, getIntent);
        orderedProductListRecyclerView.setAdapter(orderedProductListViewAdapter);

        Intent getIntentOrderedProductList=getIntent();
        String ProductGrandTotal=getIntentOrderedProductList.getStringExtra("ORDERED_PRODUCT_GRANDTOTAL");
        String userNameOfOrderedProduct=getIntentOrderedProductList.getStringExtra("ORDERED_PRODUCT_USER_NAME");

        getIntent=(List<JsonResponseToViewOrderedProductListDTO>)getIntent().getSerializableExtra("ORDERED_PRODUCT_LIST");

        orderedProductListViewAdapter.setOrderedPrdductList(getIntent);

        u_Name.setText(userNameOfOrderedProduct);
        g_Total.setText(ProductGrandTotal);

/*
     for (int i = 0; i < getIntent.size(); i++) {

           String product_N= getIntent.get(i).getOrderedProducName();
           String product_C=getIntent.get(i).getOrderedCount();
           String product_To=getIntent.get(i).getOrderedTotalPrice();
            System.out.println(product_N+" "+product_C+"   " +product_To+" " +ProductGrandTotal+" "+userNameOfOrderedProduct);

        }
*/

    }
}
