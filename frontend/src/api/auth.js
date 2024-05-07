// 구글 로그인
export const loginWithGoogle = () => {
  const apiKey = process.env.REACT_APP_REST_API_KEY_GOOGLE;
  const redirectUri = process.env.REACT_APP_REDIRECT_URI_GOOGLE;
  const googleUrl = `https://accounts.google.com/o/oauth2/auth?client_id=${apiKey}&redirect_uri=${redirectUri}&response_type=code&scope=openid email profile`
  window.location.href = googleUrl
}

// 네이버 로그인
export const loginWithNaver = () => {
  const apiKey = process.env.REACT_APP_REST_API_KEY_NAVER;
  const redirectUri = process.env.REACT_APP_REDIRECT_URI_NAVER;
  const naverUrl = `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${apiKey}&redirect_uri=${redirectUri}&state=1234`
  window.location.href = naverUrl;
}

// 카카오 로그인
export const loginWithKakao = () => {
  const apiKey = process.env.REACT_APP_REST_API_KEY_KAKAO;
  const redirectUri = process.env.REACT_APP_REDIRECT_URI_KAKAO;
  const kakaoUrl = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${apiKey}&redirect_uri=${redirectUri}&scope=openid`;
  window.location.href = kakaoUrl;
}

// 이메일을 직접 입력해서 로그인
export const loginWithEmail = (email) => {
  const encoded_email = btoa(email);
  const redirectUri = `/auth/redirect/email?encoded=${encoded_email}`;
  window.location.href = redirectUri;
}