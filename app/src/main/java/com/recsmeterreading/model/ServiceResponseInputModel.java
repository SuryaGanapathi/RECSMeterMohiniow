package com.recsmeterreading.model;

import java.util.List;

/**
 * Created by HP on 20-04-2018.
 */

public class ServiceResponseInputModel {

    public String getTotal_records() {
        return total_records;
    }

    public void setTotal_records(String total_records) {
        this.total_records = total_records;
    }

    String total_records;


    private List<DetailsBean> details;

    public List<DetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsBean> details) {
        this.details = details;
    }

    public static class DetailsBean {
        /**
         * MONTH : 618
         * AREACODE : 20209
         * SCNO : 20209 00663
         * CATEGORY : 1
         * SLABAMT : 0
         * GROUPID : 0
         * OPEN_BAL : 0
         * CLBAL : 0
         * DL : 0
         * ADJAMT : 0
         * PHASE : 1
         * OPSTATUS : 1
         * OPMONTH : May-18
         * OPRDNG : 6387
         * STATUS : 0
         * PREAVG : 138
         * CURRUNITS : 138
         * DATEDIS :
         * METERCYCLE : 0
         * EDINT : 0
         * CLEDUTY : 8
         * CLEDINT : 0
         * OPEDUTY : 0
         * OPEDINT : 0
         * CAPCHRGS : 0
         * SURCHRGS : 0
         * CSSURCHG : 0
         * OSSURCHG : 0
         * OPFSA : 0
         * CLFSA : 0
         * CL2 : 0
         * CL3 : 0
         * CL4 : 0
         * CL5 : 0
         * CL6 : 0
         * LPRNO : 2147483647
         * LPDATE : 26-Jun-18
         * LPAMT : 455
         * POINTS :
         * CAPFLAG : 0
         * CAPSURCHG : 0
         * SBSFLAG : 0
         * OPDEMAND : 0
         * CLDEMAND : 455
         * SUBCATEGORY : 1
         * CLRDNG : 6525
         * CLSTATUS : 1
         * CSM_CONSUMER_NAME :
         * CSM_ADDRESS3 :
         * CSM_CONNECTED_LOAD : 0
         */

        private String MONTH;
        private String AREACODE;
        private String SCNO;
        private String CATEGORY;
        private String SLABAMT;
        private String GROUPID;
        private String OPEN_BAL;
        private String CLBAL;
        private String DL;
        private String ADJAMT;
        private String PHASE;
        private String OPSTATUS;
        private String OPMONTH;
        private String OPRDNG;
        private String STATUS;
        private String PREAVG;
        private String CURRUNITS;
        private String DATEDIS;
        private String METERCYCLE;
        private String EDINT;
        private String CLEDUTY;
        private String CLEDINT;
        private String OPEDUTY;
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
        private String LPRNO;
        private String LPDATE;
        private String LPAMT;
        private String POINTS;
        private String CAPFLAG;
        private String CAPSURCHG;
        private String SBSFLAG;
        private String OPDEMAND;
        private String CLDEMAND;
        private String SUBCATEGORY;
        private String CLRDNG;
        private String CLSTATUS;
        private String CSM_CONSUMER_NAME;
        private String CSM_ADDRESS3;
        private String CSM_CONNECTED_LOAD;
        private String CSM_METER_NO;
        private String NEWARREARS; //added by praful 5 below field
        private String CSM_AADHAAR_NO;
        private String CSM_CASTE;
        private String CSM_PHONE_NO;
        private String CSM_SHARE;

        private String MD;
        private String ERO;

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

        public String getCSM_METER_NO() {
            return CSM_METER_NO;
        }

        public void setCSM_METER_NO(String CSM_METER_NO) {
            this.CSM_METER_NO = CSM_METER_NO;
        }



        public String getMONTH() {
            return MONTH;
        }

        public void setMONTH(String MONTH) {
            this.MONTH = MONTH;
        }

        public String getAREACODE() {
            return AREACODE;
        }

        public void setAREACODE(String AREACODE) {
            this.AREACODE = AREACODE;
        }

        public String getSCNO() {
            return SCNO;
        }

        public void setSCNO(String SCNO) {
            this.SCNO = SCNO;
        }

        public String getCATEGORY() {
            return CATEGORY;
        }

        public void setCATEGORY(String CATEGORY) {
            this.CATEGORY = CATEGORY;
        }

        public String getSLABAMT() {
            return SLABAMT;
        }

        public void setSLABAMT(String SLABAMT) {
            this.SLABAMT = SLABAMT;
        }

        public String getGROUPID() {
            return GROUPID;
        }

        public void setGROUPID(String GROUPID) {
            this.GROUPID = GROUPID;
        }

        public String getOPEN_BAL() {
            return OPEN_BAL;
        }

        public void setOPEN_BAL(String OPEN_BAL) {
            this.OPEN_BAL = OPEN_BAL;
        }

        public String getCLBAL() {
            return CLBAL;
        }

        public void setCLBAL(String CLBAL) {
            this.CLBAL = CLBAL;
        }

        public String getDL() {
            return DL;
        }

        public void setDL(String DL) {
            this.DL = DL;
        }

        public String getADJAMT() {
            return ADJAMT;
        }

        public void setADJAMT(String ADJAMT) {
            this.ADJAMT = ADJAMT;
        }

        public String getPHASE() {
            return PHASE;
        }

        public void setPHASE(String PHASE) {
            this.PHASE = PHASE;
        }

        public String getOPSTATUS() {
            return OPSTATUS;
        }

        public void setOPSTATUS(String OPSTATUS) {
            this.OPSTATUS = OPSTATUS;
        }

        public String getOPMONTH() {
            return OPMONTH;
        }

        public void setOPMONTH(String OPMONTH) {
            this.OPMONTH = OPMONTH;
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

        public String getPREAVG() {
            return PREAVG;
        }

        public void setPREAVG(String PREAVG) {
            this.PREAVG = PREAVG;
        }

        public String getCURRUNITS() {
            return CURRUNITS;
        }

        public void setCURRUNITS(String CURRUNITS) {
            this.CURRUNITS = CURRUNITS;
        }

        public String getDATEDIS() {
            return DATEDIS;
        }

        public void setDATEDIS(String DATEDIS) {
            this.DATEDIS = DATEDIS;
        }

        public String getMETERCYCLE() {
            return METERCYCLE;
        }

        public void setMETERCYCLE(String METERCYCLE) {
            this.METERCYCLE = METERCYCLE;
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

        public String getCLEDINT() {
            return CLEDINT;
        }

        public void setCLEDINT(String CLEDINT) {
            this.CLEDINT = CLEDINT;
        }

        public String getOPEDUTY() {
            return OPEDUTY;
        }

        public void setOPEDUTY(String OPEDUTY) {
            this.OPEDUTY = OPEDUTY;
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

        public String getLPRNO() {
            return LPRNO;
        }

        public void setLPRNO(String LPRNO) {
            this.LPRNO = LPRNO;
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

        public String getCAPSURCHG() {
            return CAPSURCHG;
        }

        public void setCAPSURCHG(String CAPSURCHG) {
            this.CAPSURCHG = CAPSURCHG;
        }

        public String getSBSFLAG() {
            return SBSFLAG;
        }

        public void setSBSFLAG(String SBSFLAG) {
            this.SBSFLAG = SBSFLAG;
        }

        public String getOPDEMAND() {
            return OPDEMAND;
        }

        public void setOPDEMAND(String OPDEMAND) {
            this.OPDEMAND = OPDEMAND;
        }

        public String getCLDEMAND() {
            return CLDEMAND;
        }

        public void setCLDEMAND(String CLDEMAND) {
            this.CLDEMAND = CLDEMAND;
        }

        public String getSUBCATEGORY() {
            return SUBCATEGORY;
        }

        public void setSUBCATEGORY(String SUBCATEGORY) {
            this.SUBCATEGORY = SUBCATEGORY;
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
    }
}
