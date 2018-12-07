import 'dart:async';

import 'package:flutter/services.dart';

class MovisensFlutter {
//  MethodChannel _channel = MethodChannel('movisens_flutter');
  EventChannel _eventChannel = EventChannel('movisens.event_channel');
  Stream<String> _movisenStream;

  Stream<String> get movisensStream {
    _movisenStream = _eventChannel.receiveBroadcastStream().map((x) => x);
    return _movisenStream;
  }


}
