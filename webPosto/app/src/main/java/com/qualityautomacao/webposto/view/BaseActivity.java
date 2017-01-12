package com.qualityautomacao.webposto.view;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.qualityautomacao.webposto.R;

/**
 * @author Wiliam
 * @since 11/01/2017
 */
public abstract class BaseActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStop() {
        if(progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
        super.onStop();
    }

    public void showLoadDialog(){
        bloqueiaOrientacao();

        if(progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("");
            progressDialog.setMessage(this.getResources().getString(R.string.load));
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        }

        progressDialog.show();
    }

    public void hideLoadDialog(){
        if(progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
        liberaOrientacao();
    }

    protected void bloqueiaOrientacao(){
        switch (this.getResources().getConfiguration().orientation){
            case Configuration.ORIENTATION_LANDSCAPE:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            default:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    protected void liberaOrientacao(){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }
}