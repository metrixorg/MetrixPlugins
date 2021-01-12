class MetrixAttribution {
  String acquisitionAd;
  String acquisitionAdSet;
  String acquisitionCampaign;
  String acquisitionSource;
  String attributionStatus;

  static MetrixAttribution fromMap(dynamic map) {
    MetrixAttribution attribution = new MetrixAttribution();
    try {
      attribution.acquisitionAd = map['acquisitionAd'];
      attribution.acquisitionAdSet = map['acquisitionAdSet'];
      attribution.acquisitionCampaign = map['acquisitionCampaign'];
      attribution.acquisitionSource = map['acquisitionSource'];
      attribution.attributionStatus = map['attributionStatus'];
    } catch (e) {
      print('[MetrixFlutter]: Failed to create MetrixAttribution object from given map object. Details: ' + e.toString());
    }
    return attribution;
  }
}