package com.huxley.wii.wiibox.mvp.faceplus.model;

/**
 * Created by huxley on 16/8/8.
 */
public class FacePlusModel {

    private static FacePlusModel instance;

    private FacePlusModel() {
    }

    public static FacePlusModel getInstance() {
        if (instance == null) {
            synchronized (FacePlusModel.class) {
                if (instance == null) {
                    instance = new FacePlusModel();
                }
            }
        }
        return instance;
    }
}