// 상단 네비게이션 바

import UserButton from "./UserButton";
import { Link } from "react-router-dom";

// 게시판 탭 드롭다운에 들어갈 메뉴들
const boardMenus = [
  { key: "all", title: "전체", href: "/board/all" },
  { key: "contents", title: "컨텐츠", href: "/board/contents" },
  { key: "live", title: "라이브", href: "/board/live" },
];

export default function NavBar() {
  return (
    <div className="navbar bg-base-100 text-base-content">
      <div className="navbar-start">
        <Link to="/" className="btn btn-ghost text-xl text-primary hover:text-primary">
          BeatHerb
        </Link>
        <ul className="menu menu-horizontal px-1">
          <li>
            <Link to="/" className="text-base-content hover:text-base-content">
              Home
            </Link>
          </li>
          <li>
            <details>
              <summary>게시판</summary>
              <ul className="bg-base-200 w-32 p-2 z-10">
                {boardMenus.map((menu) => (
                  <li key={menu.title}>
                    <Link to={menu.href} className="text-base-content hover:text-base-content">
                      {menu.title}
                    </Link>
                  </li>
                ))}
              </ul>
            </details>
          </li>
          <li>
            <Link to="/workplace" className="text-base-content hover:text-base-content">
              작업실
            </Link>
          </li>
        </ul>
      </div>
      <div className="navbar-end">
        <UserButton />
      </div>
    </div>
  );
}
