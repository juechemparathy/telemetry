package fyi.jue.mobile.android.telemetry.event;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;

import fyi.jue.mobile.android.telemetry.TelemetryManager;
import fyi.jue.mobile.android.telemetry.util.StringUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * TelemetryEvent represents a beacon send to the destination.
 * Assume all strings in the event are ASCII.
 */
public class TelemetryEvent {
    private static final int MAX_LENGTH_GROUP = 10;
    private static final int MAX_LENGTH_TYPE = 10;
    private static final int MAX_LENGTH_CONTEXT = 20;
    private static final int MAX_LENGTH_EXTRA_KEY = 15;
    private static final int MAX_LENGTH_EXTRA_VALUE = 80;

    private static final String KEY_TIMESTAMP = "_cts";
    private static final String KEY_GROUP = "_cgroup";
    private static final String KEY_TYPE = "_ctype";
    private static final String KEY_CONTEXT = "_ccxt";

    private final long timestamp;
    private final String group;
    private final String type;
    private final String context;
    private final Map<String, String> extras;

    /**
     * Create a new event with mandatory category, method and object.
     *
     * @param group   Group name for events. ui,playback etc.
     * @param type    Type of event that occurred, e.g. click, keydown etc.
     * @param context Context where event occurred re.g. seekbar,volume button etc.
     */
    private TelemetryEvent(@NonNull String group, @NonNull String type, @Nullable String context) {
        timestamp = System.currentTimeMillis();
        this.group = StringUtils.assertLength(group, MAX_LENGTH_GROUP);
        this.type = StringUtils.assertLength(type, MAX_LENGTH_TYPE);
        this.context = context == null ? null : StringUtils.assertLength(context, MAX_LENGTH_CONTEXT);
        this.extras = new HashMap<>();
        extras.put(KEY_TIMESTAMP, Long.toString(timestamp));
        extras.put(KEY_GROUP, this.group);
        extras.put(KEY_TYPE, this.type);
        extras.put(KEY_CONTEXT, this.context);
    }

    @CheckResult
    public static TelemetryEvent create(@NonNull String group, @NonNull String type) {
        return new TelemetryEvent(group, type, null);
    }

    public TelemetryEvent extra(String key, String value) {
        extras.put(StringUtils.assertLength(key, MAX_LENGTH_EXTRA_KEY),
                StringUtils.assertLength(value, MAX_LENGTH_EXTRA_VALUE));
        return this;
    }

    /**
     * Queue this event with TelemetryClient to forward to the destination set on TelemetryConfig.
     */
    public void queue() {
        TelemetryManager.getTelemetry().queue(this);
    }

    /**
     * Create a JSON representation of this event for storing and sending it.
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public String toJSON() {
        final JSONArray array = new JSONArray();
        if (extras != null && !extras.isEmpty()) {
            array.put(new JSONObject(extras));
        }

        return array.toString();
    }

    /**
     * Create a queryString representation of this event for storing and sending it.
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public String toQueryString() {
        StringBuilder queryString =  new StringBuilder();
        return StringUtils.urlEncodeUTF8(queryString, extras);
    }
}
