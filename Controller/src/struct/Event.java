package struct;
import java.io.Serializable;

public class Event implements Serializable {
    private int eventNo;
    private byte sex;
    private  String eventName;

    public int getEventNo() {
        return eventNo;
    }

    public void setEventNo(int eventNo) {
        this.eventNo = eventNo;
    }

    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
