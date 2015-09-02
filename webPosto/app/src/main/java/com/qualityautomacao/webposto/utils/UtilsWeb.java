package com.qualityautomacao.webposto.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.qualityautomacao.webposto.model.Token;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

public abstract class UtilsWeb {
    private static final String IP = "192.168.0.26";
    private static final String PORTA = "8080";
//    private static final String IP = "200.98.166.245";
//    private static final String PORTA = "8181";

    private static final String BASE_URL = "http://" + IP + ":" + PORTA + "/QualityPostoWEB/webresources/service/";
    public static Token token = null;

    public static final int UW_SHOW_PROGRESS_DIALOG = 1 << 0;
    public static final int UW_SHOW_TOAST_ON_EXCEPTION = 1 << 1;

    public static void requisitar(final Activity context, final String funcao, final String dados, final Consumer<JSONObject> consumer, final int opcoes, final Runnable antes, final Runnable depois) {

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
                    if (jsonObject != null)
                        consumer.accept(jsonObject);
                } catch (final Exception e) {
                    if ((opcoes & UW_SHOW_TOAST_ON_EXCEPTION) == UW_SHOW_TOAST_ON_EXCEPTION)
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });

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

                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(uri.toASCIIString());

                    String pacote = client.execute(post, responseHandler);

                    final JSONObject pacoteJson = new JSONObject(pacote);

                    if (pacoteJson.has("MEN")) {
                        if ((opcoes & UW_SHOW_TOAST_ON_EXCEPTION) == UW_SHOW_TOAST_ON_EXCEPTION)
                            context.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, pacoteJson.optString("MEN"), Toast.LENGTH_LONG).show();
                                }
                            });

                        return null;
                    }

                    return pacoteJson;
                } catch (Exception e) {
                    System.err.println(e);
                }

                return null;
            }
        }.execute();
    }

    public static void requisitar(Activity activity, String funcao, String dados, Consumer<JSONObject> consumer) {
        requisitar(activity, funcao, dados, consumer, UW_SHOW_PROGRESS_DIALOG | UW_SHOW_TOAST_ON_EXCEPTION, null, null);
    }
}
