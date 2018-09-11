package com.wakeup.easymedics;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.weike.chiginon.BroadcastCommand;
import com.weike.chiginon.BroadcastData;
import com.weike.chiginon.DataPacket;
import com.weike.manager.CommandManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.gridview)
    GridView gridview;
    @InjectView(R.id.address)
    TextView address;
    TextView textResult;
    private List<String> list;
    ArrayList<Integer> array_image;

    private BluetoothLeService mBluetoothLeService;
    private String mDeviceAddress;
    private CommandManager manager;

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
                    //stringBuffer.append("接收的数据：");
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

                                Toast.makeText(MainActivity.this,"0x21",Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            //Real-time measurement
                            if (data.get(1) == 0x0A) {
                                //Heart rate
                                String heartRate = stringBuffer.substring(5, 8);
                                textResult.setText("Heart Rate :"+heartRate);
                                Toast.makeText(context, heartRate, Toast.LENGTH_LONG).show();
                            } else if (data.get(1) == 0x12) {
                                //Blood oxygen
                                Toast.makeText(MainActivity.this,"0x12",Toast.LENGTH_SHORT).show();
                            } else if (data.get(1) == 0x22) {
                                //blood pressure
                                String bloodPressure = stringBuffer.substring(5, 10);
                                String bloodPressure1 = stringBuffer.substring(10, 12);
                                textResult.setText("Blood Pressure :"+bloodPressure +"/"+bloodPressure1);
                               // Toast.makeText(context, stringBuffer.toString(), Toast.LENGTH_LONG).show();
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
        setContentView(R.layout.activity_main);

        textResult = (TextView) findViewById(R.id.textResult);

        ButterKnife.inject(this);
        EventBus.getDefault().register(this);

        manager = CommandManager.getInstance(this);
        //Bluetooth service
        Intent bindIntent = new Intent(this, BluetoothLeService.class);
        bindService(bindIntent, mServiceConnection, Context.BIND_AUTO_CREATE);

        //Registration broadcast
        LocalBroadcastManager.getInstance(this).registerReceiver(
                BLEStatusChangeReceiver, makeGattUpdateIntentFilter());

        //Connect to Bluetooth
        initdata();

        MyAdapter myAdapter = new MyAdapter(list,array_image);
        gridview.setAdapter(myAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        // Log.i("test", "心率实时测量");
                        Log.i("test", "Heart rate real-time measurement");
                        manager.realTimeAndOnceMeasure(0X0A,1);

                        /*Intent intent = new Intent(MainActivity.this,HeartRateActivity.class);
                        startActivity(i);*/

                        break;
                    case 1:
                        Log.i("test", "Blood pressure real-time measurement");
                        manager.realTimeAndOnceMeasure(0X22,1);

                        Toast.makeText(MainActivity.this,"Blood pressure real-time measurement",Toast.LENGTH_SHORT).show();
                        break;

                  /*  case 2:
                        manager.realTimeAndOnceMeasure(0X09,1);
                        break;*/

                    /*case 2:
                        //Log.i("test", "智能提醒");
                        Log.i("test", "Smart reminder");
                        //manager.smartWarnInfo(7, 2, "微克科技");
                        manager.smartWarnInfo(7, 2, "Micro Technology");
                        Toast.makeText(MainActivity.this,"Smart reminder",Toast.LENGTH_SHORT).show();

//
                        break;
                    case 3:
                       // Log.i("test", "抬手亮屏");
                        Log.i("test", "Raise your hand to brighten");
                        manager.upHandLightScreen(1);//0关 1开

                        Toast.makeText(MainActivity.this,"Raise your hand to brighten",Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        //Log.i("test", "摇摇拍照");
                        Log.i("test", "Shake to take pictures");
                        manager.sharkTakePhoto(1);//0关 1开

                        Toast.makeText(MainActivity.this,"Shake to take pictures",Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                       // Log.i("test", "防丢");
                        Log.i("test", "Anti-lost");
                        manager.antiLost(1);//0关 1开

                        Toast.makeText(MainActivity.this,"Anti-lost",Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        //Log.i("test", "电量");
                        Log.i("test", "Electricity");
                        manager.getBattery();

                        Toast.makeText(MainActivity.this,"Electricity",Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                       // Log.i("test", "版本号");
                        Log.i("test", "version number");
                        manager.versionInfo();

                        Toast.makeText(MainActivity.this,"version number",Toast.LENGTH_SHORT).show();
                        break;
                    case 8:
                       // Log.i("test", "同步时间");
                        Log.i("test", "synchronised time");
                        manager.setTimeSync();

                        Toast.makeText(MainActivity.this,"synchronised time",Toast.LENGTH_SHORT).show();
                        break;
                    case 9:
                       // Log.i("test", "清除手环数据");
                        Log.i("test", "Clear bracelet data");
                        manager.clearData();

                        Toast.makeText(MainActivity.this,"Clear bracelet data",Toast.LENGTH_SHORT).show();
                        break;
                    case 10:
                        //Log.i("test", "跌倒");
                        Log.i("test", "Fall");
                        manager.falldownWarn(1);//0关 1开

                        Toast.makeText(MainActivity.this,"Fall",Toast.LENGTH_SHORT).show();
                        break;
                    case 11:
                       // Log.i("test", "心率单次测量");
                        Log.i("test", "Heart rate single measurement");
                        manager.realTimeAndOnceMeasure(0X08,1);

                        Toast.makeText(MainActivity.this,"Heart rate single measurement",Toast.LENGTH_SHORT).show();
//                        参数1
//                        区分单次测量和实时测量

//                        参数2
//                        0关 1开
                        break;
                    case 12:
                       // Log.i("test", "心率实时测量");
                        Log.i("test", "Heart rate real-time measurement");
                        manager.realTimeAndOnceMeasure(0X0A,1);

                        Toast.makeText(MainActivity.this,"Heart rate real-time measurement",Toast.LENGTH_SHORT).show();
                        break;
                    case 13:
                       // Log.i("test", "血氧单次测量");
                        Log.i("test", "Blood oxygen single measurement");
                        manager.realTimeAndOnceMeasure(0X11,1);

                        Toast.makeText(MainActivity.this,"Blood oxygen single measurement",Toast.LENGTH_SHORT).show();
                        break;
                    case 14:
                       // Log.i("test", "血氧实时测量");
                        Log.i("test", "Blood oxygen real-time measurement");
                        manager.realTimeAndOnceMeasure(0X12,1);

                        Toast.makeText(MainActivity.this,"Blood oxygen real-time measurement",Toast.LENGTH_SHORT).show();
                        break;
                    case 15:
                       // Log.i("test", "血压单次测量");
                        Log.i("test", "Blood pressure single measurement");
                        manager.realTimeAndOnceMeasure(0X21,1);

                        Toast.makeText(MainActivity.this,"Blood pressure single measurement",Toast.LENGTH_SHORT).show();
                        break;
                    case 16:
                       // Log.i("test", "血压实时测量");
                        Log.i("test", "Blood pressure real-time measurement");
                        manager.realTimeAndOnceMeasure(0X22,1);

                        Toast.makeText(MainActivity.this,"Blood pressure real-time measurement",Toast.LENGTH_SHORT).show();
                        break;*/
                }
            }
        });

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

    private void initdata() {
        list = new ArrayList<>();
//        list.add("Find the bracelet");
//        list.add("Pull-down sync");
//        list.add("Smart reminder");
//        list.add("Find the bracelet");
//        list.add("Shake to take pictures");
//        list.add("Anti-lost");
//        list.add("Electricity");
//        list.add("version number");
//        list.add("synchronised time");
//        list.add("clear data");
//        list.add("Fall");
 //       list.add("Heart rate single measurement");
        list.add("Heart rate measurement");
//        list.add("Blood oxygen single measurement");
//        list.add("Blood oxygen real-time measurement");
//        list.add("Blood pressure single measurement");
        list.add("Blood pressure measurement");

        array_image = new ArrayList<Integer>();
        array_image.add(R.drawable.ic_launcher_background);
        array_image.add(R.drawable.ic_launcher_background);
      //  array_image.add(R.drawable.ic_launcher_background);
        /*array_image.add(R.drawable.ic_launcher_background);
        array_image.add(R.drawable.ic_launcher_background);
        array_image.add(R.drawable.ic_launcher_background);
        array_image.add(R.drawable.ic_launcher_background);
        array_image.add(R.drawable.ic_launcher_background);
        array_image.add(R.drawable.ic_launcher_background);
        array_image.add(R.drawable.ic_launcher_background);
        array_image.add(R.drawable.ic_launcher_background);
        array_image.add(R.drawable.ic_launcher_background);
        array_image.add(R.drawable.ic_launcher_background);
        array_image.add(R.drawable.ic_launcher_background);
        array_image.add(R.drawable.ic_launcher_background);
        array_image.add(R.drawable.ic_launcher_background);
        array_image.add(R.drawable.ic_launcher_background);
        array_image.add(R.drawable.ic_launcher_background);*/



    }


    class MyAdapter extends BaseAdapter {
        private List<String> list;
        private List<Integer> array_image ;

        public MyAdapter(List<String> list,List<Integer> array_image) {
            this.list = list;
            this.array_image = array_image;
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.channel_item, null);
                viewHolder.text = (TextView) convertView.findViewById(R.id.channel_text);
                viewHolder.grid_image = (ImageView) convertView.findViewById(R.id.grid_image);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }


            viewHolder.text.setText(list.get(i));
            viewHolder.grid_image.setImageResource(array_image.get(i));
            return convertView;
        }

    }

    class ViewHolder {
        TextView text;
        ImageView grid_image;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(BLEStatusChangeReceiver);
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting, menu);
        return true;
    }

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_setting:
                Intent intent = new Intent(MainActivity.this, DeviceScanActivity.class);
                startActivityForResult(intent, 0);

                break;
        }

        return true;
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            mDeviceAddress=data.getStringExtra("mac");
            mBluetoothLeService.connect(mDeviceAddress,false);
            //address.setText("正在连接...");
            address.setText("Connecting...");
        }

    }


    public void onEventMainThread(BaseEvent baseEvent) {
        switch (baseEvent.getEventType()) {
            case BLUETOOTH_CONNECTED:
                address.setText(mDeviceAddress+"  connected");

                break;
            case BLUETOOTH_DISCONNECTED:
                Log.e("zgy", "BLUETOOTH_DISCONNECTED");
                address.setText("Disconnect...");
//
                break;

            default:
                break;
        }
    }
}
