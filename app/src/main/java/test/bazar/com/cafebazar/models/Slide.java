package test.bazar.com.cafebazar.models;

import com.google.gson.annotations.SerializedName;



public class Slide {
    @SerializedName("slide")
    private  String imagUrl;

    public String getImagUrl() {
        return imagUrl;
    }

    public void setImagUrl(String imagUrl) {
        this.imagUrl = imagUrl;
    }
}
