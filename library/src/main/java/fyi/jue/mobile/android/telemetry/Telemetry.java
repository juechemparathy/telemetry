package fyi.jue.mobile.android.telemetry;

import fyi.jue.mobile.android.telemetry.config.TelemetryConfig;
import fyi.jue.mobile.android.telemetry.event.TelemetryEvent;
import fyi.jue.mobile.android.telemetry.network.TelemetryClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Core VDMS Telemetry Implementation
 */
public final class Telemetry {
    private final TelemetryClient telemetryClient;
    private final TelemetryConfig telemetryConfig;
    private final List<TelemetryBeaconBuilder> beaconBuilders;

    public Telemetry(TelemetryClient telemetryClient, TelemetryConfig telemetryConfig) {
        this.telemetryClient = telemetryClient;
        this.telemetryConfig = telemetryConfig;
        beaconBuilders = new ArrayList<>();
    }

    public void queue(TelemetryEvent telemetryEvent) {
        // Use the provided TelemetryBeaconBuilder to prepare TelemetryBeacon out of TelemetryEvent
        // Use the provided TelemetryClient to dequeue.
        for (TelemetryBeaconBuilder beaconBuilder : beaconBuilders) {

            telemetryClient.fireEvent(telemetryConfig, beaconBuilder.build(telemetryEvent));
        }
    }

    public Telemetry addBeaconBuilder(TelemetryBeaconBuilder builder) {
        beaconBuilders.add(builder);
        return this;
    }

}
