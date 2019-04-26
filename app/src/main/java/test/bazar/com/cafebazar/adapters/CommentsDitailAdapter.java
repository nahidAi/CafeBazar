package test.bazar.com.cafebazar.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import test.bazar.com.cafebazar.R;
import test.bazar.com.cafebazar.models.Comment;
import test.bazar.com.cafebazar.retrofit.ApiClient;
import test.bazar.com.cafebazar.retrofit.ApiService;

public class CommentsDitailAdapter extends RecyclerView.Adapter<CommentsDitailAdapter.commentsDitailsViewHolder> {
    Context context;
    List<Comment> comments;
    SharedPreferences sharedPreferences;
    String userId;


    public CommentsDitailAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
        sharedPreferences = context.getSharedPreferences("home", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "");
    }

    @NonNull
    @Override
    public commentsDitailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_row_detail, parent, false);
        return new commentsDitailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final commentsDitailsViewHolder holder, int position) {
        final Comment comment = comments.get(position);
        holder.txtUserName.setText(comment.getUser_name());
        holder.txtCommentTitle.setText(comment.getTitle());
        holder.txtLikeCount.setText(comment.getLike());
        holder.txtDislikeCount.setText(comment.getDisLike());
        holder.ratingBar.setRating(Float.parseFloat(comment.getStar()));
        holder.imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiService service = ApiClient.getClient().create(ApiService.class);
                Call<ResponseBody> call = service.setVote("like",userId ,comment.getId());
                call.enqueue(new Callback<ResponseBody>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String like = response.body().string();
                            like = like.trim();

                            if(!like.equals("شما قبلا به این نظر رای داده اید")){
                                int likeCount=Integer.parseInt(holder.txtLikeCount.getText().toString());
                                holder.txtLikeCount.setText(likeCount+1+"");
                            }
                            Toast.makeText(context, like + "", Toast.LENGTH_SHORT).show();
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
        holder.imgDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ApiService service = ApiClient.getClient().create(ApiService.class);
                Call<ResponseBody> call = service.setVote("disLike",userId,comment.getId());
                call.enqueue(new Callback<ResponseBody>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String disLike = response.body().string();
                            disLike = disLike.trim();
                            if(!disLike.equals("شما قبلا به این نظر رای داده اید")){
                                int disLikeCount=Integer.parseInt(holder.txtDislikeCount.getText().toString());
                                holder.txtDislikeCount.setText(disLikeCount+1+"");
                            }
                            Toast.makeText(context, disLike + "", Toast.LENGTH_SHORT).show();
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


    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class commentsDitailsViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLike;
        ImageView imgDislike;
        TextView txtLikeCount;
        TextView txtDislikeCount;
        TextView txtCommentTitle;
        TextView txtUserName;
        AppCompatRatingBar ratingBar;


        public commentsDitailsViewHolder(View itemView) {
            super(itemView);
            imgLike = itemView.findViewById(R.id.img_commentDetailRow_like);
            imgDislike = itemView.findViewById(R.id.img_commentDetailRow_dislike);
            txtLikeCount = itemView.findViewById(R.id.txt_commentDetailRow_likeCount);
            txtDislikeCount = itemView.findViewById(R.id.txt_commentDetailRow_dislikeCount);
            txtCommentTitle = itemView.findViewById(R.id.txt_commentDetailRow_desc);
            txtUserName = itemView.findViewById(R.id.txt_commentDetailRow_userName);
            ratingBar = itemView.findViewById(R.id.rt_commentDetailRow_ratingBar);


        }
    }
}
