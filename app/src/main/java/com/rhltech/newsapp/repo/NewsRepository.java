package com.rhltech.newsapp.repo;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rhltech.newsapp.model.HeadlinesResponse;
import com.rhltech.newsapp.model.SourcesResponse;
import com.rhltech.newsapp.retrofit.ApiRequest;
import com.rhltech.newsapp.retrofit.RetrofitRequest;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {

    private ApiRequest apiRequest;
    private Application application;

    public NewsRepository(Application application) {
        this.application = application;
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public LiveData<SourcesResponse> getSources(String language, String key) {

        final MutableLiveData<SourcesResponse> data = new MutableLiveData<>();
        apiRequest.getNewsSources(language, key).enqueue(new Callback<SourcesResponse>() {
            @Override
            public void onResponse(Call<SourcesResponse> call, Response<SourcesResponse> response) {
                if (response.body() != null) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<SourcesResponse> call, Throwable t) {
                Log.d("error", Objects.requireNonNull(t.getMessage()));
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<HeadlinesResponse> getHeadlinesResponse(String source, String key) {

        final MutableLiveData<HeadlinesResponse> liveData = new MutableLiveData<>();
        apiRequest.getNewsArticles(source, key).enqueue(new Callback<HeadlinesResponse>() {
            @Override
            public void onResponse(Call<HeadlinesResponse> call, Response<HeadlinesResponse> response) {
                if (response.body() != null) {
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<HeadlinesResponse> call, Throwable t) {
                Log.d("error", Objects.requireNonNull(t.getMessage()));
                liveData.setValue(null);
            }
        });
        return liveData;
    }

}
