// 제목이 곁들어진 아이템 컨테이너

import { useNavigate } from "react-router-dom";

export default function ItemContainerWithTitle({ children, title, link, data, scrolled = false }) {
  const navigate = useNavigate();

  // 스크롤 활성화 시 추가할 tailwindcss 클래스
  const scrollClass = " scrollbar scrollbar-thin scrollbar-thumb-rounded-full scrollbar-track-rounded-full scrollbar-thumb-base-200 hover:scrollbar-thumb-primary overflow-x-scroll pb-2";

  const handleOnClick = () => {
    navigate(link, { state: { ...data } });
  }

  return (
    <div className="w-full p-8">
      <div className="flex text-base-content gap-16 mb-4">
        <p className="text-3xl font-semibold">{title}</p>
        <button onClick={handleOnClick}>바로가기</button>
      </div>
      <div className="w-full">
        <div className={"flex gap-8" + (scrolled ? scrollClass : "")}>
          { children }
        </div>
      </div>
    </div>
  );
}