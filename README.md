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
## 🔧 Git 협업 규칙

### 📝 커밋 메시지 규칙
커밋 메시지는 작업 내용을 명확하게 파악할 수 있도록 다음 형식을 따릅니다.
본인 작업에 알맞는 접두어(Prefix) 선택 후 / (기능, 작업설명) 식으로 작성합니다.
같은 작업을 수행할 경우 작업 뒤에 버전을 기입해도 됩니다. 
#### 📌 예시
feat/회원 로그인 기능 추가
docs/README 파일 수정 1.1

### 🌿 브랜치 전략
Git Flow를 단순화한 브랜치 전략을 사용합니다.
- `main` : 배포 가능한 최종 코드
- `develop` : 개발 통합 브랜치
- `feat/*` : 기능 개발 브랜치
- `fix/*` : 버그 수정 브랜치

### 🔀 PR (Pull Request) 방법
1. 기능 개발은 `feature` 브랜치에서 진행합니다.
2. 개발 완료 후 `develop` 브랜치로 PR을 생성합니다.
3. PR 생성 시 작업 내용과 변경 사항을 상세히 작성합니다.
4. 최소 1명 이상의 코드 리뷰 후 병합합니다.

### ✅ PR 병합 방법
- 코드 리뷰 승인 후 병합합니다.
- 병합 방식은 **Merge Commit**을 사용합니다.
- 병합 후 사용한 브랜치는 삭제합니다.

### 📌 Merge Commit 방식의 특징
#### ✔️ 장점
- 작업 흐름을 그대로 보존(실수할 경우 비교적 복구가 쉬움)
- 누가 무엇을 했는지 추적 쉬움
#### ❌ 단점
- 커밋 기록이 복잡해짐
- 히스토리가 지저분해질 수 있음

## 📌 ERD
------
<img width="1777" height="4123" alt="Image" src="https://github.com/user-attachments/assets/0eba2cfc-b519-4638-8d0c-80d147607f24" />

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
