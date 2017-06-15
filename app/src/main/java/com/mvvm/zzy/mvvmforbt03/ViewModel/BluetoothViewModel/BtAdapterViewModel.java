package com.mvvm.zzy.mvvmforbt03.ViewModel.BluetoothViewModel;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

import com.mvvm.zzy.mvvmforbt03.Model.Adapters.DeviceListAdapter;
import com.mvvm.zzy.mvvmforbt03.Model.Data.SystemInfo.SystemInfo;

/**
 * 蓝牙设备各种状态的处理
 */
public class BtAdapterViewModel {
    private BluetoothAdapter btAdapter;
    private SystemInfo systemInfo;
    private DeviceListAdapter deviceListAdapter;

    public BtAdapterViewModel(BluetoothAdapter btAdapter, SystemInfo systemInfo, DeviceListAdapter deviceListAdapter) {
        this.btAdapter = btAdapter;
        this.systemInfo = systemInfo;
        this.deviceListAdapter = deviceListAdapter;
    }

    public boolean processIntentChanged(Intent intent) {
        boolean result = false;
        if (processIntChanged(intent))
            result = true;
        if (processActionChanged(intent))
            result = true;
        return result;
    }

    private boolean processIntChanged(Intent intent) {
        int temp = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
        switch (temp) {
            case BluetoothAdapter.STATE_ON:
            case BluetoothAdapter.STATE_TURNING_ON:
                btAdapterStateOn();
                return true;

            case BluetoothAdapter.STATE_OFF:
            case BluetoothAdapter.STATE_TURNING_OFF:
                btAdapterStateOff();
                return true;

            default:
                break;
        }
        return false;
    }

    private void btAdapterStateOn() {
        systemInfo.setOpen(true);
    }

    private void btAdapterStateOff() {
        systemInfo.setOpen(false);
        systemInfo.setFound(false);
        systemInfo.setSearch(false);
        if (btAdapter.isDiscovering()) {
            btAdapter.cancelDiscovery();
        }
        deviceListAdapter.clearList();
    }

    private boolean processActionChanged(Intent intent) {
        switch (intent.getAction()) {
            case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                btAdapter_DISCOVERY_STARTED();
                return true;

            case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                btAdapter_DISCOVERY_FINISHED();
                return true;

            default:
                break;
        }
        return false;
    }

    private void btAdapter_DISCOVERY_STARTED() {
        systemInfo.setSearch(true);
        deviceListAdapter.clearList();
    }

    private void btAdapter_DISCOVERY_FINISHED() {
        systemInfo.setSearch(false);
    }
}
