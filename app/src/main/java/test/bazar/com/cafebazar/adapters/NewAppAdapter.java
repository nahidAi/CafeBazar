package test.bazar.com.cafebazar.adapters;



import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import test.bazar.com.cafebazar.R;
import test.bazar.com.cafebazar.fragments.FragmentDetail;
import test.bazar.com.cafebazar.models.App;

public class NewAppAdapter extends RecyclerView.Adapter<NewAppAdapter.newAppViewHolder> {
    Context context;
    List<App> apps;

    public NewAppAdapter(Context context, List<App> apps) {
        this.context = context;
        this.apps = apps;
    }

    @NonNull
    @Override
    public newAppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.newapp_row, parent, false);
        return new newAppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final newAppViewHolder holder, int position) {
        final App application = apps.get(position);
        holder.txtTitle.setText(application.getName());
        Picasso.with(context).load(application.getIcon()).into(holder.img);
        if (application.getKind().equals("free")) {
            holder.imgCoin.setVisibility(View.GONE);
        }
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id",application.getId());
                bundle.putString("name",application.getName());
                bundle.putString("kind",application.getKind());
                bundle.putString("icon",application.getIcon());
                FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                FragmentDetail fragmentDetail = new FragmentDetail();
                //میگم من به فرگمنت دیتیل یک باندل پاس میدم
                fragmentDetail.setArguments(bundle);
                fragmentTransaction.replace(R.id.rel_parent_allView,fragmentDetail);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    public class newAppViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout  parent;
        ImageView img;
        TextView txtTitle;
        ImageView imgCoin;

        public newAppViewHolder(View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.rel_appNew_parent);
            img = itemView.findViewById(R.id.img_newApp);
            txtTitle = itemView.findViewById(R.id.txt_newApp_title);
            imgCoin = itemView.findViewById(R.id.img_coin);
        }
    }
}
