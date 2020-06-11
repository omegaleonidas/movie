package com.Sidiq.moviecataloc.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.Sidiq.moviecataloc.Adapter.RecylerPopulerAdapter;
import com.Sidiq.moviecataloc.Adapter.SliderPagerAdapter;
import com.Sidiq.moviecataloc.BuildConfig;
import com.Sidiq.moviecataloc.DetailMoviesActivity;
import com.Sidiq.moviecataloc.ItemClickSupport;
import com.Sidiq.moviecataloc.R;
import com.Sidiq.moviecataloc.Retrofit.ApiClient;
import com.Sidiq.moviecataloc.model.MoviePopuler;
import com.Sidiq.moviecataloc.model.MovieRespon;
import com.Sidiq.moviecataloc.model.Slide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private static final String API_KEY = BuildConfig.API_KEY;
    private static final String LAGUAGE = "en-US";
    private int currentPage = 1;
    private String region = null;
    private int nilai = 0;
    private List<MoviePopuler> list;

    private AppBarConfiguration mAppBarConfiguration;
    private RecylerPopulerAdapter adapter;
    private List<Slide> list1;
    private Call<MovieRespon> call;
    private ApiClient apiClient = null;


    private List<MoviePopuler> moviePopulers = new ArrayList<>();

    @BindView(R.id.Recyler_MoviePopuler)
    RecyclerView recyclerView;


    @BindView(R.id.slider_pager)
    ViewPager sliderpager;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);


        region = Locale.getDefault().getCountry();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        if (savedInstanceState != null) {
            moviePopulers = savedInstanceState.getParcelableArrayList("movies");
            prepareView();
        } else {
            getMoviePopuler();
        }
//
        SliderPagerAdapter adapter = new SliderPagerAdapter(getActivity(), list1);

        sliderpager.setAdapter(adapter);

        list1 = new ArrayList<>();
        list1.add(new Slide(R.drawable.ic_menu_gallery,"silasldasd"));
        list1.add(new Slide(R.drawable.ic_menu_gallery,"silasldasd"));
        list1.add(new Slide(R.drawable.ic_menu_gallery,"silasldasd"));

//        list = new ArrayList<>();
//        list.add(new MoviePopuler("R.drawable.gradient_bg","asdadasd"));


        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showDetailMovie(moviePopulers.get(position).getId());

            }
        });


//

        return view;

    }

    private void prepareView() {

    }


    private void showDetailMovie(int movie_id) {
        String local = "0";
        Intent movieintent = new Intent(getActivity(), DetailMoviesActivity.class);
        movieintent.putExtra(DetailMoviesActivity.MOVIE_ID, movie_id);
        movieintent.putExtra(DetailMoviesActivity.LOCAL_STATUS, local);
        getActivity().startActivity(movieintent);


    }


    private void getMoviePopuler() {
        apiClient = ApiClient.getInstance();
        call = apiClient.getApi().getPopularMovie(API_KEY, LAGUAGE, currentPage, region);
        call.enqueue(new Callback<MovieRespon>() {
            @Override
            public void onResponse(Call<MovieRespon> call, Response<MovieRespon> response) {
                if (response.isSuccessful()) {
                    moviePopulers = response.body().getResults();
                    if (moviePopulers != null) {
                        adapter = new RecylerPopulerAdapter(getActivity(), moviePopulers);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(getActivity(), "empty", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieRespon> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
