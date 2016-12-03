package com.mglip.oyun.palette;

import cn.bmob.v3.BmobObject;

/**
 * @author shianqi@imudges.com
 *         Created by shianqi on 2016/12/1
 */
public class Word extends BmobObject{
    private String word;
    private String str;

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
}
