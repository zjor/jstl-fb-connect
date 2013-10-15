package org.zjor.example;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author: Sergey Royz
 * @since: 15.10.2013
 */
@Data
public class FacebookUser {

    @SerializedName("id")
    private String facebookId;

    @SerializedName("username")
    private String username;

    @SerializedName("name")
    private String fullName;

    @SerializedName("picture")
    private Picture picture;


    @Data
    public static class Picture {

        @SerializedName("data")
        private PictureData data;
    }


    @Data
    public static class PictureData {

        @SerializedName("url")
        String url;
    }

}
