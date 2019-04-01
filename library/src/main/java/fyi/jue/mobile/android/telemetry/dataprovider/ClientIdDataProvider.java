package fyi.jue.mobile.android.telemetry.dataprovider;

import fyi.jue.mobile.android.telemetry.config.TelemetryConfig;

import java.util.UUID;

/**
 * A unique, randomly generated UUID for this client.
 */
public class ClientIdDataProvider extends TelemetryDataProvider {
    private static final String FIELD_NAME = "clientId";
    private TelemetryConfig configuration;
    private String clientId;

    public ClientIdDataProvider(TelemetryConfig configuration) {
        super(FIELD_NAME);

        this.configuration = configuration;
    }

    @Override
    public Object prepare() {
        if (clientId == null) {
            clientId = generateClientId(configuration);
        }

        return clientId;
    }

    private static synchronized String generateClientId(final TelemetryConfig configuration) {
        final String clientId = UUID.randomUUID().toString();
        return clientId;
    }
}
