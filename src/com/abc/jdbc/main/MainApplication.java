package com.abc.jdbc.main;

import java.util.Scanner;
import com.abc.jdbc.dao.MembersDAO;

public class MainApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MembersDAO membersDAO = new MembersDAO();

        while (true) {
            System.out.println("<회원 관리 프로그램>");
            System.out.println("[1] 회원 추가");
            System.out.println("[2] 모든 회원 조회");
            System.out.println("[3] 종료");
            System.out.print("메뉴를 선택하세요 : ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("이름 입력: ");
                    String name = scanner.next();
                    System.out.print("비밀번호 입력: ");
                    String email = scanner.next();
                    membersDAO.addMember(name, email);
                    System.out.println("회원이 추가되었습니다. ");
                    break;
                case 2:
                    membersDAO.getAllMembers();
                    break;
                case 3:
                    membersDAO.close();
                    System.out.println("프로그램을 종료합니다.");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("올바른 숫자를 입력해주세요.");
                    break;
            }
        }
    }
}

