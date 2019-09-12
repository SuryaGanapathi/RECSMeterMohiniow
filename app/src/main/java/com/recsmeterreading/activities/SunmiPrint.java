package com.recsmeterreading.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.recsmeterreading.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import woyou.aidlservice.jiuiv5.ICallback;
import woyou.aidlservice.jiuiv5.IWoyouService;

public class SunmiPrint extends AppCompatActivity {

    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = (TextView) findViewById(R.id.txtHello);
        Button btn = (Button) findViewById(R.id.button);
        Button btn2 = (Button) findViewById(R.id.button2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    // woyouService.printerSelfChecking(callback);//这里使用的AIDL方式打印
                    woyouService.printTextWithFont(" सुन्मी इंडिया प्राइवेट लिमिटेड \n","",36,callback);
                    woyouService.printTextWithFont(" Augusta point; \n","",36,callback);
                    woyouService.printTextWithFont(" 2nd floor,Sec 53;\n","",36,callback);
                    woyouService.printTextWithFont(" Golf course road;\n","",36,callback);
                    woyouService.printTextWithFont(" गुड़गांव-122001;\n","",36,callback);
                    woyouService.printTextWithFont(" हरियाणा;\n","",36,callback);
                    woyouService.printTextWithFont(" भारत.\n\n\n\n","",36,callback);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /**

                 *

                 *Creating a Intent at where you want start scanner, calling the scanner by startActiityForResult();

                 */

                Intent intent = new Intent("com.summi.scan");

                intent.setPackage("com.sunmi.sunmiqrcodescanner");



                /**

                 * The method is the same function as above

                 *Intent intent = new Intent("com.summi.scan");

                 *intent.setClassName("com.sunmi.sunmiqrcodescanner", "com.sunmi.sunmiqrcodescanner.activity.ScanActivity");

                 */


                /**


                 //there is also some options item about the scanner module, you can transfer parameters to control some settings, each item has a defaut status,transform parameter is not necessary,

                 intent.putExtra("CURRENT_PPI", 0X0003);//The current preview resolution ,PPI_1920_1080 = 0X0001;PPI_1280_720 = 0X0002;PPI_BEST = 0X0003;

                 intent.putExtra("PLAY_SOUND", true);// Prompt tone after scanning  ,default true

                 intent.putExtra("PLAY_VIBRATE", false);//vibrate after scanning,default false,only support M1 right now.

                 intent.putExtra("IDENTIFY_INVERSE_QR_CODE", true);//Whether to identify inverse code

                 intent.putExtra("IDENTIFY_MORE_CODE", false);// Whether to identify several code，default false

                 intent.putExtra("IS_SHOW_SETTING", true);// Wether display set up button  at the top-right corner，default true

                 intent.putExtra("IS_SHOW_ALBUM", true);// Wether display album，default true

                 */

                startActivityForResult(intent, 1);


            }
        });


        Intent intent = new Intent();
        intent.setPackage("woyou.aidlservice.jiuiv5");
        intent.setAction("woyou.aidlservice.jiuiv5.IWoyouService");
        startService(intent);//启动打印服务
        bindService(intent, connService, Context.BIND_AUTO_CREATE);
    }

    private IWoyouService woyouService;

    private ServiceConnection connService = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            woyouService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            woyouService = IWoyouService.Stub.asInterface(service);
        }
    };

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && data != null) {
            Bundle bundle = data.getExtras();
            ArrayList<HashMap<String, String>> result = (ArrayList<HashMap<String, String>>) bundle
                    .getSerializable("data");

            Iterator<HashMap<String, String>> it = result.iterator();

            while (it.hasNext()) {
                HashMap<String, String> hashMap = it.next();

                Log.i("sunmi", hashMap.get("TYPE"));//this is the type of the code
                Log.i("sunmi", hashMap.get("VALUE"));//this is the result of the code

                Toast.makeText(MainActivity.this,""+hashMap.get("VALUE"), Toast.LENGTH_LONG);
                txt.setText(hashMap.get("VALUE"));

            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
