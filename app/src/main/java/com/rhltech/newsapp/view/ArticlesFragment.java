package com.rhltech.newsapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rhltech.newsapp.R;
import com.rhltech.newsapp.model.HeadlinesResponse;
import com.rhltech.newsapp.model.NewsArticles;
import com.rhltech.newsapp.view_model.NewsViewModel;

import java.util.ArrayList;
import java.util.List;

public class ArticlesFragment extends Fragment {

    NewsViewModel newsViewModel;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LinearLayoutManager layoutManager;
    private ArticlesAdapter adapter;
    private ArrayList<NewsArticles> list = new ArrayList<>();
    private String sourceId;


    public static ArticlesFragment newInstance(String id, String name) {
        ArticlesFragment fragment = new ArticlesFragment();
        Bundle args = new Bundle();
        args.putString("sourceId", id);
        args.putString("sourceName", name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sourceId = getArguments().getString("sourceId", "");
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_articles, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progress_bar);

        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        newsViewModel.setSource(sourceId);
        newsViewModel.getHeadlinesResponse().observe(this, new Observer<HeadlinesResponse>() {
            @Override
            public void onChanged(HeadlinesResponse headlinesResponse) {
                if (headlinesResponse != null) {
                    progressBar.setVisibility(View.GONE);
                    List<NewsArticles> articles = headlinesResponse.getArticles();
                    list.addAll(articles);
                    layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setHasFixedSize(true);
                    adapter = new ArticlesAdapter(getContext(), list);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        return view;

    }

}