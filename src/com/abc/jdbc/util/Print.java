package com.abc.jdbc.util;

import com.abc.jdbc.dto.CommentsDTO;
import com.abc.jdbc.dto.PostsDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Print {

    static Scanner sc = new Scanner(System.in);

    // Main
    public static void exit() {
        System.out.println("프로그램을 종료합니다.");
    }

    // DatabaseConnection
    public static void databaseConnectionError(Exception e) {
        System.out.println("DatabaseConnection Error! : " + e);
    }

    // Menu
    public static void loginMenu() { System.out.print("[1] 회원가입 [2] 로그인 [3] 종료 : "); }

    public static void boardMenu() {
        System.out.print("[1] 글쓰기, [2] 글 목록보기, [3] 로그아웃, [4] 회원 탈퇴: ");
    }

    public static void logout() { System.out.println("로그아웃 되었습니다.");}

    // Option
    public static void optionError() {
        System.out.println("올바른 숫자를 입력해주세요.");
    }


    // Members
    public static void successAddMember() {
        System.out.println("회원이 추가되었습니다!");
    }

    public static void successLogin() {
        System.out.println("로그인 성공!");
    }

    public static void failLogin() {
        System.out.println("로그인 실패 : 아이디 또는 비밀번호가 올바르지 않습니다.");
    }

    public static void hasNotAsMembers() {
        System.out.println("등록된 회원이 없습니다.");
    }

    public static String deleteMemberCheckID(){
        System.out.print("아이디를 다시 한번 입력해주세요 : ");
        String id = sc.nextLine();
        return id;
    }

    public static String deleteMemberCheckPassword(){
        System.out.print("비밀번호를 다시 한번 입력해주세요 : ");
        String password = sc.nextLine();
        return password;
    }

    public static void deleteMemberSuccess() {
        System.out.println("회원 탈퇴가 완료되었습니다.");
    }

    public static void deleteMemberNotExist() {
        System.out.println("해당 회원이 존재하지 않습니다.");
    }

    public static void membersDAOAddMemberError(Exception e) {
        System.out.println("MembersDAO addMember Error! : " + e);
    }

    public static void membersDAOLoginError(Exception e) {
        System.out.println("MembersDAO login Error! : " + e);
    }

    public static void memberDAOGetAllMembersError(Exception e) { System.out.println("MemberDAO getAllMembers Error! : " + e); }

    public static void membersDAOCloseError(Exception e) {
        System.out.println("MembersDAO close Error! : " + e);
    };
    public static void membersDAODeleteError(Exception e) {
        System.out.println("MembersDAO deleteMember Error! : " + e);
    }

    // Posts
    public static void postDAOSuccessModifyTitle() {
        System.out.println("제목이 성공적으로 수정되었습니다.");
    }

    public static void postDAOFailModifyTitle() {
        System.out.println("제목 수정에 실패했습니다.");
    }

    public static void postDAOSuccessModifyContent() {
        System.out.println("내용이 성공적으로 수정되었습니다.");
    }

    public static void postDAOFailModifyContent() {
        System.out.println("내용 수정에 실패했습니다.");
    }

    public static void postDAOAddPostError(Exception e) {
        System.out.println("PostsDAO addPost Error! : " + e);
    }

    public static void postDAOGetAllPostError(Exception e) {
        System.out.println("PostsDAO getAllPosts Error! : " + e);
    }

    public static void postDAOEnterPostError(Exception e) {
        System.out.println("PostsDAO enterPost Error! : " + e);
    }

    public static void postDAOModifyTitleError(Exception e) {
        System.out.println("PostsDAO modifyTitle: " + e);
    }

    public static void postDAOModifyContentsError(Exception e) {
        System.out.println("PostsDAO modifyContents: " + e);
    }

    public static void postDAOCloseError(Exception e) {
        System.out.println("PostsDAO close Error! : " + e);
    }

    // Comments
    public static void commentDAOAddCommentSuccess() {
        System.out.println("댓글을 작성했습니다.");
        System.out.println();
    }

    public static void commentDAOGetCommentsByPostIdCommentsListIsEmpty(String s) {
        System.out.println(s + "번 게시글에 댓글이 없습니다.");
    }

    public static void commentDAOGetCommentsByPostIdCommentsListIsNotEmpty(String s) {
        System.out.println("<" + s + "번 게시글의 댓글 목록>");
    }

    public static void commentDAOGetCommentsByPostIdCommentElement(CommentsDTO comment) {
        System.out.println("댓글 번호 : " + comment.getId()); // 댓글의 고유 식별자 출력
        System.out.println("댓글 작성자 : " + comment.getName());
        System.out.println("댓글 내용: " + comment.getCommentsText());
        System.out.println("댓글 시간: " + comment.getCommentsTime());
        System.out.println("-------------");
    }

    public static void commentDAOCommentModifySelectNumber() {
        System.out.print("수정할 댓글 번호를 선택하세요: ");
    }

    public static void commentDAOCommentModifyContent() {
        System.out.print("수정할 내용을 입력하세요: ");
    }

    public static void commentDAOCommentModifyContentSuccess() {
        System.out.println("댓글이 성공적으로 수정되었습니다.");
    }

    public static void commentDAOCommentModifyContentFail() {
        System.out.println("댓글 수정에 실패했습니다.");
    }

    public static void commentDAOAddCommentError(Exception e) {
        System.out.println("CommentsDAO addComment Error! : " + e);
    }

    public static void commentDAOGetCommentsByPostIdSqlError(SQLException e) { System.out.println("SQL 오류 발생: " + e.getMessage()); }

    public static void commentDAOGetCommentsByPostIdError(Exception e) {
        System.out.println("오류 발생: " + e.getMessage());
    }

    public static void commentDAOCommentModifyError(Exception e) {
        System.out.println("CommentDAO commentModify: " + e);
    }

    public static void commentDAOCloseError(Exception e) {
        System.out.println("CommentsDAO close Error! : " + e);
    }


    // Likes
    public static void likesDAOPressLike() {
        System.out.println("좋아요를 눌렀습니다.");
    }

    public static void likesDAOAlreadyPressLike() {
        System.out.println("이미 좋아요를 누르셨습니다!");
    }

    public static void likesDAOAddLikeError(Exception e) {
        System.out.println("LikesDAO addLike Error! : " + e);
    }





    // ================================================================================
    // path : com/abc/jdbc/util/user/IdPassword.java , com/abc/jdbc/util/user/IdPwName.java
    public static void inputId() {
        System.out.print("아이디 입력 : ");
    }

    public static void inputPw() {
        System.out.print("비밀번호 입력 : ");
    }

    // ================================================================================
    // path : com/abc/jdbc/util/user/IdPwName.java
    public static void inputNm() {
        System.out.print("이름 입력 : ");
    }

    // ================================================================================
    // path : com/abc/jdbc/util/user/createPost.java
    public static void inputTitle() { System.out.print("글 제목 입력: "); }
    public static void inputContent() { System.out.print("글 내용 입력: "); }
    public static void successCreatePost() { System.out.println("글이 작성되었습니다."); }

    // ================================================================================
    // path : com/abc/jdbc/util/user/PostList.java
    public static void allPostList(List<PostsDTO> postsList) {
        System.out.println("<모든 글 목록>");
        for (PostsDTO post : postsList) { // 향상된 for 문
            System.out.println("글 번호 : " + post.getId());
            System.out.println("글 제목 : " + post.getTitle());
            System.out.println("-".repeat(20));
        }
    }

    // ================================================================================
    // path : com/abc/jdbc/util/user/EnterPost.java
    public static void whichEnterPost() { System.out.print("몇번 글에 들어갈까요? : "); }
    public static void enteredPost(int post) {
        System.out.println(post + "번 글에 들어 왔습니다.");
        System.out.println(".A__A    ✨\uD83C\uDF82✨    A__A\n" +
                "( •⩊•)   _______   (•⩊• )\n" +
                "(>\uD83C\uDF70>)   |           |   (<\uD83D\uDD2A<)\n");
    }
    public static void emptyPost(int post) { System.out.println(post + "번 게시글은 존재하지 않습니다."); }
    public static void postNumber(int post) { System.out.println("<" + post + "번 글>"); }
    public static void viewPostDTO(PostsDTO e) {
        System.out.println("글 번호 : " + e.getId());
        System.out.println("글 제목 : " + e.getTitle());
        System.out.println("글 작성 시간 : " + e.getCurrentTime());
        System.out.println("글 내용 : " + e.getContent());
        System.out.println("작성자 : " + e.getMembersID());
        System.out.println("추천수 : " + e.getLikesCounts());
        System.out.println("-".repeat(20));
        System.out.println();
    }
}








