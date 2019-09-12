package com.recsmeterreading.model;

public class ReportDetails {
    private String services;
    private String units;
    private String amount;
    private String billed_services;
    private String unbilled;

    public ReportDetails() {
    }

    public ReportDetails(String services, String units, String amount, String billed_services, String unbilled) {
        this.services = services;
        this.units = units;
        this.amount = amount;
        this.billed_services = billed_services;
        this.unbilled = unbilled;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setBilled_services(String billed_services) {
        this.billed_services = billed_services;
    }

    public void setUnbilled(String unbilled) {
        this.unbilled = unbilled;
    }

    public String getServices() {
        return services;
    }

    public String getUnits() {
        return units;
    }

    public String getAmount() {
        return amount;
    }

    public String getBilled_services() {
        return billed_services;
    }

    public String getUnbilled() {
        return unbilled;
    }
}
