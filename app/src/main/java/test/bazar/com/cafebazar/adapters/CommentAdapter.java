package test.bazar.com.cafebazar.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import test.bazar.com.cafebazar.R;
import test.bazar.com.cafebazar.models.Comment;

/**
 * Created by USER on 11/29/2018.
 */

public class CommentAdapter extends RecyclerView .Adapter <CommentAdapter.CommentViewHolder>{
    List<Comment>commentList;
    Context context;

    public CommentAdapter(List<Comment> commentList, Context context) {
        this.commentList = commentList;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_raw,parent,false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.ratingBar.setRating(Float.parseFloat(comment.getStar()));
        holder.txtUsername.setText(comment.getUser_name());
        holder.txtDesc.setText(comment.getTitle());


    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public  class CommentViewHolder extends  RecyclerView.ViewHolder {
        TextView txtUsername;
        TextView txtDesc;
        AppCompatRatingBar ratingBar;
        public CommentViewHolder(View itemView) {
            super(itemView);
            txtUsername = itemView.findViewById(R.id.txt_commentDetailRow_userName);
            txtDesc = itemView.findViewById(R.id.txt_commentDetailRow_desc);
            ratingBar = itemView.findViewById(R.id.rt_commentDetailRow_ratingBar);
        }
    }
}