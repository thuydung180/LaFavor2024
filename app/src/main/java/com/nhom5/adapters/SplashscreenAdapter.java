package com.nhom5.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.nhom5.lafavor2024.R;




public class SplashscreenAdapter extends PagerAdapter {
    Context context;
    int images[] = {

            R.drawable.splashscreen1,
            R.drawable.splashscreen2,
            R.drawable.splashscreen3,

    };

    int heading[] = {
            R.string.title,
            R.string.title,
            R.string.title,
    };

    int description[] = {
            R.string.description,
            R.string.description,
            R.string.description,
    };

    public SplashscreenAdapter(Context context) {
        this.context = context;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_splashscreen, container, false);

        ImageView imvSplashscreen = view.findViewById(R.id.imvSplashscreen);
        TextView txtTitle = view.findViewById(R.id.txtTitle);
        TextView txtDescription = view.findViewById(R.id.txtDescription);

        imvSplashscreen.setImageResource(images[position]);
        txtTitle.setText(heading[position]);
        txtDescription.setText(description[position]);

        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //remove view
        container.removeView((LinearLayout) object);
    }

    @Override
    public int getCount() {
        return heading.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
