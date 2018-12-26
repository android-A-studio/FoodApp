package net.hycollege.ljl.foodapp.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;

import net.hycollege.ljl.foodapp.R;
import net.hycollege.ljl.foodapp.utils.RefreshUserInfo;
import net.hycollege.ljl.foodapp.view.RegistTransition.AActivityOne;
import net.hycollege.ljl.foodapp.view.Settings.SettingsActivity;

import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

public class getFragmentMe extends BaseFragment {
    RefreshUserInfo refreshUserInfo = null;
    View view = null;
    View viewByIdNoSuccess = null;
    View viewByIdSuccess = null;
    TextView username = null;
    CardView exitlogin = null;
    ImageView setting;
    ProgressBarCircularIndeterminate mProgressBarCircularIndeterminates = null;

    @Override
    protected void firstRefresh() {

    }

    /**
     * 回调函数,用来解决刷新问题
     */
    @Override
    public void initResume() {
        //使用异步(AsyncTask)解决数据刷新问题
        new MyAsyncTask().execute();
    }

    //异步类,用来实现数据刷新
    class MyAsyncTask extends AsyncTask<Boolean, Boolean, Boolean> {
        //onPreExecute用于异步处理前的操作
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //此处将progressBar设置为可见.
            if (refreshUserInfo.getLoginState()) {
                mProgressBarCircularIndeterminates.setVisibility(View.VISIBLE);
            } else {
                viewByIdSuccess.setVisibility(View.GONE);
                viewByIdNoSuccess.setVisibility(View.VISIBLE);
                mProgressBarCircularIndeterminates.setVisibility(View.INVISIBLE);
            }
        }

        //在doInBackground方法中进行异步任务的处理.
        @Override
        protected Boolean doInBackground(Boolean... voids) {
            SystemClock.sleep(1000);
            if (refreshUserInfo.getLoginState()) {
                a = true;
            }
            return a;
        }
        //onPostExecute用于UI的更新.此方法的参数为doInBackground方法返回的值.
        @Override
        protected void onPostExecute(Boolean v) {
            super.onPostExecute(v);
            if (v) {
                viewByIdNoSuccess.setVisibility(View.GONE);
                viewByIdSuccess.setVisibility(View.VISIBLE);
                //延迟执行
                mProgressBarCircularIndeterminates.setVisibility(View.INVISIBLE);
                username.setText(refreshUserInfo.isSuccess.get(0).getUsername().toString() + "的主页");
                String str = refreshUserInfo.isSuccess.get(0).getUsername();
                try {
                    str = new String(str.getBytes("utf8"), "utf8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.e("userinfo", "" + str);
                a = false;
            } else {
                viewByIdSuccess.setVisibility(View.GONE);
                viewByIdNoSuccess.setVisibility(View.VISIBLE);
            }
        }
    }
    /**
     * 设置按钮动画
     * @param paramView
     */
    private void startIconAnim(View paramView) {
        float f1 = paramView.getWidth() / 2;
        float f2 = paramView.getHeight() / 2;
        ScaleAnimation localScaleAnimation = new ScaleAnimation(0.1F, 1.0F, 0.1F, 1.0F, f1, f2);
        localScaleAnimation.setDuration(1000L);
        paramView.startAnimation(localScaleAnimation);

//        localScaleAnimation = new ScaleAnimation(1.0F, 0.5F, 1.0F, 0.5F, f1, f2);
//        localScaleAnimation.setInterpolator(new BounceInterpolator());
//        localScaleAnimation.setDuration(1000L);
//        paramView.startAnimation(localScaleAnimation);
    }
    /**
     * 创建个加载视图线程
     */
    boolean a = false;

    @Override
    public void init() {
        if (refreshUserInfo == null) {
            refreshUserInfo = new RefreshUserInfo(getActivity());
        }
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragmentset, container, false);
        }
        setting=view.findViewById(R.id.setting);
        mProgressBarCircularIndeterminates = view.findViewById(R.id.progressBarCircularIndetermininates);
        exitlogin = view.findViewById(R.id.exitlogins);
        exitlogin.setCardBackgroundColor(Color.rgb(255, 130, 1));
        viewByIdNoSuccess = view.findViewById(R.id.loginandregist);
        viewByIdSuccess = view.findViewById(R.id.success);
        username = view.findViewById(R.id.username);

        TextView textView = view.findViewById(R.id.login);
        //登陆按钮监听
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Explode explode = new Explode();
                explode.setDuration(500);
                getActivity().getWindow().setExitTransition(explode);
                getActivity().getWindow().setEnterTransition(explode);
                ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity());
                Intent i2 = new Intent(v.getContext(), AActivityOne.class);
                startActivity(i2, oc2.toBundle());
            }
        });

        return view;
    }

    @Override
    public void initData() {
        new MyAsyncTask().execute();

    }

    @Override
    public void initEvent() {
        exitlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("exit", "exit");
                if (refreshUserInfo.getLoginState() == true) {
                    refreshUserInfo.unlogin();
                    viewByIdSuccess.setVisibility(View.GONE);
                    viewByIdNoSuccess.setVisibility(View.VISIBLE);
                }
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });
        startIconAnim(setting);
    }

    @Override
    public void destroyView() {

    }

    @Override
    public void destroy() {
        a = false;
    }
}
