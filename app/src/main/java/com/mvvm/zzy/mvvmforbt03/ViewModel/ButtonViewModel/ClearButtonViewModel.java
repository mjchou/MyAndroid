package com.mvvm.zzy.mvvmforbt03.ViewModel.ButtonViewModel;

import android.view.View;

import com.mvvm.zzy.mvvmforbt03.Model.Data.SystemInfo.SystemInfo;

/**
 * 清除数据按钮的相应处理，主要是更新systemInfo类中clear变量的状态来激发BindingAdapter中定义的相关操作
 */

public class ClearButtonViewModel extends ButtonViewModel {
    SystemInfo systemInfo;

    public ClearButtonViewModel(SystemInfo systemInfo) {
        this.systemInfo = systemInfo;
    }

    @Override
    public void buttonChangedListener(View view) {
        switchoverSystemInfoClear();
    }

    private void switchoverSystemInfoClear() {
            systemInfo.triggerClear();
    }
}
