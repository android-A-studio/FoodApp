package net.hycollege.ljl.foodapp.fragment;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.hycollege.ljl.foodapp.MainActivity;
import net.hycollege.ljl.foodapp.R;
import net.hycollege.ljl.foodapp.bean.FoodBean;
import net.hycollege.ljl.foodapp.bean.Yieid;
import net.hycollege.ljl.foodapp.utils.InternetData;
import net.hycollege.ljl.foodapp.utils.ScaleTransformer0;
import net.hycollege.ljl.foodapp.view.ViewPagerCardAdapter;
import net.hycollege.ljl.foodapp.view.ViewPagerListAdapter;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class getFragmentMenu extends BaseFragment implements View.OnClickListener, InternetData.DataListener {
    private View view = null;
    ViewPager viewPager, viewPagerlist;
    public List<FoodBean> foodBean = null;
    ViewPagerCardAdapter adapter = null;
    ViewPagerListAdapter listAdapter = null;
    ProgressBarCircularIndeterminate mProgressBarCircularIndeterminate = null;
    //精简和更多列表
    TextView moreTextView, moreTextList;

    @Override
    protected void firstRefresh() {
    }

    Timer t;

    /**
     * 回调函数,用来解决刷新问题
     */
    @Override
    public void initResume() {
        //延迟执行
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBarCircularIndeterminate.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }, 2000);
    }

    @Override
    public void init() {
        t = new Timer();
        if (foodBean == null) {
            foodBean = new ArrayList<FoodBean>();
        }
        InternetData.getRequest(Yieid.foodMenu, null, this);
    }


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        view = View.inflate(context, R.layout.fragmentmenu, null);
        moreTextView = view.findViewById(R.id.title_toolbar_more);
        moreTextList = view.findViewById(R.id.title_toolbar_list);
        viewPager = view.findViewById(R.id.viewpager_main);
        viewPagerlist = view.findViewById(R.id.viewpager_list);
        mProgressBarCircularIndeterminate = view.findViewById(R.id.progressBarCircularIndetermininate);
        //请求网络数据
        listAdapter = new ViewPagerListAdapter(getActivity());
        //设置viewPager之间的间距
        viewPager.setPageMargin((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        //给viewPager添加悬浮效果
        viewPager.setPageTransformer(false, new ScaleTransformer0(getActivity()));
        //实例化更多列表
        adapter = new ViewPagerCardAdapter(getActivity(), foodBean);
        return view;
    }

    @Override
    public void initData() {
        //随机页面
        if (foodBean.size() > 0) {
            Random random = new Random();
            int result = random.nextInt(foodBean.size());
            viewPager.setCurrentItem(result);
        }
    }

    @Override
    public void initEvent() {
        gotoRunRetFragment();
        mProgressBarCircularIndeterminate.setVisibility(View.VISIBLE);
        //标题更多 按钮监听
      /*  moreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏简约列表显示list列表
                viewPagerlist.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();
                viewPager.setVisibility(View.GONE);
                viewPagerlist.setVisibility(View.VISIBLE);
                moreTextView.setVisibility(View.GONE);
                moreTextList.setVisibility(View.VISIBLE);
            }
        });*/
        //标题精简 按钮监听
        moreTextList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏list列表显示简约列表
                viewPagerlist.setVisibility(View.GONE);
                viewPager.setVisibility(View.VISIBLE);
                moreTextList.setVisibility(View.GONE);
                moreTextView.setVisibility(View.VISIBLE);
            }
        });
        if (foodBean.size() == 0) {

            InternetData.getRequest(Yieid.foodMenu, null, this);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    //异步类,用来实现数据刷新
                    new MyAsyncTask().execute();
                }
            }, 500);
        } else {
            viewPager.setAdapter(adapter);
        }
    }
    Runnable runnable;
    //异步类,用来实现数据刷新
    class MyAsyncTask extends AsyncTask<Boolean, Boolean, Boolean> {
        //onPreExecute用于异步处理前的操作
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //此处将progressBar设置为可见.
            mProgressBarCircularIndeterminate.setVisibility(View.VISIBLE);
        }

        //在doInBackground方法中进行异步任务的处理.
        @Override
        protected Boolean doInBackground(Boolean... voids) {
            SystemClock.sleep(1000);
            if (foodBean.size() > 0) {
                a = true;

            }
            return a;
        }

        //onPostExecute用于UI的更新.此方法的参数为doInBackground方法返回的值.
        @Override
        protected void onPostExecute(Boolean v) {
            super.onPostExecute(v);
            if (v) {
                //如果是在fragment中刷新viewpage,一定要放到runOnUiThread中
                try {
                    adapter = new ViewPagerCardAdapter(getActivity(), foodBean);
                } catch (Exception e) {
                    adapter=null;
                    e.printStackTrace();
                }
                viewPager.setAdapter(adapter);
                mProgressBarCircularIndeterminate.setVisibility(View.INVISIBLE);
            }
            a = false;
        }
    }

    /**
     * 创建个加载视图线程
     */
    boolean a = false;

    @Override
    public void destroyView() {

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 切换到RunRetFragment
     */
    public void gotoRunRetFragment() {
        MainActivity.startVideo = false;
        showFragment(FragmentFactory.MenuFragment);
    }

    /**
     * 获取网络响应数据
     *
     * @param data
     */
    @Override
    public void getdata(String data) {
        Gson gson = new Gson();
        //解释json数据,并封装于User对象中
        foodBean = gson.fromJson(data, new TypeToken<List<FoodBean>>() {
        }.getType());
        Log.e("test",foodBean.get(0)+"");
        Log.e("test",foodBean.get(1)+"");
    }

    /**
     * 离开后执行的方法
     */
    @Override
    public void destroy() {
        adapter = null;

        runnable=null;
        t.cancel();
        a = false;
    }
}
