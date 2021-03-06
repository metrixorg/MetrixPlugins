import 'dart:async';

import 'package:flutter/services.dart';

import 'MetrixAttribution.dart';

class Metrix {

  static var shouldLaunchDeeplink = true;

  static const MethodChannel _channel = const MethodChannel('Metrix');

  static void newEvent(String slug, Map<String, String> attributes) {
    _channel.invokeMethod('newEvent', {
      'slug': slug,
      'attributes': attributes,
    });
  }

  static void addUserAttributes(Map<String, String> attributes) {
    _channel.invokeMethod('addUserAttributes', {
      'attributes': attributes
    });
  }

  static void newRevenue(
      String slug, double amount, int currency, String orderId) {
    _channel.invokeMethod('newRevenue', {
      'slug': slug,
      'amount': amount,
      'currency': currency,
      'orderId': orderId
    });
  }

  static void setPushToken(String token) {
    _channel.invokeMethod('setPushToken', {
      'token': token
    });
  }

  static Future<int> getSessionNumber() async {
    final int sessionNum =
      await _channel.invokeMethod('getSessionNumber');
    return sessionNum;
  }

  static Future<String> getSessionId() async {
    final String sessionId =
      await _channel.invokeMethod('getSessionId');
    return sessionId;
  }

  static Future<String> getUserId() async {
    final String id =
      await _channel.invokeMethod('setUserIdListener');
    return id;
  }

  static void setShouldLaunchDeeplink(bool shouldLaunch) {
    shouldLaunchDeeplink = shouldLaunch;
  }

  static Future<MetrixAttribution> getAttributionData() async {
    final Map attribution =
    await _channel.invokeMethod('getAttributionData');
    return MetrixAttribution.fromMap(attribution);
  }

  static Future<String> getDeeplinkResponse() async {
    final String deeplink =
    await _channel.invokeMethod('getDeeplinkResponse', {
      'shouldLaunchDeeplink': shouldLaunchDeeplink
    });
    return deeplink;
  }
}