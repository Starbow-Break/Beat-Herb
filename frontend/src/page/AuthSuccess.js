// 인증 중입니다 페이지
// 사용자가 인증 메일을 받고 버튼을 누르면 이 페이지로 리다이렉트됨
// 사용자가 쿼리스트링으로 보내준 토큰을 파싱해서 accessToken과 refreshToken을 저장

import { Link } from "react-router-dom";

export default function AuthSuccess() {
  return (
    <div className="absolute w-full right-1/2 bottom-1/2 translate-x-1/2 translate-y-1/2 space-y-32">
      <p className="text-primary text-6xl">인증 되었습니다.</p>
      <p className="text-base-content text-xl">프로필을 완성해주세요.</p>
      <div>
        <Link as="button" to="/useredit" className="text-primary-content hover:text-primary-content">
          <button className="btn btn-primary w-1/6">정보 수정</button>
        </Link>
      </div>
    </div>
  );
}