package struct;
import java.io.Serializable;

public class OrderInfo implements Serializable {
    private int eventNo;
    private AgeGroup ageGroup;
    private byte preOrFinal;
    private int groupNo;
    private int athleteNo;
    private int order;

    public int getEventNo() {
        return eventNo;
    }

    public void setEventNo(int eventNo) {
        this.eventNo = eventNo;
    }

    public AgeGroup getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
    }

    public byte getPreOrFinal() {
        return preOrFinal;
    }

    public void setPreOrFinal(byte preOrFinal) {
        this.preOrFinal = preOrFinal;
    }

    public int getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(int groupNo) {
        this.groupNo = groupNo;
    }

    public int getAthleteNo() {
        return athleteNo;
    }

    public void setAthleteNo(int athleteNo) {
        this.athleteNo = athleteNo;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
