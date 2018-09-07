package com.wakeup.xxx;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.weike.chiginon.BroadcastCommand;
import com.weike.manager.CommandManager;

import de.greenrobot.event.EventBus;

public class BluetoothConnectActivity extends AppCompatActivity {
    private BluetoothLeService mBluetoothLeService;
    private String mDeviceAddress;
    private CommandManager manager;
   Button btnConnect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_connect);

        btnConnect = (Button) findViewById(R.id.btnConnect);

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BluetoothConnectActivity.this, DeviceScanActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        EventBus.getDefault().register(this);

        manager = CommandManager.getInstance(this);
        //Bluetooth service
        Intent bindIntent = new Intent(this, BluetoothLeService.class);
        bindService(bindIntent, mServiceConnection, Context.BIND_AUTO_CREATE);

        //Registration broadcast
       /* LocalBroadcastManager.getInstance(this).registerReceiver(
                BLEStatusChangeReceiver, makeGattUpdateIntentFilter());*/
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
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            Log.i("zgy", "onServiceConnected---==---");
            if (!mBluetoothLeService.initialize()) {
//                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
//            mBluetoothLeService.connect(mDeviceAddress, false);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
            Log.i("zgy", "onServiceDisconnected");
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            mDeviceAddress=data.getStringExtra("mac");
            mBluetoothLeService.connect(mDeviceAddress,false);
            //address.setText("正在连接...");
            //address.setText("Connecting...");
            Toast.makeText(BluetoothConnectActivity.this,"Connecting....",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
       // LocalBroadcastManager.getInstance(this).unregisterReceiver(BLEStatusChangeReceiver);
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }
    public void onEventMainThread(BaseEvent baseEvent) {
        switch (baseEvent.getEventType()) {
            case BLUETOOTH_CONNECTED:
                //address.setText(mDeviceAddress+"  connected");
                Toast.makeText(BluetoothConnectActivity.this,"Connected",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(BluetoothConnectActivity.this,NavigationDrawerActivity.class);
                startActivity(intent);
                finish();
                break;
            case BLUETOOTH_DISCONNECTED:
                Log.e("zgy", "BLUETOOTH_DISCONNECTED");
             //   address.setText("Disconnect...");
                Toast.makeText(BluetoothConnectActivity.this,"Disconnet...",Toast.LENGTH_SHORT).show();
//
                break;

            default:
                break;
        }
    }
}
