package com.abc.jdbc.main;

import java.util.List;
import java.util.Scanner;

import com.abc.jdbc.dao.LikesDAO;
import com.abc.jdbc.dao.MembersDAO;
import com.abc.jdbc.dao.PostsDAO;
import com.abc.jdbc.dto.LikesDTO;
import com.abc.jdbc.dto.MembersDTO;
import com.abc.jdbc.dto.PostsDTO;
import com.abc.jdbc.dao.CommentsDAO;
import com.abc.jdbc.dto.CommentsDTO;
import com.abc.jdbc.util.PrintMenu;

public class MainApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MembersDAO membersDAO = new MembersDAO();
        MembersDTO loginMember = null; // 현재 로그인한 사용자
        PostsDTO postsDTO = new PostsDTO();
        PostsDAO postsDAO = new PostsDAO();
        LikesDAO likesDAO = new LikesDAO(membersDAO);
        CommentsDAO commentsDAO = new CommentsDAO();

        PrintMenu.ABCLogo();
        PrintMenu.notLoginMenu();
        while (true) {
            if (loginMember == null) { // 로그인하지 않은 경우
            } else { // 로그인한 경우
                PrintMenu.loginMenu();
            }

            System.out.print("메뉴를 선택하세요 : ");
            int choice = sc.nextInt();
            System.out.println();
            if (loginMember == null) { // 로그인하지 않은 경우
                switch (choice) {
                    case 1: // 로그인
                        // 입력
                        MembersDTO inputMember = new MembersDTO();
                        System.out.print("아이디 입력: ");
                        String inputId = sc.next();
                        System.out.print("비밀번호 입력: ");
                        String password = sc.next();
                        // 새로운 회원 객체, 입력된 아이디와 비밀번호를 새롭게 생성된 회원 객체에 설정
                        inputMember.setInputId(inputId);
                        inputMember.setPassword(password);
                        loginMember = membersDAO.login(inputMember);
                        if (loginMember != null) {
                            System.out.println("로그인 성공!");
                        } else {
                            System.out.println("로그인 실패: 아이디 또는 비밀번호가 올바르지 않습니다.");
                        }
                        break;

                    case 2: // 회원 가입
                        System.out.print("아이디 입력: ");
                        inputId = sc.next();
                        System.out.print("비밀번호 입력: ");
                        password = sc.next();
                        System.out.print("이름 입력: ");
                        String name = sc.next();
                        MembersDTO membersDTO = new MembersDTO(inputId, password, name);
                        membersDAO.addMember(membersDTO);
                        System.out.println("회원이 추가되었습니다.");
                        break;

                    case 3: // 종료
                        membersDAO.close();
                        sc.close(); // 스캐너를 먼저 닫습니다.
                        System.out.println("프로그램을 종료합니다.");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("올바른 숫자를 입력해주세요.");
                        break;
                }
            } else { // 로그인한 경우
                switch (choice) {
                    case 1: // 글 작성하기
                        System.out.print("글 제목 입력: ");
                        String title = sc.next();
                        System.out.print("글 내용 입력: ");
                        String content = sc.next();
                        PostsDTO myPostsDTO = new PostsDTO(title, content, loginMember.getId());
                        postsDAO.addPost(myPostsDTO);
                        System.out.println("글이 작성되었습니다.");
                        break;

                    case 2:
                        // 글 목록
                        List<PostsDTO> postsList = postsDAO.getAllPosts();
                        if (!postsList.isEmpty()) {
                            System.out.println("<모든 글 목록>");
                            for (PostsDTO post : postsList) { // 향상된 for 문
                                System.out.println("글 번호 : " + post.getId());
                                System.out.println("글 제목 : " + post.getTitle());
                                System.out.println("================================================");
                            }

                            // 댓글 작성 및 좋아요 누르기 위해 글 선택
                            System.out.print("몇번 글에 들어갈까요? : ");
                            int selectedPostID = sc.nextInt();
                            boolean isPost = true;
                            while (isPost) {
                                List<PostsDTO> enter = postsDAO.enterPost(selectedPostID);
                                System.out.println(selectedPostID + "번 글에 들어 왔습니다.");
                                PrintMenu.postLogo();
                                if(enter.isEmpty()){
                                    System.out.println(selectedPostID+"번 게시글은 존재하지 않습니다.");
                                    break;
                                }
                                for (PostsDTO e : enter) { // 향상된 for 문
                                    System.out.println("<" + selectedPostID+ "번 글>");
                                    System.out.println("글 번호 : " + e.getId());
                                    System.out.println("글 제목 : " + e.getTitle());
                                    System.out.println("글 작성 시간 : " + e.getCurrentTime());
                                    System.out.println("글 내용 : " + e.getContent());
                                    System.out.println("작성자 : " + e.getName());
                                    System.out.println("추천수 : " + e.getLikesCounts());
                                    System.out.println("================================================");
                                    System.out.println();
                                }
                                System.out.print("[1] 댓글 작성, [2] 댓글 수정, [3] 댓글 보기, [4] 댓글 삭제, [5] 좋아요, [6] 글 수정하기, [7] 나가기: ");
                                int action = sc.nextInt();
                                switch (action) {
                                    case 1: // 댓글 작성
                                        System.out.print("댓글을 입력해주세요 : ");
                                        String comment = sc.next();
                                        commentsDAO.addComment(new CommentsDTO(Integer.toString(selectedPostID), loginMember.getId(), comment, loginMember.getName()));
                                        break;
                                    case 2: // 댓글 수정
                                        commentsDAO.commentModify();
                                        break;
                                    case 3: // 댓글 보기
                                        commentsDAO.getCommentsByPostId(new CommentsDTO(Integer.toString(selectedPostID), loginMember.getId()));
                                        break;
                                    case 4: // 댓글 삭제
                                        commentsDAO.commentDelete(new CommentsDTO());
                                        break;
                                    case 5: // 좋아요
                                        likesDAO.addLike(new LikesDTO(Integer.toString(selectedPostID), loginMember.getId()));
                                        break;
                                    case 6: // 글 수정하기
                                        postsDAO.modifyPost(new PostsDTO(), selectedPostID ,loginMember.getId());
                                        break;
                                    case 7: // 나가기
                                        isPost = false;
                                        break;
                                    default:
                                        break;
                                }
                            }
                        } else {
                            System.out.println("등록된 글이 없습니다.");
                        }
                        break;

                    case 3:
                        loginMember = null; // 로그아웃
                        break;
                    case 4: // 회원 탈퇴
                        membersDAO.deleteMember(loginMember);
                        break;
                    default:
                        System.out.println("올바른 숫자를 입력해주세요.");
                        break;
                }
            }
        }
    }
}
