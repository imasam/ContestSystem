package struct;
import java.io.Serializable;

public class ScoreResult implements Serializable {
    private int athleteNo;
    private int eventNo;
    private String refereeUser;
    private String scoreType;
    private float score;

    public int getAthleteNo() {
        return athleteNo;
    }

    public void setAthleteNo(int athleteNo) {
        this.athleteNo = athleteNo;
    }

    public int getEventNo() {
        return eventNo;
    }

    public void setEventNo(int eventNo) {
        this.eventNo = eventNo;
    }

    public String getRefereeUser() {
        return refereeUser;
    }

    public void setRefereeUser(String refereeUser) {
        this.refereeUser = refereeUser;
    }

    public String getScoreType() {
        return scoreType;
    }

    public void setScoreType(String scoreType) {
        this.scoreType = scoreType;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
