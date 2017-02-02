package com.qualityautomacao.webposto.view;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.qualityautomacao.webposto.R;

/**
 * @author Wiliam
 * @since 11/01/2017
 */
public abstract class BaseActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;

    @Override
    protected void onStop() {
        if(progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    protected void addToolbar(boolean backButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(backButton);
        getSupportActionBar().setDisplayShowHomeEnabled(backButton);
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

    public void showMessage(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public void showMessage(@StringRes int resString) {
        Toast.makeText(this, resString, Toast.LENGTH_SHORT).show();
    }
}
