package com.mvvm.zzy.mvvmforbt03.ViewModel.ButtonViewModel;

import android.view.View;

import com.mvvm.zzy.mvvmforbt03.Model.Data.SystemInfo.SystemInfo;

/**
 * Created by Administrator on 2017/6/12 0012.
 */

public class ReceiveButtonViewModel extends ButtonViewModel {
    SystemInfo systemInfo;
    private OnReceiveButtonListener onReceiveButtonListener;

    public ReceiveButtonViewModel(SystemInfo systemInfo) {
        this.systemInfo = systemInfo;
    }

    public void setOnReceiveButtonListener(OnReceiveButtonListener onReceiveButtonListener) {
        this.onReceiveButtonListener = onReceiveButtonListener;
    }

    @Override
    public void buttonChangedListener(View view) {
        if (systemInfo.isReceive()) {
            systemInfo.setReceive(false);
            onReceiveButtonListener.stopReceive();
        } else {
            systemInfo.setReceive(true);
            onReceiveButtonListener.startReceive();
        }
    }
}
