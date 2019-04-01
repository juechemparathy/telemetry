package fyi.jue.mobile.android.telemetry;

/**
 * Core Telemetry Manager
 */
public class TelemetryManager {
    private static Telemetry telemetry;

    public static void setTelemetry(Telemetry telemetry) {
        TelemetryManager.telemetry = telemetry;
    }

    public static Telemetry getTelemetry() {
        if (telemetry == null) {
            throw new IllegalStateException("Call setTelemetry() before calling getTelemetry()");
        }
        return telemetry;
    }
}
