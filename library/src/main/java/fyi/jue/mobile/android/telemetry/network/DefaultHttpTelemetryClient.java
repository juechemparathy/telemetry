package fyi.jue.mobile.android.telemetry.network;

import android.util.Log;

import fyi.jue.mobile.android.telemetry.DefaultTelemetryBeacon;
import fyi.jue.mobile.android.telemetry.TelemetryBeacon;
import fyi.jue.mobile.android.telemetry.config.TelemetryConfig;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Provide setup to switch between GET and POST
 */
public class DefaultHttpTelemetryClient implements TelemetryClient {

    private static String TAG = DefaultHttpTelemetryClient.class.getSimpleName();

    /**
     * Handle the response based  on the response code
     *
     * @param configuration
     * @param telemetryBeacon
     */
    @Override
    public boolean fireEvent(TelemetryConfig configuration, TelemetryBeacon telemetryBeacon) {
        HttpURLConnection connection = null;
        DefaultTelemetryBeacon beacon =  (DefaultTelemetryBeacon) telemetryBeacon;

        try {
            connection = openConnection(configuration.getServerEndpoint());
            connection.setConnectTimeout(configuration.getConnectTimeout());
            connection.setReadTimeout(configuration.getReadTimeout());

            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setRequestProperty("User-Agent", configuration.getUserAgent());
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            String queryString  = beacon.getQueryString();
            Log.d(TAG, "query string: " + queryString);
            int responseCode = connect(connection, queryString);
            Log.d(TAG, "response code: " + responseCode);

            if (responseCode >= 200 && responseCode <= 299) {
                // Known success errors (2xx):
                // 200 - OK. Request accepted into the pipeline.

                // We treat all success codes as successful upload even though we only expect 200.
                return true;
            } else if (responseCode >= 400 && responseCode <= 499) {
                // Potential client error on building the request properly and thus not recoverable.
                Log.e(TAG, "Server returned client error code: " + responseCode);
                return true;
            } else {
                // Known other errors:
                // 500 - internal error
                // Use the flag for retry mechanism if supported.
                Log.w(TAG, "Server returned response code: " + responseCode);
                return false;
            }
        } catch (MalformedURLException e) {
            // There's nothing we can do to recover from this here. So let's just log an error and
            // notify the service that this job has been completed - even though we didn't upload
            // anything to the server.
            Log.e(TAG, "Could not upload telemetry due to malformed URL", e);
            return true;
        } catch (IOException e) {
            Log.w(TAG, "IOException while uploading ping", e);
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * Connect and return response code.
     *
     * @return int response code
     */
    public int connect(HttpURLConnection connection, String beacon) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(connection.getOutputStream()))) {
            writer.write(beacon);
            writer.flush();
            writer.close();
            return connection.getResponseCode();
        } catch (ArrayIndexOutOfBoundsException e) {
            // We wrap it and handle it like other IO exceptions.
            throw new IOException(e);
        }
    }


    /**
     * Handle opening network connection.
     *
     * @param endpoint
     * @return
     * @throws IOException
     */
    HttpURLConnection openConnection(String endpoint) throws IOException {
        final URL url = new URL(endpoint);
        return (HttpURLConnection) url.openConnection();
    }
}
