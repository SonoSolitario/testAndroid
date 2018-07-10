package com.delaroystudios.sqlitelogin.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.delaroystudios.sqlitelogin.R;
import com.delaroystudios.sqlitelogin.sql.Constants;
import com.delaroystudios.sqlitelogin.sql.RequestHandler;
import com.delaroystudios.sqlitelogin.sql.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BillActivity extends Activity{
    private ImageView imageView;
    private TextView textData;
    private  Button button;
    private ProgressDialog progressDialog;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        imageView = (ImageView) findViewById(R.id.imageView55);
        progressDialog = new ProgressDialog(this);
        button =(Button) findViewById(R.id.buttonBill);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertOrder();
            }
        });SharedPreferences settings = getSharedPreferences("ohm", 0);
        int  value = settings.getInt("key",0);
        int value2 = settings.getInt("key1",0);
        if (value2==1){
            imageView.setImageResource(R.drawable.f1);
        }
        else if(value2==2){
            imageView.setImageResource(R.drawable.f2);

        }
        else if(value2==3){
            imageView.setImageResource(R.drawable.f3);

        }
        else if(value2==4){
            imageView.setImageResource(R.drawable.f4);

        }
        else if(value2==5){
            imageView.setImageResource(R.drawable.a1);

        }
        else if(value2==6){
            imageView.setImageResource(R.drawable.a3);

        }
        else if(value2==7){
            imageView.setImageResource(R.drawable.a2);

        }
        else if(value2==8){
            imageView.setImageResource(R.drawable.a4);

        }
        else if(value2==9){
            imageView.setImageResource(R.drawable.b1);

        }
        else if(value2==10){
            imageView.setImageResource(R.drawable.b3);

        }
        else if(value2==11){
            imageView.setImageResource(R.drawable.b2);

        }
        else if(value2==12){
            imageView.setImageResource(R.drawable.b4);

        }
        else if(value2==13){
            imageView.setImageResource(R.drawable.c1);

        }
        else if(value2==14){
            imageView.setImageResource(R.drawable.c3);

        }
        else if(value2==15){
            imageView.setImageResource(R.drawable.c2);

        }
        else if(value2==16){
            imageView.setImageResource(R.drawable.c4);

        }
        Bundle bundle = getIntent().getExtras();
        String body1 = bundle.getString("Body1");
        String body2 = bundle.getString("Body2");
        String body3 = bundle.getString("Body3");
        String body4 = bundle.getString("Body4");
        String body5 = bundle.getString("Body5");
        String body6  =bundle.getString("Body6");
        textData = (TextView)findViewById(R.id.textView555);
        textData.setText("รอบอก = " + String.valueOf(body1)+ " เซนติเมตร" + "\n"
                + "รอบเอว = " + String.valueOf(body2)+ " เซนติเมตร"+"\n"
                + "ขนาดสะโพก = " + String.valueOf(body3)+" เซนติเมตร" +"\n"
                + "ความยาวไหล่ = " + String.valueOf(body4)+" เซนติเมตร"+"\n"
                + "ขนาดลำตัว = " + String.valueOf(body5)+" เซนติเมตร"+"\n"
                + "รอบต้นแขน = " + String.valueOf(body6)+" เซนติเมตร"+"\n"
                + "\n"+ "ราคา = " + value+" บาท");

    }
    private void insertOrder() {
        SharedPreferences settings = getSharedPreferences("ohm", 0);
        int  value = settings.getInt("key",0);
        int value2 = settings.getInt("key1",0);
        final String id = Integer.toString(SharedPrefManager.getInstance(this).getUserId());
        final String pic= Integer.toString(value2);
        Bundle bundle = getIntent().getExtras();
        String body1 = bundle.getString("Body1");
        String body2 = bundle.getString("Body2");
        String body3 = bundle.getString("Body3");
        String body4 = bundle.getString("Body4");
        String body5 = bundle.getString("Body5");
        String body6  =bundle.getString("Body6");
        final String body ="รอบอก = " + String.valueOf(body1)+ " เซนติเมตร" + "\n"
                + "รอบเอว = " + String.valueOf(body2)+ " เซนติเมตร"+"\n"
                + "ขนาดสะโพก = " + String.valueOf(body3)+" เซนติเมตร" +"\n"
                + "ความยาวไหล่ = " + String.valueOf(body4)+" เซนติเมตร"+"\n"
                + "ขนาดลำตัว = " + String.valueOf(body5)+" เซนติเมตร"+"\n"
                + "รอบต้นแขน = " + String.valueOf(body6)+" เซนติเมตร";
        final String price =Integer.toString(value);
        Intent intent = new Intent(BillActivity.this, AddressActivity.class);
        intent.putExtra("id",id );
        intent.putExtra("pic", pic);
        intent.putExtra("body", body);
        intent.putExtra("price",price);
        startActivity(intent);


}}