# MvvmForBt03

代码托管地址:[MvvmForBt03](https://github.com/code-command/MvvmForBt03)

## Overview

本代码是为了实现一个简单的蓝牙设备的搜索与配对连接，以及相应数据接收显示的实例，实例利用MVVM+DataBinding机制完成。

文档更新时间:

## Activity

### 1.	BtActivity

本模块作用是为了实现对本级蓝牙设备的开启与关闭、周围蓝牙设备的搜索与配对连接。

**Note:**  	

* 当扫描到周围的蓝牙设备时，会用列表显示出所有扫描到的设备，已经配对的设备栏后会显示一个蓝牙标识。
* 对于未配对的蓝牙设备，单击与长按时都会弹出要求配对的`Dialog`窗口。
* 对于已经配对的蓝牙设备，单击时直接跳转到接收界面。长按时会弹出取消配对的`Dialo`g窗口。

### 2.	DataTransmissionActivity

本模块作用是用来接收规定格式的数据并做相应的显示。

**Note:**

* 单击开始接收按钮后，会开始数据接收，自定义的数据接收格式可以重构`ReceptionData.class`。

* 单击停止接收按钮后，会停止接收数据，并将清空之前的接收的数据，并关闭显示控件。

* 单击清楚时，会清除之前的接收结果。

* **接收模块只是进行了重构，未进行实际测试，若不能正常显示，可以在`DataTransmissionActivity.class`中将回调函数`conn`更新为:**

  ```java
  ServiceConnection conn = new ServiceConnection() {
          @Override
          public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
              switchBinder = (DataReceiveService.SwitchBinder) iBinder;
              dataReceiveService = switchBinder.getService();
              dataReceiveService.setOnGetDataListener(new OnGetDataListener() {
                  @Override
                  public void GetDataCollection(ReceptionData data) {
                      receptionData.updateCoreData(data.getCodeData().toString());
                  }
              });
          }
  ```

  ​

  **尝试解决，若仍不能正常工作，欢迎提交错误**

## 欢迎及时反馈各种错误与漏洞

