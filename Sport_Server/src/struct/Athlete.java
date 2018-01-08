package struct;
import java.io.Serializable;

public class Athlete implements Serializable {
    private int athleteNo;
    private byte sex;
    private String idNumber;
    private String name;
    private AgeGroup ageGroup;
    private float academicRecord;
    private String teamName;

    public int getAthleteNo() {
        return athleteNo;
    }

    public void setAthleteNo(int athleteNo) {
        this.athleteNo = athleteNo;
    }

    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AgeGroup getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
    }

    public float getAcademicRecord() {
        return academicRecord;
    }

    public void setAcademicRecord(float academicRecord) {
        this.academicRecord = academicRecord;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
