package com.campusshop.ui.checkout;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.campusshop.R;
import com.campusshop.data.CartItem;
import com.campusshop.data.DataStore;
import com.campusshop.data.Order;
import com.campusshop.utils.CommonUtils;

import java.util.List;

/**
 * 结算 Activity — 展示订单清单 + 提交订单 + 音效播放
 * 组员 3 负责
 */
public class CheckoutActivity extends AppCompatActivity {

    private RecyclerView rvCheckoutItems;
    private TextView tvCheckoutTotal;
    private Button btnSubmit;
    private TextView tvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        rvCheckoutItems = findViewById(R.id.rv_checkout_items);
        tvCheckoutTotal = findViewById(R.id.tv_checkout_total);
        btnSubmit = findViewById(R.id.btn_submit_order);
        tvBack = findViewById(R.id.tv_back);

        // 获取选中的购物车商品
        List<CartItem> selectedItems = DataStore.getSelectedItems();

        rvCheckoutItems.setLayoutManager(new LinearLayoutManager(this));
        rvCheckoutItems.setAdapter(new CheckoutAdapter(selectedItems));

        // 显示总金额
        double total = DataStore.getSelectedTotal();
        tvCheckoutTotal.setText("总计：¥" + String.format("%.2f", total));

        // 提交订单
        btnSubmit.setOnClickListener(v -> {
            if (selectedItems.isEmpty()) {
                CommonUtils.showToast(this, "没有要结算的商品");
                return;
            }

            // 生成订单
            Order order = DataStore.addOrder(selectedItems);
            // 清空已选购物车商品
            DataStore.clearSelectedCartItems();

            // 播放提示音效（多媒体处理 — 满足音视频要求）
            playOrderSuccessSound();

            CommonUtils.showToast(this, "订单提交成功！");
            finish();
        });

        // 返回
        tvBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 播放下单成功音效（多媒体应用加分项）
     */
    private void playOrderSuccessSound() {
        try {
            // 使用系统通知音效
            MediaPlayer mp = MediaPlayer.create(this,
                    android.provider.Settings.System.DEFAULT_NOTIFICATION_URI);
            if (mp != null) {
                mp.start();
                mp.setOnCompletionListener(MediaPlayer::release);
            }
        } catch (Exception e) {
            // 音效播放失败不影响主流程
        }
    }

    /**
     * 结算清单 Adapter
     */
    private class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.ViewHolder> {

        private final List<CartItem> items;

        CheckoutAdapter(List<CartItem> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_checkout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            CartItem item = items.get(position);
            holder.ivGoods.setImageResource(item.getImageResId());  // 图形图像：缩略图
            holder.tvName.setText(item.getGoodsName());
            holder.tvPriceQty.setText("¥" + String.format("%.2f", item.getPrice())
                    + " × " + item.getQuantity());
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView ivGoods;
            TextView tvName, tvPriceQty;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                ivGoods = itemView.findViewById(R.id.iv_checkout_goods);
                tvName = itemView.findViewById(R.id.tv_checkout_name);
                tvPriceQty = itemView.findViewById(R.id.tv_checkout_price_qty);
            }
        }
    }
}
