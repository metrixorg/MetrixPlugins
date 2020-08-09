using UnityEngine;
using System;
using System.Collections.Generic;

namespace ir.metrix.unity
{
    public class Metrix
    {
		private static AndroidJavaClass metrix = new AndroidJavaClass("ir.metrix.unity.MetrixUnity");
        private static GameObject metrixManager = null;
        private static Action<string> deferredDeeplinkDelegate = null;
        private static Action<MetrixAttribution> userAttributionDelegate = null;
        private static Action<string> userIdDelegate = null;
        private static bool shouldLaunchDeferredDeeplink = true;

        public static int GetSessionNum()
        {
            return metrix.CallStatic<Int32>("getSessionNum");
        }
        public static string GetSessionId()
        {
            return metrix.CallStatic<String>("getSessionId");
        }
        public static void NewEvent(string eventName)
        {
			metrix.CallStatic("newEvent", eventName);
        }
        public static void NewEvent(string eventName,
                                    Dictionary<string, string> customAttributes)
        {
            metrix.CallStatic("newEvent", eventName, ConvertDictionaryToMap(customAttributes));
        }
        public static void NewRevenue(string slug, double revenue)
        {
            metrix.CallStatic("newRevenueSimple", slug, revenue);
        }
        public static void NewRevenue(string slug, double revenue, int currency)
        {
            string cr = null;
            if (currency == 0)
            {
                cr = "IRR";
            }
            else if (currency == 1)
            {
                cr = "USD";
            }
            else if (currency == 2)
            {
                cr = "EUR";
            }
            metrix.CallStatic("newRevenueCurrency", slug, revenue, cr);
        }
        public static void NewRevenue(string slug, double revenue, int currency, string orderId)
        {
            string cr = null;
            if (currency == 0)
            {
                cr = "IRR";
            }
            else if (currency == 1)
            {
                cr = "USD";
            }
            else if (currency == 2)
            {
                cr = "EUR";
            }
            metrix.CallStatic("newRevenueFull", slug, revenue, cr, orderId);
        }
        public static void NewRevenue(string slug, double revenue, string orderId)
        {
            metrix.CallStatic("newRevenueOrderId", slug, revenue, orderId);
        }
        public static void AppWillOpenUrl(string deeplink)
        {
            metrix.CallStatic("appWillOpenUrl", deeplink);
        }
        public static void AddUserAttributes(Dictionary<string, string> userAttrs)
        {
            metrix.CallStatic("addUserAttributes", ConvertDictionaryToMap(userAttrs));
        }
        public static void SetShouldLaunchDeeplink(bool launch)
        {
            shouldLaunchDeferredDeeplink = launch;
        }
        public static void SetPushToken(string pushToken)
        {
            metrix.CallStatic("setPushToken", pushToken);
        }
        public static void SetStore(string storeName)
        {
            metrix.CallStatic("setStore", storeName);
        }
        public static void SetAppSecret(int secretId, long info1, long info2, long info3, long info4)
        {
            metrix.CallStatic("setAppSecret", secretId, info1, info2, info3, info4);
        }
        public static void SetDefaultTracker(string trackerToken)
        {
            metrix.CallStatic("setDefaultTracker", trackerToken);
        }
        public static void SetUserIdListener(Action<string> callback)
        {
            if (metrixManager == null)
            {
                metrixManager = new GameObject("MetrixManager");
                UnityEngine.Object.DontDestroyOnLoad(metrixManager);
                metrixManager.AddComponent<MetrixMessageHandler>();
            }

            userIdDelegate = callback;
            metrix.CallStatic("setUserIdListener");
        }

        public static void SetDeeplinkResponseListener(Action<string> callback)
        {
            if (metrixManager == null)
            {
                metrixManager = new GameObject("MetrixManager");
                UnityEngine.Object.DontDestroyOnLoad(metrixManager);
                metrixManager.AddComponent<MetrixMessageHandler>();
            }

            deferredDeeplinkDelegate = callback;
            metrix.CallStatic("setOnDeeplinkResponseListener", shouldLaunchDeferredDeeplink);
        }

        public static void SetAttributionChangedListener(Action<MetrixAttribution> callback)
        {
            if (metrixManager == null)
            {
                metrixManager = new GameObject("MetrixManager");
                UnityEngine.Object.DontDestroyOnLoad(metrixManager);
                metrixManager.AddComponent<MetrixMessageHandler>();
            }

            userAttributionDelegate = callback;
            metrix.CallStatic("setOnAttributionChangedListener");
        }

        public static void OnDeferredDeeplink(String uri)
        {
            if (deferredDeeplinkDelegate != null)
            {
                deferredDeeplinkDelegate(uri);
            }
        }
        public static void OnReceiveUserIdListener(String userId)
        {
            if (userIdDelegate != null)
            {
                userIdDelegate(userId);
            }
        }

        public static void OnAttributionChangeListener(String attributionDataString)
        {
            if (userAttributionDelegate != null)
            {
                userAttributionDelegate(new MetrixAttribution(attributionDataString));
            }
        }
        
        private static AndroidJavaObject ConvertDictionaryToMap(IDictionary<string, string> parameters)
        {
            AndroidJavaObject javaMap = new AndroidJavaObject("java.util.HashMap");
            IntPtr putMethod = AndroidJNIHelper.GetMethodID(
                javaMap.GetRawClass(), "put",
                    "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");

            object[] args = new object[2];
            foreach (KeyValuePair<string, string> kvp in parameters)
            {
                using (AndroidJavaObject k = new AndroidJavaObject(
                    "java.lang.String", kvp.Key))
                {
                    using (AndroidJavaObject v = new AndroidJavaObject(
                        "java.lang.String", kvp.Value))
                    {
                        args[0] = k;
                        args[1] = v;
                        AndroidJNI.CallObjectMethod(javaMap.GetRawObject(),
                                putMethod, AndroidJNIHelper.CreateJNIArgArray(args));
                    }
                }
            }

            return javaMap;
        }
    }
}

