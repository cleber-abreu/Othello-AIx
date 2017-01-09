package com.qualityautomacao.webposto.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.model.DadosFiltro;
import com.qualityautomacao.webposto.utils.Supplier;
import com.qualityautomacao.webposto.utils.Consumer;
import com.qualityautomacao.webposto.utils.UtilsInterface;
import com.qualityautomacao.webposto.view.filtro.FiltroPagarCheque;
import com.qualityautomacao.webposto.view.filtro.FiltroPagarTitulo;
import com.qualityautomacao.webposto.view.filtro.FiltroPresenter;
import com.qualityautomacao.webposto.view.filtro.FiltroReceberCartaFrete;
import com.qualityautomacao.webposto.view.filtro.FiltroReceberCartao;
import com.qualityautomacao.webposto.view.filtro.FiltroReceberCheque;
import com.qualityautomacao.webposto.view.filtro.FiltroReceberTitulo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FiltroActivity extends AppCompatActivity {

    /* COMPONENTES */
    @BindView(R.id.fil_view_periodo) View viewPediodo;
    @BindView(R.id.fil_rdg_bompara_movimento) RadioGroup rdgBomparaMovimento;
    @BindView(R.id.fil_rel_datainicio) RelativeLayout relDataInicio;
    @BindView(R.id.fil_rel_datafim) RelativeLayout relDataFim;
    @BindView(R.id.fil_rdg_estado_conta) RadioGroup rdgEstadoConta;
    @BindView(R.id.fil_rdg_estado_conta_ap) RadioGroup rdgEstadoContaAP;
    @BindView(R.id.fil_rdg_estado_conta_ar) RadioGroup rdgEstadoContaAR;
    @BindView(R.id.fil_view_cliente) RelativeLayout viewCliente;

    @BindView(R.id.fil_txt_datainicio) TextView txtDataInicio;
    @BindView(R.id.fil_txt_datafim) TextView txtDataFim;
    @BindView(R.id.fil_swt_por_cliente) Switch swtPorCliente;

    /* CONSTANTES */
    public static final String EXTRA_DESTINO = "DESTINO";
    public static final int DESTINO_TITULOS_PAGAR = 1;
    public static final int DESTINO_CHEQUE_PAGAR = 2;
    public static final int DESTINO_TITULO_RECEBER = 3;
    public static final int DESTINO_CHEQUE_RECEBER = 4;
    public static final int DESTINO_CARTA_FRETE_RECEBER = 5;
    public static final int DESTINO_CARTAO_RECEBER = 6;

    private FiltroPresenter filtroImp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro);
        ButterKnife.bind(this);

        switch (getIntent().getIntExtra(EXTRA_DESTINO, 0)){
            case DESTINO_TITULOS_PAGAR: filtroImp = new FiltroPagarTitulo(this); break;
            case DESTINO_CHEQUE_PAGAR: filtroImp = new FiltroPagarCheque(this); break;
            case DESTINO_TITULO_RECEBER: filtroImp = new FiltroReceberTitulo(this); break;
            case DESTINO_CHEQUE_RECEBER: filtroImp = new FiltroReceberCheque(this); break;
            case DESTINO_CARTA_FRETE_RECEBER: filtroImp = new FiltroReceberCartaFrete(this); break;
            case DESTINO_CARTAO_RECEBER: filtroImp = new FiltroReceberCartao(this); break;
        }

        filtroImp.carregaComponentes();
    }

    @OnClick(R.id.fil_btn_consultar)
    public void onClickBtnConsultar(View view){
        DadosFiltro dados = new DadosFiltro();
        dados.setDataInicio(txtDataInicio.getText().toString());
        dados.setDataFim(txtDataFim.getText().toString());
        dados.setEstadoConta(getEstadoConta());
        dados.setEstadoContaAP(getEstadoContaAP());
        dados.setBomparaMovimento(getBomparaMovimento());
        dados.setEstadoContaAR(getEstadoContaAR());
        dados.setPorCliente(swtPorCliente.isChecked());

        filtroImp.consulta(dados);
    }

    private String getEstadoConta() {
        switch (rdgEstadoConta.getCheckedRadioButtonId()){
            case R.id.fil_rdb_aberto: return "N";
            case R.id.fil_rdb_parcial: return "P";
            case R.id.fil_rdb_pago: return "S";
            default: return "";
        }
    }

    private String getEstadoContaAP(){
        switch (rdgEstadoContaAP.getCheckedRadioButtonId()){
            case R.id.fil_rdb_aberto_ap: return "N";
            case R.id.fil_rdb_pago_ap: return "S";
            default: return "";
        }
    }

    private String getEstadoContaAR(){
        switch (rdgEstadoContaAP.getCheckedRadioButtonId()){
            case R.id.fil_rdb_aberto_ar: return "N";
            case R.id.fil_rdb_recebido_ar: return "S";
            default: return "";
        }
    }

    private String getBomparaMovimento(){
        switch (rdgEstadoContaAP.getCheckedRadioButtonId()){
            case R.id.fil_rdb_bompara_bm: return "B";
            case R.id.fil_rdg_bompara_movimento: return "M";
            default: return "";
        }
    }

    public void initEstadoConta() {
        rdgEstadoConta.setVisibility(View.VISIBLE);
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

    public void initEstadoContaAP(){
        rdgEstadoContaAP.setVisibility(View.VISIBLE);
    }

    public void initBomparaMovimento(){rdgBomparaMovimento.setVisibility(View.VISIBLE);}

    public void initEstadoContaAR(){rdgEstadoContaAR.setVisibility(View.VISIBLE);}

    public void initCliente(){viewCliente.setVisibility(View.VISIBLE);}


}
