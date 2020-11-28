package com.rhltech.newsapp.view;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rhltech.newsapp.R;
import com.rhltech.newsapp.model.NewsArticles;

import java.util.ArrayList;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

    private Context context;
    ArrayList<NewsArticles> newsArticlesList;

    public ArticlesAdapter(Context context, ArrayList<NewsArticles> newsArticlesList) {
        this.context = context;
        this.newsArticlesList = newsArticlesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.articles_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        NewsArticles newArticle = newsArticlesList.get(position);
        Glide.with(context)
                .load(newArticle.getUrlToImage())
                .into(viewHolder.imageView);

        viewHolder.title.setText(newArticle.getTitle());
        viewHolder.publishedDate.setText(String.format("Published At: %s", newArticle.getPublishedAt()));
        viewHolder.description.setText(newArticle.getDescription());

        viewHolder.rl_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(context, Uri.parse(newArticle.getUrl()));
            }
        });


    }

    @Override
    public int getItemCount() {
        return newsArticlesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title;
        private TextView publishedDate;
        private TextView description;
        private RelativeLayout rl_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_view);
            title = (TextView) itemView.findViewById(R.id.title);
            publishedDate = (TextView) itemView.findViewById(R.id.published_date);
            description = (TextView) itemView.findViewById(R.id.description);
            rl_layout = (RelativeLayout) itemView.findViewById(R.id.rl_layout);
        }
    }
}
