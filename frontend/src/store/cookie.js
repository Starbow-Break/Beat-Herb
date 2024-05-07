import { Cookies } from 'react-cookie';

const cookies = new Cookies();

// Refresh Token 저장
export const setRefreshToken = (refreshToken, expire) => {
  const expireDate = new Date(expire * 1000);
  return cookies.set(
    "refreshToken", 
    refreshToken,
    {
      path: "/",
      expires: expireDate,
    }
  );
}

// 쿠키에 저장한 Refresh Token 받기
export const getRefreshToken = () => {
  return cookies.get("refreshToken");
}

// Refresh Token 제거
export const removeRefreshToken = () => {
  return cookies.remove("refreshToken", { path: '/' });
}