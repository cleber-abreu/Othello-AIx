package com.qualityautomacao.webposto.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.utils.Supplier;
import com.qualityautomacao.webposto.utils.Consumer;
import com.qualityautomacao.webposto.utils.UtilsDate;
import com.qualityautomacao.webposto.utils.UtilsInterface;
import com.qualityautomacao.webposto.view.filtro.FiltroPagarTitulo;
import com.qualityautomacao.webposto.view.filtro.FiltroPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FiltroActivity extends AppCompatActivity {

    /* COMPONENTE */
    @BindView(R.id.fil_view_periodo) View viewPediodo;
    @BindView(R.id.fil_rdg_estado_conta) RadioGroup rdgEstadoConta;
    @BindView(R.id.fil_rel_datainicio) RelativeLayout relDataInicio;
    @BindView(R.id.fil_rel_datafim) RelativeLayout relDataFim;
    @BindView(R.id.fil_txt_datainicio) TextView txtDataInicio;
    @BindView(R.id.fil_txt_datafim) TextView txtDataFim;

    /* CONSTANTES */
    public static final String EXTRA_DESTINO = "DESTINO";
    public static final int DESTINO_TITULOS_PAGAR = 1;

    private FiltroPresenter filtroImp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro);
        ButterKnife.bind(this);

        switch (getIntent().getIntExtra(EXTRA_DESTINO, 0)){
            case DESTINO_TITULOS_PAGAR: filtroImp = new FiltroPagarTitulo(this); break;
        }

        filtroImp.carregaComponentes();
    }

    public void initDatePicker(String dataInicio, String dataFim) {
        txtDataInicio.setText(dataInicio);
        txtDataFim.setText(dataFim);

        UtilsInterface.setDateCalendario(relDataInicio, new Supplier<String>() {
            @Override
            public String get() {
                return txtDataInicio.getText().toString();
            }
        }, new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                txtDataInicio.setText(s);
            }
        });

        UtilsInterface.setDateCalendario(relDataFim, new Supplier<String>() {
            @Override
            public String get() {
                return txtDataFim.getText().toString();
            }
        }, new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                txtDataFim.setText(s);
            }
        });

        viewPediodo.setVisibility(View.VISIBLE);
    }

    public void initEstadoConta() {
        rdgEstadoConta.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.fil_btn_consultar)
    public void onClickBtnConsultar(View view){
        filtroImp.consulta();
    }
}
