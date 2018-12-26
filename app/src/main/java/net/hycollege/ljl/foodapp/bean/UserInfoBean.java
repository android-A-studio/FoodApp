package net.hycollege.ljl.foodapp.bean;


public class UserInfoBean {

    private String id;
    private String loginstatus;
    private String username;
    private String password;
    private String phonenum;

    public UserInfoBean(String id, String loginstatus, String username, String password, String phonenum) {
        this.id = id;
        this.loginstatus = loginstatus;
        this.username = username;
        this.password = password;
        this.phonenum = phonenum;
    }
    public UserInfoBean(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginstatus() {
        return loginstatus;
    }

    public void setLoginstatus(String loginstatus) {
        this.loginstatus = loginstatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }
}
