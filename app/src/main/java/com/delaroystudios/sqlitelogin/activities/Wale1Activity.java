package com.delaroystudios.sqlitelogin.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
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
import android.widget.Toast;
import android.widget.ViewSwitcher;

/**
 * Created by delaroy on 3/27/17.
 */
public class Wale1Activity extends DesignActivity{
    private ImageButton b1,b2,b3,b4;
    private ImageSwitcher sw;
    private int Price = 3500;
    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wale);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        b1 = (ImageButton) findViewById(R.id.imageButton);
        b2 = (ImageButton) findViewById(R.id.imageButton2);
        b3 = (ImageButton) findViewById(R.id.imageButton3);
        b4 = (ImageButton) findViewById(R.id.imageButton4);
        sw = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        textView = (TextView) findViewById(R.id.textView5);
        button = (Button) findViewById(R.id.button);
        sw.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView myView = new ImageView(getApplicationContext());
                myView.setImageResource(R.drawable.un2);
                myView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                myView.setLayoutParams(new
                        ImageSwitcher.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.WRAP_CONTENT));
                return myView;
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "ลายนี้บวกราคาเพิ่ม 500บาท",
                        Toast.LENGTH_LONG).show();
                sw.setImageResource(R.drawable.a1);
                Bundle bundle = getIntent().getExtras();
                int Price = bundle.getInt("price");
                int total=Price +500;
                textView.setText(Text1+total+Text2);
                SharedPreferences settings = getSharedPreferences("ohm",0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("key", total);
                editor.putInt("key1",5 );
                editor.commit();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "ลายนี้บวกราคาเพิ่ม 200บาท",
                        Toast.LENGTH_LONG).show();
                sw.setImageResource(R.drawable.a3);
                Bundle bundle = getIntent().getExtras();
                int Price = bundle.getInt("price");
                int total=Price +200;
                textView.setText(Text1+total+Text2);
                SharedPreferences settings = getSharedPreferences("ohm",0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("key", total);
                editor.putInt("key1",6 );
                editor.commit();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "ลายนี้บวกราคาเพิ่ม 300บาท",
                        Toast.LENGTH_LONG).show();
                sw.setImageResource(R.drawable.a2);
                Bundle bundle = getIntent().getExtras();
                int Price = bundle.getInt("price");
                int total=Price +300;
                textView.setText(Text1+total+Text2);
                SharedPreferences settings = getSharedPreferences("ohm",0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("key", total);
                editor.putInt("key1",7 );
                editor.commit();
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "ลายนี้บวกราคาเพิ่ม 250บาท",
                        Toast.LENGTH_LONG).show();
                sw.setImageResource(R.drawable.a4);
                Bundle bundle = getIntent().getExtras();
                int Price = bundle.getInt("price");
                int total=Price +250;
                textView.setText(Text1+total+Text2);
                SharedPreferences settings = getSharedPreferences("ohm",0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("key", total);
                editor.putInt("key1",8 );
                editor.commit();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Wale1Activity.this,
                        BodyActivity.class);
                startActivity(myIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            Intent goMain = new Intent(Wale1Activity.this, EditActivity.class);
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
    public void m (String n ){
        Intent myIntent = new Intent(Wale1Activity.this,
                BillActivity.class);
        myIntent.putExtra("Price",n);
        startActivity(myIntent);
    }

}
