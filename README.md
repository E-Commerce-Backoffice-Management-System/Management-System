# 404 Not Missing 
# E-Commerce BackOffice Management System

## 📌 프로젝트 소개
이커머스 서비스의 핵심은 **고객, 상품, 주문 데이터를 효율적으로 관리하는 것**입니다.

이를 위해 관리자는 **백오피스(관리자 페이지)**를 사용하여 데이터를 다룹니다.

이번 프로젝트에서는 실제 백오피스 환경을 가정하고, 고객, 상품, 주문 정보를 관리할 수 있는 기능을 직접 구현해봅니다. 또한 데이터가 많아질 때를 대비해 **검색, 정렬, 페이징 기능**까지 함께 만들어봅니다.

이 프로젝트의 목표는 관리자가 고객, 상품, 주문 데이터를 **쉽고 정확하게 관리할 수 있는 기본 시스템**을 구축하는 것입니다.

## 📌 멤버 소개

| 구분   | 이름    | 역할                                        |
|------|-------|-------------------------------------------|
| 팀장   | 최형민   | 역할 분담 및 팀 관리, 관리자 & 에러 처리 파트, 프로젝트 모의 테스트 |
| 팀원   | 정호진   | 관리자 & 리뷰 파트, 프로젝트 모의 테스트                  |
| 팀원   | 박수지   | 고객 & 주문 파트                                |
| 팀원   | 류호정   | 상품 & 주문 파트                                |
| 튜터   | 조용석   | SA 피드백 & 동기부여, Git 피드백 & 기술 질의응답          |

## 📌 커밋 컨벤션

| Prefix | 설명 | 예시 |
|--------|------|------|
| `feat/` | 새로운 기능 추가 | `feat/login-api`, `feat/signup-ui` |
| `fix/` | 버그 수정 | `fix/login-error`, `fix/typo-correction` |
| `refactor/` | 기능 변경 없는 코드 개선 | `refactor/member-service`, `refactor/folder-structure` |
| `docs/` | 문서 수정 | `docs/readme`, `docs/api-spec` |
| `chore/` | 설정 변경, 패키지 업데이트 | `chore/build-gradle`, `chore/github-action` |

## 📅 프로젝트 일정 (8일 스프린트)

| 단계 | 날짜 | 내용 |
|------|------|------|
| 1 | 2월 19일 | 프로젝트 파악 및 환경 세팅 |
| 2 | 2월 20일 | 역할 분담 및 일정 설정 |
| 3 | 2월 21일 ~ 23일 | 각자 맡은 기능 구현 |
| 4 | 2월 24일 | 통합 테스트 및 오류 수정 |
| 5 | 2월 25일 | 프로젝트 초안 완성 및 재테스트 |
| 6 | 2월 26일 | 최종 제출 및 발표 |

## 🛠 기술 스택

| 구분 | 기술 |
|------|------|
| Language | <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" width="20"/> Java 17 |
| Framework | <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" width="20"/> Spring Boot 4.0.2 |
| Build Tool | <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/gradle/gradle-original.svg" width="20"/> Gradle 9.3.0 |
| Database | <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mysql/mysql-original.svg" width="20"/> MySQL 8.4.8 |

## 📌 merge 할경우 

- 작업 전 조원들과 충분한 소통 후 진행.
- 긴급한 상황에서는 마이크를 켜고 즉시 논의 가능.
  
## 📌 이슈 템플릿 읽어보고 작업 진행할것.

## 📌 브랜치 생성 및 삭제를 꼭 숙지할것.

- 브랜치 생성 터미널 명령어 : git switch -c (상황에 맞는 접두어) 작명은 알아서

데일리 스크럼
10:30 / 19:30 (월, 목요일은 19:30 생략)

1. 자리를 비울 때는 프라이빗 채팅으로 미리 공유하기
2. 조퇴나 외출 시에는 일정에 명확히 표시하기
3. 질문이 있을 경우, 먼저 ‘찌르기’ 기능을 활용해 빠르게 소통하기
4. 개인 공부 시간에는 서로 방해하지 않기
5. 팀 노션 꼭 작성하기.
6. 캠 꼭 활성화하기.


---
커밋 메시지

브랜치 전략

PR 방법 -> PR 병합방법
### 📍 API 엔드포인트 요약 (Endpoints)

#### 🔐 관리자 공통 (Admin - Login/Logout)
| 기능 | 메서드 | 엔드포인트 | 비고 |
|:---:|:---:|:---|:---|
| 관리자 로그인 | `POST` | `/api/admins/login` | 세션 기반 로그인 |


#### 🔐 관리자 계정 및 인증 (Admin - OS Amin, CS Admin)
| 기능 | 메서드 | 엔드포인트 | 비고 |
|:---:|:---:|:---|:---|
| 관리자 회원가입 | `POST` | `/api/admins/signup` | 승인 대기 상태로 등록 |
| 관리자 로그아웃 | `POST` | `/api/admins/logout` | 세션 무효화 |
| 내 프로필 조회 | `GET` | `/api/admins/{adminId}/getProfile` | - |
| 내 프로필 수정 | `PUT` | `/api/admins/{adminId}/updateProfile` | - |
| 비밀번호 변경 | `PATCH` | `/api/admins/{adminId}/password` | - |


#### 👑 슈퍼관리자 전용 (Super Admin Only)
|     기능     |   메서드    | 엔드포인트 | 비고 |
|:----------:|:--------:|:---|:---|
|   가입 승인    |  `POST`  | `/api/admins/{adminId}/approve` | - |
|  전체 목록 조회  |  `GET`   | `/api/admins` | 페이징/필터 지원 |
|  상세 정보 조회  |  `GET`   | `/api/admins/{adminId}` | - |
| 관리자 정보 수정  |  `PUT`   | `/api/admins/{adminId}/update` | - |
| 관리자 삭제(탈퇴) | `DELETE` | `/api/admins/{adminId}/deletes` | - |
|  승인 상태 변경  | `PATCH`  | `/api/admins/{adminId}/adminStatus` | 승인/거절 |
| 관리자 역할 변경  | `PATCH`  | `/api/admins/{adminId}/role` | 권한 부여 |
| 관리자 상태 변경  | `PATCH`  | `/api/admins/{adminId}/status` | 정지/활성 |


#### 👥 고객 관리 (Customer)
|      기능      | 메서드 | 엔드포인트                                | 비고 |
|:------------:|:---:|:-------------------------------------|:---|
|   고객 회원 가입   | `POST` | `/api/customerSignup`                | -  |
|    고객 로그인    | `POST` | `/api/customerLogin`                 | -  |
| 고객 집계 리스트 조회 | `GET` | `/api/customers`                     | -  |
|   고객 상세 조회   | `GET` | `/api/customers/{customerId}`        | -  |
|   고객 정보 수정   | `PATCH` | `/api/customers/{customerId}`        | -  |
|   고객 상태 변경   | `PATCH` | `/api/customers/{customerId}/status` | -  |
|  고객 삭제(탈퇴)   | `DELETE` | `/api/customers/{customerId}`        | -  |


#### 📦 상품 및 주문 (Product & Order)
|         기능         |   메서드    | 엔드포인트                             | 비고      |
|:------------------:|:--------:|:----------------------------------|:--------|
|       상품 등록        |  `POST`  | `/api/products`                   | -       |
|     상품 리스트 조회      |  `GET`   | `/api/products`                   | -       |
|      상품 상세 조회      |  `GET`   | `/api/products/{productId}`       | -       |
|       상품 수정        | `PATCH`  | `/api/products/{productId}`       | -       |
|       상품 삭제        | `DELETE` | `/api/products/{productId}`       | -       |
|     주문 생성(관리자)     |  `POST`  | `/api/admins/orders`              | CS  주문  |
|     주문 생성(고객)      |  `POST`  | `/api/customers/orders`           | 일반 고객 주문 |
|   주문 리스트 조회(관리자)   |  `GET`   | `/api/orders`                     | 페이징/키워드 검색 |
| 주문 상세 정보 조회(CS관리자) |  `GET`   | `/api/admins/orders/{orderId}`    | -       |
|  주문 상세 정보 조회(고객)   |  `GET`   | `/api/customers/orders/{orderId}` | -       |
|       주문 취소        | `PATCH`  | `/api/orders/{orderId}/cancel`    | -       |


#### 💬 리뷰 관리 (Review)
|    기능     | 메서드 | 엔드포인트 | 비고  |
|:---------:|:---:|:---|:----|
|   리뷰 생성   | `POST` | `/api/customers/{customerId}/products/{productId}/reviews` | -   |
| 리뷰 리스트 조회 | `GET` | `/api/reviews` | 페이징 |
| 리뷰 상세 조회  | `GET` | `/api/reviews/{reviewId}` | -   |
|   리뷰 삭제   | `DELETE` | `/api/admins/{adminId}/reviews/{reviewId}` | -   |


## :hammer_and_wrench: API Reference
| 구분          | 링크                     |
|:------------|:-----------------------|
| **API 명세서** | [상세 보기](./docs/api.md) |
