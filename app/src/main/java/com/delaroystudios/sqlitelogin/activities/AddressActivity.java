package com.delaroystudios.sqlitelogin.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.delaroystudios.sqlitelogin.R;
import com.delaroystudios.sqlitelogin.helper.InputValidation;
import com.delaroystudios.sqlitelogin.model.User;
import com.delaroystudios.sqlitelogin.sql.Constants;
import com.delaroystudios.sqlitelogin.sql.DatabaseHelper;
import com.delaroystudios.sqlitelogin.sql.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.delaroystudios.sqlitelogin.R;
import com.delaroystudios.sqlitelogin.helper.InputValidation;
import com.delaroystudios.sqlitelogin.model.User;
import com.delaroystudios.sqlitelogin.sql.Constants;
import com.delaroystudios.sqlitelogin.sql.DatabaseHelper;
import com.delaroystudios.sqlitelogin.sql.RequestHandler;
import com.delaroystudios.sqlitelogin.sql.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import android.app.ProgressDialog;


/**
 * Created by delaroy on 3/27/17.
 */
public class AddressActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText textInputEditTextName;

    private AppCompatButton appCompatButtonRegister;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        initViews();
        initListeners();
    }

    private void initViews(){
        textInputEditTextName = (TextInputEditText) findViewById(R.id.textInputEditAd);

        appCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonConfirmAD);
        progressDialog = new ProgressDialog(this);
    }

    private void initListeners(){
        appCompatButtonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.appCompatButtonConfirmAD:
                insertOrder();
                emptyInputEditText();
                Intent goMain = new Intent(AddressActivity.this, UsersActivity.class);
                startActivity(goMain);

        }
    }


    private void emptyInputEditText(){
        textInputEditTextName.setText(null);
    }
    private void insertOrder() {
        Bundle bundle = getIntent().getExtras();
        String body1 = bundle.getString("id");
        String body2 = bundle.getString("pic");
        String body3 = bundle.getString("body");
        String body4 = bundle.getString("price");
        final String id = String.valueOf(body1);
        final String pic = String.valueOf(body2);
        final String body = String.valueOf(body3);
        final String price = String.valueOf(body4);

        final String address =textInputEditTextName.getText().toString().trim();
        final String status ="pending";


        progressDialog.setMessage("Update user...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_INSERT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("pic", pic);
                params.put("body", body);
                params.put("price", price);
                params.put("address", address);
                params.put("status", status);
                return params;
            }
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }
}

