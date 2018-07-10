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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.delaroystudios.sqlitelogin.R;
import com.delaroystudios.sqlitelogin.sql.SharedPrefManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class CustomerOrderHistoryActivity extends AppCompatActivity {
      private Activity mActivity;
      private ArrayList<String> mItemArray;
      private ArrayList mOrderIdArray;
      private ListView mListView;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_customer_order_history);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("ประวัติการสั่งซื้อ");
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
            mListView = (ListView)findViewById(R.id.listview);
            mOrderIdArray = new ArrayList();

            loadOrderHistory();
      }

      //ส่ง Request ไปอ่านข้อมูลประวัติการสั่งซื้อของลูกค้ารายนั้นมาแสดง
      //โดยไม่จำเป็นต้องส่งข้อมูลใดๆ ขึ้นไป เพราะทางฝั่งเซิร์ฟเวอร์ได้บันทึกสถานะการเข้าสู่ระบบเอาไว้แล้ว
      private void loadOrderHistory() {
            final String id = Integer.toString(SharedPrefManager.getInstance(this).getUserId());
            mItemArray = new ArrayList<>();
            final ProgressDialog dialog = MyProgressDialog.show(mActivity, "กำลังดำเนินการ");
            Ion.with(this)
                    .load( "http://192.168.1.9//Android/v1/customer-order-history.php")
                    .setBodyParameter("id", id)
                    .asJsonArray()
                    .setCallback(new FutureCallback<JsonArray>() {
                          @Override
                          public void onCompleted(Exception e, JsonArray result) {
                                dialog.dismiss();
                                if(e != null) {
                                      MyToast.showLong(mActivity, e.getMessage());
                                      return;
                                } else if(result == null) {
                                      MyToast.showLong(mActivity, "ไม่พบข้อมูล");
                                      return;
                                }

                                //อ่านข้อมูลการสั่งซื้อแต่ละรายการจะอยู่ในรูปแบบ Json Object
                                //ซึ่งจะอ่านค่าจากแต่ละ Property มารวมเป็นสตริงเดียวกัน
                                //เพื่อแสดงผลใน TextView อันเดียวกัน
                                JsonObject jsObject;
                                String item = "";
                                String pay_status = "";
                                for(int i = 0; i < result.size(); i++) {
                                      item = "\n";
                                      jsObject = (JsonObject)result.get(i);
                                      item += "รหัสการสั่งซื้อ:  #" + jsObject.get("orId").getAsString() + "\n";
                                      item += "วันเวลาสั่งซื้อ:  " + jsObject.get("order_datetime").getAsString() + "\n";
                                      item += "ยอดที่ต้องชำระ:  " + jsObject.get("price").getAsString() + " บาท\n";
                                      pay_status = jsObject.get("status").getAsString();

                                      //สถานะการโอนเงินที่เก็บในฐานข้อมูลจะเป็นอย่างใดอย่างหนึ่งระหว่าง
                                      //pending, unchecked, incomplete, paid
                                      if(pay_status.equals("pending")) {
                                            pay_status = "ยังไม่ชำระเงิน";
                                      } else if(pay_status.equals("unchecked")) {
                                            pay_status = "ชำระเงินแล้ว รอตรวจสอบ";
                                      } else if(pay_status.equals("incomplete")) {
                                            pay_status = "การชำระไม่สมบูรณ์";
                                      } else if(pay_status.equals("paid")) {
                                            pay_status = "กำลังตัดเย็บชุด";
                                      }else if(pay_status.equals("paid1")) {
                                            pay_status = "จัดส่งแล้ว";
                                      }else if(pay_status.equals("paid2")) {
                                            pay_status = "ลูกค้าได้รับสินค้าแล้ว";
                                      }
                                      item += "การชำระเงิน:  " + pay_status + "\n";
                                      item += "\n";

                                      mItemArray.add(item);
                                      mOrderIdArray.add(jsObject.get("orId").getAsString());
                                }

                                //เลย์เอาต์ของรายการ จะสร้างจาก TextView เพียงอันเดียว (ดูที่ไฟล์ item_customer_order_history.xml)
                                //เพื่อให้ตกแต่งได้เล็กน้อย (ถ้าใช้เลย์เอาต์พื้นฐานของ ListView จะตกแต่งได้ยาก)
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(mActivity, R.layout.item_customer_order_history, mItemArray);  //android.R.layout.simple_list_item_1
                                mListView.setAdapter(adapter);

                                //เมื่อคลิกที่รายการสั่งซื้อใด จะเปิดไปยังแอคทิวิตี้ที่รับข้อมูลการโอนเงินของคำสั่งซื้อนั้น
                                //พร้อมกับแนบรหัสคำสั่งซื้อไปกับอินเทนต์ด้วย
                                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                      @Override
                                      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            String orId = mOrderIdArray.get(position).toString();
                                            Intent intent = new Intent(mActivity, BankActivity.class);
                                            intent.putExtra("orId", orId);
                                            startActivity(intent);
                                      }
                                });

                                MyToast.showLong(mActivity, "แตะที่รายการสั่งซื้อเพื่อแจ้งการโอนเงิน");
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
