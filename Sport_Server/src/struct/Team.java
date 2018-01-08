package struct;
import java.io.Serializable;

public class Team implements Serializable {
    private String teamName;
    private String teamUser;
    private String teamPwd;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamUser() {
        return teamUser;
    }

    public void setTeamUser(String teamUser) {
        this.teamUser = teamUser;
    }

    public String getTeamPwd() {
        return teamPwd;
    }

    public void setTeamPwd(String teamPwd) {
        this.teamPwd = teamPwd;
    }
}
