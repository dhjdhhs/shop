package com.campusshop.data;

import java.util.ArrayList;
import java.util.List;

/**
 * 静态数据仓库 — 替代 SQLite 数据库
 * 使用 static 内存列表存储所有数据，简单实现本地存储功能
 */
public class DataStore {

    // ========== 商品表 ==========
    public static List<Goods> goodsList = new ArrayList<>();
    // ========== 用户表 ==========
    public static List<User> userList = new ArrayList<>();
    // ========== 购物车表 ==========
    public static List<CartItem> cartList = new ArrayList<>();
    // ========== 订单表 ==========
    public static List<Order> orderList = new ArrayList<>();

    // 购物车自增 ID
    private static int cartIdCounter = 1;
    // 订单自增 ID
    private static int orderIdCounter = 1;

    // 当前登录用户名
    public static String currentUsername = null;

    // 静态初始化：预置测试账号
    static {
        userList.add(new User("admin", "123456"));
    }

    /**
     * 初始化预置商品数据
     */
    public static void initGoods() {
        if (!goodsList.isEmpty()) return;

        // 手机类
        goodsList.add(new Goods(1, "华为 Mate 60 Pro", "手机", 6999.00,
                "搭载麒麟芯片，卫星通话，超强影像", android.R.drawable.ic_menu_gallery));
        goodsList.add(new Goods(2, "iPhone 15 Pro", "手机", 8999.00,
                "A17 Pro 芯片，钛金属设计", android.R.drawable.ic_menu_gallery));
        goodsList.add(new Goods(3, "小米 14 Pro", "手机", 4999.00,
                "骁龙8Gen3，徕卡光学镜头", android.R.drawable.ic_menu_gallery));
        goodsList.add(new Goods(4, "OPPO Find X7", "手机", 4599.00,
                "双潜望长焦，旗舰影像", android.R.drawable.ic_menu_gallery));

        // 耳机类
        goodsList.add(new Goods(5, "AirPods Pro 2", "耳机", 1899.00,
                "自适应降噪，空间音频", android.R.drawable.ic_menu_gallery));
        goodsList.add(new Goods(6, "华为 FreeBuds Pro 3", "耳机", 1499.00,
                "星闪连接，智慧降噪", android.R.drawable.ic_menu_gallery));
        goodsList.add(new Goods(7, "漫步者 NeoBuds Pro 2", "耳机", 999.00,
                "Hi-Res 认证，圈铁混合", android.R.drawable.ic_menu_gallery));

        // 充电宝类
        goodsList.add(new Goods(8, "小米充电宝 20000mAh", "充电宝", 149.00,
                "大容量，双向快充", android.R.drawable.ic_menu_gallery));
        goodsList.add(new Goods(9, "罗马仕 sense 6", "充电宝", 89.00,
                "20000mAh，轻薄便携", android.R.drawable.ic_menu_gallery));
        goodsList.add(new Goods(10, "Anker 737 移动电源", "充电宝", 999.00,
                "140W 双向快充，智能数显", android.R.drawable.ic_menu_gallery));

        // 数据线类
        goodsList.add(new Goods(11, "倍思 100W 快充线", "数据线", 29.90,
                "Type-C 编织线，支持 PD 快充", android.R.drawable.ic_menu_gallery));
        goodsList.add(new Goods(12, "绿联 MFI 认证线", "数据线", 49.00,
                "苹果认证，1.5米", android.R.drawable.ic_menu_gallery));
    }

    /**
     * 获取所有分类
     */
    public static List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        for (Goods g : goodsList) {
            if (!categories.contains(g.getCategory())) {
                categories.add(g.getCategory());
            }
        }
        return categories;
    }

    /**
     * 按分类筛选商品
     */
    public static List<Goods> getGoodsByCategory(String category) {
        List<Goods> result = new ArrayList<>();
        for (Goods g : goodsList) {
            if (g.getCategory().equals(category)) {
                result.add(g);
            }
        }
        return result;
    }

    /**
     * 根据 ID 查找商品
     */
    public static Goods findGoodsById(int id) {
        for (Goods g : goodsList) {
            if (g.getId() == id) return g;
        }
        return null;
    }

    // ==================== 购物车操作 ====================

    public static void addToCart(Goods goods) {
        // 已存在则增加数量
        for (CartItem item : cartList) {
            if (item.getGoodsId() == goods.getId()) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        // 新增
        cartList.add(new CartItem(cartIdCounter++, goods.getId(),
                goods.getName(), goods.getPrice(), 1, goods.getImageResId()));
    }

    public static void removeCartItem(int id) {
        for (int i = 0; i < cartList.size(); i++) {
            if (cartList.get(i).getId() == id) {
                cartList.remove(i);
                return;
            }
        }
    }

    public static void clearSelectedCartItems() {
        List<CartItem> toRemove = new ArrayList<>();
        for (CartItem item : cartList) {
            if (item.isSelected()) toRemove.add(item);
        }
        cartList.removeAll(toRemove);
    }

    public static double getSelectedTotal() {
        double total = 0;
        for (CartItem item : cartList) {
            if (item.isSelected()) {
                total += item.getPrice() * item.getQuantity();
            }
        }
        return total;
    }

    public static List<CartItem> getSelectedItems() {
        List<CartItem> selected = new ArrayList<>();
        for (CartItem item : cartList) {
            if (item.isSelected()) selected.add(item);
        }
        return selected;
    }

    // ==================== 订单操作 ====================

    public static Order addOrder(List<CartItem> items) {
        StringBuilder names = new StringBuilder();
        double total = 0;
        int imgRes = android.R.drawable.ic_menu_gallery;
        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);
            if (i > 0) names.append("、");
            names.append(item.getGoodsName());
            total += item.getPrice() * item.getQuantity();
        }
        if (!items.isEmpty()) {
            imgRes = items.get(0).getImageResId();
        }
        String time = java.text.SimpleDateFormat.getDateTimeInstance()
                .format(new java.util.Date());
        Order order = new Order(orderIdCounter++, names.toString(), total, time, "已完成", imgRes);
        orderList.add(0, order);  // 最新订单排最前
        return order;
    }

    // ==================== 用户操作 ====================

    public static boolean register(String username, String password) {
        for (User u : userList) {
            if (u.getUsername().equals(username)) return false;
        }
        userList.add(new User(username, password));
        return true;
    }

    public static boolean login(String username, String password) {
        for (User u : userList) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                currentUsername = username;
                return true;
            }
        }
        return false;
    }

    public static void logout() {
        currentUsername = null;
    }

    public static boolean isLoggedIn() {
        return currentUsername != null;
    }
}
