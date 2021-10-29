package com.avinashsah.listen_notifications

import android.annotation.SuppressLint
import android.os.Bundle
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import io.flutter.plugin.common.MethodChannel
import java.lang.Exception

@SuppressLint("NewApi", "OverrideAbstract")
class NotificationListener : NotificationListenerService() {
//    private var channel: MethodChannel? = MethodChannelHolder.mc
    private var isListenerConnected = false

    override fun onListenerConnected() {
        super.onListenerConnected()
        val channel: MethodChannel = MethodChannelHolder.mc

        println("Listener Connected!")
        channel.invokeMethod("ListenerConnected","")
        isListenerConnected = true
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        val channel: MethodChannel = MethodChannelHolder.mc

        println("New Notification!")
        var pack = "Empty"
        var title = "Empty"
        var text = "Empty"
        var extras: Bundle? = null

        try {
            pack = sbn?.packageName.toString()
            extras = sbn?.notification?.extras
            title = extras?.getString("android.title").toString()
            text = extras?.getCharSequence("android.text").toString()

            println(channel)

        } catch (e: Exception){
            println("Exception while onNotificationPosted: $e")
        }

//        println(pack)
//        println(title)
//        println(text)

        channel.invokeMethod("ProcessNotification", "$pack : $title : $text")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Listener Destroyed!")
    }
}
