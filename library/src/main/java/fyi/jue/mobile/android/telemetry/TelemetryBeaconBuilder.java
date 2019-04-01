package fyi.jue.mobile.android.telemetry;

import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.VisibleForTesting;

import fyi.jue.mobile.android.telemetry.config.TelemetryConfig;
import fyi.jue.mobile.android.telemetry.event.TelemetryEvent;
import fyi.jue.mobile.android.telemetry.dataprovider.ClientIdDataProvider;
import fyi.jue.mobile.android.telemetry.dataprovider.EventsDataProvider;
import fyi.jue.mobile.android.telemetry.dataprovider.TelemetryDataProvider;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class TelemetryBeaconBuilder {
    private final List<TelemetryDataProvider> dataProviders;
    private EventsDataProvider eventsDataProvider;

    private TelemetryConfig configuration;

    public TelemetryBeaconBuilder(@NonNull TelemetryConfig configuration) {
        this.configuration = configuration;
        this.dataProviders = new LinkedList<>();

        // All pings contain a version and a client id
        addMeasurement(new ClientIdDataProvider(configuration));
    }

    public TelemetryConfig getConfiguration() {
        return configuration;
    }

    protected void addMeasurement(TelemetryDataProvider dataProvider) {
        dataProviders.add(dataProvider);
    }

    /**
     * Validate if we have all info for firing a beacon.
     *
     * @return
     */
    public boolean canBuild() {
        return true;
    }

    public TelemetryBeacon build(TelemetryEvent telemetryEvent) {
        final String documentId = generateBeaconId();

        if (!canBuild()) {
            return null;
        }
        eventsDataProvider = new EventsDataProvider(configuration);
        eventsDataProvider.set(telemetryEvent);
        addMeasurement(eventsDataProvider);

        return new TelemetryBeacon(
                documentId,
                configuration.getServerEndpoint(),
                flushDataProviders());
    }

    protected Map<String, Object> flushDataProviders() {
        final Map<String, Object> data = new LinkedHashMap<>();

        for (TelemetryDataProvider dataProvider : dataProviders) {
            data.put(dataProvider.getFieldName(), dataProvider.prepare());
        }
        return data;
    }

    public EventsDataProvider getEventsDataProvider() {
        return eventsDataProvider;
    }

    @VisibleForTesting
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public String generateBeaconId() {
        return UUID.randomUUID().toString();
    }
}
