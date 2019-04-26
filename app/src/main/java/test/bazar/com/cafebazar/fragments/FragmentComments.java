package test.bazar.com.cafebazar.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import test.bazar.com.cafebazar.R;
import test.bazar.com.cafebazar.adapters.CommentAdapter;
import test.bazar.com.cafebazar.adapters.CommentsDitailAdapter;
import test.bazar.com.cafebazar.models.Comment;
import test.bazar.com.cafebazar.retrofit.ApiClient;
import test.bazar.com.cafebazar.retrofit.ApiService;


public class FragmentComments extends Fragment {
    List<Comment> comments;
    public RecyclerView commentsRecycle;
    View view;
    TextView toolbarName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_comment, container, false);
        Bundle bundle = getArguments();
        String appId = bundle.getString("app_id");
        setupViews();
        getComments(appId);
        return view;
    }

    public void setupViews() {
        commentsRecycle = view.findViewById(R.id.rv_fragmentComment_comments);
        commentsRecycle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        toolbarName =view.findViewById(R.id.txt_usualToolbar_name);
        toolbarName.setText("نظرها");
    }

    private void getComments(String appId) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<List<Comment>> call = service.getAppComments(appId);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                comments = response.body();
               commentsRecycle.setAdapter(new CommentsDitailAdapter(getContext(), comments));

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });
    }
}
