import {
    NativeModules
} from 'react-native';

let module_metrix = NativeModules.Metrix;

var Metrix = {};

Metrix.shouldLaunchDeeplink = true

Metrix.setPushToken = function(token) {
    module_metrix.setPushToken(token);
};

Metrix.getSessionNum = function(callback) {
    module_metrix.getSessionNum(callback);
};

Metrix.getSessionId = function(callback) {
    module_metrix.getSessionId(callback);
};

Metrix.setOnAttributionChangedListener = function(callback) {
    module_metrix.setOnAttributionChangedListener(callback);
};

Metrix.setOnDeeplinkResponseListener = function(callback) {
    module_metrix.setOnDeeplinkResponseListener(this.shouldLaunchDeeplink, callback);
};

Metrix.setUserIdListener = function(callback) {
    module_metrix.setUserIdListener(callback);
};

Metrix.newEvent = function(eventName, customAttributes) {
    if (customAttributes) {
        module_metrix.newCustomEvent(eventName, customAttributes);
    } else {
        module_metrix.newEvent(eventName);
    }
};

Metrix.newRevenue = function(slug, revenue, currency, orderId) {
    let cr = null;
    if (currency === 0) {
        cr = "IRR";
    } else if (currency === 1) {
        cr = "USD";
    } else if (currency == 2) {
        cr = "EUR";
    }

    if (cr == null && orderId == null) {
        module_metrix.newRevenueSimple(slug, revenue);
    } else if (cr == null && orderId != null) {
        module_metrix.newRevenueOrderId(slug, revenue, orderId);
    } else if (orderId == null) {
        module_metrix.newRevenueCurrency(slug, revenue, cr);
    } else {
        module_metrix.newRevenueFull(slug, revenue, cr, orderId);
    }
};

Metrix.addUserAttributes = function(userAttributes) {
    module_metrix.addUserAttributes(userAttributes);
};

module.exports = { Metrix }