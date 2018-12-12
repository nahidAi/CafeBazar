package test.bazar.com.cafebazar.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import test.bazar.com.cafebazar.R;

public class SlidesAdapter extends RecyclerView.Adapter<SlidesAdapter.SlidesViewHolder>{
    Context context;
    List<String>slides;

    public SlidesAdapter(Context context, List<String> slides) {
        this.context = context;
        this.slides = slides;
    }

    @NonNull
    @Override
    public SlidesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.slides_raw_item,parent,false);
        return new SlidesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SlidesViewHolder holder, int position) {
        String mySlidesImage = slides.get(position);
        Picasso.with(context).load(mySlidesImage).into(holder.slideImage);


    }

    @Override
    public int getItemCount() {
        return slides.size();
    }

    public  class SlidesViewHolder extends RecyclerView.ViewHolder {
        ImageView slideImage;
        public SlidesViewHolder(View itemView) {
            super(itemView);
            slideImage = itemView.findViewById(R.id.slideRaw_image);
        }
    }
}
