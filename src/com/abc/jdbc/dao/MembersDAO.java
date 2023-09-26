package com.abc.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.abc.jdbc.util.DatabaseConnection;
import com.abc.jdbc.dto.MembersDTO;
import com.abc.jdbc.util.Print;

// DAO : Data Access Object, 데이터베이스에 접근해 데이터를 조회하거나 수정하기 위해 사용
// DML(select, insert, delete, update...)와 유사한 기능

// 회원가입
public class MembersDAO {
    // 연결
    private final Connection connection;
    public MembersDAO() {
        connection = DatabaseConnection.getConnection();
    }
    Scanner sc = new Scanner(System.in);

    //로그인
    public MembersDTO login(MembersDTO membersDTO) {
        MembersDTO loggedInMember = null;
        String sql = "SELECT * FROM MEMBERS WHERE INPUTID = ? AND PASSWORD = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, membersDTO.getInputId());
            preparedStatement.setString(2, membersDTO.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();

            // ResultSet 객체에서 다음 행이 존재하는지 확인
            if (resultSet.next()) {
                // 만약 다음 행이 존재한다면, 로그인에 성공한 회원 정보를 가져온다.
                // 로그인한 회원 객체
                loggedInMember = new MembersDTO();

                // ResultSet으로부터 회원 정보를 가져와서 로그인한 회원 객체에 설정
                // 'ID' 필드에서 값을 읽어와 loggedInMember의 'id' 필드에 설정
                loggedInMember.setId(resultSet.getString("ID"));
                loggedInMember.setInputId(resultSet.getString("INPUTID"));
                loggedInMember.setPassword(resultSet.getString("PASSWORD"));
                loggedInMember.setName(resultSet.getString("NAME"));
                System.out.println("로그인에 성공하셨습니다.");
            } else {
                // 만약 ResultSet에서 다음 행이 없다면,
                // 로그인에 실패하고 loggedInMember는 여전히 null이다.
                System.out.println("로그인에 실패하셨습니다.");
            }
        } catch (Exception e) {
            System.out.println("MembersDAO login Error! : " + e);
        }
        return loggedInMember;
    }

    // 회원 가입
    public void addMember(MembersDTO membersDTO) {
        System.out.print("아이디 입력: ");
        String inputId = sc.nextLine();
        System.out.print("비밀번호 입력: ");
        String password = sc.nextLine();
        System.out.print("이름 입력: ");
        String name = sc.nextLine();
        membersDTO.setInputId(inputId);
        membersDTO.setPassword(password);
        membersDTO.setName(name);
        String sql = "INSERT INTO MEMBERS (INPUTID, PASSWORD, NAME) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, membersDTO.getInputId());
            preparedStatement.setString(2, membersDTO.getPassword());
            preparedStatement.setString(3, membersDTO.getName());
            preparedStatement.executeUpdate();
            System.out.println("회원가입이 완료되었습니다.");
        } catch (Exception e) {
            System.out.println("MembersDAO addMember Error! : " + e);
        }
    }

    //회원 탈퇴
    public void deleteMember(MembersDTO membersDTO) {
        String id = Print.deleteMemberCheckID();
        String password = Print.deleteMemberCheckPassword();
        membersDTO.setInputId(id);
        membersDTO.setPassword(password);
        String sql = "DELETE FROM MEMBERS WHERE INPUTID = ? and PASSWORD = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, membersDTO.getInputId());
            preparedStatement.setString(2, membersDTO.getPassword());
            preparedStatement.executeUpdate();
            System.out.println("회원탈퇴 완료");
        } catch (Exception e) {
            Print.membersDAODeleteError(e);
        }
    }

    // 연결 해제
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            System.out.println("MembersDAO close Error! : " + e);
        }
    }
}