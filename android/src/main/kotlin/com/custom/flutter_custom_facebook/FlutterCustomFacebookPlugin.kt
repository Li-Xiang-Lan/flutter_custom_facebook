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
        Log.e("qwer","kk===initFaceBook==${facebookId}==${facebookToken}")
        FacebookSdk.setApplicationId(facebookId)
        FacebookSdk.setClientToken(facebookToken)
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
        Log.e("qwer","kk===logPurchase==${amount}==${currency}")
        val parameters = map["parameters"] as? Map<String, Any>
        val parameterBundle = createBundleFromMap(parameters) ?: Bundle()

        appEventsLogger?.logPurchase(amount, currency, parameterBundle)
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
    Log.e("qwer","kk===logEventAdImpression")
  }

  private fun createBundleFromMap(parameterMap: Map<String, Any>?): Bundle? {
    if (parameterMap == null) {
      return null
    }

    val bundle = Bundle()
    for (jsonParam in parameterMap.entries) {
      val value = jsonParam.value
      val key = jsonParam.key
      if (value is String) {
        bundle.putString(key, value as String)
      } else if (value is Int) {
        bundle.putInt(key, value as Int)
      } else if (value is Long) {
        bundle.putLong(key, value as Long)
      } else if (value is Double) {
        bundle.putDouble(key, value as Double)
      } else if (value is Boolean) {
        bundle.putBoolean(key, value as Boolean)
      } else if (value is Map<*, *>) {
        val nestedBundle = createBundleFromMap(value as Map<String, Any>)
        bundle.putBundle(key, nestedBundle as Bundle)
      } else {
        throw IllegalArgumentException(
          "Unsupported value type: " + value.javaClass.kotlin)
      }
    }
    return bundle
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
}
