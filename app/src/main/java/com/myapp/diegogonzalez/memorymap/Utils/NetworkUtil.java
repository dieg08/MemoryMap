package com.myapp.diegogonzalez.memorymap.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Diego Gonzalez on 9/16/2015.
 */
public class NetworkUtil  extends AsyncTask<Void, Void, Void>{

    private Context context;
    private boolean result;

    public NetworkUtil(Context context) {
        this.context = context;
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public boolean hasActiveInternetConnection() {
        if (isNetworkAvailable()) {
            try {
                HttpURLConnection urlc =
                        (HttpURLConnection) (new URL("http://clients3.google.com/generate_204").openConnection());
                urlc.setRequestProperty("User-Agent", "Android");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 204 &&
                        urlc.getContentLength() == 0);
            } catch (IOException e) {
                Log.e("MAIN_ACTIVITY", "Error checking internet connection", e);
            }
        } else {
            Log.d("MAIN_ACTIVITY", "No network available!");
            Toast.makeText(context, "No network available!", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean getResult() {
        return result;
    }

    @Override
    protected Void doInBackground(Void... params) {
        result = hasActiveInternetConnection();
        return null;
    }
}
