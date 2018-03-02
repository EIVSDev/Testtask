package com.mycompany.testtask;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

/**
 * Created by Slava on 26.02.2018.
 */

class InternetConnection {

    public static boolean checkConnection(@NonNull Context context){

        return ((ConnectivityManager)context.getSystemService
                (Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo()!=null;

    }

}
