package com.mvvm.zzy.mvvmforbt03.ViewModel.BluetoothViewModel;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;

import com.mvvm.zzy.mvvmforbt03.Model.Adapters.DeviceListAdapter;
import com.mvvm.zzy.mvvmforbt03.Model.Bluetooths.BtDeviceItem;
import com.mvvm.zzy.mvvmforbt03.Model.Data.SystemInfo.SystemInfo;

/**
 * 蓝牙设备各种状态的处理
 */

public class BtDeviceViewModel {
    private DeviceListAdapter deviceListAdapter;
    private SystemInfo systemInfo;

    public BtDeviceViewModel(DeviceListAdapter deviceListAdapter, SystemInfo systemInfo) {
        this.deviceListAdapter = deviceListAdapter;
        this.systemInfo = systemInfo;
    }

    public boolean processIntentChanged(Intent intent) {
        boolean result = false;
        if (processActionChanged(intent))
            result = true;
        return result;
    }

    private boolean processActionChanged(Intent intent) {
        switch (intent.getAction()) {
            case BluetoothDevice.ACTION_FOUND:
                btDeviceFound(intent);
                return true;

            case BluetoothDevice.ACTION_BOND_STATE_CHANGED:
                btDeviceBondChanged(intent);
                return true;

            default:
                break;
        }
        return false;
    }

    private void btDeviceFound(Intent intent) {
        systemInfo.setFound(true);
        btDeviceBondChanged(intent);
    }

    private void btDeviceBondChanged(Intent intent) {
        BluetoothDevice btDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        BtDeviceItem btDeviceItem = new BtDeviceItem(btDevice.getName(), btDevice.getAddress(),
                btDevice.getBondState()==BluetoothDevice.BOND_BONDED, btDevice);
        deviceListAdapter.updateList(btDeviceItem);
    }

}
