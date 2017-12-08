package struct;
import java.io.Serializable;

public class Controller implements Serializable {
    public String getCtrlUser() {
        return ctrlUser;
    }

    public void setCtrlUser(String ctrlUser) {
        this.ctrlUser = ctrlUser;
    }

    public String getCtrlPwd() {
        return ctrlPwd;
    }

    public void setCtrlPwd(String ctrlPwd) {
        this.ctrlPwd = ctrlPwd;
    }

    private String ctrlUser;
    private String ctrlPwd;
}
