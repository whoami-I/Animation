package com.mike.animation.scene;

import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.ViewGroup;
import android.widget.Button;


import com.mike.animation.R;
import com.mike.animation.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SceneActivity extends BaseActivity {

    @BindView(R.id.btn_start_scene)
    Button btnStartScene;
    @BindView(R.id.scene_root)
    ViewGroup mSceneRoot;
    Scene scene1;
    Scene scene2;
    boolean toogle;

    @OnClick(R.id.btn_start_scene)
    public void clickStartScene() {
        TransitionSet transitionSet = new TransitionSet();
        ChangeBounds changeBounds = new ChangeBounds();
        ColorTransition colorTransition = new ColorTransition();
        transitionSet.setDuration(3000);
        transitionSet.addTransition(changeBounds)
                .addTransition(colorTransition)
                ;
        TransitionManager.go(toogle ? scene2 : scene1, transitionSet);
        toogle = !toogle;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_scene;
    }

    @Override
    public void afterSetContentView() {
        scene1 = Scene.getSceneForLayout(mSceneRoot, R.layout.scene_1, this);
        scene2 = Scene.getSceneForLayout(mSceneRoot, R.layout.scene_2, this);
        TransitionManager.go(scene1);
        toogle = true;
    }
}
