// 인가코드를 받으면 여기로 리다이렉트 됨
// 현재는 인가코드를 받아서 보내는 부분만 구현됨, 향후 소켓도 보낼 수 있도록 작성하기

import { useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { setRefreshToken } from "../store/cookie";
import { useAuthStore } from "../store/AuthStore";

import axios from "axios";

export default function AuthRedirection() {
  const navigate = useNavigate();
  const { provider } = useParams();
  const { setAccessToken, setName } = useAuthStore();
  const serverURL = process.env.REACT_APP_TEST_SERVER_BASE_URL + "/member";

  // 비동기 작업을 동기로 바꿔주기 위해, async await 적용 생각해 볼 수 있음
  useEffect(() => {
    // 인가 코드
    const code = new URL(window.location.href).searchParams.get("code");
    if (provider === "kakao") {
      
      // 서버로 요청을 보냄
      axios({
        method: 'post',
        url: `${serverURL}/signin/kakao`,
        data: {
          code : code
        }
      })
      .then((response) => {
        const { accessToken, refreshToken, refreshTokenExpiresIn, name } = response.data.data;
        setAccessToken(accessToken);
        setRefreshToken(refreshToken, refreshTokenExpiresIn);
        setName(name);
      })
      .catch((error) => {
        alert("로그인에 실패했습니다.")
        navigate("/");
      })

    } else if (provider === "naver") {
      
      // 네이버 로그인은 state 추가로 필요
      const state = new URL(window.location.href).searchParams.get("state");

      // 서버로 요청을 보냄
      axios({
        method: 'post',
        
        url: `${serverURL}/signin/naver`,
        data: {
          code : code,
          state : state
        }
      })
      .then((response) => {
        const { accessToken, refreshToken, refreshTokenExpiresIn, name } = response.data.data;
        setAccessToken(accessToken);
        setRefreshToken(refreshToken, refreshTokenExpiresIn);
        setName(name);
      })
      .catch((error) => {
        alert("로그인에 실패했습니다.")
        navigate("/");
      })

    } else if (provider === "google") {

      // 서버로 요청을 보냄
      axios({
        method: 'post',
        url: `${serverURL}/signin/google`,
        data: {
          code : code
        }
      })
      .then((response) => {
        const { accessToken, refreshToken, refreshTokenExpiresIn, name } = response.data.data;
        setAccessToken(accessToken);
        setRefreshToken(refreshToken, refreshTokenExpiresIn);
        setName(name);
      })
      .catch((error) => {
        alert("로그인에 실패했습니다.")
        navigate("/");
      })

    } else if (provider === "email") {
      const encoded_email = new URL(window.location.href).searchParams.get("encoded");
      const email = atob(encoded_email);
      
      // 서버로 요청을 보냄
      axios({
        method: 'post',
        url: `${serverURL}/signup`,
        data: {
          email : email
        },
      })
      .then(() => {
        navigate("/auth_email");
      })
      .catch((error) => {
        if(error.response.data.message[0] === "이미 가입한 이메일입니다.") {
          axios({
            method: 'post',
            url: `${serverURL}/signin`,
            data: {
              email : email
            },
          })
          .then((response) => {
            navigate("/auth_email");
          })
          .catch((error) => {
            alert("로그인에 실패했습니다.")
            navigate("/");
          })
        } else {
          alert("로그인에 실패했습니다.")
          navigate("/");
        }
      })
    } else {
      alert("유효하지 않은 접근입니다.");
      navigate("/");
    }    
  }, []);

  return (
    <div className="absolute w-full right-1/2 bottom-1/2 translate-x-1/2 translate-y-1/2">
      <span className="loading loading-ring w-32 h-32 text-primary" />
    </div>
  );
}