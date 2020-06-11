package com.Sidiq.moviecataloc.ui.Recomendation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Sidiq.moviecataloc.Adapter.RecylerRecomentationAdapter;
import com.Sidiq.moviecataloc.BuildConfig;
import com.Sidiq.moviecataloc.DetailMoviesActivity;
import com.Sidiq.moviecataloc.ItemClickSupport;
import com.Sidiq.moviecataloc.R;
import com.Sidiq.moviecataloc.Retrofit.ApiClient;
import com.Sidiq.moviecataloc.model.Recomendasi.Recomendasi;
import com.Sidiq.moviecataloc.model.Recomendasi.Recomendation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecomendationFragment extends Fragment {

    private static String TAG = "RecomendationFragment";
    private RecomendationViewModel mViewModel;
    private int movie_id;
    private static final String API_KEY = BuildConfig.API_KEY;
    private static final String LANGUAGE = "en-US";
    public static String MOVIE_ID = "movie_id";
    private static int PAGE = 1;


    private int currentPage = 1;
    private String region1 = null;

    private RecylerRecomentationAdapter adapter;

    private Call<Recomendasi> call;
    private ApiClient apiClient = null;
    private List<Recomendation> movieRecomendationsList = new ArrayList<>();

    @BindView(R.id.TempatRecomentation1)
    RecyclerView recyclerView;

    public static RecomendationFragment newInstance() {
        return new RecomendationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recomendation_fragment, container, false);
        ButterKnife.bind(this, view);

        region1 = Locale.getDefault().getCountry();
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        getRecomendation();
        if (savedInstanceState != null) {
            movieRecomendationsList = savedInstanceState.getParcelableArrayList("movies");
            getRecomendation();
        }

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showDetailMovie(movieRecomendationsList.get(position).getId());
            }
        });
        getRecomendation();

        return view;
    }

    private void showDetailMovie(int movie_id) {
        String local = "0";
        Intent movieIdIntent = new Intent(getActivity(), DetailMoviesActivity.class);
        movieIdIntent.putExtra(DetailMoviesActivity.MOVIE_ID, movie_id);
        movieIdIntent.putExtra(DetailMoviesActivity.LOCAL_STATUS, local);
        getActivity().startActivity(movieIdIntent);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RecomendationViewModel.class);
        // TODO: Use the ViewModel
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
                        adapter = new RecylerRecomentationAdapter(getActivity(), movieRecomendationsList);
                        recyclerView.setAdapter(adapter);
                        Log.i(TAG, "respon Recomendation masuk");
                    }


                }
            }

            @Override
            public void onFailure(Call<Recomendasi> call, Throwable t) {


            }
        });

    }


}
