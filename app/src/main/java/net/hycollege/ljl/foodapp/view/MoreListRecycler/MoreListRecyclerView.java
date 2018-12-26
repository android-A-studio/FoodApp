package net.hycollege.ljl.foodapp.view.MoreListRecycler;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import net.hycollege.ljl.foodapp.R;
import net.hycollege.ljl.foodapp.bean.FoodBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现加载菜单列表的类
 */
public class MoreListRecyclerView extends RecyclerView.Adapter<MoreListRecyclerView.Viewholder> {
    private final Context context;
    private ArrayList<String>notelist;
    List<FoodBean> foodBean;
    private boolean mFirstOnlyEnable=false;

    public MoreListRecyclerView(Context mcontext,List<FoodBean> foodBeans){
        this.context=mcontext;
        notelist=new ArrayList<>();
        foodBean=foodBeans;
        notelist.add("aa");
        notelist.add("bb");
    }

    /**
     * 负责承载每个子项的布局
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View iten_recycler= LayoutInflater.from(parent.getContext()).inflate(R.layout.morelist,parent,false);
        Viewholder viewHolder=new Viewholder(iten_recycler);
        return viewHolder;
    }

    /**
     * 负责将每个子项holder绑定数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder,int position) {
//        setMenuListener(holder.recycler_image_menu,notelist.get(position));
        Glide.with(context).load(foodBean.get(position).getPicture_f()).into(holder.iv);
        //设置名字
        holder.food_name.setText(foodBean.get(position).getName_f());
        //设置价格
        holder.food_price.setText("$ "+foodBean.get(position).getPrice_f());
        addAnimations(holder);
        switch (position){
            case 0:
                break;
            case 1:
//                holder.recycler_image_menu.setImageResource(R.mipmap.beautiful2);
                break;
        }
    }
    //菜单列表监听
    private void setMenuListener(ImageView recycler_image_menu,final String s) {
        recycler_image_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"666"+s,Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * 获取Item的个数
     * @return
     */
    @Override
    public int getItemCount() {
        return foodBean==null?0:foodBean.size();
    }
    public class Viewholder extends ViewHolder{
        ImageView recycler_image_notetype,recycler_image_menu,iv;
        TextView recycler_text_note,food_name,food_price;
        public Viewholder(View itemView){
            super(itemView);
            iv=(ImageView)itemView.findViewById(R.id.iv);
            food_name=(TextView) itemView.findViewById(R.id.food_name);
            food_price=(TextView)itemView.findViewById(R.id.food_price);
            //按图片的原来size居中显示，当图片长/宽超过View的长/宽，则截取图片的居中部分显示
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }
    private boolean mOpenAnimationEnable = true;
    private int mLastPosition = -1;
    private BaseAnimation mSelectAnimation = new AlphaInAnimation();
    private int mDuration = 300;
    private Interpolator mInterpolator = new LinearInterpolator();
    /**
     * To open the animation when loading
     */
    public void openLoadAnimation() {
        this.mOpenAnimationEnable = true;
    }
    /**
     * 添加动画
     * @param holder
     */
    private void addAnimations(RecyclerView.ViewHolder holder) {
        if (mOpenAnimationEnable) {
            if (!mFirstOnlyEnable || holder.getLayoutPosition() > mLastPosition) {
                BaseAnimation animation = null;
                if (mSelectAnimation != null) {
                    animation = mSelectAnimation;
                } else {
                    animation = mSelectAnimation;
                }
                for (Animator anim : animation.getAnimators(holder.itemView)) {
                    startAnim(anim);
                }
                mLastPosition = holder.getLayoutPosition();
            }
        }
    }
    public void isFirstOnly(boolean firstOnly) {
        this.mFirstOnlyEnable = firstOnly;
    }

    /**
     * 开启动画
     * @param animator
     */
    private void startAnim(Animator animator) {
        animator.setDuration(mDuration).start();
        animator.setInterpolator(mInterpolator);
    }

    /**
     * 设置动画效果
     * @param animation
     */
    public void setAnimation(BaseAnimation animation){
        this.mOpenAnimationEnable = true;
        this.mSelectAnimation = animation;
    }

}
