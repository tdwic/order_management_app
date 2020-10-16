package com.example.ordermanagementapp.NonUiClases;

public class AllUrlsForApp {

    private String validate = "https://oder-management-api.herokuapp.com/validate";
    private String products = "https://oder-management-api.herokuapp.com/getproducts";
    private String saveOrder = "https://oder-management-api.herokuapp.com/saveOrder";
    private String orderDetailsById = "https://oder-management-api.herokuapp.com/getOrderDetailsById/";
    private String orders = "https://oder-management-api.herokuapp.com/getOrders";

    public String getValidate() {
        return validate;
    }

    public String getProducts() {
        return products;
    }

    public String getSaveOrder() {
        return saveOrder;
    }

    public String getOrderDetailsById() {
        return orderDetailsById;
    }

    public String getOrders() {
        return orders;
    }
}
