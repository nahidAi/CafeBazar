package test.bazar.com.cafebazar.retrofit;



import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import test.bazar.com.cafebazar.models.App;
import test.bazar.com.cafebazar.models.Banner;
import test.bazar.com.cafebazar.models.Comment;
import test.bazar.com.cafebazar.models.Slider;

public interface ApiService {
    @GET("getslide.php")
    Call<List<Slider>> getSliders();

    @GET("getbanners.php")
    Call<List<Banner>>getBanners();

    @GET("getnewapp.php")
    Call<List<App>>getNewApps();

    @GET("getupdateapp.php")
    Call<List<App>>getUpdatedApp();

    @GET("getuniqeapp.php")
    Call<App>getUniqeApp(@Query("id") String id);

    @FormUrlEncoded
    @POST("rating.php")
    Call<ResponseBody>addComment(@Field("app_id") String appId, @Field("user_id") String userId, @Field("star") int star, @Field("comment_title") String comment);

    @FormUrlEncoded
    @POST("sendsms.php")
    Call<ResponseBody>sendNumber(@Field("to")String phoneNumber);

    @FormUrlEncoded
    @POST("validation.php")
    Call<ResponseBody>sendvalidationCode(@Field("code")String validationCode,@Field("phone_number")String number);


    @FormUrlEncoded
    @POST("getallcomments.php")
    Call<List<Comment>> getAppComments(@Field("app_id") String appId);


    @GET("likedislike.php")
    Call<ResponseBody> setVote(@Query("vote") String vote,@Query("user_id") String userId,@Query("comment_id")String commentId);


    @GET("mostsell.php")
    Call<List<App>>getMostsell();
}
