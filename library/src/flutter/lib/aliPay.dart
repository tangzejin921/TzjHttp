import 'dart:async';

import 'package:flutter/services.dart';

class AliPay {
  final MethodChannel _channel = const MethodChannel('AliPayPlugin');

  ///
  ///成功 success（void）
  ///失败 err(PlatformException)
  ///
  Future<Null> pay(String payInfo) async {
    return await _channel.invokeMethod('pay',payInfo);
  }
    ///
   ///成功 success（url）
   ///失败 err(PlatformException)
   ///
  Future<String> h5Pay(String payInfo) async {
    final String url = await _channel.invokeMethod('h5Pay',payInfo);
    return url;
  }
}
