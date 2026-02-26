
# 🔐 Admin API 명세서

## 관리자 공통 (OS/CS Admin)
관리자 시스템 접근을 위한 로그인 API입니다. 세션/쿠키 기반으로 인증을 관리합니다.

## 1. 관리자 로그인
이메일과 비밀번호를 확인하여 관리자 세션을 생성합니다. 계정 상태가 활성(APPROVED) 상태인 관리자만 로그인이 가능합니다.

- **Endpoint**: `POST` `/api/admins/login`
- **Request Body**

```json
{
  "email" : "test@test.com",
  "password" : "11111111"
}
```
- **Response (200 OK)**

# OS/CS 관리자

관리자 권한(OS Admin, CS Admin) 계정의 생성 및 본인 정보 관리를 위한 API입니다.

## 1. 관리자 회원가입(Signup)

새로운 관리자 계정 등록을 신청합니다. 초기 상태는 승인 대기(PENDING) 상태입니다.

- **Endpoint**: `POST` `/admins/signup`
- **Request Body**

```json
{

  "name" : "스파르탄",
  "email" : "test@test.com",
  "password" : "11111111",
  "phoneNumber" : "010-0101-0202",
  "role" : "CS_ADMIN"

}
```

- **Response (201 Created)**

```json
{
"success": true,
"data": {
"id": 2,
"name": "스파르탄",
"email": "test@test.com",
"phoneNumber": "010-0101-0202",
"role": "CS_ADMIN",
"createdAt": "2026-02-25T22:07:21.4659055",
"updatedAt": "2026-02-25T22:07:21.4659055"
},
"error": null
}
```

## 2. 관리자 로그아웃 (Logout)

현재 로그인된 관리자의 세션을 무효화합니다.

- **Endpoint**: `POST` `/admins/signup`
- 
- **Response (204 NoContent)**

# 3. 내 프로필 조회 (GetProfile)

로그인한 관리자 본인의 정보를 조회합니다.

- **Endpoint**: `GET` `/admins//admins/{adminId}/getProfile`

- **Response (200 OK)**

```json
{
  "success": true,
  "data": {
    "name": "철수",
    "email": "testdummy@gmail.com",
    "phoneNumber": "010-5555-6666"
  },
  "error": null
}
```

## 4. 내 프로필 수정 (UpdateProfile)

본인의 이름, 이메일, 전화번호 등 기본 인적 사항을 수정합니다.

- **Endpoint**: `PUT` `/admins/{adminId}/updateProfile`
- **Request**
```json
{
  "name" : "철수",
  "email" : "testdummy@gmail.com",
  "phoneNumber" : "010-5555-6666"
}
```

- **Response (200 OK)**

```json
{
  "success": true,
  "data": {
    "name": "철수",
    "email": "testdummy@gmail.com",
    "phoneNumber": "010-5555-6666"
  },
  "error": null
}
```

## 5. 비밀번호 변경(Change Password)

현재 비밀번호 확인 후 새로운 비밀번호로 교체합니다.

- **Endpoint**: `PATCH` `/admins/{adminId}/password`
- **Request**
```json
{
  "password" : "newPassword123"
} 
```

- **Response (200 OK)**

```json
{
  "success": true,
  "data": {
    "name": "철수"
  },
  "error": null
}
```


# 👑 Super Admin API 명세서

슈퍼관리자 권한(`SUPER_ADMIN`)을 가진 계정만 접근 가능한 API입니다.

## 1. 가입 승인 (Approve Admin)
승인 대기 상태(PENDING)인 관리자를 최종 승인 처리합니다.

- **Endpoint**: `POST` `/api/admins/{adminId}/approve`
- **Response (200 OK)**


## 2. 관리자 리스트 조회 (Get Admin List)
시스템에 등록된 모든 관리자 목록을 조회합니다. 페이징 및 필터링을 지원합니다.

- **Endpoint**: `POST` `/api/admins`
- **Response (200 OK)**
```json
{
  "content": [
    {
      "id": 1,
      "name": "갓갓갓",
      "email": "superAdmin@system.com",
      "phoneNumber": "010-1234-5678",
      "role": "SUPER_ADMIN",
      "status": "APPROVED",
      "createdAt": "2026-02-25T19:06:55.514888",
      "approvedAt": null
    }
  ],
  "empty": false,
  "first": true,
  "last": true,
  "number": 0,
  "numberOfElements": 1,
  "pageable": {
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 10,
    "paged": true,
    "sort": {
      "empty": false,
      "sorted": true,
      "unsorted": false
    },
    "unpaged": false
  },
  "size": 10,
  "sort": {
    "empty": false,
    "sorted": true,
    "unsorted": false
  },
  "totalElements": 1,
  "totalPages": 1
}
```

## 3. 상세 조회 (Get Admin Detail)
신규 관리자의 가입 신청을 최종 승인합니다.

- **Endpoint**: `POST` `/api/admins/{adminId}`
- **Response (200 OK)**
```json
{
  "success": true,
  "data": {
    "id": 2,
    "name": "스파르탄",
    "email": "test@test.com",
    "phoneNumber": "010-0101-0202",
    "role": "CS_ADMIN",
    "status": "PENDING",
    "createdAt": "2026-02-25T22:07:21.465906",
    "approvedAt": "2026-02-25T22:07:21.465906"
  },
  "error": null
}

```

## 4. 관리자 정보 수정 (Update Admin)
슈퍼관리자가 다른 관리자의 기본 정보를 직접 수정합니다.

- **Endpoint**: `PUT` `/api/admins/{adminId}/update`
- **Request Body**
```json
{
  "name" : "신입",
  "email" : "dodo@ggg.com",
  "phoneNumber" : "010-3232-5454"
} 
```

- **Response (200 OK)**
```json
{
  "success": true,
  "data": {
    "id": 2,
    "name": "신입",
    "email": "dodo@ggg.com",
    "phoneNumber": "010-3232-5454"
  },
  "error": null
}
```

## 5. 승인 상태 변경 (Update Admin Status)
승인 대기 중인 관리자를 승인하거나 거절합니다.

- **Endpoint**: `POST` `/api/admins/{adminId}/adminStatus`
- **Request Body**
```json
{
  "status" : "REJECTED",
  "rejectReason" : "잘못된 승인"
} 
```

- **Response (200 OK)**

## 6. 관리자 역할 변경 (Change Role)
관리자의 직무 권한을 변경합니다.

- **Endpoint**: `POST` `/api/admins/{adminId}/role`
- **Request Body**
```json
{
  "role" : "OPERATION_ADMIN"
} 
```

- **Response (200 OK)**
```json
{
"success": true,
"data": {
"id": 2,
"name": "신입",
"role": "OPERATION_ADMIN"
},
"error": null
}
```

## 7. 관리자 계정 상태 변경 (Account Status)
계정의 활성화 여부(정지, 휴면 등)를 관리합니다.

- **Endpoint**: `POST` `/api/admins/{adminId}/status`
- **Request Body**
```json
{
  "status" : "INACTIVE"
} 
```

- **Response (200 OK)**
```json
{
"success": true,
"data": {
"status": "INACTIVE"
},
"error": null
}
```

## 8. 관리자 삭제 (Delete Admin)
관리자 계정을 영구 삭제하거나 탈퇴 처리합니다.

- **Endpoint**: `POST` `/api/admins/{adminId}/deletes`

- **Response (204 No Content)**


# 👥 Customer API 명세서
시스템의 일반 고객 정보를 관리하고 상태를 제어하기 위한 API입니다.

## 1. 고객 회원가입(Customer Signup)

신규 고객이 시스템에 계정을 등록합니다.

- **Endpoint**: `POST` `/api/customerSignup`

- **Request**
```json
{
    "name" : "수지",
    "email" : "Suji@abc.com",
    "phoneNumber" : "010-7777-5555",
    "password" : "12345678"
}
```

- **Response (201 Created)**

```json
  {
  "success": true,
  "data": {
  "id": 1,
  "name": "수지",
  "email": "Suji@abc.com",
  "status": "ACTIVE"
  },
  "error": null
  }
```

## 2. 고객 로그인(Customer Login)

이메일과 비밀번호를 통해 고객 인증을 진행합니다.

- **Endpoint**: `POST` `/api/customerLogin`

- **Request**
```json
{
  "email" : "Suji@abc.com",
  "password" : "12345678"
}
```

- **Response (200 OK)**

## 3. 고객 리스트 조회(Get Customer List)

전체 고객 목록을 조회합니다. (페이징 및 필터 기능을 추가할 수 있습니다.)

- **Endpoint**: `GET` `/api/customers`

- **Response (200 OK)**
```json
{
"success": true,
"data": {
"content": [
{
"id": 1,
"name": "수지",
"email": "Suji@abc.com",
"status": "ACTIVE",
"createdAt": "2026-02-25T22:49:19.553034",
"summary": {
"totalOrderCount": 0,
"totalPurchaseAmount": 0
}
}
],
"empty": false,
"first": true,
"last": true,
"number": 0,
"numberOfElements": 1,
"pageable": {
"offset": 0,
"pageNumber": 0,
"pageSize": 10,
"paged": true,
"sort": {
"empty": false,
"sorted": true,
"unsorted": false
},
"unpaged": false
},
"size": 10,
"sort": {
"empty": false,
"sorted": true,
"unsorted": false
},
"totalElements": 1,
"totalPages": 1
},
"error": null
}
```

## 4. 고객 상세 조회(Get Customer Detail)

특정 고객의 상세 프로필 정보를 조회합니다.

- **Endpoint**: `GET` `/api/customers/{customerId}`

- **Response (200 OK)**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "name": "수지",
    "email": "Suji@abc.com",
    "phoneNumber": "010-7777-5555",
    "status": "활성",
    "createdAt": "2026-02-25T22:49:19.553034",
    "totalOrderCount": 0,
    "totalOrderAmount": 0
  },
  "error": null
}
```

## 5. 고객 정보 수정(Update Customer)

고객의 이름이나 전화번호 등 개인정보를 수정합니다.

- **Endpoint**: `PATCH` `/api/customers/{customerId}`
- **Request**

```json
{
"name" : "지수",
"email": "jisu@abc.com",
"phoneNumber": "010-0202-0303"
}
```

- **Response (200 OK)**

```json
{
  "success": true,
  "data": {
    "id": 1,
    "name": "지수",
    "email": "jisu@abc.com",
    "phoneNumber": "010-0202-0303"
  },
  "error": null
}
```

## 6. 고객 상태 변경(Change Status)

고객의 계정 상태(정지, 활성 등)를 변경합니다.

- **Endpoint**: `PATCH` `/api/customers/{customerId}/status`
- **Request**

```json
{
  "status" : "INACTIVE"
}
```

- **Response (200 OK)**

## 7. 고객 탈퇴(Delete Customer)

고객 계정을 탈퇴 처리하거나 삭제합니다.

- **Endpoint**: `PATCH` `/api/customers/{customerId}`

- **Response (204 No Content)

# 📦 Product API 명세서
시스템에서 판매되는 상품 정보를 관리하기 위한 API입니다.

## 1. 상품 등록 (Create Product)
새로운 상품을 시스템에 등록합니다.

- **Endpoint**: `POST` `/api/products`
- **Request**

```json
{
  "name" : "딸기",
  "category" : "FOOD",
  "price" : "10000",
  "stock" : "10",
  "status" : "판매중"
}
```

- **Response (201 Create)**

```json
{
  "success": true,
  "data": {
    "id": 1,
    "name": "딸기",
    "category": "FOOD",
    "price": 10000,
    "stock": 10,
    "status": "판매중",
    "createdAt": "2026-02-25T23:21:43.0605492"
  },
  "error": null
}
```

## 2. 상품 리스트 조회 (Get Product List)
등록된 모든 상품의 목록을 조회합니다. 페이징 및 검색 필터가 적용될 수 있습니다.

- **Endpoint**: `GET` `/api/products`

- **Response (200 OK)**

```json
{
  "success": true,
  "data": {
    "products": [
      {
        "id": 1,
        "name": "딸기",
        "category": "FOOD",
        "price": 10000,
        "stock": 10,
        "status": "판매중",
        "createdAt": "2026-02-25T23:21:43.060549",
        "adminName": "갓갓갓"
      }
    ],
    "totalElements": 1,
    "totalPage": 1,
    "currentPage": 1,
    "size": 10
  },
  "error": null
}
```

## 3. 상품 상세 조회 (Get Product Detail)
특정 상품의 상세 설명과 정보를 조회합니다.

- **Endpoint**: `GET` `/api/products/{productId}`

- **Response (200 OK)**

```json
{
  "success": true,
  "data": {
    "id": 1,
    "name": "딸기",
    "category": "FOOD",
    "price": 10000,
    "stock": 10,
    "status": "판매중",
    "createdAt": "2026-02-25T23:21:43.060549"
  },
  "error": null
}
```

## 4. 상품 정보 수정 (Update Product)
기존 상품의 이름, 가격, 재고 등을 부분 수정합니다.

- **Endpoint**: `PATCH` `/api/products/{productId}`

- **Request Body**

```json
{
  "name" : "맥북",
  "category" : "ELECTRONICS",
  "price" : "10000"
}
```
- **Response (200 OK)**
```json
  {
  "success": true,
  "data": {
  "id": 1,
  "name": "맥북",
  "category": "ELECTRONICS",
  "price": 10000,
  "stock": 10,
  "status": "판매중",
  "createdAt": "2026-02-25T23:21:43.060549"
  },
  "error": null
  }
```

## 5. 상품 삭제 (Delete Product)
더 이상 판매하지 않는 상품을 시스템에서 삭제합니다. (Soft Delete 권장)

- **Endpoint**: `PATCH` `/api/products/{productId}`
- **Response (204 No Content)**


# 🛍️ Order API 명세서
고객의 주문 생성부터 관리자의 주문 관리 및 취소 처리를 위한 API입니다.

## 1. 주문 생성(CS 주문) (CS Order)
CS 관리자가 고객의 요청을 받아 수동으로 주문을 생성합니다.

- **Endpoint**: `POST` `/api/admins/orders`

- **Request Body**

```json
{
  "customerId": 1,
  "productId": 1,
  "quantity": 10
}
```
- **Response (200 OK)**
```json
{
  "success": true,
  "data": {
    "customerName": "수지",
    "id": 1,
    "orderDate": "2026-02-25",
    "orderNumber": "ORD-75362C9A",
    "productName": "딸기",
    "quantity": 10,
    "status": "PREPARING",
    "totalPrice": 100000
  },
  "error": null
}
```

## 2. 주문 생성(고객) (Customer Order)
일반 고객이 자신의 계정으로 직접 주문을 생성합니다.

- **Endpoint**: `POST` `/api/customers/orders`

- **Request Body**

```json
{
  "customerId": 1,
  "productId": 2,
  "quantity": 5
}
```
- **Response (200 OK)**
```json
{
  "success": true,
  "data": {
    "customerName": "수지",
    "id": 2,
    "orderDate": "2026-02-25",
    "orderNumber": "ORD-B34A8F71",
    "productName": "수박",
    "quantity": 5,
    "status": "PREPARING",
    "totalPrice": 150000
  },
  "error": null
}
```

## 3. 주문 리스트 조회(관리자) (Get Order List)
관리자가 시스템의 모든 주문 내역을 확인합니다.

- **Endpoint**: `POST` `/api/orders`
- **Response (200 OK)**
```json
{
"success": true,
"data": {
"orders": [
{
"id": 1,
"orderNumber": "ORD-75362C9A",
"customerName": "수지",
"productName": "딸기",
"quantity": 10,
"totalPrice": 100000,
"orderDate": "2026-02-25",
"status": "PREPARING",
"adminName": "스파르탄"
},
{
"id": 2,
"orderNumber": "ORD-B34A8F71",
"customerName": "수지",
"productName": "수박",
"quantity": 5,
"totalPrice": 150000,
"orderDate": "2026-02-25",
"status": "PREPARING",
"adminName": "고객직접주문"
}
],
"totalElements": 2,
"totalPages": 1,
"currentPage": 1,
"size": 10
},
"error": null
}
```

## 4. 주문 상세 조회(관리자) (Get Order Detail)
관리자가 특정 주문의 결제, 배송 등 상세 내역을 확인합니다.

- **Endpoint**: `GET` `/api/admins/orders/{orderId}`
- **Response (200 OK)**

```json
{
"success": true,
"data": {
"id": 1,
"orderNumber": "ORD-75362C9A",
"status": "PREPARING",
"orderDate": "2026-02-25",
"quantity": 10,
"totalPrice": 100000,
"customerName": "수지",
"customerEmail": "Suji@abc.com",
"productName": "딸기",
"adminName": "스파르탄",
"adminEmail": "test@test.com",
"adminRole": "CS_ADMIN"
},
"error": null
}
```

## 5. 주문 상세 조회(고객) (Get Order Detail)
고객이 특정 주문의 결제, 배송 등 상세 내역을 확인합니다.

- **Endpoint**: `GET` `/api/customers/orders/{orderId}`
- **Response (200 OK)**

```json
{
  "success": true,
  "data": {
    "id": 1,
    "orderNumber": "ORD-75362C9A",
    "status": "PREPARING",
    "orderDate": "2026-02-25",
    "quantity": 10,
    "totalPrice": 100000,
    "customerName": "수지",
    "customerEmail": "Suji@abc.com",
    "productName": "딸기",
    "adminName": "스파르탄",
    "adminEmail": "test@test.com",
    "adminRole": "CS_ADMIN"
  },
  "error": null
}
```

## 6. 주문 취소
접수된 주문을 취소 처리하고 상태를 업데이트합니다.

- **Endpoint**: `GET` `/api/orders/{orderId}/cancel`
- **Response (200 OK)**

```json
{
"success": true,
"data": {
"orderNumber": "ORD-75362C9A",
"message": "ORD-75362C9A주문이 취소되었습니다."
},
"error": null
}
```

# 💬 Review API 명세서
상품 구매 후 고객이 남기는 리뷰를 관리하고 조회하기 위한 API입니다.

## 1. 리뷰 생성 (Create Review)
특정 상품을 구매한 고객이 리뷰를 작성합니다.

- **Endpoint**: `POST` `/api/customers/{customerId}/products/{productId}/reviews`

- **Request Body**

```json
{
  "productId": 1,
  "orderId": 1,
  "rating" : 4,
  "content" : "배송이 빨라서 좋았어요"
}
```
- **Response (201 Created)**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "rating": 4,
    "Content": "배송이 빨라서 좋았어요",
    "createdAt": "2026-02-26T00:00:00.3683013",
    "updatedAt": "2026-02-26T00:00:00.3683013"
  },
  "error": null
}
```


## 2. 리뷰 리스트 조회 (Get Review List)
시스템에 등록된 전체 리뷰를 조회합니다. (주로 상품 상세 페이지나 관리자 페이지에서 사용)

- **Endpoint**: `POST` `/api/reviews`

- **Response (200 OK)**

```json
{
"success": true,
"data": {
"content": [
{
"id": 1,
"orderNumber": "ORD-75362C9A",
"customerName": "수지",
"productName": "딸기",
"rating": 4,
"content": "배송이 빨라서 좋았어요",
"createdAt": "2026-02-26T00:00:00.368301",
"updatedAt": "2026-02-26T00:00:00.368301"
}
],
"empty": false,
"first": true,
"last": true,
"number": 0,
"numberOfElements": 1,
"pageable": {
"offset": 0,
"pageNumber": 0,
"pageSize": 10,
"paged": true,
"sort": {
"empty": false,
"sorted": true,
"unsorted": false
},
"unpaged": false
},
"size": 10,
"sort": {
"empty": false,
"sorted": true,
"unsorted": false
},
"totalElements": 1,
"totalPages": 1
},
"error": null
}
```

## 3. 리뷰 상세 조회 (Get Review Detail)
특정 리뷰의 전체 내용을 상세히 확인합니다.

- **Endpoint**: `POST` `/api/reviews/{reviewId}`

- **Response (200 OK)**

```json
{
  "success": true,
  "data": {
    "productName": "딸기",
    "customerName": "지수",
    "customerEmail": "jisu@abc.com",
    "createdAt": "2026-02-25T19:17:05.173837",
    "rating": 4,
    "content": "배송이 빨라서 좋았어요"
  },
  "error": null
}
```

## 4. 리뷰 삭제 (Delete Review)
부적절한 내용이나 허위 리뷰를 관리자가 강제로 삭제합니다.

- **Endpoint**: `POST` `/api/admins/{adminId}/reviews/{reviewId}`
- **Response (204 No Content)**
