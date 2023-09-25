package com.abc.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.abc.jdbc.util.DatabaseConnection;
import com.abc.jdbc.dto.CommentsDTO;
import com.abc.jdbc.util.PrintMenu;


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
    public List<CommentsDTO> getCommentsByPostId(CommentsDTO commentsDTO) {
        List<CommentsDTO> commentsList = new ArrayList<>();
        String sql = "SELECT ID, NAME, COMMENTSTEXT, COMMENTSTIME FROM COMMENTS WHERE POSTSID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, commentsDTO.getPostsId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CommentsDTO comment = new CommentsDTO();
                // 데이터베이스에서 생성된 고유 식별자(ID)를 가져와서 설정
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
                PrintMenu.commentLogo();
                for (CommentsDTO comment : commentsList) {
                    System.out.println("댓글 번호 : " + comment.getId()); // 댓글의 고유 식별자 출력
                    System.out.println("댓글 작성자 : " + comment.getName());
                    System.out.println("댓글 내용: " + comment.getCommentsText());
                    System.out.println("댓글 시간: " + comment.getCommentsTime());
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL 오류 발생: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("오류 발생: " + e.getMessage());
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

    public void commentDelete(CommentsDTO commentsDTO){
        String sql = "DELETE FROM COMMENTS WHERE ID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            System.out.print("삭제할 댓글 번호를 입력해주세요 : ");
            int id =  sc.nextInt();
            commentsDTO.setId(String.valueOf(id));
            preparedStatement.setString(1, commentsDTO.getId());
            int rowsAffected = preparedStatement.executeUpdate(); // 실행된 행의 수를 반환
            if (rowsAffected == 0) {
                // 삭제된 행이 없는 경우
                System.out.println("해당 번호의 댓글이 존재하지 않습니다.");
            } else {
                // 삭제 작업이 성공적으로 수행된 경우
                System.out.println("댓글이 성공적으로 삭제되었습니다.");
            }
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("CommentDAO commentDelete : " + e);
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
