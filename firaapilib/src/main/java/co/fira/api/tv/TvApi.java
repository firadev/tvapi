package co.fira.api.tv;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import co.fira.api.FiraIdentifier;
import co.fira.api.tv.model.RequestData;

/**
 * Created by sam on 10/08/17.
 */

public class TvApi {

    private static final String     API_HOST		= "tv.fira.id";
    private static final String     API_PATH        = "request.php";
    private static final String     API_PATH_v3     = "/api/tv_v3_staging/request.php";

    private static final String     API_URL         = "https://" + API_HOST + "/" + API_PATH;
    private static final String     API_URL_v3      = "https://" + API_HOST + "/" + API_PATH_v3;

    private static final int ACTION_GET_CHANNEL_LIST		= 5;
    private static final int ACTION_GET_CHANNEL_HLS			= 6;

    /// Get Channel List
    public String actionMethod1(int arg, FiraIdentifier arg2) {
        //    throws Exception, InterruptedIOException, MalformedURLException {
        RequestData reqData = new RequestData();
        reqData.action = ACTION_GET_CHANNEL_LIST;
        reqData.arg.parentId = 0;
        reqData.arg.channelId = arg;
        reqData.arg.user = arg2.firaId;
        reqData.arg.device.manufacturer = arg2.deviceManufacturer;
        reqData.arg.device.model = arg2.deviceModel;
        reqData.arg.device.serialNumber = arg2.deviceSerialNumber;
        reqData.arg.device.imei1 = arg2.deviceImei1;
        reqData.arg.device.imei2 = arg2.deviceImei2;
        reqData.arg.location.latitude = 0;
        reqData.arg.location.longitude = 0;

        String paramData = new Gson().toJson(reqData);
        Log.d("FiraAPI", "actionMethod1 paramData:" + paramData);
        String serverResponse = queryServer(API_URL_v3, "POST", paramData, arg2.appId, arg2.appVersion);
        Log.d("FiraAPI", "Server response: " + serverResponse);

        return serverResponse;
    }

    /// Get Channel HLS
    public String actionMethod2(int arg, int arg2, FiraIdentifier arg3) {
        //    throws Exception, InterruptedIOException, MalformedURLException {
        RequestData reqData = new RequestData();
        reqData.action = ACTION_GET_CHANNEL_HLS;
        reqData.arg.parentId = arg;
        reqData.arg.channelId = arg2;
        reqData.arg.user = arg3.firaId;
        reqData.arg.device.manufacturer = arg3.deviceManufacturer;
        reqData.arg.device.model = arg3.deviceModel;
        reqData.arg.device.serialNumber = arg3.deviceSerialNumber;
        reqData.arg.device.imei1 = arg3.deviceImei1;
        reqData.arg.device.imei2 = arg3.deviceImei2;
        reqData.arg.location.latitude = 0;
        reqData.arg.location.longitude = 0;

        String paramData = new Gson().toJson(reqData);
        Log.d("FiraAPI", "actionMethod2 paramData:" + paramData);
        //String paramData = "";//"{\"action\":" + ACTION_GET_CHANNEL_HLS + ",\"arg\":{\"category_id\":" + arg + ",\"channel_id\":" + arg2 + ",\"user\":\"" + app.getFiraID() + "\",\"device\":{\"model\":\"" + app.getDeviceModel() + "\",\"imei1\":\"" + app.getDeviceIMEI1() + "\",\"imei2\":\"" + app.getDeviceIMEI2() + "\"},\"location\":{\"lat\":0,\"long\":0}}}";
        String serverResponse = queryServer(API_URL_v3, "POST", paramData, arg3.appId, arg3.appVersion);
        Log.d("FiraAPI", "Server response: " + serverResponse);
        return serverResponse;
    }

    private String queryServer(String serverAddress, String method, String param, String appId, String appVersion) {
        //throws Exception {
        String serverResponse = null;

        URL url = null;
        HttpsURLConnection connection = null;
        int responseCode = 0;

        //MainApplication app = MainApplication.getInstance();
        try {
            url = new URL(serverAddress);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            // Ten seconds time out
            connection.setConnectTimeout(5 * 1000);
            //give it 15 seconds to respond
            connection.setReadTimeout(10 * 1000);

            connection.setRequestProperty("Content-Type", "application/json; charset=ISO-8859-1");
            connection.setRequestProperty("Accept-Encoding", "deflate");
            connection.setRequestProperty("Accept", "application/json, text/plain, */*");
            connection.setRequestProperty("Accept-Charset", "utf-8");
            connection.setRequestProperty("User-Agent", "Polytron");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Fira-App-Id", appId);
            connection.setRequestProperty("Fira-App-Ver", appVersion);

            // For POST
            connection.setUseCaches (false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(param);
            wr.flush ();
            wr.close ();

            InputStream in = new BufferedInputStream(connection.getInputStream());
            serverResponse = extractResponse(in);
        } catch (Exception e) {
            serverResponse = "{\"error\":-1,\"message\":\"" + e.toString() + "\",\"data\":null}";
        } finally {
            connection.disconnect();
        }

        return serverResponse;
    }

    private static String extractResponse(InputStream in) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line = null;
        String result = "";
        try {
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
        return result;
    }

}
