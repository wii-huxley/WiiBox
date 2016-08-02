package com.huxley.wii.wiibox.common.widget.ExpandingPager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.huxley.wii.wiibox.R;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 */
public class QsContainFragment extends Fragment {

    private static final String BUNDLE_FRONT = "front";
    private static final String BUNDLE_BACK = "back";

    Fragment fragment1, fragment2;
    private View back;
    private View front;
    private View layout3;

    private boolean closed = true;

    private float startY = 0;
    private View.OnClickListener listener;

    //    public static Fragment getInstance(Fragment fragment1, Fragment fragment2) {
//        return new QsContainFragment(fragment1, fragment2);
//    }
    public QsContainFragment() {
        // Required empty public constructor
    }

    public static QsContainFragment getInstance(Fragment front, Fragment back) {
        QsContainFragment fragment = new QsContainFragment();
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_FRONT, (Serializable) front);
        args.putSerializable(BUNDLE_BACK, (Serializable) back);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.fragment1 = (Fragment) getArguments().get(BUNDLE_FRONT);
        this.fragment2 = (Fragment) getArguments().get(BUNDLE_BACK);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_qs_contain, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getChildFragmentManager().beginTransaction()
            .add(R.id.front, fragment1)
            .add(R.id.bottomLayout, fragment2)
            .commit();

        back = view.findViewById(R.id.back);
        front = view.findViewById(R.id.front);
        layout3 = view.findViewById(R.id.bottomLayout);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float my = 0;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        my = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (event.getY() - startY > 0) {
                            close();
                            return true;
                        }
                        break;
                }
                return false;
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClosed()) {
                    doAnimation();
                } else {
                    listener.onClick(v);
                }
            }
        });
    }

    /**
     * expanding close
     */
    public void close() {
        doAnimationOut();
    }

    /**
     * expanding is closed?
     *
     * @return
     */
    public boolean isClosed() {
//        Log.e("scale", ViewCompat.getScaleX(back)+""+(ViewCompat.getScaleX(back)!=1)+"!"+(ViewCompat.getScaleX(back)==1.2));
//        return ((float)ViewCompat.getScaleX(back)) == 1.2;
        Log.e("boolean", closed+"");
        return closed;
    }

    /**
     * expanding out clickListener
     *
     * @param listener
     */
    public void setOnExpandingClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    private void doAnimation() {
        ViewGroup.LayoutParams layoutParams = layout3.getLayoutParams();
        layoutParams.height = (int) (front.getHeight() * 1.2 / 4 * 1.2);
        layout3.setLayoutParams(layoutParams);

        ViewCompat.setTranslationY(front, 0f);
        ViewCompat.animate(front).scaleX(1f).translationY(-front.getHeight() / 4f);
        ViewCompat.setPivotY(back, 0);
        ViewCompat.animate(back).scaleX(1.2f).scaleY(1.2f);

        ViewCompat.setElevation(front, 30f);
        closed = false;
    }

    private void doAnimationOut() {
        ViewCompat.animate(front).scaleX(1f).translationY(0f);
        ViewCompat.animate(back).scaleX(1f).scaleY(1f);

        ViewCompat.setElevation(front, 20f);
        closed = true;
    }

}
