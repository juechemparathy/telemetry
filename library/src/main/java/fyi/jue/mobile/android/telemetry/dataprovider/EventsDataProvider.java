package fyi.jue.mobile.android.telemetry.dataprovider;

import fyi.jue.mobile.android.telemetry.config.TelemetryConfig;
import fyi.jue.mobile.android.telemetry.event.TelemetryEvent;

/**
 * Measurements around the data that is passed as part of TelemetryEvent.
 */
public class EventsDataProvider extends TelemetryDataProvider {
    private static final String FIELD_NAME = "event";
    private TelemetryConfig configuration;
    private TelemetryEvent telemetryEvent;

    public EventsDataProvider(TelemetryConfig configuration) {
        super(FIELD_NAME);
        this.configuration = configuration;
    }

    public void set(TelemetryEvent telemetryEvent) {
        this.telemetryEvent = telemetryEvent;
    }

    @Override
    public Object prepare() {
        if (telemetryEvent == null) {
            throw new IllegalStateException("TelemetryEvent not set");
        }
        return telemetryEvent.toQueryString();
    }
}
