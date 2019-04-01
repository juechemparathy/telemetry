package fyi.jue.mobile.android.telemetry;

import android.support.annotation.NonNull;

import fyi.jue.mobile.android.telemetry.config.TelemetryConfig;
import fyi.jue.mobile.android.telemetry.event.TelemetryEvent;
import fyi.jue.mobile.android.telemetry.dataprovider.ClientIdDataProvider;

/**
 * Handles all DataProviders needed for the specific TelemetryBeaconBuilder.
 */
public class DefaultTelemetryBeaconBuilder extends TelemetryBeaconBuilder {

    public DefaultTelemetryBeaconBuilder(@NonNull TelemetryConfig configuration) {
        super(configuration);
        // Add Horizon specific dataProviders common to all beacons.
        addMeasurement(new ClientIdDataProvider(configuration));
    }

    @Override
    public DefaultTelemetryBeacon build(TelemetryEvent telemetryEvent) {
        super.build(telemetryEvent);
        final String beaconId = generateBeaconId();

        return new DefaultTelemetryBeacon(
                beaconId,
                getConfiguration().getServerEndpoint(),
                flushDataProviders());
    }
}
