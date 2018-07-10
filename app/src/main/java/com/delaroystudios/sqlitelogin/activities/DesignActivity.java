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
public class DesignActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ImageButton imageButton;
    String Text1 = "ราคา ";
    String Text2 = "บาท";
    int price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // Locate the button in activity_main.xml
        imageButton = (ImageButton) findViewById(R.id.imageButton);
// Capture button clicks
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Start NewActivity.class
                Intent myIntent = new Intent(DesignActivity.this,
                        WaleActivity.class);
                myIntent.putExtra("price", 2500);
                startActivity(myIntent);

            }
        });
        imageButton = (ImageButton) findViewById(R.id.imageButton2);
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Start NewActivity.class
                Intent myIntent = new Intent(DesignActivity.this,
                        Wale1Activity.class);
                myIntent.putExtra("price", 3500);
                startActivity(myIntent);
            }
        });
        imageButton = (ImageButton) findViewById(R.id.imageButton3);
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Start NewActivity.class
                Intent myIntent = new Intent(DesignActivity.this,
                        Wale2Activity.class);
                myIntent.putExtra("price", 3500);
                startActivity(myIntent);
            }
        });
        imageButton = (ImageButton) findViewById(R.id.imageButton4);
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Start NewActivity.class
                Intent myIntent = new Intent(DesignActivity.this,
                        Wale3Activity.class);
                myIntent.putExtra("price", 2000);
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
            Intent goMain = new Intent(DesignActivity.this, EditActivity.class);
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
