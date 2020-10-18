import 'dart:math';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_openvpn_android/Models/OpenVPNData.dart';
import 'package:fluttertoast/fluttertoast.dart';

class Home extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        // This is the theme of your application.
        //
        // Try running your application with "flutter run". You'll see the
        // application has a blue toolbar. Then, without quitting the app, try
        // changing the primarySwatch below to Colors.green and then invoke
        // "hot reload" (press "r" in the console where you ran "flutter run",
        // or simply save your changes to "hot reload" in a Flutter IDE).
        // Notice that the counter didn't reset back to zero; the application
        // is not restarted.
        primarySwatch: Colors.blue,
        // This makes the visual density adapt to the platform that you run
        // the app on. For desktop platforms, the controls will be smaller and
        // closer together (more dense) than on mobile platforms.
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: MyHomePage(title: 'Flutter Demo Open VPN'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {

  int _counter = 0;
  String vpnState = "";
  String dataUsage = "You are not connect !";


  static const platform = const MethodChannel('openVPNChannel');
  static const EventChannel openVpnStateChannel = EventChannel('openVPNStateChannel');
  static const EventChannel openVpnDataUsageChannel = EventChannel('openVPNStateDataUsageChannel');



  @override
  void initState() {
    super.initState();
    //Setting For Recieving VPN State Changed :
    openVpnStateChannel.receiveBroadcastStream().listen(_onOpenVpnStateChanged, onError: _onOpenVpnStateChangedError);

    //Setting For Data Usage VPN Changed :
    openVpnDataUsageChannel.receiveBroadcastStream().listen(_onOpenVpnDataUsageChanged, onError: _onOpenVpnDataUsageChangedError);
  }



  void _onOpenVpnDataUsageChanged (Object event) {
    setState(() {
      String dataUsageObj = event;
      dataUsage = "↓ " + dataUsageObj.split("~")[0]  + " -- " + "↑ " + dataUsageObj.split("~")[1] ;
    });

  }


  void _onOpenVpnStateChanged (Object event) {
    //debugPrint('openVPNState: ' + event);
    setState(() {
      vpnState = event ;
    });

  }

  void _onOpenVpnStateChangedError (Object error) {

  }

  void _onOpenVpnDataUsageChangedError (Object error) {

  }


  Future<void> startOpenVPN() async {
    try {
      OpenVPNData openVPNData = OpenVPNData.getPreparedData();
      await platform.invokeMethod("startOpenVPN" , {"config": openVPNData.config ,
        "userName" : openVPNData.userName , "userPassword" : openVPNData.userPassword});
    } on PlatformException catch (e) {

    }
  }


  Future<void> stopOpenVPN() async {
    try {
      dataUsage = "You are not connect !";
      await platform.invokeMethod("stopOpenVPN" );
    } on PlatformException catch (e) {

    }
  }



  Future<void> getLastOpenVPNState() async {
    try {
      String connectionState = await platform.invokeMethod("getLastConnectionState" );
      setState(() {
        vpnState  = connectionState;
      });
    } on PlatformException catch (e) {

    }
  }


  @override
  Widget build(BuildContext context) {

    // getLastOpenVPNState();
    // This method is rerun every time setState is called, for instance as done
    // by the _incrementCounter method above.
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    return Scaffold(
      appBar: AppBar(
        // Here we take the value from the MyHomePage object that was created by
        // the App.build method, and use it to set our appbar title.
        title: Text(widget.title),
      ),
      body: Center(
        // Center is a layout widget. It takes a single child and positions it
        // in the middle of the parent.
        child: Column(
          // Column is also a layout widget. It takes a list of children and
          // arranges them vertically. By default, it sizes itself to fit its
          // children horizontally, and tries to be as tall as its parent.
          //
          // Invoke "debug painting" (press "p" in the console, choose the
          // "Toggle Debug Paint" action from the Flutter Inspector in Android
          // Studio, or the "Toggle Debug Paint" command in Visual Studio Code)
          // to see the wireframe for each widget.
          //
          // Column has various properties to control how it sizes itself and
          // how it positions its children. Here we use mainAxisAlignment to
          // center the children vertically; the main axis here is the vertical
          // axis because Columns are vertical (the cross axis would be
          // horizontal).
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              'Open VPN State :',
            ),
           Container (
               margin: const EdgeInsets.only(top: 10.0, bottom: 10.0),
               child :
             Text(
                 dataUsage,
                 style: TextStyle(color: Colors.blueAccent , fontStyle: FontStyle.italic ,
                 fontWeight:  FontWeight.bold  , fontSize: 15),
               )
           ) ,

            FlatButton(
              color: vpnState == "CONNECTED" ? Colors.green :  Colors.blue,
              textColor: Colors.white,
              disabledColor: Colors.grey,
              disabledTextColor: Colors.black,
              padding: EdgeInsets.all(8.0),
              splashColor: vpnState == "CONNECTED" ? Colors.greenAccent : Colors.blueAccent,
              onPressed: () {
                if(vpnState == "CONNECTED") {
                  stopOpenVPN();
                }else if(vpnState == "DISCONNECTED") {
                  startOpenVPN();
                }

              },
              child: Text(
                vpnState,
                style: TextStyle(fontSize: 20.0),
              ),
            )

          ],
        ),
      ),
    );
  }
}
