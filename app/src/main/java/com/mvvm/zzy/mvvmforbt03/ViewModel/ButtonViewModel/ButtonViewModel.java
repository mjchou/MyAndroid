package com.mvvm.zzy.mvvmforbt03.ViewModel.ButtonViewModel;

import android.view.View;

/**
 * 各按钮控制的ViewModel的基类，所有的按钮相应都在buttonChangedListener函数中实现
 */
public abstract class ButtonViewModel {
    public abstract void buttonChangedListener(View view);
}
