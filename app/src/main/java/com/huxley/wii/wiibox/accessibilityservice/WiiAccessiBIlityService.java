package com.huxley.wii.wiibox.accessibilityservice;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.huxley.wii.wiitools.common.Utils.GsonUtils;
import com.huxley.wii.wiitools.common.Utils.L;

import java.util.List;

/**
 *
 * Created by LeiJin01 on 2016/8/4.
 */
public class WiiAccessibilityService extends AccessibilityService {

    private static final String BUTTON    = "android.widget.Button";
    private static final String TEXT_VIEW = "android.widget.TextView";

    private static final String PACKAGE_NAME_PACKAGE_INSTALLER = "com.android.packageinstaller";
//    private static final String PACKAGE_NAME_WEIXIN = "com.tencent.mm";
//
//    private final static String MM_PNAME = "com.tencent.mm";
//    boolean hasAction  = false;
//    boolean locked     = false;
//    boolean background = false;
//    private String name;
//    private String scontent;
//    AccessibilityNodeInfo itemNodeinfo;
//    private KeyguardManager.KeyguardLock kl;
//    private Handler handler = new Handler();

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        String packageName = event.getPackageName().toString();
        if (PACKAGE_NAME_PACKAGE_INSTALLER.equals(packageName)) {
            findAndPerformActionButton("下一步", event);
            findAndPerformActionButton("安装", event);
            findAndPerformActionButton("完成", event);
        }
    }

    @Override
    public void onInterrupt() {

    }

    private void findAndPerformActionButton(String text, AccessibilityEvent event) {
        // 当前激活窗体的根节点
        if (getRootInActiveWindow() == null) {
            return;
        }
        // 通过文字找到当前的节点
        List<AccessibilityNodeInfo> nodes = getRootInActiveWindow().findAccessibilityNodeInfosByText(text);
        for (AccessibilityNodeInfo node : nodes) {
            if (BUTTON.equals(node.getClassName()) && node.isEnabled()) {
                // 执行点击
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                L.json(GsonUtils.get().toJson(event));
            }
        }
    }
}
