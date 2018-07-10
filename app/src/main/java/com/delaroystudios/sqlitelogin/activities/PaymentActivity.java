package com.delaroystudios.sqlitelogin.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.TextView;
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

/**
 * Created by delaroy on 3/27/17.
 */
public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = PaymentActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutPay1;
    private TextInputLayout textInputLayoutPay2;
    private TextInputLayout textInputLayoutPay3;
    private TextInputLayout textInputLayoutPay4;
    private TextInputLayout textInputLayoutPay5;
    private TextInputLayout textInputLayoutPay6;

    private TextInputEditText textInputEditTextPay1;
    private TextInputEditText textInputEditTextPay2;
    private TextInputEditText textInputEditTextPay3;
    private TextInputEditText textInputEditTextPay4;
    private TextInputEditText textInputEditTextPay5;
    private TextInputEditText textInputEditTextPay6;

    private ProgressDialog progressDialog;

    private AppCompatButton appCompatButtonPay;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        initViews();
        initListeners();
    }

    private void initViews(){
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutPay1 = (TextInputLayout) findViewById(R.id.textInputLayoutPay1);
        textInputLayoutPay2= (TextInputLayout) findViewById(R.id.textInputLayoutPay2);
        textInputLayoutPay3 = (TextInputLayout) findViewById(R.id.textInputLayoutPay3);
        textInputLayoutPay4= (TextInputLayout) findViewById(R.id.textInputLayoutPay4);
        textInputLayoutPay5 = (TextInputLayout) findViewById(R.id.textInputLayoutPay5);
        textInputLayoutPay6 = (TextInputLayout) findViewById(R.id.textInputLayoutPay6);

        textInputEditTextPay1 = (TextInputEditText) findViewById(R.id.textInputEditPay1);
        textInputEditTextPay2 = (TextInputEditText) findViewById(R.id.textInputEditTextPay2);
        textInputEditTextPay3 = (TextInputEditText) findViewById(R.id.textInputEditTextPay3);
        textInputEditTextPay4 = (TextInputEditText) findViewById(R.id.textInputEditTextPay4);
        textInputEditTextPay5 = (TextInputEditText) findViewById(R.id.textInputEditTextPay5);
        textInputEditTextPay6 = (TextInputEditText) findViewById(R.id.textInputEditTextPay6);

        appCompatButtonPay = (AppCompatButton) findViewById(R.id.appCompatButtonPay);
        progressDialog = new ProgressDialog(this);

    }

    private void initListeners(){
        appCompatButtonPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.appCompatButtonPay:
               postDataToBill();
               intent();


                break;
        }
    }

    private void postDataToBill(){
        textInputEditTextPay1.getText().toString();
        textInputEditTextPay2.getText().toString();
        textInputEditTextPay3.getText().toString();
        textInputEditTextPay4.getText().toString();
        textInputEditTextPay5.getText().toString();
        textInputEditTextPay6.getText().toString();
        final AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle("Error! ");
        ad.setIcon(android.R.drawable.btn_star_big_on);
        ad.setPositiveButton("Close", null);
        // Check Username
        if(textInputEditTextPay1.getText().length() == 0)
        {
            ad.setMessage("กรุณากรอกหมายเลขออเดอร์");
            ad.show();
            textInputEditTextPay1.requestFocus();
            return;
        }
// Check Password
        if(textInputEditTextPay2.getText().length() == 0)
        {
            ad.setMessage("กรุณากรอกชื่อบัญชี");
            ad.show();
            textInputEditTextPay2.requestFocus();
            return;
        }
// Check Password and Confirm Password (Match)
        if(textInputEditTextPay3.getText().length() == 0)
        {
            ad.setMessage("กรุณากรอกเลขบัญชี");
            ad.show();
            textInputEditTextPay3.requestFocus();
            return;
        }
// Check Name
        if(textInputEditTextPay4.getText().length() == 0)
        {
            ad.setMessage("กรุณากรอกธนาคารที่โอน");
            ad.show();
            textInputEditTextPay4.requestFocus();
            return;
        }
// Check Email
        if(textInputEditTextPay5.getText().length() == 0)
        {
            ad.setMessage("กรุณากรอกวันที่โอน");
            ad.show();
            textInputEditTextPay5.requestFocus();
            return;
        }
        // Check Tel
        if(textInputEditTextPay6.getText().length() == 0)
        {
            ad.setMessage("กรุณากรอกเวลาที่โอน");
            ad.show();
            textInputEditTextPay6.requestFocus();
            return;
        }
        else{
            insertPay();
        }
    }
    private void insertPay(){
        final String orderid =textInputEditTextPay1.getText().toString().trim();
        final String namepay = textInputEditTextPay2.getText().toString().trim();
        final String numpay =textInputEditTextPay3.getText().toString().trim();
        final String bname =textInputEditTextPay4.getText().toString().trim();
        final String day = textInputEditTextPay5.getText().toString().trim();
        final String time =textInputEditTextPay6.getText().toString().trim();
        final String status ="แจ้งชำระเงินแล้ว";
        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_PAY,
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
                params.put("orderid", orderid);
                params.put("namepay", namepay);
                params.put("numpay", numpay);
                params.put("bname", bname);
                params.put("day", day);
                params.put("time", time);
                params.put("status", status);
                return params;
            }
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }
    private void intent() {
        Intent intent = new Intent(PaymentActivity.this,UsersActivity.class);
        startActivity(intent);
    }
}

