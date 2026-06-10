package com.campusshop.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.campusshop.R;
import com.campusshop.data.DataStore;
import com.campusshop.data.Goods;
import com.campusshop.ui.detail.GoodsDetailActivity;

import java.util.List;

/**
 * 首页 Fragment — 轮播图 + 热门商品列表
 * 组员 1 负责
 */
public class HomeFragment extends Fragment {

    private LinearLayout bannerContainer;
    private RecyclerView rvHotGoods;
    private GoodsAdapter adapter;

    // 轮播图用本地系统图标代替（模拟图片资源）
    private final int[] bannerIcons = {
            android.R.drawable.ic_menu_gallery,
            android.R.drawable.ic_menu_camera,
            android.R.drawable.ic_menu_slideshow
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // 初始化控件
        bannerContainer = view.findViewById(R.id.banner_container);
        rvHotGoods = view.findViewById(R.id.rv_hot_goods);

        // 初始化商品数据
        DataStore.initGoods();

        // 设置轮播图
        setupBanner();

        // 设置 RecyclerView
        rvHotGoods.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new GoodsAdapter(DataStore.goodsList);
        rvHotGoods.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 每次回到首页刷新列表
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 释放图片资源引用
        if (bannerContainer != null) {
            bannerContainer.removeAllViews();
        }
    }

    /**
     * 设置轮播图（图形图像处理）
     */
    private void setupBanner() {
        for (int iconRes : bannerIcons) {
            ImageView iv = new ImageView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    getResources().getDisplayMetrics().widthPixels - 32,  // 屏幕宽 - padding
                    ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(0, 0, 16, 0);
            iv.setLayoutParams(params);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setImageResource(iconRes);
            iv.setBackgroundColor(0xFFE0E0E0);
            bannerContainer.addView(iv);
        }
    }

    /**
     * 商品列表 Adapter — 带图片和价格的 Item
     */
    private class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> {

        private final List<Goods> goodsList;

        GoodsAdapter(List<Goods> goodsList) {
            this.goodsList = goodsList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_goods, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Goods goods = goodsList.get(position);
            holder.ivGoods.setImageResource(goods.getImageResId());  // 图形图像处理
            holder.tvName.setText(goods.getName());
            holder.tvPrice.setText("¥" + String.format("%.2f", goods.getPrice()));

            // 点击跳转商品详情
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra("goods_id", goods.getId());
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return goodsList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView ivGoods;
            TextView tvName, tvPrice;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                ivGoods = itemView.findViewById(R.id.iv_goods);
                tvName = itemView.findViewById(R.id.tv_goods_name);
                tvPrice = itemView.findViewById(R.id.tv_goods_price);
            }
        }
    }
}
