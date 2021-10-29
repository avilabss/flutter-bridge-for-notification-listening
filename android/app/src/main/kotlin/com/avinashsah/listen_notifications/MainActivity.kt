package com.avinashsah.listen_notifications

import android.content.Intent
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {
    private var CHANNEL = "com.avinashsah/helpers"
    private lateinit var channel:MethodChannel

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        channel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
        MethodChannelHolder.mc = channel
        val intent = Intent(this@MainActivity, NotificationListener::class.java)
        startService(intent)
        channel.setMethodCallHandler { call, result ->
            if (call.method == "getResponseFromKotlin") {
                result.success("Response From Kotlin!!!")
            } else {
                result.notImplemented()
            }
        }
    }
}

class MethodChannelHolder{
    companion object{
        lateinit var mc: MethodChannel
    }
}
