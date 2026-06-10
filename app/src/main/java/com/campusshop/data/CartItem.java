package com.campusshop.data;

/**
 * 购物车条目实体类
 */
public class CartItem {
    private int id;
    private int goodsId;
    private String goodsName;
    private double price;
    private int quantity;
    private int imageResId;
    private boolean selected;   // 是否被勾选

    public CartItem(int id, int goodsId, String goodsName, double price, int quantity, int imageResId) {
        this.id = id;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.price = price;
        this.quantity = quantity;
        this.imageResId = imageResId;
        this.selected = false;
    }

    public int getId() { return id; }
    public int getGoodsId() { return goodsId; }
    public String getGoodsName() { return goodsName; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public int getImageResId() { return imageResId; }
    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
