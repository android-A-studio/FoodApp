package net.hycollege.ljl.foodapp.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gc.materialdesign.views.ButtonFloat;
import com.gc.materialdesign.views.LayoutRipple;
import com.nineoldandroids.view.ViewHelper;
import com.sun.easysnackbar.EasySnackBar;

import net.hycollege.ljl.foodapp.MainActivity;
import net.hycollege.ljl.foodapp.bean.ArrayListFoodBean;
import net.hycollege.ljl.foodapp.bean.FoodBean;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import net.hycollege.ljl.foodapp.R;
import net.hycollege.ljl.foodapp.bean.Means;
import net.hycollege.ljl.foodapp.utils.Powers;
import net.hycollege.ljl.foodapp.utils.SerializableMap;
import net.hycollege.ljl.foodapp.utils.SnackbarUtils;

/**
 * 这是一个菜单选项卡，菜单页面实现类
 * 参考https://www.jb51.net/article/117766.htm
 */
public class ViewPagerCardAdapter extends PagerAdapter implements View.OnClickListener {
    private LayoutInflater layoutInflater;
    ImageView imageview_item_viewpagercard, lableview_item_viewpagercard, menu_item_viewpagercard;
    TextView textview_item_viewpagercard, createtime_item_viewpagercard, food_price, food_money;
    CardView viewpager_card;
    Context mContext;
    String noteinfo, image;
    View view;
    int backgroundColor = Color.parseColor("#1afa29");
    ButtonFloat buttonFloat;
    private List<FoodBean> list = null;
    public static List<Map<String, Object>> listitem;

    public ViewPagerCardAdapter(Context context, List<FoodBean> mlist) {
        if (list != null) {
            list.clear();
        }
        listitem = new ArrayList<Map<String, Object>>();
        list = mlist;
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", list.get(i).getName_f());
            map.put("money", list.get(i).getPrice_f());
            map.put("pic", list.get(i).getPicture_f());
            listitem.add(map);
        }
        //把菜谱信息存储起来
        ArrayListFoodBean.setFoodBeans(mlist);
        mContext = context;
        layoutInflater = LayoutInflater.from(mContext);
    }

    //菜单界面视图加载数据
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        noteinfo = list.get(position).getDetails_f().toString();
        image = list.get(position).getPicture_f();
        view = layoutInflater.inflate(R.layout.item_viewpagercard, container, false);
        //悬浮下单点菜按钮
        buttonFloat = view.findViewById(R.id.buttonFloat);
        food_price = view.findViewById(R.id.food_price);
        food_money = view.findViewById(R.id.food_money);

        food_money.setText("" + list.get(position).getPrice_f());
        food_price.setText(list.get(position).getName_f());
        imageview_item_viewpagercard = (ImageView) view.findViewById(R.id.imageview_item_viewpagercard);
        lableview_item_viewpagercard = (ImageView) view.findViewById(R.id.lableview_item_viewpagercard);
        menu_item_viewpagercard = (ImageView) view.findViewById(R.id.menu_item_viewpagercard);
        textview_item_viewpagercard = (TextView) view.findViewById(R.id.textview_item_viewpagercard);
        createtime_item_viewpagercard = (TextView) view.findViewById(R.id.createtime_item_viewpagercard);
        createtime_item_viewpagercard.setText(list.get(position).getUtime_f());
        viewpager_card = (CardView) view.findViewById(R.id.viewPager_card);
        textview_item_viewpagercard.setText(Means.getNotetextOnViewPagerCard(noteinfo));
        initview(list.get(position).getPicture_f());
        initeven(1);
        container.addView(view);
        return view;
    }

    private void initeven(int a) {
        //悬浮下单点菜按钮可点击
        buttonFloat.setOnClickListener(this);
    }

    Powers mPowers = Powers.off;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonFloat:
                //下单提示SnackBar,
                View contentView = EasySnackBar.convertToContentView(view, R.layout.layout_custom);
                final EasySnackBar easySnackBar = EasySnackBar.make(view, contentView, EasySnackBar.LENGTH_INDEFINITE, false).setCallback(new EasySnackBar.Callback() {
                    //显示与隐藏监听
                    @Override
                    public void onShown(EasySnackBar sb) {
                        super.onShown(sb);
                        //  Toast.makeText(mContext,"onShown!",Toast.LENGTH_SHORT).show();
                    }

                    //显示与隐藏监听
                    @Override
                    public void onDismissed(EasySnackBar transientBottomBar, int event) {
                        super.onDismissed(transientBottomBar, event);
                        // Toast.makeText(mContext,"onDismissed!",Toast.LENGTH_SHORT).show();
                    }
                });
                LayoutRipple layoutRipple = (LayoutRipple) contentView.findViewById(R.id.itemButtons);
                setOriginRiple(layoutRipple);
                layoutRipple.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {

                    }
                });
                //  Toast.makeText(mContext,"测试",Toast.LENGTH_SHORT).show();
                Button buttons = contentView.findViewById(R.id.buttonPanel);
                buttons.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, OrderPages.class);
                        final SerializableMap myMap = new SerializableMap();
                        myMap.setMap(listitem.get(0));//将map数据添加到封装的myMap中
                        //有问题
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("map", myMap);
                        intent.putExtra("BACKGROUND", backgroundColor);
                        mContext.startActivity(intent);
                        //Toast.makeText(mContext, "测试", Toast.LENGTH_SHORT).show();
                        mPowers = Powers.off;
                        easySnackBar.dismiss();
                    }
                });
                //第一次点击才显示
                if (mPowers == Powers.off) {
                    easySnackBar.show();
                    mPowers = Powers.on;
                }
                break;
        }
    }

    /**
     * 设置按钮背景色点击延申
     *
     * @param layoutRipple
     */
    private void setOriginRiple(final LayoutRipple layoutRipple) {

        layoutRipple.post(new Runnable() {

            @Override
            public void run() {
                View v = layoutRipple.getChildAt(0);
                layoutRipple.setxRippleOrigin(ViewHelper.getX(v) + v.getWidth() / 2);
                layoutRipple.setyRippleOrigin(ViewHelper.getY(v) + v.getHeight() / 2);

                layoutRipple.setRippleColor(R.color.colorFloatingButton4);

                layoutRipple.setRippleSpeed(30);
            }
        });

    }

    private void initview(String imagpath) {
        //点击后打开菜单详细页面
        startNoteinfoActivity(imageview_item_viewpagercard, imagpath/*,list.get(position)*/);
        if (image.equals("<图片>")) {
            imageview_item_viewpagercard.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageview_item_viewpagercard.setImageResource(R.mipmap.recipe);
        } else {
            //按图片的原来size居中显示，当图片长/宽超过View的长/宽，则截取图片的居中部分显示
            imageview_item_viewpagercard.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(mContext).load(image).into(imageview_item_viewpagercard);
        }
    }

    @Override
    public int getCount() {
        Log.w("size", list.size() + "");
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    //打开菜单详细页面
    private void startNoteinfoActivity(View view, final String imagpath/*,final NoteBean noteBean*/) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initphotoshowdialog(imagpath);
            }
        });

    }

    private void initphotoshowdialog(String imagpath) {//实例化一个显示图片的dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View centerview = layoutInflater.inflate(R.layout.activity_add_photodiaolg, null);
        final ImageView photoimageview;
        final AlertDialog alertDialog = builder.setView(centerview).create();
        photoimageview = (ImageView) centerview.findViewById(R.id.add_dialog_imageview);
        //按图片的原来size居中显示，当图片长/宽超过View的长/宽，则截取图片的居中部分显示
//        photoimageview.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        Glide.with(centerview.getContext()).load(imagpath).into(photoimageview);
        photoimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
