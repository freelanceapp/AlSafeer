package com.alsafeer.models;

import java.io.Serializable;
import java.util.List;

public class JointDealDataModel implements Serializable {
    private List<Data> data;
    private int status;



    public List<Data> getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }

    public static class Detail implements Serializable{
        private int id;
        private int deal_id;
        private int user_id;
        private int describe_id;
        private int percent;
        private int value;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public int getDeal_id() {
            return deal_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public int getDescribe_id() {
            return describe_id;
        }

        public int getPercent() {
            return percent;
        }

        public int getValue() {
            return value;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }

    public static class Data implements Serializable{
        private int id;
        private String name;
        private int creation_date;
        private String created_at;
        private String updated_at;
        private Detail detail;
        private List<ProductModel> products;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getCreation_date() {
            return creation_date;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public Detail getDetail() {
            return detail;
        }

        public List<ProductModel> getProducts() {
            return products;
        }
    }

}
