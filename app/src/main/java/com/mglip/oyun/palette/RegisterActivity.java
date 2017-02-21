package com.mglip.oyun.palette;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.apache.http.Header;

import java.lang.reflect.Type;


/**
 * @author shianqi@imudges.com
 *         Created by shianqi on 2017/2/20
 */
public class RegisterActivity extends AppCompatActivity {
    private TextView textView;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textView = (TextView)findViewById(R.id.goLogin);
        editText1 = (EditText)findViewById(R.id.editText1);
        editText2 = (EditText)findViewById(R.id.editText2);
        editText3 = (EditText)findViewById(R.id.editText3);
        registerButton = (Button)findViewById(R.id.registerButton);

        //已有账号，去登陆界面
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(intent);
                RegisterActivity.this.finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editText1.getText().toString();
                String password1 = editText2.getText().toString();
                String password2 = editText3.getText().toString();

                if(password1.equals(password2)){
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password1);
                    user.register(getApplicationContext(), new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int i, Header[] headers, byte[] bytes) {
                            Gson gson = new Gson();
                            Type type = new TypeToken<UserJsonBean>() {}.getType();
                            UserJsonBean jsonBean = gson.fromJson(new String(bytes), type);
                            if(jsonBean.state.equals("1")){
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                                RegisterActivity.this.finish();
                                Toast.makeText(getApplicationContext(),"注册成功" , Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(),"注册失败："+jsonBean.message , Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                            Toast.makeText(getApplicationContext(),"注册失败：请检查网络链接" , Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{

                }
            }
        });
    }

}
