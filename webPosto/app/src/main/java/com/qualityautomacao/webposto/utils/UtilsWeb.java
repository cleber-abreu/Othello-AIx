package com.qualityautomacao.webposto.utils;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.qualityautomacao.webposto.BuildConfig;
import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.model.Token;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

public abstract class UtilsWeb {

    private static final int MINUTO_TIMEOUT = 1;
    private static final int TIMEOUT = MINUTO_TIMEOUT * 60000;

    private static final String IP_LOCAL = "192.168.0.42";
    private static final String PORTA = "8181";

    private static final String BASE_URL = "http://" + ip() + ":" + PORTA + "/QualityPostoWEB/webresources/service/MOBILE/";

    public static Token token;
    public static String KEY;

    public static final int UW_SHOW_PROGRESS_DIALOG = 1 << 0;
    public static final int UW_SHOW_TOAST_ON_EXCEPTION = 1 << 1;

    public static void requisitar(final Request request) {

//        final ProgressDialog progressDialog;

        if ((request.getOpcoes() & UW_SHOW_PROGRESS_DIALOG) == UW_SHOW_PROGRESS_DIALOG) {
//            progressDialog = ProgressDialog.show(request.getContext(), null, "Aguarde...", true);
        }else{
//            progressDialog = null;
        }

        if (request.getOnPreExecute() != null)
            request.getOnPreExecute().run();

        RequestQueue queue = Volley.newRequestQueue(request.getContext());
        String url = BASE_URL + request.getFuncao() + "/" + codifica(request.getDados()) + "/" + codifica(token == null ? "{}" : token.toString());
        Log.i("WEB_POSTO_LOG", "requisitar: " + url);

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.has("MEN")) {
                                if ((request.getOpcoes() & UW_SHOW_TOAST_ON_EXCEPTION) == UW_SHOW_TOAST_ON_EXCEPTION)
                                    showToast(request.getContext(), jsonObject.optString("MEN"), Toast.LENGTH_LONG);

                            if (jsonObject != null && request.getConsumer() != null)
                                request.getConsumer().accept(jsonObject);
                            }
                        } catch (final Exception e) {
                            if ((request.getOpcoes() & UW_SHOW_TOAST_ON_EXCEPTION) == UW_SHOW_TOAST_ON_EXCEPTION)
                                showToast(request.getContext(), e.getMessage(), Toast.LENGTH_LONG);
                        } finally {
                            if ((request.getOpcoes() & UW_SHOW_PROGRESS_DIALOG) == UW_SHOW_PROGRESS_DIALOG){}
//                                progressDialog.dismiss();

                            if (request.getOnPosExecute() != null)
                                request.getOnPosExecute().run();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("WEB_POSTO_LOG", "requisitar: " + error.getCause());
                    }
        });

        queue.add(stringRequest);

//        new AsyncTask<String, Void, JSONObject>() {
//            ProgressDialog progressDialog;
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//
//                if ((request.getOpcoes() & UW_SHOW_PROGRESS_DIALOG) == UW_SHOW_PROGRESS_DIALOG)
//                    progressDialog = ProgressDialog.show(request.getContext(), null, "Aguarde...", true);
//
//                if (request.getOnPreExecute() != null)
//                    request.getOnPreExecute().run();
//            }
//
//            @Override
//            protected void onPostExecute(JSONObject jsonObject) {
//                super.onPostExecute(jsonObject);
//
//                try {
//                    if (jsonObject != null && request.getConsumer() != null)
//                        request.getConsumer().accept(jsonObject);
//                } catch (final Exception e) {
//                    if ((request.getOpcoes() & UW_SHOW_TOAST_ON_EXCEPTION) == UW_SHOW_TOAST_ON_EXCEPTION)
//                        showToast(request.getContext(), e.getMessage(), Toast.LENGTH_LONG);
//                } finally {
//                    if ((request.getOpcoes() & UW_SHOW_PROGRESS_DIALOG) == UW_SHOW_PROGRESS_DIALOG)
//                        progressDialog.dismiss();
//
//                    if (request.getOnPosExecute() != null)
//                        request.getOnPosExecute().run();
//                }
//            }
//
//            @Override
//            protected JSONObject doInBackground(String... params) {
//                try {
//                    final String stringUrl = BASE_URL + request.getFuncao() + "/" + URLEncoder.encode(request.getDados(), "UTF-8") + "/" + (token == null ? "{}" : token.toString());
//                    Log.i("WEB_POSTO_LOG", "requisitar: " + stringUrl);
//                    URL url = new URL(stringUrl);
//                    URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
//
//                    ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
//                        public String handleResponse(final HttpResponse response) throws IOException {
//                            StatusLine statusLine = response.getStatusLine();
//                            if (statusLine.getStatusCode() >= 300) {
//                                throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
//                            }
//
//                            HttpEntity entity = response.getEntity();
//                            return entity == null ? null : EntityUtils.toString(entity, "UTF-8");
//                        }
//                    };
//
//                    HttpParams httpParams = new BasicHttpParams();
//                    HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT);
//                    HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT);
//
//                    HttpClient client = new DefaultHttpClient(httpParams);
//                    HttpPost post = new HttpPost(uri.toASCIIString());
//
//                    String pacote = client.execute(post, responseHandler);
//
//                    final JSONObject pacoteJson = new JSONObject(pacote);
//
//                    if (pacoteJson.has("MEN")) {
//                        if ((request.getOpcoes() & UW_SHOW_TOAST_ON_EXCEPTION) == UW_SHOW_TOAST_ON_EXCEPTION)
//                            showToast(request.getContext(), pacoteJson.optString("MEN"), Toast.LENGTH_LONG);
//                        return null;
//                    }
//
//                    return pacoteJson;
//                } catch (ConnectTimeoutException e){
//                    if (request.getOnTimeout() != null)
//                        request.getOnTimeout().run();
//                    else
//                        showToast(request.getContext(), "Timeout. Verique sua conexão.", Toast.LENGTH_LONG);
//
//                    return null;
//                } catch (Exception e) {
//                    System.err.println(e);
//                }
//
//                return null;
//            }
//        }.execute();
    }

    private static String codifica(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e("WEB_POSTO_LOG", "enconda: ", e);
        }

        return "%7B%%7D";
    }

    public static void verificarLiberacaoDispositivo(Context context, final Runnable seLiberado) {
        requisitar(new Request(context, "MAQUINA")
                .setDados(getDados(context))
                .onCompleteRequest(new Consumer<JSONObject>() {
                    @Override
                    public void accept(JSONObject jsonObject) throws Exception {
                        seLiberado.run();
                    }
                }));
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

    private static String ip() {
        if(BuildConfig.DEBUG) return IP_LOCAL;

        JSONObject json = null;
        try {
            json = new AsyncTask<Void, Void, JSONObject>() {
                    @Override
                    protected JSONObject doInBackground(Void... params) {
                        try {
                            final String stringUrl = "http://wpadmin.qualityautomacao.com/server/ip/principal";
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

                            DefaultHttpClient client = new DefaultHttpClient(httpParams);
                            HttpGet get = new HttpGet(uri.toASCIIString());

                            client.getCredentialsProvider().setCredentials(new AuthScope("wpadmin.qualityautomacao.com", 80), new UsernamePasswordCredentials("dev", "dev@2015"));

                            String pacote = client.execute(get, responseHandler);

                            return new JSONObject(pacote);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        return null;
                    }
                }.execute().get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return json == null ? "" : json.optString("ip_ds_ip");
    }
}
