package sql;

import struct.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class Dao {

    private Connection con;
    private PreparedStatement prepstmt;

    // 默认构造函数，连接到默认数据库
    public Dao() {
        String url = "jdbc:mysql://xiaobye.com:3306/sport";
        String usr = "root";
        String pwd = "root";

        connect(url, usr, pwd);
    }

    // 带参构造函数，连接到指定数据库
    public Dao(String url, String usr, String pwd) {
        connect(url, usr, pwd);
    }

    // 连接到数据库
    private void connect(String url, String usr, String pwd) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, usr, pwd);
        } catch (SQLException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
     * 登录账号，根据option的值登录对应账号
     * 密码正确返回true，否则返回false
     */
    public boolean login(String option, String user, String pwd) {
        String sql;

        switch (option) {
            case "admin":
                sql = "select * from Admin where adminUser=? and adminPwd=?;";
                break;
            case "referee":
                sql = "select * from Referee where refereeUser=? and refereePwd=?;";
                break;
            case "controller":
                sql = "select * from Controller where ctrlUser=? and ctrlPwd=?;";
                break;
            default:
                return false;
        }

        try {
            prepstmt = con.prepareStatement(sql);
            prepstmt.setString(1, user);
            prepstmt.setString(2, pwd);

            ResultSet rs = prepstmt.executeQuery();
            if(!rs.next())      // 查询结果为空则说明账号或密码错误
                return false;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;
    }

    // 获取所有运动员
    public Athlete[] getAllAthlete() {
        ResultSet rs;
        Athlete[] athletes = null;
        String sql = "select * from Athlete;";
        int count;

        try {
            prepstmt = con.prepareStatement(sql);
            rs = prepstmt.executeQuery();
            rs.last();
            count = rs.getRow();
            rs.beforeFirst();

            athletes = new Athlete[count];
            count = 0;
            while(rs.next()) {
                athletes[count].setAthleteNo(rs.getInt("athleteNo"));
                athletes[count].setAcademicRecord(rs.getFloat("academicRecord"));
                int ageMin = rs.getInt("ageMin");
                int ageMax = rs.getInt("ageMax");
                athletes[count].setAgeGroup(new AgeGroup(ageMin, ageMax));
                athletes[count].setName(rs.getString("name"));
                athletes[count].setIdNumber(rs.getString("idNumber"));
                athletes[count].setSex(rs.getByte("sex"));
                athletes[count].setTeamName(rs.getString("teamName"));
                count++;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return athletes;
    }


    // 设置运动员编号
    public void setAthleteNo(String idNumber, int athleteNo)
    {
        String sql = "update Athlete set athleteNo=? where idNumber=?;";

        try {
            prepstmt = con.prepareStatement(sql);
            prepstmt.setInt(1, athleteNo);
            prepstmt.setString(2, idNumber);

            prepstmt.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    // 设置运动员出场顺序
    public void setAthleteOrder(int athleteNo, int eventNo, int groupNo, int order)
    {
        String sql = "update OrderInfo set eventNo=?, groupNo=?, order=? where idNumber=?;";

        try {
            prepstmt = con.prepareStatement(sql);
            prepstmt.setInt(1, eventNo);
            prepstmt.setInt(2, groupNo);
            prepstmt.setInt(3, order);
            prepstmt.setInt(4, athleteNo);

            prepstmt.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 通过项目编号和年龄组获取出场信息
    public Vector<OrderInfo> getOrderInfo(int eventNo, AgeGroup ageGroup)
    {
        ResultSet rs;
        Vector<OrderInfo> athleteList = new Vector<>();
        String sql = "select * from OrderInfo where eventNo=? and ageMax=? and ageMin=?;";
        int count = 0;

        try {
            prepstmt = con.prepareStatement(sql);
            prepstmt.setInt(1, eventNo);
            prepstmt.setInt(2, ageGroup.getAgeMax());
            prepstmt.setInt(3, ageGroup.getAgeMin());
            rs = prepstmt.executeQuery();
            /*rs.last();
            count = rs.getRow();
            rs.beforeFirst();

            athletes = new OrderInfo[count];*/
            while(rs.next()) {
                OrderInfo athlete = new OrderInfo();
                athlete.setAthleteNo(rs.getInt("athleteNo"));
                athlete.setEventNo(rs.getInt("eventNo"));
                int ageMin = rs.getInt("ageMin");
                int ageMax = rs.getInt("ageMax");
                athlete.setAgeGroup(new AgeGroup(ageMin, ageMax));
                athlete.setOrder(rs.getInt("orderNo"));
                athlete.setPreOrFinal(rs.getString("preOrFinal"));

                athleteList.add(athlete);
                count++;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("OrderInfo Wrong");
        }

        System.out.println("OrderInfo length is "+count);

        return athleteList;
    }

    // 获取项目列表
    public Vector<Event> getEvent()
    {
        ResultSet rs;
        String sql = "select * from Event;";
        int count;
        Vector<Event> eventList = new Vector<>();

        try {
            prepstmt = con.prepareStatement(sql);
            rs = prepstmt.executeQuery();
            /*rs.last();
            count = rs.getRow();
            rs.beforeFirst();*/

            //event = new Event[count];
            count = 0;
            while(rs.next()) {
                Event event = new Event();
                event.setEventName(rs.getString("eventName"));
                event.setEventNo(rs.getInt("eventNo"));
                event.setSex(rs.getString("sex"));

                eventList.add(event);
                count++;
            }
            //System.out.println(count);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Here Wrong");
        }

        return eventList;
    }

    // 保存成绩
    public void saveScore(ScoreResult rs)
    {
        String sql = "insert into ScoreResult(athleteNo,eventNo,scoreType,score,refereeUser)" +
                "VALUES(?,?,?,?,?);";

        try {
            prepstmt = con.prepareStatement(sql);
            prepstmt.setInt(1, rs.getAthleteNo());
            prepstmt.setInt(2, rs.getEventNo());
            prepstmt.setString(3, rs.getScoreType());
            prepstmt.setFloat(4, rs.getScore());
            prepstmt.setString(5, rs.getRefereeUser());

            prepstmt.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 通过运动员编号获取成绩
    public ScoreResult[] getScoreByAthleteNo(int athleteNo)
    {
        ResultSet rs;
        ScoreResult[] scores = null;
        String sql = "select * from ScoreResult where athleteNo=?;";
        int count;

        try {
            prepstmt = con.prepareStatement(sql);
            prepstmt.setInt(1, athleteNo);
            rs = prepstmt.executeQuery();
            rs.last();
            count = rs.getRow();
            rs.beforeFirst();

            scores = new ScoreResult[count];
            count = 0;
            while(rs.next()) {
                scores[count].setAthleteNo(rs.getInt("athleteNo"));
                scores[count].setEventNo(rs.getInt("eventNo"));
                scores[count].setScore(rs.getFloat("score"));
                scores[count].setRefereeUser(rs.getString("refereeUser"));
                scores[count].setScoreType(rs.getString("scoreType"));
                count++;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return scores;
    }

    // 通过队名获取运动员编号列表
    public int[] getAthletesByTeamName(String teamName)
    {
        ResultSet rs;
        int[] athleteNo = null;
        String sql = "select * from athlete where teamName=?;";
        int count;

        try {
            prepstmt = con.prepareStatement(sql);
            prepstmt.setString(1, teamName);
            rs = prepstmt.executeQuery();
            rs.last();
            count = rs.getRow();
            rs.beforeFirst();

            athleteNo = new int[count];
            count = 0;
            while(rs.next()) {
                athleteNo[count]=rs.getInt("athleteNo");
                count++;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return athleteNo;
    }

}