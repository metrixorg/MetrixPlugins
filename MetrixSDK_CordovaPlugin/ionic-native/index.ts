import { Injectable } from '@angular/core';
import { Cordova, IonicNativePlugin, Plugin } from '@ionic-native/core';

export interface MetrixAttribution {
    acquisitionAd: string;
    acquisitionAdSet: string;
    acquisitionCampaign: string;
    acquisitionSource: string;
    attributionStatus: MetrixAttributionStatus;
}

export enum MetrixAttributionStatus {
    Attributed = 'ATTRIBUTED',
    NotAttributedYet = 'NOT_ATTRIBUTED_YET',
    AttributionNotNeeded = 'ATTRIBUTION_NOT_NEEDED',
    Unknown = 'UNKNOWN'
}

/**
 * @name Metrix
 * @description
 * This is the Ionic Cordova SDK of Metrix™. You can read more about Metrix™ at metrix.ir.
 *
 * Requires Cordova plugin: `ir.metrix.cordova`. 
 * 
 * @interfaces
 * MetrixAttribution
 * @classes
 * Metrix
 * @enums
 * MetrixAttributionStatus
 */
@Plugin({
    pluginName: 'Metrix',
    plugin: 'ir.metrix.cordova',
    pluginRef: 'Metrix',
    platforms: ['Android']
})

@Injectable()
export class Metrix extends IonicNativePlugin {

    /**
     * Function used to get Session Number
     * @return {Promise<number>} Returns a promise with session number value
     */
    @Cordova()
    getSessionNum(): Promise<number> { return; }

    /**
     * Function used to get user attribution data
     * @return {Promise<MetrixAttribution>} Returns a promise with attribution data
     */
    @Cordova()
    setAttributionChangeListener(): Promise<MetrixAttribution> { return; }

    /**
     * Function used to get deeplinks
     * @return {Promise<string>} Returns a promise with the deeplink
     */
    @Cordova()
    setDeeplinkResponseListener(): Promise<string> { return; }

    /**
     * Function used to get Metrix userId
     * @return {Promise<string>} Returns a promise with the user id
     */
    @Cordova()
    setUserIdListener(): Promise<string> { return; }

    /**
     * Function used to get Session Id
     * @return {Promise<string>} Returns a promise with session id value
     */
    @Cordova()
    getSessionId(): Promise<string> { return; }

    /**
     * This method tracks an event
     * @param {string} slug the slug of the event to be tracked
     * @param {Map<String, String> | object} attributes optional attributes of the event to be tracked
     */
    @Cordova({ sync: true })
    newEvent(slug: string, attributes?: Map<string, string> | object): void {}

    /**
     * This method sets the app SDK signature
     * @param {number} secretId
     * @param {number} info1
     * @param {number} info2
     * @param {number} info3
     * @param {number} info4
     */
    @Cordova({ sync: true })
    setAppSecret(secretId: number, info1: number, info2: number, info3: number, info4: number): void {}

    /**
     * This method adds the provided attributes to all metrix events
     * @param {Map<string, string> | object} attributes the attributes to be added to all future events
     */
    @Cordova({ sync: true })
    addUserAttributes(attributes: Map<string, string> | object): void {}

    /**
     * This method sets the value determining wether Metrix SDK should launch deferred deeplinks
     * @param {boolean} shouldLaunchDeeplink
     */
    @Cordova()
    setShouldLaunchDeeplink(shouldLaunchDeeplink: boolean): void {}

    /**
     * This method sets the build store name
     * @param {string} storeName the store name
     */
    @Cordova({ sync: true })
    setStore(storeName: string): void {}
    
    /**
     * This method sets the build default tracker
     * @param {string} trackerToken the tracker token
     */
    @Cordova({ sync: true })
    setDefaultTracker(trackerToken: string): void {}

    /**
     * This method sets the FCM push token
     * @param {string} pushToken the push token
     */
    @Cordova({ sync: true })
    setPushToken(pushToken: string): void {}

    /**
     * This method tracks a revenue
     * @param {string} slug the slug of the event to be tracked
     * @param {number} amount the amount of money gained by the revenue
     * @param {number} currency (OPTIONAL) currency of the amount
     * @param {string} orderId (OPTIONAL) id of the revenue
     */
    @Cordova({ sync: true })
    newRevenue(slug: string, amount: number, currency?: number, orderId?: string): void {}

    /**
     * By making this call, the Metrix SDK will try to find if there is any new attribution info inside of the deep link and if any, it will be sent to the Metrix backend.
     * @param {string} url URL of the deeplink
     */
    @Cordova({ sync: true })
    appWillOpenUrl(url: string): void {}
}
