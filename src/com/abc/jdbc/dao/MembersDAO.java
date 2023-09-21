package com.abc.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.abc.jdbc.util.DatabaseConnection;
import com.abc.jdbc.dto.MembersDTO;

// DAO : Data Access Object, 데이터베이스에 접근해 데이터를 조회하거나 수정하기 위해 사용
// DML(select, insert, delete, update...)와 유사한 기능

// 회원가입
public class MembersDAO {
    private final Connection connection;

    public MembersDAO() {
        connection = DatabaseConnection.getConnection();
    }

    // 회원 추가
    public void addMember(MembersDTO membersDTO) {
        String sql = "INSERT INTO MEMBERS (INPUTID, PASSWORD, NAME) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, membersDTO.getInputId());
            preparedStatement.setString(2, membersDTO.getPassword());
            preparedStatement.setString(3, membersDTO.getName());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("MembersDAO addMember Error! : " + e);
        }
    }

    // 로그인
    public void login(MembersDTO membersDTO){

    }

    // 모든 회원 조회
    public List<MembersDTO> getAllMembers() {
        List<MembersDTO> membersList = new ArrayList<>();
        String sql = "SELECT * FROM MEMBERS";
        boolean hasMembers = false; // 등록된 회원이 존재하는지 확인
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                hasMembers = true;
                MembersDTO membersDTO = new MembersDTO();
                membersDTO.setId(resultSet.getString("ID"));
                membersDTO.setInputId(resultSet.getString("INPUTID"));
                membersDTO.setPassword(resultSet.getString("PASSWORD"));
                membersDTO.setName(resultSet.getString("NAME"));
                membersList.add(membersDTO);
            }
            if (!hasMembers) {
                System.out.println("등록된 회원이 없습니다.");
            }
        } catch (Exception e) {
            System.out.println("MemberDAO getAllMembers Error! : " + e);
        }
        return membersList;
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
