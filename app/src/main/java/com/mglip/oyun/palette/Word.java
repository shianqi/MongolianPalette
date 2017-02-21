package com.mglip.oyun.palette;

import android.content.Context;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @author shianqi@imudges.com
 *         Created by shianqi on 2016/12/1
 */
public class Word{
    private String word;
    private int wordIndex;
    private String str;
    private String phoneId;
    private String userId;

    public int getWordIndex() {
        return wordIndex;
    }

    public void setWordIndex(int wordIndex) {
        this.wordIndex = wordIndex;
    }

    public String getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void save(Context context, AsyncHttpResponseHandler asyncHttpResponseHandler){
        RequestParams params = new RequestParams();
        params.put("word", word);
        params.put("wordIndex", wordIndex);
        params.put("str", str);
        params.put("phoneId", phoneId);
        params.put("userId", userId);
        TwitterRestClient.post("save", params, asyncHttpResponseHandler);
    }
}
