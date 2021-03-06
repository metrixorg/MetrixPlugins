function callCordova(action) {
    var args = Array.prototype.slice.call(arguments, 1);

    cordova.exec(
        function callback(data) { },
        function errorHandler(err) { },
        'Metrix',
        action,
        args
    );
}

function callCordovaCallback(action, callback) {
    var args = Array.prototype.slice.call(arguments, 2);

    cordova.exec(callback,
        function errorHandler(err) { },
        'Metrix',
        action,
        args
    );
}

var Metrix = {
    setPushToken: function(token) {
        if (token) {
            callCordova('setPushToken', token);
        }
    },

    setOnAttributionChangedListener: function(callback) {
        if (callback != null) {
            callCordovaCallback('setAttributionChangeListener', callback);
        }
    },

    getSessionId: function(callback) {
        if (callback != null) {
            callCordovaCallback('getSessionId', callback);
        }
    },

    getSessionNum: function(callback) {
        if (callback != null) {
            callCordovaCallback('getSessionNum', callback);
        }
    },

    setUserIdListener: function(callback) {
        if (callback != null) {
            callCordovaCallback('setUserIdListener', callback);
        }
    },

    setShouldLaunchDeeplink: function(launch) {
        callCordova('setShouldLaunchDeeplink', launch);
    },

    setOnDeeplinkResponseListener: function(callback) {
        if (callback != null) {
            callCordovaCallback('setDeeplinkResponseListener', callback, this.shouldLaunchDeeplink);
        }
    },

    newEvent: function(slug, attributes) {
        if(attributes) {
            callCordova('trackCustomEvent', slug, attributes);
        } else {
            callCordova('trackSimpleEvent', slug);
        }
    },

    addUserAttributes: function(attributes) {
        callCordova('addUserDefaultAttributes', attributes);
    },

    newRevenue: function(slug, amount, currency, orderId) {
        var cr = null;
        if (currency === 0) {
            cr = "IRR";
        } else if (currency === 1) {
            cr = "USD";
        } else if (currency === 2) {
            cr = "EUR";
        } else {
            cr = "IRR";
        }
        if (orderId) {
            callCordova('trackFullRevenue', slug, amount, cr, orderId);
        } else {
            callCordova('trackSimpleRevenue', slug, amount, cr);
        }       
    },
};

module.exports = Metrix;