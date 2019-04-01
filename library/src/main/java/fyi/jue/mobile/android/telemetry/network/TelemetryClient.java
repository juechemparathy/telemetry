package fyi.jue.mobile.android.telemetry.network;

import fyi.jue.mobile.android.telemetry.TelemetryBeacon;
import fyi.jue.mobile.android.telemetry.config.TelemetryConfig;

public interface TelemetryClient {
    boolean fireEvent(TelemetryConfig telemetryConfig, TelemetryBeacon telemetryBeacon);
}
