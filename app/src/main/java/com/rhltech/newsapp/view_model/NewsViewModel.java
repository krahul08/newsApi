package com.rhltech.newsapp.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rhltech.newsapp.model.HeadlinesResponse;
import com.rhltech.newsapp.model.SourcesResponse;
import com.rhltech.newsapp.repo.NewsRepository;
import com.rhltech.newsapp.view.ArticlesFragment;

public class NewsViewModel extends AndroidViewModel {

    private NewsRepository newsRepository;
    ArticlesFragment articlesFragment;
    private LiveData<SourcesResponse> responseLiveData;
    private LiveData<HeadlinesResponse> headlinesResponseLiveData;

    private String apiKey = "cba25c5284814c8ebc1f0ef6c037cc7d";

    public NewsViewModel(@NonNull Application application) {
        super(application);
        newsRepository = new NewsRepository(application);
        articlesFragment = new ArticlesFragment();
        String language = "en";
        this.responseLiveData = newsRepository.getSources(language, apiKey);
    }

    public LiveData<SourcesResponse> getResponseLiveData() {
        return responseLiveData;
    }

    public LiveData<HeadlinesResponse> getHeadlinesResponse() {
        return headlinesResponseLiveData;
    }


    public void setSource(String sourceId) {
        this.headlinesResponseLiveData = newsRepository.getHeadlinesResponse(sourceId, apiKey);
    }
}
