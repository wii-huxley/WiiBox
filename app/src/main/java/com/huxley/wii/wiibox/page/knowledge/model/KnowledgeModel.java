package com.huxley.wii.wiibox.page.knowledge.model;

import android.os.Environment;

import com.huxley.wii.wiibox.common.WiiApp;
import com.huxley.wii.wiitools.common.Utils.L;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import cn.finalteam.toolsfinal.io.FileUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * KnowledgeModel
 * Created by LeiJin01 on 2016/8/15.
 */
public class KnowledgeModel {

    private int node;

    public KnowledgeModel() {
    }

    public Observable<ArrayList<KnowledgeBean>> getKnowledges() {
        File filesDir = WiiApp.getContext().getFilesDir();
        L.i(filesDir.getAbsolutePath());
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        L.i(externalStorageDirectory.getAbsolutePath());
        node = 1;
        return Observable.just(externalStorageDirectory.getAbsolutePath() + File.separator + "wiibox" + File.separator + "LearningNotes")
                .subscribeOn(Schedulers.io())
                .map(s -> getListFiles(s, null))
                .observeOn(AndroidSchedulers.mainThread());
    }

    /***
     * 获取指定目录下的所有的文件（不包括文件夹），采用了递归
     *
     * @param obj
     * @return
     */
    public ArrayList<KnowledgeBean> getListFiles(Object obj, KnowledgeBean knowledge) {
        File directory;
        if (obj instanceof File) {
            directory = (File) obj;
        } else {
            directory = new File(obj.toString());
        }
        ArrayList<KnowledgeBean> knowledgeList = new ArrayList<>();
        if (directory.isFile()) {
            KnowledgeBean knowledgeBean = new KnowledgeBean();
            knowledgeBean.id = node;
            knowledgeBean.path = directory.getAbsolutePath();
            knowledgeBean.name = directory.getName().replaceAll("\\.md", "");
            knowledgeBean.pId = knowledge == null ? 0 : knowledge.id;
            knowledgeList.add(knowledgeBean);
            node ++;
        } else if (directory.isDirectory()) {
            KnowledgeBean knowledgeBean = new KnowledgeBean();
            knowledgeBean.id = node;
            knowledgeBean.path = directory.getAbsolutePath();
            knowledgeBean.name = directory.getName();
            knowledgeBean.pId = knowledge == null ? 0 : knowledge.id;
            knowledgeList.add(knowledgeBean);
            node ++;
            for (File fileOne : directory.listFiles()) {
                knowledgeList.addAll(getListFiles(fileOne, knowledgeBean));
            }
        }
        return knowledgeList;
    }

    public Observable<String> getKnowledgeContent(String path) {
        return Observable.just(new File(path))
                .subscribeOn(Schedulers.io())
                .map(file -> {
                    try {
                        return FileUtils.readFileToString(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).observeOn(AndroidSchedulers.mainThread());
    }
}