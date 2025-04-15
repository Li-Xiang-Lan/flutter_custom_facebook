import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_custom_facebook/flutter_custom_facebook.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              TextButton(
                onPressed: (){
                  FlutterCustomFacebook.instance.initFaceBook(facebookId: "1337901080596464", facebookToken: "a5fdccfedd4bb9d734ce1fd86c64905e");
                },
                child: Text("init"),
              ),
              TextButton(
                onPressed: (){
                  FlutterCustomFacebook.instance.logPurchase(amount: 0.000033333, currency: "USD");
                },
                child: Text("logPurchase"),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
