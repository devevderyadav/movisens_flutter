package com.example.movisensflutter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.util.HashMap;

import de.kn.uni.smartact.movisenslibrary.bluetooth.MovisensService;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.PluginRegistry.Registrar;



/** MovisensFlutterPlugin */
public class MovisensFlutterPlugin implements EventChannel.StreamHandler {

  private EventChannel.EventSink eventSink;

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    MovisensFlutterPlugin plugin = new MovisensFlutterPlugin(registrar.activeContext());
    EventChannel eventChannel = new EventChannel(registrar.messenger(), "movisens.event_channel");
    eventChannel.setStreamHandler(plugin);
  }

  public MovisensFlutterPlugin(Context context) {
    /// Set up the intent filter
    MovisensEventReceiver receiver = new MovisensEventReceiver();
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction(MovisensService.MOVISENS_INTENT_NAME);
    context.registerReceiver(receiver, intentFilter);

    /// Start MoviSens service
    Intent intent = new Intent(context, NewActivity.class);
    context.startActivity(intent);
  }

  @Override
  public void onListen(Object o, EventChannel.EventSink eventSink) {
    this.eventSink = eventSink;
  }

  @Override
  public void onCancel(Object o) {
    this.eventSink = null;

  }

  class MovisensEventReceiver extends BroadcastReceiver {
    final static String TAG = "MovisensEventReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
      Log.d(TAG, "MovisensEventReceiver: onReceive()");
      String batteryLevel = intent.getStringExtra(MovisensService.MOVISENS_BATTERY_LEVEL);
      String tapMarker = intent.getStringExtra(MovisensService.MOVISENS_TAP_MARKER);
      HashMap<String, String> data = new HashMap<>();

      if (batteryLevel != null) data.put("batteryLevel", batteryLevel);
      if (tapMarker != null) data.put("tapMarker", tapMarker);
      eventSink.success(data);
    }
  }
}


