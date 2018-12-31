package net.hycollege.ljl.foodapp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;

import net.hycollege.ljl.foodapp.R;
import net.hycollege.ljl.foodapp.bean.Yieid;
import net.hycollege.ljl.foodapp.pay.PayResult;
import net.hycollege.ljl.foodapp.utils.InternetData;
import net.hycollege.ljl.foodapp.utils.RefreshUserInfo;
import net.hycollege.ljl.foodapp.utils.SerializableMap;
import net.hycollege.ljl.foodapp.view.RegistTransition.AActivityOne;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * 来的订单页面
 */
public class OrderPages extends AppCompatActivity implements InternetData.DataListener {
    private RecyclerView recyclerView;
    View back, paybtn;
    private static final int SDK_PAY_FLAG = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderpages);
        back = findViewById(R.id.toolbar_Order);
        paybtn = findViewById(R.id.txt_order_submit);
        initRecyclerView();
        initEvent();
        getintentExtra();
        refreshUserInfo = new RefreshUserInfo(this);
    }

    private void getintentExtra() {//获取传递过来的信息，并通过getIntent读取显示在map上
        Intent mintent = getIntent();
        Bundle bundle = mintent.getExtras();
//        SerializableMap serializableMap = (SerializableMap) bundle.get("map");
//        Map<String, Object> map = serializableMap.getMap();
        //有问题
//        map.get("pic");
//        Log.e("tag",serializableMap.getMap()+"");
    }

    RefreshUserInfo refreshUserInfo = null;

    //返回
    private void initEvent() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //完成支付
        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否登陆状态
                if (refreshUserInfo.getLoginState()) {
                    payV2();
                } else {
                    //执行动画跳转到登陆页面
                    Explode explode = new Explode();
                    explode.setDuration(500);
                    getWindow().setExitTransition(explode);
                    getWindow().setEnterTransition(explode);
                    ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(OrderPages.this);
                    //准备加载登陆页面
                    Intent i2 = new Intent(v.getContext(), AActivityOne.class);
                    //跳转到登陆页面
                    startActivity(i2, oc2.toBundle());
                }
            }
        });
    }

    /**
     * 支付宝支付业务示例
     */
    public void payV2() {
        InternetData.getRequest(Yieid.userAplipay, "10", this);
    }

    /**
     * 对订单列表的初始化
     */
    private void initRecyclerView() {//对recyclerview进行实例化
        recyclerView = (RecyclerView) this.findViewById(R.id.recycler_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        //订单列表页面
        OrderRecyclerView orderRecyclerView = new OrderRecyclerView(this/*(ArrayList<NoteBean>) noteBeanList,this,this*/);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(orderRecyclerView);
    }

    private static void showAlert(Context ctx, String info) {
        showAlert(ctx, info, null);
    }

    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton("确定", null)
                .setOnDismissListener(onDismiss)
                .show();
    }

    String orderInfo = "";

    @Override
    public void getdata(String data) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(data);
            if (jsonObject.getString("paystatus").equals("true")) {

                orderInfo = jsonObject.getString("aplipay");
                Runnable payRunnable = new Runnable() {

                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(OrderPages.this);
                        Map<String, String> result = alipay.payV2(orderInfo, true);
                        Log.i("msp", result.toString());

                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };
                Thread payThread = new Thread(payRunnable);
                payThread.start();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        showAlert(OrderPages.this, "支付成功: " + payResult);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showAlert(OrderPages.this, "支付失败: " + payResult);
                    }
                    break;
                }
            }
        }

        ;
    };
}
