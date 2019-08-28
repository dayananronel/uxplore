package com.example.uxplore.model;

public class PaymentsCallBack {
    private String txnno ="";
    private String DateTimeIN="";
    private String packageid="";
    private String paymenttype="";
    private String TotalAmountToPay="";

    public String getTxnno() {
        return txnno;
    }

    public void setTxnno(String txnno) {
        this.txnno = txnno;
    }

    public String getDateTimeIN() {
        return DateTimeIN;
    }

    public void setDateTimeIN(String dateTimeIN) {
        DateTimeIN = dateTimeIN;
    }

    public String getPackageid() {
        return packageid;
    }

    public void setPackageid(String packageid) {
        this.packageid = packageid;
    }

    public String getPaymenttype() {
        return paymenttype;
    }

    public void setPaymenttype(String paymenttype) {
        this.paymenttype = paymenttype;
    }

    public String getTotalAmountToPay() {
        return TotalAmountToPay;
    }

    public void setTotalAmountToPay(String totalAmountToPay) {
        TotalAmountToPay = totalAmountToPay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTouristid() {
        return touristid;
    }

    public void setTouristid(String touristid) {
        this.touristid = touristid;
    }

    public String getDriverid() {
        return driverid;
    }

    public void setDriverid(String driverid) {
        this.driverid = driverid;
    }

    private String status="";
    private String touristid="";
    private String driverid="";
}
