// 게시판 페이지

import NavBar from "../components/NavBar";
import { useParams } from "react-router";
import { Link, Navigate } from "react-router-dom";
import ContentsBoard from "./ContentsBoard";
import LiveBoard from "./LiveBoard";
import AllBoard from "./AllBoard";

// 사이드바에 들어갈 메뉴들
const sideBarMenus = [
  { key: "all", title: "전체", href: "/board/all"},
  { key: "contents", title: "컨텐츠", href: "/board/contents"},
  { key: "live", title: "라이브", href: "/board/live"},
]

export default function Board() {
  const { category } = useParams();
  let children = null; // 하위 항목

  switch (category) {
    case "all":
      children = <AllBoard />;
      break;
    case "contents":
      children = <ContentsBoard />;
      break;
    case "live":
      children = <LiveBoard />;
      break;
    default:
      alert("잘못된 접근입니다.");
      return <Navigate to="/" />;
  }

  return (
    <>
      <div className="fixed top-0 w-full z-10">
        <NavBar />
      </div>
      <div className="drawer drawer-open">
        <input id="my-drawer-2" type="checkbox" className="drawer-toggle" />
        <div className="drawer-content flex flex-col items-center justify-center mt-[64px]">
          { children }
        </div>
        <div className="drawer-side">
          <label htmlFor="my-drawer-2" aria-label="close sidebar" className="drawer-overlay" />
          <ul className="menu p-4 w-80 min-h-full bg-base-200 text-base-content pt-[78px]">
            {
              sideBarMenus.map((menu) => (
                <li key={menu.key}>
                  <Link to={menu.href} className="text-3xl p-4 text-base-content hover:text-base-content">{menu.title}</Link>
                </li>
              ))
            }
          </ul>
        </div>
      </div>
    </>
  );
}