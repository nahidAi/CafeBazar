package test.bazar.com.cafebazar.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.com.infinitescrollprovider.InfiniteScrollProvider;
import ss.com.infinitescrollprovider.OnLoadMoreListener;
import test.bazar.com.cafebazar.R;
import test.bazar.com.cafebazar.adapters.MostSellAdapter;
import test.bazar.com.cafebazar.models.App;
import test.bazar.com.cafebazar.retrofit.ApiClient;
import test.bazar.com.cafebazar.retrofit.ApiService;

public class FragmentMostPopular extends Fragment {
    ImageView imgSlide;
    RecyclerView recyclerView;
    View view;
    List<App> apps;
    ProgressBar progressBar;
    String imgUrl = "https://s.cafebazaar.ir/1/icons/ir.geekop.gnote_512x512.png";
    NestedScrollView nestedScrollView;
    MostSellAdapter mostSellAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_most_popular, container, false);
        setupViews();
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                            scrollY > oldScrollY) {
                        progressBar.setVisibility(View.VISIBLE);
                        getMostSellApp();

                    }
                }
            }
        });
       /* InfiniteScrollProvider infiniteScrollProvider = new InfiniteScrollProvider();
        infiniteScrollProvider.attach(recyclerView, new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Toast.makeText(getContext(), "رسیدیم به ته خط", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.VISIBLE);

            }
        });*/

        getMostSellApp();
        return view;
    }

    private void setupViews() {

        nestedScrollView = view.findViewById(R.id.nestedScrollview);
        progressBar = view.findViewById(R.id.progressBar_fragmentMostPopular);
        imgSlide = view.findViewById(R.id.img_fragmentPopular);
        recyclerView = view.findViewById(R.id.rv_fragmentMostSell_recycle);
        Picasso.with(getContext()).load(imgUrl).into(imgSlide);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mostSellAdapter = new MostSellAdapter(getContext());
        recyclerView.setAdapter(mostSellAdapter);
    }

    private void getMostSellApp() {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<List<App>> call = service.getMostsell();
        call.enqueue(new Callback<List<App>>() {
            @Override
            public void onResponse(Call<List<App>> call, Response<List<App>> response) {
                apps = response.body();
                mostSellAdapter.addData(apps);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<App>> call, Throwable t) {

            }
        });
    }
}
