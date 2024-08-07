# BeatHerb
### SSAFY 10기 공통 프로젝트 서울 5반 :musical_score: 'Bit는 알아도 Beat는 모른다' :musical_score: <br /> (2024.01.08 ~ 2024.02.16)

![beatherb](https://github.com/Starbow-Break/Beat-Herb/assets/103927296/15b40eaf-dccf-4dea-a63f-d8dae463f49c)

## :green_book: 목차 :green_book:
:one: [프로젝트 소개](#1-프로젝트-소개) <br/>
:two: [개발 환경](#2-개발-환경)<br/>
:three: [서비스 화면](#3-서비스-화면) <br/>
:four: [주요 기능](#4-주요-기능) <br/>
:five: [프로젝트 특징 기술 소개](#5-프로젝트-특징-기술-소개) <br/>
:six: [설계 문서](#6-설계-문서) <br/>
:seven: [팀원 소개](#7-팀원-소개) <br/>

## 1. 프로젝트 소개
### :scroll: Overview :scroll:
#### "음악 제작? 어렵게 생각하지 마세요! BeatHerb가 도와드립니다."
##### 피아노 반주에 키보드 소리를 입혀보거나 잔잔한 멜로디에 고양이 울음 소리를 입혀보거나 마음껏 소리를 만들어보세요. <br /> 내가 만든 소리는 업로드와 라이브를 통해 BeatHerb 사용자들과 공유할 수 있습니다.

## 2. 개발 환경
### :books: Tech Stack :books:
#### Backend Framework
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![SpringBoot](https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white)
![OpenVidu](https://img.shields.io/badge/OpenVidu-yellow?style=for-the-badge&logo=LiveChat&logoColor=white)
![Socket.io](https://img.shields.io/badge/WebSocket-blue?style=for-the-badge&logo=Socket.io)
![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-000?style=for-the-badge&logo=apachekafka)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Jenkins](https://img.shields.io/badge/jenkins-%232C5263.svg?style=for-the-badge&logo=jenkins&logoColor=white)
![SpringDataJpa](https://img.shields.io/badge/Spring_Data_Jpa-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
#### Frontend Framework
![React](https://img.shields.io/badge/react-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB)
![TailwindCSS](https://img.shields.io/badge/tailwindcss-%2338B2AC.svg?style=for-the-badge&logo=tailwind-css&logoColor=white)
![ffmpeg](https://img.shields.io/badge/ffmpeg-007808?style=for-the-badge&logo=ffmpeg)
![Tone.js](https://img.shields.io/badge/Tone.js-red?style=for-the-badge&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAMAAABEpIrGAAAAYFBMVEX6Jdn9INn/FNrFW9P2KNn7I9nhPdZwocoA2MHzJdn/HNqagM4hzcMA4b8A28DBXtNBv8UA3cBupMkA3MAA37+YhM4S0sLlONd4ncqlec/LVNRJucZ8msscz8JFvMWffc8w6ozjAAAAm0lEQVR4AdXOxxEDMQxDUYWNyjTknPrv0mIBGF9tXvnmD8w/nv3ydn4y/Ga3rNs+03fYY8qFAmtryyIEaP2ADhAw6sdTFjBgQ21dAAK0XjrAwNiudQ50O8CADUY6wMDszhcDUBDMNWUOrLvdu4CBOZwfJQMMhP36zAIGrPMvfRMwtq+lAwyE/a11Dm5aBwMoq2idA3QBAeR+AXwA1o8n4DQ388IAAAAASUVORK5CYII=)
![Wavesurfer.js](https://img.shields.io/badge/Wavesurfer.js-orange?style=for-the-badge&logo=data:image/png;base64,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)
![ffmpeg.wasm](https://img.shields.io/badge/ffmpeg.wasm-007808?style=for-the-badge&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAMAAABEpIrGAAAAilBMVEUAAABVVf9gQP9gQN8AAP9VVf9lTfBlTvBlTu9lTvBlTfBdRuhjTu9lTvBVKtRkTvBlTvBjTu5jTvNlTe9lTvFgSO9lTe5jS/NkTfBlTfBlTvBkTfFkTPBkTfBlTvBlTvBjR/FkTe5kTfBlTe9mTe5kTe9jTOxkTe9kTfFlTfFkTe9kTe9mS/NjS+4OknaJAAAALnRSTlMABggIAQN38Pb1uQs+3wbv/0s+8bggTFXJ8f6RVGbxyBKFhX5noDaSkjWRylVLpIxVKAAAAOBJREFUeAG900UWgwAMRdHgDr/u7rb/7bXBT6jbm97ggf6VonKaTpyhJimFm5bNOa5HRH5gJ1lm5mGEtBpRvYGsKKQkvYkkq1V2uDqlee1OF0Cvzo7+YDgaA5hMKU+f9Quf69PJi06LB65YV1xfrtbZgGoDI+nzPgKjNDAUzndlq6WBTcXFAKouB6TLAeG9rRiQXh+WBoxAPN/FaVQaoN1qKZ2f2lKo6Ir3F1UXX+1FH4+G+4s3Esfh2PYy111k8fEtS65cGJWdTmJps7XnAp83tOmItU9+HM4gTtfkj/PbzofFKTJo5uJ5AAAAAElFTkSuQmCC)
![waveform_playlist](https://img.shields.io/badge/waveform_playlist-green?style=for-the-badge&logo=waveform_playlist)
#### DB
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)
#### Management Tool
![Notion](https://img.shields.io/badge/Notion-%23000000.svg?style=for-the-badge&logo=notion&logoColor=white)
![Jira](https://img.shields.io/badge/jira-%230A0FFF.svg?style=for-the-badge&logo=jira&logoColor=white)
![GitLab](https://img.shields.io/badge/gitlab-orange.svg?style=for-the-badge&logo=gitlab&logoColor=white)
![Figma](https://img.shields.io/badge/figma-%23F24E1E.svg?style=for-the-badge&logo=figma&logoColor=white)
![Mattermost](https://img.shields.io/badge/Mattermost-blue?style=for-the-badge&logo=Mattermost)

## 3. 서비스 화면
#### 홈 화면 & 로그인 화면
|<img alt="home" src="https://github.com/Starbow-Break/Beat-Herb/assets/103927296/5bead36f-9f06-4576-a50e-ea1b73dacbbf" width="500" height="250">|<img alt="login" src="https://github.com/Starbow-Break/Beat-Herb/assets/103927296/f6e1f9df-9f1c-4cb6-9cce-b2d7a2633e7c" width="500" height="250">|
|:---:|:---:|
|**홈 화면**|**로그인 화면**|

#### 작업실 화면 (소리 쌓기 화면)
|<img alt="work1" src="https://github.com/Starbow-Break/Beat-Herb/assets/103927296/7c6e66ea-0b11-4836-b0b8-449fbd1cd9b6" width="500" height="250">|<img alt="work2" src="https://github.com/Starbow-Break/Beat-Herb/assets/103927296/9847f0d1-4b61-4fdd-bcee-5b1a623ce72dc" width="500" height="250">|
|:---:|:---:|
|**내 컴퓨터에서 음원 파일 가져오고 BeatHerb에서 <br />마음에 드는 소리를 쌓은 후 업로드하는 화면**|**BeatHerb에서 마음에 드는 소리를 가져오고 <br />가상 악기로 만든 소리를 쌓은 후 업로드하는 화면**|
#### 컨텐트 상세 검색 화면
|<img alt="detail_search" src="https://github.com/Starbow-Break/Beat-Herb/assets/103927296/9a91cb21-0f80-438c-81d6-b747f7794715" width="500" height="250">|
|:---:|
|**컨텐트 상세 검색 화면**|

#### 게시판 화면
|<img alt="content" src="https://github.com/Starbow-Break/Beat-Herb/assets/103927296/defb51fc-1946-475d-9a2f-9e69779a8158" width="500" height="250">|<img alt="live" src="https://github.com/Starbow-Break/Beat-Herb/assets/103927296/19d4e8ea-e2ee-46c7-94ec-16994fa2c917" width="500" height="250">
|:---:|:---:|
|**전체 컨텐트 게시판 화면**|**라이브 게시판 화면**|

#### 라이브 화면
|<img alt="livecat" src="https://github.com/Starbow-Break/Beat-Herb/assets/103927296/adbcfec3-fa59-46a3-a08e-ca7414b2da62" width="500" height="250">|
|:---:|
|**라이브 화면**|

## 4. 주요 기능
#### 1. 소리 쌓기 서비스 :sound:
##### 기존에 등록되어 있는 음반 정보를 가져와서 그 음반 위에 소리를 쌓을 수 있습니다.<br/><br/> ex)<br/>1-1). 피아노 연주가 나오는 멜로디 정보를 가져와서 보컬 소리를 쌓을 수 있음.<br/>1-2). 원하는 보컬 정보를 가져온 후 piano, synth, drum과 같은 가상악기를 통해 소리를 녹음하여 쌓을 수 있음.
![소리쌓기서비스](/uploads/26068053b2240e8174cb0f394978950c/drum.mp4)

#### 2. 라이브를 활용해 커뮤니티로 확장 :tv:
##### 라이브에서 공유하는 컨텐츠에 관심 있는 사람은 누구나 들어와서 라이브 방송에 참여 할 수 있습니다.
![라이브](https://github.com/Starbow-Break/Beat-Herb/assets/103927296/a5b08ffe-97c0-4a51-8e63-c0bcb79f43c1)

#### 3. 해시태그를 이용한 상세 검색 :computer:
##### 피아노, 드럼, MR 등과 같은 해시태그를 기반으로 정확한 검색 결과를 얻을 수 있습니다.
![해시태그를 이용한 상세검색](https://github.com/Starbow-Break/Beat-Herb/assets/103927296/5e3aa269-9663-427c-bfea-5480e11c9840)

## 5. 프로젝트 특징 기술 소개
#### 1. kafka 도입을 활용한 멀티 프로세싱 음악 처리 후 사용자는 업로드가 되었다는 신호를 보다 빠르게 제공 받을 수 있습니다.<br/> :arrow_forward: (약 10초 이상 -> 약 3초)
#### 2. hls 기술을 이용한 실시간 음악 스트리밍
#### 3. Openvidu 를 활용한 라이브
#### 4. 가상 악기를 이용하여 음악 생성 후 업로드 가능

## 6. 설계 문서
#### API
<details>
<summary>Member</summary>
![member-controller](/uploads/86e6f8888f3eac80ad12acf659a6f1da/member-controller.PNG)
![member-info-controller](/uploads/026d9e72b03460ba22c0b2fc06469c69/member-info-controller.PNG)
</details>

<details>
<summary>Auth</summary>
![auth-controller](/uploads/42268e797cbc6c283b823d3d603cbae0/auth-controller.PNG)
</details>

<details>
<summary>HashTag</summary>
![hash-tag-controller](/uploads/e0240fb282ab5d936eff92c94743766f/hash-tag-controller.PNG)
</details>

<details>
<summary>Live</summary>
![live-controller](/uploads/ff3abc8893837f545349fa3cfdb2bc8b/live-controller.PNG)
</details>

<details>
<summary>Content</summary>
![content-controller](/uploads/6d540e30751dde097ae2cc04b85d994a/content-controller.PNG)
![content-load-controller](/uploads/8c5c7836b0c711ece760ab49374d3555/content-load-controller.PNG)
![content-detail-controller](/uploads/f468c84a4fb8da9c07cb2f666066113f/content-detail-controller.PNG)
![content-search-controller](/uploads/808b950a4edca9ea9f56f31d164d669d/content-search-controller.PNG)
</details>

<details>
<summary>Follow</summary>
![follwer-controller](/uploads/5ce9a9ec610a347d1176252644928545/follwer-controller.PNG)
![following-controller](/uploads/7fb20754acfb80ac812a9f9bf6368502/following-controller.PNG)
</details>

<details>
<summary>DirectMessage</summary>
![direct-message-controller](/uploads/2730867f590f29ceb3f3c7ddbbab970e/direct-message-controller.PNG)
</details>

<details>
<summary>Interest</summary>
![interest-controller](/uploads/a84fb3ecd8aae38831f0b7df35792f41/interest-controller.PNG)
</details>

<details>
<summary>Star</summary>
![star-controller](/uploads/1adc9a1ec22d8c2692cabedbd4d110a1/star-controller.PNG)
</details>

<details>
<summary>Comment</summary>
![comment-controller](/uploads/302ac002cb85debddd38b7072f5221f5/comment-controller.PNG)
</details>

#### ERD
![BeatHerb_erd](https://github.com/Starbow-Break/Beat-Herb/assets/103927296/b8181161-4271-4d19-a61f-67798e55d3a2)

#### Architecture Structure
![architecture](https://github.com/Starbow-Break/Beat-Herb/assets/103927296/19d4e8ea-e2ee-46c7-94ec-16994fa2c917)

## 7. 팀원 소개
|팀장(김도현)|팀원(김범수)|팀원(최지희)|팀원(박도연)|팀원(박세웅)|팀원(유시연)|
|:---:|:---:|:---:|:---:|:---:|:---:|
|<img alt="dohyun" src="https://github.com/Starbow-Break/Beat-Herb/assets/103927296/d22eea46-2f5c-4595-a5e1-bd2daea97bb7" width="500" height="250">|<img alt="bumsoo" src="https://github.com/Starbow-Break/Beat-Herb/assets/103927296/a70ae838-6e9a-45cd-bb1d-7221e0ab68df" width="500" height="250">|<img alt="sihee" src="https://github.com/Starbow-Break/Beat-Herb/assets/103927296/7f1e4fbf-8d67-4a3c-a29e-ff3726151745" width="500" height="250">|<img alt="doyeon" src="https://github.com/Starbow-Break/Beat-Herb/assets/103927296/cce67b93-61aa-4368-b3d0-116fd561c5b4" width="500" height="250">|<img alt="seyoog" src="https://github.com/Starbow-Break/Beat-Herb/assets/103927296/8c773395-932b-468a-8afc-841ec6635fe0" width="500" height="250">|<img alt="siyeon" src="https://github.com/Starbow-Break/Beat-Herb/assets/103927296/3161356c-12ff-4681-8c18-ab8dbe204cd7" width="500" height="250">|
|**Backend Lead/Infra**|**Frontend Lead**|**Full Stack**|**Full Stack**|**Full Stack**|**Full Stack**|
