package struct;
import java.io.Serializable;

public class AgeGroup implements Serializable {
    private int ageMin;
    private int ageMax;

    public int getAgeMin() {
        return ageMin;
    }

    public AgeGroup(int ageMin, int ageMax) {
        this.ageMin = ageMin;
        this.ageMax = ageMax;
    }

    public void setAgeMin(int ageMin) {
        this.ageMin = ageMin;
    }

    public int getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(int ageMax) {
        this.ageMax = ageMax;
    }

}
