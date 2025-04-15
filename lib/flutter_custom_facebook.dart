
import 'flutter_custom_facebook_platform_interface.dart';

class FlutterCustomFacebook {
  static final FlutterCustomFacebook _instance = FlutterCustomFacebook();
  static FlutterCustomFacebook get instance => _instance;

  Future<bool> initFaceBook({
    required String facebookId,
    required String facebookToken,
    required String facebookAppName,
  })async{
    return FlutterCustomFacebookPlatform.instance.initFaceBook(facebookId, facebookToken,facebookAppName);
  }

  Future<bool> logPurchase({
    required double amount,
    required String currency
  })async{
    return await FlutterCustomFacebookPlatform.instance.logPurchase(amount: amount, currency: currency);
  }

  Future<bool> logEventAdImpression({
    required double amount,
    required String currency,
  })async{
    return await FlutterCustomFacebookPlatform.instance.logEventAdImpression(amount, currency);
  }
}
