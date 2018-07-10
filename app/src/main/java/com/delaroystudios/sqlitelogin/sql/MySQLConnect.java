package com.delaroystudios.sqlitelogin.sql;

import android.app.Activity;
import android.os.Build;
import android.os.StrictMode;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MySQLConnect {

    private final Activity main;
    private List<String> list;
    private String URL = "http://192.168.1.9/", GET_URL = "android/get_post.php?status=0", SENT_URL = "android/sent_post.php";

    public MySQLConnect(){
        main = null;
    }

    public MySQLConnect(Activity mainA){
        main = mainA;
        list = new ArrayList<String>();
    }

    public List<String> getData(){

        String url = URL + GET_URL;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);
                Toast.makeText(main, list.get(0), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(main, error.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(main.getApplicationContext());
        requestQueue.add(stringRequest);

        return list;
    }

    public void showJSON(String response){
        String comment = "";

        try{

            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");

            for(int i = 0; i < result.length(); i++){
                JSONObject collectData = result.getJSONObject(i);
                comment = collectData.getString("comment");
                list.add(comment);
            }

        }catch(JSONException ex){ ex.printStackTrace(); }
    }

}
