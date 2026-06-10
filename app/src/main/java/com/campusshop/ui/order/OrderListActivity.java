package com.campusshop.ui.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.campusshop.R;
import com.campusshop.data.DataStore;
import com.campusshop.data.Order;

import java.util.List;

/**
 * 订单列表 Activity — 展示所有历史订单
 * 组员 3 负责
 */
public class OrderListActivity extends AppCompatActivity {

    private RecyclerView rvOrderList;
    private TextView tvBack;
    private OrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        rvOrderList = findViewById(R.id.rv_order_list);
        tvBack = findViewById(R.id.tv_order_back);

        rvOrderList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OrderAdapter(DataStore.orderList);
        rvOrderList.setAdapter(adapter);

        // 返回
        tvBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 订单列表 Adapter — 展示商品缩略图、时间、金额、状态
     */
    private class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

        private final List<Order> orders;

        OrderAdapter(List<Order> orders) {
            this.orders = orders;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_order, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Order order = orders.get(position);
            holder.ivGoods.setImageResource(order.getImageResId());  // 图形图像：缩略图
            holder.tvGoods.setText(order.getGoodsNames());
            holder.tvTime.setText(order.getTime());
            holder.tvTotal.setText("¥" + String.format("%.2f", order.getTotalPrice()));
            holder.tvStatus.setText(order.getStatus());  // 图形图像：状态图标展示

            // 点击查看简要详情
            holder.itemView.setOnClickListener(v -> {
                new android.app.AlertDialog.Builder(OrderListActivity.this)
                        .setTitle("订单详情")
                        .setMessage("商品：" + order.getGoodsNames() + "\n\n"
                                + "金额：¥" + String.format("%.2f", order.getTotalPrice()) + "\n"
                                + "时间：" + order.getTime() + "\n"
                                + "状态：" + order.getStatus())
                        .setPositiveButton("确定", null)
                        .show();
            });
        }

        @Override
        public int getItemCount() {
            return orders.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView ivGoods;
            TextView tvGoods, tvTime, tvTotal, tvStatus;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                ivGoods = itemView.findViewById(R.id.iv_order_goods);
                tvGoods = itemView.findViewById(R.id.tv_order_goods);
                tvTime = itemView.findViewById(R.id.tv_order_time);
                tvTotal = itemView.findViewById(R.id.tv_order_total);
                tvStatus = itemView.findViewById(R.id.tv_order_status);
            }
        }
    }
}
