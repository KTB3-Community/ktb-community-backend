# TFT(롤토체스) 커뮤니티 플랫폼 - Backend

![Java](https://img.shields.io/badge/Java-17-red) ![Spring Boot](https://img.shields.io/badge/SpringBoot-3.0-green) ![MySQL](https://img.shields.io/badge/MySQL-8.0-blue) ![Spring Security](https://img.shields.io/badge/Spring_Security-6.0-6DB33F)


<br/>

## 🏛 System Architecture & ERD
### ERD (Entity Relationship Diagram)
유저, 게시글, 댓글, 전적 데이터 간의 관계를 설계했습니다.
![ERD 이미지](이미지경로/erd.png)

<br/>

## 🛠 Tech Stack
| Category | Technology |
| :-- | :-- |
| **Language** | Java 17 |
| **Framework** | Spring Boot 3.x |
| **Database** | MySQL 8.0, Spring Data JPA |
| **Security** | Spring Security, JWT (또는 Session) |
| **Testing** | **Spring Test, JUnit5, Mockito** |
| **External API** | Riot Games API (TFT Data) |

<br/>

## 🧪 Testing Strategy
안정적인 서비스 운영을 위해 **단위 테스트(Unit Test)**와 **통합 테스트(Integration Test)**를 적극적으로 작성했습니다.
- **Unit Test**: Mockito를 활용하여 Service 계층의 비즈니스 로직(회원가입, 전적 갱신 등)을 독립적으로 검증
- **Controller Test**: `MockMvc`를 사용하여 API 엔드포인트 요청/응답 검증
- **Repository Test**: `@DataJpaTest`를 사용하여 쿼리 메서드 및 JPA 동작 검증

```java
// 테스트 코드 예시 (Service Layer)
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Test
    void 회원가입_성공_데이터_검증() {
        // ... Given, When, Then 패턴 기반 테스트 작성
    }
}
