package ir.metrix.reactnative;

import android.app.Application;
import android.net.Uri;
import android.os.Bundle;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.WritableMap;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import ir.metrix.AttributionData;
import ir.metrix.messaging.RevenueCurrency;
import ir.metrix.OnAttributionChangeListener;
import ir.metrix.OnDeeplinkResponseListener;
import ir.metrix.UserIdListener;

public class MetrixModule extends ReactContextBaseJavaModule {

    private static final String TAG = "Metrix";

    public MetrixModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return TAG;
    }

    @ReactMethod
    public void newEvent(String eventName) {
        ir.metrix.Metrix.newEvent(eventName);
    }

    @ReactMethod
    public void newCustomEvent(String eventName, ReadableMap customAttributes) {
        Map<String, String> customAttr = new HashMap<>();

        ReadableMapKeySetIterator customAttrMapKeySetIterator = customAttributes.keySetIterator();
        while (customAttrMapKeySetIterator.hasNextKey()) {
            String key = customAttrMapKeySetIterator.nextKey();
            customAttr.put(key, customAttributes.getString(key));
        }

        ir.metrix.Metrix.newEvent(eventName, customAttr);
    }

    @ReactMethod
    public void newRevenueSimple(String slug, Double revenue) {
        ir.metrix.Metrix.newRevenue(slug, revenue);
    }

    @ReactMethod
    public void newRevenueCurrency(String slug, Double revenue, String currency) {
        ir.metrix.Metrix.newRevenue(slug, revenue, RevenueCurrency.valueOf(currency));
    }

    @ReactMethod
    public void newRevenueOrderId(String slug, Double revenue, String orderId) {
        ir.metrix.Metrix.newRevenue(slug, revenue, orderId);
    }

    @ReactMethod
    public void newRevenueFull(String slug, Double revenue, String currency, String orderId) {
        ir.metrix.Metrix.newRevenue(slug, revenue, RevenueCurrency.valueOf(currency), orderId);
    }

    @ReactMethod
    public void addUserAttributes(ReadableMap userAttributes) {

        Map<String, String> userAttrs = new HashMap<>();

        ReadableMapKeySetIterator userAttributesMapKeySetIterator = userAttributes.keySetIterator();
        while (userAttributesMapKeySetIterator.hasNextKey()) {
            String key = userAttributesMapKeySetIterator.nextKey();
            userAttrs.put(key, userAttributes.getString(key));
        }

        ir.metrix.Metrix.addUserAttributes(userAttrs);
    }

    @ReactMethod
    public void setPushToken(String token) {
        ir.metrix.Metrix.setPushToken(token);
    }

    @ReactMethod
    public void getSessionNum(Callback callback) {
        if (callback != null) {
            callback.invoke(ir.metrix.Metrix.getSessionNum());
        }
    }

    @ReactMethod
    public void getSessionId(Callback callback) {
        if (callback != null) {
            callback.invoke(ir.metrix.Metrix.getSessionId());
        }
    }

    @ReactMethod
    public void setOnAttributionChangedListener(final Callback callback) {

        if (callback != null) {
            ir.metrix.Metrix.setOnAttributionChangedListener(
                    new OnAttributionChangeListener() {
                        @Override
                        public void onAttributionChanged(AttributionData attributionData) {
                            WritableMap map = getWritableMapFromAttributionModel(attributionData);

                            callback.invoke(map);
                        }
                    });
        }
    }

    @ReactMethod
    public void setUserIdListener(final Callback callback) {

        if (callback != null) {
            ir.metrix.Metrix.setUserIdListener(
                    new UserIdListener() {
                        @Override
                        public void onUserIdReceived(String userId) {
                            callback.invoke(userId);
                        }
                    });
        }
    }

    @ReactMethod
    public void setOnDeeplinkResponseListener(final boolean shouldLaunchDeeplink, final Callback callback) {

        if (callback != null) {
            ir.metrix.Metrix.setOnDeeplinkResponseListener(
                    new OnDeeplinkResponseListener() {
                        @Override
                        public boolean launchReceivedDeeplink(Uri deeplink) {
                            callback.invoke(deeplink.toString());
                            return shouldLaunchDeeplink;
                        }
                    });
        }
    }

    private WritableMap getWritableMapFromAttributionModel(AttributionData attributionData) {
        WritableMap map = Arguments.createMap();


        if (attributionData.getAcquisitionAd() != null) {
            map.putString("acquisitionAd", attributionData.getAcquisitionAd());
        }
        if (attributionData.getAcquisitionAdSet() != null) {
            map.putString("acquisitionAdSet",
                    attributionData.getAcquisitionAdSet());
        }
        if (attributionData.getAcquisitionCampaign() != null) {
            map.putString("acquisitionCampaign",
                    attributionData.getAcquisitionCampaign());
        }
        if (attributionData.getAcquisitionSource() != null) {
            map.putString("acquisitionSource",
                    attributionData.getAcquisitionSource());
        }
        if (attributionData.getAttributionStatus() != null) {
            map.putString("attributionStatus",
                    attributionData.getAttributionStatus().name());
        }
        return map;
    }
}