package ir.metrix.flutter;

import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import ir.metrix.messaging.RevenueCurrency;
import ir.metrix.AttributionData;

/**
 * MetrixSDKFlutterPlugin
 */
public class Metrix implements FlutterPlugin, MethodCallHandler {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private MethodChannel channel;

    @Override
    public void onAttachedToEngine(FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "Metrix");
        channel.setMethodCallHandler(this);
    }

    // This static function is optional and equivalent to onAttachedToEngine. It supports the old
    // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
    // plugin registration via this function while apps migrate to use the new Android APIs
    // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
    //
    // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
    // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
    // depending on the user's project. onAttachedToEngine or registerWith must both be defined
    // in the same class.
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "Metrix");
        channel.setMethodCallHandler(new Metrix());
    }

    @Override
    public void onMethodCall(MethodCall call, final Result result) {
        switch (call.method) {
            case "newEvent": {
                String slug = call.argument("slug");
                Map<String, String> attr = call.argument("attributes");

                ir.metrix.Metrix.newEvent(slug, attr);
                break;
            }
            case "addUserAttributes": {
                Map<String, String> attr = call.argument("attributes");
                ir.metrix.Metrix.addUserAttributes(attr);
                break;
            }
            case "setUserIdListener": {
                ir.metrix.Metrix.setUserIdListener(
                        new ir.metrix.UserIdListener() {
                            @Override
                            public void onUserIdReceived(String userId) {
                                result.success(userId);
                            }
                        });
                break;
            }
            case "getSessionNumber": {
                result.success(ir.metrix.Metrix.getSessionNum());
                break;
            }
            case "getSessionId": {
                result.success(ir.metrix.Metrix.getSessionId());
                break;
            }
            case "newRevenue": {
                String slug = call.argument("slug");
                Double amount = call.argument("amount");
                Integer currency = call.argument("currency");
                String orderId = call.argument("orderId");
                RevenueCurrency revenueCurrency = RevenueCurrency.IRR;
                if (currency != null && currency == 1)
                    revenueCurrency = RevenueCurrency.USD;
                if (currency != null && currency == 2)
                    revenueCurrency = RevenueCurrency.EUR;

                ir.metrix.Metrix.newRevenue(slug, amount, revenueCurrency, orderId);
                break;
            }
            case "setAppSecret": {
                Integer secretId = call.argument("secretId");
                Integer info1 = call.argument("info1");
                Integer info2 = call.argument("info2");
                Integer info3 = call.argument("info3");
                Integer info4 = call.argument("info4");

                if (secretId != null
                        && info1 != null
                        && info2 != null
                        && info3 != null
                        && info4 != null
                ) {
                    ir.metrix.Metrix.setAppSecret(secretId, info1, info2, info3, info4);
                }
                break;
            }
            case "setDefaultTracker": {
                String token = call.argument("trackerToken");
                if (token != null) {
                    ir.metrix.Metrix.setDefaultTracker(token);
                }
                break;
            }
            case "setStore": {
                String storeName = call.argument("storeName");
                if (storeName != null) {
                    ir.metrix.Metrix.setStore(storeName);
                }
                break;
            }
            case "setPushToken": {
                String token = call.argument("token");
                if (token != null) {
                    ir.metrix.Metrix.setPushToken(token);
                }
                break;
            }
            case "getAttributionData": {
                ir.metrix.Metrix.setOnAttributionChangedListener(
                        new ir.metrix.OnAttributionChangeListener() {
                            @Override
                            public void onAttributionChanged(AttributionData attributionData) {
                                Map<String, String> attributionDataMap = new HashMap<>();
                                if (attributionData.getAcquisitionAd() != null) {
                                    attributionDataMap.put("acquisitionAd",
                                            attributionData.getAcquisitionAd());
                                }
                                if (attributionData.getAcquisitionAdSet() != null) {
                                    attributionDataMap.put("acquisitionAdSet",
                                            attributionData.getAcquisitionAdSet());
                                }
                                if (attributionData.getAcquisitionCampaign() != null) {
                                    attributionDataMap.put("acquisitionCampaign",
                                            attributionData.getAcquisitionCampaign());
                                }
                                if (attributionData.getAcquisitionSource() != null) {
                                    attributionDataMap.put("acquisitionSource",
                                            attributionData.getAcquisitionSource());
                                }
                                if (attributionData.getAttributionStatus() != null) {
                                    attributionDataMap.put("attributionStatus",
                                            attributionData.getAttributionStatus().name());
                                }
                                result.success(attributionDataMap);
                            }
                        });
                break;
            }
            case "getDeeplinkResponse": {
                final Boolean shouldLaunchDeeplink = call.argument("shouldLaunchDeeplink");
                ir.metrix.Metrix.setOnDeeplinkResponseListener(
                        new ir.metrix.OnDeeplinkResponseListener() {
                            @Override
                            public boolean launchReceivedDeeplink(Uri deeplink) {
                                result.success(deeplink.toString());
                                return shouldLaunchDeeplink;
                            }
                        });
                break;
            }
            default:
                result.notImplemented();
        }
    }

    @Override
    public void onDetachedFromEngine(FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }
}
