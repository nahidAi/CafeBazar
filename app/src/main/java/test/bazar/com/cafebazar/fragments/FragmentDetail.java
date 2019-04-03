package test.bazar.com.cafebazar.fragments;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import test.bazar.com.cafebazar.R;
import test.bazar.com.cafebazar.adapters.CommentAdapter;
import test.bazar.com.cafebazar.adapters.SlidesAdapter;
import test.bazar.com.cafebazar.models.App;
import test.bazar.com.cafebazar.models.Comment;
import test.bazar.com.cafebazar.retrofit.ApiClient;
import test.bazar.com.cafebazar.retrofit.ApiService;

public class FragmentDetail extends Fragment {
    TextView txtName;
    TextView toolbarAppname;
    TextView txtKind;
    AppCompatRatingBar ratingBar;
    TextView txtMore;
    ImageView imgIcon;
    TextView developer;
    TextView txtOptionDownload;
    TextView txtOptionAverage;
    TextView txtOptionCommentCount;
    TextView txtOptionSize;
    TextView txtOptionCatName;
    ImageView imgCatIcon;
    View view;
    String bName;
    String bId;
    String bIcon;
    App app;
    String bKind;
    List<String> slides;
    RecyclerView slidesRecycler;
    RecyclerView commentRecyclerView;
    TextView txtDesc;
    SharedPreferences sharedPreferences;
    int stars = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_detail, container, false);
        Bundle bundle = getArguments();
        bName = bundle.getString("name");
        bId = bundle.getString("id");
        bIcon = bundle.getString("icon");
        bKind = bundle.getString("kind");
        firstSetup();
        getuniqeAppFromserver();

        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void firstSetup() {
        txtName = view.findViewById(R.id.txt_fragmentDetail_appName);
        txtKind = view.findViewById(R.id.txt_fragmentDetail_appPayment);
        imgIcon = view.findViewById(R.id.img_fragment_detail_icon);
        txtMore = view.findViewById(R.id.txt_fragmentDetail_more);
        toolbarAppname = view.findViewById(R.id.txt_fragmentDetailToolbar_appName);
        Picasso.with(getContext()).load(bIcon).into(imgIcon);
        txtName.setText(bName);
        if (bKind.equals("free")) {
            txtKind.setText("+رایگان");
        } else {
            txtKind.setText("+پرداخت درون برنامه ای");
        }
        developer = view.findViewById(R.id.txt_fragmentDetail_developer);
        txtOptionDownload = view.findViewById(R.id.txt_fragmentDetail_optionDownload);
        txtOptionAverage = view.findViewById(R.id.txt_fragmentDetail_average);
        txtOptionCommentCount = view.findViewById(R.id.txt_fragmentDetail_commentCount);
        txtOptionSize = view.findViewById(R.id.txt_fragmentDetail_optionSize);
        txtOptionCatName = view.findViewById(R.id.txt_fragmentDetail_catName);
        imgCatIcon = view.findViewById(R.id.img_fragmentDetail_catImg);
        txtDesc = view.findViewById(R.id.txt_fragmentDetail_desc);
        slidesRecycler = view.findViewById(R.id.rv_fragmentDetail_slides);
        slidesRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        commentRecyclerView = view.findViewById(R.id.rv_fragmentDetail_comments);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        ratingBar = view.findViewById(R.id.rt_fragmentDetail_setStar);
        ratingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_UP){
                sharedPreferences = getContext().getSharedPreferences("home", Context.MODE_PRIVATE);
                final String userId = sharedPreferences.getString("userId","");
                if (userId.equals("")){
                    Toast.makeText(getContext(), "لطفا وارد حساب کاربری خود شوید", Toast.LENGTH_SHORT).show();
                }else {
                    final Dialog dialog;
                    dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.dialog);
                    final AppCompatRatingBar ratingBarDialog = dialog.findViewById(R.id.rt_dialog_ratingBar);
                    final EditText edtComment = dialog.findViewById(R.id.edt_dialog_commentTitle);
                    TextView txtUserName = dialog.findViewById(R.id.txt_dialog_userName);
                    TextView saveComment = dialog.findViewById(R.id.txt_dialog_saveComment);
                    saveComment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int star = Math.round(ratingBarDialog.getRating());
                            String commentTitle = edtComment.getText().toString();
                            String appId = app.getId();
                            ApiService service = ApiClient.getClient().create(ApiService.class);
                            Toast.makeText(getActivity(), userId+"", Toast.LENGTH_SHORT).show();
                            Call<ResponseBody> call = service.addComment(appId, userId, star, commentTitle);
                            call.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    try {
                                        String addResult = response.body().string();
                                        addResult = addResult.trim();
                                        if (addResult.equals("ok")){
                                            Toast.makeText(getContext(), " نظر شما با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                       // Log.i("LOG", "onResponse: " + response.body().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });
                        }
                    });
                    dialog.show();
                }
                }



                return true;

            }
        });



    }


    public void getuniqeAppFromserver() {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<App> call = service.getUniqeApp(bId);
        call.enqueue(new Callback<App>() {
            @Override
            public void onResponse(Call<App> call, Response<App> response) {
                app = response.body();
                slides = app.getSlides();
                developer.setText(app.getUserName());
                txtOptionDownload.setText(app.getDownload());
                List<Comment>comment = app.getComments();
                for (int i = 0; i < comment.size(); i++) {
                    stars += Integer.parseInt(comment.get(i).getStar());
                }
                float avrage = (float)stars/comment.size();
                txtOptionAverage.setText(String.format("%.2f",avrage)+"");
               // txtOptionAverage.setText(stars/comment.size()+"");
                txtOptionCommentCount.setText(comment.size() + "نظر");




                txtOptionSize.setText(app.getSize() + "مگابایت");
                txtOptionCatName.setText(app.getCatName());
                Picasso.with(getContext()).load(app.getCatIcon()).into(imgCatIcon);
                slidesRecycler.setAdapter(new SlidesAdapter(getContext(), slides));
                txtDesc.setText(app.getDecs());
                List<Comment> comments = app.getComments();
                commentRecyclerView.setAdapter(new CommentAdapter(comments, getContext()));
                toolbarAppname.setText(bName);
                txtMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("desc", app.getDecs());
                        FragmentManager manager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = manager.beginTransaction();
                        FragmentDescription fragmentDescription = new FragmentDescription();
                        fragmentDescription.setArguments(bundle);
                        fragmentTransaction.replace(R.id.rel_parent_allView, fragmentDescription);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });

            }


            @Override
            public void onFailure(Call<App> call, Throwable t) {
                Toast.makeText(getContext(), t.toString() + "", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
