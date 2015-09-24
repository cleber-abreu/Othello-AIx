package com.qualityautomacao.webposto.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.model.Token;
import com.qualityautomacao.webposto.notificacao.RegistrationIntentService;
import com.qualityautomacao.webposto.utils.Consumer;
import com.qualityautomacao.webposto.utils.UtilsWeb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (checkPlayServices()) {
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    public void login(View view) throws Exception {
        UtilsWeb.requisitar(this, "LOGIN", getDadosLogin().toString(), new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject jsonObject) throws Exception {
                final int ret = jsonObject.getInt("RET");

                if (ret == 2)
                    loginMultiplasFiliais(jsonObject);
                else if (ret == 0)
                    loginUnicaFilial(jsonObject);
            }
        });
    }

    private JSONObject getDadosLogin() throws Exception {
        final String usuario = ((EditText) findViewById(R.id.editTextLogin)).getText().toString();
        final String senha = ((EditText) findViewById(R.id.editTextSenha)).getText().toString();

        return new JSONObject()
                .put("USU_DS_LOGIN", usuario)
                .put("USU_DS_SENHA", senha);
    }

    private void loginUnicaFilial(JSONObject jsonObject) throws JSONException {
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
