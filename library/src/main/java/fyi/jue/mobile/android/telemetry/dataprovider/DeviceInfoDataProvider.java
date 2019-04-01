package fyi.jue.mobile.android.telemetry.dataprovider;

import android.os.Build;
import fyi.jue.mobile.android.telemetry.util.StringUtils;

public class DeviceInfoDataProvider extends TelemetryDataProvider {
    private static final String FIELD_NAME = "device";

    public DeviceInfoDataProvider() {
        super(FIELD_NAME);
    }

    @Override
    public Object prepare() {
        return StringUtils.assertLength(getManufacturer(), 20) + '-' +  StringUtils.assertLength(getModel(),  20);
    }

    String getManufacturer() {
        return Build.MANUFACTURER;
    }
    String getModel() {
        return Build.MODEL;
    }
}
