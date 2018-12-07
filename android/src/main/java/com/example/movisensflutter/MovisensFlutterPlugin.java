package com.example.movisensflutter;

import android.content.Context;
import android.content.Intent;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;



/** MovisensFlutterPlugin */
public class MovisensFlutterPlugin implements MethodCallHandler {
  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
//    final MethodChannel channel = new MethodChannel(registrar.messenger(), "movisens_flutter");
//    channel.setMethodCallHandler(new MovisensFlutterPlugin());
    Context context = registrar.activeContext();
    Intent intent = new Intent(context, NewActivity.class);
    context.startActivity(intent);
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else {
      result.notImplemented();
    }
  }
}
