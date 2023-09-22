package com.abc.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.abc.jdbc.dto.MembersDTO;
import com.abc.jdbc.util.DatabaseConnection;
import com.abc.jdbc.dto.LikesDTO;
import com.abc.jdbc.dao.MembersDAO;

public class LikesDAO {
    // 연결
    private final Connection connection;
    private final MembersDAO LoginMembersDAO;

    public LikesDAO(MembersDAO membersDAO) {
        connection = DatabaseConnection.getConnection();
        this.LoginMembersDAO = membersDAO;
    }

    // 좋아요 추가
    public void addLike(LikesDTO likesDTO) {
        String sql = "INSERT INTO LIKES (POSTID, MEMBERSID) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, likesDTO.getPostId());
            preparedStatement.setString(2, likesDTO.getId()); // 로그인한 회원의 ID 사용
            preparedStatement.executeUpdate();
            System.out.println("좋아요를 눌렀습니다.");
        } catch (Exception e) {
            System.out.println("LikesDAO addLike Error! : " + e);
        }
    }

}
