package com.mvvm.zzy.mvvmforbt03.ViewModel.ButtonViewModel;

import android.bluetooth.BluetoothAdapter;
import android.view.View;

/**
 * 控制设备蓝牙的开关操作
 */

public class SwitchButtonViewModel extends ButtonViewModel {

    private BluetoothAdapter btAdapter;

    public SwitchButtonViewModel(BluetoothAdapter btAdapter) {
        this.btAdapter = btAdapter;
    }

    @Override
    public void buttonChangedListener(View view) {
        if (btAdapter.isEnabled()) {
            btAdapter.disable();
        } else {
            btAdapter.enable();
        }
    }
}
