package fyi.jue.mobile.android.telemetry.dataprovider;

public abstract class TelemetryDataProvider<T> {
    private final String fieldName;

    public TelemetryDataProvider(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public abstract T prepare();
}
