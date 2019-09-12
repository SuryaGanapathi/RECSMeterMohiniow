package com.recsmeterreading.Utils;

import android.util.Log;

import com.recsmeterreading.model.GetBillDetailsModel;
import com.recsmeterreading.model.LocalTarrifModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StringConstants {

    private static final String LOG_TAG = "String_Constants";
    private static final int CATEGORY_100 = 100;
    private static final int CATEGORY_101 = 101;
    private static final int CATEGORY_102 = 102;
    private static final int CATEGORY_200 = 200;
    private static final int CATEGORY_201 = 201;
    private static final int CATEGORY_202 = 202;
    private static final int CATEGORY_203 = 203;
    private static final int CATEGORY_204 = 204;
    private static final int CATEGORY_205 = 205;
    private static final int CATEGORY_300 = 300;
    private static final int CATEGORY_301 = 301;
    private static final int CATEGORY_302 = 302;
    private static final int CATEGORY_303 = 303;
//    private static final int CATEGORY_304 = 304;
//    private static final int CATEGORY_305 = 305;
//    private static final int CATEGORY_306 = 306;
//    private static final int CATEGORY_307 = 307;
//    private static final int CATEGORY_308 = 308;
//    private static final int CATEGORY_400 = 400;
    private static final int CATEGORY_401 = 401;
    private static final int CATEGORY_402 = 402;
    private static final int CATEGORY_403 = 403;
    private static final int CATEGORY_404 = 404;
    private static final int CATEGORY_405 = 405;
    private static final int CATEGORY_406 = 406;
    private static final int CATEGORY_407 = 407;
    private static final int CATEGORY_408 = 408;
    private static final int CATEGORY_501 = 501;
    private static final int CATEGORY_502 = 502;
    private static final int CATEGORY_503 = 503;
    private static final int CATEGORY_504 = 504;
    private static final int CATEGORY_505 = 505;
    private static final int CATEGORY_506 = 506;
    private static final int CATEGORY_507 = 507;
//    private static final int CATEGORY_508 = 508;
//    private static final int CATEGORY_509 = 509;
    private static final int CATEGORY_601 = 601;
    private static final int CATEGORY_602 = 602;
    private static final int CATEGORY_603 = 603;
    private static final int CATEGORY_604 = 604;
    private static final int CATEGORY_605 = 605;
//    private static final int CATEGORY_700 = 700;
    private static final int CATEGORY_701 = 701;
    private static final int CATEGORY_702 = 702;
    private static final int CATEGORY_800 = 800;

    private static final int UNITS_0 = 0;
    private static final int UNITS_50 = 50;
    private static final int UNITS_51 = 51;
    private static final int UNITS_100 = 100;
    private static final int UNITS_101 = 101;
    private static final int UNITS_200 = 200;
    private static final int UNITS_201 = 201;
    private static final int UNITS_300 = 300;
    private static final int UNITS_301 = 301;
    private static final int UNITS_400 = 400;
    private static final int UNITS_401 = 401;
    private static final int UNITS_500 = 500;

    private static final int CUSTOMER_CHARGES_25 = 25;
    private static final int CUSTOMER_CHARGES_30 = 30;
    private static final int CUSTOMER_CHARGES_35 = 35;
    private static final int CUSTOMER_CHARGES_40 = 40;
    private static final int CUSTOMER_CHARGES_45 = 45;
    private static final int CUSTOMER_CHARGES_50 = 50;
    private static final int CUSTOMER_CHARGES_55 = 55;
    private static final int CUSTOMER_CHARGES_63 = 63;
    private static final int CUSTOMER_CHARGES_250 = 250;
    private static final int CUSTOMER_CHARGES_500 = 500;
    private static final int CUSTOMER_CHARGES_938 = 938;
    private static final int CUSTOMER_CHARGES_1406 = 1406;
    private static GetBillDetailsModel billdetailModel;


    public static GetBillDetailsModel CALICULATE_BILL(GetBillDetailsModel billdetailModel,
                                                      String curRead, LocalTarrifModel mResponseJsondata, int status, Double peakLoad) throws ParseException {

        Log.e(LOG_TAG, " units 11111   " + curRead + " ");
        Log.e(LOG_TAG, " units 11111 " + billdetailModel.getCLRDNG() + " ");
        int units = 0;
        int currentReadUnits = 0;
        float surCharge = 0;
        boolean eDuty30678 = false;
                            //changed on 5th march 2019 status code 2 to be calculated as status code 1
        if ((status == 3) || (status == 2) || (status == 5) || (status == 6) || (status == 9) || (status == 8)) {
            currentReadUnits = Integer.parseInt(curRead);
            units = currentReadUnits;
        }else if(status == 11 || status == 4){ //praful
            units = Integer.parseInt(curRead);
        }
        else {
            currentReadUnits = Integer.parseInt(curRead) - Integer.parseInt(billdetailModel.getCLRDNG());
            units = currentReadUnits;
        }

        if (status == 7) {
            currentReadUnits = Integer.parseInt(curRead);
            units = currentReadUnits;
        }



        int remainingUnits;
        float energyCharges = 0;
        float electricalDuty = 0;
        int nkt = 5;
        int nktmd = 0;
        if(!billdetailModel.getMD().equals("")){
            nktmd = Integer.parseInt(billdetailModel.getMD());
        }

        int customerCharges = 0;
        float amount = 0;
        float fixedCharges = 0.0f;


        int burnt_charges = 0;
        //praful
        if (status == 11) {
            burnt_charges = Integer.parseInt(billdetailModel.getBurntValue());
        }

        float load = Float.parseFloat(billdetailModel.getCSM_CONNECTED_LOAD());
        int phase = Integer.parseInt(billdetailModel.getPHASE());
        int category = Integer.parseInt(billdetailModel.getCategory() + "0" + billdetailModel.getSubcategory());

        Log.e(LOG_TAG, " " + category + " ");
        Log.e(LOG_TAG, " units " + units + " ");
        remainingUnits = units;

        units = (int) (units+peakLoad);

        int unitEduty30678 = units;

        billdetailModel.setUNITS(String.valueOf(units));

        billdetailModel.setOpstatus(String.valueOf(status));

        if ((status == 3) || (status == 9)) {
            if (phase == 1) {
                if (category == CATEGORY_100) {
                    nkt = 5;
                    fixedCharges = 0;
                    electricalDuty = 0;
                    customerCharges = CUSTOMER_CHARGES_25;
                    if (load <= 0.5) {
                        energyCharges = 25;
                    } else if (load > 0.5) {
                        energyCharges = 50;
                    }

                } else if (category == CATEGORY_101) {
                    nkt = 5;
                    fixedCharges = 0;
                    electricalDuty = 0;
                    customerCharges = CUSTOMER_CHARGES_35;
                    if (load <= 0.5) {
                        energyCharges = 25;
                    } else if (load > 0.5) {
                        energyCharges = 50;
                    }

                } else if (category == CATEGORY_102) {
                    nkt = 5;
                    fixedCharges = 0;
                    electricalDuty = 0;
                    customerCharges = CUSTOMER_CHARGES_25;
                    if (load <= 0.5) {
                        energyCharges = 25;
                    } else if (load > 0.5) {
                        energyCharges = 50;
                    }

                } else if (category == CATEGORY_200) {
                    nkt = 0;
                    energyCharges = 65;
                    fixedCharges = load * 55;
                    electricalDuty = 0;
                    customerCharges = CUSTOMER_CHARGES_30;
                } else if (category == CATEGORY_201) {
                    nkt = 0;
                    energyCharges = 65;
                    fixedCharges = load * 55;  //before fixedCharges = (float) (load * 75);
                    electricalDuty = 0;
                    customerCharges = CUSTOMER_CHARGES_30;
                }  else if(category == 407){  //added on 25/04/2019
                    nkt = 0;
                    energyCharges = 50;
                    fixedCharges = load * 30;
                    electricalDuty = 0;
                    customerCharges = CUSTOMER_CHARGES_30;
                }

            }
            if (phase == 3) {
                if (category == CATEGORY_100) {
                    nkt = 5;
                    fixedCharges = 0;
                    electricalDuty = 0;
                    customerCharges = CUSTOMER_CHARGES_25;
                    energyCharges = 150;
                } else if (category == CATEGORY_101) {
                    nkt = 5;
                    fixedCharges = 0;
                    electricalDuty = 0;
                    customerCharges = CUSTOMER_CHARGES_25;
                    energyCharges = 150;
                } else if (category == CATEGORY_102) {
                    nkt = 5;
                    fixedCharges = 0;
                    electricalDuty = 0;
                    customerCharges = CUSTOMER_CHARGES_25;
                    energyCharges = 150;
                } else if (category == CATEGORY_200) {
                    nkt = 0;
                    energyCharges = 200;
                    fixedCharges = load * 55;
                    electricalDuty = 0;
                    customerCharges = CUSTOMER_CHARGES_30;
                } else if (category == CATEGORY_201) {
                    nkt = 0;
                    energyCharges = 200;
                    fixedCharges = load * 75;
                    electricalDuty = 0;
                    customerCharges = CUSTOMER_CHARGES_30;
                }  else if(category == 407){
                    nkt = 0;
                    energyCharges = 150;
                    fixedCharges = load * 30;
                    electricalDuty = 0;
                    customerCharges = CUSTOMER_CHARGES_30;
                }
            }
            if (phase == 1 || phase == 3) {
                if (category == CATEGORY_202) {
                    nkt = 0;
                    energyCharges = 300;
                    fixedCharges = load * 75;
                    electricalDuty = 0;
                    customerCharges = CUSTOMER_CHARGES_50;
                } else if (category == CATEGORY_203) {
                    nkt = 0;
                    energyCharges = 0;
                    fixedCharges = 0;
                    electricalDuty = 0;
                    customerCharges = CUSTOMER_CHARGES_50;
                }else if (category == CATEGORY_204) {
                    nkt = 0;
                    energyCharges = 0;
                    fixedCharges = 0;
                    electricalDuty = 0;
                    customerCharges = CUSTOMER_CHARGES_250; //previously customerCharges = CUSTOMER_CHARGES_50; changed on 25/04/2019
                } else if (category == CATEGORY_300) {
                    nkt = 0;
                    energyCharges = 0;
                    fixedCharges = load * 75 * 0.75f; // previously fixedCharges = (float) (load * 75 * 0.75); // changed 25/04/19
                    electricalDuty = 0;
                    if (load <= 20) {
                        customerCharges = 63;
                    } else if (load > 20 && load <= 50) {
                        customerCharges = 250;
                    } else if (load >= 51 && load <= 100) {
                        customerCharges = 938;
                    }
                } else if (category == CATEGORY_301) {
                    nkt = 0;
                    energyCharges = 0;
                    fixedCharges = load * 20 * 0.75f; // previously fixedCharges = (float) (load * 30 * 0.75); changed on 25/04/2019
                    electricalDuty = 0;
                    if (load <= 20) {
                        customerCharges = 63;
                    } else if (load > 20 && load <= 50) {
                        customerCharges = 250;
                    } else if (load >= 51 && load >= 100) {
                        customerCharges = 938;
                    }
                } else if (category == CATEGORY_302) {
                    nkt = 0;
                    energyCharges = 0;
                    fixedCharges = load * 475 * 0.75f;   //fixedCharges = (float) (load * 30 * 0.75);
                    electricalDuty = 0;
                    if (load <= 20) {
                        customerCharges = 63;
                    } else if (load > 20 && load <= 50) {
                        customerCharges = 250;
                    } else if (load >= 51 && load >= 100) {
                        customerCharges = 938;
                    } else if(load > 100){     //added on 25/04/2019
                        customerCharges = 1406;
                    }
                }  else if (category == CATEGORY_401) {
                    nkt = 0;
                    energyCharges = 0;
                    fixedCharges = load * 75 ;  //(float) (load * 20 * 0.75);
                    electricalDuty = 0;
                    customerCharges = CUSTOMER_CHARGES_35;  //30
                } else if (category == CATEGORY_402) {
                    nkt = 0;
                    energyCharges = 0;
                    fixedCharges = load * 75 ;
                    electricalDuty = 0;
                    customerCharges = CUSTOMER_CHARGES_35;

                }else if (category == CATEGORY_403) {
                    nkt = 0;
                    energyCharges = 0;
                    fixedCharges = load * 75 * 0.75f;
                    electricalDuty = 0;
                    customerCharges = CUSTOMER_CHARGES_35;

                } else if (category == CATEGORY_404) {
                    nkt = 0;
                    energyCharges = 0;
                    fixedCharges = load * 75 * 0.75f;
                    electricalDuty = 0;
                    customerCharges = CUSTOMER_CHARGES_35;

                } else if (category == CATEGORY_405) {
                    nkt = 0;
                    energyCharges = 0;
                    fixedCharges = load * 10 * 0.75f;
                    electricalDuty = 0;
                    customerCharges = CUSTOMER_CHARGES_30;

                }
                else if (category == CATEGORY_406) {
                    nkt = 0;
                    energyCharges = 0;
                    fixedCharges = load * 30;
                    electricalDuty = 0;
                    customerCharges = CUSTOMER_CHARGES_45;

                }
                else if (category == CATEGORY_408) {
                    nkt = 0;
                    energyCharges = 0;
                    fixedCharges = load * 30;
                    electricalDuty = 0;
                    customerCharges = CUSTOMER_CHARGES_30;

                }else if (category == CATEGORY_501) {
                    nkt = 5;
                    energyCharges = 0;
                    fixedCharges = 0;
                    electricalDuty = 0;
                    customerCharges = CUSTOMER_CHARGES_30;
                } else if (category == CATEGORY_502) {
                    nkt = 5;
                    energyCharges = 0;
//                    fixedCharges = (float) (load * 525);
                    fixedCharges = 0;
                    electricalDuty = 0;
                    customerCharges = CUSTOMER_CHARGES_30;
                } else if (category == CATEGORY_503) {
                    nkt = 5;
                    energyCharges = 0; //14/06/19
                    energyCharges = (float)(units*3.85);
//                    fixedCharges = 0; changed 25/04/2019
                    fixedCharges = (float) (load * 30 * 0.75);
                    electricalDuty = 0;
                    customerCharges = CUSTOMER_CHARGES_30;
                } else if (category == CATEGORY_504) {
                    nkt = 5;
                    energyCharges = 0;
//                    fixedCharges = 0; /changed 25/04/19
                    fixedCharges = (float) (load * 75 * 0.75);
                    electricalDuty = 0;
                    customerCharges = CUSTOMER_CHARGES_30;
                } else if (category == CATEGORY_505) {
                    nkt = 5;
                    energyCharges = 0;
//                    fixedCharges = (float) (load * 1050); changed 25/04/2019
                    fixedCharges = (float) (load * 20 * 0.75);
                    electricalDuty = 0;
                    customerCharges = CUSTOMER_CHARGES_30;
                } else if (category == CATEGORY_506) {
                    nkt = 5;
                    energyCharges = 0;
//                    fixedCharges = (float) (load * 1050); changed 25/04/2019
                    fixedCharges = (float) (load * 30 * 0.75);
                    electricalDuty = 0;
//                    customerCharges = CUSTOMER_CHARGES_30; // changed 25/04/19
                    customerCharges = CUSTOMER_CHARGES_1406;
                } else if (category == CATEGORY_507) {
                    nkt = 5;
                    energyCharges = 0;
//                    fixedCharges = (float) (load * 20); //25/04/19
                    fixedCharges = (float) (load * 475 * 0.75);
                    electricalDuty = 0;
//                    customerCharges = CUSTOMER_CHARGES_30; // changed 25/04/19
                    customerCharges = CUSTOMER_CHARGES_1406;
                }  else if (category == CATEGORY_800) {
                    nkt = 0;
                    energyCharges = 200;
                    fixedCharges = load * 30;
                    electricalDuty = 0;
                    customerCharges = CUSTOMER_CHARGES_50;
                }
                // to be  added here
            }

            //surcharge to be counted if NewArrears / CLBAL > 0 for the below category
            if(billdetailModel.getLPDATE() != null){
                if(Integer.parseInt(billdetailModel.getClose_bal()) > 0
                        && !paidOnTime(billdetailModel.getLPDATE(),billdetailModel.getERO())){
//            if(Integer.parseInt(billdetailModel.getCLDEMAN()) > 0){


                    int dat = 0;
                    dat = getdata(billdetailModel.getLPDATE(),billdetailModel.getERO());

                  //  if(dat == 2) {
                    String ero = billdetailModel.getERO();
                    if(ero.equals("009") || ero.equals("9")) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
                        String sd = "05-Jun-19";
                        Date startDate = dateFormat.parse(sd);
                        String ed = "30-Jun-19";
                        Date dueDate = dateFormat.parse(ed);
                        String lpdate = billdetailModel.getLPDATE();
                        // String str_lpdate = billdetailModel.getLPDATE();
                        Date lpdate1 = dateFormat.parse(lpdate);
                        if (startDate.compareTo(lpdate1) < 0 || lpdate1.compareTo(dueDate) < 0) {
                            if (billdetailModel.getClose_bal().equals( billdetailModel.getLPAMT())) {
                                // arrears.setText("0");
                                billdetailModel.setClose_bal("0");
                            } else if (Integer.parseInt(billdetailModel.getClose_bal()) > Integer.parseInt(billdetailModel.getLPAMT())) {
                                int amt =  Integer.parseInt(billdetailModel.getClose_bal()) - Integer.parseInt(billdetailModel.getLPAMT());
                                // arrears.setText(String.valueOf(amt));
                                billdetailModel.setClose_bal(String.valueOf(amt));
                            } else if (Integer.parseInt(billdetailModel.getClose_bal()) < Integer.parseInt(billdetailModel.getLPAMT())) {
                                int amt1 =  Integer.parseInt(billdetailModel.getClose_bal()) - Integer.parseInt(billdetailModel.getLPAMT());
                                // arrears.setText(String.valueOf(amt1));
                                billdetailModel.setClose_bal(String.valueOf(amt1));
                            }
                        }
                    }

                    int noOfDays = 0;
                    if(billdetailModel.getLPDATE() != null){
                        noOfDays = getNoOfDelayedDays(billdetailModel.getLPDATE(),billdetailModel.getERO());
                    }

                    if(noOfDays>0){
                        if(category == CATEGORY_100){
                            surCharge = 10;

                            // changed on 25/04/2019
                        }else if((category == CATEGORY_101) || (category == CATEGORY_102)
                                || (category == CATEGORY_301) || (category == CATEGORY_505)){
                            surCharge = 25;

                        }
                        // changed on 25/04/2019
                        else if((category == CATEGORY_501) || (category == CATEGORY_502)){
                            surCharge = 0;

                        }else if((category == CATEGORY_302) || (category == CATEGORY_506) || (category == CATEGORY_507) ){
                            surCharge = Math.round(noOfDays*Integer.parseInt(billdetailModel.getNEWARREARS())*0.0005);
                            if(surCharge<550){
                                surCharge = 550;
                            }

                        }else if((category == CATEGORY_800)){
                            surCharge = Math.round(noOfDays*Integer.parseInt(billdetailModel.getNEWARREARS())*0.0005);
                            if(surCharge<150){
                                surCharge = 150;
                            }
                        }else if(category == CATEGORY_200){
                            if(units<=50){
                                surCharge = 25;
                            }else {
                                if(billdetailModel.getLPDATE().length()>0){
//                            noOfDays = getNoOfDelayedDays(billdetailModel.getLPDATE());
                                    Log.e("no of days:",""+noOfDays);
                                    surCharge = Math.round(noOfDays*Integer.parseInt(billdetailModel.getNEWARREARS())*0.0005);
                                    if(surCharge<150){
                                        surCharge = 150;
                                    }
                                }
                            }
                        }
                        else{
                            if(billdetailModel.getLPDATE().length()>0){
//                            noOfDays = getNoOfDelayedDays(billdetailModel.getLPDATE());
                                Log.e("no of days:",""+noOfDays);
                                Log.e("Surcharge",""+surCharge);
                                surCharge = Math.round(noOfDays*Integer.parseInt(billdetailModel.getNEWARREARS())*0.0005);
                                Log.e("Surcharge",""+surCharge);
                                if(surCharge<150){
                                    surCharge = 150;
                                }
                            }

                        }
                    }


                }
            }


            billdetailModel.setSURCHRGS(String.valueOf(surCharge));
            Log.e("Surcharge",""+surCharge);
            Log.e("test 1",""+Integer.parseInt(billdetailModel.getClose_bal()));
//            amount = energyCharges + customerCharges + fixedCharges + electricalDuty + nkt+surCharge;
            amount = energyCharges + customerCharges + fixedCharges + electricalDuty + nktmd+surCharge;
            //arrears praful
            int arrers = 0;
            if(!billdetailModel.getClose_bal().equals("") || !billdetailModel.getClose_bal().isEmpty()
                    || !(billdetailModel.getClose_bal() == null)){
                Log.e("arrears", ""+arrers);
                arrers = Integer.parseInt(billdetailModel.getClose_bal());
            }
            Log.e("arrears", ""+arrers);
            amount = amount + arrers;

            billdetailModel.setEDCHG(String.valueOf(electricalDuty));
            billdetailModel.setCUSCHG(String.valueOf(customerCharges));
            billdetailModel.setENGCHG(String.valueOf(energyCharges));
            billdetailModel.setFIXEDCHG(String.valueOf(fixedCharges));
//            billdetailModel.setNkt(String.valueOf(nkt));
            billdetailModel.setNkt(String.valueOf(nktmd));
            billdetailModel.setLORG(String.valueOf(amount - Math.round(amount)));
            billdetailModel.setBILLAMT(String.valueOf(Math.round(amount)));
            billdetailModel.setOPRDNG(billdetailModel.getCLRDNG());
                //changed 5th march 2019 status code 2
            if (status == 2 || status == 5 || status == 6 || status == 8) {
                String oldValue = String.valueOf(Integer.parseInt(curRead) + Integer.parseInt(billdetailModel.getOPRDNG()));

                billdetailModel.setCLRDNG(oldValue);
                //praful2
            }else if(status == 3 || status == 9){
                billdetailModel.setCLRDNG(billdetailModel.getOPRDNG());
            }
            else if(status == 7){
                billdetailModel.setCLRDNG(billdetailModel.getOPRDNG());
            }
            else  {
                billdetailModel.setCLRDNG(curRead);
            }
            return billdetailModel;

        } else if (status == 1 || status == 4 || status == 17 ||
                status == 18 || status == 5 || status == 7
                || status == 2 || status == 6 || status == 8 || status == 10
                || status == 21 || status == 13 || status == 14 || status == 11) {

            if (phase == 1) {
                if (category == CATEGORY_100) {
                    nkt = 5;
                    if (units == UNITS_0) {
                        fixedCharges = 0;
                        electricalDuty = 0;
                        customerCharges = CUSTOMER_CHARGES_25;
                        if (load <= 0.5) {
                            energyCharges = 25;
                        } else if (load > 0.5) {
                            energyCharges = 50;
                        }

                    } else if (units > UNITS_0 && units <= 50) {
                        energyCharges = (float) (units * 1.45);
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_25;
                    } else if (units >= 51 && units <= 100) {
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_30;
                        remainingUnits = remainingUnits - UNITS_50;
                        energyCharges = (float) ((remainingUnits * 2.60) + (50 * 1.45));
                    } else if (units >= 101 && units <= 200) {
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_35;
                        remainingUnits = remainingUnits - 100;
                        energyCharges = (float) ((remainingUnits * 3.60) + (50 * 1.45) + (50 * 2.60));

                    } else {
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_40;
                        remainingUnits = remainingUnits - 200;
                        energyCharges = (float) ((remainingUnits * 6.90) + (100 * 3.60) + (50 * 2.60) + (50 * 1.45));
                    }
                } else if (category == CATEGORY_101) {
                    nkt = 5;
                    if (units == UNITS_0) {
                        fixedCharges = 0;
                        electricalDuty = 0;
                        customerCharges = CUSTOMER_CHARGES_35;
                        if (load <= 0.5) {
                            energyCharges = 25;
                        } else if (load > 0.5) {
                            energyCharges = 50;
                        }

                    } else if (units > UNITS_0 && units <= 50) {
                        energyCharges = (float) (units * 2.60);
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_35;
                    } else if (units >= 51 && units <= 100) {
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_40;
                        energyCharges = (float) ((units * 2.60));
                    } else if (units >= 101 && units <= 200) {
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_45;
                        remainingUnits = remainingUnits - 100;
                        energyCharges = (float) ((remainingUnits * 3.60) + (50 * 2.60) + (50 * 2.60));

                    } else if (units >= 201 && units <= 300) {
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_50;
                        remainingUnits = remainingUnits - 200;
                        energyCharges = (float) ((remainingUnits * 6.90) + (100 * 3.60) + (50 * 2.60) + (50 * 2.60));
                    } else {
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_55;
                        remainingUnits = remainingUnits - 300;
                        energyCharges = (float) ((remainingUnits * 7.75) + (100 * 6.90) + (100 * 3.60) + (50 * 2.60) + (50 * 2.60));
                    }
                } else if (category == CATEGORY_102) {
                    nkt = 5;
                    if (units == UNITS_0) {
                        fixedCharges = 0;
                        electricalDuty = 0;
                        customerCharges = CUSTOMER_CHARGES_25;
                        if (load <= 0.5) {
                            energyCharges = 25;
                        } else if (load > 0.5) {
                            energyCharges = 50;
                        }

                    } else if (units > UNITS_0 && units <= 50) {
                        energyCharges = (float) (units * 2.65); // (units * 2.68) changed on 25/04/2019
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_35;
                    } else if (units >= 51 && units <= 100) {
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_40;
                        remainingUnits = remainingUnits - 50;
                        energyCharges = (float) ((50 * 2.65) + (remainingUnits * 3.35)); // changed 2.68 to 2.65 on 25/04/2019
                    } else if (units >= 101 && units <= 200) {
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_45;
                        remainingUnits = remainingUnits - 100;
                        energyCharges = (float) ((remainingUnits * 5.40) + (50 * 3.35) + (50 * 2.65));  // changed 2.68 to 2.65 and 5.42 to 5.40 on 25/04/2019

                    } else if (units >= 201 && units <= 300) {
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_50;
                        remainingUnits = remainingUnits - 200;
                        // 7.12 to 7.10  changed 2.68 to 2.65 and 5.42 to 5.40 on 25/04/2019
                        energyCharges = (float) ((remainingUnits * 7.10) + (100 * 5.40) + (50 * 3.35) + (50 * 2.65));
                    } else if (units >= 301 && units <= 400) {
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_55;
                        remainingUnits = remainingUnits - 300;
                        //7.98 to 7.95 // 7.12 to 7.10  changed 2.68 to 2.65 and 5.42 to 5.40 on 25/04/2019
                        energyCharges = (float) ((remainingUnits * 7.95) + (100 * 7.10) + (100 * 5.40) + (50 * 3.35) + (50 * 2.65));
                    } else if (units >= 401 && units <= 500) {
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_55;
                        remainingUnits = remainingUnits - 400;
                        //changed on 25/04/2019
//                        energyCharges = (float) ((remainingUnits * 8.52) + (100 * 7.98) + (100 * 7.11) + (100 * 5.42) + (50 * 3.35) + (50 * 2.68));
                        energyCharges = (float) ((remainingUnits * 8.50) + (100 * 7.95) + (100 * 7.10) + (100 * 5.40) + (50 * 3.35) + (50 * 2.65));
                    } else {
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_55;
                        remainingUnits = remainingUnits - 500;
//                        energyCharges = (float) ((remainingUnits * 9.06) + (100 * 8.52) + (100 * 7.98) + (100 * 7.11) + (100 * 5.42) + (50 * 3.35) + (50 * 2.68));
                        energyCharges = (float) ((remainingUnits * 9.05) + (100 * 8.50) + (100 * 7.95) + (100 * 7.10) + (100 * 5.40) + (50 * 3.35) + (50 * 2.65));
                    }

                } else if (category == CATEGORY_200) {

                    if (units == 0) {
                        nkt = 0;
                        energyCharges = 65;
                        fixedCharges = load * 55;
                        customerCharges = CUSTOMER_CHARGES_30;
                        electricalDuty = 0;
                    } else if (units > 0 && units <= 50) {
                        nkt = 0;
                        energyCharges = (float) (units * 5.40); //before energyCharges = (float) (units * 6.90);
                        fixedCharges = load * 55;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_30;
                    } else if (units >= 51 && units <= 100) {
                        nkt = 0;
                        remainingUnits = units - 50;
//                        energyCharges = (float) ((remainingUnits * 7.69) + (50 * 6.90)); changed on 25/04/2019
                        energyCharges = (float) ((remainingUnits * 7.65) + (50 * 6.90));
                        fixedCharges = load * 75;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_40;
                    } else if (units >= 101 && units <= 300) {
                        nkt = 0;
                        remainingUnits = units - 100;
//                        energyCharges = (float) ((remainingUnits * 7.69) + (50 * 7.69) + (50 * 6.90));

                        energyCharges = (float) ((remainingUnits * 9.05) + (50 * 7.65) + (50 * 6.90));
                        fixedCharges = load * 75;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_45;
                    } else if (units >= 301 && units <= 500) {
                        nkt = 0;
                        remainingUnits = units - 300;
//                        energyCharges = (float) ((remainingUnits * 9.61) + (200 * 9.06) + (50 * 7.69) + (50 * 6.90)); // changed 250419
                        energyCharges = (float) ((remainingUnits * 9.60) + (200 * 9.05) + (50 * 7.65) + (50 * 6.90));
                        fixedCharges = load * 75;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_45;
                    } else if (units > 500) {
                        nkt = 0;
                        remainingUnits = units - 500;
                        energyCharges = (float) ((remainingUnits * 10.15) + (200 * 9.60) + (200 * 9.05) + (50 * 7.65) + (50 * 6.90));
                        fixedCharges = load * 75;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_45;
                    }
                }else if (category == CATEGORY_407) {
                    if (units == UNITS_0) {
                        nkt = 0;
                        energyCharges = 50;
                        electricalDuty = 0;
                        fixedCharges = load * 30;
                        customerCharges = CUSTOMER_CHARGES_30;
                    } else {
                        nkt = 0;
                        energyCharges = (float) (units * 4.80);
                        fixedCharges = load * 30;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_30;
                    }

                }
            }
            if (phase == 3) {
                if (category == CATEGORY_100) {
                    nkt = 5;
                    if (units == UNITS_0) {
                        fixedCharges = 0;
                        electricalDuty = 0;
                        customerCharges = CUSTOMER_CHARGES_25;
                        energyCharges = 150;

                    } else if (units > UNITS_0 && units <= 50) {
                        energyCharges = (float) (units * 1.45);
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_25;
                    } else if (units >= 51 && units <= 100) {
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_30;
                        remainingUnits = units - UNITS_50;
                        energyCharges = (float) ((remainingUnits * 2.60) + (50 * 1.45));
                    } else if (units >= 101 && units <= 200) {
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_35;
                        remainingUnits = units - 100;
                        energyCharges = (float) ((remainingUnits * 3.60) + (50 * 1.45) + (50 * 2.60));
                    } else {
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_40;
                        remainingUnits = units - 200;
                        energyCharges = (float) ((remainingUnits * 6.90) + (100 * 3.60) + (50 * 2.60) + (50 * 1.45));
                    }

                } else if (category == CATEGORY_101) {
                    nkt = 5;
                    if (units == UNITS_0) {
                        fixedCharges = 0;
                        electricalDuty = 0;
                        customerCharges = CUSTOMER_CHARGES_25;
                        energyCharges = 150;
                    } else if (units > UNITS_0 && units <= 50) {
                        energyCharges = (float) (units * 2.60);
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_35;
                    } else if (units >= 51 && units <= 100) {
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_40;
                        energyCharges = (float) (units * 2.60);
                    } else if (units >= 101 && units <= 200) {
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_45;
                        remainingUnits = units - 100;
                        energyCharges = (float) ((remainingUnits * 3.60) + (50 * 2.60) + (50 * 2.60));

                    } else if (units >= 201 && units <= 300) {
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_50;
                        remainingUnits = units - 200;
                        energyCharges = (float) ((remainingUnits * 6.90) + (100 * 3.60) + (50 * 2.60) + (50 * 2.60));
                    } else {
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_55;
                        remainingUnits = units - 300;
                        energyCharges = (float) ((remainingUnits * 7.75) + (100 * 6.90) + (100 * 3.60) + (50 * 2.60) + (50 * 2.60));

                    }
                } else if (category == CATEGORY_102) {
                    nkt = 5;
                    if (units == UNITS_0) {
                        fixedCharges = 0;
                        electricalDuty = 0;
                        customerCharges = CUSTOMER_CHARGES_25;
                        energyCharges = 150;

                    } else if (units > UNITS_0 && units <= 50) {
                        energyCharges = (float) (units * 2.65); // 2.68 to 2.65 on 25/04/2019
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_35;
                    } else if (units >= 51 && units <= 100) {
                        fixedCharges = 0;
                        remainingUnits = units - 50;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_40;
//                        energyCharges = (float) ((remainingUnits * 3.35) + (50 * 2.68)); //25/04/2019
                        energyCharges = (float) ((remainingUnits * 3.35) + (50 * 2.65));
                    } else if (units >= 101 && units <= 200) {
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_45;
                        remainingUnits = units - 100;
//                        energyCharges = (float) ((remainingUnits * 5.42) + (50 * 3.35) + (50 * 2.68)); // 25/042019
                        energyCharges = (float) ((remainingUnits * 5.40) + (50 * 3.35) + (50 * 2.65));

                    } else if (units >= 201 && units <= 300) {
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_50;
                        remainingUnits = units - 200;
//                        energyCharges = (float) ((remainingUnits * 7.11) + (100 * 5.42) + (50 * 3.35) + (50 * 2.68));
                        energyCharges = (float) ((remainingUnits * 7.10) + (100 * 5.40) + (50 * 3.35) + (50 * 2.65));
                    } else if (units >= 301 && units <= 400) {
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_55;
                        remainingUnits = units - 300;
//                        energyCharges = (float) ((remainingUnits * 7.98) + (100 * 7.11) + (100 * 5.42) + (50 * 3.35) + (50 * 2.68));
                        energyCharges = (float) ((remainingUnits * 7.95) + (100 * 7.10) + (100 * 5.40) + (50 * 3.35) + (50 * 2.65));

                    } else if (units >= 401 && units <= 500) {
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_55;
                        remainingUnits = remainingUnits - 400;
//                        energyCharges = (float) ((remainingUnits * 8.52) + (100 * 7.98) + (100 * 7.11) + (100 * 5.42) + (50 * 3.35) + (50 * 2.68));
                        energyCharges = (float) ((remainingUnits * 8.50) + (100 * 7.95) + (100 * 7.10) + (100 * 5.40) + (50 * 3.35) + (50 * 2.65));
                    } else {
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_55;
                        remainingUnits = remainingUnits - 500;
//                        energyCharges = (float) ((remainingUnits * 9.06) + (100 * 8.52) + (100 * 7.98) + (100 * 7.11) + (100 * 5.42) + (50 * 3.35) + (50 * 2.68));
                        energyCharges = (float) ((remainingUnits * 9.05) + (100 * 8.50) + (100 * 7.95) + (100 * 7.10) + (100 * 5.40) + (50 * 3.35) + (50 * 2.65));
                    }
                }
                if (category == CATEGORY_200) {
                    if (units == UNITS_0) {
                        nkt = 0;
                        energyCharges = 200;
                        fixedCharges = load * 55;
                        electricalDuty = 0;
                        customerCharges = CUSTOMER_CHARGES_30;
                    } else if (units > UNITS_0 && units <= UNITS_50) {
                        nkt = 0;
                        energyCharges = (float) (units * 5.40);  // energyCharges = (float) (units * 6.90);
                        fixedCharges = load * 55;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_30;
                    } else if (units >= 51 && units <= 100) {
                        nkt = 0;
                        remainingUnits = units - 50;
//                        energyCharges = (float) ((remainingUnits * 7.69) + (50 * 6.90));  // 25/04/2019
                        energyCharges = (float) ((remainingUnits * 7.65) + (50 * 6.90));
                        fixedCharges = load * 75;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_40;
                    } else if (units >= 101 && units <= 300) {
                        nkt = 0;
                        remainingUnits = units - 100;
//                        energyCharges = (float) ((remainingUnits * 7.69) + (50 * 7.69) + (50 * 6.90)); //  25/04/2019
                        energyCharges = (float) ((remainingUnits * 9.05) + (50 * 7.65) + (50 * 6.90));
                        fixedCharges = load * 75;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_45;
                    } else if (units >= 301 && units <= 500) {
                        nkt = 0;
                        remainingUnits = units - 300;
//                        energyCharges = (float) ((remainingUnits * 9.61) + (200 * 9.06) + (50 * 7.69) + (50 * 6.90)); 25/04/2019
                        energyCharges = (float) ((remainingUnits * 9.60) + (200 * 9.05) + (50 * 7.65) + (50 * 6.90));
                        fixedCharges = load * 75;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_45;
                    } else if (units > 500) {
                        nkt = 0;
                        remainingUnits = units - 500;
//                        energyCharges = (float) ((remainingUnits * 10.19) + (200 * 9.61) + (200 * 9.06) + (50 * 7.69) + (50 * 6.90));  // 25/04/2019
                        energyCharges = (float) ((remainingUnits * 10.15) + (200 * 9.60) + (200 * 9.05) + (50 * 7.65) + (50 * 6.90));
                        fixedCharges = load * 75;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_45;
                    }
                } else if (category == CATEGORY_201) {
                    if (units == UNITS_0) {
                        nkt = 0;
                        fixedCharges = load * 55;   //before fixedCharges = (float) (load * 75);
                        electricalDuty = 0;
                        customerCharges = CUSTOMER_CHARGES_30;
                        energyCharges = 200;

                    } else if (units > UNITS_0 && units <= 50) {
                        nkt = 0;
                        energyCharges = (float) (units * 5.40);  //before energyCharges = (float) (units * 6.90);
                        electricalDuty = (float) (units * 0.06);
                        fixedCharges = load * 55;    //before fixedCharges = (float) (load * 75);
                        customerCharges = CUSTOMER_CHARGES_30;
                    } else if (units >= 51 && units <= 100) {
                        nkt = 0;
                        electricalDuty = (float) (units * 0.06);
                        remainingUnits = units - 50;
                        fixedCharges = load * 75;
                        customerCharges = CUSTOMER_CHARGES_40;
                        energyCharges = (float) ((remainingUnits * 7.69) + (50 * 6.90));
                    } else if (units >= 101 && units <= 300) {
                        nkt = 0;
                        fixedCharges = load * 75;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_45;
                        remainingUnits = units - 100;
                        energyCharges = (float) ((remainingUnits * 9.06) + (50 * 7.69) + (50 * 6.90));

                    } else if (units >= 301 && units <= 500) {
                        nkt = 0;
                        fixedCharges = load * 75;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_45;
                        remainingUnits = units - 300;
                        energyCharges = (float) ((remainingUnits * 9.61) + (200 * 9.06) + (50 * 7.69) + (50 * 6.90));
                    } else if (units > 500) {
                        nkt = 0;
                        fixedCharges = load * 75;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_45;
                        remainingUnits = units - 500;
                        energyCharges = (float) ((remainingUnits * 10.19) + (200 * 9.61) + (200 * 9.06) + (50 * 7.69) + (50 * 6.90));
                    }
                }else if (category == CATEGORY_407) {
                    if (units == UNITS_0) {
                        nkt = 0;
                        energyCharges = 150;
                        electricalDuty = 0;
                        fixedCharges = load * 30;
                        customerCharges = CUSTOMER_CHARGES_30;
                    } else {
                        nkt = 0;
                        energyCharges = (float) (units * 4.80);
                        fixedCharges = load * 30;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_30;
                    }
                }

            }
            if (phase == 1 || phase == 3) {
                // Category 202 for both phases 1 & 3
                if (category == CATEGORY_202) {
                    if (units == UNITS_0) {
                        nkt = 0;
                        energyCharges = 300;
                        fixedCharges = load * 75;
                        electricalDuty = 0;
                        customerCharges = CUSTOMER_CHARGES_50;
                    } else {
                        nkt = 0;
                        energyCharges = (float) (units * 12.25);  // previously units * 12.28
                        fixedCharges = load * 75;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_50;
                    }
                }
                // Category 203 for both phases 1 & 3
                else if (category == CATEGORY_203) {
                    if (units == UNITS_0) {
                        nkt = 0;
                        energyCharges = 0;
                        fixedCharges = 0;
                        electricalDuty = 0;
                        customerCharges = CUSTOMER_CHARGES_50;
                    } else {
                        nkt = 0;
//                        energyCharges = (float) (units * 11.77); //25/04/2019
                        energyCharges = (float) (units * 11.75);
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_50;
                    }
                }

                else if (category == CATEGORY_204) {
                    if (units == UNITS_0) {
                        nkt = 0;
                        energyCharges = 0;
                        fixedCharges = 0;
                        electricalDuty = 0;
                        customerCharges = CUSTOMER_CHARGES_250; // prev 50 //25/04/2019
                    } else {
                        nkt = 0;
//                        energyCharges = (float) (units * 6.95);
                        energyCharges = (float) (units * 5.00);
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_250; // prev 50 //25/04/2019
                    }
                }

                //  for both phases 1 & 3
                else if (category == CATEGORY_300) {
                    if (units == 0) {
                        nkt = 0;
                        energyCharges = 0;
                        fixedCharges = load * 75; //previously fixedCharges = (float) (load * 75 * 0.75); // changed 06/05/19
                        electricalDuty = 0;
                        if (load <= 20) {
                            customerCharges = 63;
                        } else if (load > 20 && load <= 50) {
                            customerCharges = 250;
                        } else if (load > 50 && load <= 100) {
                            customerCharges = 938;
                        }
                    } else {
                        nkt = 0;
                        energyCharges = (float) (units * 6.70); //6.71
                        fixedCharges = load * 75;//fixedCharges = (float) (load * 75 * 0.75);
                        electricalDuty = (float) (units * 0.06);
                        if (load <= 20) {
                            customerCharges = 63;
                        } else if (load > 20 && load <= 50) {
                            customerCharges = 250;
                        } else if (load > 50 && load <= 100) {
                            customerCharges = 938;
                        }
                    }
                }

                // Category 301 for both phases 1 & 3
                else if (category == CATEGORY_301) {
                    if (units == 0) {
                        nkt = 0;
                        energyCharges = 0;
//                        fixedCharges = (float) (load * 30 * 0.75); //25/04/2019
                        fixedCharges = load * 20;
                        electricalDuty = 0;
                        if (load <= 20) {
                            customerCharges = 63;
                        } else if (load > 20 && load <= 50) {
                            customerCharges = 250;
                        } else if (load > 50 && load <= 100) {
                            customerCharges = 938;
                        }
                    } else {
                        nkt = 0;
//                        energyCharges = (float) (units * 3.86);
//                        fixedCharges = (float) (load * 30 * 0.75);  // 25/04/2019
//                        electricalDuty = (float) (units * 0.06);
                        energyCharges = (float) (units * 3.75);
                        fixedCharges = load * 20;
                        electricalDuty = (float) (units * 0.06);
                        if (load <= 20) {
                            customerCharges = 63;
                        } else if (load > 20 && load <= 50) {
                            customerCharges = 250;
                        } else if (load > 50 && load <= 100) {
                            customerCharges = 938;
                        }
                    }
                }
                // Category 302 for both phases 1 & 3
                else if (category == CATEGORY_302) {
                    if (units == UNITS_0) {
                        nkt = 0;
                        energyCharges = 0;
                        electricalDuty = 0;
//                        fixedCharges = (float) (load * 30 * .75); // 25/04/2019
                        fixedCharges = load * 475;
                        if (load <= 20) {
                            customerCharges = 63;
                        } else if (load > 20 && load <= 50) {
                            customerCharges = 250;
                        } else if (load > 50 && load <= 100) {
                            customerCharges = 938;
                        }//added new condition
                        else if (load > 100) {
                            customerCharges = 1406;
                        }
                    } else {
                        nkt = 0;
//                        energyCharges = (float) (units * 3.86);  // 30/04/2019
                        energyCharges = (float) (units * 6.30);
//                        fixedCharges = (float) (load * 30 * 0.75);  //30/04/2019
//                        fixedCharges = (float) (load * 30 * 0.75);
                        fixedCharges = load * 475;
                        electricalDuty = (float) (units * 0.06);
                        if (load <= 20) {
                            customerCharges = 63;
                        } else if (load > 20 && load <= 50) {
                            customerCharges = 250;
                        } else if (load > 50 && load <= 100) {
                            customerCharges = 938;
                        }//added new condition
                        else if (load > 100) {
                            customerCharges = 1406;
                        }
                    }
                }

                // Category 303 for both phases 1 & 3
                else if (category == CATEGORY_303 || category == 309) {
                    if (units == UNITS_0) {
                        nkt = 0;
                        energyCharges = 0;
                        electricalDuty = 0;
                        fixedCharges = (float) (load * 75 * 0.75);
                        if (load <= 20) {
                            customerCharges = 63;
                        } else if (load > 20 && load <= 50) {
                            customerCharges = 250;
                        } else if (load > 50 && load <= 100) {
                            customerCharges = 938;
                        }

                    } else {
                        nkt = 0;
                        energyCharges = (float) (units * 4.89);
                        fixedCharges = (float) (load * 75 * 0.75);
                        electricalDuty = (float) (units * 0.06);
                        if (load <= 20) {
                            customerCharges = 63;
                        } else if (load > 20 && load <= 50) {
                            customerCharges = 250;
                        } else if (load > 50 && load <= 100) {
                            customerCharges = 938;
                        }
                    }
                }

                // Category 401 for both phases 1 & 3
                else if (category == CATEGORY_401) {
                    if (units == UNITS_0) {
                        nkt = 0;
                        energyCharges = 0;
                        electricalDuty = 0;
                        fixedCharges = load * 75; //(float) (load * 20 * 0.75);
                        customerCharges = CUSTOMER_CHARGES_35;
                    } else {
                        nkt = 0;
                        energyCharges = (float) (units * 5.95); //3.75
                        fixedCharges = load * 75; //fixedCharges = (float) (load * 20 * 0.75);
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_35;
                    }
                }

                // added new category - Category 402 for both phases 1 & 3
                else if (category == CATEGORY_402) {
                    if (units == UNITS_0) {
                        nkt = 0;
                        energyCharges = 0;
                        electricalDuty = 0;
                        fixedCharges = load * 75;
                        customerCharges = CUSTOMER_CHARGES_35;
                    } else {
                        nkt = 0;
                        energyCharges = (float) (units * 7.05);
                        fixedCharges = load * 75;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_35;
                    }
                }
                // added new category - Category 403 for both phases 1 & 3
                else if (category == CATEGORY_403) {
                    if (units == UNITS_0) {
                        nkt = 0;
                        energyCharges = 0;
                        electricalDuty = 0;
                        fixedCharges = load * 75;
                        customerCharges = CUSTOMER_CHARGES_35;
                    } else {
                        nkt = 0;
                        energyCharges = (float) (units * 4.85);
                        fixedCharges = load * 75;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_35;
                    }
                }

                // added new category - Category 404 for both phases 1 & 3
                else if (category == CATEGORY_404) {
                    if (units == UNITS_0) {
                        nkt = 0;
                        energyCharges = 0;
                        electricalDuty = 0;
                        fixedCharges = load * 75;
                        customerCharges = CUSTOMER_CHARGES_35;
                    } else {
                        nkt = 0;
                        energyCharges = (float) (units * 6.50);
                        fixedCharges = load * 75;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_35;
                    }
                }
                // added new category - Category 405 for both phases 1 & 3
                else if (category == CATEGORY_405) {
                    if (units == UNITS_0) {
                        nkt = 0;
                        energyCharges = 0;
                        electricalDuty = 0;
                        fixedCharges = load * 10;
                        customerCharges = CUSTOMER_CHARGES_30;
                    } else {
                        nkt = 0;
                        energyCharges = (float) (units * 4.00);
                        fixedCharges = load * 10;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_30;
                    }
                }
                // added new category - Category 406 for both phases 1 & 3
                else if (category == CATEGORY_406) {
                    if (units == UNITS_0) {
                        nkt = 0;
                        energyCharges = 0;
                        electricalDuty = 0;
                        fixedCharges = load * 30;
                        customerCharges = CUSTOMER_CHARGES_45;
                    } else {
                        nkt = 0;
                        energyCharges = (float) (units * 7.25);
                        fixedCharges = load * 30;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_45;
                    }
                }

                // added new category - Category 408 for both phases 1 & 3
                else if (category == CATEGORY_408) {
                    if (units == UNITS_0) {
                        nkt = 0;
                        energyCharges = 0;
                        electricalDuty = 0;
                        fixedCharges = load * 30;
                        customerCharges = CUSTOMER_CHARGES_30;
                    } else {
                        nkt = 0;
                        energyCharges = (float) (units * 5.00);
                        fixedCharges = load * 30;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_30;
                    }
                }

                // Category 501 for both phases 1 & 3
                else if (category == CATEGORY_501) {
                    nkt = 5;
                    if (units == UNITS_0) {
                        energyCharges = 0;
                        electricalDuty = 0;
                        fixedCharges = 0;
                        customerCharges = CUSTOMER_CHARGES_30;
                    } else {
                        energyCharges = (float) (units * 2.50);
                        fixedCharges = 0;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_30;
                    }
                }

                // Category 502 for both phases 1 & 3
                else if (category == CATEGORY_502) {
                    nkt = 5;
                    if (units == UNITS_0) {
                        energyCharges = 0;
                        electricalDuty = 0;
//                        fixedCharges = (float) (load * 525);  //25/04/2019
                        fixedCharges = 0;
                        customerCharges = CUSTOMER_CHARGES_30;
                    } else {
//                        energyCharges = (float) (units * 0.50);
//                        fixedCharges = (float) (load * 525);
//                        electricalDuty = (float) (units * 0.06); //25/04/2019
//                        customerCharges = CUSTOMER_CHARGES_30;
                        energyCharges = 0;
                        fixedCharges = 0;
                        electricalDuty = 0;
                        customerCharges = CUSTOMER_CHARGES_30;
                    }
                }

                // Category 503 for both phases 1 & 3
                else if (category == CATEGORY_503) {
                    nkt = 5;
                    if (units == UNITS_0) {
//                        energyCharges = 0; //14/06/2019
                        energyCharges = (float)(units*3.85);
                        electricalDuty = 0;
//                        fixedCharges = 0;
                        fixedCharges = (float) (load * 30 * 0.75);
                        customerCharges = CUSTOMER_CHARGES_30;
                    } else {
//                        energyCharges = 0; //14/106/2019
                        energyCharges = (float)(units*3.85);
//                        fixedCharges = 0;
                        fixedCharges = (float) (load * 30 * 0.75);
                        electricalDuty = 0;
                        customerCharges = CUSTOMER_CHARGES_30;
                    }
                }

                // Category 504 for both phases 1 & 3
                else if (category == CATEGORY_504) {
                    nkt = 5;
                    if (units == UNITS_0) {
                        energyCharges = 0;
                        electricalDuty = 0;
//                        fixedCharges = 0; //25/04/2019
                        fixedCharges = (float) (load * 75*0.75);
                        customerCharges = CUSTOMER_CHARGES_30;
                    } else {
//                        energyCharges = (float) (units * 3.50); //250419
//                        fixedCharges = 0;
                        energyCharges = (float) (units * 3.85);
                        fixedCharges = (float) (load * 75*0.75);
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_30;
                    }
                }

                // Category 505 for both phases 1 & 3
                else if (category == CATEGORY_505) {
                    nkt = 5;
                    if (units == UNITS_0) {
                        energyCharges = 0;
                        electricalDuty = 0;
//                        fixedCharges = (float) (load * 1050);  //250419
                        fixedCharges = (float) (load * 20*0.75);
                        customerCharges = CUSTOMER_CHARGES_30;
                    } else {
//                        energyCharges = (float) (units * 1.00); // 250419
//                        fixedCharges = (float) (load * 1050);
                        energyCharges = (float) (units * 3.75);
                        fixedCharges = (float) (load * 20*0.75);
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_30;
                    }
                }

                // Category 506 for both phases 1 & 3
                else if (category == CATEGORY_506) {
                    nkt = 5;
                    if (units == UNITS_0) {
                        energyCharges = 0;
                        electricalDuty = 0;
//                        fixedCharges = (float) (load * 1050);  // 250419
                        fixedCharges = (float) (load * 30 * 0.75);
//                        customerCharges = CUSTOMER_CHARGES_30; //14/06/2019
                        customerCharges = CUSTOMER_CHARGES_1406;
                    } else {
//                        energyCharges = (float) (units * 1.00);
//                        fixedCharges = (float) (load * 1050);    // 250419
//                        electricalDuty = (float) (units * 0.06);
                        energyCharges = (float) (units * 3.85);
                        fixedCharges = (float) (load * 30 * 0.75);
                        electricalDuty = (float) (units * 0.06);
//                        customerCharges = CUSTOMER_CHARGES_30; //14/06/2019
                        customerCharges = CUSTOMER_CHARGES_1406;
                    }
                }

                // Category 507 for both phases 1 & 3
                else if (category == CATEGORY_507) {
                    nkt = 5;
                    if (units == UNITS_0) {
                        energyCharges = 0;
                        electricalDuty = 0;
//                        fixedCharges = (float) (load * 20);
//                        customerCharges = CUSTOMER_CHARGES_30;  //25/04/19
                        fixedCharges = (float) (load * 475 * 0.75);
                        customerCharges = CUSTOMER_CHARGES_1406;
                    } else {
//                        energyCharges = (float) (units * 3.70);
//                        fixedCharges = (float) (load * 20);
//                        electricalDuty = (float) (units * 0.06);   //25/04/19
//                        customerCharges = CUSTOMER_CHARGES_30;
                        energyCharges = (float) (units * 4.85);
                        fixedCharges = (float) (load * 475 * 0.75);
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_1406;
                    }
                }

                // Category 601 for both phases 1 & 3
                else if (category == CATEGORY_601) {
                    if (units == UNITS_0) {
                        nkt = 0;
                        energyCharges = 0;
                        electricalDuty = 0;
                        fixedCharges = load * 75;
                        customerCharges = CUSTOMER_CHARGES_35;
                    } else {
                        nkt = 0;
//                        energyCharges = (float) (units * 5.98);  //250419
                        energyCharges = (float) (units * 7.09);
                        fixedCharges = load * 75;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_35;
                    }
                }

                // Category 602 for both phases 1 & 3
                else if (category == CATEGORY_602) {
                    if (units == UNITS_0) {
                        nkt = 0;
                        energyCharges = 0;
                        electricalDuty = 0;
                        fixedCharges = load * 75;
                        customerCharges = CUSTOMER_CHARGES_35;
                    } else {
                        nkt = 0;
                        energyCharges = (float) (units * 7.09);
                        fixedCharges = load * 75;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_35;
                    }
                }

                // Category 603 for both phases 1 & 3
                else if (category == CATEGORY_603) {
                    if (units == UNITS_0) {
                        nkt = 0;
                        energyCharges = 0;
                        electricalDuty = 0;
                        fixedCharges = load * 75;
                        customerCharges = CUSTOMER_CHARGES_35;
                    } else {
                        nkt = 0;
                        energyCharges = (float) (units * 4.87);
                        fixedCharges = load * 75;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_35;
                    }
                }

                // Category 604 for both phases 1 & 3
                else if (category == CATEGORY_604) {
                    if (units == UNITS_0) {
                        nkt = 0;
                        energyCharges = 0;
                        electricalDuty = 0;
                        fixedCharges = load * 75;
                        customerCharges = CUSTOMER_CHARGES_35;
                    } else {
                        nkt = 0;
                        energyCharges = (float) (units * 6.53);
                        fixedCharges = load * 75;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_35;
                    }
                }

                // Category 605 for both phases 1 & 3
                else if (category == CATEGORY_605) {
                    if (units == UNITS_0) {
                        nkt = 0;
                        energyCharges = 0;
                        electricalDuty = 0;
                        fixedCharges = load * 10;
                        customerCharges = CUSTOMER_CHARGES_30;
                    } else {
                        nkt = 0;
                        energyCharges = (float) (units * 4.00);
                        fixedCharges = load * 10;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_30;
                    }
                }

                // Category 701 for both phases 1 & 3
                else if (category == CATEGORY_701) {
                    if (units == UNITS_0) {
                        nkt = 0;
                        energyCharges = 0;
                        electricalDuty = 0;
                        fixedCharges = load * 30;
                        customerCharges = CUSTOMER_CHARGES_30;
                    } else {
                        nkt = 0;
                        energyCharges = (float) (units * 4.84);
                        fixedCharges = load * 30;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_30;
                    }
                }

                // Category 701 for both phases 1 & 3
                else if (category == CATEGORY_702) {
                    if (units == UNITS_0) {
                        nkt = 0;
                        energyCharges = 0;
                        electricalDuty = 0;
                        fixedCharges = load * 10;
                        customerCharges = CUSTOMER_CHARGES_30;
                    } else {
                        nkt = 0;
                        energyCharges = (float) (units * 5.10);
                        fixedCharges = load * 10;
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_30;
                    }
                }

                // Category 800 for both phases 1 & 3
                else if (category == CATEGORY_800) {
                    if (units == UNITS_0) {
                        nkt = 0;
                        energyCharges = load * 125;
                        fixedCharges = (float) (load * 30.00);
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_50;
                    } else {
                        nkt = 0;
                        float charges_units = (float) (units * 10.50);
                        float charges_load = (load * 125);
                        if (charges_load > charges_units) {
                            energyCharges = charges_load;
                        } else if (charges_units > charges_load) {
                            energyCharges = charges_units;
                        }
                        fixedCharges = (float) (load * 30.00);
                        electricalDuty = (float) (units * 0.06);
                        customerCharges = CUSTOMER_CHARGES_50;
                    }
                }
            }

            if (category == 100 || category == 101 || category == 102) {
                if (load <= 0.5 && energyCharges < 25 && phase == 1) {
                    energyCharges = 25;
                } else if (load > 0.5 && energyCharges < 50 && phase == 1) {
                    energyCharges = 50;
                }
                if (phase == 3 && energyCharges < 150) {
                    energyCharges = 150;
                }
            }
            if (category == 200 || category == 202 || category == 201) {
                if (energyCharges < 65 && phase == 1) {
                    energyCharges = 65;
                } else if (energyCharges < 200 && phase == 3) {
                    energyCharges = 200;
                }
            }
//            if (category == 700) {
//                if (energyCharges < 50 && phase == 1) {
//                    energyCharges = 50;
//                } else if (energyCharges < 150 && phase == 3) {
//                    energyCharges = 150;
//                }
//            }

            //surcharge to be counted if NewArrears > 0 for the below category
            if(billdetailModel.getLPDATE() != null){
                if(Integer.parseInt(billdetailModel.getClose_bal()) > 0
                        && !paidOnTime(billdetailModel.getLPDATE(),billdetailModel.getERO())){

                    int dat = 0;
                    dat = getdata(billdetailModel.getLPDATE(),billdetailModel.getERO());

                    //if(dat == 2) {
                    String ero = billdetailModel.getERO();
                    if(ero.equals("009") || ero.equals("9")) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
                        String sd = "05-Jun-19";
                        Date startDate = dateFormat.parse(sd);
                        String ed = "30-Jun-19";
                        Date dueDate = dateFormat.parse(ed);
                        String lpdate = billdetailModel.getLPDATE();
                        // String str_lpdate = billdetailModel.getLPDATE();
                        Date lpdate1 = dateFormat.parse(lpdate);
                        if (startDate.compareTo(lpdate1) <= 0 && lpdate1.compareTo(dueDate) <= 0) {
                            if (billdetailModel.getClose_bal().equals( billdetailModel.getLPAMT())) {
                                // arrears.setText("0");
                                billdetailModel.setClose_bal("0");
                            } else if (Integer.parseInt(billdetailModel.getClose_bal()) > Integer.parseInt(billdetailModel.getLPAMT())) {
                                int amt =  Integer.parseInt(billdetailModel.getClose_bal()) - Integer.parseInt(billdetailModel.getLPAMT());
                                // arrears.setText(String.valueOf(amt));
                                billdetailModel.setClose_bal(String.valueOf(amt));
                            } else if (Integer.parseInt(billdetailModel.getClose_bal()) < Integer.parseInt(billdetailModel.getLPAMT())) {
                                int amt1 =  Integer.parseInt(billdetailModel.getClose_bal()) - Integer.parseInt(billdetailModel.getLPAMT());
                                // arrears.setText(String.valueOf(amt1));
                                billdetailModel.setClose_bal(String.valueOf(amt1));
                            }
                        }
                    }

                    int noOfDays = 0;
                    if(billdetailModel.getLPDATE() != null){
                        noOfDays = getNoOfDelayedDays(billdetailModel.getLPDATE(), billdetailModel.getERO());
                    }
                    if(noOfDays>0){
                        if(category == CATEGORY_100){
                            surCharge = 10;
                        }else if((category == CATEGORY_101) || (category == CATEGORY_102)
                                || (category == CATEGORY_301) || (category == CATEGORY_505)){

                            surCharge = 25;

                        }
                        else if((category == CATEGORY_501) || (category == CATEGORY_502)){
                            surCharge = 0;

                        }else if((category == CATEGORY_800)){
                            surCharge = Math.round(noOfDays*Integer.parseInt(billdetailModel.getNEWARREARS())*0.0005);
                            if(surCharge<150){
                                surCharge = 150;
                            }
                        }else if((category == CATEGORY_302) || (category == CATEGORY_506) || (category == CATEGORY_507) ){
                            surCharge = Math.round(noOfDays*Integer.parseInt(billdetailModel.getNEWARREARS())*0.0005);
                            if(surCharge<550){
                                surCharge = 550;
                            }

                        }else if(category == CATEGORY_200){

                            if(units<=50){
                                surCharge = 25;
                            }else {
                                if(billdetailModel.getLPDATE().length()>0)   {
//                            noOfDays = getNoOfDelayedDays(billdetailModel.getLPDATE());
                                    Log.e("no of days:",""+noOfDays);
                                    surCharge = Math.round(noOfDays*Integer.parseInt(billdetailModel.getNEWARREARS())*0.0005); // s
                                    if(surCharge<150)  {
                                        surCharge = 150;
                                    }
                                }
                            }
                        }
                        else{
                            if(billdetailModel.getLPDATE().length()>0){
//                            noOfDays = getNoOfDelayedDays(billdetailModel.getLPDATE());
                                Log.e("no of days:",""+noOfDays);
                                surCharge = Math.round(noOfDays*Integer.parseInt(billdetailModel.getNEWARREARS())*0.0005); //s

                                if(surCharge<150){
                                    surCharge = 150;
                                }
                            }

                        }
                    }


                }
            }

            billdetailModel.setSURCHRGS(String.valueOf(surCharge));
            Log.e("Surcharge",""+surCharge);
            Log.e("test 1",""+Integer.parseInt(billdetailModel.getClose_bal()));
//            amount = energyCharges + customerCharges + fixedCharges + electricalDuty + nkt+burnt_charges+surCharge;
            amount = energyCharges + customerCharges + fixedCharges + electricalDuty + nktmd+burnt_charges+surCharge; //burnt charges added praful fot status 11
            Log.e(LOG_TAG, "final charges " + energyCharges + ", " + electricalDuty + ",  " + customerCharges + ", " + nktmd + " , final Amount  " + Math.round(amount));

        }


        if (status == 17) {

            int prevBalance = Integer.parseInt(billdetailModel.getOpen_bal());
            amount = amount - prevBalance;
        }
//          praful arrears
        int arrers = 0;
        if(!billdetailModel.getClose_bal().equals("") || !billdetailModel.getClose_bal().isEmpty()
                || !(billdetailModel.getClose_bal() == null)){
            Log.e("arrears", ""+arrers);
            arrers = Integer.parseInt(billdetailModel.getClose_bal());
        }
        Log.e("arrears", ""+arrers);
//            int arrers = Integer.parseInt(billdetailModel.getAdj_amount());
            amount = amount + arrers;



        billdetailModel.setEDCHG(String.valueOf(electricalDuty));
        billdetailModel.setCUSCHG(String.valueOf(customerCharges));
        billdetailModel.setENGCHG(String.valueOf(energyCharges));
        billdetailModel.setFIXEDCHG(String.valueOf(fixedCharges));
//        billdetailModel.setNkt(String.valueOf(nkt));
        billdetailModel.setNkt(String.valueOf(nktmd));
        billdetailModel.setLORG(String.valueOf(amount - Math.round(amount)));
        billdetailModel.setBILLAMT(String.valueOf(Math.round(amount)));
        billdetailModel.setOPRDNG(billdetailModel.getCLRDNG());
        //changed on 5th march 2019
        if(status == 2 || status == 6){
            billdetailModel.setCLRDNG(billdetailModel.getOPRDNG());
        }
        else if (status == 5 || status == 8) {
            String oldValue = String.valueOf(Integer.parseInt(curRead) + Integer.parseInt(billdetailModel.getOPRDNG()));
          //  billdetailModel.setCLRDNG(curRead);  //add s
            billdetailModel.setCLRDNG(oldValue);  //remove s
        }//praful added end reading = avg unit + start reading for status 11
         //@param curRead here is avg unit
        else if(status == 11){
            billdetailModel.setCLRDNG(String.valueOf(Integer.parseInt(curRead) + Integer.parseInt(billdetailModel.getCLRDNG())));
        }else if(status == 4){
            billdetailModel.setOPRDNG(String.valueOf(Integer.parseInt(billdetailModel.getCLRDNG())-Integer.parseInt(curRead)));
//            billdetailModel.setCLRDNG(curRead);
        }
        else {
            billdetailModel.setCLRDNG(curRead);
        }

        if (status == 7) {
            String oldValue = String.valueOf(units + Integer.parseInt(billdetailModel.getOPRDNG()));

          //  billdetailModel.setCLRDNG(oldValue);
            billdetailModel.setCLRDNG(billdetailModel.getOPRDNG()); // s
        }

        Log.e(LOG_TAG, " 11 avg/cur reading:  " + curRead);
        Log.e(LOG_TAG, " 11 unit :  " +billdetailModel.getUNITS());
        Log.e(LOG_TAG, " 11 :start  " +billdetailModel.getOPRDNG());
        Log.e(LOG_TAG, " 11 end :  " +billdetailModel.getCLRDNG());
        Log.e(LOG_TAG, " 11 :bill amount  " +billdetailModel.getBILLAMT());
        return billdetailModel;


    }

    private static boolean paidOnTime(String lpdate, String ero) {
                lpdate ="30-Jun-19";
//                lpdate = "20-Dec-18";
//        lpdate = "02-Mar-19";
//        lpdate = "07-Mar-19";   // delay or all ero
//        lpdate = "07-Mar-19";  ero = "009"; // delay or all ero
//        lpdate = "27-Feb-19"; ero = "009";// delay or ero 009 ok for others
//        lpdate = "02-Feb-19";
//        lpdate = "19-Feb-19"; // ok or all ero
//        lpdate = "10-Feb-19"; // ok or all ero
//        lpdate = "14-Jan-19"; // need to analyze
        boolean isPaidOnTime = true;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
        long noOdDay = 0;

        Date convertedDate = null;
        try {
            convertedDate = dateFormat.parse(lpdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(convertedDate != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(convertedDate);

            int paidMonth = c.get(Calendar.MONTH);


            Calendar currentMonth = Calendar.getInstance();
            currentMonth.setTime(new Date());

            int cMonth = currentMonth.get(Calendar.MONTH);
            int diffMonth;
            // check if same year or not
            Log.e("year","year1:"+c.get(Calendar.YEAR)+" year 2:"+currentMonth.get(Calendar.YEAR));
            if(c.get(Calendar.YEAR) != currentMonth.get(Calendar.YEAR)){
                diffMonth = 2;
            }else {
                diffMonth = cMonth-paidMonth;
            }
            Calendar todayC = Calendar.getInstance();

            Calendar dDateC = Calendar.getInstance();
            dDateC.set(Calendar.HOUR_OF_DAY,0);
            dDateC.set(Calendar.MINUTE,0);
            dDateC.set(Calendar.SECOND,0);
            // change to previous month

            todayC.add(Calendar.MONTH, -1);


            int max = todayC.getActualMaximum(Calendar.DAY_OF_MONTH);
            todayC.set(Calendar.DAY_OF_MONTH, max);
            todayC.set(Calendar.HOUR_OF_DAY,0);
            todayC.set(Calendar.MINUTE,0);
            todayC.set(Calendar.SECOND,0);


            int mon = todayC.get(Calendar.MONTH);
            int year = todayC.get(Calendar.YEAR);
//            todayC.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date dueDate = todayC.getTime();
            todayC.set(year, mon, 16);
            Date startDate = todayC.getTime();
            dDateC.set(year, (mon + 1), 15);
            Date disConDate = dDateC.getTime();


            if (ero.equals("009") || ero.equals("9")) {

                int day = todayC.get(Calendar.DAY_OF_MONTH);
                todayC.set(year, mon, 5);
                startDate = todayC.getTime();
                todayC.set(year, mon, 25);
                dueDate = todayC.getTime();
                Log.e(" time ero 9", "" + dueDate);
                todayC.set(year, mon, 27);
                disConDate = todayC.getTime();
            }
            Log.e("time Due day", "" + dueDate);
            Log.e("time Start day", "" + startDate);
            Log.e("Dis Connection date", "" + disConDate);
            int dueMonth = todayC.get(Calendar.DAY_OF_MONTH);

            try {
                Date lpDate = dateFormat.parse(lpdate);

                long diff1 = dueDate.getTime() - lpDate.getTime();
                Log.e("due date:",""+dueDate.getTime()+" lpdate:"+lpDate.getTime()+"diff1"+diff1);
                double afterNoDay = (double)diff1/86400000;
//                float asdf = (float)TimeUnit.DAYS.convert(diff89564, TimeUnit.MILLISECONDS);
                Log.e("diff11111",""+afterNoDay+(1.0/2.0));

                long diff2 = lpDate.getTime() - startDate.getTime();
//                int beforeNoDay = (int) TimeUnit.DAYS.convert(diff2, TimeUnit.MILLISECONDS);
                double beforeNoDay = (double)diff2/86400000;
                Date billDate = new Date();

                Log.e("time Bill day", "" + billDate);
                if (afterNoDay > 0 && beforeNoDay > 0) { // no delay
                    noOdDay = 0;
                    isPaidOnTime = true;
                    Log.e(" time Delay", "No delay");
                } else if (afterNoDay < 0) {

                    long diff3 = lpDate.getTime() - dueDate.getTime();
//                    noOdDay = (int) TimeUnit.DAYS.convert(diff3, TimeUnit.MILLISECONDS);
                    noOdDay = Math.round((double)diff3/86400000);
                    isPaidOnTime = false;
                    Log.e("time Delay", "delay: " + noOdDay);
                } else if (beforeNoDay < 0) {
                    if (diffMonth >= 2 || diffMonth <= -2) {
                        long diff8 = disConDate.getTime() - dueDate.getTime();
//                        noOdDay = (int) TimeUnit.DAYS.convert(diff8, TimeUnit.MILLISECONDS);
                        noOdDay = Math.round((double)diff8/86400000);
                        isPaidOnTime = false;
                        Log.e("time Delay", "delay case 4: " + noOdDay);
                    } else {
//                        long diff7 = billDate.getTime() - lpDate.getTime(); ///important for within 2 months
                        long diff7 = disConDate.getTime() - dueDate.getTime();
//                        noOdDay = (int) TimeUnit.DAYS.convert(diff7, TimeUnit.MILLISECONDS);
                        noOdDay = Math.round((double)diff7/86400000);
                        isPaidOnTime = false;
                        Log.e("time Delay", "delay case 3: " + noOdDay);
                    }

                    isPaidOnTime = false;

                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return isPaidOnTime;
    }



// s
    private static int getdata(String lpdate,String ero) throws ParseException {
        int rec=0;

        if(ero.equals("009") || ero.equals("9")){
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");



            Calendar todayC = Calendar.getInstance();
            Calendar dDateC = Calendar.getInstance();
            dDateC.set(Calendar.HOUR_OF_DAY,0);
            dDateC.set(Calendar.MINUTE,0);
            dDateC.set(Calendar.SECOND,0);


            int day = todayC.get(Calendar.DAY_OF_MONTH);


            int mon = todayC.get(Calendar.MONTH);
            int year = todayC.get(Calendar.YEAR);
            todayC.set(year,mon,5);
            String sd = "05-Jun-19";
            Date startDate = dateFormat.parse ( sd );
            String ed = "30-Jun-19";
            Date dueDate = dateFormat.parse ( ed );

           // String str_lpdate = billdetailModel.getLPDATE();
            Date lpdate1 = dateFormat.parse ( lpdate );

            if(startDate.compareTo(lpdate1) < 0 || lpdate1.compareTo(dueDate)< 0){

               rec = 1;

            }
            else
                 rec = 2;
        }

        return rec;
    }



    private static int getNoOfDelayedDays(String lpdate, String ero) {
        lpdate = "30-Mar-19";
//        lpdate ="05-Jun-19";
//        lpdate = "02-Mar-19";
//        lpdate = "07-Mar-19";   // delay or all ero
//        lpdate = "07-Mar-19";  ero = "009"; // delay or all ero
//        lpdate = "27-Feb-19"; ero = "009";// delay or ero 009 ok for others
//        lpdate = "02-Feb-19";
//        lpdate = "19-Feb-19"; // ok or all ero
//        lpdate = "10-Feb-19"; // ok or all ero
//        lpdate = "14-Jan-19"; // need to analyze
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
        long noOdDay = 0;

        Date convertedDate = null;
        try {
            convertedDate = dateFormat.parse(lpdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(convertedDate != null){
            Calendar c = Calendar.getInstance();
            c.setTime(convertedDate);

            int paidMonth = c.get(Calendar.MONTH);


            Calendar currentMonth = Calendar.getInstance();
            currentMonth.setTime(new Date());

            int cMonth = currentMonth.get(Calendar.MONTH);
            int diffMonth;
            // check if same year or not
            Log.e("year","year1:"+c.get(Calendar.YEAR)+" year 2:"+currentMonth.get(Calendar.YEAR));
            if(c.get(Calendar.YEAR) != currentMonth.get(Calendar.YEAR)){
                diffMonth = 2;
            }else {
                diffMonth = cMonth-paidMonth;
            }


            Calendar todayC = Calendar.getInstance();
            Calendar dDateC = Calendar.getInstance();
            dDateC.set(Calendar.HOUR_OF_DAY,0);
            dDateC.set(Calendar.MINUTE,0);
            dDateC.set(Calendar.SECOND,0);

            // change to previous month
            todayC.add(Calendar.MONTH, -1);
            int max = todayC.getActualMaximum(Calendar.DAY_OF_MONTH);
            todayC.set(Calendar.DAY_OF_MONTH, max);
            todayC.set(Calendar.HOUR_OF_DAY,0);
            todayC.set(Calendar.MINUTE,0);
            todayC.set(Calendar.SECOND,0);

            int mon = todayC.get(Calendar.MONTH);
            int year = todayC.get(Calendar.YEAR);
//            todayC.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date dueDate = todayC.getTime();
            todayC.set(year,mon,16);
            Date startDate = todayC.getTime();
            dDateC.set(year,(mon+1),15);
            Date disConDate = dDateC.getTime();




            if(ero.equals("009") || ero.equals("9")){

                int day = todayC.get(Calendar.DAY_OF_MONTH);
                todayC.set(year,mon,5);
                startDate = todayC.getTime();
                todayC.set(year,mon,20);
                dueDate = todayC.getTime();
                Log.e("ero 9",""+dueDate);
                todayC.set(year,mon,27);
                disConDate = todayC.getTime();




            }
            Log.e("Due day",""+dueDate);
            Log.e("Start day",""+startDate);
            Log.e("Dis Connection date",""+disConDate);
            int dueMonth = todayC.get(Calendar.DAY_OF_MONTH);

            try {
                Date lpDate = dateFormat.parse(lpdate);

                long diff1 = dueDate.getTime() - lpDate.getTime();
//                int afterNoDay = (int) TimeUnit.DAYS.convert(diff1, TimeUnit.MILLISECONDS);
                double afterNoDay = (double)diff1/86400000;

                long diff2 = lpDate.getTime() - startDate.getTime();
//                int beforeNoDay = (int) TimeUnit.DAYS.convert(diff2, TimeUnit.MILLISECONDS);
                double beforeNoDay = (double)diff2/86400000;
                Date billDate = new Date();

                Log.e("Bill day",""+billDate);
                if(afterNoDay>0 && beforeNoDay>0){ // no delay
                    noOdDay = 0;
                    Log.e("Delay","No delay");
                }else if(afterNoDay<0){

                    long diff3 = lpDate.getTime() - dueDate.getTime();
//                    noOdDay = (int) TimeUnit.DAYS.convert(diff3, TimeUnit.MILLISECONDS);
                    noOdDay = Math.round((double)diff3/86400000);
                    Log.e("Delay","delay: "+noOdDay);
                }else if(beforeNoDay<0){
                    if(diffMonth>=2 || diffMonth<=-2){
                        long diff8 = disConDate.getTime() - dueDate.getTime();
//                        noOdDay = (int) TimeUnit.DAYS.convert(diff8, TimeUnit.MILLISECONDS);
                        noOdDay = Math.round((double)diff8/86400000);
                        Log.e("Delay","delay case 4: "+noOdDay);
                    }else {
//                        long diff7 = billDate.getTime() - lpDate.getTime(); // important for within 2 months changed on 13/06/2019
                        long diff7 = disConDate.getTime() - dueDate.getTime();
//                        noOdDay = (int) TimeUnit.DAYS.convert(diff7, TimeUnit.MILLISECONDS);
                        noOdDay = Math.round((double)diff7/86400000);
                        Log.e("Delay","delay case 3: "+noOdDay);
                    }

                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

//        calcuclateNoOFDays(lpdate);
        return (int) noOdDay;
    }



}