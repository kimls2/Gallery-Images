package com.sample.ykim.galleyimages.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sample.ykim.galleyimages.BuildConfig;
import com.sample.ykim.galleyimages.data.model.GalleryResponse;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ykim on 2017. 4. 11..
 */

public interface ImgurService {

  String ENDPOINT = "https://api.imgur.com";

  @GET("/3/gallery/{section}/{sort}/{window}/{page}.json") Observable<GalleryResponse> getGallery(
      @Path("section") String section, @Path("sort") String sort, @Path("window") String window,
      @Path("page") int page, @Query("showViral") boolean showViral);

  class Factory {
    public static ImgurService makeImugurService() {
      HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
      logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
          : HttpLoggingInterceptor.Level.NONE);

      OkHttpClient okHttpClient = new OkHttpClient.Builder() //
          .addInterceptor(logging)  //
          .addInterceptor(chain -> {
            Request request = chain.request()
                .newBuilder()
                .addHeader("Authorization", "Client-ID fb89f7a636907c2")
                .build();
            return chain.proceed(request);
          }) //
          .build();

      Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

      Retrofit retrofit = new Retrofit.Builder() //
          .baseUrl(ImgurService.ENDPOINT) //
          .client(okHttpClient) //
          .addConverterFactory(GsonConverterFactory.create(gson)) //
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //
          .build();
      return retrofit.create(ImgurService.class);
    }
  }
}
