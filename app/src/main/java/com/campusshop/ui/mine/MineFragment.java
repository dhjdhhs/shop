package com.campusshop.ui.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.campusshop.R;
import com.campusshop.data.DataStore;
import com.campusshop.ui.login.LoginActivity;
import com.campusshop.ui.order.OrderListActivity;
import com.campusshop.utils.CommonUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 个人中心 Fragment — 头像、用户名、功能菜单
 * 组员 2 负责
 */
public class MineFragment extends Fragment {

    private ImageView ivAvatar;
    private TextView tvUsername;
    private RecyclerView rvMenu;
    private Button btnLogout;

    // 菜单数据
    private final List<MenuItem> menuItems = Arrays.asList(
            new MenuItem("我的订单", android.R.drawable.ic_menu_manage),
            new MenuItem("收货地址", android.R.drawable.ic_menu_compass),
            new MenuItem("设置", android.R.drawable.ic_menu_preferences),
            new MenuItem("关于我们", android.R.drawable.ic_menu_info_details)
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);

        ivAvatar = view.findViewById(R.id.iv_avatar);
        tvUsername = view.findViewById(R.id.tv_username);
        rvMenu = view.findViewById(R.id.rv_mine_menu);
        btnLogout = view.findViewById(R.id.btn_logout);

        // 设置 RecyclerView 菜单
        rvMenu.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMenu.setAdapter(new MenuAdapter(menuItems));

        // 退出登录
        btnLogout.setOnClickListener(v -> {
            DataStore.logout();
            // 清除 SharedPreferences
            if (getActivity() != null) {
                SharedPreferences sp = getActivity()
                        .getSharedPreferences("user_prefs", getActivity().MODE_PRIVATE);
                sp.edit().clear().apply();
            }
            CommonUtils.showToast(getContext(), "已退出登录");
            CommonUtils.goToAndClear(getActivity(), LoginActivity.class);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 刷新用户信息
        refreshUserInfo();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (ivAvatar != null) {
            ivAvatar.setImageDrawable(null);
        }
    }

    /**
     * 刷新用户信息（图形图像处理：头像加载）
     */
    private void refreshUserInfo() {
        if (DataStore.isLoggedIn()) {
            tvUsername.setText(DataStore.currentUsername);
            ivAvatar.setImageResource(android.R.drawable.ic_menu_gallery);  // 头像加载
            btnLogout.setVisibility(View.VISIBLE);
        } else {
            tvUsername.setText("未登录");
            ivAvatar.setImageResource(android.R.drawable.ic_menu_gallery);
            btnLogout.setVisibility(View.GONE);
        }
    }

    /**
     * 菜单实体
     */
    private static class MenuItem {
        String text;
        int iconRes;

        MenuItem(String text, int iconRes) {
            this.text = text;
            this.iconRes = iconRes;
        }
    }

    /**
     * 菜单 Adapter — 带图标和文字的复杂 Item
     */
    private class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

        private final List<MenuItem> items;

        MenuAdapter(List<MenuItem> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_mine_menu, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            MenuItem item = items.get(position);
            holder.ivIcon.setImageResource(item.iconRes);  // 图形图像：图标美化
            holder.tvText.setText(item.text);

            holder.itemView.setOnClickListener(v -> {
                if (!DataStore.isLoggedIn()) {
                    CommonUtils.showToast(getContext(), "请先登录");
                    return;
                }
                // 点击"我的订单"跳转订单列表
                if (item.text.equals("我的订单")) {
                    startActivity(new Intent(getActivity(), OrderListActivity.class));
                } else {
                    CommonUtils.showToast(getContext(), item.text + "（功能待开发）");
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView ivIcon;
            TextView tvText;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                ivIcon = itemView.findViewById(R.id.iv_menu_icon);
                tvText = itemView.findViewById(R.id.tv_menu_text);
            }
        }
    }
}
