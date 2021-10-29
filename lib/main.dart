import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Notification Listener',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        primarySwatch: Colors.deepPurple,
      ),
      home: const MyHomePage(title: 'Notification Listener'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  static const channel = MethodChannel("com.avinashsah/helpers");

  @override
  void initState() {
    super.initState();
    channel.setMethodCallHandler(kotlinMethodHandler);
  }

  Future<dynamic> kotlinMethodHandler(MethodCall methodCall) async {
    switch (methodCall.method) {
      case "ProcessNotification":
        print("Notification Arrived:");
        print(methodCall.arguments);
        break;
        // return "printThis was fired!";

      case "ListenerConnected":
        print("ListenerConnected from Kotlin!");
        break;

      default:
        throw MissingPluginException('notImplemented');
    }
  }

  Future<void> _doSomething() async {
    String response = "";

    try {
      final String result = await channel.invokeMethod("getResponseFromKotlin");
      response = result;
    } on PlatformException catch (e) {
      response = "Failed to Invoke: '${e.message}'.";
    }

    print("Response -> $response");
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            ElevatedButton(
                onPressed: _doSomething, child: const Text("Start Listening!"))
          ],
        ),
      ),
    );
  }
}
