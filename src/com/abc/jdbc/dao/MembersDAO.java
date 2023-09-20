package com.abc.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.abc.jdbc.util.DatabaseConnection;

// DAO : Data Access Object, 데이터베이스에 접근해 데이터를 조회하거나 수정하기 위해 사용
// DML(select, insert,delete, udate...)와 유사한 기능

// 회원가입
public class MembersDAO {
    private final Connection connection;

    public MembersDAO() {
        connection = DatabaseConnection.getConnection();
    }

    // 회원 추가
    public void addMember(String name, String password) {
        String sql = "INSERT INTO MEMBERS (NAME, PASSWORD) VALUES (?, ?)";
        // try-with-resources 사용: PreparedStatement를 try-with-resources 문으로 감싸서 자동으로 자원을 닫는 것이 가능
        // 이렇게 하면 finally 블록을 명시적으로 작성하지 않아도 무방
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // PreparedStatement : Statement 클래스보다 기능이 향상된 클래스
            // 코드의 안정성과 가독성이 높다.
            // 인자 값으로 전달이 가능하다.
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("MembersDAO addMember Error! : " + e);
        }
    }

    // 모든 회원 조회
    public void getAllMembers() {
        String sql = "SELECT * FROM MEMBERS";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("NAME");
                String password = resultSet.getString("PASSWORD");
                System.out.println("Name : " + name + ", Password : " + password);
            }
        } catch (Exception e) {
            System.out.println("MemberDAO getAllMembers Error! : " + e);
            System.out.println();
        }
    }

    // 연결 해제
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e){
            System.out.println("MembersDAO close Error! : " + e);
        }
    }
}
