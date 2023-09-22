package com.abc.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.abc.jdbc.dto.PostsDTO;
import com.abc.jdbc.util.DatabaseConnection;
import com.abc.jdbc.dto.CommentsDTO;


public class CommentsDAO {
    // 연결
    private final Connection connection;

    public CommentsDAO() {
        connection = DatabaseConnection.getConnection();
    }

    // 댓글 달기
    public void addComment(CommentsDTO commentsDTO) {
        String sql = "INSERT INTO COMMENTS (POSTSID, MEMBERSID, COMMENTSTEXT) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, commentsDTO.getPostId());
            preparedStatement.setString(2, commentsDTO.getMembersId());
            preparedStatement.setString(3, commentsDTO.getCommentsText());
            preparedStatement.executeUpdate();
            System.out.println("댓글을 작성했습니다.");
            System.out.println();
        } catch (Exception e) {
            System.out.println("CommentsDAO addComment Error! : " + e);
        }
    }

    // 댓글 보기
    public List<CommentsDTO> printCommentsByPostId(CommentsDTO commentsDTO) {
        List<CommentsDTO> comments = new ArrayList<>();
        String sql = "SELECT COMMENTSIN, COMMENTSTEXT, COMMENTSTIME FROM COMMENTS WHERE = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, commentsDTO.getPostId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                CommentsDTO comment = new CommentsDTO(); // 각 줄 마다 새로운 댓글 객체를 생성
                comment.setId(resultSet.getString("ID"));
                comment.setCommentsText(resultSet.getString("COMMENTSTEXT"));
                comment.setCommentsTime(String.valueOf(resultSet.getTimestamp("COMMENTSTIME")));
                comments.add(comment);
            }

            if (comments.isEmpty()) {
                System.out.println("해당 게시글에 댓글이 없습니다.");
            } else {
                System.out.println("게시글 #" + commentsDTO.getPostId() + "의 댓글 목록:");
                for (CommentsDTO comment : comments) {
                    System.out.println("댓글 ID: " + comment.getId());
                    System.out.println("댓글 내용: " + comment.getCommentsText());
                    System.out.println("댓글 시간: " + comment.getCommentsTime());
                    System.out.println("-------------");
                }
            }
        } catch (Exception e) {
            System.out.println("CommentsDAO printCommentsByPostId Error! : " + e);
        }
        return comments;
    }


    // 연결 해제
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            System.out.println("CommentsDAO close Error! : " + e);
        }
    }
}
