package com.abc.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.abc.jdbc.util.DatabaseConnection;
import com.abc.jdbc.dto.LikesDTO;

public class LikesDAO {
    // 연결
    private final Connection connection;

    public LikesDAO() {
        connection = DatabaseConnection.getConnection();
    }

    // 좋아요 추가
    public void addLike(LikesDTO likesDTO) {
        String sql = "INSERT INTO LIKES (POSTID, MEMBERSID) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            //preparedStatement.setInt(1, re likesDTO.getPostId());
            //preparedStatement.setInt(2, membersId);
            preparedStatement.executeUpdate();
            System.out.println("좋아요를 눌렀습니다.");
        } catch (Exception e) {
            System.out.println("LikesDAO addLike Error! : " + e);
        }
    }
}
