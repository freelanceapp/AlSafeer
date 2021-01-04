package com.alsafeer.models;

import java.io.Serializable;

public class ProductModel implements Serializable {
    private int id;
    private int product_id;
    private int deal_id;
    private int value;
    private int remaining;
    private String created_at;
    private String updated_at;
    private Item item;

    public int getId() {
        return id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public int getDeal_id() {
        return deal_id;
    }

    public int getValue() {
        return value;
    }

    public int getRemaining() {
        return remaining;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public Item getItem() {
        return item;
    }

    public static class Item implements Serializable{
        private int id;
        private String name;
        private String notes;
        private String image;
        private int buy_brice;
        private int sell_brice;
        private int category_id;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getNotes() {
            return notes;
        }

        public String getImage() {
            return image;
        }

        public int getBuy_brice() {
            return buy_brice;
        }

        public int getSell_brice() {
            return sell_brice;
        }

        public int getCategory_id() {
            return category_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }
}
