package com.mike.animation.test;


import android.animation.ValueAnimator;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.Choreographer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.core.text.TextUtilsCompat;

import com.mike.animation.R;
import com.mike.animation.base.BaseActivity;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.view)
    TextView view;

    @Override
    public int getContentViewId() {
        return R.layout.activity_test;
    }

    ValueAnimator valueAnimator;

    @Override
    public void afterSetContentView() {
//        valueAnimator = new ValueAnimator();
//        valueAnimator.setIntValues(0, 500);
//        valueAnimator.setDuration(20000);
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                view.setTranslationX((int) animation.getAnimatedValue());
//                Log.d(TAG, "-->" + SystemClock.uptimeMillis());
//            }
//        });
        //valueAnimator.start();
        Log.d(TAG, "-->" + "abcdefg");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick");
            }
        });


        choreographer.postFrameCallback(c);
    }

    long nano = 0;
    int frames = 0;
    long nanos = 1000000000L;
    Choreographer.FrameCallback c = new Choreographer.FrameCallback() {
        @Override
        public void doFrame(long frameTimeNanos) {
            //Log.d(TAG, "-->" + frameTimeNanos);
            frames++;
            if (nano == 0) {
                nano = frameTimeNanos;
            } else {
                if (frameTimeNanos - nano > nanos) {
                    view.setText(String.valueOf(frames));
                    nano = frameTimeNanos;
                    frames = 0;
                }
            }
            choreographer.postFrameCallback(this);
        }
    };

    final Choreographer choreographer = Choreographer.getInstance();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //valueAnimator.cancel();
        choreographer.removeFrameCallback(c);
    }
}
