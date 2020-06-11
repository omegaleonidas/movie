package com.Sidiq.moviecataloc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.Sidiq.moviecataloc.R;
import com.Sidiq.moviecataloc.model.Slide;

import java.util.List;

public class SliderPagerAdapter extends PagerAdapter {
    private Context mcontext;
    private List<Slide> moviePopulers;

    @Override
    public int getCount() {
       int count;
       if (moviePopulers == null) {
           count = -1;
    } else {
       count = moviePopulers.size();
    }
      return count;
    }

//    int count;
//        if (moviePopulers == null) {
//        count = -1;
//    } else {
//        count = moviePopulers.size();
//    }
//        return count;

    public SliderPagerAdapter(Context mcontext, List<Slide> moviePopulers) {
        this.mcontext = mcontext;
        this.moviePopulers = moviePopulers;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View slideLayout = inflater.inflate(R.layout.slide_item,null);

        ImageView slideImg = slideLayout.findViewById(R.id.slide_image);
        TextView textView = slideLayout.findViewById(R.id.slide_title);
       // slideImg.setImageResource(Integer.parseInt(moviePopulers.get(position).getPosterPath()));
       // textView.setText(moviePopulers.get(position).getTitle());
       slideImg.setImageResource(moviePopulers.get(position).getImage());
       textView.setText(moviePopulers.get(position).getTitle());





        container.addView(slideLayout);
        return slideLayout;



    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
