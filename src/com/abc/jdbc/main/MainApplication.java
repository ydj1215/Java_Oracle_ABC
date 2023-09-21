package com.abc.jdbc.main;

import java.util.List;
import java.util.Scanner;

import com.abc.jdbc.dao.MembersDAO;
import com.abc.jdbc.dto.MembersDTO;

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
                    System.out.print("아이디 입력: ");
                    String inputId = scanner.next();
                    System.out.print("비밀번호 입력: ");
                    String password = scanner.next();
                    System.out.print("이름 입력: ");
                    String name = scanner.next();
                    MembersDTO membersDTO = new MembersDTO(inputId, password, name);
                    membersDAO.addMember(membersDTO);
                    System.out.println("회원이 추가되었습니다.");
                    break;
                case 2:
                    List<MembersDTO> membersList = membersDAO.getAllMembers();
                    if (!membersList.isEmpty()) {
                        System.out.println("<모든 회원 정보>");
                        for (MembersDTO member : membersList) { // 향상된 for 문
                            System.out.println("식별 번호 : " + member.getId());
                            System.out.println("아이디: " + member.getInputId());
                            System.out.println("비밀번호: " + member.getPassword());
                            System.out.println("이름: " + member.getName());
                            System.out.println();
                        }
                    } else {
                        System.out.println("등록된 회원이 없습니다.");
                    }
                    break;

                case 3:
                    membersDAO.close();
                    scanner.close(); // 스캐너를 먼저 닫습니다.
                    System.out.println("프로그램을 종료합니다.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("올바른 숫자를 입력해주세요.");
                    break;
            }
        }
    }
}
