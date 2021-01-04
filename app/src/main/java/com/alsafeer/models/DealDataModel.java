package com.alsafeer.models;

import java.io.Serializable;
import java.util.List;

public class DealDataModel implements Serializable {
    private List<Data> data;
    private int status;
    public List<Data> getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }

    public static class Data implements Serializable{
        private int id;
        private String name;
        private int deal_id;
        private int creation_date;
        private int describe_id;
        private int user_id;
        private int total;
        private String created_at;
        private String updated_at;
        private Describe describe;
        private List<ProductModel2> details;


        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getDeal_id() {
            return deal_id;
        }

        public int getCreation_date() {
            return creation_date;
        }

        public int getDescribe_id() {
            return describe_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public int getTotal() {
            return total;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public Describe getDescribe() {
            return describe;
        }

        public List<ProductModel2> getDetails() {
            return details;
        }

        public static class Describe implements Serializable{
            private int id;
            private String name;
            private String created_at;
            private String updated_at;

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getCreated_at() {
                return created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }
        }
    }

}
