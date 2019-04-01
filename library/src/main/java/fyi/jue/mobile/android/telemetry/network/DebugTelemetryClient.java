package fyi.jue.mobile.android.telemetry.network;

import android.util.Log;

import fyi.jue.mobile.android.telemetry.TelemetryBeacon;
import fyi.jue.mobile.android.telemetry.config.TelemetryConfig;

public class DebugTelemetryClient implements TelemetryClient {
    private static String TAG = DebugTelemetryClient.class.getSimpleName();

    @Override
    public boolean fireEvent(TelemetryConfig config, TelemetryBeacon telemetryBeacon) {
        Log.d(TAG, telemetryBeacon.getDataMap().toString());
        return false;
    }
}
