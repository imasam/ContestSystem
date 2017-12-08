package struct;

import java.io.Serializable;

public enum TcpType implements Serializable{
    ScoreInstr(1),			// 控制人员发出打分指令
    Score (2),				// 裁判的打分分数
    ScoreResult(3),			// 总裁判返回的打分结果
    GetFreeReferee(4),		// 控制人员获取空闲裁判列表
    FreeList(5), 			// 服务器返回的空闲裁判列表
    SetFree(6), 			// 裁判一轮比赛结束后将自己设为空闲
    Login(7),				// 登录，包括裁判、控制人员、总裁判的登录
    LoginResult(8),			// 服务器返回登录结果，账号密码是否正确
    GetEvent(9),			// 获取项目列表
    EventList(10),			// 服务器返回的项目列表
    OrderInfo(11),			// 服务器根据项目编号返回的出场顺序列表
    GetAthleteScore(12),	// 根据运动员编号获取其所有项目成绩
    AthleteScore(13),		// 服务器返回的运动员成绩
    GetTeamScore(14),		// 根据代表队账号获取其团体成绩
    TeamScore(15),			// 服务器返回的团体成绩
    GetOrderInfo(16);       // 根据项目编号和年龄组获取项目列表

    private int index;

    TcpType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}

