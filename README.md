![image](https://github.com/ryu-jaehyun/AiPlanner/blob/master/images/%ED%94%8C%EB%9E%98%EB%84%88logo.png?raw=true)



# 프로젝트 소개

> 머신러닝 기반 사용자 맞춤 스케줄러 웹사이트입니다.
>
> 사용자에게는 일정 설계에 어려움이 있으면 머신러닝과 알고리즘을 통해 사용자와 비슷한 그룹의 보편적인 스케줄을 제시함으로써 효율성을 제공합니다.
>
> 월별 일정, 일일 일정을 구분하여 일정관리 측면에서 접근성이 좋아 편리함을 제공합니다.
> 
> Spring Boot & Spring Data JPA & Spring Security를 사용해 REST API를 구현하고, Amazon EC2, Amazon RDS를 이용해 서버를 배포했습니다.


> ###  개발 기간 및 인원
>
> 23.03.06 ~ 23.06.05 (3개월)
>
> Frontend  : 전재오, 김민우
> 
> Backend  : 하정민(AI), 류제현(Server)-PL


# ERD 구조

![ERD](https://github.com/ryu-jaehyun/AiPlanner/blob/master/images/ERD%20%EA%B5%AC%EC%A1%B0.png?raw=true)


# 시스템 아키텍쳐

![시스템 아키텍쳐](https://github.com/ryu-jaehyun/AiPlanner/blob/master/images/%EC%8B%9C%EC%8A%A4%ED%85%9C%EC%95%84%ED%82%A4%ED%85%8D%EC%B3%90.png?raw=true)

# 기술 스택


[![stackticon](https://firebasestorage.googleapis.com/v0/b/stackticon-81399.appspot.com/o/images%2F1708924375402?alt=media&token=441b4e89-2ded-40a6-9a39-725030396c03)](https://github.com/msdio/stackticon)

# 기능

###  회원가입,로그인, 아이디&비밀번호 찾기

+ 사용자는 아이디, 비밀번호, 이름 , 생년월일, 성별, 핸드폰 번호를 기입하고 직업군(중고등학생/대학생)을 선택하여 회원가입할 수 있다.
+ 사용자가 24시간동안 웹 페이지를 사용하지 않는 경우 자동으로 로그아웃되어 보안을 유지한다.
+ 사용자는 핸드폰 번호를 이용해 아이디를 찾을수 있고, 아이디와 핸드폰 번호를 이용해 비밀번호를 찾을수 있다.


### 일정 관리

+  월별일정, 일일일정 별 일정목록을 조회할 수 있다. [월별일정 보기](https://github.com/ryu-jaehyun/AiPlanner/blob/master/images/%EC%9B%94%EB%B3%84%EC%9D%BC%EC%A0%95.png?raw=true)  [일일일정 보기](https://github.com/ryu-jaehyun/AiPlanner/blob/master/images/%EC%9D%BC%EC%9D%BC%EC%9D%BC%EC%A0%95.png?raw=true)
+  월별일정, 일일일정을 구분해 등록할 수 있다.
    + 일일일정은 다시 고정일정, 유동일정으로 구분된다.
    + 고정일정은 월,수,금 이런 형식으로 요일을 선택해 수업, 아르바이트 등등 유동 가능성이 거의 없는 일정이다.
+  일정타입(식사,공부,휴식,여가,운동)을 확인하여 타입에 맞춰서 등록할 수 있다.
+  일정을 하나 등록, 수정, 삭제할 때 마다 자동적으로 해당 사용자의 전체일정이 웹에 갱신된다.
+  시간대가 겹쳐도 최대 2~3(월별 3개/ 일일 2개)까지 등록할수 있다.


### 부가기능

+ 월별일정, 일일일정 모두 각 일정마다 체크박스에 체크함으로써 달성표시를 할 수 있다. --> 체크할때마다 갱신된 달성률과 달성률에 따른 특정 문구도 볼수 있다.
+ 일일 일정을 조회할때 총 공부시간을 확인할 수 있다.
+ 기본 5가지 일정타입 이외에 일정타입을 사용자가 직접 등록하고 그에 해당하는 색깔도 정할수 있다.
+ 이메일을 기입하고 이메일 전송 동의 버튼을 누르면 매일 자정에 사용자 이메일로 일일일정에 대한 정보가 전송된다.

# API 명세서

|  **Domain** |        **URL**       | **Http Method** |      **Description**     |
|:-----------:|:--------------------:|:---------------:|:------------------------:|
|   **User**  |     /user/checkId    |       POST      |      아이디 중복검증     |
|             |     /user/signup     |       POST      |      사용자 회원가입     |
|             |     /user/findId     |       POST      |        아이디 찾기       |
|             |     /user/findPw     |       POST      |       비밀번호 찾기      |
|             |      /user/login     |       POST      |       사용자 로그인      |
|   **Plan**  |     /plan/day/add    |       POST      |       일일일정 등록      |
|             |     /plan/day/get    |       GET       |       일일일정 조회      |
|             |   /plan/day/update   |      PATCH      |       일일일정 수정      |
|             |   /plan/day/delete   |      DELETE     |       일일일정 삭제      |
|             |    /plan/month/add   |       POST      |       월별일정 등록      |
|             |    /plan/month/get   |       GET       |       월별일정 조회      |
|             |  /plan/month/update  |      PATCH      |       월별일정 수정      |
|             |  /plan/month/delete  |      DELETE     |       월별일정 삭제      |
| **Setting** |   /setting/success   |       POST      |    사용자 달성률 표시    |
|             | /setting/study-total |       GET       |  사용자 총 공부시간 표시 |
|             |   /setting/type/add  |       POST      |       일정타입 추가      |
|             |   /setting/type/get  |       GET       |       일정타입 조회      |
|             |  /setting/send-email |       POST      | 일정 시작 전 이메일 전송 |



# 프로젝트 소감

- 새로운 기술 스택을 학습하고 적용하면서 보안 및 데이터 액세스에 대한 기능이 이전 프로젝트보다 향상되어서 성취감이 컸다.
- 관리자에 대한 기능과 다양한 이벤트(ex. 달성률 상위 10명에게 기프티콘 증정) 등 초기 설계를 더 확장성있게 했으면 프로젝트 퀄리티가 상승했을것에 대한 아쉬운점이 있다.
