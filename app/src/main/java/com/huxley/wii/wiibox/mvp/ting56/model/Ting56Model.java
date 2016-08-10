package com.huxley.wii.wiibox.mvp.ting56.model;

import com.huxley.wii.wiitools.common.Utils.CodeUtils;
import com.huxley.wii.wiitools.common.Utils.L;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 *
 * Created by huxley on 16/7/12.
 */
public class Ting56Model {

    private static Ting56Model mModel;
    public static final String URL_BASE = "http://www.ting56.com";
    private static final String URL_SEARCH = URL_BASE + "/search.asp?searchword=";

    private Ting56Model() {

    }

    public static Ting56Model getInstance() {
        if (mModel == null) {
            synchronized (Ting56Model.class) {
                if (mModel == null) {
                    mModel = new Ting56Model();
                }
            }
        }
        return mModel;
    }

    public Observable<Ting56ListBean> search(String url){
        return Observable.just(url)
                .map(this::loadSearch);
    }

    public Observable<Ting56ListBean> getList(String url){
        return Observable.just(url)
                .subscribeOn(Schedulers.io())
                .map(this::loadList)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Ting56ListBean> search_1(String url){
        return Observable.just(url)
                .map(this::loadSearch_1);
    }

    private Ting56ListBean loadList(String url) {
        Ting56ListBean ting56ListBean = new Ting56ListBean();
        try {
            Document doc = Jsoup.connect(url).get();
            Element content = doc.select("div[class=content]").first();
            Elements books = content.select("div[class=book]");
            Ting56Bean audioBookInfo;
            for (Element book : books) {
                L.i(book.toString());
                audioBookInfo = new Ting56Bean();
                audioBookInfo.cover = book.select("a>img").first().attr("original");//"/pic/images/2016-6/201662220322335038.jpg"
                Element name = book.select("dl>dt>a").first();
                audioBookInfo.name = name.text();
                audioBookInfo.url = name.attr("href");
                audioBookInfo.content = book.select("dd[class=js]").first().text();
                audioBookInfo.status = book.select("dd[class=zt]").first().select("a").first().text();
                audioBookInfo.info = book.select("dd[class=zz]").first().text() + " " + book.select("dd[class=by]").first().text();
                ting56ListBean.ting56BeanList.add(audioBookInfo);
            }
            Elements as = content.select("div[class=fanye]").first().select("a");
            Element a = as.get(as.size() - 2);
            if ("下一页".equals(a.text())) {
                ting56ListBean.nextUrl = a.attr("href");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ting56ListBean;
    }

    private Ting56ListBean loadSearch(String content) {
        Ting56ListBean ting56ListBean = new Ting56ListBean();
        try {
            Document doc = Jsoup.connect(URL_SEARCH + CodeUtils.urlEncode(content, "gb2312")).get();
            Element xiaoshuo = doc.select("div[class=xiaoshuo left]").first();
            Elements lis = xiaoshuo.select("li");
            Ting56Bean audioBookInfo;
            for (Element ls : lis) {
                audioBookInfo = new Ting56Bean();
                audioBookInfo.cover = ls.select("a>img").first().attr("src");//"/pic/images/2016-6/201662220322335038.jpg"
                Element name = ls.select("dl>dt>a").first();
                audioBookInfo.name = name.text();
                audioBookInfo.url = name.attr("href");
                audioBookInfo.status = ls.select("dl>dt>span").first().text();
                Elements dds = ls.select("dl>dd");
                audioBookInfo.content = dds.first().text();
                audioBookInfo.info = dds.last().text();
                ting56ListBean.ting56BeanList.add(audioBookInfo);
            }
            L.json(ting56ListBean.toString());
            Elements as = xiaoshuo.select("div[class=pagelink]").first().select("a");
            Element a = as.get(as.size() - 2);
            if ("下一页".equals(a.text())) {
                ting56ListBean.nextUrl = a.attr("href");
            }
            L.json(ting56ListBean.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ting56ListBean;
    }

    private Ting56ListBean loadSearch_1(String url){
        Ting56ListBean ting56ListBean = new Ting56ListBean();
        try {
            Document doc = Jsoup.connect(url).get();
            Element xiaoshuo = doc.select("div[class=xiaoshuo left]").first();
            Elements lis = xiaoshuo.select("li");
            Ting56Bean audioBookInfo;
            for (Element ls : lis) {
                audioBookInfo = new Ting56Bean();
                audioBookInfo.cover = ls.select("a>img").first().attr("src");//"/pic/images/2016-6/201662220322335038.jpg"
                Element name = ls.select("dl>dt>a").first();
                audioBookInfo.name = name.text();
                audioBookInfo.url = name.attr("href");
                audioBookInfo.status = ls.select("dl>dt>span").first().text();
                Elements dds = ls.select("dl>dd");
                audioBookInfo.content = dds.first().text();
                audioBookInfo.info = dds.last().text();
                ting56ListBean.ting56BeanList.add(audioBookInfo);
            }
            L.json(ting56ListBean.toString());
            Elements as = xiaoshuo.select("div[class=pagelink]").first().select("a");
            Element a = as.get(as.size() - 2);
            if ("下一页".equals(a.text())) {
                ting56ListBean.nextUrl = a.attr("href");
            }
            L.json(ting56ListBean.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ting56ListBean;
    }

    public Observable<List<Ting56Bean.Ting56DetailBean>> getChapterInfos(String url) {
        return Observable.just(url)
                .flatMap(new Func1<String, Observable<Ting56Bean.Ting56DetailBean>>() {
                    @Override
                    public Observable<Ting56Bean.Ting56DetailBean> call(String content) {
                        return Observable.from(loadChapterInfos(content));
                    }
                }).toList();
    }

    private List<Ting56Bean.Ting56DetailBean> loadChapterInfos(String url){
        List<Ting56Bean.Ting56DetailBean> mp3List = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(URL_BASE + url).get();
            Elements lis = doc.select("div[id=vlink_1]").first().select("li");
            Ting56Bean.Ting56DetailBean chapterBean;
            for (Element li : lis) {
                chapterBean = new Ting56Bean.Ting56DetailBean();
                Element a = li.getElementsByTag("a").first();
                chapterBean.title = a.attr("title");
                chapterBean.url = a.attr("href");
                mp3List.add(chapterBean);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mp3List;
    }

    public Observable<String> getVoiceUrls(final String url) {
        return Observable.just(url).map(content -> getVoiceUrl(url));
    }

    private String getVoiceUrl(String url){
        String result = null;
        try {
            Connection connect = Jsoup.connect(URL_BASE + url);
            Document doc = connect.get();
            Element script = doc.select("head>script").last();
            String password = script.toString().split("'") [1] ;
            String[] splits = password.split("\\*");
            StringBuilder builder = new StringBuilder();
            for(int i = 1, len=splits.length; i<len; i++){
                builder.append((char)(Integer.parseInt(splits [i])));
            }
            result = builder.toString().split("&")[0];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
