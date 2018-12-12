package test.bazar.com.cafebazar.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import test.bazar.com.cafebazar.R;
import test.bazar.com.cafebazar.models.Banner;


public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerViewHolder> {
    private List<Banner> banners;
    private Context context;

    public BannerAdapter(List<Banner> banners, Context context) {
        this.banners = banners;
        this.context = context;
    }

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.banners_raw, parent, false);
        return new BannerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder holder, int position) {
        Banner banner = banners.get(position);
        Picasso.with(context).load(banner.getUrl()).into(holder.img);
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, " کلیک شد", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return banners.size();
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView img;
        private LinearLayout parent;

        public BannerViewHolder(View itemView) {
            super(itemView);
            img = (RoundedImageView) itemView.findViewById(R.id.img_bannerRaw);
            parent = itemView.findViewById(R.id.bannersRow_linear_parent);
        }
    }
}
