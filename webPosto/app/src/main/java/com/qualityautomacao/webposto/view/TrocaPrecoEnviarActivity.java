package com.qualityautomacao.webposto.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.utils.Consumer;
import com.qualityautomacao.webposto.utils.Request;
import com.qualityautomacao.webposto.utils.UtilsWeb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TrocaPrecoEnviarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_troca_preco_enviar);
        alternarComponentes(null);
        ((TimePicker) findViewById(R.id.timePickerTrocaPreco)).setIs24HourView(true);
    }

    public void alternarComponentes(View view) {
        final boolean inicioTurno = ((RadioGroup) findViewById(R.id.radioTroca)).getCheckedRadioButtonId() == R.id.radioButtonInicioTurno;
        findViewById(R.id.timePickerTrocaPreco).setVisibility(inicioTurno ? View.GONE : View.VISIBLE);
        findViewById(R.id.spinnerTurnos).setVisibility(inicioTurno ? View.VISIBLE : View.GONE);
    }

    private void enviarTrocaPreco() {
        UtilsWeb.requisitar(new Request(this, "ALTERA_PRECO_INCLUIR")
                .setDados(getDados())
                .onCompleteRequest(new Consumer<JSONObject>() {
                    @Override
                    public void accept(JSONObject jsonObject) throws Exception {
                        Toast.makeText(TrocaPrecoEnviarActivity.this, "Troca enviada.", Toast.LENGTH_LONG).show();
                    }
                })
                .setFlags(0)
                .onPosExecute(new Runnable() {
                    @Override
                    public void run() {
                        TrocaPrecoEnviarActivity.this.setResult(0);
                        TrocaPrecoEnviarActivity.this.finish();
                    }
                }));
    }

    private String getDados() {
        JSONObject json = new JSONObject();

        try {
            final DatePicker datePicker = (DatePicker) findViewById(R.id.datePickerTrocaPreco);
            json.put("DATA", datePicker.getDayOfMonth() + "-" + datePicker.getMonth() + "-" + datePicker.getYear());

            if (((RadioGroup) findViewById(R.id.radioTroca)).getCheckedRadioButtonId() == R.id.radioButtonInicioTurno)
                json.put("TURNO", ((Spinner) findViewById(R.id.spinnerTurnos)).getSelectedItemPosition() + 1);
            else {
                final TimePicker timePicker = (TimePicker) findViewById(R.id.timePickerTrocaPreco);
                json.put("HORA", timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute() + ":00");
            }

            json.put("TROCAS", new JSONArray(getIntent().getStringExtra("TROCAS")));
        } catch (JSONException e) {
            System.err.println(e);
        }

        return json.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_troca_preco_enviar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_enviar_troca_preco) {
            enviarTrocaPreco();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}