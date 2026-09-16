# BEENTTEUM PoC

## 0. Coding Test - Lv.0

### 두 수의 곱

#### 문제 설명
두 정수 num1, num2가 주어질 때 두 수의 곱을 반환한다.

#### Requirements
- 두 개의 정수를 입력받는다.
- 입력받은 두 수를 곱한다.
- 계산된 결과를 반환한다.

#### 입력값
- num1: 첫 번째 정수
- num2: 두 번째 정수

#### 출력값
- num1 * num2의 결과

#### 풀이 Flow
- num1, num2 입력
- ↓
- 두 값을 곱함
- ↓
- 결과 반환

#### Business Logic
1. num1과 num2를 입력받는다.
2. num1 * num2를 계산한다.
3. 계산 결과를 반환한다.

#### Code
```python
def solution(num1, num2):
    return num1 * num2
```

#### Test Case
- 입력: num1 = 3, num2 = 4 / 출력: 12
- 입력: num1 = 27, num2 = 19 / 출력: 513

#### 핵심 포인트
- 입력값 두 개를 받아 연산한 뒤 결과를 반환하는 가장 기본적인 함수 구조를 이해한다.

---
## 1. PoC 기능 개발

## 1. PoC 기능 개발: 좌석·공간 특성 필터링 검색

### [SPEC-01] 요구사항 명세 (Requirements)

사용자가 설정한 공간 편의 조건(콘센트 여부, 저소음 집중 환경, 와이파이 제공)을 기준으로 유효한 카페 목록을 조회한다.

- **다중 조건 연산**: 선택한 조건들을 모두 만족(AND)하는 카페만 선별한다.
- **유연한 파라미터 처리**: 필터 조건을 선택하지 않으면 전체 카페 목록을 반환한다.
- **무결과 처리**: 조건을 만족하는 대상이 없더라도 오류가 아닌 빈 배열(`[]`)과 함께 `200 OK`를 전달한다.
- **비정상 입력 처리**: `Boolean` 타입으로 변환할 수 없는 요청 파라미터가 입력되면 Spring MVC의 타입 변환 검증에 의해 `400 Bad Request`로 처리한다.


| 필드명 | 타입 | 필수 여부 | 설명 |
| :--- | :--- | :--- | :--- |
| `needOutlet` | Boolean | Optional | 콘센트 구비 좌석 필터 |
| `quietOnly` | Boolean | Optional | 집중 가능한 저소음 환경 필터 |
| `needWifi` | Boolean | Optional | 매장 전용 무선 인터넷 필터 |


---

### [FLOW-01] 시스템 처리 흐름 (Execution Flow)

```text
사용자가 카페 필터 검색 요청
↓
입력값 확인
├─ 잘못된 값 입력 → 검색 실패 (400 Bad Request)
└─ 정상 입력
    ↓
등록된 카페 목록 가져오기
↓
선택한 조건과 카페 정보 비교
├─ 콘센트 필요 여부 확인
├─ 조용한 분위기 여부 확인
└─ 와이파이 필요 여부 확인
↓
조건에 맞는 카페만 선별 및 태그 추가
↓
결과 반환
├─ 일치 카페 있음 → 카페 목록 반환
└─ 일치 카페 없음 → 빈 목록([]) 반환
```
---

### [LOGIC-01] 상세 비즈니스 규칙 (Core Business Rules)

1. **조건 누락 허용 (Null-safe Filtering)**: 전달된 쿼리 파라미터가 null인 경우, 해당 속성의 참/거짓 여부와 무관하게 모든 대상을 검사 통과 처리한다.

2. **매칭 태그 동적 주입**: 사용자가 찾은 조건과 일치하는 항목을 카페 정보에 태그(matchedTags)로 붙여서 보여준다.

3. **요청 파라미터 유효성 검증**: 정의되지 않은 형식이거나 Boolean 변환이 불가능한 파라미터 유입 시 프레임워크 레벨에서 차단하여 400 Bad Request를 반환한다.

---

### [POC-SCOPE] 검증 범위 및 설계 기준

* **포함 범위**: 더미 데이터를 활용해 3가지 조건(콘센트, 조용함, 와이파이)을 조합해 찾아내는 필터링 로직 검증.

* **제외 범위**: 실제 지도 위치 기반 검색, 혼잡도 연동, DB 연결.
---

### [API-SPEC] 인터페이스 정의

* **엔드포인트**: `GET /api/cafes/filter`

* **호출 예시**: `GET /api/cafes/filter?needOutlet=true&quietOnly=true`

### [API-SPEC] 인터페이스 정의

- **엔드포인트**: `GET /api/cafes/filter`
- **호출 예시**: `GET /api/cafes/filter?needOutlet=true&quietOnly=true`

#### 테스트용 카페 데이터 구성 (Mock Data)

필터링 로직 검증을 위해 등록해 둔 4곳의 카페 데이터 기준

| 매장명 | 콘센트(`hasOutlet`) | 조용한 분위기(`isQuiet`) | 와이파이(`hasWifi`) | 특징 |
| :--- | :---: | :---: | :---: | :--- |
| **카페 A** | O | O | O | 모든 조건을 다 갖춘 집중형 카페 |
| **카페 B** | O | X | O | 콘센트와 와이파이는 있지만 대화가 많은 분위기 |
| **카페 C** | X | O | O | 조용하지만 콘센트 좌석은 없는 카페 |
| **카페 D** | X | X | X | 휴식 중심의 기본 카페 |

#### 정상 응답 결과 (200 OK)

```json
[
  {
    "id": 1,
    "name": "카페 A",
    "hasOutlet": true,
    "isQuiet": true,
    "hasWifi": true,
    "matchedTags": [
      "콘센트 좌석 확보",
      "조용한 집중 환경"
    ]
  }
]
```

**예외 응답 결과 (400 Bad Request)**

* 요청 형태: `GET /api/cafes/filter?needOutlet=invalid`
* 처리 방식: Spring MVC의 쿼리 파라미터 타입 변환 실패로 400 Bad Request 상태 코드 반환
[cite: 1]
---

### [TEST-REPORT] 단위 테스트 명세 및 검증

CafeController의 필터링 기능과 비정상 요청 에러 처리를 검증하였다.

* **TC-01 (정상 - 전체 조회)**: 아무 조건도 고르지 않았을 때 등록된 카페 4곳이 전부 잘 나오는지 확인 (성공)
* **TC-02 (정상 - 단일 조건)**: 콘센트 있는 카페만 골랐을 때 해당 카페(카페 A, B)만 잘 걸러지는지 확인 (성공)
* **TC-03 (정상 - 복합 조건)**: 콘센트도 있고 조용한 카페를 골랐을 때 두 조건 다 맞는 곳(카페 A)이 잘 나오는지 확인 (성공)
* **TC-04 (정상 - 검색 결과 없음)**: 조건에 맞는 카페가 하나도 없을 때 에러 안 나고 빈 목록([])으로 잘 끝나는지 확인 (성공)
* **TC-05 (예외 - 잘못된 요청 처리)**: 참/거짓 대신 이상한 글자를 입력했을 때 400 에러(Bad Request)로 잘 막아내는지 확인 (성공)

* **테스트 결과**: BUILD SUCCESSFUL

---

### [RUN-GUIDE] 환경 구성 및 실행 명령

* **개발 환경**: OpenJDK 17, Spring Boot 3.x

**단위 테스트 실행**
* Mac / Linux: `./gradlew test`
* Windows: `.\gradlew.bat test`

**로컬 엔드포인트 호출**
* `http://localhost:8080/api/cafes/filter?needOutlet=true&quietOnly=true`$



