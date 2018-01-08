package struct;
import java.io.Serializable;

import java.util.Date;

public class Match implements Serializable {
    private int eventNo;
    private String matchType;
    private Date matchTime;
    private char preOrFinal;
    private int groupNo;

    public int getEventNo() {
        return eventNo;
    }

    public void setEventNo(int eventNo) {
        this.eventNo = eventNo;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public Date getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(Date matchTime) {
        this.matchTime = matchTime;
    }

    public char getPreOrFinal() {
        return preOrFinal;
    }

    public void setPreOrFinal(char preOrFinal) {
        this.preOrFinal = preOrFinal;
    }

    public int getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(int groupNo) {
        this.groupNo = groupNo;
    }
}
