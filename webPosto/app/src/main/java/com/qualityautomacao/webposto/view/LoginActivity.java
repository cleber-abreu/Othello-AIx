package com.qualityautomacao.webposto.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.model.Token;
import com.qualityautomacao.webposto.notificacao.RegistrationIntentService;
import com.qualityautomacao.webposto.utils.Consumer;
import com.qualityautomacao.webposto.utils.Request;
import com.qualityautomacao.webposto.utils.UtilsPreferences;
import com.qualityautomacao.webposto.utils.UtilsWeb;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @BindView(R.id.editTextLogin) EditText editTextLogin;
    @BindView(R.id.editTextSenha) EditText editTextSenha;
    @BindView(R.id.buttonLogin) Button btnLogin;

    private UtilsPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        preferences = new UtilsPreferences(this);

        editTextLogin.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        editTextLogin.setText(preferences.getPreferences(UtilsPreferences.KEY_LOGIN));
        editTextSenha.setText(preferences.getPreferences(UtilsPreferences.KEY_SENHA));

        if (checkPlayServices()) {
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    @OnClick(R.id.buttonLogin)
    public void login(View view){
        showLoadDialog();

        UtilsWeb.requisitar(new Request(LoginActivity.this, "LOGIN", new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject jsonObject) {
                int ret = jsonObject.optInt("RET", -1);

                if (ret == 2) {
                    loginMultiplasFiliais(jsonObject);
                } else if (ret == 0) {
                    loginUnicaFilial(jsonObject);
                }

                hideLoadDialog();
            }
        }, new Consumer<String>() {
            @Override
            public void accept(String s) {
                hideLoadDialog();
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        }).setDados(getDadosLogin().toString()));

        preferences.setPreferences(UtilsPreferences.KEY_LOGIN, editTextLogin.getText().toString());
        preferences.setPreferences(UtilsPreferences.KEY_SENHA, editTextSenha.getText().toString());
    }

    private JSONObject getDadosLogin(){
        final String usuario = editTextLogin.getText().toString();
        final String senha = editTextSenha.getText().toString();

        try{
            return new JSONObject()
                    .put("USU_DS_LOGIN", usuario)
                    .put("USU_DS_SENHA", senha);
        }catch (Exception e){
            return new JSONObject();
        }

    }

    private void loginUnicaFilial(JSONObject jsonObject){
        try{
            JSONArray obj = jsonObject.getJSONArray("OBJ");

            final JSONObject jsonUnidadeNegocio = obj.getJSONObject(1);

            UtilsWeb.token = new Token(
                    jsonUnidadeNegocio.getInt("UNN_CD_UNIDADE_NEGOCIO"),
                    jsonUnidadeNegocio.getString("UNN_DS_FANTASIA"),
                    obj.getJSONObject(2).getInt("USU_CD_USUARIO"),
                    obj.getJSONObject(3).getInt("PER_CD_PERFIL"),
                    obj.getJSONObject(0).getInt("RED_CD_REDE")
            );

            UtilsWeb.verificarLiberacaoDispositivo(this, new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            });
        }catch (Exception e){
            Log.e("WEB_POSTO_LOG", "loginUnicaFilial: ", e);
        }
    }

    private void loginMultiplasFiliais(JSONObject jsonObject) {
        startActivity(new Intent(this, FilialActivity.class).putExtra("DADOS", jsonObject.toString()));
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(this, getString(R.string.dispositivo_sem_google_play_services), Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }

        return true;
    }
}
