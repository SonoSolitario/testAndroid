package com.delaroystudios.sqlitelogin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;

import com.delaroystudios.sqlitelogin.R;
import com.delaroystudios.sqlitelogin.sql.SharedPrefManager;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by delaroy on 3/27/17.
 */
public class BankActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Locate the button in activity_main.xml
        imageButton = (ImageButton) findViewById(R.id.b1);
        Intent intent = getIntent();
        final String mOrder_id = intent.getStringExtra("orId");
// Capture button clicks
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Start NewActivity.class
                Intent myIntent = new Intent(BankActivity.this,
                        CustomerTransferActivity.class);
                myIntent.putExtra("bank","1");
                myIntent.putExtra("orId",mOrder_id);
                startActivity(myIntent);

            }
        });
        imageButton = (ImageButton) findViewById(R.id.b2);
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Start NewActivity.class
                Intent myIntent = new Intent(BankActivity.this,
                        CustomerTransferActivity.class);
                myIntent.putExtra("bank", "2");
                myIntent.putExtra("orId",mOrder_id);
                startActivity(myIntent);
            }
        });
        imageButton = (ImageButton) findViewById(R.id.b3);
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Start NewActivity.class
                Intent myIntent = new Intent(BankActivity.this,
                        CustomerTransferActivity.class);
                myIntent.putExtra("bank", "3");
                myIntent.putExtra("orId",mOrder_id);
                startActivity(myIntent);
            }
        });
        imageButton = (ImageButton) findViewById(R.id.b4);
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Start NewActivity.class
                Intent myIntent = new Intent(BankActivity.this,
                        CustomerTransferActivity.class);
                myIntent.putExtra("bank", "4");
                myIntent.putExtra("orId",mOrder_id);
                startActivity(myIntent);
            }
        });
        imageButton = (ImageButton) findViewById(R.id.b5);
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Start NewActivity.class
                Intent myIntent = new Intent(BankActivity.this,
                        CustomerTransferActivity.class);
                myIntent.putExtra("bank", "5");
                myIntent.putExtra("orId",mOrder_id);
                startActivity(myIntent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.member) {
            finish();
            Intent goMain = new Intent(BankActivity.this, EditActivity.class);
            startActivity(goMain);


            return true;
        } else if (id == R.id.logout) {
            SharedPrefManager.getInstance(this).logout();
            finish();
            startActivity(new Intent(this, LoginActivity.class));


            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}