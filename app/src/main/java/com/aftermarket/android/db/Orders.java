package com.aftermarket.android.db;

import org.litepal.crud.DataSupport;

public class Orders extends DataSupport {
    private int id;
    private int status;
    private int customer_id;
    private int maintainer_id;
    private int customer_phone;
    private int maintainer_phone;
    private String address;
    private int goods_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getMaintainer_id() {
        return maintainer_id;
    }

    public void setMaintainer_id(int maintainer_id) {
        this.maintainer_id = maintainer_id;
    }

    public int getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(int customer_phone) {
        this.customer_phone = customer_phone;
    }

    public int getMaintainer_phone() {
        return maintainer_phone;
    }

    public void setMaintainer_phone(int maintainer_phone) {
        this.maintainer_phone = maintainer_phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }
}
