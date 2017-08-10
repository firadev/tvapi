package co.fira.api.tv.model;

import java.util.Date;

/**
 * Created by sam on 10/08/17.
 */

public class SuperSoccerMatchChannel extends TvChannel {
    public Date                 date;       // match date
    public String               time_zone;  // match time zone
    public SuperSoccerMatchTeam home;       // home team
    public SuperSoccerMatchTeam away;       // away team
}
