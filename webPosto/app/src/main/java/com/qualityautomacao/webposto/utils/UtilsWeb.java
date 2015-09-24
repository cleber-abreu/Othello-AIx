package com.qualityautomacao.webposto.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;

import com.qualityautomacao.webposto.BuildConfig;
import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.model.Token;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

public abstract class UtilsWeb {
    private static final String IP = "192.168.1.118";
    private static final String PORTA = "8080";

    private static final int MINUTO_TIMEOUT = 1;
    private static final int TIMEOUT = MINUTO_TIMEOUT * 60000;

//    private static final String IP = "200.98.166.245";
//    private static final String PORTA = "8181";

    private static final String BASE_URL = "http://" + IP + ":" + PORTA + "/QualityPostoWEB/webresources/service/";
    public static Token token;
    public static String KEY;

    public static final int UW_SHOW_PROGRESS_DIALOG = 1 << 0;
    public static final int UW_SHOW_TOAST_ON_EXCEPTION = 1 << 1;

    public static void requisitar(final Context context, final String funcao, final String dados, final Consumer<JSONObject> consumer, final int opcoes, final Runnable antes, final Runnable depois) {

        new AsyncTask<String, Void, JSONObject>() {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                if ((opcoes & UW_SHOW_PROGRESS_DIALOG) == UW_SHOW_PROGRESS_DIALOG)
                    progressDialog = ProgressDialog.show(context, null, "Aguarde...", true);

                if (antes != null)
                    antes.run();
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                super.onPostExecute(jsonObject);

                try {
                    if (jsonObject != null && consumer != null)
                        consumer.accept(jsonObject);
                } catch (final Exception e) {
                    if ((opcoes & UW_SHOW_TOAST_ON_EXCEPTION) == UW_SHOW_TOAST_ON_EXCEPTION)
                        showToast(context, e.getMessage(), Toast.LENGTH_LONG);
                } finally {
                    if ((opcoes & UW_SHOW_PROGRESS_DIALOG) == UW_SHOW_PROGRESS_DIALOG)
                        progressDialog.dismiss();

                    if (depois != null)
                        depois.run();
                }
            }

            @Override
            protected JSONObject doInBackground(String... params) {
                try {
                    final String stringUrl = BASE_URL + funcao + "/" + dados + "/" + (token == null ? "{}" : token.toString());
                    URL url = new URL(stringUrl);
                    URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());

                    ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                        public String handleResponse(final HttpResponse response) throws IOException {
                            StatusLine statusLine = response.getStatusLine();
                            if (statusLine.getStatusCode() >= 300) {
                                throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
                            }

                            HttpEntity entity = response.getEntity();
                            return entity == null ? null : EntityUtils.toString(entity, "UTF-8");
                        }
                    };

                    HttpParams httpParams = new BasicHttpParams();
                    HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT);
                    HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT);

                    HttpClient client = new DefaultHttpClient(httpParams);
                    HttpPost post = new HttpPost(uri.toASCIIString());

                    String pacote = client.execute(post, responseHandler);

                    final JSONObject pacoteJson = new JSONObject(pacote);

                    if (pacoteJson.has("MEN")) {
                        if ((opcoes & UW_SHOW_TOAST_ON_EXCEPTION) == UW_SHOW_TOAST_ON_EXCEPTION)
                            showToast(context, pacoteJson.optString("MEN"), Toast.LENGTH_LONG);
                        return null;
                    }

                    return pacoteJson;
                } catch (ConnectTimeoutException e){
                    showToast(context, "Timeout. Verique sua conexão.", Toast.LENGTH_LONG);
                    return null;
                } catch (Exception e) {
                    System.err.println(e);
                }

                return null;
            }
        }.execute();
    }

    public static void requisitar(Context context, String funcao, String dados, Consumer<JSONObject> consumer) {
        requisitar(context, funcao, dados, consumer, UW_SHOW_PROGRESS_DIALOG | UW_SHOW_TOAST_ON_EXCEPTION, null, null);
    }

    public static void verificarLiberacaoDispositivo(Context context, final Runnable seLiberado) {
        requisitar(context, "MAQUINA", getDados(context), new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject jsonObject) throws Exception {
                seLiberado.run();
            }
        });
    }

    private static String getDados(Context context) {
        JSONObject dados = new JSONObject();

        try {
            dados.put("MAQ_DS_MAC", KEY);
            dados.put("MAQ_DS_SERIAL", context.getString(R.string.serial_id_server));
            dados.put("MAQ_DS_COMPUTER", Build.MODEL);
            dados.put("MAQ_DS_VERSAO", BuildConfig.VERSION_NAME);
        } catch (Exception e) {
            System.out.println(e);
        }

        return dados.toString();
    }

    private static void showToast(final Context context, final String mensagem, final int duracaoMensagem) {
        if (context instanceof Activity)
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, mensagem, duracaoMensagem).show();
                }
            });
        else
            Toast.makeText(context, mensagem, duracaoMensagem).show();
    }
}
