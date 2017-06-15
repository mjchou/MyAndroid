package com.mvvm.zzy.mvvmforbt03.Model.Data.SystemInfo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.mvvm.zzy.mvvmforbt03.BR;

import java.io.Serializable;

/**
 * 用于表示当前操作状态类。
 * 最好是采用单例模式，但是在实验中发现无法实现DataBinding，故采用此模式。
 */
public class SystemInfo extends BaseObservable implements Serializable {
    private boolean open;
    private boolean search;
    private boolean found;
    private boolean receive;
    private boolean send;
    private boolean clear;

    public SystemInfo() {
        this.open = false;
        this.search = false;
        this.found = false;
        this.receive = false;
        this.send = false;
        this.clear = false;
    }

    public void setSystemInfo(SystemInfo info) {
        this.open = info.isOpen();
        this.search = info.isSearch();
        this.found = info.isFound();
        this.receive  =info.isReceive();
        this.send = info.isSend();
        this.clear = info.isClear();
    }

    @Bindable
    public boolean isReceive() {
        return receive;
    }

    public void setReceive(boolean receive) {
        this.receive = receive;
        notifyPropertyChanged(BR.receive);
        if (receive) {
            send = false;
            notifyPropertyChanged(BR.send);
        }

    }

    @Bindable
    private boolean isSend() {
        return send;
    }

    @Bindable
    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
        notifyPropertyChanged(BR.found);
    }

    @Bindable
    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
        notifyPropertyChanged(BR.open);
    }

    @Bindable
    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
        notifyPropertyChanged(BR.search);
    }

    @Bindable
    public boolean isClear() {
        return clear;
    }

    public void triggerClear() {
        this.clear = true;
        notifyPropertyChanged(BR.clear);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SystemInfo that = (SystemInfo) o;

        if (open != that.open) return false;
//        if (search != that.search) return false;
        return search == that.search;

    }

    @Override
    public int hashCode() {
        int result = (open ? 1 : 0);
        result = 31 * result + (search ? 1 : 0);
        result = 31 * result + (found ? 1 : 0);
        return result;
    }
}

