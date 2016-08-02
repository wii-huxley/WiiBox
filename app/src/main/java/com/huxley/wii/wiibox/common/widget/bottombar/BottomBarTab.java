package com.huxley.wii.wiibox.common.widget.bottombar;

import android.graphics.drawable.Drawable;

/**
 * Created by huxley on 16/5/21.
 */
public class BottomBarTab {

    private Drawable icon;
    private String name;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BottomBarTab(Drawable icon, String name, int id) {
        this.icon = icon;
        this.name = name;
        this.id = id;
    }

    public BottomBarTab(Drawable icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BottomBarTab{" +
                "icon=" + icon +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
