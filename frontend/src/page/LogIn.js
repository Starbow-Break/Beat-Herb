// 로그인 페이지
import { useEffect, useState } from "react";
import { loginWithEmail } from "../api/auth";
import { useNavigate } from "react-router-dom";
import { useAuthStore } from "../store/AuthStore";

export default function LogIn() {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const { accessToken } = useAuthStore();

  useEffect(() => {
    if(accessToken) {
      alert("이미 로그인된 상태입니다.");
      navigate(-1);
      return;
    }
  });

  // 이메일 정규표현식
  const emailExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
  const isValid = emailExp.test(email);

  return (
    <div className="absolute w-full right-1/2 bottom-1/2 translate-x-1/2 translate-y-1/2">
      <div className="flex flex-col w-full items-center justify-center">
        <p className="text-primary text-5xl my-10">BeatHerb에 오신 것을 환영합니다.</p>
        <div className="flex items-center justify-center w-full text-base-content">
          <div className="divider divider-primary w-10/12 text-2xl my-10">이메일로 로그인하기</div>
        </div>
        <input
          className={"input input-bordered w-1/3 join-item text-base-content"+(isValid || !email ? "" : " input-error")}
          placeholder="이메일을 입력해주세요"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <p className={"text-error mt-2 mb-1 text-sm" + (isValid || !email ? " invisible" : "")}>이메일 형식이 올바르지 않습니다.</p>
        <button disabled={!isValid} onClick={() => loginWithEmail(email)} className="btn btn-primary w-4/12 items-center my-2">이메일로 로그인</button>
      </div>
    </div>
  );
}
