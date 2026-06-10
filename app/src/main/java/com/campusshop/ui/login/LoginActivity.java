package com.campusshop.ui.login;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.campusshop.R;
import com.campusshop.data.DataStore;
import com.campusshop.ui.MainActivity;
import com.campusshop.ui.register.RegisterActivity;
import com.campusshop.utils.CommonUtils;
import com.google.android.material.textfield.TextInputEditText;

/**
 * 登录 Activity — 账号密码校验 + SharedPreferences 存储登录状态
 * 组员 2 负责
 */
public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvToRegister;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 初始化控件
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        tvToRegister = findViewById(R.id.tv_to_register);

        // SharedPreferences 存储登录状态
        sp = getSharedPreferences("user_prefs", MODE_PRIVATE);

        // 登录按钮
        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText() != null ?
                    etUsername.getText().toString().trim() : "";
            String password = etPassword.getText() != null ?
                    etPassword.getText().toString().trim() : "";

            if (username.isEmpty() || password.isEmpty()) {
                CommonUtils.showToast(this, "请输入账号和密码");
                return;
            }

            if (DataStore.login(username, password)) {
                // 保存登录状态到 SharedPreferences
                sp.edit().putBoolean("is_logged_in", true)
                        .putString("username", username)
                        .apply();

                CommonUtils.showToast(this, "登录成功！");
                CommonUtils.goToAndClear(this, MainActivity.class);
            } else {
                CommonUtils.showToast(this, "账号或密码错误");
            }
        });

        // 跳转注册页
        tvToRegister.setOnClickListener(v ->
                CommonUtils.goTo(this, RegisterActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 检查是否已登录
        if (sp.getBoolean("is_logged_in", false)) {
            String username = sp.getString("username", "");
            DataStore.currentUsername = username;
            CommonUtils.goToAndClear(this, MainActivity.class);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 清空输入框
        if (etUsername != null) etUsername.setText(null);
        if (etPassword != null) etPassword.setText(null);
    }
}
