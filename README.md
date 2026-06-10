# 简易校园电子商城 Android App

## 项目概述

三人小组合作开发的简易校园电子商城 Android 应用，覆盖 Activity/Fragment、RecyclerView、本地存储、生命周期等全部必选要求，附带图形图像处理与多媒体音效。

## 技术栈

- **语言**: Java
- **最低 SDK**: Android 7.0 (API 24)
- **目标 SDK**: Android 14 (API 34)
- **依赖**: AppCompat、Material Design、RecyclerView、CardView
- **数据存储**: 静态内存存储（替代 SQLite，简单实现本地存储功能）

## 项目结构

```
CampusShop/
├── build.gradle                          # 根构建文件
├── settings.gradle                       # 项目设置
├── gradle/wrapper/
│   └── gradle-wrapper.properties         # Gradle 配置
└── app/
    ├── build.gradle                      # 模块构建文件
    └── src/main/
        ├── AndroidManifest.xml           # 应用清单
        ├── res/
        │   ├── layout/                   # 布局文件 (12 个)
        │   │   ├── activity_main.xml          # 主容器
        │   │   ├── activity_login.xml         # 登录页
        │   │   ├── activity_register.xml      # 注册页
        │   │   ├── activity_goods_detail.xml  # 商品详情
        │   │   ├── activity_checkout.xml      # 结算页
        │   │   ├── activity_order_list.xml    # 订单列表
        │   │   ├── fragment_home.xml          # 首页碎片
        │   │   ├── fragment_category.xml      # 分类碎片
        │   │   ├── fragment_cart.xml          # 购物车碎片
        │   │   ├── fragment_mine.xml          # 个人中心碎片
        │   │   ├── item_goods.xml             # 商品 Item
        │   │   ├── item_category.xml          # 分类 Item
        │   │   ├── item_cart.xml              # 购物车 Item
        │   │   ├── item_checkout.xml          # 结算 Item
        │   │   ├── item_mine_menu.xml         # 菜单 Item
        │   │   └── item_order.xml             # 订单 Item
        │   ├── menu/
        │   │   └── bottom_nav.xml             # 底部导航菜单
        │   ├── drawable/
        │   │   └── circle_bg.xml              # 圆形背景
        │   └── values/
        │       ├── colors.xml                 # 颜色定义
        │       ├── strings.xml                # 字符串资源
        │       └── themes.xml                 # 主题样式
        └── java/com/campusshop/
            ├── data/                          # 数据层
            │   ├── Goods.java                 # 商品实体
            │   ├── User.java                  # 用户实体
            │   ├── CartItem.java              # 购物车实体
            │   ├── Order.java                 # 订单实体
            │   └── DataStore.java             # 静态数据仓库（替代SQLite）
            ├── utils/
            │   └── CommonUtils.java           # 公共工具类
            └── ui/                            # 界面层
                ├── MainActivity.java          # 主容器（底部导航）
                ├── home/
                │   └── HomeFragment.java      # 首页 Fragment
                ├── category/
                │   └── CategoryFragment.java  # 分类 Fragment
                ├── detail/
                │   └── GoodsDetailActivity.java # 商品详情 Activity
                ├── login/
                │   └── LoginActivity.java     # 登录 Activity
                ├── register/
                │   └── RegisterActivity.java  # 注册 Activity
                ├── mine/
                │   └── MineFragment.java      # 个人中心 Fragment
                ├── cart/
                │   └── CartFragment.java      # 购物车 Fragment
                ├── checkout/
                │   └── CheckoutActivity.java  # 结算 Activity
                └── order/
                    └── OrderListActivity.java # 订单列表 Activity
```

---

## 一、公共基础（三人共用）

| 文件 | 说明 |
|------|------|
| `MainActivity.java` | 主容器，底部导航栏切换 Fragment（首页/分类/购物车/我的） |
| `DataStore.java` | 静态数据仓库，管理商品/用户/购物车/订单数据（替代 SQLite） |
| `CommonUtils.java` | 公共工具：页面跳转、Toast 提示、音效播放 |

---

## 二、组员 1：首页 + 商品分类模块

### 负责页面
- `HomeFragment` — 首页（轮播图 + 热门商品列表）
- `CategoryFragment` — 商品分类（分类列表 + 弹窗展示商品）
- `GoodsDetailActivity` — 商品详情（大图 + 信息 + 加入购物车）

### 对应文件（3 布局 + 3 Java）

| 文件 | 类型 |
|------|------|
| `fragment_home.xml` | 布局 |
| `fragment_category.xml` | 布局 |
| `activity_goods_detail.xml` | 布局 |
| `HomeFragment.java` | 逻辑 |
| `CategoryFragment.java` | 逻辑 |
| `GoodsDetailActivity.java` | 逻辑 |

### 技术点覆盖

| 要求 | 实现方式 |
|------|----------|
| ✅ Activity/Fragment | 1 Activity + 2 Fragment，页面跳转 |
| ✅ RecyclerView | 商品列表（图文 Item）+ 分类列表（文字 Item），2 个 Adapter |
| ✅ 本地存储 | DataStore 读取商品数据 |
| ✅ 生命周期 | onCreate 初始化加载；onResume 刷新列表；onDestroy 释放资源 |
| ✅ 图形图像 | 轮播图 ImageView 加载、商品图片展示、详情页大图 |

---

## 三、组员 2：登录注册 + 个人中心模块

### 负责页面
- `LoginActivity` — 登录（表单校验 + SP 存储登录状态）
- `RegisterActivity` — 注册（表单校验 + 数据存储）
- `MineFragment` — 个人中心（头像 + 菜单列表 + 退出登录）

### 对应文件（3 布局 + 3 Java）

| 文件 | 类型 |
|------|------|
| `activity_login.xml` | 布局 |
| `activity_register.xml` | 布局 |
| `fragment_mine.xml` | 布局 |
| `LoginActivity.java` | 逻辑 |
| `RegisterActivity.java` | 逻辑 |
| `MineFragment.java` | 逻辑 |

### 技术点覆盖

| 要求 | 实现方式 |
|------|----------|
| ✅ Activity/Fragment | 2 Activity + 1 Fragment，登录状态控制跳转 |
| ✅ RecyclerView | 个人中心功能菜单列表，带图标+文字的复杂 Item |
| ✅ 本地存储 | DataStore 存用户 + SharedPreferences 存登录状态 |
| ✅ 生命周期 | onCreate 初始化控件；onResume 检查登录状态；onDestroy 清空数据 |
| ✅ 图形图像 | 用户头像展示、页面背景图标、菜单图标美化 |

---

## 四、组员 3：购物车 + 订单模块

### 负责页面
- `CartFragment` — 购物车（全选、数量修改、删除、实时总价）
- `CheckoutActivity` — 结算（订单清单 + 提交 + 音效）
- `OrderListActivity` — 订单列表（历史订单 + 详情弹窗）

### 对应文件（3 布局 + 3 Java）

| 文件 | 类型 |
|------|------|
| `fragment_cart.xml` | 布局 |
| `activity_checkout.xml` | 布局 |
| `activity_order_list.xml` | 布局 |
| `CartFragment.java` | 逻辑 |
| `CheckoutActivity.java` | 逻辑 |
| `OrderListActivity.java` | 逻辑 |

### 技术点覆盖

| 要求 | 实现方式 |
|------|----------|
| ✅ Activity/Fragment | 1 Fragment + 2 Activity，订单流程跳转 |
| ✅ RecyclerView | 购物车列表（勾选+数量+删除）+ 订单列表（缩略图+状态），2 个 Adapter |
| ✅ 本地存储 | DataStore 操作购物车 + 订单数据，增删改查 |
| ✅ 生命周期 | onCreate 加载数据；onResume 刷新；onDestroy 释放资源 |
| ✅ 图形图像 | 购物车缩略图、订单商品缩略图、订单状态图标 |
| ✅ 多媒体音效 | 提交订单时播放系统提示音（满足音视频/音乐要求） |

---

## 五人对比确认：难度完全均等

| 对比项 | 组员 1 | 组员 2 | 组员 3 |
|--------|--------|--------|--------|
| 页面数量 | 2 Fragment + 1 Activity | 1 Fragment + 2 Activity | 1 Fragment + 2 Activity |
| XML 布局 | 3 个 | 3 个 | 3 个 |
| Java 文件 | 3 个 | 3 个 | 3 个 |
| RecyclerView | 2 个 Adapter | 1 个 Adapter | 2 个 Adapter |
| 本地存储 | DataStore 读商品 | DataStore + SP | DataStore 双表 |
| 生命周期 | 全钩子覆盖 | 全钩子覆盖 | 全钩子覆盖 |
| 图形图像 | 轮播图+商品图片 | 头像+背景+图标 | 缩略图+状态图标 |
| 额外工作 | 无 | 无 | 音效（5行代码） |

---

## 如何使用

1. 用 Android Studio 打开 `/CampusShop` 目录
2. 等待 Gradle 同步完成
3. 选择模拟器或真机运行
4. 首次运行：先注册账号 → 登录 → 浏览商品 → 加入购物车 → 结算 → 查看订单

## 数据说明

- 所有数据存储在内存中（`DataStore.java` 的 static 列表）
- 预置 12 件商品（手机/耳机/充电宝/数据线 各3件）
- 预置 1 个测试账号：`admin` / `123456`（可自行注册新账号）
- App 关闭后数据会重置（因为是静态存储，不持久化）
