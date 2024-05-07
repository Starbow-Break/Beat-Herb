// 인가코드를 받으면 여기로 리다이렉트 됨
// 현재는 인가코드를 받아서 보내는 부분만 구현됨, 향후 소켓도 보낼 수 있도록 작성하기

import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { setRefreshToken } from "../store/cookie";
import { useAuthStore } from "../store/AuthStore";

import axios from "axios";

export default function AuthEmailRedirection() {
  const navigate = useNavigate();
  const { setAccessToken, setNickname, setUserId, setSocket } = useAuthStore();
  
  // 비동기 작업을 동기로 바꿔주기 위해, async await 적용 생각해 볼 수 있음
  useEffect(() => {
    // 인가 코드
    const serverURL = process.env.REACT_APP_TEST_SERVER_BASE_URL;
    const token = new URL(window.location.href).searchParams.get("token");
    axios({
      method: 'get',
      url: `${serverURL}/api/auth/verify?token=${token}`,
    })
    .then((response) => {
      const { accessToken, refreshToken, refreshTokenExpiresIn, nickname, id, socket } = response.data;
      setAccessToken(accessToken);
      setRefreshToken(refreshToken, refreshTokenExpiresIn);
      setNickname(nickname);
      setUserId(id);
      setSocket(socket);
      navigate(`/mypage/${(id)}`);
      console.log(socket);
    })
    .catch((error) => {
      console.log(error);
      alert("로그인에 실패했습니다");
      navigate("/login");
    })
  }, []);

  return (
    <div className="absolute w-full right-1/2 bottom-1/2 translate-x-1/2 translate-y-1/2">
      <span className="loading loading-ring w-32 h-32 text-primary" />
    </div>
  );
}