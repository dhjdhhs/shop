package com.campusshop.ui.register;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.campusshop.R;
import com.campusshop.data.DataStore;
import com.campusshop.ui.login.LoginActivity;
import com.campusshop.utils.CommonUtils;
import com.google.android.material.textfield.TextInputEditText;

/**
 * 注册 Activity — 表单校验 + SQLite 存储用户信息
 * 组员 2 负责
 */
public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etUsername, etPassword, etConfirm;
    private Button btnRegister;
    private TextView tvToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 初始化控件
        etUsername = findViewById(R.id.et_reg_username);
        etPassword = findViewById(R.id.et_reg_password);
        etConfirm = findViewById(R.id.et_reg_confirm);
        btnRegister = findViewById(R.id.btn_register);
        tvToLogin = findViewById(R.id.tv_to_login);

        // 注册按钮
        btnRegister.setOnClickListener(v -> {
            String username = etUsername.getText() != null ?
                    etUsername.getText().toString().trim() : "";
            String password = etPassword.getText() != null ?
                    etPassword.getText().toString().trim() : "";
            String confirm = etConfirm.getText() != null ?
                    etConfirm.getText().toString().trim() : "";

            // 表单校验：非空判断
            if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                CommonUtils.showToast(this, "请填写完整信息");
                return;
            }

            // 表单校验：密码一致性
            if (!password.equals(confirm)) {
                CommonUtils.showToast(this, "两次密码不一致");
                return;
            }

            if (password.length() < 3) {
                CommonUtils.showToast(this, "密码至少3位");
                return;
            }

            // 存入数据库
            if (DataStore.register(username, password)) {
                CommonUtils.showToast(this, "注册成功！");
                CommonUtils.goTo(this, LoginActivity.class);
                finish();
            } else {
                CommonUtils.showToast(this, "账号已存在");
            }
        });

        // 跳转登录
        tvToLogin.setOnClickListener(v -> {
            CommonUtils.goTo(this, LoginActivity.class);
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (etUsername != null) etUsername.setText(null);
        if (etPassword != null) etPassword.setText(null);
        if (etConfirm != null) etConfirm.setText(null);
    }
}
