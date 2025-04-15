package com.custom.flutter_custom_facebook

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsConstants.EVENT_NAME_AD_IMPRESSION
import com.facebook.appevents.AppEventsConstants.EVENT_PARAM_CURRENCY
import com.facebook.appevents.AppEventsConstants.EVENT_PARAM_VALUE_TO_SUM
import com.facebook.appevents.AppEventsLogger
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import java.util.Currency

/** FlutterCustomFacebookPlugin */
class FlutterCustomFacebookPlugin: FlutterPlugin, MethodCallHandler {
  private lateinit var channel : MethodChannel
  private lateinit var mContext: Context
  private var appEventsLogger: AppEventsLogger?=null

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_custom_facebook")
    channel.setMethodCallHandler(this)
    mContext=flutterPluginBinding.applicationContext
  }

  override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
    when(call.method){
      "initFaceBook"->initFaceBook(call,result)
      "logPurchase"->logPurchase(call,result)
      "logEventAdImpression"->logEventAdImpression()
    }
  }

  private fun initFaceBook(call: MethodCall,result: MethodChannel.Result){
    call.arguments?.let{
      runCatching {
        val map = it as Map<String, Any>
        val facebookId = (map["facebookId"] as? String)?:""
        val facebookToken = (map["facebookToken"] as? String)?:""
        val facebookAppName = (map["facebookAppName"] as? String)?:""
        FacebookSdk.setApplicationId(facebookId)
        FacebookSdk.setClientToken(facebookToken)
        FacebookSdk.setApplicationName(facebookAppName)
        FacebookSdk.sdkInitialize(mContext)
        appEventsLogger = AppEventsLogger.newLogger(mContext)
        result.success(true)
      }.onFailure {
        Log.e("qwer","initFaceBook onFailure===>${it.message}")
      }
    }
  }

  private fun logPurchase(call: MethodCall,result: MethodChannel.Result){
    call.arguments?.let{
      runCatching {
        val map = it as Map<String, Any>
        val amount = (map["amount"] as? Double)?.toBigDecimal()
        val currency = Currency.getInstance(map["currency"] as? String)

        appEventsLogger?.logPurchase(amount, currency)
        result.success(true)
      }.onFailure {
        Log.e("qwer","logPurchase onFailure===>${it.message}")
      }
    }
  }

  private fun logEventAdImpression(){
    val parameters = Bundle()
    parameters.putString(EVENT_PARAM_CURRENCY,"USD")
    val decimal = 0.001.toBigDecimal()
    parameters.putString(EVENT_PARAM_VALUE_TO_SUM,decimal.toPlainString())
    appEventsLogger?.logEvent(EVENT_NAME_AD_IMPRESSION, parameters)
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
}
