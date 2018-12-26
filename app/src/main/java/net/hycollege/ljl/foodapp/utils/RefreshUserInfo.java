package net.hycollege.ljl.foodapp.utils;

import android.content.Context;

import net.hycollege.ljl.foodapp.Model.UserInfoModel;
import net.hycollege.ljl.foodapp.Model.UserInfoModelImp;
import net.hycollege.ljl.foodapp.bean.User;
import net.hycollege.ljl.foodapp.bean.Yieid;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RefreshUserInfo implements InternetData.DataListener {
    boolean mBoolean=false;
    public RefreshUserInfo(Context context){
        if (userInfoModelImp == null) {
            userInfoModelImp = new UserInfoModel(context);
        }
    }
    public UserInfoModelImp userInfoModelImp = null;
    public List<User> isSuccess=null;
    /**
     * 获取当前登陆状态
     */
    List<User> search=null;
    public boolean getLoginState(){
        search=userInfoModelImp.QueryAllinfofromData();
        ////查询本地数据库如果没有记录,就插入数据
        if(search.size()>0){
           isSuccess= userInfoModelImp.getSearchfromState("success");
            if (isSuccess.size()==0){
               return false;
            }else{
                //进行数据更新
                JSONObject obj = new JSONObject();
                try {
                    obj.put("phonenum", isSuccess.get(0).getPhonenum());
                    obj.put("password", isSuccess.get(0).getPassword());
                    InternetData.getRequest(Yieid.userLogin, obj.toString(), this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return mBoolean;
            }
        }
        return false;
    }
    public void unlogin(){
        search=userInfoModelImp.QueryAllinfofromData();
        ////查询本地数据库如果没有记录,就插入数据
        if(search.size()>0) {
            isSuccess = userInfoModelImp.getSearchfromState("success");
            isSuccess.get(0).setUserinfobean("aaa");
            userInfoModelImp.ChangeNotetoData(isSuccess.get(0));
        }
    }

    @Override
    public void getdata(String data) {
        JSONObject jsonObject = null;
        mBoolean=true;
        try {
            jsonObject = new JSONObject(data);
            if (jsonObject.getString("loginstatus").equals("true")) {
                mBoolean=true;
            }else {
//                mBoolean=false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
