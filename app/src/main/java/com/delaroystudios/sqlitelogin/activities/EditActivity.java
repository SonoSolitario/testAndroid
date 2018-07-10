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
public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = EditActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;


    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;


    private AppCompatButton appCompatButtonRegister;

    private InputValidation inputValidation;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews(){
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutName);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        textInputEditTextName = (TextInputEditText) findViewById(R.id.textInputEditTextName);
        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);;

        appCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);

        progressDialog = new ProgressDialog(this);
    }

    private void initListeners(){
        appCompatButtonRegister.setOnClickListener(this);
    }

    private void initObjects(){
        inputValidation = new InputValidation(activity);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.appCompatButtonRegister:
               editUser();
                emptyInputEditText();
                Intent goMain = new Intent(EditActivity.this, UsersActivity.class);
                startActivity(goMain);
                finish();
                break;
        }
    }


    private void emptyInputEditText(){
        textInputEditTextName.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }
    private void editUser() {
        final String username =textInputEditTextName.getText().toString().trim();
        final String email = textInputEditTextEmail.getText().toString().trim();
        final String password =textInputEditTextPassword.getText().toString().trim();
        final String id = Integer.toString(SharedPrefManager.getInstance(this).getUserId());


        progressDialog.setMessage("Update user...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_EDIT,
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
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                params.put("id", id);
                return params;
            }
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }
}
