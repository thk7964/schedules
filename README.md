# 일정 관리 프로젝트
Lv 1 부터 Lv 8순으로 점차적으로 기능을 확장해 나간 일정 관리 프로그램 입니다.
Lv7까지구현 했습니다.

## 목차 
* 개발 기간
* 개발 환경
* 구현한 기능
* 프로그램 확장 과정
* 패키지 구조
* API 명세
* ERD 명세
* 사용 방법


### 개발 기간 
25.11.07~25.11.19

### 개발 환경 
* 개발 언어 : JAVA
* JDK 버전 : 17
* 프레임워크 : Spring boot 3.5.7
* 빌드 도구 : Gradle
* DB : MySQL
* ORM : Spring Data JPA
* IDE : IntelliJ IDEA
* API 테스트 : Postman

### 구현한 기능 
| 번호| 기능명 | 설명 |
|-------|-------|-------|
|1| 일정 CRUD | 일정 생성, 조회, 수정, 삭제할 수 있습니다. |
|2| 유저 CRUD | 유저 생성, 조회, 수정, 삭제할 수 있습니다. |
|3| 회원가입 | 유저에 비밀번호 필드를 추가한다. |
|4| 로그인(인증) | Session과 Filter를 활용한 로그인 및 인증 기능 구현 |
|5| 다양한 예외처리 적용하기 | Validation을 활용해 다양한 예외처리를 적용합니다. |
|6| 비밀번호 암호화 | 비밀번호 필드에 들어가는 비밀번호를 암호화합니다. |
|7| 댓글 CRUD | 댓글 생성, 조회, 수정, 삭제할 수 있습니다. |
|8| 일정 페이징 조회 (미구현) | 일정을 Spring Data JPA의 Pageable과 Page 인터페이스를 활용하여 페이지네이션을 구현 |

### 프로그램 확장 과정 
| 레벨 | 구현 내용 |
|-----|-----|
| Lv 1 | 일정 CRUD 기능 구현 |
| Lv 2 | 유저 CRUD 기능 구현 |
| Lv 3 | 회원가입 기능 구현 |
| Lv 4 | 로그인 기능 구현 |
| Lv 5 | 다양한 예외처리 적용 |
| Lv 6 | 비밀번호 암호화 기능 구현 |
| Lv 7 | 댓글 CRUD 기능 구현 |
| Lv 8 (미구현)| 일정 페이징 조회 기능 구현 |

### 패키지 구조
<img width="648" height="943" alt="image" src="https://github.com/user-attachments/assets/b4771516-3532-4fd5-8912-54fc66343f27" />

### API 명세
[https://documenter.getpostman.com/view/12989531/2sB3Wqu132](https://plucky-raincoat-f93.notion.site/API-2a60a0fa496d800e80e3f87601457103?pvs=73)


### ERD 명세
<img width="970" height="505" alt="image" src="https://github.com/user-attachments/assets/e8d71926-da45-4b98-8986-3e23803987e7" />


### 사용 방법
1. IntelliJ 실행
2. schedule 폴더를 open
3. src/main/java/com/example/schedules/SchedulesApplication.java 파일을 열기
4. 상단 메뉴에서 Run 클릭 해서 실행
5. Postman으로 API요청 보내기



