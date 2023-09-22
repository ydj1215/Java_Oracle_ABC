package com.abc.jdbc.util;

public class PrintMenu {
    static public void notLoginMenu() {
        System.out.println("<회원 관리 프로그램>");
        System.out.println("[1] 로그인");
        System.out.println("[2] 회원가입");
        System.out.println("[3] 종료");
    }

    static public void loginMenu(){
        System.out.println("<글 관리 프로그램>");
        System.out.println("[1] 글쓰기");
        System.out.println("[2] 글 목록보기");
        System.out.println("[3] 로그아웃");
    }
}
