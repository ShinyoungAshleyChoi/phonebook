package controller;

import java.util.Scanner;

import service.ContactService;

public class ContactController {

	public static void main(String[] args) {
		
		ContactService contsrv = new ContactService();
		Scanner sc = new Scanner(System.in);
		
		while(true) { //메뉴 출력 무한루프
			System.out.println();
			System.out.println("============================");
			System.out.println("  다음 메뉴 중 하나를 선택하세요.");
			System.out.println("============================");
			System.out.println("1. 회원추가");
			System.out.println("2. 회원 목록 보기");
			System.out.println("3. 회원 정보 수정하기");
			System.out.println("4. 회원 삭제");
			System.out.println("5. 종료");
				
			if(sc.hasNext()) {
				String menu = sc.next();
				switch(menu) {
				case "1":
					contsrv.insertCont();
					break;
				case "2":
					contsrv.list();
					break;
				case "3":
					contsrv.update();
					break;
				case "4":
					contsrv.delete();
					break;
				case "5":
					System.out.println("프로그램을 종료합니다.");
					sc.close();
					return;
				default:
					System.out.println("메뉴를 다시 선택해주세요.");
				}
				
			}
		}
	}
}
