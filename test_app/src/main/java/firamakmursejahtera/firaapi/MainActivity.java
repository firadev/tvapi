package firamakmursejahtera.firaapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import co.fira.api.FiraTvApi;

public class MainActivity extends AppCompatActivity {

    private static final int GET_CHANNEL_LIST = 1;
    private static final int GET_CHANNEL_HLS = 2;

    Button mButtonGetChannelList;
    Button mButtonGetChannelHls;
    Button mButtonGetMovieList;
    Button mButtonGetMovieDetail;

    private FiraTvApi firaTvApi;
    private FiraTvApi.ResponseListener tvApiListener = new FiraTvApi.ResponseListener() {
        @Override
        public void OnFiraTvApiResponse(int requestCode, int resultCode, String data) {
            if (resultCode==RESULT_OK) {
                switch (requestCode) {
                    case GET_CHANNEL_LIST:
                        break;
                    case GET_CHANNEL_HLS:
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

        mButtonGetChannelList = (Button) findViewById(R.id.get_tv_channel_list);
        mButtonGetChannelList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firaTvApi.getChannelList(tvApiListener, GET_CHANNEL_LIST, 1);
            }
        });

        mButtonGetChannelHls = (Button) findViewById(R.id.get_tv_channel_hls);
        mButtonGetChannelHls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firaTvApi.getChannelHls(tvApiListener, GET_CHANNEL_HLS, 1, 40);
            }
        });

    }

}
