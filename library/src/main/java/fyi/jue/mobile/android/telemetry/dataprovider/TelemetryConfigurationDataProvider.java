package fyi.jue.mobile.android.telemetry.dataprovider;

import fyi.jue.mobile.android.telemetry.config.TelemetryConfig;

public class TelemetryConfigurationDataProvider extends TelemetryDataProvider {
    private static final String FIELD_NAME = "config";
    private TelemetryConfig configuration;

    public TelemetryConfigurationDataProvider(TelemetryConfig configuration) {
        super(FIELD_NAME);
        this.configuration = configuration;
    }

    @Override
    public Object prepare() {
        return configuration.getAppName() + " - " +  configuration.getAppVersion();
    }
}
