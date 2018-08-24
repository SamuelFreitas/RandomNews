package com.random.news.br.Tasks;


import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.random.news.br.Adapters.AdapterNews;
import com.random.news.br.Classes.News;
import com.random.news.br.MainActivity;
import com.random.news.br.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetNews extends AsyncTask<Void, Void, Void> {

    public ArrayList<News> newsList = new ArrayList<>();;
    private ListView lv;
    private Activity act;
    private TextView progress;


    public GetNews(ListView lv, Activity act) {
        this.lv = lv;
        this.act = act;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        String jsonStr = null;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://newsapi.org/v2/top-headlines?country=us&apiKey=e2aef8c23bec434bb6901041b3be0d57")
                .build();
        Response response = null;

        try {
            response = client.newCall(request).execute();
            jsonStr = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                JSONArray news = jsonObj.getJSONArray("articles");

                for (int i = 0; i < news.length(); i++) {

                    JSONObject c = news.getJSONObject(i);

                    String title = c.getString("title");
                    String description = c.getString("description");
                    String image = c.getString("urlToImage");
                    String link = c.getString("url");

                    if(title.equals("null")  || description.equals("null") || image.equals("null") || link.equals("null"))
                        continue;

                    News n = new News();
                    n.setTitle(title);
                    n.setDescription(description);
                    n.setImage(image);
                    n.setLink(link);

                    if (n != null)
                        newsList.add(n);
                }
            } catch (final JSONException e) {
                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(act,
                                "Json error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });

            }

        } else {
            Log.e("Script", "Couldn't get json from server.");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        progress = act.findViewById(R.id.progress);
        progress.setVisibility(View.GONE);
        AdapterNews adapter = new AdapterNews(newsList, act);
        lv.setAdapter(adapter);
    }


}
