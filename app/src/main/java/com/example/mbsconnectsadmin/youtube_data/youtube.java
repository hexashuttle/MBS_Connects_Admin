package com.example.mbsconnectsadmin.youtube_data;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mbsconnectsadmin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class youtube extends AppCompatActivity {

    TextView channel_id,total_view,total_subs,total_video;
    private RequestQueue mQueue;
    String channel_url="https://www.youtube.com/channel/UCKIufDX0ghxP00ycsSdZ8YQ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        channel_id=findViewById(R.id.channel_id);
        total_view=findViewById(R.id.total_view);
        total_subs=findViewById(R.id.total_subs);
        total_video=findViewById(R.id.total_video);

        mQueue = Volley.newRequestQueue(this);

        if(!connected())
        {
            Toast.makeText(this, "Please connect to internet", Toast.LENGTH_SHORT).show();
        }
        
        fetch();

    }

    private void fetch() {
        String url="https://www.googleapis.com/youtube/v3/channels?part=statistics&id=UCKIufDX0ghxP00ycsSdZ8YQ&key=AIzaSyDri0qWLm-R54YhMnfstVKe2tD9DbQ-JeE";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("items");


//                                JSONObject items = jsonArray.getJSONObject(0).getJSONObject("snippet");

//                                String view1 = items.getString("title");
//                                String subs2 = items.getString("description");

                            JSONObject items = jsonArray.getJSONObject(0).getJSONObject("statistics");
                            String video3 = items.getString("videoCount");
                            String view1 = items.getString("subscriberCount");
                            String subs2 = items.getString("viewCount");


                            total_view.setText(subs2);
                            total_subs.setText(view1);
                            total_video.setText(video3);

                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);

        channel_id.setText("UCKIufDX0ghxP00ycsSdZ8YQ");
    }

    public void open_youtube(View view) {
        CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();

        customIntent.setToolbarColor(ContextCompat.getColor(youtube.this, R.color.red));

        openCustomTab(youtube.this, customIntent.build(), Uri.parse(channel_url));

    }

    private void openCustomTab(youtube youtube, CustomTabsIntent build, Uri parse) {
        String packageName = "com.android.chrome";
        if (packageName != null) {


          build.intent.setPackage(packageName);


            build.launchUrl(youtube, parse);
        } else
            youtube.startActivity(new Intent(Intent.ACTION_VIEW, parse));
    }



    private boolean connected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wificonn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileconn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wificonn != null && wificonn.isConnected()) || (mobileconn != null && mobileconn.isConnected())) {
            return true;
        } else {

            return false;

        }
    }
}