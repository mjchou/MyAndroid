package com.mvvm.zzy.mvvmforbt03.ViewModel.ListViewModel;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.view.View;
import android.widget.AdapterView;

import com.mvvm.zzy.mvvmforbt03.Model.Adapters.DeviceListAdapter;
import com.mvvm.zzy.mvvmforbt03.Model.Bluetooths.BtDeviceItem;
import com.mvvm.zzy.mvvmforbt03.Model.Bluetooths.BtPairing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * List的单击与长按操作处理，实现：
 * 1. 未配对设备单击与长按时都实现配对操作
 * 2. 已配对设备单击时跳转到数据接收界面，长按时进行取消配对操作
 */

public class ListItemViewModel implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{
    private BluetoothAdapter btAdapter;
    private List<BtDeviceItem> deviceList;

    private ListItemViewUpdata listItemViewUpdata;
    private NewProgressCreation newProgressCreation;

    public ListItemViewModel(BluetoothAdapter btAdapter, DeviceListAdapter deviceListAdapter) {
        this.btAdapter = btAdapter;
        deviceList = deviceListAdapter.getList();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        cancelBtAdapterDiscover();
        processBondState(deviceList.get(position).getBtDevice());
    }

    private void cancelBtAdapterDiscover() {
        if(btAdapter !=null && btAdapter.isDiscovering())
            btAdapter.cancelDiscovery();
    }
    private void processBondState(BluetoothDevice btDevice) {
        switch (btDevice.getBondState()) {
            case BluetoothDevice.BOND_NONE:
                processDeviceBondNone(btDevice);
                break;

            case BluetoothDevice.BOND_BONDED:
                processDeviceBondBonded(btDevice);
                break;

            default:
                break;
        }
    }

    private void processDeviceBondNone(BluetoothDevice btDevice) {
        try {
            BtPairing.creatBond(btDevice.getClass(), btDevice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processDeviceBondBonded(BluetoothDevice btDevice) {
        Map<String, String> extraMap = new HashMap<>();
        extraMap.put("deviceAddr",btDevice.getAddress());
        newProgressCreation.startNewActivity(extraMap);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        cancelBtAdapterDiscover();
        processItemLongClick(position);
        return true;
    }

    private void processItemLongClick(int position) {
        BluetoothDevice device = deviceList.get(position).getBtDevice();
        if (device.getBondState()==BluetoothDevice.BOND_BONDED) {
            listItemViewUpdata.showDialog(deviceList.get(position));
        } else {
            processDeviceBondNone(device);
        }
    }

    public void setListItemViewUpdata(ListItemViewUpdata listItemViewUpdata) {
        this.listItemViewUpdata = listItemViewUpdata;
    }

    public void setNewProgressCreation(NewProgressCreation newProgressCreation) {
        this.newProgressCreation = newProgressCreation;
    }

}
