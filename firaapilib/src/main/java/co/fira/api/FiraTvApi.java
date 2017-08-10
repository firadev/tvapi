package co.fira.api;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import co.fira.api.tv.GetChannelHlsAsync;
import co.fira.api.tv.GetChannelListAsync;

/**
 * Created by sam on 10/08/17.
 */

public class FiraTvApi {

    private static final String TAG = "FiraAPI";
    private FiraIdentifier appIdentifier;

    public interface ResponseListener {
        void OnFiraTvApiResponse(int requestCode, int resultCode, String data);
    }

    public FiraTvApi(Context context) {
        ApplicationInfo caller = context.getApplicationInfo();
        PackageManager pm = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = pm.getPackageInfo(caller.packageName, PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {

        }

        //String serialNumber = Build.SERIAL != Build.UNKNOWN ? Build.SERIAL : Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        /// Get SIM Count
        int simSlotCount = 1;
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1) {
            SubscriptionManager subscriptionManager = SubscriptionManager.from(context);
            simSlotCount = subscriptionManager.getActiveSubscriptionInfoCountMax();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            TelephonyManager manager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            simSlotCount = manager.getPhoneCount();
        }

        appIdentifier = new FiraIdentifier();
        appIdentifier.firaId = "sample@fira.co";
        appIdentifier.deviceManufacturer = Build.MANUFACTURER;
        appIdentifier.deviceModel = Build.MODEL;
        appIdentifier.deviceSerialNumber = Build.SERIAL;
        appIdentifier.deviceImei1 = "";
        appIdentifier.deviceImei2 = "";
        appIdentifier.appId = info.packageName;
        appIdentifier.appVersion = info.versionName;

        Log.d(TAG, "Application\n\t" +
                "firaId: " + caller.toString() + "\n\t" +
                "deviceModel: " + Build.MODEL + "\n\t" +
                "deviceImei1: " + info.versionName + "\n\t" +
                "deviceImei2: " + info.versionName + "\n\t" +
                "deviceSerialNumber: " + Build.SERIAL + "\n\t" +
                "appId: " + info.packageName + "\n\t" +
                "appVersion: " + info.versionName);
    }

    public void getChannelList(ResponseListener listener, int requestCode, int channelId) {
        new GetChannelListAsync(listener, requestCode, appIdentifier).execute(channelId);
    }

    public void getChannelHls(ResponseListener listener, int requestCode, int parentId, int channelIid) {
        //new GetChannelHlsAsync(listener, requestCode).execute(parentId, channelIid);
        new GetChannelHlsAsync(listener, requestCode, appIdentifier).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, parentId, channelIid);
    }

}
