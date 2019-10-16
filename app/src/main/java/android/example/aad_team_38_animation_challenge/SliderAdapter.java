package android.example.aad_team_38_animation_challenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    // Arrays of images for the onboard slides
    public int[] slide_images = {
            R.drawable.onboard_1,
            R.drawable.onboard_2,
            R.drawable.onboard_3
    };

    public int[] slide_headings = {
            R.string.onboard1Header,
            R.string.onboard2Header,
            R.string.onboard3Header
    };

    public int[] slide_description = {
            R.string.onboard1Body,
            R.string.onboard2Body,
            R.string.onboard3Body
    };


    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.onboard_slider, container, false);

        ImageView imageView = view.findViewById(R.id.image_view);
        TextView viewHeader = view.findViewById(R.id.textViewHeader);
        TextView viewBody = view.findViewById(R.id.textViewBody);

        imageView.setImageResource(slide_images[position]);
        viewHeader.setText(slide_headings[position]);
        viewBody.setText(slide_description[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
