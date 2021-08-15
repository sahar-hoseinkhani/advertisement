package ir.ayantech.advertisement.helper

import android.content.Context
import ir.ayantech.advertisement.R

object AdvertisementIdHelper {

    fun getInterstitialAdUnitId(context: Context) =
        context.resources.getString(R.string.interstitialAdUnitID)

    fun getNativeAdUnitId(context: Context) =
        context.resources.getString(R.string.nativeAdUnitId)

    fun getBannerAdUnitId(context: Context) =
        context.resources.getString(R.string.bannerAdUnitId)

    fun getAppKey(context: Context) =
        context.resources.getString(R.string.appKey)
}