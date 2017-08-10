package co.fira.api.tv;

import android.os.AsyncTask;
import com.google.gson.Gson;

import co.fira.api.FiraIdentifier;
import co.fira.api.FiraTvApi;
import co.fira.api.FiraTvApi.ResponseListener;
import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by Sam on 30 Maret 2017
 * Fira Makmur Sejahtera
 * Jakarta, Indonesia
 */
public class GetChannelHlsAsync extends AsyncTask</*Params*/Integer, /*Progress*/Void, /*Result*/ /*HttpResponseModel*/String> {
    private ResponseListener    mListener;
    private int                 mRequestCode;
    private FiraIdentifier mFiraIdentifier;

    /// Default constructor
    public GetChannelHlsAsync(FiraTvApi.ResponseListener listener, int requestCode,  FiraIdentifier identifier) {
        mListener = listener;
        mRequestCode = requestCode;
        mFiraIdentifier = identifier;
    }

    @Override
    protected /*HttpResponseModel*/String doInBackground(Integer... params) {
        int parentId  = params[0];
        int channelId	= params[1];
        TvApi firaApi = new TvApi();
        String result = firaApi.actionMethod2(parentId, channelId, mFiraIdentifier);
        return result;
    }

    @Override
    protected void onPostExecute(/*HttpResponseModel*/String response) {
        GetLiveTvChannelHlsResponse data1 = new Gson().fromJson(response, GetLiveTvChannelHlsResponse.class);
        int status = data1.error;

        if (status == 0) {
            if (mListener != null)
                mListener.OnFiraTvApiResponse(mRequestCode, RESULT_OK, response);
        } else {
            if (mListener != null)
                mListener.OnFiraTvApiResponse(mRequestCode, RESULT_CANCELED, response);
        }

    }

    private class GetLiveTvChannelHlsResponse {
        int error;
        String message;
        ChannelList data;
    }

    private class ChannelList {

    }
}
