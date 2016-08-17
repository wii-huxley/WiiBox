package com.huxley.wii.wiibox.mvp.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.UIHelper;
import com.huxley.wii.wiibox.mvp.knowledge.KnowledgeFragment;
import com.huxley.wii.wiibox.mvp.knowledge.KnowledgePresenter;
import com.huxley.wii.wiibox.mvp.knowledge.model.KnowledgeModel;
import com.huxley.wii.wiibox.mvp.loginRegister.model.UserInfo;
import com.huxley.wii.wiibox.mvp.loginRegister.model.UserInfoEvent;
import com.huxley.wii.wiibox.mvp.main.androidtools.AndroidToolsFragment;
import com.huxley.wii.wiibox.mvp.main.gank.GankFragment;
import com.huxley.wii.wiibox.mvp.main.gank.GankPresenter;
import com.huxley.wii.wiibox.mvp.main.translate.TranslateFragment;
import com.huxley.wii.wiibox.mvp.main.translate.TranslatePresenter;
import com.huxley.wii.wiitools.base.BaseActivity;
import com.huxley.wii.wiitools.base.BaseFragment;
import com.huxley.wii.wiitools.common.helper.SnackbarHelper;
import com.huxley.wii.wiitools.common.manager.ActivityManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout     mDrawerLayout;
    private NavigationView   navigationView;
    private BaseFragment[][] fragments;

    /**
     * 0:是pagePosition
     * 1:是fragmentPosition
     */
    private int[] currentPositions = {0, -1};
    private ImageView ivHeaderPhoto;
    private TextView  tvUserName;
    private View      headerView;
    private UserInfo  userInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        initData();
        initView();
        initListener();
        EventBus.getDefault().register(this);
    }

    private void initData() {
        userInfo = UserInfo.getCurrentUser(UserInfo.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        fragments = new BaseFragment[4][];

        BaseFragment[] gankFragments = new BaseFragment[1];
        gankFragments[0] = GankFragment.newInstance();
        fragments[0] = gankFragments;
        new GankPresenter((GankFragment) gankFragments[0]);

        BaseFragment[] androidToolsFragments = new BaseFragment[1];
        androidToolsFragments[0] = AndroidToolsFragment.newInstance();
        fragments[1] = androidToolsFragments;

        BaseFragment[] translateFragments = new BaseFragment[1];
        translateFragments[0] = TranslateFragment.newInstance();
        fragments[2] = translateFragments;
        new TranslatePresenter((TranslateFragment) translateFragments[0]);

        BaseFragment[] knowledgeFragments = new BaseFragment[1];
        knowledgeFragments[0] = KnowledgeFragment.newInstance();
        fragments[3] = knowledgeFragments;
        new KnowledgePresenter((KnowledgeFragment) knowledgeFragments[0], new KnowledgeModel());

        mDrawerLayout = $(R.id.drawer_layout);

        navigationView = $(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);
        tvUserName = (TextView) headerView.findViewById(R.id.tvUserName);
        if (userInfo != null) {
            tvUserName.setText(userInfo.getUsername());
        }

        checkNotNull(navigationView).setItemIconTintList(null);
        showFragment(0, 0);
    }

    @SuppressWarnings("unchecked")
    private void initListener() {
        navigationView.setNavigationItemSelectedListener(this);

        headerView.setOnClickListener(v -> {
            Pair<View, String> textViewStringPair = Pair.create(tvUserName, tvUserName.getTransitionName());
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, textViewStringPair);
            if (userInfo == null) {
                UIHelper.startLoginActivity(this, optionsCompat);
            } else {
                UIHelper.startUserActivity(this);
            }
        });
    }

    private void showFragment(int pagePosition, int fragmentPosition) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragments[pagePosition][fragmentPosition].isAdded()) {
            transaction.add(R.id.contentFragment, fragments[pagePosition][fragmentPosition]);
        }
        if (currentPositions[1] >= 0) {
            transaction.hide(fragments[currentPositions[0]][currentPositions[1]]);
        }
        transaction.show(fragments[pagePosition][fragmentPosition]);
        transaction.commit();
        currentPositions[0] = pagePosition;
        currentPositions[1] = fragmentPosition;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navGank:
                changePage(0);
                break;
            case R.id.navAndroidTools:
                changePage(1);
                break;
            case R.id.navTranslate:
                changePage(2);
                break;
            case R.id.navKnowledge:
                changePage(3);
                break;

            case R.id.navCode:
                UIHelper.startCodekkActivity(this);
                break;
            case R.id.navMovie:
                UIHelper.startDyttActivity(this);
                break;
            case R.id.navTing56:
                UIHelper.startTing56Activity(this);
                break;

            case R.id.navSetting:
                SnackbarHelper.showInfo(mDrawerLayout, "暂无");
                break;
            case R.id.navExit:
                ActivityManager.getInstance().appExit(this);
                break;
        }
        menuItem.setChecked(true);
        mDrawerLayout.closeDrawers();
        return true;
    }

    private void changePage(int position) {
        if (currentPositions[0] != position) {
            showFragment(position, 0);
        }
    }

    @Override
    protected boolean back(int keyCode, KeyEvent event) {
        moveTaskToBack(false);
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserInfoEvent event) {
        tvUserName.setText(event.userInfo.getUsername());
    }
}