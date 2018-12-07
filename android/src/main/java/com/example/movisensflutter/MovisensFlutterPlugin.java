package com.example.movisensflutter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

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
    TapReceiver receiver = new TapReceiver();
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction(MovisensService.TAP_INTENT_NAME);
    context.registerReceiver(receiver, intentFilter);

    /// Start MoviSens service
    Intent intent = new Intent(context, NewActivity.class);
    context.startActivity(intent);
  }

  @Override
  public void onListen(Object o, EventChannel.EventSink eventSink) {
    this.eventSink = eventSink;
    eventSink.success("Hello world!");
  }

  @Override
  public void onCancel(Object o) {

  }

  class TapReceiver extends BroadcastReceiver {
    final static String TAG = "TapReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
      Log.d(TAG, "TapReceiver: onReceive()");
      String markerData = intent.getStringExtra(MovisensService.TAP_INTENT_NAME);
      eventSink.success(markerData);
    }
  }
}


