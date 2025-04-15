import Flutter
import UIKit
import FBSDKCoreKit
import FBSDKCoreKit_Basics

public class FlutterCustomFacebookPlugin: NSObject, FlutterPlugin {
  var application: UIApplication?
  var launchOptions: [AnyHashable : Any] = [:]

  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "flutter_custom_facebook", binaryMessenger: registrar.messenger())
    let instance = FlutterCustomFacebookPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
    registrar.addApplicationDelegate(instance)
  }

    /// Connect app delegate with SDK
  public func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [AnyHashable : Any] = [:]) -> Bool {
       self.application = application
       self.launchOptions = launchOptions
       return true
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    switch call.method {
    case "initFaceBook":
      handleInit(call, result: result)
    break
    case "logPurchase":
      handlePurchased(call, result: result)
    break
    default:
      result(FlutterMethodNotImplemented)
    }
  }

    private func handleInit(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
      let arguments = call.arguments as? [String: Any] ?? [String: Any]()

      Settings.shared.appID=arguments["facebookId"] as? String
      Settings.shared.clientToken=arguments["facebookToken"] as? String
      Settings.shared.displayName=arguments["facebookAppName"] as? String
      Settings.shared.isCodelessDebugLogEnabled=true
        Settings.shared.loggingBehaviors=[.networkRequests]


      var options = [UIApplication.LaunchOptionsKey: Any]()
      for (k, value) in self.launchOptions {
          let key = k as! UIApplication.LaunchOptionsKey
          options[key] = value
      }
      ApplicationDelegate.shared.application(self.application!,didFinishLaunchingWithOptions: options)
        
      result(true)

    }

   private func handlePurchased(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
        let arguments = call.arguments as? [String: Any] ?? [String: Any]()
        let amount = arguments["amount"] as! Double
        let currency = arguments["currency"] as! String
       AppEvents.shared.logPurchase(amount: amount, currency: currency)

        result(true)
    }
}
