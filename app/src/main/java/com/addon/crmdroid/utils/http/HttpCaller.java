package com.addon.crmdroid.utils.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.addon.crmdroid.utils.http.HttpRequest.GET;
import static com.addon.crmdroid.utils.http.HttpRequest.POST;


/**
 * Created by sathishbabur on 1/31/2018.
 */

public class HttpCaller extends AsyncTask<HttpRequest, String, HttpResponse> {


    private static final String UTF_8 = "UTF-8";
    private Context context;
    private ProgressDialog progressDialog;
    private String progressMessage;

    public HttpCaller(Context context, String progressMessage) {
        super();
        this.context = context;
        this.progressMessage = progressMessage;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(progressMessage);
        progressDialog.show();
    }

    @Override
    protected HttpResponse doInBackground(HttpRequest... httpRequests) {
        HttpRequest httpRequest = httpRequests[0];
        HttpResponse httpResponse = new HttpResponse();
        OkHttpClient client = new OkHttpClient();
        Request request = null;
        try {
            switch (httpRequest.getMethodType()) {
                case POST:
                    request = new Request.Builder()
                            .url(httpRequest.getUrl())
                            .post(httpRequest.getRequestBody())
                            .build();
                    break;
                case GET:
                    request = new Request.Builder()
                            .url(httpRequest.getUrl())
                            .get()
                            .build();
                    break;
            }

            Response response = client.newCall(request).execute();

            String message = response.body().string();
            if (response.isSuccessful()) {
                httpResponse.setSuccessMessage(message);
            } else {
                httpResponse.setErrorMessage(response.message());
            }
            response.body().close();
            return httpResponse;

        } catch (Exception e) {
            progressDialog.dismiss();
            Log.e("Http URL", e.toString());
            httpResponse.setErrorMessage(e.getMessage());
        }
        return httpResponse;
    }


    @Override
    protected void onPostExecute(HttpResponse response) {

        super.onPostExecute(response);
        progressDialog.dismiss();
        onResponse(response);
        context = null;
    }

    public void onResponse(HttpResponse response) {
    }
}