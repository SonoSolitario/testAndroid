package com.delaroystudios.sqlitelogin.activities;

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

import com.delaroystudios.sqlitelogin.R;
import com.delaroystudios.sqlitelogin.helper.InputValidation;
import com.delaroystudios.sqlitelogin.model.User;
import com.delaroystudios.sqlitelogin.sql.DatabaseHelper;

/**
 * Created by delaroy on 3/27/17.
 */
public class BodyActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = BodyActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutBody1;
    private TextInputLayout textInputLayoutBody2;
    private TextInputLayout textInputLayoutBody3;
    private TextInputLayout textInputLayoutBody4;
    private TextInputLayout textInputLayoutBody5;
    private TextInputLayout textInputLayoutBody6;

    private TextInputEditText textInputEditTextBody1;
    private TextInputEditText textInputEditTextBody2;
    private TextInputEditText textInputEditTextBody3;
    private TextInputEditText textInputEditTextBody4;
    private TextInputEditText textInputEditTextBody5;
    private TextInputEditText textInputEditTextBody6;

    private AppCompatButton appCompatButtonConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);

        initViews();
        initListeners();
    }

    private void initViews(){
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutBody1 = (TextInputLayout) findViewById(R.id.textInputLayoutBody1);
        textInputLayoutBody2 = (TextInputLayout) findViewById(R.id.textInputLayoutBody2);
        textInputLayoutBody3 = (TextInputLayout) findViewById(R.id.textInputLayoutBody3);
        textInputLayoutBody4 = (TextInputLayout) findViewById(R.id.textInputLayoutBody4);
        textInputLayoutBody5 = (TextInputLayout) findViewById(R.id.textInputLayoutBody5);
        textInputLayoutBody6 = (TextInputLayout) findViewById(R.id.textInputLayoutBody6);

        textInputEditTextBody1 = (TextInputEditText) findViewById(R.id.textInputEditBody1);
        textInputEditTextBody2 = (TextInputEditText) findViewById(R.id.textInputEditTextBody2);
        textInputEditTextBody3 = (TextInputEditText) findViewById(R.id.textInputEditTextBody3);
        textInputEditTextBody4 = (TextInputEditText) findViewById(R.id.textInputEditTextBody4);
        textInputEditTextBody5 = (TextInputEditText) findViewById(R.id.textInputEditTextBody5);
        textInputEditTextBody6 = (TextInputEditText) findViewById(R.id.textInputEditTextBody6);

        appCompatButtonConfirm = (AppCompatButton) findViewById(R.id.appCompatButtonConfirm);

    }

    private void initListeners(){
        appCompatButtonConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.appCompatButtonConfirm:
                postDataToBill();
                break;
        }
    }

    private void postDataToBill(){
         textInputEditTextBody1.getText().toString();
         textInputEditTextBody2.getText().toString();
         textInputEditTextBody3.getText().toString();
         textInputEditTextBody4.getText().toString();
         textInputEditTextBody5.getText().toString();
         textInputEditTextBody6.getText().toString();
       final AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle("Error! ");
        ad.setIcon(android.R.drawable.btn_star_big_on);
        ad.setPositiveButton("Close", null);
        // Check Username
        if(textInputEditTextBody1.getText().length() == 0)
        {
            ad.setMessage("กรุณากรอกรอบอก");
            ad.show();
            textInputEditTextBody1.requestFocus();
            return;
        }
// Check Password
        if(textInputEditTextBody2.getText().length() == 0)
        {
            ad.setMessage("กรุณากรอกรอบเอว");
            ad.show();
            textInputEditTextBody2.requestFocus();
            return;
        }
// Check Password and Confirm Password (Match)
        if(textInputEditTextBody3.getText().length() == 0)
        {
            ad.setMessage("กรุณากรอกขนาดสะโพก");
            ad.show();
            textInputEditTextBody3.requestFocus();
            return;
        }
// Check Name
        if(textInputEditTextBody4.getText().length() == 0)
        {
            ad.setMessage("กรุณากรอกความยาวไหล่");
            ad.show();
            textInputEditTextBody4.requestFocus();
            return;
        }
// Check Email
        if(textInputEditTextBody5.getText().length() == 0)
        {
            ad.setMessage("กรุณากรอกขนาดลำตัว");
            ad.show();
            textInputEditTextBody5.requestFocus();
            return;
        }
        // Check Tel
        if(textInputEditTextBody6.getText().length() == 0)
        {
            ad.setMessage("กรุณากรอกรอบต้นแขน");
            ad.show();
            textInputEditTextBody6.requestFocus();
            return;
        }
        else{
            intent();
        }
        }
        private void intent(){
        Intent intent = new Intent(BodyActivity.this, BillActivity.class);
        intent.putExtra("Body1",textInputEditTextBody1.getText().toString() );
        intent.putExtra("Body2", textInputEditTextBody2.getText().toString());
        intent.putExtra("Body3", textInputEditTextBody3.getText().toString());
        intent.putExtra("Body4", textInputEditTextBody4.getText().toString());
        intent.putExtra("Body5", textInputEditTextBody5.getText().toString());
        intent.putExtra("Body6", textInputEditTextBody6.getText().toString());
        startActivity(intent);

    }
}
