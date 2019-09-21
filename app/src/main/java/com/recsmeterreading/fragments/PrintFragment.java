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
    private ICallback callback=BluetoothPrinterMain.callback;
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

                        mBtp.printLineFeed();
                        mBtp.setAlignment(BtpCommands.LEFT_ALIGN);
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


                        mBtp.printLineFeed();
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

                break;
            case R.id.btnPrint:
                if(report == 0) {
                    if(woyouService!=null)
                    try {
                        // woyouService.printerSelfChecking(callback);//这里使用的AIDL方式打印
                        woyouService.printTextWithFont("-------" + "RECS" + "-------\n", "BOLD", 30, callback);
                        woyouService.printTextWithFont("Bill Receipt\n", "", 36, callback);
                        woyouService.printTextWithFont("Bill No             : " + getBillDetailsModel.getSTATUS().substring(2) + "\n", "", 16, callback);
                        woyouService.printTextWithFont("Service No          : " + getBillDetailsModel.getSCNO() + "\n", "", 16, callback);
                        woyouService.printTextWithFont("Meter No            : " + getBillDetailsModel.getCSM_METER_NO() + "\n", "", 16, callback);
                        woyouService.printTextWithFont("Consumer Name       : " + getBillDetailsModel.getCSM_CONSUMER_NAME() + " \n", "", 16, callback);
                        woyouService.printTextWithFont("Address             : " + getBillDetailsModel.getCSM_ADDRESS3() + "\n", "", 16, callback);
                        if(getBillDetailsModel.getCSM_AADHAAR_NO().length()>=12){
                            woyouService.printTextWithFont("Aadhar Number       : "+"XXXXXXXXX"+getBillDetailsModel.getCSM_AADHAAR_NO().substring(9)+ "\n", "", 16, callback);

                        }

                        if(getBillDetailsModel.getCSM_PHONE_NO().length() >=10){
                            woyouService.printTextWithFont("Phone Number        : "+"XXXXXXX"+getBillDetailsModel.getCSM_PHONE_NO().substring(7)+ "\n", "", 16, callback);

                        }else {
                            woyouService.printTextWithFont("Phone Number        : "+"NA" + "\n", "", 16, callback);
                            }
                        woyouService.printTextWithFont("Bill Date           : "+getBillDetailsModel.getBILLDATE() + "\n", "", 16, callback);
                        woyouService.printTextWithFont("Status Code         : "+getBillDetailsModel.getSTATUS().substring(0,2)+ "\n", "", 16, callback);
                        woyouService.printTextWithFont("Category            : "+getBillDetailsModel.getCategory() + "\n", "", 16, callback);
                        if(getBillDetailsModel.getSubcategory().length()<2){
                            getBillDetailsModel.setSubcategory("0"+getBillDetailsModel.getSubcategory());

                        }
                        woyouService.printTextWithFont("Sub Category        : "+getBillDetailsModel.getSubcategory() + "\n", "", 16, callback);

                        int category = Integer.parseInt(getBillDetailsModel.getCategory() + "0" + getBillDetailsModel.getSubcategory());
                        //kw
                        if(getBillDetailsModel.getCategory().equals("1")
                                || getBillDetailsModel.getCategory().equals("2")
                                || category == 601
                                || category == 602
                                || getBillDetailsModel.getCategory().equals("7")
                                || getBillDetailsModel.getCategory().equals("8")){
                            woyouService.printTextWithFont("Load                : "+getBillDetailsModel.getCSM_CONNECTED_LOAD()+" kw" + "\n", "", 16, callback);

                        }else {
                            woyouService.printTextWithFont("Load                : "+getBillDetailsModel.getCSM_CONNECTED_LOAD()+" hp" + "\n", "", 16, callback);
                        }
                        woyouService.printTextWithFont("Phase               : "+getBillDetailsModel.getPHASE() + "\n", "", 16, callback);

                        if(getBillDetailsModel.getSTATUS().substring(0,2).equals("11")){
                            woyouService.printTextWithFont("Meter Charges       : "+getBillDetailsModel.getBurntValue() + "\n", "", 16, callback);
                            }
                        woyouService.printTextWithFont("Start Reading Date : "+getBillDetailsModel.getOpmonth() + "\n", "", 16, callback);
                        woyouService.printTextWithFont("Start Reading Units : "+getBillDetailsModel.getOPRDNG() + "\n", "", 16, callback);
                        woyouService.printTextWithFont("End Reading Units : "+getBillDetailsModel.getCLRDNG() + "\n", "", 16, callback);
                        woyouService.printTextWithFont("End Reading Date : "+getBillDetailsModel.getBILLDATE() + "\n", "", 16, callback);
                        woyouService.printTextWithFont("Units               : "+getBillDetailsModel.getUNITS() + "\n", "", 16, callback);
                        woyouService.printTextWithFont("Energy Charges      : Rs."+getBillDetailsModel.getENGCHG() + "\n", "", 16, callback);
                        woyouService.printTextWithFont("Customer Charges    : Rs."+getBillDetailsModel.getCUSCHG()+ "\n", "", 16, callback);
                        woyouService.printTextWithFont("Surcharges          : Rs."+getBillDetailsModel.getSURCHRGS()+ "\n", "", 16, callback);
                        woyouService.printTextWithFont("Fixed Meter Charges : Rs."+getBillDetailsModel.getFIXEDCHG()+ "\n", "", 16, callback);
                        woyouService.printTextWithFont("Electrical Duty     : Rs."+getBillDetailsModel.getEDCHG()+ "\n", "", 16, callback);
                        String[] abc = getBillDetailsModel.getNkt().split(" ");

                        woyouService.printTextWithFont("NKT                 : Rs."+abc[0]+ "\n", "", 16, callback);

                        if(abc.length>2){
                            woyouService.printTextWithFont("6AM to 10AM         :  "+abc[1]+ "\n", "", 16, callback);
                            woyouService.printTextWithFont("6AM to 10AM         :  "+abc[2]+ "\n", "", 16, callback);
                            woyouService.printTextWithFont("10PM to 6AM          :  "+abc[3]+ "\n", "", 16, callback);

                        }
                        double totalAmount = 0;
                        if(getBillDetailsModel.getCSM_CASTE().equals("SC") || getBillDetailsModel.getCSM_CASTE().equals("ST")){

                            totalAmount= Double.parseDouble(getBillDetailsModel.getBILLAMT())- Double.parseDouble(getBillDetailsModel.getClose_bal())+ Double.parseDouble(getBillDetailsModel.getAdj_amount());


                        }else {
                            totalAmount = Double.parseDouble(getBillDetailsModel.getBILLAMT())- Double.parseDouble(getBillDetailsModel.getClose_bal());
                        }
                        woyouService.printTextWithFont("Total Bill Amount   : Rs."+ totalAmount+ "\n", "", 16, callback);

                        if(getBillDetailsModel.getCSM_CASTE().equals("SC") || getBillDetailsModel.getCSM_CASTE().equals("ST"))
                            woyouService.printTextWithFont("Adjustment Amount   : Rs."+getBillDetailsModel.getAdj_amount()+ "\n", "", 16, callback);
                        woyouService.printTextWithFont("Arrears             : Rs."+getBillDetailsModel.getClose_bal()+ "\n", "", 16, callback);
                        woyouService.printTextWithFont("Grand Total Amount  : Rs."+getBillDetailsModel.getBILLAMT()+ "\n", "", 16, callback);
                        woyouService.printTextWithFont("Due Date            : "+getBillDetailsModel.getDUEDATE()+ "\n", "", 16, callback);
                        woyouService.printTextWithFont("Disconnection Date  : "+getBillDetailsModel.getDISCDATE()+ "\n", "", 16, callback);
                        woyouService.printTextWithFont("Last Paid Date      : "+getBillDetailsModel.getLPDATE()+ "\n", "", 16, callback);
                        woyouService.printTextWithFont("Last paid amount    : "+getBillDetailsModel.getLPAMT()+ "\n\n", "", 16, callback);
                        woyouService.printTextWithFont("Share Capital       : "+getBillDetailsModel.getCSM_SHARE()+ "\n", "", 16, callback);
                        woyouService.printTextWithFont("                             Signature"+ "\n", "", 16, callback);
                        woyouService.printTextWithFont("                 Thank you                "+ "\n", "", 16, callback);
                        woyouService.printTextWithFont(" *****************************************"+ "\n", "", 16, callback);
                        woyouService.printTextWithFont(" Toll Free No: 1800 425 1539"+ "\n", "", 16, callback);
                        woyouService.printTextWithFont(" Support: recsrevenue@gmail.com"+ "\n", "", 16, callback);


                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }else  if(report == 5){
                    if(woyouService!=null)
                    try {
                    if(title.equals("TotalAmount") || title.equals("TotalUnits")) {
                            woyouService.printTextWithFont("-------" + "RECS" + "-------;\n\n", "", 36, callback);
                            woyouService.printTextWithFont("Report \n", "", 36, callback);
                            woyouService.printTextWithFont(   "Category          : "+category+";\n", "", 36, callback);
                            woyouService.printTextWithFont(      "                 Thank you                "+";\n", "", 36, callback);
                        woyouService.printTextWithFont(" *****************************************"+ "\n", "", 16, callback);
                        woyouService.printTextWithFont(" Toll Free No: 1800 425 1539"+ "\n", "", 16, callback);
                        woyouService.printTextWithFont(" Support: recsrevenue@gmail.com"+ "\n", "", 16, callback);



                    }else {
                        if(serviceNoList != null && serviceNoList.size()>0){
                            Toast.makeText(getActivity(), "data count"+serviceNoList.size(), Toast.LENGTH_SHORT).show();
                        }else
                            Toast.makeText(getActivity(), "null data", Toast.LENGTH_SHORT).show();
                        if(serviceNoList != null){
                            woyouService.printTextWithFont("-------" + "RECS" + "-------\n", "", 16, callback);
                            woyouService.printTextWithFont("Report \n", "", 16, callback);
                            for(ServiceNo serviceNo: serviceNoList ){
                                woyouService.printTextWithFont(      "Service No          : "+serviceNo.getSCNO()+"\n", "", 16, callback);

                            }
                            woyouService.printTextWithFont(      "                 Thank you                "+"\n", "", 16, callback);

                            woyouService.printTextWithFont(" *****************************************"+ "\n", "", 16, callback);
                            woyouService.printTextWithFont(" Toll Free No: 1800 425 1539"+ "\n", "", 16, callback);
                            woyouService.printTextWithFont(" Support: recsrevenue@gmail.com"+ "\n", "", 16, callback);


                        }
                    }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                }else {
                    if(serviceNumberList != null && serviceNumberList.size()>0){

                        Toast.makeText(getActivity(), "Number Of Record: "+serviceNumberList.size(), Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(getActivity(), "null data 3", Toast.LENGTH_SHORT).show();
                    if(serviceNumberList != null){
                        if(woyouService!=null)
                        try {
                            woyouService.printTextWithFont("-------" + "RECS" + "-------;\n", "", 16, callback);
                            woyouService.printTextWithFont("Unbilled Report;\n", "", 16, callback);
                            for(ServiceNumber serviceNo: serviceNumberList ){
                                woyouService.printTextWithFont("Category: "+serviceNo.getCategory()+" & No Of Services: "+ serviceNo.getValue()+";\n", "", 16, callback);

                            }
                            woyouService.printTextWithFont("                 Thank you                "+";\n", "", 16, callback);

                            woyouService.printTextWithFont(" *****************************************"+ "\n", "", 16, callback);
                            woyouService.printTextWithFont(" Toll Free No: 1800 425 1539"+ "\n", "", 16, callback);
                            woyouService.printTextWithFont(" Support: recsrevenue@gmail.com"+ "\n", "", 16, callback);


                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }

                break;
        }

    }

    public interface OnFragmentInteractionListenerC {
        void onFragmentInteractionC();

    }

}

