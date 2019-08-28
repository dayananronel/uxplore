package com.example.uxplore.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class ScreenNotification  extends AppCompatActivity {

    ProgressDialog mProgressDialog;

    public void showToast(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();

    }
    public  void showSnackbar(View view, String message){
        Snackbar.make(view,message,Snackbar.LENGTH_SHORT).show();
    }
    public void progressDialog(Context context, String title, String message) {
        try {
            if (!isFinishing() && mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(context);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setCancelable(false);
                mProgressDialog.setInverseBackgroundForced(true);
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.setMessage(title + "\n" + message);
                mProgressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void hideProgressDialog(Context context) {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing())
                if (mProgressDialog != null && !isFinishing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

