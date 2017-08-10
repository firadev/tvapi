package co.fira.api.tv.model;

/**
 * Created by sam on 10/08/17.
 */

public class TvChannel {
    public int              id;          // channel id
    public String           name;        // channel name
    public CardGradient     gradient;    // gradient for card
    public String           logo;        // logo url
    public String           description; // channel description
    public int              parent_id;   // parent id
    public String           current;     // program
    public int              vendor;      // channel vendor id
    public String           hls;         // channel hls
}
