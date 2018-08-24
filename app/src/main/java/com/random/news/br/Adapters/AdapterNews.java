package com.random.news.br.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.random.news.br.Classes.News;
import com.random.news.br.NewsView;
import com.random.news.br.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterNews extends BaseAdapter {

    private final ArrayList<News> list_news;
    private final Activity act;

    public AdapterNews(ArrayList<News> list_news, Activity act) {
        this.list_news = list_news;
        this.act = act;
    }

    @Override
    public int getCount() {
        return list_news.size();
    }

    @Override
    public Object getItem(int i) {
        return list_news.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        View view = act.getLayoutInflater()
                .inflate(R.layout.news_list_adapter, parent, false);

        News news = list_news.get(position);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView description = (TextView) view.findViewById(R.id.descrition);
        ImageView image = (ImageView) view.findViewById(R.id.image);

        final String url = news.getLink();

        title.setText(news.getTitle());
        description.setText(news.getDescription());
        Picasso.get()
                .load(news.getImage()).
                    resize(360, 360)
                .centerCrop()
                .into(image);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, NewsView.class);
                intent.putExtra("url", url);
                act.startActivity(intent);
            }
        });


        return view;
    }

}
