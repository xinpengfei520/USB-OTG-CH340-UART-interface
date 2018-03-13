package com.xpf.ch340_host;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xpf.ch340_library.CH340Master;
import com.xpf.ch340_library.driver.InitCH340;
import com.xpf.ch340_library.utils.CH340Util;

public class MainActivity extends AppCompatActivity implements InitCH340.IUsbPermissionListener {

    private boolean isFirst;//判断是否打开
    private Button btnSend;
    private EditText etContent;
    private static final String ACTION_USB_PERMISSION = "com.linc.USB_PERMISSION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSend = findViewById(R.id.btnSend);
        etContent = findViewById(R.id.etContent);
        initData();
        initListener();
    }

    private void initListener() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });
    }

    private void sendData() {
        String string = etContent.getText().toString();
        if (!TextUtils.isEmpty(string)) {
            CH340Util.writeData(string.getBytes());
        } else {
            Toast.makeText(MainActivity.this, "发送的数据不能为空！", Toast.LENGTH_SHORT).show();
        }
    }

    private void initData() {
        InitCH340.setListener(this);
        if (!isFirst) {
            isFirst = true;
            // 初始化室内机模块
            CH340Master.initialize(MyApplication.getContext());
        }
    }

    @Override
    public void result(boolean isGranted) {
        if (!isGranted) {
            PendingIntent mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
            InitCH340.getmUsbManager().requestPermission(InitCH340.getmUsbDevice(), mPermissionIntent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter usbFilter = new IntentFilter();
        usbFilter.addAction(ACTION_USB_PERMISSION);
        registerReceiver(mUsbReceiver, usbFilter);
    }

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {
                            Toast.makeText(MainActivity.this, "EXTRA_PERMISSION_GRANTED~", Toast.LENGTH_SHORT).show();
                            InitCH340.loadDriver(MyApplication.getContext(), InitCH340.getmUsbManager());
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "EXTRA_PERMISSION_GRANTED null!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mUsbReceiver);
    }
}
