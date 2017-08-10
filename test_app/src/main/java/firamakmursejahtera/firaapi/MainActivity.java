package firamakmursejahtera.firaapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import co.fira.api.FiraTvApi;
//import co.firaapi.tv.TvApi;

public class MainActivity extends AppCompatActivity {

    private FiraTvApi firaTvApi;
    private FiraTvApi.ResponseListener tvApiListener = new FiraTvApi.ResponseListener() {
        @Override
        public void OnFiraTvApiResponse(int requestCode, int resultCode, String data) {
            if (resultCode==RESULT_OK) {
                switch (requestCode) {
                    case 1:
                        break;
                    case 2:
                        break;
                }
            } else if (resultCode==RESULT_CANCELED) {

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firaTvApi = new FiraTvApi(MainActivity.this);
        //firaTvApi.getChannelList(tvApiListener, 1, 1);
        firaTvApi.getChannelHls(tvApiListener, 2, 1, 40);
    }

}
