package com.qualityautomacao.webposto.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.utils.Consumer;
import com.qualityautomacao.webposto.utils.UtilsWeb;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) throws Exception {
        UtilsWeb.requisitar(this, "LOGIN", getDadosLogin().toString(), new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject jsonObject) throws Exception {
                final int ret = jsonObject.getInt("RET");

                if (ret == 2)
                    loginMultiplasFiliais(jsonObject);
                else if (ret == 0)
                    loginUnicaFilial();
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

    private void loginUnicaFilial() {

    }

    private void loginMultiplasFiliais(JSONObject jsonObject) {
        startActivity(new Intent(this, FilialActivity.class).putExtra("DADOS", jsonObject.toString()));
    }
}
