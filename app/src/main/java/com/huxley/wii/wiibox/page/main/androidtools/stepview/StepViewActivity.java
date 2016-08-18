package com.huxley.wii.wiibox.page.main.androidtools.stepview;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiitools.base.BaseActivity;


/**
 * 日期：16/6/22 16:01
 * <p/>
 * 描述：
 */
public class StepViewActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_step_view;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        VerticalStepViewFragment mVerticalStepViewFragment = new VerticalStepViewFragment();
        getFragmentManager().beginTransaction().replace(R.id.container, mVerticalStepViewFragment).commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("StepView");
            toolbar.setNavigationIcon(R.drawable.ic_back);
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(v -> finish());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_step_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        VerticalStepViewFragment mVerticalStepViewFragment;
        DrawCanvasFragment mDrawCanvasFragment;
        HorizontalStepviewFragment mHorizontalStepviewFragment;
        VerticalStepViewSnapshotFragment mVerticalStepViewSnapshotFragment;
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_horizontal_stepview:
                mHorizontalStepviewFragment = new HorizontalStepviewFragment();
                fragmentTransaction.replace(R.id.container, mHorizontalStepviewFragment).commit();
                break;
            case R.id.action_drawcanvas:
                mDrawCanvasFragment = new DrawCanvasFragment();
                fragmentTransaction.replace(R.id.container, mDrawCanvasFragment).commit();
                break;
            case R.id.action_vertical_stepview:
                mVerticalStepViewFragment = new VerticalStepViewFragment();
                fragmentTransaction.replace(R.id.container, mVerticalStepViewFragment).commit();
                break;
            case R.id.action_vertical_stepview_snapshot:
                mVerticalStepViewSnapshotFragment = new VerticalStepViewSnapshotFragment();
                fragmentTransaction.replace(R.id.container, mVerticalStepViewSnapshotFragment).commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}