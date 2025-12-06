# TFT(롤토체스) 커뮤니티 플랫폼 - Frontend

![JavaScript](https://img.shields.io/badge/JavaScript-ES6+-yellow) ![HTML5](https://img.shields.io/badge/HTML5-E34F26) ![CSS3](https://img.shields.io/badge/CSS3-1572B6)

> **롤토체스(TFT) 유저들을 위한 전적 검색 및 커뮤니티 플랫폼**

<br/>

## ✨ Key Features
- **📊 전적 시각화**: 유저의 최근 승률, 전적 기록, 가장 많이 선택한 챔프 정보를 시각화하여 제공합니다.
- **🗣️ 커뮤니티**: 덱 추천, 팁 공유, 자유 게시판 등 유저 간 실시간 소통 기능을 제공합니다.
- **🔍 전적 검색**: 소환사 이름을 입력하여 실시간 게임 데이터와 과거 기록을 조회할 수 있습니다.
- **📱 반응형 디자인**: PC와 모바일 환경 모두에 최적화된 UI/UX를 제공합니다.

<br/>

## 🛠 Tech Stack
외부 프레임워크(React, Vue 등) 없이 **DOM 조작과 상태 관리의 본질**을 이해하기 위해 순수 자바스크립트를 사용했습니다.

| Category | Tech |
| :-- | :-- |
| **Language** | JavaScript (ES6+), HTML5, CSS3 |
| **HTTP Client** | Fetch API (Native) |
| **Build/Bundle** | Webpack (또는 Vite) |

<br/>

// 이미지 첨부 예정
## 📸 Screenshots
| 전적 검색 / 대시보드 | 커뮤니티 게시판 |
| :--: | :--: |
| <img src="이미지경로/stats_dashboard.png" width="400" alt="전적화면" /> | <img src="이미지경로/community_list.png" width="400" alt="게시판" /> |

<br/>

## 📂 Folder Structure
컴포넌트 기반 프레임워크의 구조를 Vanilla JS에 모방하여 모듈화된 구조를 설계했습니다.

```bash
community_front/
├─ src/
│  ├─ assets/                     # 공통 정적 리소스 (이미지 등)
│  │  ├─ background.jpg           # 게시글/프로필 배경 이미지
│  │  ├─ penguknight_classic.png  # 상단 좌측 펭구 기사 아이콘
│  │  └─ poro.jpg                 # 전역 프로필 아바타 이미지
│  │
│  ├─ login/
│  │  ├─ login.html               # 로그인 페이지
│  │  ├─ login.css
│  │  └─ login.js
│  │
│  ├─ sign-up/
│  │  ├─ sign-up.html             # 회원가입 페이지
│  │  ├─ sign-up.css
│  │  └─ sign-up.js
│  │
│  ├─ posts/
│  │  ├─ posts.html               # 게시글 목록 페이지
│  │  ├─ posts.css
│  │  └─ posts.js
│  │
│  ├─ post/
│  │  ├─ post.html                # 게시글 상세 페이지
│  │  ├─ post.css
│  │  └─ post.js
│  │
│  ├─ make-post/
│  │  ├─ make-post.html           # 게시글 작성 페이지
│  │  ├─ make-post.css
│  │  └─ make-post.js
│  │
│  ├─ edit-post/
│  │  ├─ edit-post.html           # 게시글 수정 페이지
│  │  ├─ edit-post.css
│  │  └─ edit-post.js
│  │
│  ├─ profile/
│  │  ├─ profile.html             # 프로필/전적 페이지
│  │  ├─ profile.css
│  │  └─ profile.js
│  │
│  ├─ edit-profile/
│  │  ├─ edit-profile.html        # 회원정보 수정 페이지
│  │  ├─ edit-profile.css
│  │  └─ edit-profile.js
│  │
│  ├─ edit-password/
│  │  ├─ edit-password.html       # 비밀번호 변경 페이지
│  │  ├─ edit-password.css
│  │  └─ edit-password.js
│  │
│  ├─ logout/
│  │  └─ logout.js                # 로그아웃 처리 스크립트
│  │
│  └─ profile-dropdown/
│     └─ profile-dropdown.js      # 우측 상단 프로필 드롭다운 공통 로직
