package co.fira.api.tv.model;

/**
 * Created by sam on 10/08/17.
 */

public class CardGradient {
    private int      start;
    private int      middle;
    private int      end;
    private String   direction;

    public int getStartColor() {
        return start;
    }

    public void setStartColor(int color) {
        this.start = color;
    }

    public int getMidColor() {
        return middle;
    }

    public void setMidColor(int color) {
        this.middle = color;
    }

    public int getEndColor() {
        return end;
    }

    public void setEndColor(int color) {
        this.end = color;
    }

    public void setDirection(String direction) {
        this.direction =  direction;
    }

    public String getDirection() {
        return this.direction;
    }

}
