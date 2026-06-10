package com.campusshop.ui.cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.campusshop.R;
import com.campusshop.data.CartItem;
import com.campusshop.data.DataStore;
import com.campusshop.ui.checkout.CheckoutActivity;
import com.campusshop.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车 Fragment — 商品列表、全选、数量修改、结算
 * 组员 3 负责
 */
public class CartFragment extends Fragment {

    private RecyclerView rvCart;
    private CheckBox cbSelectAll;
    private TextView tvTotalPrice;
    private Button btnCheckout;
    private CartAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        rvCart = view.findViewById(R.id.rv_cart);
        cbSelectAll = view.findViewById(R.id.cb_select_all);
        tvTotalPrice = view.findViewById(R.id.tv_total_price);
        btnCheckout = view.findViewById(R.id.btn_checkout);

        rvCart.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CartAdapter(DataStore.cartList);
        rvCart.setAdapter(adapter);

        // 全选
        cbSelectAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            for (CartItem item : DataStore.cartList) {
                item.setSelected(isChecked);
            }
            adapter.notifyDataSetChanged();
            updateTotal();
        });

        // 去结算
        btnCheckout.setOnClickListener(v -> {
            List<CartItem> selected = DataStore.getSelectedItems();
            if (selected.isEmpty()) {
                CommonUtils.showToast(getContext(), "请选择商品");
                return;
            }
            // 跳转结算页
            Intent intent = new Intent(getActivity(), CheckoutActivity.class);
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 每次进入购物车刷新数据
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        updateTotal();
        updateSelectAllCheckbox();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 更新总价
     */
    private void updateTotal() {
        double total = DataStore.getSelectedTotal();
        tvTotalPrice.setText("¥" + String.format("%.2f", total));
    }

    /**
     * 更新全选 CheckBox 状态
     */
    private void updateSelectAllCheckbox() {
        boolean allSelected = !DataStore.cartList.isEmpty();
        for (CartItem item : DataStore.cartList) {
            if (!item.isSelected()) {
                allSelected = false;
                break;
            }
        }
        cbSelectAll.setOnCheckedChangeListener(null);
        cbSelectAll.setChecked(allSelected);
        cbSelectAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            for (CartItem item : DataStore.cartList) {
                item.setSelected(isChecked);
            }
            adapter.notifyDataSetChanged();
            updateTotal();
        });
    }

    /**
     * 购物车 Adapter — 带勾选、数量修改、删除的复杂 Item
     */
    private class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

        private final List<CartItem> cartItems;

        CartAdapter(List<CartItem> cartItems) {
            this.cartItems = cartItems;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_cart, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            CartItem item = cartItems.get(position);

            // 图形图像：商品缩略图
            holder.ivGoods.setImageResource(item.getImageResId());
            holder.tvName.setText(item.getGoodsName());
            holder.tvPrice.setText("¥" + String.format("%.2f", item.getPrice()));
            holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
            holder.cbSelected.setChecked(item.isSelected());

            // 勾选
            holder.cbSelected.setOnCheckedChangeListener(null);
            holder.cbSelected.setOnCheckedChangeListener((buttonView, isChecked) -> {
                item.setSelected(isChecked);
                updateTotal();
                updateSelectAllCheckbox();
            });

            // 数量减
            holder.btnMinus.setOnClickListener(v -> {
                if (item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                    notifyItemChanged(position);
                    updateTotal();
                }
            });

            // 数量加
            holder.btnPlus.setOnClickListener(v -> {
                item.setQuantity(item.getQuantity() + 1);
                notifyItemChanged(position);
                updateTotal();
            });

            // 删除
            holder.btnDelete.setOnClickListener(v -> {
                DataStore.removeCartItem(item.getId());
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartItems.size());
                updateTotal();
                updateSelectAllCheckbox();
            });
        }

        @Override
        public int getItemCount() {
            return cartItems.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            CheckBox cbSelected;
            ImageView ivGoods;
            TextView tvName, tvPrice, tvQuantity;
            Button btnMinus, btnPlus, btnDelete;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                cbSelected = itemView.findViewById(R.id.cb_cart_item);
                ivGoods = itemView.findViewById(R.id.iv_cart_goods);
                tvName = itemView.findViewById(R.id.tv_cart_name);
                tvPrice = itemView.findViewById(R.id.tv_cart_price);
                tvQuantity = itemView.findViewById(R.id.tv_quantity);
                btnMinus = itemView.findViewById(R.id.btn_minus);
                btnPlus = itemView.findViewById(R.id.btn_plus);
                btnDelete = itemView.findViewById(R.id.btn_delete);
            }
        }
    }
}
