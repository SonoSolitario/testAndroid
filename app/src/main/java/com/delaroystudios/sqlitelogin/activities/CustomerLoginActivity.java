package com.delaroystudios.sqlitelogin.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.delaroystudios.sqlitelogin.R;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class CustomerLoginActivity extends AppCompatActivity {
      private Activity mActivity;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_customer_login);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("เข้าสู่ระบบ");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                  }
            });
            fab.hide();

            mActivity = this;

            //เมื่อคลิกปุุ่ม ขั้นตอนถัดไป ให้อ่านเบอร์โทรและรหัสผ่าน
            //จากนั้นส่งขึ้นไปตรวจสอบว่ามีตรงกับของลูกค้าคนใดหรือไม่
            //หากไม่มีข้อผิดพลาด (ไม่มีข้อความส่งกลับมา) แสดงว่า ถูกต้อง
            //ก็เปิดไปยังแอคทิวิตี้แสดงประวัติการสั่งซื้อ
            //โดยทางฝั่งเซิร์ฟเวอร์จะจัดเก็บสถานะของการเข้าสู่ระบบเอาไว้ จึงไม่จำเป็นต้องส่งข้อมูลไปยังแอคทิวิตี้ถัดไป
            findViewById(R.id.bt_next_step)
                    .setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                                EditText editPhone = (EditText)findViewById(R.id.edit_phone);
                                String phone = editPhone.getText().toString();

                                EditText editPassword = (EditText)findViewById(R.id.edit_password);
                                String password = editPassword.getText().toString();

                                final ProgressDialog dialog = MyProgressDialog.show(mActivity, "กำลังดำเนินการ");
                                Ion.with(mActivity)
                                        .load(StoreBaseUrl.get() + "customer-login.php")
                                        .setBodyParameter("phone", phone)
                                        .setBodyParameter("password", password)
                                        .asString()
                                        .setCallback(new FutureCallback<String>() {
                                              @Override
                                              public void onCompleted(Exception e, String result) {
                                                    dialog.dismiss();
                                                    if(!result.isEmpty())  {
                                                          MyToast.showLong(mActivity, result);
                                                          return;
                                                    }

                                                    Intent intent = new Intent(mActivity, CustomerOrderHistoryActivity.class);
                                                    startActivity(intent);
                                              }
                                        });
                          }
                    });
      }

      @Override
      public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if(id == android.R.id.home) {
                  onBackPressed();
                  return true;
            }
            return super.onOptionsItemSelected(item);
      }

}

