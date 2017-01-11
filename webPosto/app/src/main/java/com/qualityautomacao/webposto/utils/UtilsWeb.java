package com.qualityautomacao.webposto.utils;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.system.ErrnoException;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public abstract class UtilsWeb {

    private static final int MINUTO_TIMEOUT = 1;
    private static final int TIMEOUT = MINUTO_TIMEOUT * 60000;

    private static final String IP_LOCAL = "192.168.0.42";
    private static final String PORTA = "8181";

    private static final String BASE_URL = "http://" + ip() + ":" + PORTA + "/QualityPostoWEB/webresources/service";

    public static Token token;
    public static String KEY;

    public static void requisitar(final Request request) {
        RequestQueue queue = Volley.newRequestQueue(request.getContext());
        String url = BASE_URL + "/" + request.getRotina() + "/" + request.getFuncao();

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        if (jsonObject.has("MEN")) {
                            request.getOnFalha().accept(jsonObject.getString("MEN"));
                        }else{
                            request.getOnSucesso().accept(new JSONObject(response));
                        }
                    } catch (JSONException e) {
                        request.getOnFalha().accept("Formato invalido");        //TODO FAZER POR RECURSO
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    request.getOnFalha().accept("Server error");
                    Log.e("WEB_POSTO_LOG", "requisitar: ", error);
                }
            }
        ){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("pacoteDados", request.getDados());
                params.put("token", (token == null ? "{}" : token.toString()));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return super.getHeaders();
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                8000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(stringRequest);
    }

    public static void verificarLiberacaoDispositivo(Context context, final Runnable seLiberado) {
        requisitar(new Request(context, "MAQUINA", new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject jsonObject) {
                seLiberado.run();
            }
        }).setDados(getDados(context)));
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
