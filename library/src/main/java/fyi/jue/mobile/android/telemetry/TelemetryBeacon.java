package fyi.jue.mobile.android.telemetry;

import java.util.Map;

public class TelemetryBeacon {
    private final String beaconId;
    private final String uploadPath;
    private final Map<String, Object> dataMap;

    TelemetryBeacon(String beaconId, String uploadPath, Map<String, Object> dataMap) {
        this.beaconId = beaconId;
        this.uploadPath = uploadPath;
        this.dataMap = dataMap;
    }

    public String getBeaconId() {
        return beaconId;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }
}
