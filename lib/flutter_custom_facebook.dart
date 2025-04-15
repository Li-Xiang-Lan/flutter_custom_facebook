
import 'flutter_custom_facebook_platform_interface.dart';

class FlutterCustomFacebook {
  static final FlutterCustomFacebook _instance = FlutterCustomFacebook();
  static FlutterCustomFacebook get instance => _instance;

  Future<bool> initFaceBook({
    required String facebookId,
    required String facebookToken,
  })async{
    return FlutterCustomFacebookPlatform.instance.initFaceBook(facebookId, facebookToken);
  }

  Future<bool> logPurchase({
    required double amount,
    required String currency,
    Map<String, dynamic>? parameters,
  })async{
    return await FlutterCustomFacebookPlatform.instance.logPurchase(amount: amount, currency: currency,parameters: parameters);
  }
}
