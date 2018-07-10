package com.delaroystudios.sqlitelogin.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.provider.DocumentFile;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.delaroystudios.sqlitelogin.R;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import java.io.File;

import io.blackbox_vision.datetimepickeredittext.view.DatePickerEditText;
import io.blackbox_vision.datetimepickeredittext.view.TimePickerEditText;

public class CustomerTransferActivity extends AppCompatActivity {
    private String mOrder_id;
    private String bankname;
    private Activity mActivity;

    private static String TAG = "TAG";
    private static final int READ_REQUEST_CODE = 42;
    private ImageView preview_upload_img;
    private Uri uri = null;
    private int serverResponseCode = 0;
    private ProgressDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_transfer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("แจ้งการโอนเงิน");
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
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 23
                );
            }
        }

        //รหัสคำสั่งซื้อที่ส่งมากับอินเทนต์
        Intent intent = getIntent();
        mOrder_id = intent.getStringExtra("orId");
        bankname = intent.getStringExtra("bank");


        //แสดงหมายเลขคำสั่งซื้อใน TextView
        TextView textView = (TextView) findViewById(R.id.text_order_id);
        textView.append(mOrder_id);

        //ไลบรารี DatePickerEditText สำหรับเลือกวันเดือนปี และเวลาที่โอนเงิน
        DatePickerEditText datePicker =
                (DatePickerEditText) findViewById(R.id.date_picker);

        TimePickerEditText timePicker =
                (TimePickerEditText) findViewById(R.id.time_picker);

        //ต้องเซต FragmentManager ตามข้อกำหนดของไลบรารี
        datePicker.setManager(getSupportFragmentManager());
        timePicker.setManager(getSupportFragmentManager());

        findViewById(R.id.bt_transfer_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transfer();
            }
        });

        preview_upload_img = (ImageView) findViewById(R.id.img_transfer_preview);
        findViewById(R.id.btn_select_transfer_img).setOnClickListener(view -> {
            try {
                Intent select_file = new Intent(Intent.ACTION_GET_CONTENT);
                select_file.addCategory(Intent.CATEGORY_OPENABLE);
                select_file.setType("image/*");
                startActivityForResult(select_file, READ_REQUEST_CODE);

            } catch (ActivityNotFoundException e) {
                Log.d(TAG, "ERROR: " + e.getMessage());
            }
        });

    }

    //เมื่อเข้าสู่ขั้นตอนการโอนเงิน
    private void transfer() {
        String errMsg = "";
        EditText pay_name = (EditText) findViewById(R.id.pay_name);
        EditText editDate = (EditText) findViewById(R.id.date_picker);
        EditText editTime = (EditText) findViewById(R.id.time_picker);
        EditText editAmount = (EditText) findViewById(R.id.edit_amount);


        //ตรวจสอบว่าใส่ข้อมูลครบหรือไม่

        if (editDate.getText().toString().trim().isEmpty()) {
            errMsg = "กรุณาระบุวันเดือนปีที่โอนเงิน";
        } else if (editTime.getText().toString().trim().isEmpty()) {
            errMsg = "กรุณาระบุเวลาที่โอนเงิน";
        } else if (editAmount.getText().toString().trim().isEmpty()) {
            errMsg = "กรุณาระบุจำนวนเงินที่โอน";
        } else if (pay_name.getText().toString().trim().isEmpty()) {
            errMsg = "กรุณาระบุชื่อบัญชีที่โอน";
        } else if (uri == null) {
            errMsg = "กรุณารเลือกไฟล์รูปภาพ";
        }

        if (!errMsg.isEmpty()) {
            MyToast.showLong(this, errMsg);
            return;
        }

        //ส่งข้อมูลการโอนขึ้นไปอัปเดตตาราง orders
        final ProgressDialog dialog = MyProgressDialog.show(mActivity, "กำลังดำเนินการ");


        Ion.with(this)
                .load("http://192.168.1.9//Android/v1/customer-transfer-save.php")
                .setBodyParameter("orId", mOrder_id)
                .setBodyParameter("bank", bankname)
                .setBodyParameter("namebank", pay_name.getText().toString().trim())
                .setBodyParameter("date", editDate.getText().toString().trim())
                .setBodyParameter("time", editTime.getText().toString().trim())
                .setBodyParameter("pay_price", editAmount.getText().toString().trim())
                .setBodyParameter("status", "unchecked")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {

                        if (e != null) {
                            MyToast.showLong(mActivity, e.getMessage());
                        } else if (result == null) {
                            MyToast.showLong(mActivity, result);
                        }

                        upload(uri.getPath().replace("/document/raw:", ""),mOrder_id);
                        dialog.dismiss();
                        MyToast.showLong(mActivity, "เราได้รับมูลการโอนเงินของท่านแล้ว");
                        startActivity(
                                new Intent(mActivity, CustomerOrderHistoryActivity.class)
                                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }
                });

    }

    private void upload(String uploaded_file,String id) {

        Ion.with(this)
                .load("http://192.168.1.9/web/api/upload.php")
//                .uploadProgressBar(uploadProgressBar)
                .setMultipartParameter("id",""+id)
                .setMultipartFile("uploaded_file", "image/*", new File(uploaded_file))
                .asJsonObject()
                .setCallback((e, result) -> {
                    Log.d(TAG, "upload: " + result.toString());
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                uri = resultData.getData();
//                Log.d(TAG, "Uri: " + uri.getPath());
                preview_upload_img.setImageURI(uri);
            }
        }
    }

}

