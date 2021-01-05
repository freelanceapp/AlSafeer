package com.alsafeer.models;

import java.io.Serializable;
import java.util.List;

public class NotificationDataModel implements Serializable {
    public List<NotificationModel> data;
    public int status;

    public List<NotificationModel> getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }

    public static class NotificationModel implements Serializable{
        private int id;
        private int value;
        private String details;
        private int deal_id;
        private int user_id;
        private String created_at;
        private String updated_at;
        private long date;

        public int getId() {
            return id;
        }

        public int getValue() {
            return value;
        }

        public String getDetails() {
            return details;
        }

        public int getDeal_id() {
            return deal_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public long getDate() {
            return date;
        }
    }
}
