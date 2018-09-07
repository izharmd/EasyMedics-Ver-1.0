package com.wakeup.xxx;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daasuu.ahp.AnimateHorizontalProgressBar;
import com.weike.chiginon.BroadcastCommand;
import com.weike.chiginon.BroadcastData;
import com.weike.chiginon.DataPacket;
import com.weike.manager.CommandManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


public class HeartRateActivity extends AppCompatActivity {
    private CommandManager manager;

    AnimateHorizontalProgressBar heart_progress_bar, sys_progress_bar, dia_progress_bar;
    ImageView imv_plush;


    private final BroadcastReceiver BLEStatusChangeReceiver = new BroadcastReceiver() {
        @SuppressLint("UseValueOf")
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BroadcastCommand.ACTION_DATA_AVAILABLE)) {
                BroadcastData bData = (BroadcastData) intent
                        .getSerializableExtra(BroadcastData.keyword);
                if (bData.commandID == BroadcastCommand.BLE_SEND_DATA) {
                    DataPacket dataPacket = (DataPacket) bData.data;
                    ArrayList<Byte> datas = dataPacket.data;

                    //byte ---> int
                    ArrayList<Integer> data = new ArrayList<>();
                    for (int i = 0; i < datas.size(); i++) {
                        int ii = datas.get(i) & 0xff;
                        data.add(ii);
                    }

                    StringBuffer stringBuffer = new StringBuffer();
                    // stringBuffer.append("Received data");
                    for (int i = 0; i < data.size(); i++) {
                        stringBuffer.append(data.get(i) + " ");
                        //  Toast.makeText(context, "rtyui", Toast.LENGTH_LONG).show();

                    }
                    //   Toast.makeText(context, stringBuffer.toString(), Toast.LENGTH_LONG).show();

                    //battery power
                    if (data.get(0) == 0x91) {

                    }

                    //Bracelet version information
                    if (data.get(0) == 0x92) {

                    }

                    //The first package of integer data
                    if (data.get(0) == 0X51 && data.get(1) == 0x20) {

                    }

                    //The second package of integer data
                    if (dataPacket.commandID == 0) {

                    }

                    //Single, real-time measurement data
                    if (data.get(0) == 0x31) {
                        //Single measurement
                        if (data.get(1) == 0x09 || data.get(1) == 0x11 || data.get(1) == 0x21) {
                            if (data.get(1) == 0x09) {

                                // Toast.makeText(MainActivity.this,"0x31",Toast.LENGTH_SHORT).show();
                                //Heart rate
                                Toast.makeText(context, stringBuffer.toString(), Toast.LENGTH_LONG).show();
                            } else if (data.get(1) == 0x11) {

                                //Toast.makeText(MainActivity.this,"0x11",Toast.LENGTH_SHORT).show();
                                //Blood oxygen

                            } else if (data.get(1) == 0x21) {
                                //blood pressure

                                Toast.makeText(HeartRateActivity.this,"0x21",Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            //Real-time measurement
                            if (data.get(1) == 0x0A) {
                                //Heart rate
                                String heartRate = stringBuffer.substring(5, 8);
                                //textResult.setText("Heart Rate :"+heartRate);
                                Toast.makeText(context, heartRate, Toast.LENGTH_LONG).show();
                            } else if (data.get(1) == 0x12) {
                                //Blood oxygen
                                Toast.makeText(HeartRateActivity.this,"0x12",Toast.LENGTH_SHORT).show();
                            } else if (data.get(1) == 0x22) {
                                //blood pressure
                                String bloodPressure = stringBuffer.substring(5, 10);
                                String bloodPressure1 = stringBuffer.substring(10, 12);
                               // textResult.setText("Blood Pressure :"+bloodPressure +"/"+bloodPressure1);
                                Toast.makeText(context, bloodPressure, Toast.LENGTH_LONG).show();
                            }
                        }
                    }else {
                        //Toast.makeText(MainActivity.this,"heart beat else",Toast.LENGTH_SHORT).show();
                    }

                    //One-button measurement
                    if (data.get(0) == 0x32) {
                        //Toast.makeText(MainActivity.this,"heart beat",Toast.LENGTH_SHORT).show();
                    }



                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate);
        getActionBar().hide();



        EventBus.getDefault().register(this);

        manager = CommandManager.getInstance(this);
        //Bluetooth service
        Intent bindIntent = new Intent(this, BluetoothLeService.class);
        bindService(bindIntent, mServiceConnection, Context.BIND_AUTO_CREATE);

        //Registration broadcast
        LocalBroadcastManager.getInstance(this).registerReceiver(
                BLEStatusChangeReceiver, makeGattUpdateIntentFilter());






    }

    private IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BroadcastCommand.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(BroadcastCommand.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BroadcastCommand.ACTION_GATT_DISCONNECTED);
        return intentFilter;
    }

    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {

            // Automatically connects to the device upon successful start-up initialization.
//            mBluetoothLeService.connect(mDeviceAddress, false);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

            Log.i("zgy", "onServiceDisconnected");
        }
    };






    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(BLEStatusChangeReceiver);
        unbindService(mServiceConnection);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_setting:

                break;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {

        }

    }


    public void onEventMainThread(BaseEvent baseEvent) {
        switch (baseEvent.getEventType()) {
            case BLUETOOTH_CONNECTED:


                break;
            case BLUETOOTH_DISCONNECTED:
                Log.e("zgy", "BLUETOOTH_DISCONNECTED");

//
                break;

            default:
                break;
        }
    }
}