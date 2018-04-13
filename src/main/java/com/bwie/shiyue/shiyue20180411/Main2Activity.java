package com.bwie.shiyue.shiyue20180411;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main2Activity extends AppCompatActivity {
    private EditText et_mobile;
    private EditText et_password;
    private Button btn_regist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_regist = (Button) findViewById(R.id.btn_regist);
        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile = et_mobile.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                //判断输入的内容是否为phone
                boolean b = isPhoneNumber(mobile);
                if (mobile.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Main2Activity.this, "用户名/密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (!b) {
                    Toast.makeText(Main2Activity.this, "手机号不合法", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    Toast.makeText(Main2Activity.this, "密码不能少于六位数", Toast.LENGTH_SHORT).show();
                } else {
                    register(mobile, password);
                }
            }
        });
    }
    private boolean isPhoneNumber(String phoneStr) {
        //定义电话格式的正则表达式
        String regex = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        //设定查看模式
        Pattern p = Pattern.compile(regex);
        //判断Str是否匹配，返回匹配结果
        Matcher m = p.matcher(phoneStr);
        return m.find();
    }

    private void register(String mobile,String password){
        RequestParams params = new RequestParams("http://120.27.23.105/user/reg");
        params.addQueryStringParameter("mobile",mobile);
        params.addQueryStringParameter("password",password);
        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //成功
                Gson gson = new Gson();
                RegistBean registBean = gson.fromJson(result, RegistBean.class);

                //如果注册成功就返回登录页面
                Toast.makeText(Main2Activity.this,registBean.getMsg(), Toast.LENGTH_SHORT).show();
                if (registBean.getCode().equals("0")){
                    finish();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
    }
}
