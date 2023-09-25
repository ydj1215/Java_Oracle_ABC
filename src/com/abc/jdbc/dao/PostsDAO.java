package com.abc.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.abc.jdbc.util.DatabaseConnection;
import com.abc.jdbc.dto.PostsDTO;

public class PostsDAO {
    private final Connection connection;

    public PostsDAO() {
        connection = DatabaseConnection.getConnection();
    }

    Scanner sc = new Scanner(System.in);

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

    // 게시글 수정
    public void modifyPost(PostsDTO postsDTO, int postID, String memberID) {
        System.out.print("[1] 제목 수정, [2] 내용 수정 : ");
        int choice = sc.nextInt();
        if (choice == 1) { // 제목 수정
            String sql = "update posts set title = ? WHERE id = ? and membersid = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                postsDTO.setId(String.valueOf(postID)); // 현재 선택된 게시글
                postsDTO.setMembersID(memberID); // 현재 로그인한 회원
                System.out.print("새로운 게시글 제목을 입력해주세요 : ");
                String newTitle = sc.next();
                sc.nextLine();
                postsDTO.setTitle(newTitle);
                preparedStatement.setString(1, postsDTO.getTitle());
                preparedStatement.setString(2, postsDTO.getId());
                preparedStatement.setString(3, postsDTO.getMembersID());
                preparedStatement.executeUpdate();
                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("게시글 제목이 성공적으로 수정되었습니다.");
                } else {
                    System.out.println("게시글 제목 수정에 실패했습니다.");
                }
            } catch (Exception e) {
                System.out.println("PostsDAO modifyTitle: " + e);
            }
        } else if (choice == 2) { // 내용 수정
            String sql = "update posts set content = ? WHERE id = ? and membersid = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                postsDTO.setId(String.valueOf(postID)); // 현재 선택된 게시글
                postsDTO.setMembersID(memberID); // 현재 로그인한 회원
                System.out.print("새로운 게시글 내용을 입력해주세요 : ");
                String newContent = sc.next();
                sc.nextLine();
                postsDTO.setContent(newContent);
                preparedStatement.setString(1, postsDTO.getContent());
                preparedStatement.setString(2, postsDTO.getId());
                preparedStatement.setString(3, postsDTO.getMembersID());
                preparedStatement.executeUpdate();
                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("게시글 내용이 성공적으로 수정되었습니다.");
                } else {
                    System.out.println("게시글 내용 수정에 실패했습니다.");
                }
            } catch (Exception e) {
                System.out.println("PostsDAO modifyTitle: " + e);
            }

        } else {
            System.out.println("올바른 번호를 입력해주세요.");
        }
    }

    // 게시글 삭제

    // 모든 게시글 조회
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
                //post.setName(String.valueOf(resultSet.getInt("NAME")));
                post.setLikesCounts(String.valueOf(resultSet.getInt("LIKESCOUNTS")));
                postsList.add(post);
            }
        } catch (Exception e) {
            System.out.println("PostsDAO getAllPosts Error! : " + e);
        }
        return postsList;
    }

    // 게시글 들어가기
    public List<PostsDTO> enterPost(int postId) {
        List<PostsDTO> postsList = new ArrayList<>();
        String sql = "SELECT * FROM POSTS WHERE ID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, postId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PostsDTO post = new PostsDTO();
                post.setId(resultSet.getString("ID"));
                post.setTitle(resultSet.getString("TITLE"));
                post.setName(resultSet.getString("NAME"));
                post.setCurrentTime(String.valueOf(resultSet.getTimestamp("CURRENTTIME")));
                post.setContent(resultSet.getString("CONTENT"));
                post.setMembersID(String.valueOf(resultSet.getInt("MEMBERSID")));
                post.setLikesCounts(String.valueOf(resultSet.getInt("LIKESCOUNTS")));
                postsList.add(post);
            }
        } catch (Exception e) {
            System.out.println("PostsDAO enterPost Error! : " + e);
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
            System.out.println("PostsDAO close Error! : " + e);
        }
    }
}
