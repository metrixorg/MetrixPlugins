using UnityEngine;
using System.Collections.Generic;
using ir.metrix.unity;

public class Main : MonoBehaviour
{
    // Start is called before the first frame update
    void Start()
    {
        Metrix.SetAttributionChangedListener(AttributionChangedCallback);

        Metrix.NewEvent("perzu");
        Metrix.SetPushToken("token");

        Metrix.SetShouldLaunchDeeplink(true);
        Metrix.SetDeeplinkResponseListener(DeeplinkCallback);

        Metrix.SetDefaultTracker("cf9z0p");
        
        // Metrix.SetAppSecret(2, 12345, 12345, 12345, 12345);
        
        Metrix.SetStore("google play");

        Metrix.SetUserIdListener(UserIdCallback);

        Debug.Log("[MetrixExample]: SessionId: " + Metrix.GetSessionId());
        Debug.Log("[MetrixExample]: SessionNum: " + Metrix.GetSessionNum());

        var attributes = new Dictionary<string, string>();
        attributes.Add("first", "Ken");
        attributes.Add("last", "Adams");
        Metrix.AddUserAttributes(attributes);
    }

    public void UserIdCallback(string userId)
    {
        Debug.Log("[MetrixExample]: UserId listener called. ID: " + userId);
    }

    public void SessionIdCallback(string sessionId)
    {
        Debug.Log("[MetrixExample]: SessionId listener called. ID: " + sessionId);
    }

    public void AttributionChangedCallback(MetrixAttribution attribution)
    {
        Debug.Log("[MetrixExample]: Attribution callback received.");
        Debug.Log("[MetrixExample]: acquisitionAd = " + attribution.acquisitionAd);
        Debug.Log("[MetrixExample]: acquisitionAdSet = " + attribution.acquisitionAdSet);
        Debug.Log("[MetrixExample]: acquisitionCampaign = " + attribution.acquisitionCampaign);
        Debug.Log("[MetrixExample]: acquisitionSource = " + attribution.acquisitionSource);
        Debug.Log("[MetrixExample]: attributionStatus = " + attribution.attributionStatus);
    }

    public void DeeplinkCallback(string uri)
    {
        Debug.Log("[MetrixExample]: Deeplink callback received. deeplink: " + uri);
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
