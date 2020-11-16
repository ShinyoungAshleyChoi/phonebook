package service;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import dao.ContactDao;
import vo.ContactVo;

/**
 * @author shinyoung
 *
 */
public class ContactService {

Scanner sc = new Scanner(System.in); //Scanner생성자 호출

	
	/**
	 *1. 회원 추가
	 *Vo를 불러와서 회원 정보 키보드로 받아 입력 받아 Dao 클래스의 insertcont실행
	 * void
	 */
	public void insertCont() {
		ContactVo cont = new ContactVo();
		
		System.out.println("등록할 회원의 정보를 입력하세요.");
		System.out.print("이름: ");
		String name = sc.next();
		cont.setName(name);
		System.out.print("번호: ");
		String number = sc.next();
		cont.setNumber(number);
		System.out.print("지역: ");
		String city = sc.next();
		cont.setCity(city);
		try {
			System.out.print("종류(100:가족, 200:친구, 300:회사, 400:기타): ");
			int categoryno = sc.nextInt();
			cont.setCategoryno(categoryno);
		} catch(InputMismatchException e) {
			System.out.println("구분은 숫자형태(100:가족, 200:친구, 300:회사, 400:기타)로만 입력해주세요.");
		}

		ContactDao contDao = new ContactDao();
		contDao.insertCont(cont);
	}
	/**
	 * 2. 전체 회원 출력
	 * 회원 정보가 담겨 있는 Arraylist를 불러와 Arraylist의 사이즈가 0이면 회원 정보가 없다는 메시지를,
	 * 1이상이면 for문으로 인덱스를 반복하여 Arraylist의 내용 출력
	 * void
	 */
	public void list() {
		ContactDao contDao = new ContactDao();
		ArrayList<ContactVo> contal = contDao.list();
		if(contal == null) {
			System.out.println("회원 정보가 없습니다.");
		}
		else {
			System.out.println("총 "
					+  contal.size()
					+  "명의 회원이 저장되어 있습니다.");
			for (int i = 0; i < contal.size(); i++) {
				System.out.println(contal.get(i));
			}
		}
	}
	
	/**
	 * 3. 회원 정보 수정
	 * 1) 이름을 입력받아 해당 이름과 일치하는 회원정보를 Arraylist에 담음
	 * 2) 해당 이름과 일치하는 회원 정보가 index+1되어 채번
	 * 3) 수정하려는 회원 정보의 번호를 선택하면 해당번호-1하여 Arraylist의 인덱스 값을 찾고
	 * 해당되는 회원정보의 변경전 전화번호를 numberbefore변수에 저장
	 * 4) 위 변수는 쿼리문 where절 조건에 걸려있는 phnumber 컬럼의 값으로 사용됨
	 * 5) 변경 사항을 키보드로 입력받아 Vo에 저장하여 updateCont 메서드 실행 
	 * void
	 */
	public void update() {
		System.out.println("수정할 회원의 이름을 입력하세요.");
		ContactDao contDao = new ContactDao();
		String searchName = sc.next();
		ArrayList<ContactVo> contal = contDao.search(searchName);
		
		int size = contal.size();
		
		if(size == 0) {
			System.out.println("해당하는 회원이 없습니다.");
		} else {
			for (int i = 0; i < contal.size(); i++) {
				System.out.print(i+1 + ". ");
				System.out.println(contal.get(i));
			}
			
			System.out.println("수정할 회원의 번호를 선택하세요.");
			int updateNo = sc.nextInt();
			
			ContactVo cont = new ContactVo();
				
			System.out.print("이름: ");
			String name = sc.next();
			cont.setName(name);
			System.out.print("번호: ");
			String number = sc.next();
			cont.setNumber(number);
			System.out.print("지역: ");
			String city = sc.next();
			cont.setCity(city);
			try {
				System.out.print("종류(100:가족, 200:친구, 300:회사, 400:기타): ");
				int categoryno = sc.nextInt();
				cont.setCategoryno(categoryno);
				} catch(InputMismatchException e) {
					System.out.println("종류는 숫자형태(100:가족, 200:친구, 300:회사, 400:기타)로만 입력해주세요.");
				}
			cont.setNumberbefore(contal.get(updateNo - 1).getNumber());
			contDao.updateCont(cont);
			System.out.println("수정이 완료되었습니다.");
			
		}
	}
		
		
	/**
	 * 4. 회원 정보 지우기
	 * 위와 동일한 알고리즘으로 삭제할 회원 정보를 찾아 deleteCont메서드 실행 
	 * void
	 */
	public void delete() {
		System.out.println("삭제할 회원의 이름을 입력하세요.");
		ContactDao contDao = new ContactDao();
		String searchName = sc.next();
		ArrayList<ContactVo> contal = contDao.search(searchName);
		
		int size = contal.size();
		
		if(size == 0) {
			System.out.println("해당하는 회원이 없습니다.");
		} else {
			for (int i = 0; i < contal.size(); i++) {
				System.out.print(i+1 + ". ");
				System.out.println(contal.get(i));
			}
			System.out.println("삭제할 회원의 번호를 선택하세요.");
			int deleteNo = sc.nextInt();
			
			ContactVo cont = new ContactVo();
			cont.setNumber(contal.get(deleteNo - 1).getNumber());
			contDao.deleteCont(cont);
			
			System.out.println("삭제가 완료되었습니다.");
		}
	}
	
}
