package struct;
import java.io.Serializable;

public class Referee implements Serializable {
    private String idNumber;
    private String phone;
    private String name;
    private String teamName;
    private String refereeUser;
    private String refereePwd;

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getRefereeUser() {
        return refereeUser;
    }

    public void setRefereeUser(String refereeUser) {
        this.refereeUser = refereeUser;
    }

    public String getRefereePwd() {
        return refereePwd;
    }

    public void setRefereePwd(String refereePwd) {
        this.refereePwd = refereePwd;
    }
}
