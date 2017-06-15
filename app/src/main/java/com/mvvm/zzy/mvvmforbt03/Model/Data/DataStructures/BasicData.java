package com.mvvm.zzy.mvvmforbt03.Model.Data.DataStructures;

import java.util.Queue;


/**
 * 暂时未启用
 */
public abstract class BasicData {

    public abstract String getCheckCode();

    public abstract boolean checkCompleteData(Queue<Byte> resource);

    public abstract void setCoreData(String resource);

    public abstract void interceptCoreData(Queue<Byte> resource);

    public abstract int getDataLength();

}
