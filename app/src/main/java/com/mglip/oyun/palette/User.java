package com.mglip.oyun.palette;

import android.content.Context;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @author shianqi@imudges.com
 *         Created by shianqi on 2017/2/20
 */
public class User {
    private String username;
    private String password;

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

    public void register(Context context, AsyncHttpResponseHandler asyncHttpResponseHandler){
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);
        TwitterRestClient.post("users/register", params, asyncHttpResponseHandler);
    }

    public void login(Context context, AsyncHttpResponseHandler asyncHttpResponseHandler){
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);
        TwitterRestClient.post("users/login", params, asyncHttpResponseHandler);
    }
}
