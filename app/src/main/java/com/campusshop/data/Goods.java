package com.campusshop.data;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品实体类
 */
public class Goods {
    private int id;
    private String name;
    private String category;
    private double price;
    private String description;
    private int imageResId;   // 本地图片资源 ID

    public Goods(int id, String name, String category, double price, String description, int imageResId) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.imageResId = imageResId;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public int getImageResId() { return imageResId; }
}
