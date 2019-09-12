package com.recsmeterreading.model;

import java.io.Serializable;

/**
 * Created by HP on 09-04-2018.
 */

public class GetBillDetailsModel implements Serializable {
    private String id;
    private String AREACODE;
    private String month;
    private String SCNO;
    private String Category;
    private String Subcategory;
    private String Slbamoth;
    private String groupid;
    private String open_bal;
    private String close_bal;
    private String dj;
    private String adj_amount;
    private String PHASE;
    private String opmonth;
    private String opstatus;
    private String OPRDNG;
    private String STATUS;
    private String UNITS;
    private String DATEDIS;
    private String METERCY;
    private String EDINT;
    private String CLEDUTY;
    private String CELEDINT;
    private String OPEDINT;
    private String CAPCHRGS;
    private String SURCHRGS;
    private String CSSURCHG;
    private String OSSURCHG;
    private String OPFSA;
    private String CLFSA;
    private String CL2;
    private String CL3;
    private String CL4;
    private String CL5;
    private String CL6;
    private String LRPNO;
    private String LPDATE;
    private String LPAMT;
    private String POINTS;
    private String CAPFLAG;
    private String CAPSURCH;
    private String SBSFLAG;
    private String OPDEMAN;
    private String CLDEMAN;
    private String CLRDNG;
    private String CLSTATUS;
    private String CSM_CONSUMER_NAME;
    private String CSM_ADDRESS3;
    private String CSM_CONNECTED_LOAD;
    private String nkt;
    private String previousMeterStartRead;
    private String currentMeterStartRead;
    private String previousMeterEndRead;
    private String currentMeterEndRead;
    private String ENGCHG,FIXEDCHG,CUSCHG,EDCHG;
    private String BILLAMT;
    private String LORG;
    private String BILLDATE;
    private String DUEDATE;
    private String DISCDATE;
    private String LASTMCONSUPTION;
    private String NEWARREARS; //added by praful 5 below field
    private String CSM_AADHAAR_NO;
    private String CSM_CASTE;
    private String CSM_PHONE_NO;
    private String CSM_SHARE;
    private String MD;
    private String ERO;

    public GetBillDetailsModel(GetBillDetailsModel billdetailModel) {
        this.id = billdetailModel.id;
        this.AREACODE = billdetailModel.AREACODE;
        this.month = billdetailModel.month;
        this.SCNO = billdetailModel.SCNO;
        this.Category = billdetailModel.Category;
        this.Subcategory = billdetailModel.Subcategory;
        this.Slbamoth = billdetailModel.Slbamoth;
        this.groupid = billdetailModel.groupid;
        this.open_bal = billdetailModel.open_bal;
        this.close_bal = billdetailModel.close_bal;
        this.dj = billdetailModel.dj;
        this.adj_amount = billdetailModel.adj_amount;
        this.PHASE = billdetailModel.PHASE;
        this.opmonth = billdetailModel.opmonth;
        this.opstatus = billdetailModel.opstatus;
        this.OPRDNG = billdetailModel.OPRDNG;
        this.STATUS = billdetailModel.STATUS;
        this.UNITS = billdetailModel.UNITS;
        this.DATEDIS = billdetailModel.DATEDIS;
        this.METERCY = billdetailModel.METERCY;
        this.EDINT = billdetailModel.EDINT;
        this.CLEDUTY = billdetailModel.CLEDUTY;
        this.CELEDINT = billdetailModel.CELEDINT;
        this.OPEDINT = billdetailModel.OPEDINT;
        this.CAPCHRGS = billdetailModel.CAPCHRGS;
        this.SURCHRGS = billdetailModel.SURCHRGS;
        this.CSSURCHG = billdetailModel.CSSURCHG;
        this.OSSURCHG = billdetailModel.OSSURCHG;
        this.OPFSA = billdetailModel.OPFSA;
        this.CLFSA = billdetailModel.CLFSA;
        this.CL2 = billdetailModel.CL2;
        this.CL3 = billdetailModel.CL3;
        this.CL4 = billdetailModel.CL4;
        this.CL5 = billdetailModel.CL5;
        this.CL6 = billdetailModel.CL6;
        this.LRPNO = billdetailModel.LRPNO;
        this.LPDATE = billdetailModel.LPDATE;
        this.LPAMT = billdetailModel.LPAMT;
        this.POINTS = billdetailModel.POINTS;
        this.CAPFLAG = billdetailModel.CAPFLAG;
        this.CAPSURCH = billdetailModel.CAPSURCH;
        this.SBSFLAG = billdetailModel.SBSFLAG;
        this.OPDEMAN = billdetailModel.OPDEMAN;
        this.CLDEMAN = billdetailModel.CLDEMAN;
        this.CLRDNG = billdetailModel.CLRDNG;
        this.CLSTATUS = billdetailModel.CLSTATUS;
        this.CSM_CONSUMER_NAME = billdetailModel.CSM_CONSUMER_NAME;
        this.CSM_ADDRESS3 = billdetailModel.CSM_ADDRESS3;
        this.CSM_CONNECTED_LOAD = billdetailModel.CSM_CONNECTED_LOAD;
        this.nkt = billdetailModel.nkt;
        this.previousMeterStartRead = billdetailModel.previousMeterStartRead;
        this.currentMeterStartRead = billdetailModel.currentMeterStartRead;
        this.previousMeterEndRead = billdetailModel.previousMeterEndRead;
        this.currentMeterEndRead = billdetailModel.currentMeterEndRead;
        this.ENGCHG = billdetailModel.ENGCHG;
        this.FIXEDCHG = billdetailModel.FIXEDCHG;
        this.CUSCHG = billdetailModel.CUSCHG;
        this.EDCHG = billdetailModel.EDCHG;
        this.BILLAMT = billdetailModel.BILLAMT;
        this.LORG = billdetailModel.LORG;
        this.BILLDATE = billdetailModel.BILLDATE;
        this.DUEDATE = billdetailModel.DUEDATE;
        this.DISCDATE = billdetailModel.DISCDATE;
        this.LASTMCONSUPTION = billdetailModel.LASTMCONSUPTION;
        this.NEWARREARS = billdetailModel.NEWARREARS;
        this.CSM_AADHAAR_NO = billdetailModel.CSM_AADHAAR_NO;
        this.CSM_CASTE = billdetailModel.CSM_CASTE;
        this.CSM_PHONE_NO = billdetailModel.CSM_PHONE_NO;
        this.CSM_SHARE = billdetailModel.CSM_SHARE;
        this.MD = billdetailModel.MD;
        this.ERO = billdetailModel.ERO;
    }

    public GetBillDetailsModel() {

    }

    public String getMD() {
        return MD;
    }

    public void setMD(String MD) {
        this.MD = MD;
    }

    public String getERO() {
        return ERO;
    }

    public void setERO(String ERO) {
        this.ERO = ERO;
    }

    public String getNEWARREARS() {
        return NEWARREARS;
    }

    public void setNEWARREARS(String NEWARREARS) {
        this.NEWARREARS = NEWARREARS;
    }

    public String getCSM_AADHAAR_NO() {
        return CSM_AADHAAR_NO;
    }

    public void setCSM_AADHAAR_NO(String CSM_AADHAAR_NO) {
        this.CSM_AADHAAR_NO = CSM_AADHAAR_NO;
    }

    public String getCSM_CASTE() {
        return CSM_CASTE;
    }

    public void setCSM_CASTE(String CSM_CASTE) {
        this.CSM_CASTE = CSM_CASTE;
    }

    public String getCSM_PHONE_NO() {
        return CSM_PHONE_NO;
    }

    public void setCSM_PHONE_NO(String CSM_PHONE_NO) {
        this.CSM_PHONE_NO = CSM_PHONE_NO;
    }

    public String getCSM_SHARE() {
        return CSM_SHARE;
    }

    public void setCSM_SHARE(String CSM_SHARE) {
        this.CSM_SHARE = CSM_SHARE;
    }

    public String getLASTMCONSUPTION() {
        return LASTMCONSUPTION;
    }

    public void getLASTMCONSUPTION(String LASTMCONSUPTION) {
        this.LASTMCONSUPTION = LASTMCONSUPTION;
    }



    public String getDISCDATE() {
        return DISCDATE;
    }

    public void setDISCDATE(String DISCDATE) {
        this.DISCDATE = DISCDATE;
    }


    public String getDUEDATE() {
        return DUEDATE;
    }

    public void setDUEDATE(String DUEDATE) {
        this.DUEDATE = DUEDATE;
    }



    public String getBILLDATE() {
        return BILLDATE;
    }

    public void setBILLDATE(String BILLDATE) {
        this.BILLDATE = BILLDATE;
    }

    public String getCSM_METER_NO() {
        return CSM_METER_NO;
    }

    public void setCSM_METER_NO(String CSM_METER_NO) {
        this.CSM_METER_NO = CSM_METER_NO;
    }

    private String CSM_METER_NO;

    public String getBurntValue() {
        return BurntValue;
    }

    public void setBurntValue(String burntValue) {
        BurntValue = burntValue;
    }

    private String BurntValue;


    public String getPREAVG() {
        return PREAVG;
    }

    public void setPREAVG(String PREAVG) {
        this.PREAVG = PREAVG;
    }

    private String PREAVG;

    public String getLORG() {
        return LORG;
    }

    public void setLORG(String LORG) {
        this.LORG = LORG;
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAREACODE() {
        return AREACODE;
    }

    public void setAREACODE(String AREACODE) {
        this.AREACODE = AREACODE;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getSCNO() {
        return SCNO;
    }

    public void setSCNO(String SCNO) {
        this.SCNO = SCNO;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public String getSubcategory() {
        return Subcategory;
    }

    public void setSubcategory(String Subcategory) {
        this.Subcategory = Subcategory;
    }

    public String getSlbamoth() {
        return Slbamoth;
    }

    public void setSlbamoth(String Slbamoth) {
        this.Slbamoth = Slbamoth;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getOpen_bal() {
        return open_bal;
    }

    public void setOpen_bal(String open_bal) {
        this.open_bal = open_bal;
    }

    public String getClose_bal() {
        return close_bal;
    }

    public void setClose_bal(String close_bal) {
        this.close_bal = close_bal;
    }

    public String getDj() {
        return dj;
    }

    public void setDj(String dj) {
        this.dj = dj;
    }

    public String getAdj_amount() {
        return adj_amount;
    }

    public void setAdj_amount(String adj_amount) {
        this.adj_amount = adj_amount;
    }

    public String getPHASE() {
        return PHASE;
    }

    public void setPHASE(String PHASE) {
        this.PHASE = PHASE;
    }

    public String getOpmonth() {
        return opmonth;
    }

    public void setOpmonth(String opmonth) {
        this.opmonth = opmonth;
    }

    public String getOpstatus() {
        return opstatus;
    }

    public void setOpstatus(String opstatus) {
        this.opstatus = opstatus;
    }

    public String getOPRDNG() {
        return OPRDNG;
    }

    public void setOPRDNG(String OPRDNG) {
        this.OPRDNG = OPRDNG;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getUNITS() {
        return UNITS;
    }

    public void setUNITS(String UNITS) {
        this.UNITS = UNITS;
    }

    public String getDATEDIS() {
        return DATEDIS;
    }

    public void setDATEDIS(String DATEDIS) {
        this.DATEDIS = DATEDIS;
    }

    public String getMETERCY() {
        return METERCY;
    }

    public void setMETERCY(String METERCY) {
        this.METERCY = METERCY;
    }

    public String getEDINT() {
        return EDINT;
    }

    public void setEDINT(String EDINT) {
        this.EDINT = EDINT;
    }

    public String getCLEDUTY() {
        return CLEDUTY;
    }

    public void setCLEDUTY(String CLEDUTY) {
        this.CLEDUTY = CLEDUTY;
    }

    public String getCELEDINT() {
        return CELEDINT;
    }

    public void setCELEDINT(String CELEDINT) {
        this.CELEDINT = CELEDINT;
    }

    public String getOPEDINT() {
        return OPEDINT;
    }

    public void setOPEDINT(String OPEDINT) {
        this.OPEDINT = OPEDINT;
    }

    public String getCAPCHRGS() {
        return CAPCHRGS;
    }

    public void setCAPCHRGS(String CAPCHRGS) {
        this.CAPCHRGS = CAPCHRGS;
    }

    public String getSURCHRGS() {
        return SURCHRGS;
    }

    public void setSURCHRGS(String SURCHRGS) {
        this.SURCHRGS = SURCHRGS;
    }

    public String getCSSURCHG() {
        return CSSURCHG;
    }

    public void setCSSURCHG(String CSSURCHG) {
        this.CSSURCHG = CSSURCHG;
    }

    public String getOSSURCHG() {
        return OSSURCHG;
    }

    public void setOSSURCHG(String OSSURCHG) {
        this.OSSURCHG = OSSURCHG;
    }

    public String getOPFSA() {
        return OPFSA;
    }

    public void setOPFSA(String OPFSA) {
        this.OPFSA = OPFSA;
    }

    public String getCLFSA() {
        return CLFSA;
    }

    public void setCLFSA(String CLFSA) {
        this.CLFSA = CLFSA;
    }

    public String getCL2() {
        return CL2;
    }

    public void setCL2(String CL2) {
        this.CL2 = CL2;
    }

    public String getCL3() {
        return CL3;
    }

    public void setCL3(String CL3) {
        this.CL3 = CL3;
    }

    public String getCL4() {
        return CL4;
    }

    public void setCL4(String CL4) {
        this.CL4 = CL4;
    }

    public String getCL5() {
        return CL5;
    }

    public void setCL5(String CL5) {
        this.CL5 = CL5;
    }

    public String getCL6() {
        return CL6;
    }

    public void setCL6(String CL6) {
        this.CL6 = CL6;
    }

    public String getLRPNO() {
        return LRPNO;
    }

    public void setLRPNO(String LRPNO) {
        this.LRPNO = LRPNO;
    }

    public String getLPDATE() {
        return LPDATE;
    }

    public void setLPDATE(String LPDATE) {
        this.LPDATE = LPDATE;
    }

    public String getLPAMT() {
        return LPAMT;
    }

    public void setLPAMT(String LPAMT) {
        this.LPAMT = LPAMT;
    }

    public String getPOINTS() {
        return POINTS;
    }

    public void setPOINTS(String POINTS) {
        this.POINTS = POINTS;
    }

    public String getCAPFLAG() {
        return CAPFLAG;
    }

    public void setCAPFLAG(String CAPFLAG) {
        this.CAPFLAG = CAPFLAG;
    }

    public String getCAPSURCH() {
        return CAPSURCH;
    }

    public void setCAPSURCH(String CAPSURCH) {
        this.CAPSURCH = CAPSURCH;
    }

    public String getSBSFLAG() {
        return SBSFLAG;
    }

    public void setSBSFLAG(String SBSFLAG) {
        this.SBSFLAG = SBSFLAG;
    }

    public String getOPDEMAN() {
        return OPDEMAN;
    }

    public void setOPDEMAN(String OPDEMAN) {
        this.OPDEMAN = OPDEMAN;
    }

    public String getCLDEMAN() {
        return CLDEMAN;
    }

    public void setCLDEMAN(String CLDEMAN) {
        this.CLDEMAN = CLDEMAN;
    }

    public String getCLRDNG() {
        return CLRDNG;
    }

    public void setCLRDNG(String CLRDNG) {
        this.CLRDNG = CLRDNG;
    }

    public String getCLSTATUS() {
        return CLSTATUS;
    }

    public void setCLSTATUS(String CLSTATUS) {
        this.CLSTATUS = CLSTATUS;
    }

    public String getCSM_CONSUMER_NAME() {
        return CSM_CONSUMER_NAME;
    }

    public void setCSM_CONSUMER_NAME(String CSM_CONSUMER_NAME) {
        this.CSM_CONSUMER_NAME = CSM_CONSUMER_NAME;
    }

    public String getCSM_ADDRESS3() {
        return CSM_ADDRESS3;
    }

    public void setCSM_ADDRESS3(String CSM_ADDRESS3) {
        this.CSM_ADDRESS3 = CSM_ADDRESS3;
    }

    public String getCSM_CONNECTED_LOAD() {
        return CSM_CONNECTED_LOAD;
    }

    public void setCSM_CONNECTED_LOAD(String CSM_CONNECTED_LOAD) {
        this.CSM_CONNECTED_LOAD = CSM_CONNECTED_LOAD;
    }

    public String getNkt() {
        return nkt;
    }

    public void setNkt(String nkt) {
        this.nkt = nkt;
    }

    public String getPreviousMeterStartRead() {
        return previousMeterStartRead;
    }

    public void setPreviousMeterStartRead(String previousMeterStartRead) {
        this.previousMeterStartRead = previousMeterStartRead;
    }

    public String getPreviousMeterEndRead() {
        return previousMeterEndRead;
    }

    public void setPreviousMeterEndRead(String previousMeterEndRead) {
        this.previousMeterEndRead = previousMeterEndRead;
    }

    public String getCurrentMeterStartRead() {
        return currentMeterStartRead;
    }

    public void setCurrentMeterStartRead(String currentMeterStartRead) {
        this.currentMeterStartRead = currentMeterStartRead;
    }

    public String getCurrentMeterEndRead() {
        return currentMeterEndRead;
    }

    public void setCurrentMeterEndRead(String currentMeterEndRead) {
        this.currentMeterEndRead = currentMeterEndRead;
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

    public String getBILLAMT() {
        return BILLAMT;
    }

    public void setBILLAMT(String BILLAMT) {
        this.BILLAMT = BILLAMT;
    }

//    added by dileep
    public void setLASTMCONSUPTION (String currentReading1) {
        this.currentMeterEndRead = BILLAMT;
    }
}
