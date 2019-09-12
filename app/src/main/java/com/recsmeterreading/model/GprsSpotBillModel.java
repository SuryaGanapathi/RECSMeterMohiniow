package com.recsmeterreading.model;

/**
 * Created by HP on 07-05-2018.
 */

public class GprsSpotBillModel {

     String BILLNO;
     String MACHINEID;
     String SERVICENO;
     String AREACODE;
     String OLDRDG_KWH;
     String OLDRDG_KVAH;
     String OLDDT;
     String PRESRDG_KVAH;
     String PRESRDG_KWH;
     String PRESRDG_LT;
     String PRESRDG_KVA;
     String PRESDT;
     String PRESSTS;
     String PRESSTS_LT;
     String INITRDG_KWH;
     String FINALRDG_KWH;
     String INITRDG_KVAH;
     String FINALRDG_KVAH;
     String UNITS;
     String UNITS_LT;
     String ENGCHG;
     String FIXEDCHG;
     String CUSCHG;
     String EDCHG;
     String LPF_CAP_CHG;
     String BILLAMT;
     String ADJAMT;
     String TOTALAMT;
     String AVGUNITS;
     String OLDARREARS;
     String NEWARREARS;
     String ASURCHG;
     String AEDINT;
     String PAMOUNT;
     String PEDCHG;
     String ACD;
     String FSA;
     String OTHERS1;
     String OTHERS2;
     String DUEDATE;
     String DISCDATE;
     String SUBSIDIAMT;
     String PF;
     String LORG;
     String MD;
     String KVAH;
     String EROCODE;
     String CATEGORY;
     String BILLMONTH;
    String lastMonthUnits;

    public String getLastMonthUnits() {
        return lastMonthUnits;
    }

    public void setLastMonthUnits(String lastMonthUnits) {
        this.lastMonthUnits = lastMonthUnits;
    }

    public String getBILLNO() {
        return BILLNO;
    }

    public void setBILLNO(String BILLNO) {
        this.BILLNO = BILLNO;
    }

    public String getMACHINEID() {
        return MACHINEID;
    }

    public void setMACHINEID(String MACHINEID) {
        this.MACHINEID = MACHINEID;
    }

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
        return OLDRDG_KWH;
    }

    public void setOLDRDG_KWH(String OLDRDG_KWH) {
        this.OLDRDG_KWH = OLDRDG_KWH;
    }

    public String getOLDRDG_KVAH() {
        return OLDRDG_KVAH;
    }

    public void setOLDRDG_KVAH(String OLDRDG_KVAH) {
        this.OLDRDG_KVAH = OLDRDG_KVAH;
    }

    public String getOLDDT() {
        return OLDDT;
    }

    public void setOLDDT(String OLDDT) {
        this.OLDDT = OLDDT;
    }

    public String getPRESRDG_KVAH() {
        return PRESRDG_KVAH;
    }

    public void setPRESRDG_KVAH(String PRESRDG_KVAH) {
        this.PRESRDG_KVAH = PRESRDG_KVAH;
    }

    public String getPRESRDG_KWH() {
        return PRESRDG_KWH;
    }

    public void setPRESRDG_KWH(String PRESRDG_KWH) {
        this.PRESRDG_KWH = PRESRDG_KWH;
    }

    public String getPRESRDG_LT() {
        return PRESRDG_LT;
    }

    public void setPRESRDG_LT(String PRESRDG_LT) {
        this.PRESRDG_LT = PRESRDG_LT;
    }

    public String getPRESRDG_KVA() {
        return PRESRDG_KVA;
    }

    public void setPRESRDG_KVA(String PRESRDG_KVA) {
        this.PRESRDG_KVA = PRESRDG_KVA;
    }

    public String getPRESDT() {
        return PRESDT;
    }

    public void setPRESDT(String PRESDT) {
        this.PRESDT = PRESDT;
    }

    public String getPRESSTS() {
        return PRESSTS;
    }

    public void setPRESSTS(String PRESSTS) {
        this.PRESSTS = PRESSTS;
    }

    public String getPRESSTS_LT() {
        return PRESSTS_LT;
    }

    public void setPRESSTS_LT(String PRESSTS_LT) {
        this.PRESSTS_LT = PRESSTS_LT;
    }

    public String getINITRDG_KWH() {
        return INITRDG_KWH;
    }

    public void setINITRDG_KWH(String INITRDG_KWH) {
        this.INITRDG_KWH = INITRDG_KWH;
    }

    public String getFINALRDG_KWH() {
        return FINALRDG_KWH;
    }

    public void setFINALRDG_KWH(String FINALRDG_KWH) {
        this.FINALRDG_KWH = FINALRDG_KWH;
    }

    public String getINITRDG_KVAH() {
        return INITRDG_KVAH;
    }

    public void setINITRDG_KVAH(String INITRDG_KVAH) {
        this.INITRDG_KVAH = INITRDG_KVAH;
    }

    public String getFINALRDG_KVAH() {
        return FINALRDG_KVAH;
    }

    public void setFINALRDG_KVAH(String FINALRDG_KVAH) {
        this.FINALRDG_KVAH = FINALRDG_KVAH;
    }

    public String getUNITS() {
        return UNITS;
    }

    public void setUNITS(String UNITS) {
        this.UNITS = UNITS;
    }

    public String getUNITS_LT() {
        return UNITS_LT;
    }

    public void setUNITS_LT(String UNITS_LT) {
        this.UNITS_LT = UNITS_LT;
    }

    public String getENGCHG() {
        return ENGCHG;
    }

    public void setENGCHG(String ENGCHG) {
        this.ENGCHG = ENGCHG;
    }

    public String getFIXEDCHG() {
        return FIXEDCHG;
    }

    public void setFIXEDCHG(String FIXEDCHG) {
        this.FIXEDCHG = FIXEDCHG;
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

    public String getLPF_CAP_CHG() {
        return LPF_CAP_CHG;
    }

    public void setLPF_CAP_CHG(String LPF_CAP_CHG) {
        this.LPF_CAP_CHG = LPF_CAP_CHG;
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

    public String getAVGUNITS() {
        return AVGUNITS;
    }

    public void setAVGUNITS(String AVGUNITS) {
        this.AVGUNITS = AVGUNITS;
    }

    public String getOLDARREARS() {
        return OLDARREARS;
    }

    public void setOLDARREARS(String OLDARREARS) {
        this.OLDARREARS = OLDARREARS;
    }

    public String getNEWARREARS() {
        return NEWARREARS;
    }

    public void setNEWARREARS(String NEWARREARS) {
        this.NEWARREARS = NEWARREARS;
    }

    public String getASURCHG() {
        return ASURCHG;
    }

    public void setASURCHG(String ASURCHG) {
        this.ASURCHG = ASURCHG;
    }

    public String getAEDINT() {
        return AEDINT;
    }

    public void setAEDINT(String AEDINT) {
        this.AEDINT = AEDINT;
    }

    public String getPAMOUNT() {
        return PAMOUNT;
    }

    public void setPAMOUNT(String PAMOUNT) {
        this.PAMOUNT = PAMOUNT;
    }

    public String getPEDCHG() {
        return PEDCHG;
    }

    public void setPEDCHG(String PEDCHG) {
        this.PEDCHG = PEDCHG;
    }

    public String getACD() {
        return ACD;
    }

    public void setACD(String ACD) {
        this.ACD = ACD;
    }

    public String getFSA() {
        return FSA;
    }

    public void setFSA(String FSA) {
        this.FSA = FSA;
    }

    public String getOTHERS1() {
        return OTHERS1;
    }

    public void setOTHERS1(String OTHERS1) {
        this.OTHERS1 = OTHERS1;
    }

    public String getOTHERS2() {
        return OTHERS2;
    }

    public void setOTHERS2(String OTHERS2) {
        this.OTHERS2 = OTHERS2;
    }

    public String getDUEDATE() {
        return DUEDATE;
    }

    public void setDUEDATE(String DUEDATE) {
        this.DUEDATE = DUEDATE;
    }

    public String getDISCDATE() {
        return DISCDATE;
    }

    public void setDISCDATE(String DISCDATE) {
        this.DISCDATE = DISCDATE;
    }

    public String getSUBSIDIAMT() {
        return SUBSIDIAMT;
    }

    public void setSUBSIDIAMT(String SUBSIDIAMT) {
        this.SUBSIDIAMT = SUBSIDIAMT;
    }

    public String getPF() {
        return PF;
    }

    public void setPF(String PF) {
        this.PF = PF;
    }

    public String getLORG() {
        return LORG;
    }

    public void setLORG(String LORG) {
        this.LORG = LORG;
    }

    public String getMD() {
        return MD;
    }

    public void setMD(String MD) {
        this.MD = MD;
    }

    public String getKVAH() {
        return KVAH;
    }

    public void setKVAH(String KVAH) {
        this.KVAH = KVAH;
    }

    public String getEROCODE() {
        return EROCODE;
    }

    public void setEROCODE(String EROCODE) {
        this.EROCODE = EROCODE;
    }

    public String getCATEGORY() {
        return CATEGORY;
    }

    public void setCATEGORY(String CATEGORY) {
        this.CATEGORY = CATEGORY;
    }

    public String getBILLMONTH() {
        return BILLMONTH;
    }

    public void setBILLMONTH(String BILLMONTH) {
        this.BILLMONTH = BILLMONTH;
    }
}
