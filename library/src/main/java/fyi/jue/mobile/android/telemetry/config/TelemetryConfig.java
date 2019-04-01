package fyi.jue.mobile.android.telemetry.config;

import android.content.Context;

import fyi.jue.mobile.android.telemetry.util.ContextUtils;

import java.util.Collections;
import java.util.Map;


public class TelemetryConfig {
    private static final String DEFAULT_SERVER_ENDPOINT = "https://example.com/";
    private static final String DEFAULT_USER_AGENT = "Telemetry/1.0 (Android)";
    private static final int DEFAULT_CONNECT_TIMEOUT = 10000;
    private static final int DEFAULT_READ_TIMEOUT = 30000;

    private boolean telemetryEnabled = true;
    private final Context context;
    private final Map<String, String> extraConfigs;
    private String appName;
    private String appVersion;
    private String serverEndpoint;
    private int connectTimeout;
    private int readTimeout;
    private String userAgent;


    public TelemetryConfig(Context context) {
        this.context = context.getApplicationContext();
        this.extraConfigs = Collections.emptyMap();

        setAppName(ContextUtils.getAppName(context));
        setAppVersion(ContextUtils.getVersionName(context));
        setServerEndpoint(DEFAULT_SERVER_ENDPOINT);
        setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);
        setReadTimeout(DEFAULT_READ_TIMEOUT);
        setUserAgent(DEFAULT_USER_AGENT);
    }

    public Context getContext() {
        return context;
    }

    public TelemetryConfig setServerEndpoint(String endpoint) {
        this.serverEndpoint = endpoint;
        return this;
    }

    public String getServerEndpoint() {
        return serverEndpoint;
    }

    public TelemetryConfig setExtraConfig(String key, String value) {
        extraConfigs.put(key, value);
        return this;
    }

    public Map<String, String> getExtraConfigs() {
        return extraConfigs;
    }

    public String getExtraConfig(String key) {
        return extraConfigs.get(key);
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public TelemetryConfig setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public TelemetryConfig setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public String getAppName() {
        return appName;
    }

    public TelemetryConfig setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public TelemetryConfig setAppVersion(String appVersion) {
        this.appVersion = appVersion;
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public TelemetryConfig setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public boolean isTelemetryEnabled() {
        return telemetryEnabled;
    }

    public TelemetryConfig setTelemetryEnabled(boolean telemetryEnabled) {
        this.telemetryEnabled = telemetryEnabled;
        return this;
    }

}
