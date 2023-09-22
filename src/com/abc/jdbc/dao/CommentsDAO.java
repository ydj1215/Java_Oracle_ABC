package com.abc.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.abc.jdbc.dto.PostsDTO;
import com.abc.jdbc.util.DatabaseConnection;
import com.abc.jdbc.dto.CommentsDTO;


public class CommentsDAO {
    // 연결
    private final Connection connection;
    Scanner sc = new Scanner(System.in);

    public CommentsDAO() {
        connection = DatabaseConnection.getConnection();
    }

    // 댓글 달기
    public void addComment(CommentsDTO commentsDTO) {
        String sql = "INSERT INTO COMMENTS (POSTSID, MEMBERSID, COMMENTSTEXT) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, commentsDTO.getPostsId());
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
        List<CommentsDTO> commentsList = new ArrayList<>();
        String sql = "SELECT NAME, COMMENTSTEXT, COMMENTSTIME FROM COMMENTS WHERE ID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, commentsDTO.getPostsId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CommentsDTO comment = new CommentsDTO(); // 각 줄 마다 새로운 댓글 객체를 생성
                comment.setId(resultSet.getString("ID"));
                comment.setName(resultSet.getString("NAME"));
                comment.setCommentsText(resultSet.getString("COMMENTSTEXT"));
                comment.setCommentsTime(String.valueOf(resultSet.getTimestamp("COMMENTSTIME")));
                commentsList.add(comment);
            }

            if (commentsList.isEmpty()) {
                System.out.println(commentsDTO.getPostsId() + "번 게시글에 댓글이 없습니다.");
            } else {
                System.out.println("<" + commentsDTO.getPostsId() + "번 게시글의 댓글 목록>");
                for (CommentsDTO comment : commentsList) {
                    System.out.println("댓글 번호 : " + comment.getId());
                    System.out.println("댓글 작성자 : " + comment.getName());
                    System.out.println("댓글 내용: " + comment.getCommentsText());
                    System.out.println("댓글 시간: " + comment.getCommentsTime());
                    System.out.println("-------------");
                }
            }
        } catch (Exception e) {
            System.out.println("CommentsDAO printCommentsByPostId Error! : " + e);
        }
        return commentsList;
    }

    // 댓글 수정
    public void commentModify() {
        String sql = "UPDATE COMMENTS SET COMMENTSTEXT = ? WHERE ID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            System.out.print("수정할 댓글 번호를 선택하세요: ");
            int modCommentId = sc.nextInt();
            sc.nextLine();
            System.out.print("수정할 내용을 입력하세요: ");
            String modComment = sc.nextLine();
            preparedStatement.setString(1, modComment);
            preparedStatement.setInt(2, modCommentId);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("댓글이 성공적으로 수정되었습니다.");
            } else {
                System.out.println("댓글 수정에 실패했습니다.");
            }
        } catch (Exception e) {
            System.out.println("CommentDAO commentModify: " + e);
        }
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
