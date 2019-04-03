package test.bazar.com.cafebazar.models;


import com.google.gson.annotations.SerializedName;

public class Slider {
    // دقیقا برای بی نیاز بودن از پارس کردن این انوتیشن هارو مینویسیم
    //و این نام های داخل انوتیشن ها باید همنام با کلیدهای جیسون باشه
    @SerializedName("id")
    private String id;
    @SerializedName("url")
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
