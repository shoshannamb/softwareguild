/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dto;

/**
 *
 * @author apprentice
 */
public class Order {
    private int orderNumber;
    private String customerName;// "First Last"
    private String state; // as 2 letter abbreviation
    private double taxRate; // as percent divided by 100
    private String productType;
    private double area; // square foot
    private double materialCostPerSquareFoot;
    private double laborCostPerSqFt;
    private double materialCost;
    private double laborCost;
    private double tax;
    private double total;
    private String date;
    private boolean isDeleted;
    
    public String toString() {
        String orderString = "Name: " + this.customerName +
                       "\nFlooring Material: " + this.productType +
                       "\nArea: " + this.area +
                       "\nMaterial Cost Per Sq. Ft.: $" + String.format("%.2f", this.materialCostPerSquareFoot) +
                       "\nLabor Cost Per Sq. Ft.: $" + String.format("%.2f",this.laborCostPerSqFt) +
                       "\nTotal Material Cost: $" + String.format("%.2f",this.materialCost) +
                       "\nTotal Labor Cost: $" + String.format("%.2f",this.laborCost) +
                       "\nState: " + this.state +
                       "\n" + this.state + " Tax Rate: " + String.format("%.2f", this.taxRate) + "%" +
                       "\nSales Tax : $" + String.format("%.2f",this.tax) +
                       "\nTotal: $" + String.format("%.2f",this.total);
        return orderString;
    }

    public int getOrderNum() {
        return orderNumber;
    }

    public void setOrderNum(int orderNum) {
        this.orderNumber = orderNum;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getMaterialCostPerSquareFoot() {
        return materialCostPerSquareFoot;
    }

    public void setMaterialCostPerSquareFoot(double materialCostPerSquareFoot) {
        this.materialCostPerSquareFoot = materialCostPerSquareFoot;
    }

    public double getLaborCostPerSqFt() {
        return laborCostPerSqFt;
    }

    public void setLaborCostPerSqFt(double laborCostPerSqFt) {
        this.laborCostPerSqFt = laborCostPerSqFt;
    }

    public double getMaterialCost() {
        return this.materialCost;
    }

    public void setMaterialCost(double totalMaterialCost) {
        this.materialCost = totalMaterialCost;
    }

    public double getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(double totalLaborCost) {
        this.laborCost = totalLaborCost;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double totalCost) {
        this.total = totalCost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
