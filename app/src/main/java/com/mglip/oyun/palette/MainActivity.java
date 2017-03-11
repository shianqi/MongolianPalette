package com.mglip.oyun.palette;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.TextView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

import java.io.*;


public class MainActivity extends AppCompatActivity {
    private MyView myView;
    private TextView textView;
    private int textSize = 0;
    private String nowString = "";
    private int nowIndex = 0;
    private String userId;
    private SharedPreferences userSettings;
    private TextView allSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userSettings = getSharedPreferences("setting", 0);
        userId = userSettings.getString("userId","");
        if(userId.equals("")){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            MainActivity.this.startActivity(intent);
            MainActivity.this.finish();
        }

        allSize = (TextView)findViewById(R.id.allSize);
        updateAllSize();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myView.clear();
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myView.save(nowString,nowIndex,userId);
                String allSizeString = userSettings.getString("allSize","0");
                int newNumber = (Integer.parseInt(allSizeString)+1);
                allSize.setText("已写："+newNumber+"个");

                SharedPreferences.Editor editor = userSettings.edit();
                editor.putString("allSize", newNumber+"");
                editor.apply();
                loadText();
            }
        });
        Typeface type =Typeface.createFromAsset(this.getAssets(),"font/LD-MONG-BT.ttf");

        myView= (MyView) findViewById(R.id.surfaceView);
        textView = (TextView) this.findViewById(R.id.textView);
        textView.setTypeface(type);
        init();
    }

    /**
     * 更新底部的数量
     */
    private void updateAllSize(){
        String allSizeString = userSettings.getString("allSize","0");
        allSize.setText("已写："+allSizeString+"个");

        String username = userSettings.getString("userId","");
        RequestParams params = new RequestParams();
        params.put("username", username);
        TwitterRestClient.post("getSizeNoPaid", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String text = "已写："+new String(bytes)+"个";

                SharedPreferences.Editor editor = userSettings.edit();
                editor.putString("allSize", new String(bytes));
                editor.apply();
                allSize.setText(text);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                //allSize.setText("未领赏词条：加载失败");
            }
        });
    }

    /**
     * 初始化文本长度
     */
    private void textSizeInit(){
        InputStreamReader inputStreamReader = null;
        InputStream inputStream = getResources().openRawResource(R.raw.text);
        try {
            inputStreamReader = new InputStreamReader(inputStream, "unicode");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        try {
            while ((reader.readLine()) != null) {
                textSize++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadText(){
        InputStreamReader inputStreamReader = null;
        InputStream inputStream = getResources().openRawResource(R.raw.text);
        try {
            inputStreamReader = new InputStreamReader(inputStream, "unicode");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line = "";
        try {
            int size = (int)(Math.random()*textSize);
            nowIndex = size;
            while ((line = reader.readLine()) != null) {
                size--;
                if(size==1){
                    sb.append(line);
                    sb.append("\n");
                    nowString = line;
                    textView.setText(sb);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init(){
        textSizeInit();
        loadText();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, MyInformationActivity.class);
            MainActivity.this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}