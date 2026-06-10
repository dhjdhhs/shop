package com.campusshop.ui.detail;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.campusshop.R;
import com.campusshop.data.DataStore;
import com.campusshop.data.Goods;
import com.campusshop.utils.CommonUtils;

/**
 * 商品详情 Activity — 展示商品完整信息 + 加入购物车
 * 组员 1 负责
 */
public class GoodsDetailActivity extends AppCompatActivity {

    private ImageView ivDetail;
    private TextView tvName, tvPrice, tvDesc;
    private Button btnAddCart;

    private Goods goods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);

        // 初始化控件
        ivDetail = findViewById(R.id.iv_detail);
        tvName = findViewById(R.id.tv_detail_name);
        tvPrice = findViewById(R.id.tv_detail_price);
        tvDesc = findViewById(R.id.tv_detail_desc);
        btnAddCart = findViewById(R.id.btn_add_cart);

        // 接收传值，加载商品详情
        int goodsId = getIntent().getIntExtra("goods_id", -1);
        DataStore.initGoods();
        goods = DataStore.findGoodsById(goodsId);

        if (goods != null) {
            loadGoodsDetail();
        }

        // 加入购物车按钮
        btnAddCart.setOnClickListener(v -> {
            if (goods != null) {
                DataStore.addToCart(goods);
                CommonUtils.showToast(this, "已加入购物车！");
                // 播放提示音（多媒体加分项）
                // CommonUtils.playSound(this, R.raw.add_cart);
            }
        });
    }

    /**
     * 加载商品详情数据（图形图像处理：展示高清商品图片）
     */
    private void loadGoodsDetail() {
        ivDetail.setImageResource(goods.getImageResId());  // 图形图像：加载商品大图
        tvName.setText(goods.getName());
        tvPrice.setText("¥" + String.format("%.2f", goods.getPrice()));
        tvDesc.setText(goods.getDescription());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放图片资源
        if (ivDetail != null) {
            ivDetail.setImageDrawable(null);
        }
    }
}
