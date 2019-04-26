package test.bazar.com.cafebazar.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import test.bazar.com.cafebazar.R;
import test.bazar.com.cafebazar.models.App;

public class MostSellAdapter extends RecyclerView.Adapter<MostSellAdapter.MostSellViewHolder> {
    List<App> apps;
    Context context;

    public MostSellAdapter(Context context) {
        this.apps = new ArrayList<>();
        this.context = context;
    }
    public void  addData(List<App>newApps){
        this.apps.addAll(newApps);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MostSellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mostsell_resycler_row,parent,false);
        return new MostSellViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MostSellViewHolder holder, int position) {
        App app = apps.get(position);
        holder.txtAppName.setText(app.getName());
        holder.txtKind.setText(app.getKind());
        Picasso.with(context).load(app.getIcon()).into(holder.imgSlide);

    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    public class MostSellViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSlide;
        TextView txtAppName;
        TextView txtKind;

        public MostSellViewHolder(View itemView) {
            super(itemView);
            imgSlide = itemView.findViewById(R.id.img_fragmentMostSell_appIcon);
            txtAppName = itemView.findViewById(R.id.txt_fragmentMostSell_appName);
            txtKind = itemView.findViewById(R.id.txt_fragmentMostSell_kind);
        }
    }
}
