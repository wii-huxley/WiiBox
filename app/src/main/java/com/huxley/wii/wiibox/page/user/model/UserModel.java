package com.huxley.wii.wiibox.page.user.model;

import android.app.Activity;
import android.content.Intent;

import com.huxley.wii.wiibox.common.WiiApp;
import com.huxley.wii.wiibox.common.utils.SP;
import com.huxley.wii.wiibox.page.loginRegister.model.UserInfo;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * username: 用户的用户名（必需）。
 * password: 用户的密码（必需）。
 * email: 用户的电子邮件地址（可选）。
 * emailVerified:邮箱认证状态（可选）。
 * mobilePhoneNumber：手机号码（可选）。
 * mobilePhoneNumberVerified：手机号码的认证状态（可选）
 * <p>
 * Created by huxley on 16/8/13.
 */
public class UserModel {

    private AuthInfo   mWeiboAuthInfo;
    private SsoHandler mWeiboSsoHandler;
    private Oauth2AccessToken mAccessToken;

    public UserModel(Activity activity) {
        mWeiboAuthInfo = WiiApp.getInstance().getAuthInfo();
        mWeiboSsoHandler = new SsoHandler(activity, mWeiboAuthInfo);
        mAccessToken = SP.User.readWeiboAccessToken();
    }

    public void weiboAuthorize(WeiboAuthListener listener) {
        mWeiboSsoHandler.authorize(listener);
    }

    public void weiboAuthorize(int requestCode, int resultCode, Intent data) {
        mWeiboSsoHandler.authorizeCallBack(requestCode, resultCode, data);
    }

    public boolean isWeiboSessionValid() {
        return mAccessToken.isSessionValid();
    }

    public void update(UserInfo userInfo, UpdateListener listener) {
        userInfo.update(getUserInfo().getObjectId(), listener);
    }

    public UserInfo getUserInfo() {
        return UserInfo.getCurrentUser(UserInfo.class);
    }

    // 退出登录
    public void logOut() {
        UserInfo.logOut();
    }

    // 密码修改
    public void updateCurrentUserPassword(String oldPassword, String newPassword, UpdateListener listener) {
        UserInfo.updateCurrentUserPassword(oldPassword, newPassword, listener);
    }

    // 邮箱验证
    public void requestEmailVerify(String email, UpdateListener listener) {
        BmobUser.requestEmailVerify(email, listener);
    }

    /**
     * 第三方账号一键注册或登录
     *
     * @param snsType     只能是三种取值中的一种：weibo、qq、weixin
     * @param accessToken 接口调用凭证
     * @param expiresIn   access_token的有效时间
     * @param userId      :用户身份的唯一标识，对应微博授权信息中的uid,对应qq和微信授权信息中的openid
     */
    public void loginWithAuthData(String snsType, String accessToken, String expiresIn, String userId, LogInListener<JSONObject> listener) {
        UserInfo.BmobThirdUserAuth authInfo = new UserInfo.BmobThirdUserAuth(snsType, accessToken, expiresIn, userId);
        UserInfo.loginWithAuthData(authInfo, listener);
    }

    /**
     * 账号关联
     */
    public void associateWithAuthData(String snsType, String accessToken, String expiresIn, String userId, UpdateListener listener) {
        UserInfo.BmobThirdUserAuth authInfo = new UserInfo.BmobThirdUserAuth(snsType, accessToken, expiresIn, userId);
        UserInfo.associateWithAuthData(authInfo, listener);
    }

    /**
     * 解除关联
     */
    public void dissociateAuthData(String snsType, UpdateListener listener) {
//        UserInfo.dissociateAuthData(snsType, listener);
    }

    public void uploadblock(File file, UploadFileListener listener) {
        BmobFile bmobFile = new BmobFile(file);
        bmobFile.uploadblock(listener);
    }

    public void uploadBatch() {
        //详细示例可查看BmobExample工程中BmobFileActivity类
        String filePath_mp3 = "/mnt/sdcard/testbmob/test1.png";
        String filePath_lrc = "/mnt/sdcard/testbmob/test2.png";
        final String[] filePaths = new String[2];
        filePaths[0] = filePath_mp3;
        filePaths[1] = filePath_lrc;
        BmobFile.uploadBatch(filePaths, new UploadBatchListener() {

            @Override
            public void onSuccess(List<BmobFile> files, List<String> urls) {
                //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                //2、urls-上传文件的完整url地址
                if (urls.size() == filePaths.length) {//如果数量相等，则代表文件全部上传完成
                    //do something
                }
            }

            @Override
            public void onError(int statuscode, String errormsg) {
            }

            @Override
            public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                //1、curIndex--表示当前第几个文件正在上传
                //2、curPercent--表示当前上传文件的进度值（百分比）
                //3、total--表示总的上传文件数
                //4、totalPercent--表示总的上传进度（百分比）
            }
        });
    }
}