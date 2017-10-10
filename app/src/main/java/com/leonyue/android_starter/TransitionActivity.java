package com.leonyue.android_starter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class TransitionActivity extends AppCompatActivity {

    private Scene mScene1,mScene2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        final ViewGroup transitionContainer = (ViewGroup) findViewById(R.id.activity_transition_LinearLayout);
        final TextView text = (TextView) transitionContainer.findViewById(R.id.text);
        final Button button = (Button) transitionContainer.findViewById(R.id.button);


        mScene1 = Scene.getSceneForLayout(transitionContainer,R.layout.fragment_transition_scene_before,this);
        mScene2 = Scene.getSceneForLayout(transitionContainer,R.layout.fragment_transition_scene_after,this);

        button.setOnClickListener(new View.OnClickListener() {
            boolean visible;
            boolean before;

            /*
            接下来让我们看看有哪些 Transition ：
            ChangeBounds. 改变 View 的位置和大小。
            Fade. 继承自 Visibility 类，可以用来做最常用的淡入和淡出动画-，上个例子中 TextView的出现和消失用的就是这个。
            TransitionSet.用来驱动其他的 Transition .类似于 AnimationSet,能够让一组 Transition 有序，或者同时执行。
            AutoTransition. TransitionSet 同时包含了 Fade out ，ChangeBounds 和 Fade in 效果，
                只不过是有序的执行，首先 View 会在退场时，执行淡出，并伴随大小和位置的变化，然后在进场是执行淡入。
                如果不指定 beginDelayedTransition 的第二个参数，默认的转场效果就是 AutoTransition 。
            但是这个框架只能在 Andorid 4.4 以上使用
            * */
            @Override
            public void onClick(View v) {
//                TransitionManager.beginDelayedTransition(transitionContainer);
//                visible = !visible;
//                text.setVisibility(visible?View.VISIBLE:View.GONE);
                gotoScene(before?mScene1:mScene2);

                before = !before;
            }
        });

        //xml方式
//        TransitionInflater inflater = TransitionInflater.from(this);
//        inflater.inflateTransitionManager(R.transition.transition_manager,transitionContainer);

    }

    //代码方式
    private void gotoScene(Scene scene) {
        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(2000);
        Fade fadeOut = new Fade(Fade.OUT);
        fadeOut.setDuration(2000);
        Fade fadeIn = new Fade(Fade.IN);
        fadeIn.setDuration(2000);
        TransitionSet transition = new TransitionSet();
        transition.setOrdering(TransitionSet.ORDERING_SEQUENTIAL);
        transition
                .addTransition(fadeOut)
                .addTransition(changeBounds)
                .addTransition(fadeIn);
        TransitionManager.go(scene,transition);
    }
}
