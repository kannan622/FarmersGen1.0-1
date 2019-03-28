package com.example.saravanamurali.farmersgen.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.saravanamurali.farmersgen.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Count_Price_Show_Fragment extends Fragment {

    int fragment_productCount;

    String currentUser;
    String brand_ID_For_ProductList;
    String brand_Name_For_ProductList;
    String brand_Name_For_ProductRating;

    public Count_Price_Show_Fragment() {

    }

    @SuppressLint("ValidFragment")
    public Count_Price_Show_Fragment(String curentUser) {
        this.currentUser=curentUser;
    }

    public void getCountPrice(int productCount) {

        fragment_productCount = productCount;
    }

    TextView textViewName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_count__price__show, container, false);

        textViewName = (TextView) view.findViewById(R.id.viewCart);



        System.out.println("Current User At Fragment"+currentUser);

        textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewCartIntent=new Intent(getActivity(),ViewCartActivity.class);
                startActivity(viewCartIntent);
            }
        });


        /*textViewCount=(TextView)view.findViewById(R.id.showCount);
        textViewPrice=(TextView)view.findViewById(R.id.showPrice);
      //  textViewName.setText(fragment_productName);
        if(fragment_productCount>0){
        textViewCount.setText(""+fragment_productCount);
        textViewPrice.setText(""+fragment_prodcutPrice);
        }
        else{
            textViewCount.setText(" ");
            textViewPrice.setText(" ");
        }
*/
        return view;
    }

}