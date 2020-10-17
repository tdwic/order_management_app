package com.example.ordermanagementapp.NonUiClases;

public class AllUrlsForApp {

    private String validate = "https://oder-management-api.herokuapp.com/validate";
    private String products = "https://oder-management-api.herokuapp.com/getproducts";
    private String saveOrder = "https://oder-management-api.herokuapp.com/saveOrder";
    private String saveProduct = "https://oder-management-api.herokuapp.com/saveProduct";
    private String orderDetailsById = "https://oder-management-api.herokuapp.com/getOrderDetailsById/";
    private String orders = "https://oder-management-api.herokuapp.com/getOrders";
    private String allSiteManagers = "https://oder-management-api.herokuapp.com/allSiteManagers";
    private String allSuppliers = "https://oder-management-api.herokuapp.com/allSuppliers";
    private String OrderDetailsByNo = "https://oder-management-api.herokuapp.com/getOrderDetailsByNo/";
    private String updateOrderStatus = "https://oder-management-api.herokuapp.com/updateStatus";

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

    public String getAllSiteManagers() {
        return allSiteManagers;
    }

    public String getAllSuppliers() {
        return allSuppliers;
    }

    public String getSaveProduct() {
        return saveProduct;
    }

    public String getOrderDetailsByNo() {
        return OrderDetailsByNo;
    }

    public String getUpdateOrderStatus() {
        return updateOrderStatus;
    }
}
