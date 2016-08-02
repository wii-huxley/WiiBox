package com.huxley.wii.wiitools.common.helper;

import android.annotation.TargetApi;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.ComponentCallbacks;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.annotation.StyleableRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.huxley.wii.wiitools.common.WiiTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 上下文工具类
 * Created by huxley on 16/7/2.
 */
public class ContextHelper {

    // No Instance
    private ContextHelper() {
    }

    public static boolean bindService(Intent service, ServiceConnection conn, int flags) {
        return WiiTools.getContext().bindService(service, conn, flags);
    }

    public static int checkCallingOrSelfPermission(String permission) {
        return WiiTools.getContext().checkCallingOrSelfPermission(permission);
    }

    public static int checkCallingOrSelfUriPermission(Uri uri, int modeFlags) {
        return WiiTools.getContext().checkCallingOrSelfUriPermission(uri, modeFlags);
    }

    public static int checkCallingPermission(String permission) {
        return WiiTools.getContext().checkCallingPermission(permission);
    }

    public static int checkCallingUriPermission(Uri uri, int modeFlags) {
        return WiiTools.getContext().checkCallingUriPermission(uri, modeFlags);
    }

    public static int checkPermission(String permission, int pid, int uid) {
        return WiiTools.getContext().checkPermission(permission, pid, uid);
    }

    public static int checkSelfPermission(@NonNull String permission) {
        return ContextCompat.checkSelfPermission(WiiTools.getContext(), permission);
    }

    public static int checkUriPermission(Uri uri, int pid, int uid, int modeFlags) {
        return WiiTools.getContext().checkUriPermission(uri, pid, uid, modeFlags);
    }

    public static int checkUriPermission(Uri uri, String readPermission, String writePermission, int pid, int uid, int modeFlags) {
        return WiiTools.getContext().checkUriPermission(uri, readPermission, writePermission, pid, uid, modeFlags);
    }

    public static Context createPackageContext(String packageName, int flags) throws PackageManager.NameNotFoundException {
        return WiiTools.getContext().createPackageContext(packageName, flags);
    }

    public static String[] databaseList() {
        return WiiTools.getContext().databaseList();
    }

    public static boolean deleteDatabase(String name) {
        return WiiTools.getContext().deleteDatabase(name);
    }

    public static boolean deleteFile(String name) {
        return WiiTools.getContext().deleteFile(name);
    }

    public static void enforceCallingOrSelfPermission(String permission, String message) {
        WiiTools.getContext().enforceCallingOrSelfPermission(permission, message);
    }

    public static void enforceCallingOrSelfUriPermission(Uri uri, int modeFlags, String message) {
        WiiTools.getContext().enforceCallingOrSelfUriPermission(uri, modeFlags, message);
    }

    public static void enforceCallingPermission(String permission, String message) {
        WiiTools.getContext().enforceCallingPermission(permission, message);
    }

    public static void enforceCallingUriPermission(Uri uri, int modeFlags, String message) {
        WiiTools.getContext().enforceCallingUriPermission(uri, modeFlags, message);
    }

    public static void enforcePermission(String permission, int pid, int uid, String message) {
        WiiTools.getContext().enforcePermission(permission, pid, uid, message);
    }

    public static void enforceUriPermission(Uri uri, int pid, int uid, int modeFlags, String message) {
        WiiTools.getContext().enforceUriPermission(uri, pid, uid, modeFlags, message);
    }

    public static void enforceUriPermission(Uri uri, String readPermission, String writePermission, int pid, int uid, int modeFlags, String message) {
        WiiTools.getContext().enforceUriPermission(uri, readPermission, writePermission, pid, uid, modeFlags, message);
    }

    public static String[] fileList() {
        return WiiTools.getContext().fileList();
    }

    public static Context getApplicationContext() {
        return WiiTools.getContext().getApplicationContext();
    }

    public static ApplicationInfo getApplicationInfo() {
        return WiiTools.getContext().getApplicationInfo();
    }

    public static AssetManager getAssets() {
        return WiiTools.getContext().getAssets();
    }

    public static File getCacheDir() {
        return WiiTools.getContext().getCacheDir();
    }

    public static ClassLoader getClassLoader() {
        return WiiTools.getContext().getClassLoader();
    }

    public static File getCodeCacheDir() {
        return new ContextCompat().getCodeCacheDir(WiiTools.getContext());
    }

    @ColorInt
    public static int getColor(@ColorRes int colorRes) {
        return ContextCompat.getColor(WiiTools.getContext(), colorRes);
    }

    public static ColorStateList getColorStateList(@ColorRes int colorRes) {
        return ContextCompat.getColorStateList(WiiTools.getContext(), colorRes);
    }

    public static ContentResolver getContentResolver() {
        return WiiTools.getContext().getContentResolver();
    }

    public static File getDatabasePath(String name) {
        return WiiTools.getContext().getDatabasePath(name);
    }

    public static File getDir(String name, int mode) {
        return WiiTools.getContext().getDir(name, mode);
    }

    public static Drawable getDrawable(@DrawableRes int drawableRes) {
        return ContextCompat.getDrawable(WiiTools.getContext(), drawableRes);
    }

    @Nullable
    @TargetApi(8)
    public static File getExternalCacheDir() {
        return WiiTools.getContext().getExternalCacheDir();
    }

    public static File[] getExternalCacheDirs() {
        return ContextCompat.getExternalCacheDirs(WiiTools.getContext());
    }

    @Nullable
    @TargetApi(8)
    public static File getExternalFilesDir(String type) {
        return WiiTools.getContext().getExternalFilesDir(type);
    }

    public static File[] getExternalFilesDirs(String type) {
        return ContextCompat.getExternalFilesDirs(WiiTools.getContext(), type);
    }

    @TargetApi(21)
    public static File[] getExternalMediaDirs() {
        return WiiTools.getContext().getExternalMediaDirs();
    }

    public static File getFileStreamPath(String name) {
        return WiiTools.getContext().getFileStreamPath(name);
    }

    public static File getFilesDir() {
        return WiiTools.getContext().getFilesDir();
    }

    public static Looper getMainLooper() {
        return WiiTools.getContext().getMainLooper();
    }

    public static File getNoBackupFilesDir() {
        return new ContextCompat().getNoBackupFilesDir(WiiTools.getContext());
    }

    @TargetApi(11)
    public static File getObbDir() {
        return WiiTools.getContext().getObbDir();
    }

    public static File[] getObbDirs() {
        return ContextCompat.getObbDirs(WiiTools.getContext());
    }

    @TargetApi(8)
    public static String getPackageCodePath() {
        return WiiTools.getContext().getPackageCodePath();
    }

    public static PackageManager getPackageManager() {
        return WiiTools.getContext().getPackageManager();
    }

    public static String getPackageName() {
        return WiiTools.getContext().getPackageName();
    }

    @TargetApi(8)
    public static String getPackageResourcePath() {
        return WiiTools.getContext().getPackageResourcePath();
    }

    public static Resources getResources() {
        return WiiTools.getContext().getResources();
    }

    public static SharedPreferences getSharedPreferences(String name, int mode) {
        return WiiTools.getContext().getSharedPreferences(name, mode);
    }

    public static String getString(@StringRes int stringRes) {
        return WiiTools.getContext().getString(stringRes);
    }

    public static String getString(@StringRes int stringRes, Object... formatArgs) {
        return WiiTools.getContext().getString(stringRes, formatArgs);
    }

    @TargetApi(23)
    public static <T> T getSystemService(Class<T> serviceClass) {
        return WiiTools.getContext().getSystemService(serviceClass);
    }

    public static Object getSystemService(String name) {
        return WiiTools.getContext().getSystemService(name);
    }

    @TargetApi(23)
    public static String getSystemServiceName(Class<?> serviceClass) {
        return WiiTools.getContext().getSystemServiceName(serviceClass);
    }

    public static CharSequence getText(@StringRes int stringRes) {
        return WiiTools.getContext().getText(stringRes);
    }

    public static Resources.Theme getTheme() {
        return WiiTools.getContext().getTheme();
    }

    public static Drawable getWallpaper() {
        return WallpaperManager.getInstance(WiiTools.getContext()).getDrawable();
    }

    public static int getWallpaperDesiredMinimumHeight() {
        return WallpaperManager.getInstance(WiiTools.getContext()).getDesiredMinimumHeight();
    }

    public static int getWallpaperDesiredMinimumWidth() {
        return WallpaperManager.getInstance(WiiTools.getContext()).getDesiredMinimumWidth();
    }

    public static void grantUriPermission(String toPackage, Uri uri, int modeFlags) {
        WiiTools.getContext().grantUriPermission(toPackage, uri, modeFlags);
    }

    public static boolean isRestricted() {
        return WiiTools.getContext().isRestricted();
    }

    public static TypedArray obtainStyledAttributes(@StyleableRes int[] attrs) {
        return WiiTools.getContext().obtainStyledAttributes(attrs);
    }

    public static TypedArray obtainStyledAttributes(AttributeSet set, @StyleableRes int[] attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        return WiiTools.getContext().obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes);
    }

    public static TypedArray obtainStyledAttributes(AttributeSet set, @StyleableRes int[] attrs) {
        return WiiTools.getContext().obtainStyledAttributes(set, attrs);
    }

    public static TypedArray obtainStyledAttributes(@StyleRes int resid, @StyleableRes int[] attrs) {
        return WiiTools.getContext().obtainStyledAttributes(resid, attrs);
    }

    public static FileInputStream openFileInput(String name) throws FileNotFoundException {
        return WiiTools.getContext().openFileInput(name);
    }

    public static FileOutputStream openFileOutput(String name, int mode) throws FileNotFoundException {
        return WiiTools.getContext().openFileOutput(name, mode);
    }

    public static SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        return WiiTools.getContext().openOrCreateDatabase(name, mode, factory);
    }

    @TargetApi(11)
    public static SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        return WiiTools.getContext().openOrCreateDatabase(name, mode, factory, errorHandler);
    }

    public static Drawable peekWallpaper() {
        return WallpaperManager.getInstance(WiiTools.getContext()).peekDrawable();
    }

    @TargetApi(14)
    public static void registerComponentCallbacks(ComponentCallbacks callback) {
        WiiTools.getContext().registerComponentCallbacks(callback);
    }

    public static Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        return WiiTools.getContext().registerReceiver(receiver, filter);
    }

    public static Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, String broadcastPermission, Handler scheduler) {
        return WiiTools.getContext().registerReceiver(receiver, filter, broadcastPermission, scheduler);
    }

//    public static void removeStickyBroadcast(Intent intent) {
//        WiiTools.getContext().removeStickyBroadcast(intent);
//    }
//
//    @TargetApi(17)
//    public static void removeStickyBroadcastAsUser(Intent intent, UserHandle user) {
//        WiiTools.getContext().removeStickyBroadcastAsUser(intent, user);
//    }

    public static void revokeUriPermission(Uri uri, int modeFlags) {
        WiiTools.getContext().revokeUriPermission(uri, modeFlags);
    }

    public static void sendBroadcast(Intent intent, String receiverPermission) {
        WiiTools.getContext().sendBroadcast(intent, receiverPermission);
    }

    public static void sendBroadcast(Intent intent) {
        WiiTools.getContext().sendBroadcast(intent);
    }

    @TargetApi(17)
    public static void sendBroadcastAsUser(Intent intent, UserHandle user) {
        WiiTools.getContext().sendBroadcastAsUser(intent, user);
    }

    @TargetApi(17)
    public static void sendBroadcastAsUser(Intent intent, UserHandle user, String receiverPermission) {
        WiiTools.getContext().sendBroadcastAsUser(intent, user, receiverPermission);
    }

    public static void sendOrderedBroadcast(Intent intent, String receiverPermission, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
        WiiTools.getContext().sendOrderedBroadcast(intent, receiverPermission, resultReceiver, scheduler, initialCode, initialData, initialExtras);
    }

    public static void sendOrderedBroadcast(Intent intent, String receiverPermission) {
        WiiTools.getContext().sendOrderedBroadcast(intent, receiverPermission);
    }

    @TargetApi(17)
    public static void sendOrderedBroadcastAsUser(Intent intent, UserHandle user, String receiverPermission, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
        WiiTools.getContext().sendOrderedBroadcastAsUser(intent, user, receiverPermission, resultReceiver, scheduler, initialCode, initialData, initialExtras);
    }

//    public static void sendStickyBroadcast(Intent intent) {
//        WiiTools.getContext().sendStickyBroadcast(intent);
//    }
//
//    @TargetApi(17)
//    public static void sendStickyBroadcastAsUser(Intent intent, UserHandle user) {
//        WiiTools.getContext().sendStickyBroadcastAsUser(intent, user);
//    }
//
//    public static void sendStickyOrderedBroadcast(Intent intent, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
//        WiiTools.getContext().sendStickyOrderedBroadcast(intent, resultReceiver, scheduler, initialCode, initialData, initialExtras);
//    }
//
//    @TargetApi(17)
//    public static void sendStickyOrderedBroadcastAsUser(Intent intent, UserHandle user, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
//        WiiTools.getContext().sendStickyOrderedBroadcastAsUser(intent, user, resultReceiver, scheduler, initialCode, initialData, initialExtras);
//    }

    public static void setTheme(@StyleRes int styleRes) {
        WiiTools.getContext().setTheme(styleRes);
    }

    public static void setWallpaper(InputStream data) throws IOException {
        WallpaperManager.getInstance(WiiTools.getContext()).setStream(data);
    }

    public static void setWallpaper(Bitmap bitmap) throws IOException {
        WallpaperManager.getInstance(WiiTools.getContext()).setBitmap(bitmap);
    }

    public static boolean startActivities(Intent[] intents, Bundle options) {
        for (Intent intent : intents)
            if (intent != null)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return ContextCompat.startActivities(WiiTools.getContext(), intents, options);
    }

    public static boolean startActivities(Intent[] intents) {
        for (Intent intent : intents)
            if (intent != null)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return ContextCompat.startActivities(WiiTools.getContext(), intents);
    }

    public static void startActivity(@NonNull Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        WiiTools.getContext().startActivity(intent);
    }

    @TargetApi(16)
    public static void startActivity(Intent intent, Bundle options) {
        WiiTools.getContext().startActivity(intent, options);
    }

    public static boolean startInstrumentation(ComponentName className, String profileFile, Bundle arguments) {
        return WiiTools.getContext().startInstrumentation(className, profileFile, arguments);
    }

    @TargetApi(16)
    public static void startIntentSender(IntentSender intent, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, Bundle options) throws IntentSender.SendIntentException {
        WiiTools.getContext().startIntentSender(intent, fillInIntent, flagsMask, flagsValues, extraFlags, options);
    }

    public static void startIntentSender(IntentSender intent, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags) throws IntentSender.SendIntentException {
        WiiTools.getContext().startIntentSender(intent, fillInIntent, flagsMask, flagsValues, extraFlags);
    }

    public static ComponentName startService(Intent service) {
        return WiiTools.getContext().startService(service);
    }

    public static boolean stopService(Intent service) {
        return WiiTools.getContext().stopService(service);
    }

    public static void unbindService(ServiceConnection conn) {
        WiiTools.getContext().unbindService(conn);
    }

    @TargetApi(14)
    public static void unregisterComponentCallbacks(ComponentCallbacks callback) {
        WiiTools.getContext().unregisterComponentCallbacks(callback);
    }

    public static void unregisterReceiver(BroadcastReceiver receiver) {
        WiiTools.getContext().unregisterReceiver(receiver);
    }
}
