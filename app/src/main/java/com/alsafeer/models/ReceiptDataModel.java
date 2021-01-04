package com.alsafeer.models;

import java.io.Serializable;
import java.util.List;

public class ReceiptDataModel implements Serializable {
    public List<ReceiptModel> data;
    public int status;

    public List<ReceiptModel> getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }

    public static class ReceiptModel implements Serializable {
        private int id;
        private int deal_id;
        private int user_id;
        private int seal_id;
        private int value;
        private int pay_date;
        private String status;
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

        public int getSeal_id() {
            return seal_id;
        }

        public int getValue() {
            return value;
        }

        public int getPay_date() {
            return pay_date;
        }

        public String getStatus() {
            return status;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }


}
