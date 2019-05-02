package com.example.saravanamurali.farmersgen.pastorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JsonResponseToViewPastOrderedProductListDTO;


import java.util.ArrayList;
import java.util.List;

public class PastOrderedProductListView extends AppCompatActivity {

    List<JsonResponseToViewPastOrderedProductListDTO> getPastOrderedProductListDTO;

    RecyclerView pastOrderedRecyclerView;

    PastOrderedProductListViewAdapter pastOrderedProductListViewAdapter;

    TextView past_UserName;
    TextView past_GrandTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_ordered_product_list_view);

        past_UserName=(TextView)findViewById(R.id.PastorderedProductUserName);
        past_GrandTotal=(TextView)findViewById(R.id.pastOrderedProductListGrandTotal);

        pastOrderedRecyclerView=(RecyclerView)findViewById(R.id.pastOrderedProductListViewR_View);
        pastOrderedRecyclerView.setLayoutManager(new LinearLayoutManager(PastOrderedProductListView.this));
        pastOrderedRecyclerView.setHasFixedSize(true);

        getPastOrderedProductListDTO=new ArrayList<JsonResponseToViewPastOrderedProductListDTO>();

        pastOrderedProductListViewAdapter=new PastOrderedProductListViewAdapter(getPastOrderedProductListDTO,PastOrderedProductListView.this);

        pastOrderedRecyclerView.setAdapter(pastOrderedProductListViewAdapter);


        Intent getIntentPastOrderedProductList=getIntent();
        String ProductGrandTotal=getIntentPastOrderedProductList.getStringExtra("PAST_ORDERED_PRODUCT_GRANDTOTAL");
        String userNameOfOrderedProduct=getIntentPastOrderedProductList.getStringExtra("PAST_ORDERED_PRODUCT_USER_NAME");

        getPastOrderedProductListDTO=(List<JsonResponseToViewPastOrderedProductListDTO>)getIntent().getSerializableExtra("PAST_ORDERED_PRODUCT_LIST");

        pastOrderedProductListViewAdapter.setPastOrderedProductListView(getPastOrderedProductListDTO);

        past_UserName.setText(userNameOfOrderedProduct);
        past_GrandTotal.setText(ProductGrandTotal);



    }
}
