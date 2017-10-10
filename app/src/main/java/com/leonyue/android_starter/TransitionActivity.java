package com.leonyue.android_starter;

import android.Manifest;
import android.os.AsyncTask;
import android.support.annotation.AnimRes;
import android.support.annotation.AnimatorRes;
import android.support.annotation.AnyRes;
import android.support.annotation.ArrayRes;
import android.support.annotation.AttrRes;
import android.support.annotation.BinderThread;
import android.support.annotation.BoolRes;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.Keep;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.annotation.Size;
import android.support.annotation.UiThread;
import android.support.annotation.VisibleForTesting;
import android.support.annotation.WorkerThread;
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
import android.widget.Toast;

import java.lang.reflect.Array;

public class TransitionActivity extends AppCompatActivity {

    private Scene mScene1,mScene2;


    //类型定义注解
    @IntDef({h,i,j}) //定义可接受的常量列表
    public @interface NavigationMode {} //定义NavigationMode注解
    public static final int h = 1;
    public static final int i = 2;
    public static final int j = 3;

    @NavigationMode
    public int getNavigationMode() {
        return 2;
    }
    public void setNavigationMode(@NavigationMode int mode){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);



        //Nullnuess注解
        hellowordl(null);

        //资源注解
        @AnimatorRes int a;
        @AnimRes int b;
        @AnyRes int c;
        @ArrayRes int d;
        @AttrRes int e;
        @BoolRes int f;
        @ColorRes int g;
        //...

        //线程注解
        @UiThread//标记运行在UI线程，典型例子AsyncTask的实现
        @MainThread
        @WorkerThread
        @BinderThread

        //值范围注解
        @Size(min=1)
        @Size(max=23)
        @Size(2)//2个元素
        @Size(multiple = 2)//2的倍数
        @IntRange(from=0,to=1000)
        @FloatRange(from=0.0,to=10.0)


        //权限注解
        @RequiresPermission(Manifest.permission.SET_WALLPAPER)
        @RequiresPermission(anyOf = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION})
        @RequiresPermission(allOf = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        })
        @RequiresPermission(Manifest.permission.BLUETOOTH)//对于Intent调用所需权限，可以在ACTION字符串定义出添加注解
        @RequiresPermission.Read(@RequiresPermission(Manifest.permission.BLUETOOTH))


        //重写函数注解
        @CallSuper

        //返回值注解(如果我们编写的函数需要调用者对返回值做某种处理)
        @CheckResult(suggest = "#enforcePermission(String,int,int,String)")

        //测试注解
        @VisibleForTesting

        //Keep注解
        @Keep

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

    private void hellowordl(@NonNull String s) {
        Toast.makeText(this,"Hello,"+s,Toast.LENGTH_SHORT).show();
    }
}
