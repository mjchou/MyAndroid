package com.mvvm.zzy.mvvmforbt03.View;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;

import com.mvvm.zzy.mvvmforbt03.Model.Data.DataStructures.ReceptionData;
import com.mvvm.zzy.mvvmforbt03.Model.Data.SystemInfo.SystemInfo;
import com.mvvm.zzy.mvvmforbt03.R;
import com.mvvm.zzy.mvvmforbt03.ViewModel.ButtonViewModel.ClearButtonViewModel;
import com.mvvm.zzy.mvvmforbt03.ViewModel.ButtonViewModel.OnReceiveButtonListener;
import com.mvvm.zzy.mvvmforbt03.ViewModel.ButtonViewModel.ReceiveButtonViewModel;
import com.mvvm.zzy.mvvmforbt03.ViewModel.DataTransmission.DataReceiveService;
import com.mvvm.zzy.mvvmforbt03.ViewModel.DataTransmission.OnGetDataListener;
import com.mvvm.zzy.mvvmforbt03.databinding.ActivityDataTransmissionBinding;

public class DataTransmissionActivity extends AppCompatActivity {
    SystemInfo systemInfo;
    String deviceAddr;
    ReceptionData receptionData;
    DataReceiveService.SwitchBinder switchBinder;
    DataReceiveService dataReceiveService;
    ReceiveButtonViewModel receiveButtonViewModel;
    ClearButtonViewModel clearButtonViewModel;

    private Intent bindIntent;
    ActivityDataTransmissionBinding binding;

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            switchBinder = (DataReceiveService.SwitchBinder) iBinder;
            dataReceiveService = switchBinder.getService();
            dataReceiveService.setOnGetDataListener(new OnGetDataListener() {
                @Override
                public void GetDataCollection(ReceptionData data) {
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(DataTransmissionActivity.this, R.layout.activity_data_transmission);
        getImportData();
        initData();
        setDataBinding();
    }

    private void getImportData() {
        Intent getintent = getIntent();
        deviceAddr = getintent.getStringExtra("deviceAddr");
        systemInfo = (SystemInfo) getintent.getSerializableExtra("systemInfo");
    }

    private void initData() {
        receptionData = new ReceptionData();
        clearButtonViewModel = new ClearButtonViewModel(systemInfo);
        receiveButtonViewModel = new ReceiveButtonViewModel(systemInfo);
        createBindIntent();
        receiveButtonViewModel.setOnReceiveButtonListener(new OnReceiveButtonListener() {
            @Override
            public void startReceive() {
                DataTransmissionActivity.this.getApplicationContext().bindService(bindIntent, conn, Context.BIND_AUTO_CREATE);
            }

            @Override
            public void stopReceive() {
                DataTransmissionActivity.this.getApplicationContext().unbindService(conn);
            }
        });
    }

    private void createBindIntent() {
        //绑定Service
        bindIntent = new Intent(DataTransmissionActivity.this, DataReceiveService.class);
        bindIntent.putExtra("deviceAddr", deviceAddr);
        String deviceUUID = "00001101-0000-1000-8000-00805F9B34FB";
        bindIntent.putExtra("deviceUUID", deviceUUID);
        bindIntent.putExtra("systemInfo", systemInfo);
        bindIntent.putExtra("receiveData", receptionData);
    }

    private void setDataBinding() {
        binding.setSystemInfo(systemInfo);
        binding.setClearButton(clearButtonViewModel);
        binding.setReceiveButton(receiveButtonViewModel);
        binding.setReceiveData(receptionData);
    }
}
