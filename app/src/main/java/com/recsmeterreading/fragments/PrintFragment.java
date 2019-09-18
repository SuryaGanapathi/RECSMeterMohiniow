package com.recsmeterreading.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ngx.BluetoothPrinter;
import com.ngx.BtpCommands;
import com.recsmeterreading.R;
import com.recsmeterreading.Utils.DialogAction;
import com.recsmeterreading.api.ApiUtils;
import com.recsmeterreading.api.MeterApi;
import com.recsmeterreading.bluetoothPrinter.BluetoothPrinterMain;
import com.recsmeterreading.bluetoothPrinter.SetLogo;
import com.recsmeterreading.model.GetBillDetailsModel;
import com.recsmeterreading.model.ServiceDetails;
import com.recsmeterreading.model.ServiceNo;
import com.recsmeterreading.model.ServiceNumber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import woyou.aidlservice.jiuiv5.ICallback;
import woyou.aidlservice.jiuiv5.IWoyouService;

/**
 * Created by SuryaTejaChalla on 04-01-2018.
 */

public class PrintFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListenerC mListener;

    private BluetoothPrinter mBtp = BluetoothPrinterMain.mBtp;
    private IWoyouService woyouService=BluetoothPrinterMain.woyouService;
    Bundle r_bundle;
    String print_type;
    Map<String, String> responseMap;
    //    CollectionsModel.CollectionsBean printDetails;
    private EditText mEdit;
    private TextView tvSeekBar;
    private int minutes = 1440;
    private CheckBox cbVolatile;

    ArrayList<String> paymmentModesNames = new ArrayList<>();
    ArrayList<String> paymmentModesId = new ArrayList<>();
    ArrayList<String> aryTaxNames = new ArrayList<>();
    ArrayList<String> aryTaxIds = new ArrayList<>();
    GetBillDetailsModel getBillDetailsModel;

    List<ServiceNo> serviceNoList = new ArrayList<>();
    List<ServiceNumber> serviceNumberList = new ArrayList<>();
    int report = 1;
    DialogAction dialogAction;
    ServiceDetails serviceDetails;
    String title,value,category = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ascii_layout, container, false);

        r_bundle = getActivity().getIntent().getExtras();
        dialogAction = new DialogAction(getActivity());
        report = r_bundle.getInt("report",0);
        if(report == 5){

            Intent data = getActivity().getIntent();
            String areaCode = data.getStringExtra("areaCode");
            category = data.getStringExtra("category");
            title = data.getStringExtra("typeService");
            Bundle args = data.getBundleExtra("data");

            if(title.equals("TotalAmount") || title.equals("TotalUnits")){
                value = data.getStringExtra("value");
            }else {
                if(areaCode != null){
                    MeterApi meterApi = ApiUtils.getMeterApi();
                    dialogAction.showDialog("Reports","Retrieving Reports");
                    meterApi.getTotalService(title,areaCode,category).enqueue(new Callback<ServiceDetails>() {
                        @Override
                        public void onResponse(Call<ServiceDetails> call, Response<ServiceDetails> response) {
                            dialogAction.hideDialog();
                            if(response.isSuccessful()){
                                serviceDetails = response.body();
                                if(serviceDetails != null){
                                    if(serviceDetails.getService_numbers().size()>0){
                                        serviceNoList = serviceDetails.getService_numbers();
                                        Log.e("assssddss",""+serviceDetails.getCATEGORY()+serviceDetails.getService_numbers().get(0).getSCNO());
//                                    UpdateUi(serviceDetails.getValue());

                                    }else {

                                    }

                                }

                            }

                        }

                        @Override
                        public void onFailure(Call<ServiceDetails> call, Throwable t) {
                            dialogAction.hideDialog();
                        }
                    });
                }
            }

//            if(args!=null){
//                serviceNoList = (List<ServiceNo>) args.getSerializable("ARRAYLIST");
//                if (serviceNoList != null) {
//                    Log.e("asss",""+serviceNoList.size());
//                }else {
//                    Log.e("s","aaaaa");
//                }
//            }
        }else if(report == 11){

            Bundle args = r_bundle.getBundle("data");

            if(args!=null){
                serviceNumberList = (List<ServiceNumber>)args.getSerializable("ARRAYLIST");
                if (serviceNumberList != null) {
                    Log.e("print 3",""+serviceNumberList.size());
                }else {
                    Log.e("print 3a","aaaaa");
                }
            }

        }else {
            getBillDetailsModel = (GetBillDetailsModel) r_bundle.getSerializable("data") ;
        }




        paymmentModesNames.clear();
        paymmentModesId.clear();
        aryTaxNames.clear();
        aryTaxIds.clear();

        paymmentModesNames.addAll(Arrays.asList(getResources().getStringArray(R.array.payment_names)));

        Gson gson = new Gson();
//        printDetails = gson.fromJson(data, CollectionsModel.CollectionsBean.class);
        intiView(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListenerC) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void intiView(View v) {

        mEdit = v.findViewById(R.id.txt);
        mEdit.setText(print_type);

        Button btnPrintLogo = v.findViewById(R.id.btnPrintLogo);
        btnPrintLogo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mBtp.printLogo();
            }
        });

        Button btnPrintText = v.findViewById(R.id.btnPrintText);
        btnPrintText.setOnClickListener(this);
        btnPrintText.setText("Print Out");
        Button btnPrint = v.findViewById(R.id.btnPrint);
        btnPrint.setOnClickListener(this);


        Button btnSetLogo = v.findViewById(R.id.btnChangeLogo);
        btnSetLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment sl = new SetLogo();
                BluetoothPrinterMain.changeFragment(R.id.container, sl, true);
            }
        });

//        Button btnPrintLineFeed = (Button) v.findViewById(R.id.btnPrintLineFeed);
//        btnPrintLineFeed.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                mBtp.printLineFeed();
//            }
//        });



//        Button btnPrintAsciiText = (Button) v.findViewById(R.id.btnPrintAsciiText);
//        btnPrintAsciiText.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                String txt = mEdit.getText().toString();
//                mBtp.setPrintFontStyle(BtpCommands.FONT_KANNADA);
//                mBtp.printAsciiText(txt);
//            }
//        });

//        Button btnPrintKnAksharamale = (Button) v
//                .findViewById(R.id.btnPrintKnAksharamale);
//        btnPrintKnAksharamale.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                String txt = "C D E F G H IÄ J K L M N O CA CB\n\n"
//                        +
//
//                        "PÀ R UÀ WÀ k \n"
//                        + "ZÀ bÀ d gÀhÄ Y \n"
//                        + "l oÀ qÀ qsÀ uÁ t \n"
//                        + "vÀ xÀ zÀ zsÀ £À \n"
//                        + "¥À ¥sÀ ¨Á ¨sÀ ªÀÄ \n"
//                        + "AiÀÄ ® ªÀ ±À µÀ ¸À ºÀ ¼À \n\n"
//                        +
//
//                        "Pï PÀ PÁ Q QÃ PÀÄ PÀÈ PÉ PÉÃ PÉÊ PÉÆ PÉÆÃ PË PÀA PÀB \n"
//                        + "Sï R SÁ T TÃ RÄ RÆ RÈ SÉ SÉÃ SÉÊ SÉÆ SÉÆÃ SË RA RB\n"
//                        + "Uï UÀ UÁ V VÃ UÀÄ UÀÆ UÀÈ UÉ UÉÃ UÉÊ UÉÆ UÉÆÃ UË UÀA UÀB\n"
//                        + "Wï WÀ WÁ X XÃ WÀÄ WÀÆ WÀÈ WÉ WÉÃ WÉÊ WÉÆ WÉÆÃ WË WÀA WÀB\n\n"
//                        +
//
//                        "Zï ZÀ ZÁ a aÃ ZÀÄ ZÀÆ ZÀÈ ZÉ ZÉÃ ZÉÊ ZÉÆ ZÉÆÃ ZË ZÀA ZÀB\n"
//                        + "bï bÀ bÁ c cÃ bÀÄ bÀÆ bÀÈ bÉ bÉÃ bÉÊ bÉÆ bÉÆÃ bË bÀA bÀB\n"
//                        + "eï d eÁ f fÃ dÄ dÆ dÈ eÉ eÉÃ eÉÊ eÉÆ eÉÆÃ eË dA dB\n"
//                        + "gÀhiï gÀhÄ gÀhiÁ jhÄ jhÄÃ gÀhÄÄ gÀhÄÆ gÀhÄÈ gÉhÄ gÉhÄÃ gÉhÄÊ gÉhÆ gÉhÆÃ gÀhiË gÀhÄA gÀhÄB\n"
//                        + "\n"
//                        + "mï l mÁ n nÃ lÄ lÆ lÈ mÉ mÉÃ mÉÊ mÉÆ mÉÆÃ mË lA lB\n"
//                        + "oï oÀ oÁ p pÃ oÀÄ oÀÆ oÀÈ oÉ oÉÃ oÉÊ oÉÆ oÉÆÃ oË oÀA oÀB \n"
//                        + "qï qÀ qÁ r rÃ qÀÄ qÀÆ qÀÈ qÉ qÉÃ qÉÊ qÉÆ qÉÆÃ qË qÀA qÀB \n"
//                        + "qsï qsÀ qsÁ rü rüÃ qsÀÄ qsÀÆ qsÀÈ qsÉ qsÉÃ qsÉÊ qsÉÆ qsÉÆÃ qsË qsÀA qsÀB\n"
//                        + "uï t uÁ tÂ tÂÃ tÄ tÆ tÈ uÉ uÉÃ uÉÊ uÉÆ uÉÆÃ uË tA tB \n"
//                        + "\n"
//                        + "vï vÀ vÁ w wÃ vÀÄ vÀÆ vÀÈ vÉ vÉÃ vÉÊ vÉÆ vÉÆÃ vË vÀA vÀB\n"
//                        + "xï xÀ xÁ y yÃ xÀÄ xÀÆ xÀÈ xÉ xÉÃ xÉÊ xÉÆ xÉÆÃ xË xÀA xÀB\n"
//                        + "zï zÀ zÁ ¢ ¢Ã zÀÄ zÀÆ zÀÈ zÉ zÉÃ zÉÊ zÉÆ zÉÆÃ zË zÀA zÀB\n"
//                        + "zsï zsÀ zsÁ ¢ü ¢üÃ zsÀÄ zsÀÆ zsÀÈ zsÉ zsÉÃ zsÉÊ zsÉÆ zsÉÆÃ zsË zsÀA zsÀB\n"
//                        + "£ï £À £Á ¤ ¤Ã £ÀÄ £ÀÆ £ÀÈ £É £ÉÃ £ÉÊ £ÉÆ £ÉÆÃ £Ë £ÀA £ÀB\n"
//                        + "\n"
//                        + "¥ï ¥À ¥Á ¦ ¦Ã ¥ÀÄ ¥ÀÆ ¥ÀÈ ¥É ¥ÉÃ ¥ÉÊ ¥ÉÆ ¥ÉÆÃ ¥Ë ¥ÀA ¥ÀB\n"
//                        + "¥sï ¥sÀ ¥sÁ ¦ü ¦üÃ ¥sÀÄ ¥sÀÆ ¥sÀÈ ¥sÉ ¥sÉÃ ¥sÉÊ ¥sÉÆ ¥sÉÆÃ ¥sË ¥sÀA ¥sÀB\n"
//                        + "¨ï § ¨Á © ©Ã §Ä §Æ §È ¨É ¨ÉÃ ¨ÉÊ ¨ÉÆ ¨ÉÆÃ ¨Ë §A §B \n"
//                        + "¨sï ¨sÀ ¨sÁ ©ü ©Ã ¨sÀÄ ¨sÀÆ ¨sÀÈ ¨sÉ ¨sÉÃ ¨sÉÊ ¨sÉÆ ¨sÉÆÃ ¨sË ¨sÀA ¨sÀB\n"
//                        + "ªÀiï ªÀÄ ªÀiÁ «Ä «ÄÃ ªÀÄÄ ªÀÄÆ ªÀÄÈ ªÉÄ ªÉÄÃ ªÉÄÊ ªÉÆ ªÉÆÃ ªÀiË ªÀÄA ªÀÄB \n"
//                        + "\n"
//                        + "AiÀiï AiÀÄ AiÀiÁ ¬Ä ¬ÄÃ AiÀÄÄ AiÀÄÆ AiÀÄÈ AiÉÄ AiÉÄÃ AiÉÄÊ AiÉÆ AiÉÆÃ AiÀiË AiÀÄA AiÀÄB\n"
//                        + "gï gÀ gÁ j jÃ gÀÄ gÀÆ gÀÈ gÉ gÉÃ gÉÊ gÉÆ gÉÆÃ gË gÀA gÀB \n"
//                        + "¯ï ® ¯Á ° °Ã ®Ä ®Æ ®È ¯É ¯ÉÃ ¯ÉÊ ¯ÉÆ ¯ÉÆÃ ¯Ë ®A ®B\n"
//                        + "ªï ªÀ ªÁ « «Ã ªÀÅ ªÀÇ ªÀÈ ªÉ ªÉÃ ªÉÊ ªÉÇ ªÉÇÃ ªË ªÀA ªÀB\n"
//                        + "±ï ±À ±Á ² ²Ã ±ÀÄ ±ÀÆ ±ÀÈ ±É ±ÉÃ ±ÉÊ ±ÉÆ ±ÉÆÃ ±Ë ±ÀA ±ÀB\n"
//                        + "µï µÀ µÁ ¶ ¶Ã µÀÄ µÀÆ µÀÈ µÉ µÉÃ µÉÊ µÉÆ µÉÆÃ µË µÀA µÀB\n"
//                        + "¸ï ¸À ¸Á ¹ ¹Ã ¸ÀÄ ¸ÀÆ ¸ÀÈ ¸É ¸ÉÃ ¸ÉÊ ¸ÉÆ ¸ÉÆÃ ¸Ë ¸ÀA ¸ÀB\n"
//                        + "ºï ºÀ ºÁ » »Ã ºÀÄ ºÀÆ ºÀÈ ºÉ ºÉÃ ºÉÊ ºÉÆ ºÉÆÃ ºË ºÀA ºÀB\n"
//                        + "¼ï ¼À ¼Á ½ ½Ã ¼ÀÄ ¼ÀÆ ¼ÀÈ ¼É ¼ÉÃ ¼ÉÊ ¼ÉÆ ¼ÉÆÃ ¼Ë ¼ÀA ¼ÀB \n\n";
//                mEdit.setText(txt);
//                mBtp.setPrintFontStyle(BtpCommands.FONT_KANNADA);
//                mBtp.printAsciiText(txt);
//            }
//        });

//        Button btnSetFontBold = (Button) v.findViewById(R.id.btnSetFontBold);
//        btnSetFontBold.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_BOLD);
//            }
//        });

//        Button btnSetFontRegular = (Button) v.findViewById(R.id.btnSetFontRegular);
//        btnSetFontRegular.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_REGULAR);
//            }
//        });

//        Button btnSetFontNormal = (Button) v.findViewById(R.id.btnSetFontNormal);
//        btnSetFontNormal.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_NORMAL);
//            }
//        });
//
//        Button btnSetFontKannada = (Button) v.findViewById(R.id.btnSetFontkannada);
//        btnSetFontKannada.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                mBtp.setPrintFontStyle(BtpCommands.FONT_KANNADA);
//            }
//        });
//
//
//        Button btnSetFontDoubleWidth = (Button) v
//                .findViewById(R.id.btnSetFontDoubleWidth);
//        btnSetFontDoubleWidth.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_DOUBLE_WIDTH);
//            }
//        });
//
//        Button btnSetFontDoubleHeight = (Button) v
//                .findViewById(R.id.btnSetFontDoubleHeight);
//        btnSetFontDoubleHeight.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_DOUBLE_HEIGHT);
//            }
//        });
//
//        Button btnSetFontDoubleWidthAndHeight = (Button) v
//                .findViewById(R.id.btnSetFontDoubleWidthAndHeight);
//        btnSetFontDoubleWidthAndHeight
//                .setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View v) {
//                        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_DOUBLE_W_H);
//                    }
//                });



//        Button btnTestBill = (Button) v.findViewById(R.id.btnTestBill);
//        btnTestBill.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
////				mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_REGULAR);
////				mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_NORMAL);
////				mBtp.printLogo();
////				mBtp.printLineFeed();
////				mBtp.printTextLine("----------------------------------------");
////				mBtp.printTextLine("Item              Quantity        Price");
////				mBtp.printTextLine("----------------------------------------");
////				mBtp.printTextLine("Item 1                1            1.00");
////				mBtp.printTextLine("Some big item        10         7890.00");
////				mBtp.printTextLine("Next Item           999        10000.00");
////				mBtp.printLineFeed();
////				mBtp.printTextLine("----------------------------------------");
////				mBtp.printTextLine("Total                          17891.00");
////				mBtp.printTextLine("----------------------------------------");
////				mBtp.printLineFeed();
////				mBtp.printTextLine("         Thank you ! Visit Again");
////				mBtp.printLineFeed();
////				mBtp.printTextLine("****************************************");
////				mBtp.printLineFeed();
//
//            }
//        });
//
//        Button btnNumber = (Button) v.findViewById(R.id.btnNumber);
//        btnNumber.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                mBtp.printTextLine("          #   ");
//                mBtp.printTextLine("         ##   ");
//                mBtp.printTextLine("        # #   ");
//                mBtp.printTextLine("          #   ");
//                mBtp.printTextLine("          #   ");
//                mBtp.printTextLine("          #   ");
//                mBtp.printTextLine("        ##### ");
//            }
//        });
//
//        Button btnNumber1 = (Button) v.findViewById(R.id.btnNumber1);
//        btnNumber1.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                mBtp.printTextLine("          _    ");
//                mBtp.printTextLine("       _ (_)   ");
//                mBtp.printTextLine("      (_)(_)   ");
//                mBtp.printTextLine("         (_)   ");
//                mBtp.printTextLine("         (_)   ");
//                mBtp.printTextLine("         (_)   ");
//                mBtp.printTextLine("       _ (_) _ ");
//                mBtp.printTextLine("      (_)(_)(_)");
//                mBtp.printLineFeed();
//            }
//        });
//
//        Button btnAbtDriver = (Button) v.findViewById(R.id.btnAbtDriver);
//        btnAbtDriver.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                String s = mBtp.aboutDriver();
//                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        Button btnBatteryStatus = (Button) v.findViewById(R.id.btnBatteryStatus);
//        btnBatteryStatus.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                mBtp.GetBatteryStatus();
//            }
//        });

//        Button btnPaperStatus = (Button) v.findViewById(R.id.btnPaperStatus);
//        btnPaperStatus.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                mBtp.GetPaperStatus();
//            }
//        });
//
//        cbVolatile = (CheckBox) v.findViewById(R.id.checkBox1);

//        SeekBar seekBar = (SeekBar) v.findViewById(R.id.seekBar1);
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progresValue,
//                                          boolean fromUser) {
//                minutes = progresValue;
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                tvSeekBar.setText("Auto switch off idle Minutes: " + minutes);
//            }
//        });
//
//        tvSeekBar = (TextView) v.findViewById(R.id.tvSeekBar);
//        minutes = seekBar.getProgress();
//        tvSeekBar.setText("Auto switch off idle Minutes: " + minutes);
//
//        Button btnEnableAutoSwitchOff = (Button) v.findViewById(R.id.btnEnableAutoSwitchOff);
//        btnEnableAutoSwitchOff.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                mBtp.setAutoSwitchOffTime(minutes, cbVolatile.isChecked());
//            }
//        });
//
//        Button btnDisableAutoSwitchOff = (Button) v.findViewById(R.id.btnDisableAutoSwitchOff);
//        btnDisableAutoSwitchOff.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                mBtp.disableAutoSwitchOff();
//            }
//        });
//
//        Button btnLeftAlign = (Button) v.findViewById(R.id.btnLeftAlign);
//        btnLeftAlign.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                mBtp.setAlignment(BtpCommands.LEFT_ALIGN);
//            }
//        });
//
//        Button btnCenterAlign = (Button) v.findViewById(R.id.btnCenterAlign);
//        btnCenterAlign.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                mBtp.setAlignment(BtpCommands.CENTER_ALIGN);
//            }
//        });

    }

    @Override
    public void onClick(View view) {
//        String login_type= SharedPreferenceUtil.getLoginType(getActivity());

        switch (view.getId()) {

            case R.id.btnPrintText:
                if(report == 0){
                    if(mBtp.getState() == BluetoothPrinter.STATE_CONNECTED){
                        Toast.makeText(getActivity(), "Device Connected", Toast.LENGTH_SHORT).show();


                        mBtp.printLogo();
                        mBtp.printLineFeed();
                        mBtp.setAlignment(BtpCommands.CENTER_ALIGN);
                        mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_BOLD);
                        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_DOUBLE_W_H);
                        // mBtp.printTextLine("-------" + "CDMA" + "-------");
                        mBtp.printTextLine("-------" + "RECS" + "-------");
                        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_DOUBLE_WIDTH);
                        mBtp.printTextLine("Bill Receipt");
                        mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_REGULAR);
                        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_NORMAL);
                        // mBtp.printLineFeed();
                        // mBtp.printTextLine("Narsipatnam Muncipality");
                        mBtp.printLineFeed();
                        mBtp.setAlignment(BtpCommands.LEFT_ALIGN);
              /*  mBtp.printTextLine("సర్వీసు నెం  : "+getBillDetailsModel.getSCNO());
                mBtp.printTextLine("కస్టమర్ పేరు : "+getBillDetailsModel.getCSM_CONSUMER_NAME());
                mBtp.printTextLine("చిరునామా : "+getBillDetailsModel.getCSM_ADDRESS3());
                mBtp.printTextLine("బిల్లు తేది : "+getBillDetailsModel.getSCNO());
                mBtp.printTextLine("కేటగిరి : "+getBillDetailsModel.getCategory());
                mBtp.printTextLine("సబ్ కేటగిరి : "+getBillDetailsModel.getSubcategory());
                mBtp.printTextLine("లోడు : "+getBillDetailsModel.getCSM_CONNECTED_LOAD());
                mBtp.printTextLine("ఫేజు : "+getBillDetailsModel.getPHASE());
                mBtp.printTextLine("Meter Charges :"+getBillDetailsModel.getBurntValue());
                mBtp.printTextLine("రీడింగు  ప్రారంభ యూనిట్లు : "+getBillDetailsModel.getOPRDNG());
                mBtp.printTextLine("రీడింగు ముగింపు యూనిట్లు : "+getBillDetailsModel.getCLRDNG());
                mBtp.printTextLine("రీడింగు ముగింపు తేది : "+getBillDetailsModel.getSCNO());
                mBtp.printTextLine("గత నెల వినియోగము : "+getBillDetailsModel.getSCNO());
                mBtp.printTextLine("డయల్ విలువ : "+"");
                mBtp.printTextLine("యూనిట్లు : "+getBillDetailsModel.getUNITS());
                mBtp.printTextLine("ఇంధనపు చార్జీలు : "+getBillDetailsModel.getENGCHG());
                mBtp.printTextLine("కస్టమర్ చార్జీలు : "+getBillDetailsModel.getCUSCHG());
                mBtp.printTextLine("ఆలస్యంగా చెల్లించినందుకు సర్ చార్జ : "+"\u20B9 0");
                mBtp.printTextLine("విద్యుత్ సుంకము : "+getBillDetailsModel.getEDCHG());
                mBtp.printTextLine("ఎన్ కె టి :"+getBillDetailsModel.getNkt());
                mBtp.printTextLine("Arrears :"+getBillDetailsModel.getAdj_amount());
                mBtp.printTextLine("మొత్తం బిల్లు :"+getBillDetailsModel.getBILLAMT());
                mBtp.printTextLine("గడువు తేది         :"+"");
                mBtp.printTextLine("సరఫరా నిలిపివేయు తేది    :"+"");*/
                        mBtp.printTextLine("Bill No             : "+getBillDetailsModel.getSTATUS().substring(2));
                        mBtp.printTextLine("Service No          : "+getBillDetailsModel.getSCNO());
                        mBtp.printTextLine("Meter No            : "+getBillDetailsModel.getCSM_METER_NO());
                        mBtp.printTextLine("Consumer Name       : "+getBillDetailsModel.getCSM_CONSUMER_NAME());
                        mBtp.printTextLine("Address             : "+getBillDetailsModel.getCSM_ADDRESS3());
                        if(getBillDetailsModel.getCSM_AADHAAR_NO().length()>=12){
                            mBtp.printTextLine("Aadhar Number       : "+"XXXXXXXXX"+getBillDetailsModel.getCSM_AADHAAR_NO().substring(9));
                        }

                        if(getBillDetailsModel.getCSM_PHONE_NO().length() >=10){
                            mBtp.printTextLine("Phone Number        : "+"XXXXXXX"+getBillDetailsModel.getCSM_PHONE_NO().substring(7));
                        }else {
                            mBtp.printTextLine("Phone Number        : "+"NA");
                        }

                        mBtp.printTextLine("Bill Date           : "+getBillDetailsModel.getBILLDATE());
                        mBtp.printTextLine("Status Code         : "+getBillDetailsModel.getSTATUS().substring(0,2));
                        mBtp.printTextLine("Category            : "+getBillDetailsModel.getCategory());

                        if(getBillDetailsModel.getSubcategory().length()<2){
                            getBillDetailsModel.setSubcategory("0"+getBillDetailsModel.getSubcategory());
                        }
                        mBtp.printTextLine("Sub Category        : "+getBillDetailsModel.getSubcategory());
                        int category = Integer.parseInt(getBillDetailsModel.getCategory() + "0" + getBillDetailsModel.getSubcategory());
                        //kw
                        if(getBillDetailsModel.getCategory().equals("1")
                                || getBillDetailsModel.getCategory().equals("2")
                                || category == 601
                                || category == 602
                                || getBillDetailsModel.getCategory().equals("7")
                                || getBillDetailsModel.getCategory().equals("8")){

                            mBtp.printTextLine("Load                : "+getBillDetailsModel.getCSM_CONNECTED_LOAD()+" kw");
                        }else {
                            mBtp.printTextLine("Load                : "+getBillDetailsModel.getCSM_CONNECTED_LOAD()+" hp");
                        }

                        mBtp.printTextLine("Phase               : "+getBillDetailsModel.getPHASE());
                        if(getBillDetailsModel.getSTATUS().substring(0,2).equals("11")){
                            mBtp.printTextLine("Meter Charges       : "+getBillDetailsModel.getBurntValue());
                        }
                        mBtp.printTextLine("Start Reading Date : "+getBillDetailsModel.getOpmonth());
                        mBtp.printTextLine("Start Reading Units : "+getBillDetailsModel.getOPRDNG());
                        mBtp.printTextLine("End Reading Units   : "+getBillDetailsModel.getCLRDNG());
                        mBtp.printTextLine("End Reading Date    : "+getBillDetailsModel.getBILLDATE());
                        mBtp.printTextLine("Units               : "+getBillDetailsModel.getUNITS());
                        mBtp.printTextLine("Energy Charges      : Rs."+getBillDetailsModel.getENGCHG());

                        mBtp.printTextLine("Customer Charges    : Rs."+getBillDetailsModel.getCUSCHG());
                        mBtp.printTextLine("Surcharges          : Rs."+getBillDetailsModel.getSURCHRGS());
                        mBtp.printTextLine("Fixed Meter Charges : Rs."+getBillDetailsModel.getFIXEDCHG());
                        mBtp.printTextLine("Electrical Duty     : Rs."+getBillDetailsModel.getEDCHG());

                        String[] abc = getBillDetailsModel.getNkt().split(" ");


                        mBtp.printTextLine("NKT                 : Rs."+abc[0]);
                        if(abc.length>2){

                            mBtp.printTextLine("6AM to 10AM         :  "+abc[1]);
                            mBtp.printTextLine("6PM to 10PM         :  "+abc[2]);
                            mBtp.printTextLine("10PM to 6AM         :  "+abc[3]);
                        }
                        double totalAmount = 0;
                        if(getBillDetailsModel.getCSM_CASTE().equals("SC") || getBillDetailsModel.getCSM_CASTE().equals("ST")){

                            totalAmount= Double.parseDouble(getBillDetailsModel.getBILLAMT())- Double.parseDouble(getBillDetailsModel.getClose_bal())+ Double.parseDouble(getBillDetailsModel.getAdj_amount());


                        }else {
                            totalAmount = Double.parseDouble(getBillDetailsModel.getBILLAMT())- Double.parseDouble(getBillDetailsModel.getClose_bal());
                        }
//                int totalAmount = Integer.parseInt(getBillDetailsModel.getBILLAMT())- Integer.parseInt(getBillDetailsModel.getNEWARREARS());
//                int totalAmount = Integer.parseInt(getBillDetailsModel.getBILLAMT())- Integer.parseInt(getBillDetailsModel.getCLDEMAN());


//                mBtp.printTextLine("Arrears             : Rs."+getBillDetailsModel.getCLDEMAN());
                        mBtp.printTextLine("Total Bill Amount   : Rs."+ totalAmount);
                        if(getBillDetailsModel.getCSM_CASTE().equals("SC") || getBillDetailsModel.getCSM_CASTE().equals("ST"))
                            mBtp.printTextLine("Adjustment Amount   : Rs."+getBillDetailsModel.getAdj_amount());
                        mBtp.printTextLine("Arrears             : Rs."+getBillDetailsModel.getClose_bal());
                        mBtp.printTextLine("Grand Total Amount  : Rs."+getBillDetailsModel.getBILLAMT());
                        mBtp.printTextLine("Due Date            : "+getBillDetailsModel.getDUEDATE());
                        mBtp.printTextLine("Disconnection Date  : "+getBillDetailsModel.getDISCDATE());
                        mBtp.printTextLine("Last Paid Date      : "+getBillDetailsModel.getLPDATE());
                        mBtp.printTextLine("Last paid amount    : "+getBillDetailsModel.getLPAMT());
                        mBtp.printTextLine("Share Capital       : "+getBillDetailsModel.getCSM_SHARE());


//                        mBtp.printTextLine("Payment Id    : " + printDetails.getTxn_payment_id());
//                        mBtp.printTextLine("Name          : " + printDetails.getCustomer_name());
//                        mBtp.printTextLine("Due Date      : " + date);
//                        mBtp.printTextLine("Payment Date  : " + Utils.getCurrentDateTime());
//                        mBtp.printTextLine("Assessment No : " + printDetails.getLoan_number());
//                        mBtp.printTextLine("Pay Ref No    : " + printDetails.getPay_txn());
//                        mBtp.printTextLine("Pay Ref No    : " + printDetails.getPay_txn());
//                        mBtp.printTextLine("Tax Type      : " + t_type);
//                        mBtp.printTextLine("Payment Type  : " + p_type);
//                        mBtp.printTextLine("Bill Amount   : " + printDetails.getPaid_amount());
//                        mBtp.printTextLine("Bank Charges  : " + printDetails.getBank_charges());
//                        mBtp.printTextLine("Fine Amount   : " + printDetails.getPaid_over_due());
//                        mBtp.printTextLine("User Charges  : " + printDetails.getService_tax());
//                        mBtp.printTextLine("Total Amount  : " + printDetails.getTotal_amount());
                        mBtp.printLineFeed();
//                        mBtp.printTextLine("********Charges as per CDMA Norms********");
//                        mBtp.printTextLine("********Charges as per RECS Norms********");
//                        mBtp.printLineFeed();
                        mBtp.printTextLine("                             Signature");
                        mBtp.printLineFeed();
                        mBtp.printTextLine("                 Thank you                ");
                        mBtp.printTextLine(" *****************************************");
                        mBtp.printLineFeed();
                        mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_BOLD);
                        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_NORMAL);
                        mBtp.printTextLine(" Toll Free No: 1800 425 1539"); //1800 425 1539
//                       mBtp.printTextLine(" Contact: 8008612200/5500/3300");   //8008612200/5500/3300
                        mBtp.printTextLine(" Support: recsrevenue@gmail.com");
                        mBtp.printLineFeed();
                        mBtp.printLineFeed();
//
//                    }else if(login_type.equals(Constants.LOGIN_TYPE_GVMC)){
//
//
//                        mBtp.printLogo();
//                        mBtp.printLineFeed();
//                        mBtp.setAlignment(BtpCommands.CENTER_ALIGN);
//                        mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_BOLD);
//                        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_DOUBLE_W_H);
//                        mBtp.printTextLine("-------" + print_type + "-------");
//                        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_DOUBLE_WIDTH);
//                        mBtp.printTextLine("Payment Slip");
//                        mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_REGULAR);
//                        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_NORMAL);
//                        mBtp.printLineFeed();
//                        mBtp.setAlignment(BtpCommands.LEFT_ALIGN);
//
//                        System.out.println(responseMap.get("Payment Id     "));
//                        System.out.println(responseMap.get("Name           "));
//                        System.out.println(responseMap.get("Due Date       "));
//                        System.out.println(responseMap.get("Payment Date   "));
//                        System.out.println(responseMap.get("Assessment NO  "));
//                        System.out.println(responseMap.get("Tax Type       "));
//                        System.out.println(responseMap.get("Payment Type   "));
//                        System.out.println(responseMap.get("Bill Amount    "));
//                        System.out.println(responseMap.get("Bank Charges   "));
//                        System.out.println(responseMap.get("Fine Amount    "));
//                        System.out.println(responseMap.get("Total Amount   "));
//
//                        mBtp.printTextLine("Payment Id     : " + responseMap.get("Payment Id     "));
//                        mBtp.printTextLine("Name           : " + responseMap.get("Name           "));
//                        mBtp.printTextLine("Due Date       : " + responseMap.get("Due Date       "));
//                        mBtp.printTextLine("Payment Date   : " + responseMap.get("Payment Date   "));
//                        mBtp.printTextLine("Assessment No  : " + responseMap.get("Assessment NO  "));
//                        mBtp.printTextLine("Tax Type       : " + responseMap.get("Tax Type       "));
//                        mBtp.printTextLine("Payment Type   : " + responseMap.get("Payment Type   "));
//                        mBtp.printTextLine("Bill Amount    : " + responseMap.get("Bill Amount    "));
//                        mBtp.printTextLine("Fine Amount    : " + responseMap.get("Fine Amount    "));
//                        mBtp.printTextLine("Bank Charge    : " + responseMap.get("Bank Charges   "));
//                        mBtp.printTextLine("Total Amount   : " + responseMap.get("Total Amount   "));
//                        mBtp.printLineFeed();
//                        mBtp.printTextLine("********Charges as per GVMC Norms********");
//                        mBtp.printLineFeed();
//                        mBtp.printTextLine("                             Signature");
//                        mBtp.printLineFeed();
//                        mBtp.printTextLine("         Thank you Payment Sucess         ");
//                        mBtp.printTextLine(" *****************************************");
//                        mBtp.printLineFeed();
//                        mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_BOLD);
//                        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_NORMAL);
//                        mBtp.printTextLine(" Contact: 8008612200/8008615500");
//                        mBtp.printTextLine(" Support: helpdesk@anyemi.com");
//                        mBtp.printLineFeed();
//                        mBtp.printLineFeed();
//
//                    }else if(login_type.equals(Constants.LOGIN_TYPE_APEPDCL)){
//
//
//                        mBtp.printLogo();
//                        mBtp.printLineFeed();
//                        mBtp.setAlignment(BtpCommands.CENTER_ALIGN);
//                        mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_BOLD);
//                        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_DOUBLE_W_H);
//                        mBtp.printTextLine("-----" + print_type + "-----");
//                        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_DOUBLE_WIDTH);
//                        mBtp.printTextLine("Payment Slip");
//                        mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_REGULAR);
//                        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_NORMAL);
//                        mBtp.printLineFeed();
//                        mBtp.setAlignment(BtpCommands.LEFT_ALIGN);
//
//                        System.out.println(responseMap.get("Payment Id     "));
//                        System.out.println(responseMap.get("Payment Ref Id "));
//                        System.out.println(responseMap.get("Service Number "));
//                        System.out.println(responseMap.get("Name           "));
//                        System.out.println(responseMap.get("Bill Date      "));
//                        System.out.println(responseMap.get("Paid Date      "));
//                        System.out.println(responseMap.get("Payment Type   "));
//                        System.out.println(responseMap.get("Bill Amount    "));
//                        System.out.println(responseMap.get("Service Charge "));
//                        System.out.println(responseMap.get("Bank Charge    "));
//                        System.out.println(responseMap.get("Total Amount   "));
//
//                        mBtp.printTextLine("Payment Id     : " + responseMap.get("Payment Id     "));
//                        mBtp.printTextLine("Payment Ref Id : " + responseMap.get("Payment Ref Id "));
//                        mBtp.printTextLine("Service Number : " + responseMap.get("Service Number "));
//                        mBtp.printTextLine("Name           : " + responseMap.get("Name           "));
//                        mBtp.printTextLine("Bill Date      : " + responseMap.get("Bill Date      "));
//                        mBtp.printTextLine("Paid Date      : " + responseMap.get("Paid Date      "));
//                        mBtp.printTextLine("Payment Type   : " + responseMap.get("Payment Type   "));
//                        mBtp.printTextLine("Bill Amount    : " + responseMap.get("Bill Amount    "));
//                        mBtp.printTextLine("Service Charge : " + responseMap.get("Service Charge "));
//                        mBtp.printTextLine("Bank Charge    : " + responseMap.get("Bank Charge    "));
//                        mBtp.printTextLine("Total Amount   : " + responseMap.get("Total Amount   "));
//                        mBtp.printLineFeed();
//                        mBtp.printTextLine("********Charges as per APEPDCL Norms********");
//                        mBtp.printLineFeed();
//                        mBtp.printTextLine("                             Signature");
//                        mBtp.printLineFeed();
//                        mBtp.printTextLine("         Thank you Payment Sucess         ");
//                        mBtp.printTextLine(" *****************************************");
//                        mBtp.printLineFeed();
//                        mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_BOLD);
//                        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_NORMAL);
//                        mBtp.printTextLine(" Contact: 8008612200/8008615500");
//                        mBtp.printTextLine(" Support: helpdesk@anyemi.com");
//                        mBtp.printLineFeed();
//                        mBtp.printLineFeed();
//                    }
//
//
//                }else{
//                    Globals.showToast(getActivity(),"Error while reading data");
//                }



/*
                if (printDetails != null) {
                    if(login_type.equals(Constants.LOGIN_TYPE_COLLECTION_AGENT)|| login_type.equals(Constants.LOGIN_TYPE_CUSTOMER)){

                        String t_type = "";
                        String p_type = "";

//                        if (printDetails.getTax_type() != null) {
//                            int taxtype = aryTaxIds.indexOf(printDetails.getTax_type());
//                            t_type=(aryTaxNames.get(taxtype));
//                        }
                        if (printDetails.getPayment_type() != null) {
                            int taxtype = paymmentModesId.indexOf(printDetails.getPayment_type());
                            p_type=(paymmentModesNames.get(taxtype));
                        }

                        String date = Utils.parseDate(printDetails.getDue_date());

                       String p_total_amt="";
                       String p_ref="";

                       if (printDetails.getTotal_emi() != null) {
                           p_total_amt=printDetails.getTotal_emi();
                        }else if(printDetails.getTotal_amount() != null){
                           p_total_amt=printDetails.getTotal_amount();
                        }

                        if (printDetails.getPay_ref() != null) {
                            p_ref=printDetails.getPay_ref();
                        }else if(printDetails.getTotal_amount() != null){
                            p_ref="";
                        }


                        Double emi_amount=Double.parseDouble(printDetails.getTotal_amount());
                        Double bankCharges=Double.parseDouble(printDetails.getBank_charges());
                        Double user_charges=Double.parseDouble(printDetails.getService_charge());
                        Double emi_amount_without_charge=emi_amount-user_charges;
                        Double total_amount=emi_amount+bankCharges;


                        System.out.println("-------" + "RECS" + "-------");
                        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_DOUBLE_WIDTH);
                        System.out.println("Payment Slip");
                        System.out.println("Payment Id    : " + printDetails.getTxn_payment_id());
                        System.out.println("Pay Ref Id    : " + p_ref);
                        System.out.println("Service No    : " + printDetails.getLoan_number());
                        System.out.println("Name          : " + printDetails.getCustomer_name());
                        System.out.println("Due Date      : " + date);
                        System.out.println("Paid Date     : " + Utils.getCurrentDateTime());
                        System.out.println("Payment Type  : " + p_type);
                        System.out.println("Bill Amount   : " + Utils.parseAmount(String.valueOf(emi_amount_without_charge)));
                        System.out.println("User Charges  : " + Utils.parseAmount(printDetails.getService_charge()));
                        System.out.println("Bank Charges  : " + Utils.parseAmount(printDetails.getBank_charges()));
                        System.out.println("Total Amount  : " + Utils.parseAmount(String.valueOf(total_amount)));





                        mBtp.printLogo();
                        mBtp.printLineFeed();
                        mBtp.setAlignment(BtpCommands.CENTER_ALIGN);
                        mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_BOLD);
                        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_DOUBLE_W_H);
                        // mBtp.printTextLine("-------" + "CDMA" + "-------");
                        mBtp.printTextLine("-------" + "RECS" + "-------");
                        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_DOUBLE_WIDTH);
                        mBtp.printTextLine("Payment Slip");
                        mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_REGULAR);
                        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_NORMAL);
                        // mBtp.printLineFeed();
                        // mBtp.printTextLine("Narsipatnam Muncipality");
                        mBtp.printLineFeed();
                        mBtp.setAlignment(BtpCommands.LEFT_ALIGN);
                        mBtp.printTextLine("Payment Id    : " + printDetails.getTxn_payment_id());
                        if (printDetails.getPay_ref() != null) {
                            mBtp.printTextLine("Pay Ref Id    : " + printDetails.getPay_ref());
                        }
                        mBtp.printTextLine("Service No    : " + printDetails.getLoan_number());
                        mBtp.printTextLine("Name          : " + printDetails.getCustomer_name());
                        mBtp.printTextLine("Due Date      : " + date);
                        mBtp.printTextLine("Payment Date  : " + Utils.getCurrentDateTime());
                        mBtp.printTextLine("Payment Type  : " + p_type);
                        mBtp.printTextLine("Bill Amount   : " + "Rs. "+Utils.parseAmount(String.valueOf(emi_amount_without_charge))+" /-");
                        mBtp.printTextLine("User Charges  : " + "Rs. "+Utils.parseAmount(printDetails.getService_charge())+" /-");
                        mBtp.printTextLine("Bank Charges  : " + "Rs. "+Utils.parseAmount(printDetails.getBank_charges())+" /-");
                        mBtp.printTextLine("Total Amount  : " + "Rs. "+Utils.parseAmount(String.valueOf(total_amount))+" /-");
                        mBtp.printLineFeed();
                        //mBtp.printTextLine("********Charges as per CDMA Norms********");
                        mBtp.printTextLine("********Charges as per RECS Norms********");
                        mBtp.printLineFeed();
                        mBtp.printTextLine("                             Signature");
                        mBtp.printLineFeed();
                        mBtp.printTextLine("         Thank you Payment Success         ");
                        mBtp.printTextLine(" *****************************************");
                        mBtp.printLineFeed();
                        mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_BOLD);
                        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_NORMAL);
                        mBtp.printTextLine(" Contact: 8008612200/8008615500");
                        mBtp.printTextLine(" Support: helpdesk@anyemi.com");
                        mBtp.printLineFeed();
                        mBtp.printLineFeed();

                    }else if(login_type.equals(Constants.LOGIN_TYPE_GVMC)){


                        mBtp.printLogo();
                        mBtp.printLineFeed();
                        mBtp.setAlignment(BtpCommands.CENTER_ALIGN);
                        mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_BOLD);
                        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_DOUBLE_W_H);
                        mBtp.printTextLine("-------" + print_type + "-------");
                        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_DOUBLE_WIDTH);
                        mBtp.printTextLine("Payment Slip");
                        mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_REGULAR);
                        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_NORMAL);
                        mBtp.printLineFeed();
                        mBtp.setAlignment(BtpCommands.LEFT_ALIGN);

                        System.out.println(responseMap.get("Payment Id     "));
                        System.out.println(responseMap.get("Name           "));
                        System.out.println(responseMap.get("Due Date       "));
                        System.out.println(responseMap.get("Payment Date   "));
                        System.out.println(responseMap.get("Assessment NO  "));
                        System.out.println(responseMap.get("Tax Type       "));
                        System.out.println(responseMap.get("Payment Type   "));
                        System.out.println(responseMap.get("Bill Amount    "));
                        System.out.println(responseMap.get("Bank Charges   "));
                        System.out.println(responseMap.get("Fine Amount    "));
                        System.out.println(responseMap.get("Total Amount   "));

                        mBtp.printTextLine("Payment Id     : " + responseMap.get("Payment Id     "));
                        mBtp.printTextLine("Name           : " + responseMap.get("Name           "));
                        mBtp.printTextLine("Due Date       : " + responseMap.get("Due Date       "));
                        mBtp.printTextLine("Payment Date   : " + responseMap.get("Payment Date   "));
                        mBtp.printTextLine("Assessment No  : " + responseMap.get("Assessment NO  "));
                        mBtp.printTextLine("Tax Type       : " + responseMap.get("Tax Type       "));
                        mBtp.printTextLine("Payment Type   : " + responseMap.get("Payment Type   "));
                        mBtp.printTextLine("Bill Amount    : " + responseMap.get("Bill Amount    "));
                        mBtp.printTextLine("Fine Amount    : " + responseMap.get("Fine Amount    "));
                        mBtp.printTextLine("Bank Charge    : " + responseMap.get("Bank Charges   "));
                        mBtp.printTextLine("Total Amount   : " + responseMap.get("Total Amount   "));
                        mBtp.printLineFeed();
                        mBtp.printTextLine("********Charges as per GVMC Norms********");
                        mBtp.printLineFeed();
                        mBtp.printTextLine("                             Signature");
                        mBtp.printLineFeed();
                        mBtp.printTextLine("         Thank you Payment Sucess         ");
                        mBtp.printTextLine(" *****************************************");
                        mBtp.printLineFeed();
                        mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_BOLD);
                        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_NORMAL);
                        mBtp.printTextLine(" Contact: 8008612200/8008615500");
                        mBtp.printTextLine(" Support: helpdesk@anyemi.com");
                        mBtp.printLineFeed();
                        mBtp.printLineFeed();

                    }else if(login_type.equals(Constants.LOGIN_TYPE_APEPDCL)){


                        mBtp.printLogo();
                        mBtp.printLineFeed();
                        mBtp.setAlignment(BtpCommands.CENTER_ALIGN);
                        mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_BOLD);
                        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_DOUBLE_W_H);
                        mBtp.printTextLine("-----" + print_type + "-----");
                        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_DOUBLE_WIDTH);
                        mBtp.printTextLine("Payment Slip");
                        mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_REGULAR);
                        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_NORMAL);
                        mBtp.printLineFeed();
                        mBtp.setAlignment(BtpCommands.LEFT_ALIGN);

                        System.out.println(responseMap.get("Payment Id     "));
                        System.out.println(responseMap.get("Payment Ref Id "));
                        System.out.println(responseMap.get("Service Number "));
                        System.out.println(responseMap.get("Name           "));
                        System.out.println(responseMap.get("Bill Date      "));
                        System.out.println(responseMap.get("Paid Date      "));
                        System.out.println(responseMap.get("Payment Type   "));
                        System.out.println(responseMap.get("Bill Amount    "));
                        System.out.println(responseMap.get("Service Charge "));
                        System.out.println(responseMap.get("Bank Charge    "));
                        System.out.println(responseMap.get("Total Amount   "));

                        mBtp.printTextLine("Payment Id     : " + responseMap.get("Payment Id     "));
                        mBtp.printTextLine("Payment Ref Id : " + responseMap.get("Payment Ref Id "));
                        mBtp.printTextLine("Service Number : " + responseMap.get("Service Number "));
                        mBtp.printTextLine("Name           : " + responseMap.get("Name           "));
                        mBtp.printTextLine("Bill Date      : " + responseMap.get("Bill Date      "));
                        mBtp.printTextLine("Paid Date      : " + responseMap.get("Paid Date      "));
                        mBtp.printTextLine("Payment Type   : " + responseMap.get("Payment Type   "));
                        mBtp.printTextLine("Bill Amount    : " + responseMap.get("Bill Amount    "));
                        mBtp.printTextLine("Service Charge : " + responseMap.get("Service Charge "));
                        mBtp.printTextLine("Bank Charge    : " + responseMap.get("Bank Charge    "));
                        mBtp.printTextLine("Total Amount   : " + responseMap.get("Total Amount   "));
                        mBtp.printLineFeed();
                        mBtp.printTextLine("********Charges as per APEPDCL Norms********");
                        mBtp.printLineFeed();
                        mBtp.printTextLine("                             Signature");
                        mBtp.printLineFeed();
                        mBtp.printTextLine("         Thank you Payment Sucess         ");
                        mBtp.printTextLine(" *****************************************");
                        mBtp.printLineFeed();
                        mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_BOLD);
                        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_NORMAL);
                        mBtp.printTextLine(" Contact: 8008612200/8008615500");
                        mBtp.printTextLine(" Support: helpdesk@anyemi.com");
                        mBtp.printLineFeed();
                        mBtp.printLineFeed();
                    }


                }else{
                    Globals.showToast(getActivity(),"Error while reading data");
                }
*/
                        mListener.onFragmentInteractionC();
                    }else {
                        Toast.makeText(getActivity(), " Device Not Connected", Toast.LENGTH_SHORT).show();
                    }

                }else if(report == 5){
                    if(title.equals("TotalAmount") || title.equals("TotalUnits")){
                        Toast.makeText(getActivity(), ""+value, Toast.LENGTH_SHORT).show();
                        if(mBtp.getState() == BluetoothPrinter.STATE_CONNECTED){
                            if(value != null){
                                mBtp.printLogo();
                                mBtp.printLineFeed();
                                mBtp.setAlignment(BtpCommands.CENTER_ALIGN);
                                mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_BOLD);
                                mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_DOUBLE_W_H);
                                // mBtp.printTextLine("-------" + "CDMA" + "-------");
                                mBtp.printTextLine("-------" + "RECS" + "-------");
                                mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_DOUBLE_WIDTH);
                                mBtp.printTextLine("Report");
                                mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_REGULAR);
                                mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_NORMAL);
                                // mBtp.printLineFeed();
                                // mBtp.printTextLine("Narsipatnam Muncipality");
                                mBtp.printLineFeed();
                                mBtp.setAlignment(BtpCommands.LEFT_ALIGN);

                                mBtp.printTextLine("Category          : "+category);
                                mBtp.printTextLine("Value             : "+value);

                                mBtp.printLineFeed();
                                mBtp.printTextLine("                 Thank you                ");
                                mBtp.printTextLine(" *****************************************");
                                mBtp.printLineFeed();
                                mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_BOLD);
                                mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_NORMAL);
                                mBtp.printTextLine(" Toll Free No: 1800 425 1539"); //1800 425 1539
//                       mBtp.printTextLine(" Contact: 8008612200/5500/3300");   //8008612200/5500/3300
                                mBtp.printTextLine(" Support: recsrevenue@gmail.com");
                                mBtp.printLineFeed();
                            }
                        }else {
                            Toast.makeText(getActivity(), " Device Not Connected", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        if(serviceNoList != null && serviceNoList.size()>0){
                            Toast.makeText(getActivity(), "data count"+serviceNoList.size(), Toast.LENGTH_SHORT).show();
                        }else
                            Toast.makeText(getActivity(), "null data", Toast.LENGTH_SHORT).show();
                        if(mBtp.getState() == BluetoothPrinter.STATE_CONNECTED){
                            if(serviceNoList != null){
                                Toast.makeText(getActivity(), "Device Connected", Toast.LENGTH_SHORT).show();
//                            Log.e("print fragment",new Gson().toJson(serviceNoList));

                                mBtp.printLogo();
                                mBtp.printLineFeed();
                                mBtp.setAlignment(BtpCommands.CENTER_ALIGN);
                                mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_BOLD);
                                mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_DOUBLE_W_H);
                                // mBtp.printTextLine("-------" + "CDMA" + "-------");
                                mBtp.printTextLine("-------" + "RECS" + "-------");
                                mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_DOUBLE_WIDTH);
                                mBtp.printTextLine("Report");
                                mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_REGULAR);
                                mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_NORMAL);
                                // mBtp.printLineFeed();
                                // mBtp.printTextLine("Narsipatnam Muncipality");
                                mBtp.printLineFeed();
                                mBtp.setAlignment(BtpCommands.LEFT_ALIGN);

                                for(ServiceNo serviceNo: serviceNoList ){
                                    mBtp.printTextLine("Service No          : "+serviceNo.getSCNO());
                                }

                                mBtp.printLineFeed();
                                mBtp.printTextLine("                 Thank you                ");
                                mBtp.printTextLine(" *****************************************");
                                mBtp.printLineFeed();
                                mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_BOLD);
                                mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_NORMAL);
                                mBtp.printTextLine(" Toll Free No: 1800 425 1539"); //1800 425 1539
//                       mBtp.printTextLine(" Contact: 8008612200/5500/3300");   //8008612200/5500/3300
                                mBtp.printTextLine(" Support: recsrevenue@gmail.com");
                                mBtp.printLineFeed();
                            }


                        }else {
                            Toast.makeText(getActivity(), " Device Not Connected", Toast.LENGTH_SHORT).show();
                        }
                    }


                }else {

                    if(serviceNumberList != null && serviceNumberList.size()>0){

                        Toast.makeText(getActivity(), "Number Of Record: "+serviceNumberList.size(), Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(getActivity(), "null data 3", Toast.LENGTH_SHORT).show();

                    if(mBtp.getState() == BluetoothPrinter.STATE_CONNECTED){
                        if(serviceNumberList != null){
                            Toast.makeText(getActivity(), "Device Connected", Toast.LENGTH_SHORT).show();
                            Log.e("print fragment",new Gson().toJson(serviceNumberList));

                            mBtp.printLogo();
                            mBtp.printLineFeed();
                            mBtp.setAlignment(BtpCommands.CENTER_ALIGN);
                            mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_BOLD);
                            mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_DOUBLE_W_H);
                            // mBtp.printTextLine("-------" + "CDMA" + "-------");
                            mBtp.printTextLine("-------" + "RECS" + "-------");
                            mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_DOUBLE_WIDTH);
                            mBtp.printTextLine("UnBilled Report");
                            mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_REGULAR);
                            mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_NORMAL);
                            // mBtp.printLineFeed();
                            // mBtp.printTextLine("Narsipatnam Muncipality");
                            mBtp.printLineFeed();
                            mBtp.setAlignment(BtpCommands.LEFT_ALIGN);

                            for(ServiceNumber serviceNo: serviceNumberList ){
                                mBtp.printTextLine("Category: "+serviceNo.getCategory()+" & No Of Services: "+ serviceNo.getValue());
                            }

                            mBtp.printLineFeed();
                            mBtp.printTextLine("                 Thank you                ");
                            mBtp.printTextLine(" *****************************************");
                            mBtp.printLineFeed();
                            mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_BOLD);
                            mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_NORMAL);
                            mBtp.printTextLine(" Toll Free No: 1800 425 1539"); //1800 425 1539
//                       mBtp.printTextLine(" Contact: 8008612200/5500/3300");   //8008612200/5500/3300
                            mBtp.printTextLine(" Support: recsrevenue@gmail.com");
                            mBtp.printLineFeed();
                        }

                    }else {
                        Toast.makeText(getActivity(), " Device Not Connected", Toast.LENGTH_SHORT).show();
                    }

                }

//                if (printDetails != null) {
//                    if(login_type.equals(Constants.LOGIN_TYPE_CDMA)){
//
//                        String t_type = "";
//                        String p_type = "";
//
//                        if (printDetails.getTax_type() != null) {
//                            int taxtype = aryTaxIds.indexOf(printDetails.getTax_type());
//                            t_type=(aryTaxNames.get(taxtype));
//                        }
//
//                        if (printDetails.getPayment_type() != null) {
//                            int taxtype = paymmentModesId.indexOf(printDetails.getPayment_type());
//                            p_type=(paymmentModesNames.get(taxtype));
//                        }
//
//                        String date = Utils.parseDate(printDetails.getDuedates());
//
                break;
            case R.id.btnPrint:
                try {
                    // woyouService.printerSelfChecking(callback);//这里使用的AIDL方式打印
                    woyouService.printTextWithFont("-------" + "RECS" + "-------;\n","",36,callback);
                    woyouService.printTextWithFont("Bill Receipt; \n","",36,callback);
                    woyouService.printTextWithFont("Bill No             : "+getBillDetailsModel.getSTATUS().substring(2)+";\n","",36,callback);
                    woyouService.printTextWithFont("Service No          : "+getBillDetailsModel.getSCNO()+";\n","",36,callback);
                    woyouService.printTextWithFont("Meter No            : "+getBillDetailsModel.getCSM_METER_NO()+"\n","",36,callback);
                    woyouService.printTextWithFont("Consumer Name       : "+getBillDetailsModel.getCSM_CONSUMER_NAME()+" ;\n","",36,callback);
                    woyouService.printTextWithFont("Address             : "+getBillDetailsModel.getCSM_ADDRESS3()+"\n","",36,callback);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                break;
        }

    }

    public interface OnFragmentInteractionListenerC {
        void onFragmentInteractionC();

    }
    ICallback callback = new ICallback.Stub() {

        @Override
        public void onRunResult(boolean success) throws RemoteException {
        }

        @Override
        public void onReturnString(final String value) throws RemoteException {
        }

        @Override
        public void onRaiseException(int code, final String msg)
                throws RemoteException {
        }

        @Override
        public void onPrintResult(int code, String msg) throws RemoteException {

        }
    };

}

