package com.campusshop.data;

/**
 * 订单实体类
 */
public class Order {
    private int id;
    private String goodsNames;     // 商品名称拼接
    private double totalPrice;
    private String time;
    private String status;         // "已完成"
    private int imageResId;        // 订单商品缩略图

    public Order(int id, String goodsNames, double totalPrice, String time, String status, int imageResId) {
        this.id = id;
        this.goodsNames = goodsNames;
        this.totalPrice = totalPrice;
        this.time = time;
        this.status = status;
        this.imageResId = imageResId;
    }

    public int getId() { return id; }
    public String getGoodsNames() { return goodsNames; }
    public double getTotalPrice() { return totalPrice; }
    public String getTime() { return time; }
    public String getStatus() { return status; }
    public int getImageResId() { return imageResId; }
}
