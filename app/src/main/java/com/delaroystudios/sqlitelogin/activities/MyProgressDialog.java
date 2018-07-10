package com.delaroystudios.sqlitelogin.activities;

import android.app.ProgressDialog;
import android.content.Context;

public class MyProgressDialog {
      public static ProgressDialog show(Context c, String msg) {
            final ProgressDialog dialog = new ProgressDialog(c);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage(msg);
            dialog.setIndeterminate(true);
            dialog.show();
            return dialog;
      }
}
