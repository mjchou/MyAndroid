package com.mvvm.zzy.mvvmforbt03.View;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.mvvm.zzy.mvvmforbt03.BR;
import com.mvvm.zzy.mvvmforbt03.Model.Adapters.DeviceListAdapter;
import com.mvvm.zzy.mvvmforbt03.Model.Bluetooths.BtDeviceItem;
import com.mvvm.zzy.mvvmforbt03.Model.Bluetooths.BtPairing;
import com.mvvm.zzy.mvvmforbt03.Model.Data.SystemInfo.SystemInfo;
import com.mvvm.zzy.mvvmforbt03.R;
import com.mvvm.zzy.mvvmforbt03.ViewModel.BluetoothViewModel.BtReceiver;
import com.mvvm.zzy.mvvmforbt03.ViewModel.ButtonViewModel.SearchButtonViewModel;
import com.mvvm.zzy.mvvmforbt03.ViewModel.ButtonViewModel.SwitchButtonViewModel;
import com.mvvm.zzy.mvvmforbt03.ViewModel.ListViewModel.ListItemViewModel;
import com.mvvm.zzy.mvvmforbt03.ViewModel.ListViewModel.ListItemViewUpdata;
import com.mvvm.zzy.mvvmforbt03.ViewModel.ListViewModel.NewProgressCreation;
import com.mvvm.zzy.mvvmforbt03.databinding.ActivityBtBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BtActivity extends AppCompatActivity {

    private SystemInfo systemInfo;
    private SwitchButtonViewModel switchButtonViewModel;
    private SearchButtonViewModel searchButtonViewModel;
    private ListItemViewModel listItemViewModel;
    private BtReceiver btReceiver;
    private IntentFilter btFilter;
    private BluetoothAdapter btAdapter;
    private ActivityBtBinding binding;
    private List<BtDeviceItem> deviceList;
    private DeviceListAdapter deviceListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParameters();
        initBinding();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (btAdapter.isEnabled()) {
            systemInfo.setOpen(true);
        }
    }

    private void initParameters() {
        systemInfo = new SystemInfo();
        deviceList = new ArrayList<>();
        deviceListAdapter = new DeviceListAdapter<>(BtActivity.this, R.layout.device_item, BR.deviceItem, deviceList);
        initBtAdapter();
        initBtReceiver();
    }

    private void initBtAdapter() {
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null) {
            processInvalidBtAdapter();;
        }
    }

    private void processInvalidBtAdapter() {
        Toast.makeText(getApplicationContext(), R.string.invalid_bt, Toast.LENGTH_SHORT).show();
        System.exit(0);
    }

    private void initBtReceiver() {
        btReceiver = new BtReceiver(btAdapter, systemInfo, deviceListAdapter);
        registerIntentFilter();
        registerReceiver(btReceiver, btFilter);
    }

    private void registerIntentFilter() {
        btFilter = new IntentFilter();
        btFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        btFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        btFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        btFilter.addAction(BluetoothDevice.ACTION_FOUND);
        btFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        btFilter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
    }

    private void initBinding() {
        switchButtonViewModel = new SwitchButtonViewModel(btAdapter);
        searchButtonViewModel = new SearchButtonViewModel(btAdapter);
        listItemViewModel = new ListItemViewModel(btAdapter, deviceListAdapter);
        listItemViewModel.setListItemViewUpdata(new ListItemViewUpdata() {
            @Override
            public void showDialog(BtDeviceItem deviceItem) {
                processShowDialog(deviceItem);
            }
        });
        listItemViewModel.setNewProgressCreation(new NewProgressCreation() {
            @Override
            public void startNewActivity(Map<String, String> extraMap) {
                Intent intent = new Intent(getApplicationContext(), DataTransmissionActivity.class);
                for (Map.Entry<String, String> entry : extraMap.entrySet()) {
                    intent.putExtra(entry.getKey(), entry.getValue());
                }
                intent.putExtra("systemInfo", systemInfo);
                startActivity(intent);
            }
        });

        binding = DataBindingUtil.setContentView(BtActivity.this, R.layout.activity_bt);
        binding.setSystemInfo(systemInfo);
        binding.setSwitchButton(switchButtonViewModel);
        binding.setSearchButton(searchButtonViewModel);
        binding.setDeviceListAdapter(deviceListAdapter);

        binding.lvShowDev.setOnItemClickListener(listItemViewModel);
        binding.lvShowDev.setOnItemLongClickListener(listItemViewModel);
    }

    private void processShowDialog(BtDeviceItem deviceItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(BtActivity.this);
        builder.setIcon(R.drawable.bt_image)
                .setMessage("是取消"+getString(R.string.Device_Name)+"为: "+deviceItem.getDeviceName()+"\n"
                        +getString(R.string.Device_Address)+"为: "+deviceItem.getDeviceAddr()+"\n"
                        +getString(R.string.Pairing));
        setPositiveButton(builder, deviceItem.getBtDevice());
        setNegativeButton(builder, deviceItem.getBtDevice())
                .create()
                .show();
    }

    private AlertDialog.Builder setPositiveButton(AlertDialog.Builder builder, final BluetoothDevice btDevice)
    {
        return builder.setPositiveButton(R.string.Select_Ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    BtPairing.removeBond(btDevice.getClass(), btDevice);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private AlertDialog.Builder setNegativeButton(AlertDialog.Builder builder, BluetoothDevice btDevice)
    {
        return builder.setNegativeButton(R.string.Select_Cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

    }
}
