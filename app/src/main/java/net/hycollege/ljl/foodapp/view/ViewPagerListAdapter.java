package net.hycollege.ljl.foodapp.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import net.hycollege.ljl.foodapp.R;
import net.hycollege.ljl.foodapp.bean.ArrayListFoodBean;
import net.hycollege.ljl.foodapp.view.MoreListRecycler.CustomAnimation;
import net.hycollege.ljl.foodapp.view.MoreListRecycler.MoreListRecyclerView;

/**
 * 更多列表的ViewPager
 */
public class ViewPagerListAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    View view;
    Context mContext;
    public ViewPagerListAdapter(Context context){
        mContext=context;
        layoutInflater=LayoutInflater.from(mContext);
    }
    @Override
    public int getCount() {
        return 1;
    }

    /**
     * 确定一个页面视图是否关联到一个特定的对象。
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    /**
     * 在viewpager移除一个item时调用
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    /**
     * 是初始化item用的
     * @param container
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
       view=layoutInflater.inflate(R.layout.item_viewpagerlist,container,false);
        initRecyclerView();
        container.addView(view);
        return view;
    }
    private RecyclerView recyclerView;
    /**
     * 对订单列表的初始化
     */
    private void initRecyclerView(){//对recyclerview进行实例化
        recyclerView=view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        MoreListRecyclerView moreListRecyclerView=new MoreListRecyclerView(mContext, ArrayListFoodBean.getFoodBean());
        //这里可以添加动画
        moreListRecyclerView.openLoadAnimation();
        //添加动画CustomAnimation,还写了另外一个动画AlphaInAnimation
        moreListRecyclerView.setAnimation(new CustomAnimation());
        //使每次点击都有动画效果
        moreListRecyclerView.isFirstOnly(false);
        //设置布局
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(moreListRecyclerView);
    }
}
