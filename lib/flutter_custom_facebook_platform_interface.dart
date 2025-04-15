import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'flutter_custom_facebook_method_channel.dart';

abstract class FlutterCustomFacebookPlatform extends PlatformInterface {
  /// Constructs a FlutterCustomFacebookPlatform.
  FlutterCustomFacebookPlatform() : super(token: _token);

  static final Object _token = Object();

  static FlutterCustomFacebookPlatform _instance = MethodChannelFlutterCustomFacebook();

  /// The default instance of [FlutterCustomFacebookPlatform] to use.
  ///
  /// Defaults to [MethodChannelFlutterCustomFacebook].
  static FlutterCustomFacebookPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [FlutterCustomFacebookPlatform] when
  /// they register themselves.
  static set instance(FlutterCustomFacebookPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<bool> initFaceBook(String facebookId,String facebookToken) {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<bool> logPurchase({
    required double amount,
    required String currency,
    Map<String, dynamic>? parameters,
  }) {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<bool> logEventAdImpression(double amount, String currency,){
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
