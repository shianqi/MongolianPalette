package com.mglip.oyun.palette;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

/**
 * @author shianqi@imudges.com
 *         Created by shianqi on 2017/2/21
 */
public class MyInformationActivity extends AppCompatActivity {
    private TextView itemPaid;
    private TextView itemNoPaid;
    private Button logoutButton;
    private SharedPreferences userSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinformation);

//        itemPaid = (TextView)findViewById(R.id.itemPaid);
//        itemNoPaid = (TextView)findViewById(R.id.itemNoPaid);
        logoutButton = (Button)findViewById(R.id.logoutButton);

//        userSettings = getSharedPreferences("setting", 0);
//        String username = userSettings.getString("userId","");
//        RequestParams params = new RequestParams();
//        params.put("username", username);
//        TwitterRestClient.post("getSizePaid", params, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int i, Header[] headers, byte[] bytes) {
//                String text = "已领赏词条： "+new String(bytes)+" 个";
//                itemPaid.setText(text);
//            }
//
//            @Override
//            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//                itemPaid.setText("已领赏词条：加载失败");
//            }
//        });
//        TwitterRestClient.post("getSizeNoPaid", params, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int i, Header[] headers, byte[] bytes) {
//                String text = "未领赏词条： "+new String(bytes)+" 个";
//                itemNoPaid.setText(text);
//            }
//
//            @Override
//            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//                itemNoPaid.setText("未领赏词条：加载失败");
//            }
//        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userSettings = getSharedPreferences("setting", 0);
                SharedPreferences.Editor editor = userSettings.edit();
                editor.putString("userId","");
                editor.apply();

                Intent intent = new Intent(MyInformationActivity.this, LoginActivity.class);
                MyInformationActivity.this.startActivity(intent);
                MyInformationActivity.this.finish();
            }
        });
    }
}
