package com.upDownInteractive.snowshoelibrary;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

public class SnowShoeActivity extends Activity {

    private Boolean stampBeingChecked = false;
    private static List<Float> xPoints = new LinkedList<Float>();
    private static List<Float> yPoints = new LinkedList<Float>();
    private String stampResult = "none";
    public String YOUR_APP_KEY = "";
    public String YOUR_APP_SECRET = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public String getStampResult() {
        return this.stampResult;
    }

    // function that interprets the 5-touch action
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (event.getPointerCount() == 5) {
                if (!stampBeingChecked) {
                    stampBeingChecked = true;
                    stampResult = "waiting";

                    xPoints.clear();
                    yPoints.clear();

                    xPoints.add((float) event.getX(0));
                    xPoints.add((float) event.getX(1));
                    xPoints.add((float) event.getX(2));
                    xPoints.add((float) event.getX(3));
                    xPoints.add((float) event.getX(4));
                    yPoints.add((float) event.getY(0));
                    yPoints.add((float) event.getY(1));
                    yPoints.add((float) event.getY(2));
                    yPoints.add((float) event.getY(3));
                    yPoints.add((float) event.getY(4));

                    // we used to
                    new VerifyStamp().execute();

                }
            }
        }
        return true;
    }

    private class VerifyStamp extends AsyncTask<Void, Void, Void> {
        private final ProgressDialog dialog;

        public VerifyStamp() {
            this.dialog = new ProgressDialog(SnowShoeActivity.this);
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Verifying stamp...");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            goOnline();
            return null;
        }

        @Override
        protected void onPostExecute(Void argg) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            stampBeingChecked = false;

        }
    }

    private void goOnline() {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();

        try {

            // put together a query String
            StringBuilder queryBuilder = new StringBuilder();

            queryBuilder.append("http://beta.snowshoestamp.com/api/v2/stamp?");

            queryBuilder.append("&x1=");
            queryBuilder
                    .append(URLEncoder.encode("" + xPoints.get(0), "UTF-8"));

            queryBuilder.append("&x2=");
            queryBuilder
                    .append(URLEncoder.encode("" + xPoints.get(1), "UTF-8"));

            queryBuilder.append("&x3=");
            queryBuilder
                    .append(URLEncoder.encode("" + xPoints.get(2), "UTF-8"));

            queryBuilder.append("&x4=");
            queryBuilder
                    .append(URLEncoder.encode("" + xPoints.get(3), "UTF-8"));

            queryBuilder.append("&x5=");
            queryBuilder
                    .append(URLEncoder.encode("" + xPoints.get(4), "UTF-8"));

            queryBuilder.append("&y1=");
            queryBuilder
                    .append(URLEncoder.encode("" + yPoints.get(0), "UTF-8"));

            queryBuilder.append("&y2=");
            queryBuilder
                    .append(URLEncoder.encode("" + yPoints.get(1), "UTF-8"));

            queryBuilder.append("&y3=");
            queryBuilder
                    .append(URLEncoder.encode("" + yPoints.get(2), "UTF-8"));

            queryBuilder.append("&y4=");
            queryBuilder
                    .append(URLEncoder.encode("" + yPoints.get(3), "UTF-8"));

            queryBuilder.append("&y5=");
            queryBuilder
                    .append(URLEncoder.encode("" + yPoints.get(4), "UTF-8"));

            Log.i("query", queryBuilder.toString());

            HttpGet httpGet = new HttpGet(queryBuilder.toString());

            OAuthConsumer consumer = new CommonsHttpOAuthConsumer(
                    YOUR_APP_KEY,
                    YOUR_APP_SECRET);
            consumer.setTokenWithSecret("", "");


            try {
                consumer.sign(httpGet);
            } catch (OAuthMessageSignerException e) {
                e.printStackTrace();
            } catch (OAuthExpectationFailedException e) {
                e.printStackTrace();
            } catch (OAuthCommunicationException e) {
                e.printStackTrace();
            }

            HttpResponse response = client.execute(httpGet);
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                this.stampResult = builder.toString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        onStampResult();

    }

    public void onStampResult() {

        Log.i("result", "result is " + this.stampResult);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.snow_shoe, menu);
        return true;
    }
    
}
