package fyi.jue.mobile.android.telemetry;

import fyi.jue.mobile.android.telemetry.util.StringUtils;

import java.util.Map;

public class DefaultTelemetryBeacon extends TelemetryBeacon {

    DefaultTelemetryBeacon(String beaconId, String uploadPath, Map<String, Object> measurementResults) {
        super(beaconId, uploadPath, measurementResults);
    }

    public String getQueryString() {
         StringBuilder builder = new StringBuilder(getUploadPath());
         return StringUtils.urlEncodeUTF8(builder, getDataMap());
    }
}
