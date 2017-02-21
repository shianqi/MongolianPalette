package com.mglip.oyun.palette;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
public class LoginActivity extends AppCompatActivity {
    private TextView textView;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private SharedPreferences userSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textView = (TextView)findViewById(R.id.goRegister);
        //没有账号，去注册界面
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
                LoginActivity.this.finish();
            }
        });

        usernameEditText = (EditText)findViewById(R.id.username);
        passwordEditText = (EditText)findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.login(getApplicationContext(), new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<UserJsonBean>() {}.getType();
                        UserJsonBean jsonBean = gson.fromJson(new String(bytes), type);
                        if(jsonBean.state.equals("1")){
                            userSettings = getSharedPreferences("setting", 0);
                            SharedPreferences.Editor editor = userSettings.edit();
                            editor.putString("userId",username);
                            editor.apply();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            LoginActivity.this.startActivity(intent);
                            LoginActivity.this.finish();
                            Toast.makeText(getApplicationContext(),"登陆成功" , Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"登陆失败："+jsonBean.message , Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        Toast.makeText(getApplicationContext(),"登陆失败：请检查网络链接" , Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
