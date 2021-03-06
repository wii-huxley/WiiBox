package com.huxley.wii.wiibox.common.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.Constant;
import com.huxley.wii.wiibox.common.ui.PhotoActivity;
import com.huxley.wii.wiibox.common.ui.photoAlbum.PhotoAlbumActivity;
import com.huxley.wii.wiibox.page.codekk.CodekkActivity;
import com.huxley.wii.wiibox.page.codekk.detail.CodekkDetailActivity;
import com.huxley.wii.wiibox.page.dytt.DyttActivity;
import com.huxley.wii.wiibox.page.dytt.detail.DyttDetailActivity;
import com.huxley.wii.wiibox.page.dytt.model.DyttListBean;
import com.huxley.wii.wiibox.page.knowledge.detail.KnowledgeDetailActivity;
import com.huxley.wii.wiibox.page.knowledge.model.KnowledgeBean;
import com.huxley.wii.wiibox.page.loginRegister.LoginRegisterActivity;
import com.huxley.wii.wiibox.page.main.MainActivity;
import com.huxley.wii.wiibox.page.main.androidtools.alignedtext.AlignedTextActivity;
import com.huxley.wii.wiibox.page.main.androidtools.bezier.BezierActivity;
import com.huxley.wii.wiibox.page.main.androidtools.blurview.BlurViewActivity;
import com.huxley.wii.wiibox.page.main.androidtools.expandingPager.ExpandingPagerActivity;
import com.huxley.wii.wiibox.page.main.androidtools.gobang.GobangActivity;
import com.huxley.wii.wiibox.page.main.androidtools.navigationTabBar.NavigationTabBarActivity;
import com.huxley.wii.wiibox.page.main.androidtools.stepview.StepViewActivity;
import com.huxley.wii.wiibox.page.main.androidtools.tagFlowLayout.CategoryActivity;
import com.huxley.wii.wiibox.page.main.androidtools.treeview.TreeViewActivity;
import com.huxley.wii.wiibox.page.main.gank.detail.GankDataDetailActivity;
import com.huxley.wii.wiibox.page.main.gank.model.GankInfo;
import com.huxley.wii.wiibox.page.ting56.Ting56Activity;
import com.huxley.wii.wiibox.page.ting56.detail.TingPlayActivity;
import com.huxley.wii.wiibox.page.user.UserActivity;
import com.thefinestartist.finestwebview.FinestWebView;

import butterknife.ButterKnife;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

/**
 *
 * Created by huxley on 16/5/9.
 */
public class UIHelper {

    public static void jumpSplash(final Activity activity) {
        activity.getWindow().getDecorView().post(() -> new Handler().postDelayed(() -> {
            activity.startActivity(new Intent(activity, MainActivity.class));
            activity.finish();
        }, 1000));
    }

    public static void setOnClickListener(Object object, View... views) {
        if (!(object instanceof View.OnClickListener)) {
            return;
        }
        for (View view : views) {
            view.setOnClickListener((View.OnClickListener)object);
        }
    }


    public static void setOnClickListener(View.OnClickListener listener, View... views) {
        for (View view : views) {
            view.setOnClickListener(listener);
        }
    }

    public static Toolbar createToolbar(AppCompatActivity activity, View rootView, String title) {
        Toolbar toolbar = ButterKnife.findById(rootView, R.id.toolbar);
        if (title != null) {
            toolbar.setTitle(title);
        }
        (activity).setSupportActionBar(toolbar);
        return toolbar;
    }

    public static Toolbar createToolbar(AppCompatActivity activity, View rootView, @StringRes int titleRes) {
        Toolbar toolbar = ButterKnife.findById(rootView, R.id.toolbar);
        if (titleRes != 0) {
            toolbar.setTitle(titleRes);
        }
        (activity).setSupportActionBar(toolbar);
        return toolbar;
    }

    public static Toolbar createToolbar(AppCompatActivity activity, String title) {
        Toolbar toolbar = ButterKnife.findById(activity, R.id.toolbar);
        if (title != null) {
            toolbar.setTitle(title);
        }
        (activity).setSupportActionBar(toolbar);
        return toolbar;
    }

    public static Toolbar createToolbar(AppCompatActivity activity, @StringRes int titleRes) {
        Toolbar toolbar = ButterKnife.findById(activity, R.id.toolbar);
        if (titleRes != 0) {
            toolbar.setTitle(titleRes);
        }
        (activity).setSupportActionBar(toolbar);
        return toolbar;
    }

    public static void openWebView(String url, String title, Context context) {
        new FinestWebView.Builder(context)
                .urlColor(ResHelper.getColor(R.color.wii_color_50))
                .setCustomAnimations(R.anim.activity_fade_in, R.anim.activity_fade_out, R.anim.activity_fade_in, R.anim.activity_fade_out)
                .titleColor(ResHelper.getColor(R.color.wii_color_50))
                .titleDefault(title)
                .show(url);
    }

    public static void startPhotoAlbumActivity(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, PhotoAlbumActivity.class));
        }
    }

    public static void startGobangActivity(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, GobangActivity.class));
        }
    }

    public static void startExpandingPagerActivity(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, ExpandingPagerActivity.class));
        }
    }

    public static void startCategoryActivity(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, CategoryActivity.class));
        }
    }

    public static void setTreeViewActivity(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, TreeViewActivity.class));
        }
    }

    public static void startIndexableListViewActivity(Context context) {
        if (context != null) {
            context.startActivity(new Intent());
        }
    }

    public static void setNavigationTabBarActivity(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, NavigationTabBarActivity.class));
        }
    }

    public static void setStepViewActivity(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, StepViewActivity.class));
        }
    }

    public static void setAlignedTextActivity(Context context) {
        checkNotNull(context).startActivity(new Intent(checkNotNull(context), AlignedTextActivity.class));
    }


    public static void startChapterListActivity(Context context, String url, String name) {
        Intent intent = new Intent(checkNotNull(context), TingPlayActivity.class);
        if (url != null && name != null) {
            intent.putExtra(Constant.Key.URL, url);
            intent.putExtra(Constant.Key.NAME, name);
        }
        checkNotNull(context).startActivity(intent);
    }

    public static void startDyttActivity(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, DyttActivity.class));
        }
    }

    public static void startDyttDetailActivity(Context context, DyttListBean.MovieInfo movieInfo) {
        if (context != null) {
            Intent intent = new Intent(context, DyttDetailActivity.class);
            if (movieInfo != null) {
                intent.putExtra(Constant.Key.DATE, movieInfo);
            }
            context.startActivity(intent);
        }
    }

    public static void startTing56Activity(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, Ting56Activity.class));
        }
    }

    public static void startCodekkActivity(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, CodekkActivity.class));
        }
    }

    public static void startBlurViewActivity(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, BlurViewActivity.class));
        }
    }

    public static void startGankDataDetailActivity(Activity activity, GankInfo gankInfo, int position, ActivityOptionsCompat optionsCompat) {
        Intent intent = new Intent(activity, GankDataDetailActivity.class);
        intent.putExtra(Constant.Key.DATA, gankInfo);
        intent.putExtra(Constant.Key.POSITION, position);
        ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle());
    }

    public static void startPhotoActivity(Activity activity, String url, ActivityOptionsCompat optionsCompat) {
        Intent intent = new Intent(activity, PhotoActivity.class);
        intent.putExtra(Constant.Key.URL, url);
        ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle());
    }

    /** 唤醒迅雷 */
    public static void setXunLeiActivity(Context context, Uri ftpUrl) {
        context.startActivity(new Intent("android.intent.action.VIEW", ftpUrl));
    }

    public static void startLoginActivity(Activity activity, ActivityOptionsCompat optionsCompat) {
        if (activity != null) {
            ActivityCompat.startActivity(activity, new Intent(activity, LoginRegisterActivity.class), optionsCompat.toBundle());
        }
    }

    public static void startKnowledgeDetailActivity(Context context, KnowledgeBean knowledgeBean) {
        if (context != null) {
            Intent intent = new Intent(context, KnowledgeDetailActivity.class);
            if (knowledgeBean != null) {
                intent.putExtra(Constant.Key.DATA, knowledgeBean);
            }
            context.startActivity(intent);
        }
    }

    public static void startUserActivity(Activity activity) {
        if (activity != null) {
            activity.startActivity(new Intent(activity, UserActivity.class));
        }
    }

    public static void startCodekkDetailActivity(Activity activity, String id, String name) {
        if (activity != null) {
            Intent intent = new Intent(activity, CodekkDetailActivity.class);
            intent.putExtra(Constant.Key.ID, id);
            intent.putExtra(Constant.Key.NAME, name);
            activity.startActivity(intent);
        }
    }

    public static void startBezierActivity(Activity activity) {
        if (activity != null) {
            activity.startActivity(new Intent(activity, BezierActivity.class));
        }
    }
}
