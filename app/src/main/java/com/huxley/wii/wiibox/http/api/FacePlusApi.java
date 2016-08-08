package com.huxley.wii.wiibox.http.api;

import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by huxley on 16/8/8.
 */

public interface FacePlusApi {

    String BASE_URL = "http://apicn.faceplusplus.com/v2/";

    String API_KEY = "1e3c5e63de907ca3fafcbb5ad4773109";
    String API_SECRET = "hl6SuzxwVLqLPz7bhDWBYxXZzw7F1hqp";

    /** 检测给定图片(Image)中的所有人脸(Face)的位置和相应的面部属性 */
    @POST("detection/detect")
    Observable<Object> detectionDetect (
            @Field("url") String url,
            @Part("img") MultipartBody.Part img, //待检测图片的URL 或者 通过POST方法上传的二进制数据，原始图片大小需要小于1M

            @Field("mode") String mode, //检测模式可以是normal(默认) 或者 oneface 。在oneface模式中，检测器仅找出图片中最大的一张脸。
            @Field("attribute") String attribute, //可以是none或者由逗号分割的属性列表。默认为gender, age, race, smiling。目前支持的属性包括：gender, age, race, smiling, glass, pose
            @Field("tag") String tag, //可以为图片中检测出的每一张Face指定一个不包含^@,&=*'"等非法字符且不超过255字节的字符串作为tag，tag信息可以通过 /info/get_face 查询
            @Field("async") String async //如果置为true，该API将会以异步方式被调用；也就是立即返回一个session id，稍后可通过/info/get_session查询结果。默认值为false
    );

    /** 检测给定人脸(Face)相应的面部轮廓，五官等关键点的位置，包括25点和83点两种模式 */
    @POST("detection/landmark")
    Observable<Object> detectionLandmark(
            @Field("face_id") String faceId, //待检测人脸的face_id

            @Field("type") String type //表示返回的关键点个数，目前支持83p或25p，默认为83p
    );


    /**  */
    /**  */
    /**  */
    /**  */
    /**  */
    /**  */
    /**  */
    /**  */
    /**  */
    /**  */
    /**  */
    /**  */
    /**  */
}
