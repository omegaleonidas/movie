package com.Sidiq.moviecataloc.API;

import com.Sidiq.moviecataloc.model.Detail.DetailMovie;
import com.Sidiq.moviecataloc.model.MovieRespon;
import com.Sidiq.moviecataloc.model.Recomendasi.Recomendasi;
import com.Sidiq.moviecataloc.model.Video.Video;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiMovieInterface {
    @GET("movie/popular")
    Call<MovieRespon> getPopularMovie(@Query("api_key")String apikey, @Query("laguage") String laguage, @Query("page") int page, @Query("region") String region );

    @GET("movie/{movie_id}")
    Call<DetailMovie> getDetailMovie(@Path("movie_id") int movie_id,@Query("api_key") String apikey);

    @GET("movie/{movie_id}/videos")
    Call<Video> getVideo(@Path("movie_id") int movie_id,@Query("api_key") String apikey,@Query("language") String language);

    @GET("movie/{movie_id}/recommendations")
    Call<Recomendasi> getRecomendasi(@Path("movie_id") int movie_id,@Query("api_key") String apikey,@Query("language") String language,@Query("page") int page);




}
