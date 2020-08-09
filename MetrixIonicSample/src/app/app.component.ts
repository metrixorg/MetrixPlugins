import { Component } from '@angular/core';

import { Platform } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';
import { Metrix } from '@ionic-native/metrix';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss']
})
export class AppComponent {
  constructor(
    private platform: Platform,
    private splashScreen: SplashScreen,
    private statusBar: StatusBar
  ) {
    this.initializeApp();
  }

  initializeApp() {
    this.platform.ready().then(() => {

      this.statusBar.styleDefault();
      this.splashScreen.hide();

      this.initMetrix();
    });
  }

  initMetrix() {
    console.log("metrix", "initializing...");

    Metrix.setAttributionChangeListener(function(attribution) {
      console.log("[MetrixExample]: Attribution callback received.");
      console.log("[MetrixExample]: acquisitionAd = " + attribution.acquisitionAd);
      console.log("[MetrixExample]: acquisitionAdSet = " + attribution.acquisitionAdSet);
      console.log("[MetrixExample]: acquisitionCampaign = " + attribution.acquisitionCampaign);
      console.log("[MetrixExample]: acquisitionSource = " + attribution.acquisitionSource);
      console.log("[MetrixExample]: attributionStatus = " + attribution.attributionStatus);
    });

    Metrix.setPushToken("token");

    Metrix.setShouldLaunchDeeplink(true);
    Metrix.setDeeplinkResponseListener(function(deeplink) {
        console.log("[MetrixExample]: Deeplink callback received. deeplink: " + deeplink);
    });

    Metrix.setDefaultTracker("trackerToken");
    // Metrix.setAppSecret(2, 12345, 12345, 12345, 12345);
    Metrix.setStore("google play");

    Metrix.setUserIdListener(function(metrixUserId) {
        console.log("[MetrixExample]: UserId listener called. ID: " + metrixUserId);
    });

    Metrix.getSessionId(function(metrixSessionId) {
        console.log("[MetrixExample]: SessionId listener called. ID: " + metrixSessionId);
    });

    var attributes = {};
    attributes['first'] = 'Ken';
    attributes['last'] = 'Adams';
    Metrix.addUserAttributes(attributes);
  }
}
