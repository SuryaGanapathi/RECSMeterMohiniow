package com.recsmeterreading.model;

public class LocalBillModel {


    /**
     * SERVICENO : 80827 00096
     * AREACODE : 80827
     * OLDRDG_KVAH : 535
     * PRESRDG_KWH : 868
     * UNITS : 333
     * ENGCHG : 1480.2
     * CUSCHG : 40
     * EDCHG : 19.98
     * BILLAMT : 1545
     * ADJAMT : 0
     * TOTALAMT : 1545
     * CATEGORY : 100
     */

    private String SERVICENO;
    private String AREACODE;
    private String OLDRDG_KVAH;
    private String PRESRDG_KWH;
    private String UNITS;
    private String ENGCHG;
    private String CUSCHG;
    private String EDCHG;
    private String BILLAMT;
    private String ADJAMT;
    private String TOTALAMT;
    private String CATEGORY;
    private String LORG;
    private String STATUS;
    //praful
    private String LASTCONSUMPTION;
    private String NKT;
    private String FIXEDCHARGE;
    private String METERBRUNTVALUE;
    private String AADHARNO;
    private String PHONENO;
    private String NEWMETERNO;
    private String BILLNO;

    //praful
    private String OPSTATUS;
    private String BILLDATE;
    private String LPAMT;
    private String DUEDATE;
    private String DISCONDATE;
    private String LOAD;
    private String CASTE;
    private String NEWARREARS;

    public String getCASTE() {
        return CASTE;
    }

    public void setCASTE(String CASTE) {
        this.CASTE = CASTE;
    }

    public String getNEWARREARS() {
        return NEWARREARS;
    }

    public void setNEWARREARS(String NEWARREARS) {
        this.NEWARREARS = NEWARREARS;
    }

    public String getOPSTATUS() {
        return OPSTATUS;
    }

    public void setOPSTATUS(String OPSTATUS) {
        this.OPSTATUS = OPSTATUS;
    }

    public String getBILLDATE() {
        return BILLDATE;
    }

    public void setBILLDATE(String BILLDATE) {
        this.BILLDATE = BILLDATE;
    }

    public String getLPAMT() {
        return LPAMT;
    }

    public void setLPAMT(String LPAMT) {
        this.LPAMT = LPAMT;
    }

    public String getDUEDATE() {
        return DUEDATE;
    }

    public void setDUEDATE(String DUEDATE) {
        this.DUEDATE = DUEDATE;
    }

    public String getDISCONDATE() {
        return DISCONDATE;
    }

    public void setDISCONDATE(String DISCONDATE) {
        this.DISCONDATE = DISCONDATE;
    }

    public String getLOAD() {
        return LOAD;
    }

    public void setLOAD(String LOAD) {
        this.LOAD = LOAD;
    }

    public String getOLDRDG_KVAH() {
        return OLDRDG_KVAH;
    }

    public void setOLDRDG_KVAH(String OLDRDG_KVAH) {
        this.OLDRDG_KVAH = OLDRDG_KVAH;
    }

    public String getLASTCONSUMPTION() {
        return LASTCONSUMPTION;
    }

    public void setLASTCONSUMPTION(String LASTCONSUMPTION) {
        this.LASTCONSUMPTION = LASTCONSUMPTION;
    }

    public String getNKT() {
        return NKT;
    }

    public void setNKT(String NKT) {
        this.NKT = NKT;
    }

    public String getFIXEDCHARGE() {
        return FIXEDCHARGE;
    }

    public void setFIXEDCHARGE(String FIXEDCHARGE) {
        this.FIXEDCHARGE = FIXEDCHARGE;
    }

    public String getMETERBRUNTVALUE() {
        return METERBRUNTVALUE;
    }

    public void setMETERBRUNTVALUE(String METERBRUNTVALUE) {
        this.METERBRUNTVALUE = METERBRUNTVALUE;
    }

    public String getAADHARNO() {
        return AADHARNO;
    }

    public void setAADHARNO(String AADHARNO) {
        this.AADHARNO = AADHARNO;
    }

    public String getPHONENO() {
        return PHONENO;
    }

    public void setPHONENO(String PHONENO) {
        this.PHONENO = PHONENO;
    }

    public String getNEWMETERNO() {
        return NEWMETERNO;
    }

    public void setNEWMETERNO(String NEWMETERNO) {
        this.NEWMETERNO = NEWMETERNO;
    }

    public String getBILLNO() {
        return BILLNO;
    }

    public void setBILLNO(String BILLNO) {
        this.BILLNO = BILLNO;
    }

    public String getBILL_DATE() {
        return BILL_DATE;
    }

    public void setBILL_DATE(String BILL_DATE) {
        this.BILL_DATE = BILL_DATE;
    }

    private String BILL_DATE;

    public String getLORG() {
        return LORG;
    }

    public void setLORG(String LORG) {
        this.LORG = LORG;
    }


    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }


    public String getCONSUMER_NAME() {
        return CONSUMER_NAME;
    }

    public void setCONSUMER_NAME(String CONSUMER_NAME) {
        this.CONSUMER_NAME = CONSUMER_NAME;
    }

    private String CONSUMER_NAME;

    public String getSERVICENO() {
        return SERVICENO;
    }

    public void setSERVICENO(String SERVICENO) {
        this.SERVICENO = SERVICENO;
    }

    public String getAREACODE() {
        return AREACODE;
    }

    public void setAREACODE(String AREACODE) {
        this.AREACODE = AREACODE;
    }

    public String getOLDRDG_KWH() {
        return OLDRDG_KVAH;
    }

    public void setOLDRDG_KWH(String OLDRDG_KVAH) {
        this.OLDRDG_KVAH = OLDRDG_KVAH;
    }

    public String getPRESRDG_KWH() {
        return PRESRDG_KWH;
    }

    public void setPRESRDG_KWH(String PRESRDG_KWH) {
        this.PRESRDG_KWH = PRESRDG_KWH;
    }

    public String getUNITS() {
        return UNITS;
    }

    public void setUNITS(String UNITS) {
        this.UNITS = UNITS;
    }

    public String getENGCHG() {
        return ENGCHG;
    }

    public void setENGCHG(String ENGCHG) {
        this.ENGCHG = ENGCHG;
    }

    public String getCUSCHG() {
        return CUSCHG;
    }

    public void setCUSCHG(String CUSCHG) {
        this.CUSCHG = CUSCHG;
    }

    public String getEDCHG() {
        return EDCHG;
    }

    public void setEDCHG(String EDCHG) {
        this.EDCHG = EDCHG;
    }

    public String getBILLAMT() {
        return BILLAMT;
    }

    public void setBILLAMT(String BILLAMT) {
        this.BILLAMT = BILLAMT;
    }

    public String getADJAMT() {
        return ADJAMT;
    }

    public void setADJAMT(String ADJAMT) {
        this.ADJAMT = ADJAMT;
    }

    public String getTOTALAMT() {
        return TOTALAMT;
    }

    public void setTOTALAMT(String TOTALAMT) {
        this.TOTALAMT = TOTALAMT;
    }

    public String getCATEGORY() {
        return CATEGORY;
    }

    public void setCATEGORY(String CATEGORY) {
        this.CATEGORY = CATEGORY;
    }
}
