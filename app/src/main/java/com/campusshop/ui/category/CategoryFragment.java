package com.campusshop.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.campusshop.R;
import com.campusshop.data.DataStore;
import com.campusshop.data.Goods;

import java.util.List;

/**
 * 分类 Fragment — 展示商品分类列表
 * 组员 1 负责
 */
public class CategoryFragment extends Fragment {

    private RecyclerView rvCategory;
    private CategoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        DataStore.initGoods();

        rvCategory = view.findViewById(R.id.rv_category);
        rvCategory.setLayoutManager(new LinearLayoutManager(getContext()));

        // 用分类列表填充
        List<String> categories = DataStore.getCategories();
        adapter = new CategoryAdapter(categories);
        rvCategory.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 分类列表 Adapter — 纯文字分类 Item
     */
    private class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

        private final List<String> categories;
        private int expandedPosition = -1;  // 当前展开的分类

        CategoryAdapter(List<String> categories) {
            this.categories = categories;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_category, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String category = categories.get(position);
            List<Goods> goodsInCategory = DataStore.getGoodsByCategory(category);
            holder.tvName.setText(category + " (" + goodsInCategory.size() + "件)");

            holder.itemView.setOnClickListener(v -> {
                // 点击分类：显示该分类下商品（用 Dialog 或跳转）
                showCategoryGoods(holder.itemView, category, goodsInCategory);
            });
        }

        @Override
        public int getItemCount() {
            return categories.size();
        }

        /**
         * 显示分类下的商品（简单弹窗形式）
         */
        private void showCategoryGoods(View anchor, String title, List<Goods> goodsList) {
            if (getContext() == null) return;

            // 使用 AlertDialog 展示
            StringBuilder sb = new StringBuilder();
            for (Goods g : goodsList) {
                sb.append("• ").append(g.getName())
                        .append("  ¥").append(String.format("%.2f", g.getPrice()))
                        .append("\n");
            }

            new android.app.AlertDialog.Builder(getContext())
                    .setTitle(title + " 商品列表")
                    .setMessage(sb.toString())
                    .setPositiveButton("确定", null)
                    .show();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvName;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tv_category_name);
            }
        }
    }
}
