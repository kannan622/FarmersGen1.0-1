package com.example.saravanamurali.farmersgen.util;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.saravanamurali.farmersgen.R;


public class Network_config {

    public static boolean is_Network_Connected_flag(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }


    public static void customAlert(final Dialog dialog, Context context, String title, String message) {
        dialog.setContentView(R.layout.dialog_push_notification);
        final TextView tilte_text = dialog.findViewById(R.id.tilte_text);
        final TextView message_text = dialog.findViewById(R.id.message_text);
        tilte_text.setText(title);
        message_text.setText(message);
        Button ok_button = dialog.findViewById(R.id.ok_button);
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
}
