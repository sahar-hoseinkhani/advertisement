package ir.ayantech.advertisement.helper

import com.adivery.sdk.NativeAd

typealias SimpleCallback = () -> Unit

typealias YektanetNativeAdCallBack = (NativeAd) -> Unit

typealias StringCallback = (String) -> Unit

typealias TwoStringCallback = (String, String) -> Unit

typealias IntCallback = (Int) -> Unit

typealias BooleanCallback = (Boolean) -> Unit

typealias StringReturn = () -> String