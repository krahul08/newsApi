package com.rhltech.newsapp.retrofit;

import com.rhltech.newsapp.model.HeadlinesResponse;
import com.rhltech.newsapp.model.SourcesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiRequest {

    @GET("v2/sources/")
    Call<SourcesResponse> getNewsSources(
            @Query("language") String language,
            @Query("apikey") String apiKey
    );

    @GET("v2/top-headlines/")
    Call<HeadlinesResponse> getNewsArticles(
            @Query("sources") String sources,
            @Query("apikey") String apiKey
    );
}
