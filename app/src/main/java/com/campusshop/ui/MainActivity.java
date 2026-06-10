package com.campusshop.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.campusshop.R;
import com.campusshop.data.DataStore;
import com.campusshop.ui.cart.CartFragment;
import com.campusshop.ui.category.CategoryFragment;
import com.campusshop.ui.home.HomeFragment;
import com.campusshop.ui.mine.MineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * 主容器 Activity — 底部导航栏管理 Fragment 切换
 * 四个 Tab：首页、分类、购物车、我的
 */
public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_nav);

        // 默认显示首页
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }

        // 底部导航切换监听
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                fragment = new HomeFragment();
            } else if (id == R.id.nav_category) {
                fragment = new CategoryFragment();
            } else if (id == R.id.nav_cart) {
                fragment = new CartFragment();
            } else if (id == R.id.nav_mine) {
                fragment = new MineFragment();
            }

            if (fragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 每次回到主界面初始化商品数据
        DataStore.initGoods();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
