package com.qualityautomacao.webposto.utils;

import android.app.ProgressDialog;
import android.content.Context;
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
    private static final String IP = "192.168.1.112";
    private static final String PORTA = "8080";
    private static final String BASE_URL = "http://" + IP + ":" + PORTA + "/QualityPostoWEB/webresources/service/";
    public final static Token token = new Token();

    public static void requisitar(final Context context, final String funcao, final String dados, final Consumer<JSONObject> consumer) {

        new AsyncTask<String, Void, JSONObject>() {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(context, null, "Aguarde...", true);
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                super.onPostExecute(jsonObject);

                try {
                    consumer.accept(jsonObject);
                } catch (Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                } finally {
                    progressDialog.dismiss();
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

                    JSONObject pacoteJson = new JSONObject(pacote);
                    if (pacoteJson.has("MEN")) {
                        Toast.makeText(context, pacoteJson.getString("MEN"), Toast.LENGTH_LONG).show();
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
}
