package com.tzj.ali.pay;

import android.app.Activity;

import java.lang.ref.WeakReference;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** AliPayPlugin */
public class AliPayPlugin implements MethodCallHandler {
  private final WeakReference<Activity> mActivityWeak;
  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), AliPayPlugin.class.getSimpleName());
    channel.setMethodCallHandler(new AliPayPlugin(registrar.activity()));
  }

  public AliPayPlugin(Activity mActivity) {
    this.mActivityWeak = new WeakReference<Activity>(mActivity);
  }

  @Override
  public void onMethodCall(MethodCall call, final Result result) {
    if(call.method.equals("pay")){
      String str = call.arguments();
      UtilAlipay.pay(mActivityWeak.get(), str, new UtilAlipay.PayCallback() {
        @Override
        public void CallBack(PayResult payResult) {
          if (payResult.isOk()){
            result.success(null);
          }else{
            result.error(payResult.getResultStatus(),payResult.getResult(),null);
          }
        }
      });
    }else if(call.method.equals("h5Pay")){
      String str = call.arguments();
      UtilAlipay.h5Pay(mActivityWeak.get(), str, new UtilAlipay.PayCallback() {
        @Override
        public void CallBack(PayResult payResult) {
          if (payResult.isOk()){
            result.success(payResult.getResult());
          }else{
            result.error(payResult.getResultStatus(),payResult.getResult(),null);
          }
        }
      });
    } else {
      result.notImplemented();
    }
  }
}
