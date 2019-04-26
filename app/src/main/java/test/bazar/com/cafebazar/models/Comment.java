package test.bazar.com.cafebazar.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Comment implements Parcelable {
    //اینا دقیقا همنام با کلیدهای جیسون باید باشه وگرنه درست عمل نمیکنه و نال برمیگردونه
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("star")
    private  String star;
    @SerializedName("user_name")
    private  String user_name;
    @SerializedName("app_id")
    private String appId;
    @SerializedName("like")
    private String like;
    @SerializedName("dislike")
    private String disLike;


    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getDisLike() {
        return disLike;
    }

    public void setDisLike(String disLike) {
        this.disLike = disLike;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.star);
        dest.writeString(this.user_name);
    }

    public Comment() {
    }

    protected Comment(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.star = in.readString();
        this.user_name = in.readString();
    }

    public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
