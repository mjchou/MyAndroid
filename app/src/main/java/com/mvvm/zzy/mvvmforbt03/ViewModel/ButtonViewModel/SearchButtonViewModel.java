package com.mvvm.zzy.mvvmforbt03.ViewModel.ButtonViewModel;

import android.bluetooth.BluetoothAdapter;
import android.view.View;

/**
 * 搜索按钮的单击相应，主要通过更改BluetoothAdapter的扫描状态
 */

public class SearchButtonViewModel extends ButtonViewModel {
    private BluetoothAdapter btAdapter;

    public SearchButtonViewModel(BluetoothAdapter btAdapter) {
        this.btAdapter = btAdapter;
    }

    @Override
    public void buttonChangedListener(View view) {
        if (!btAdapter.isEnabled())
            return;
        if (btAdapter.isDiscovering()) {
            btAdapter.cancelDiscovery();
        } else {
            btAdapter.startDiscovery();
        }
    }
}
