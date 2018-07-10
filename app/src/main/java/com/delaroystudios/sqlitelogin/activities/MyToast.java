package com.delaroystudios.sqlitelogin.activities;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class MyToast {
      public static void showLong(Context context, String msg) {
            show(context, msg, Toast.LENGTH_LONG);
      }

      public static void showShort(Context context, String msg) {
            show(context, msg, Toast.LENGTH_SHORT);
      }

      private static void show(Context context, String msg, int length) {
            Toast t = Toast.makeText(context, msg, length);
            t.setGravity(Gravity.CENTER, 0, 0);
            t.show();
      }
}
