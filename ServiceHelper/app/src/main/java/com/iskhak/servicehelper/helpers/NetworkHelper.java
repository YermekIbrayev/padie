package com.iskhak.servicehelper.helpers;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.iskhak.servicehelper.ui.EstimatedOrderActivity;
import com.iskhak.servicehelper.ui.MainActivity;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.adapter.rxjava.HttpException;


public class NetworkHelper{

    private Context context;
    private static String GET_LIST_URL = DataHolder.URL_ADDRESS + "serviceList";
    private static String SEND_DATA_URL = DataHolder.URL_ADDRESS +"crunchifyService";

    public NetworkHelper(Context context){
        this.context = context;
       // new Connect2Server().execute("GET", GET_LIST_URL,"");
    }

    /**
     * Returns true if the Throwable is an instance of RetrofitError with an
     * http status code equals to the given one.
     */
    public static boolean isHttpStatusCode(Throwable throwable, int statusCode) {
        return throwable instanceof HttpException
                && ((HttpException) throwable).code() == statusCode;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public void sendJSON(){
        JSONObject jsonObject =DataHolder.getInstance().generateJSON();
        new Connect2Server().execute("POST", SEND_DATA_URL, jsonObject.toString());

    }

    private class Connect2Server extends AsyncTask<String, Void, String> {
        String httpMethod;

        //Must be 3 params
        //String httpMethod = params[0];
        //String url = params[1];
        //String body = params[2];
        @Override
        protected String doInBackground(String... params) {
            httpMethod = params[0];
/*            if(httpMethod.equals("POST")){

                return makeCall(params);
            }*/
            return makeCall(params);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            if(httpMethod.equals("POST")){
                Toast.makeText(context, "Data Sent!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, EstimatedOrderActivity.class);
                int totalSum = DataHolder.getInstance().getTotalSum(result);
                intent.putExtra("TotalSum", totalSum);
                context.startActivity(intent);
            }else if(httpMethod.equals("GET")){
                if(result!=null) {
                    DataHolder.getInstance().setServiceListJson(result);
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
            }
        }
    }

    public String makeCall(String... params) {
        String httpMethod = params[0];
        String url = params[1];
        String body = params[2];
        Log.i(DataHolder.TAG, "Making the call to " + url /*+ " accessToken = " + userPrefs.getAccessToken()*/ +"\nBody: \n" + body);
        String result = "";
        HttpURLConnection urlConnection = null;
        try {
            URL u = new URL(url);
            urlConnection = (HttpURLConnection) u.openConnection();
            urlConnection.setChunkedStreamingMode(0);
            urlConnection.setRequestMethod(httpMethod);
            urlConnection.setRequestProperty("Content-Type", "application/json");

            if ("POST".equals(httpMethod)) {
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8");
                writer.write(body);
                writer.flush();
            }

            InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream(), "UTF-8");
            result = read(reader);
            Log.d("POST", "RESULT = " + result);
            return result;
        } catch (Exception e) {
            Log.w(DataHolder.TAG, "Exception: " + e);
            //setStatus(500);
        } finally {
            if(urlConnection!=null)
                urlConnection.disconnect();
        }
        return result;
    }
    static String read(InputStreamReader reader) throws IOException {
        char[] chars = new char[1024];
        StringBuffer buf = new StringBuffer();
        int len;
        while ((len = reader.read(chars)) != -1) {
            buf.append(chars, 0, len);
        }
        return buf.toString();
    }
}
