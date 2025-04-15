import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'flutter_custom_facebook_platform_interface.dart';

/// An implementation of [FlutterCustomFacebookPlatform] that uses method channels.
class MethodChannelFlutterCustomFacebook extends FlutterCustomFacebookPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('flutter_custom_facebook');

  @override
  Future<bool> initFaceBook(String facebookId,String facebookToken,String facebookAppName) async {
    final result = await methodChannel.invokeMethod<bool>('initFaceBook',{"facebookId":facebookId,"facebookToken":facebookToken,"facebookAppName":facebookAppName});
    return result??false;
  }

  @override
  Future<bool> logPurchase({required double amount, required String currency}) async{
    var result = await methodChannel.invokeMethod<bool>('logPurchase', {"amount":amount,"currency":currency});
    return result??false;
  }

  @override
  Future<bool> logEventAdImpression(double amount, String currency) async{
    final result = await methodChannel.invokeMethod<bool>('logEventAdImpression',{"amount":amount,"currency":currency});
    return result??false;
  }
}
