package com.abc.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.abc.jdbc.util.DatabaseConnection;
import com.abc.jdbc.dto.PostsDTO;

public class PostsDAO {
    private final Connection connection;
    public PostsDAO() {
        connection = DatabaseConnection.getConnection();
    }

    // 게시글 작성
    public void addPost(PostsDTO postsDTO) {
        String sql = "INSERT INTO Posts (TITLE, CONTENT, MEMBERSID) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, postsDTO.getTitle());
            preparedStatement.setString(2, postsDTO.getContent());
            preparedStatement.setString(3, postsDTO.getMembersID());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("PostsDAO addPost Error! : " + e);
        }
    }

    // 모든 글 조회
    public List<PostsDTO> getAllPosts() {
        List<PostsDTO> postsList = new ArrayList<>();
        String sql = "SELECT * FROM POSTS";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PostsDTO post = new PostsDTO();
                post.setId(resultSet.getString("ID"));
                post.setTitle(resultSet.getString("TITLE"));
                post.setCurrentTime(String.valueOf(resultSet.getTimestamp("CURRENTTIME")));
                post.setContent(resultSet.getString("CONTENT"));
                post.setMembersID(String.valueOf(resultSet.getInt("MEMBERSID")));
                post.setLikesCounts(String.valueOf(resultSet.getInt("LIKESCOUNTS")));
                postsList.add(post);
            }
        } catch (Exception e) {
            System.out.println("PostsDAO getAllPosts Error! : " + e);
        }
        return postsList;
    }

    // 연결 해제
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            System.out.println("PostDAO close Error! : " + e);
        }
    }
}
