package com.recsmeterreading.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.recsmeterreading.Utils.Utils;


public class NetworkChangeReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(final Context context, Intent intent) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        final boolean isConnected = wifi != null && wifi.isConnectedOrConnecting() || mobile != null && mobile.isConnectedOrConnecting();

        /*This checked when the internet is connected in the device it autmaticcally detects the connectiona and use to sync data when internet is available*/
        if(isConnected) {
//            if(!((Utilities.getSharedInstance().currentActivityDetails instanceof SplashScreenActivity) ||
//                    (Utilities.getSharedInstance().currentActivityDetails instanceof SignInActivity)
//                    || ((Utilities.getSharedInstance().currentActivityDetails instanceof OTPVerificationActivity)) ||
//                    ((Utilities.getSharedInstance().currentActivityDetails instanceof ResetPasswordActivity)))) {
                Utils.getSharedInstance().whenInternetisEnabled(context, true);
//            }
        }

    }
}