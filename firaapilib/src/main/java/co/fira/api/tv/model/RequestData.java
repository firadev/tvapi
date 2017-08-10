package co.fira.api.tv.model;

import co.fira.api.tv.TvApi;

/**
 * Created by sam on 10/08/17.
 */

public class RequestData {
    public int action;
    public Arg arg;

    public RequestData() {
        arg = new Arg();
    }

    public class Arg {
        public int parentId;
        public int channelId;
        public String user;
        public Device device;
        public Location location;

        public Arg() {
            device = new Device();
            location = new Location();
        }
    }

    public class Device {
        public String manufacturer;
        public String model;
        public String serialNumber;
        public String imei1;
        public String imei2;
    }

    public class Location {
        public int latitude;
        public int longitude;
    }

}
