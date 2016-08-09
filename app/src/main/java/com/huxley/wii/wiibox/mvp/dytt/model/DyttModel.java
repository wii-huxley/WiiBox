package com.huxley.wii.wiibox.mvp.dytt.model;

import com.huxley.wii.wiitools.common.Utils.CodeUtils;
import com.huxley.wii.wiitools.common.Utils.StringUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * DyttModel
 * Created by huxley on 16/7/20.
 */
public class DyttModel {

    private static DyttModel instance;
    private static final String BaseUrl = "http://www.ygdy8.net";
    public static final String FirstUrl = "index.html";
    public static final String NewMovieBaseUrl = BaseUrl + "/html/gndy/dyzz/";//最新电影
    public static final String ChinaMovieBaseUrl = BaseUrl + "/html/gndy/china/";//国内电影
    public static final String OumeiMovieBaseUrl = BaseUrl + "/html/gndy/oumei/";//欧美电影
    public static final String RihanMovieBaseUrl = BaseUrl + "/html/gndy/rihan/";//日韩电影
    public static final String HytvMovieBaseUrl = BaseUrl + "/html/tv/hytv/";//华语电视
    public static final String RihantvMovieBaseUrl = BaseUrl + "/html/tv/rihantv/";//日韩电视
    public static final String OumeitvMovieBaseUrl = BaseUrl + "/html/tv/oumeitv/";//欧美电视
    public static final String Zongyi2013MovieBaseUrl = BaseUrl + "/html/zongyi2013/";//最新综艺
    public static final String Zongyi2009MovieBaseUrl = BaseUrl + "/html/2009zongyi/";//旧版综艺
    public static final String DongmanMovieBaseUrl = BaseUrl + "/html/dongman/";//动漫资源
    private Map<String, List<DyttListBean.MovieInfo>> mData;

    private DyttModel() {
        mData = new HashMap<>();
    }

    public static DyttModel getInstance() {
        if (instance == null) {
            synchronized (DyttModel.class) {
                if (instance == null) {
                    instance = new DyttModel();
                }
            }
        }
        return instance;
    }

    public Observable<DyttListBean> getMovieList(String url) {
        return Observable.just(url)
                .map(this::loadMovieList);
    }

    private DyttListBean loadMovieList(String url){
        DyttListBean movieListInfo = new DyttListBean();
        try {
            Document doc = Jsoup.parse(new URL(url).openStream(), "GBK", url);
            Element element = doc.select("div.co_content8").first();
            Elements tables = element.select("table");
            DyttListBean.MovieInfo movie;
            for (Element table : tables) {
                movie = new DyttListBean.MovieInfo();
                Element a = table.select("a").last();
                String aText = a.text();
                movie.name = StringUtil.getCenterString("《", "》", aText);
                movie.url = a.attr("href");
                movie.content = table.select("td").last().text();
                movieListInfo.mMovieInfos.add(movie);
            }
            Element x = element.select("div.x").first();
            Elements as = x.select("a");
            Element a = as.get(as.size() - 2);
            if ("下一页".equals(a.text())) {
                movieListInfo.nextUrl = a.attr("href");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movieListInfo;
    }

    public Observable<DyttDetailInfo> getMovieDetailInfo(String url) {
        return Observable.just(url)
                .map(this::loadMovieDetailInfo);
    }

    private DyttDetailInfo loadMovieDetailInfo(String url) {
        DyttDetailInfo movie = new DyttDetailInfo();
        try {
            Document doc = Jsoup.parse(new URL(BaseUrl + url).openStream(), "GBK", BaseUrl + url);
            Element co_content8 = doc.select("div.co_content8").first();
            Element zoom = co_content8.select("div#Zoom").first();
//            Elements spans = zoom.select("span").first().children();
//            if(spans.get(0).tagName() == "p"){
//            	spans = zoom.select("p");
//            }
            Elements imgs = zoom.select("img").remove();
            if(imgs != null){
                for (Element img : imgs) {
                    if(img != null){
                        String src = img.attr("src");
                        if(src != null){
                            movie.pics.add(src);
                        }
                    }
                }
            }
//            boolean isStart = true;
//            for (Element p : spans) {
//            	String pText = p.text();
//                if (isStart) {
//                    if (hasChinese(pText)) {
//                        movie.content = (p.toString());
//                        isStart = false;
//                    }
//                } else {
//                    if (pText.contains("下载地址")) {
//                        break;
//                    }
//                    movie.content += (p.toString());
//                }
//            }
            Elements as = zoom.select("table>tbody>tr>td>a");
            for (Element a : as) {
                movie.urls.add(a.text());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movie;
    }


    public String encode(String url) {
        StringBuilder builder = new StringBuilder("AA");
        for (char ch : url.toCharArray()) {
            if (StringUtil.isChinese2(ch)) {
                builder.append(CodeUtils.urlEncode(String.valueOf(ch), "utf-8"));
            } else {
                builder.append(ch);
            }
        }
        return "thunder://" + CodeUtils.base64encode(CodeUtils.utf16to8(builder.append("ZZ").toString()));
    }

    public String decode(String content) {
        return CodeUtils.urlDecode(
                CodeUtils.utf8to16(
                        CodeUtils.base64decode(
                                content.substring(10, content.length())
                        )
                ).substring(2, content.length()).substring(0, content.length() - 2),
                "utf-8"
        );
    }

    public Observable<DyttListBean> search (String content) {
        return Observable.just(content)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .switchMap(s -> Observable.just(loadSearch(s)))
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<DyttListBean> search_1 (String content) {
        return Observable.just(content)
                .subscribeOn(Schedulers.io())
                .map(this::loadSearch_1)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static final String SBaseUrl = "http://s.dydytt.net";
    private static final String SearchUrl = SBaseUrl + "/plus/search.php?keyword=";//搜索url
    private DyttListBean loadSearch(String s){
        DyttListBean movies = new DyttListBean();
        String url = SearchUrl + CodeUtils.urlEncode(s, "gb2312");
        System.out.println(url);
        try {
            Document doc = Jsoup.parse(new URL(url).openStream(), "GBK", url);
            Element element = doc.select("div.co_content8").first();
            Elements tables = element.select("table");
            Element remove = tables.remove(tables.size() - 1);
            Elements pageAs = remove.select("a");
            Element nextA = pageAs.get(pageAs.size() - 2);
            System.out.println(nextA);
            if("下一页".equals(nextA.text())){
                movies.nextUrl = nextA.attr("href");
            }
            DyttListBean.MovieInfo movie;
            for (Element table : tables) {
                movie = new DyttListBean.MovieInfo();
                Element a = table.select("a").first();
                String aText = a.text();
                movie.name = StringUtil.getCenterString("《", "》", aText);
                movie.url = a.attr("href");
                movie.content = table.select("tr").get(1).select("td").first().text();
                movies.mMovieInfos.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

    private DyttListBean loadSearch_1(String url){
        DyttListBean movies = new DyttListBean();
        try {
            Document doc = Jsoup.parse(new URL(url).openStream(), "GBK", url);
            Element element = doc.select("div.co_content8").first();
            Elements tables = element.select("table");
            Element remove = tables.remove(tables.size() - 1);
            Elements pageAs = remove.select("a");
            Element nextA = pageAs.get(pageAs.size() - 2);
            System.out.println(nextA);
            if("下一页".equals(nextA.text())){
                movies.nextUrl = nextA.attr("href");
            }
            DyttListBean.MovieInfo movie;
            for (Element table : tables) {
                movie = new DyttListBean.MovieInfo();
                Element a = table.select("a").first();
                String aText = a.text();
                movie.name = StringUtil.getCenterString("《", "》", aText);
                movie.url = a.attr("href");
                movie.content = table.select("tr").get(1).select("td").first().text();
                movies.mMovieInfos.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

    public void clearHomeData(String key) {
        if (mData.containsKey(key)) {
            mData.remove(key);
        }
    }

    public void setHomeData(String key, List<DyttListBean.MovieInfo> data) {
        if (mData.containsKey(key)) {
            mData.get(key).addAll(data);
        } else {
            this.mData.put(key, data);
        }
    }

    public List<DyttListBean.MovieInfo> getHomeData(String key) {
        if (mData.containsKey(key)) {
            return mData.get(key);
        } else {
            return null;
        }
    }
}