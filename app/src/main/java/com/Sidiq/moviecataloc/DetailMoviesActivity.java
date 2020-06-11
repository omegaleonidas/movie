package com.Sidiq.moviecataloc;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Sidiq.moviecataloc.Adapter.RecylerRecomentationAdapter;
import com.Sidiq.moviecataloc.Retrofit.ApiClient;
import com.Sidiq.moviecataloc.model.Detail.DetailMovie;
import com.Sidiq.moviecataloc.model.Recomendasi.Recomendasi;
import com.Sidiq.moviecataloc.model.Recomendasi.Recomendation;
import com.Sidiq.moviecataloc.model.Video.ResultsItem;
import com.Sidiq.moviecataloc.model.Video.Video;
import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;

import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMoviesActivity extends YouTubeBaseActivity implements View.OnClickListener, YouTubePlayer.OnInitializedListener {
    public static String TITLE = "title";
    private List<Recomendation> movieRecomendationsList = new ArrayList<>();
    private List<ResultsItem> resultsItemVideo = new ArrayList<>();
    private static String TAG = "DetailMoviesActivity";
    private static final String IMAGE_BASE_URL = com.Sidiq.moviecataloc.BuildConfig.IMAGE_BASE_URL;
    private static final String API_KEY = com.Sidiq.moviecataloc.BuildConfig.API_KEY;
    private static final String LANGUAGE = "en-US";
    public static String MOVIE_ID = "movie_id";
    private static int PAGE = 1;
    public static String LOCAL_STATUS = "local_status";
    private static final String TMDB_URL = "https://www.themoviedb.org/movie/";

    private RecylerRecomentationAdapter adapter;
    private int movie_id;
    private int mutedColor = R.attr.colorAccent;

    private int nilai = 0;
    private String movie_url;
    private String check;
    private String region1 = null;
    private Context context;

    private ApiClient apiClient = null;
    private Call<DetailMovie> detailMovieCall;
    private Call<Video> VideoCall;

    private Call<Recomendasi> call;
    private YouTubePlayer youTubePlayer;

    private DetailMovie detailMovie;
    private Recomendasi recomendasi;

    private Uri uri;

    public static final String API_KEYYT = "AIzaSyBuYS8gK6vmVY2J67u1fMSPj1UgEbmg1xw";


    @BindView(R.id.TempatRecomentation1)
    RecyclerView recyclerView;


    @BindView(R.id.titleTes)
    TextView title1;

    @BindView(R.id.titleTes3)
    TextView title3;

    @BindView(R.id.titleTes2)
    TextView title2;
    @BindView(R.id.imageView6)
    ImageView imageView6title;
    @BindView(R.id.imageView3)
    ImageView imageView3title;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movies);
        ButterKnife.bind(this);

        uri = getIntent().getData();
        movie_id = getIntent().getIntExtra(MOVIE_ID, movie_id);
        check = getIntent().getStringExtra(LOCAL_STATUS);
        movie_url = TMDB_URL + "" + movie_id;
        region1 = Locale.getDefault().getCountry();
        recyclerView.setHasFixedSize(true);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));

        prepareView();
        GetVideoMovie();
        getDetailMovie();
        getRecomendation();

        if (savedInstanceState != null) {

            detailMovie = savedInstanceState.getParcelable("movie");
            getDetailMovie();
            GetVideoMovie();
        }
      //  imageView6title.setAnimation(AnimationUtils.loadAnimation(this,R.animator.scale_animation));


        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                showDetailMovie(movieRecomendationsList.get(position).getId());
                showVideoMovie(movieRecomendationsList.get(position).getId());


            }
        });
//

//        YouTubePlayerView youTubePlayerView = findViewById(R.id.yt_player);
//        youTubePlayerView.initialize(API_KEYYT, this);


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("movies", detailMovie);
    }

    private void prepareView() {
        progressBar.setVisibility(View.GONE);
        adapter = new RecylerRecomentationAdapter(getApplicationContext(), movieRecomendationsList);
        recyclerView.setAdapter(adapter);
    }


    private void getDetailMovie() {
        apiClient = ApiClient.getInstance();
        detailMovieCall = apiClient.getApi().getDetailMovie(movie_id, API_KEY);
        detailMovieCall.enqueue(new Callback<DetailMovie>() {
            @Override
            public void onResponse(Call<DetailMovie> call, Response<DetailMovie> response) {
                if (response.isSuccessful()) {
                    detailMovie = response.body();

                    //mProgress.dissmis();
                    String poster_url = IMAGE_BASE_URL + "w185" + detailMovie.getPosterPath();
                    String backdrop_url = IMAGE_BASE_URL + "w780" + detailMovie.getBackdropPath();
                    title1.setText(detailMovie.getOriginalTitle());
                    title2.setText(detailMovie.getOverview());
                    Log.i(TAG, "data detail movie masuk");
//                    movieTitleBig.setText(detailMovie.getTitle());
//                    movieYear.setText(detailMovie.getReleaseDate().split("-")[0]);
//
//
////                    movieRate.setText(detailMovie.getVoteAverage().toString());
//                    movieReleaseStatus.setText(detailMovie.getStatus());
//                    movieReleaseDate.setText(dateFormat(detailMovie.getReleaseDate()));
//                    movieRuntime.setText(detailMovie.getRuntime() + getString(R.string.app_name));
//                    movieOverview.setText(detailMovie.getOverview());
//                    movieLanguage.setText(detailMovie.getOriginalLanguage());
//                    movieHomepage.setText(detailMovie.getHomepage());

                    Glide.with(getApplicationContext())
                            .load(poster_url)
                            .placeholder(R.drawable.ic_menu_camera)
                            .error(R.drawable.ic_menu_camera)
                            .crossFade()
                            .into(imageView3title);
//
                    Glide.with(getApplicationContext())
                            .load(backdrop_url)
                            .placeholder(R.drawable.ic_menu_gallery)
                            .error(R.drawable.ic_menu_gallery)
                            .crossFade()
                            .into(imageView6title);


//                    int myWidth = 600;
//                    int myHeight = 900;


//                    Glide.with(getApplicationContext())
//                            .load(poster_url)
//                            .asBitmap()
//                            .into(new SimpleTarget<Bitmap>(myWidth, myHeight) {
//                                @Override
//                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                                    // mengekstrak warna dari gambar yang digunakan
//                                    Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
//                                        @Override
//                                        public void onGenerated(Palette palette) {
//                                            mutedColor = palette.getMutedColor(R.attr.colorPrimary);
//
//                                        }
//                                    });
//                                }
//                            });

                }
            }

            @Override
            public void onFailure(Call<DetailMovie> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(getBaseContext(),
                            "Request Timeout.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getBaseContext(),
                            "Connection Error!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    private void GetVideoMovie() {
        apiClient = ApiClient.getInstance();
        VideoCall = apiClient.getApi().getVideo(movie_id, API_KEY, LANGUAGE);
        VideoCall.enqueue(new Callback<Video>() {
            @Override
            public void onResponse(Call<Video> call, Response<Video> response) {
                if (response.isSuccessful()) {
                    if (resultsItemVideo != null) {
                        resultsItemVideo = response.body().getResults();
                        Log.i(TAG, "data video masuk");

                    } else {
                        Toast.makeText(context, "no DaTa", Toast.LENGTH_SHORT).show();
                    }


                    // youTubePlayer.cueVideo(resultsItemVideo.get(0).getKey());
//                    title2.setText(resultsItemVideo.get(0).getKey());


                }
            }

            @Override
            public void onFailure(Call<Video> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(getBaseContext(),
                            "Request Timeout.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getBaseContext(),
                            "Connection Error!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void getRecomendation() {
        apiClient = ApiClient.getInstance();
        call = apiClient.getApi().getRecomendasi(movie_id, API_KEY, LANGUAGE, PAGE);
        call.enqueue(new Callback<Recomendasi>() {
            @Override
            public void onResponse(Call<Recomendasi> call, Response<Recomendasi> response) {
                if (response.isSuccessful()) {
                    movieRecomendationsList = response.body().getResults();
                    if (movieRecomendationsList != null) {
                        progressBar.setVisibility(View.GONE);
                        adapter = new RecylerRecomentationAdapter(getApplicationContext(), movieRecomendationsList);
                        recyclerView.setAdapter(adapter);
                        Log.i(TAG, "respon Recomendation masuk");
                    }


                }
            }

            @Override
            public void onFailure(Call<Recomendasi> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(getBaseContext(),
                            "Request Timeout.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getBaseContext(),
                            "Connection Error!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

//        ContentValues values = new ContentValues();
//        values.put(TITLE, detailMovie.getTitle());


    }

    private String dateFormat(String oldDate) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = null;
        try {
            myDate = dateFormat.parse(oldDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat newFormat = new SimpleDateFormat("MMMM dd, yyyy");
        String finalDate = newFormat.format(myDate);

        return finalDate;

    }


    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void showDetailMovie(int movie_id) {
        String local = "0";
        Intent movieintent = new Intent(getApplicationContext(), DetailMoviesActivity.class);
        movieintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        movieintent.putExtra(DetailMoviesActivity.MOVIE_ID, movie_id);
        movieintent.putExtra(DetailMoviesActivity.LOCAL_STATUS, local);
        getApplicationContext().startActivity(movieintent);


    }

    private void showVideoMovie(int movie_id) {
        String local = "0";
        Intent movieintent = new Intent(getApplicationContext(), DetailMoviesActivity.class);
        movieintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        movieintent.putExtra(DetailMoviesActivity.MOVIE_ID, movie_id);
        movieintent.putExtra(DetailMoviesActivity.LOCAL_STATUS, local);
        getApplicationContext().startActivity(movieintent);


    }

    private void showRecomendationMovie(int movie_id) {
        String local = "0";
        Intent movieintent = new Intent(getApplicationContext(), DetailMoviesActivity.class);
        movieintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        movieintent.putExtra(DetailMoviesActivity.MOVIE_ID, movie_id);
        movieintent.putExtra(DetailMoviesActivity.LOCAL_STATUS, local);
        getApplicationContext().startActivity(movieintent);
    }


    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };


    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playbackEventListener);


//            youTubePlayer.cueVideo(resultsItemVideo.get(nilai).getKey());
//            Log.i(TAG, "key film masuk");


    }

    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };
}
