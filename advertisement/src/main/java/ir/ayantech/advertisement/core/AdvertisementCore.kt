package ir.ayantech.advertisement.core

import android.app.Application
import android.content.Context
import android.view.ViewGroup
import com.adivery.sdk.*
import ir.ayantech.advertisement.helper.AdvertisementIdHelper
import ir.ayantech.advertisement.helper.SimpleCallback
import ir.ayantech.advertisement.helper.StringCallback
import ir.ayantech.advertisement.helper.TwoStringCallback

object AdvertisementCore {

    fun initialize(
        application: Application
    ) {
        Adivery.configure(application, AdvertisementIdHelper.getAppKey(application))
    }

    fun requestInterstitialAds(
        context: Context,
        customAdUnit: String? = null,
        onAdLoaded: StringCallback? = null,
        onAdClicked: StringCallback? = null,
        onAdShown: StringCallback? = null,
        onAdClosed: StringCallback? = null,
        onAdError: TwoStringCallback? = null,
    ) {
        //one time call is enough. adivery will prepare next ad after showing current ad automatically
        Adivery.prepareInterstitialAd(
            context,
            customAdUnit ?: AdvertisementIdHelper.getInterstitialAdUnitId(context)
        )
        Adivery.addListener(
            simplifiedInterstitialAdListener(
                onAdLoaded,
                onAdClicked,
                onAdShown,
                onAdClosed,
                onAdError
            )
        )
    }

    private fun simplifiedInterstitialAdListener(
        onAdLoaded: StringCallback?,
        onAdClicked: StringCallback?,
        onAdShown: StringCallback?,
        onAdClosed: StringCallback?,
        onAdError: TwoStringCallback?,
    ) = object : AdiveryListener() {
        override fun onInterstitialAdLoaded(placementId: String) {
            onAdLoaded?.invoke(placementId)
        }

        override fun onInterstitialAdClicked(placementId: String) {
            onAdClicked?.invoke(placementId)
        }

        override fun onInterstitialAdClosed(placement: String) {
            onAdClosed?.invoke(placement)
        }

        override fun onInterstitialAdShown(placementId: String) {
            onAdShown?.invoke(placementId)
        }

        override fun onError(placementId: String, reason: String) {
            onAdError?.invoke(placementId, reason)
        }
    }

    fun showInterstitialAds(
        context: Context,
        customAdUnit: String? = null
    ) {
        if (Adivery.isLoaded(customAdUnit ?: AdvertisementIdHelper.getInterstitialAdUnitId(context))
        ) {
            Adivery.showAd(customAdUnit ?: AdvertisementIdHelper.getInterstitialAdUnitId(context))
        }
    }

    fun requestBannerAds(
        context: Context,
        viewGroup: ViewGroup,
        bannerSize: BannerSize? = null,
        customAdUnit: String? = null,
        onAdLoaded: SimpleCallback? = null,
        onAdClicked: SimpleCallback? = null,
        onAdShown: SimpleCallback? = null,
        onError: StringCallback? = null
    ) {
        val adView = AdiveryBannerAdView(context)
        adView.setPlacementId(customAdUnit ?: AdvertisementIdHelper.getBannerAdUnitId(context))
        adView.setBannerSize(bannerSize ?: BannerSize.BANNER)
        adView.loadAd()
        viewGroup.addView(adView)
        adView.setBannerAdListener(
            simplifiedNativeAndBannerAdListener(
                onAdLoaded,
                onAdClicked,
                onAdShown,
                onError
            )
        )
    }

    private fun simplifiedNativeAndBannerAdListener(
        onAdLoaded: SimpleCallback?,
        onAdClicked: SimpleCallback?,
        onAdShown: SimpleCallback?,
        onAdError: StringCallback?,
    ) = object : AdiveryAdListener() {
        override fun onAdLoaded() {
            onAdLoaded?.invoke()
        }

        override fun onAdClicked() {
            onAdClicked?.invoke()
        }

        override fun onAdShown() {
            onAdShown?.invoke()
        }

        override fun onError(reason: String?) {
            reason?.let { onAdError?.invoke(it) }
        }
    }


    fun requestNativeAds(
        context: Context,
        layoutId: Int,
        customAdUnit: String? = null,
        onAdClicked: SimpleCallback? = null,
        onAdShown: SimpleCallback? = null,
        onError: StringCallback? = null,
        onAdLoaded: SimpleCallback? = null,
    ): AdiveryNativeAdView {
        val adiveryNativeAdView = AdiveryNativeAdView(context)
        adiveryNativeAdView.setPlacementId(
            customAdUnit ?: AdvertisementIdHelper.getNativeAdUnitId(context)
        )
        adiveryNativeAdView.setNativeAdLayout(layoutId)
        adiveryNativeAdView.setListener(
            simplifiedNativeAndBannerAdListener(
                onAdClicked,
                onAdShown,
                onAdLoaded,
                onError
            )
        )
        adiveryNativeAdView.loadAd()
        return adiveryNativeAdView
    }
}