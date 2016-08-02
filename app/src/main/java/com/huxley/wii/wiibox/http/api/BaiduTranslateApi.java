package com.huxley.wii.wiibox.http.api;


import com.huxley.wii.wiibox.mvp.main.translate.model.BaiduTranslateInfo;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 *
 * Created by huxley on 16/4/14.
 */
public interface BaiduTranslateApi {

    String BASE_URL = "http://api.fanyi.baidu.com/";
    String APP_ID = "20160714000025190";
    String KEY = "Eq3OqdQTcOv37YEixpJq";
    String AUTO	 = "auto"; // 自动检测
    String ZH	 = "zh"; // 中文
    String EN	 = "en"; // 英语
    String YUE	 = "yue"; // 粤语
    String WYW	 = "wyw"; // 文言文
    String JP	 = "jp"; // 日语
    String KOR	 = "kor"; // 韩语
    String FRA	 = "fra"; // 法语
    String SPA	 = "spa"; // 西班牙语
    String TH	 = "th"; // 泰语
    String ARA	 = "ara"; // 阿拉伯语
    String RU	 = "ru"; // 俄语
    String PT	 = "pt"; // 葡萄牙语
    String DE	 = "de"; // 德语
    String IT	 = "it"; // 意大利语
    String EL	 = "el"; // 希腊语
    String NL	 = "nl"; // 荷兰语
    String PL	 = "pl"; // 波兰语
    String BUL	 = "bul"; // 保加利亚语
    String EST	 = "est"; // 爱沙尼亚语
    String DAN	 = "dan"; // 丹麦语
    String FIN	 = "fin"; // 芬兰语
    String CS	 = "cs"; // 捷克语
    String ROM	 = "rom"; // 罗马尼亚语
    String SLO	 = "slo"; // 斯洛文尼亚语
    String SWE	 = "swe"; // 瑞典语
    String HU	 = "hu"; // 匈牙利语
    String CHT	 = "cht"; // 繁体中文

    @FormUrlEncoded
    @POST("api/trans/vip/translate")
    Observable<BaiduTranslateInfo> translate(
            @Field("q") String q,
            @Field("from") String from,
            @Field("to") String to,
            @Field("appid") String appid,
            @Field("salt") int salt,
            @Field("sign") String sign
    );
}
