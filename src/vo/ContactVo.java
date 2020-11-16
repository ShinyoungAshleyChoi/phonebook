package vo;

public class ContactVo {
	private String name;			//회원 이름(psname 컬럼)의 내용 저장하는 변수
	private String number; 			//회원 번호(phnumber 컬럼)의 내용 저장하는 변수
	private String city;			//회원 지역(pscity 컬럼)의 내용 저장하는 변수
	private String category;		//회원 종류(pscategory 컬럼)의 내용 저장하는 변수
	private int categoryno;			//회원 종류 번호(categoryno 컬럼)의 내용 저장하는 변수
	private String categorynm;      //종류명(categorynm 컬럼)의 내용 저장하는 변수
	private String numberbefore;	//회원 정보 수정 시 where절 조건에 걸리는 번호 컬럼을 저장하는 변수
	

	//다른 클래스에서 위 변수에 접근 가능하도록 Getters/Setters 메서드 생성
	public String getNumberbefore() {
		return numberbefore;
	}



	public void setNumberbefore(String numberbefore) {
		this.numberbefore = numberbefore;
	}



	public String getCategorynm() {
		return categorynm;
	}



	public void setCategorynm(String categorynm) {
		this.categorynm = categorynm;
	}



	public String getCategory() {
		return category;
	}



	public void setCategory(String category) {
		this.category = category;
	}


	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getNumber() {
		return number;
	}



	public void setNumber(String number) {
		if(number.matches("^[0-9]*$")) {					//회원번호 저장시 입력 값이 숫자로 구성되어 있는지 확인 후 저
			this.number = number;
		}else {
			System.out.println("번호는 숫자로만 입력해주세요.");   //숫자가 아닌 문자 등이 포함되어 있다면 해당 문구 출력
		}
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}


	public int getCategoryno() {
		return categoryno;
	}



	public void setCategoryno(int categoryno) {
			switch(categoryno) {  				//종류번호는 아래 네가지만 저장 가능, 이 네가지 이외의 숫자는 저장하지 않고 아래 default문구를 출력함
			case 100:
			case 200:
			case 300:
			case 400:
				this.categoryno = categoryno;
				break;
			default:
				System.out.println("종류는 숫자형태(100(가족), 200(친구), 300(회사), 400(기타))로만 입력해주세요.");
			}
	}
	
	
	//회원 정보 출력시에 아래 toString메서드가 자동으로 실행되며 아래 문구대로 출력
	@Override
	public String toString() {
		return "회원정보: 이름: " + name
			 + ", 번호: " + number
			 + ", 지역: " + city
			 + ", 구분: " + category;
	}
	
	
}
