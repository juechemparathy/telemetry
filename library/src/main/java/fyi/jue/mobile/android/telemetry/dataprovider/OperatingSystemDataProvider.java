package fyi.jue.mobile.android.telemetry.dataprovider;

public class OperatingSystemDataProvider extends TelemetryDataProvider<String> {
    private static final String FIELD_NAME = "os";
    private static final String VALUE = "Android";
    public OperatingSystemDataProvider() {
        super(FIELD_NAME);
    }

    @Override
    public String prepare() {
        return VALUE;
    }
}
