import 'package:Metrix/Metrix.dart';
import 'package:Metrix/MetrixAttribution.dart';
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String sessionNumber = "";
  String sessionId = "";
  String userId = "";
  String deeplink = "";
  MetrixAttribution attribution;

  @override
  void initState() {
    super.initState();

    setState(() {
      sessionNumber = "";
      sessionId = "";
      userId = "";
      deeplink = "";
      attribution = null;
    });

    Metrix.setDefaultTracker('token');
    Metrix.setStore('store');
    Metrix.setPushToken('pushToken');

    Metrix.addUserAttributes({
      "name": "hisName"
    });

    Metrix.getAttributionData().then((value) => {
      this.setState(() {
        attribution = value;
      })
    });

    Metrix.shouldLaunchDeeplink = true;
    Metrix.getDeeplinkResponse().then((value) => {
      this.setState(() {
        deeplink = value;
      })
    });

    Metrix.getUserId().then((value) => {
      this.setState(() {
        userId = value;
      })
    });

    Metrix.getSessionNumber().then((sessionNum) => {
      this.setState(() {
        sessionNumber = sessionNum.toString();
      })
    });

    Metrix.getSessionId().then((id) => {
      this.setState(() {
        sessionId = id;
      })
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Metrix plugin example app'),
        ),
        body: Center(child: Column(children: <Widget>[
          Container(
            margin: EdgeInsets.all(20),
            child: Text('Session number is: $sessionNumber')
          ),
          Container(
            margin: EdgeInsets.all(20),
            child: Text('Session id is: $sessionId')
          ),
          Container(
            margin: EdgeInsets.all(20),
            child: FlatButton(
              child: Text('SendEvent'),
              color: Colors.blueAccent,
              textColor: Colors.white,
              onPressed: () {
                Metrix.newEvent('perzu', {
                  "name": "hisName"
                });
              },
            ),
          ),
          Container(
            margin: EdgeInsets.all(20),
            child: FlatButton(
              child: Text('SendRevenue'),
              color: Colors.blueAccent,
              textColor: Colors.white,
              onPressed: () {
                Metrix.newRevenue('perzu', 2500.5, 0, null);
              },
            ),
          ),
          Container(
              margin: EdgeInsets.all(20),
              child: Text('UserId is: $userId')
          ),
          Container(
              margin: EdgeInsets.all(20),
              child: Text('Deeplink is: $deeplink')
          ),
          Container(
              margin: EdgeInsets.all(20),
              child: Text('User attribution status: ${attribution != null ? attribution.attributionStatus : ""}')
          ),
        ]))
      ),
    );
  }
}
