// 전체 게시판 페이지 항목

import ItemContainerWithTitle from "../components/ItemContainerWithTitle";
import LiveItem from "../components/LiveItem";
import ContentsRanking from "../components/ContentsRanking";
import SearchBar from "../components/SearchBar";
import ContentsItem from "../components/ContentsItem";
import { useSearchParams } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";
import { creatorListFormatWithoutLink } from "../common/creatorListFormat";

// 탭 리스트
const tabs = [
  { value: "melody", title: "멜로디" },
  { value: "vocal", title: "보컬" },
  { value: "music", title: "음원" },
];

export default function AllBoard() {
  const [searchParams] = useSearchParams();
  const queryParam = searchParams.get("query");
  const query = queryParam ? queryParam : "";

  const hashtagListParam = searchParams.get("hashtagList");
  const hashtagListString = hashtagListParam ? hashtagListParam : "";
  const hashtagIdList = (hashtagListString === "" ? [] : hashtagListString.split(' '));

  const [contents, setContents] = useState({});
  const [liveList, setLiveList] = useState([]);
  const [category, setCategory] = useState("melody");

  useEffect(() => {
    const serverUrl = process.env.REACT_APP_TEST_SERVER_BASE_URL;
    let searchUrl = `${serverUrl}/content/search?title=${query}`

    hashtagIdList.forEach((hashtagId, index) => {
      searchUrl += `&id=${hashtagId}`;
    });

    axios({
      method: "get",
      url: searchUrl,
    })
    .then((response) => {
      console.log(response);
      setContents(response.data.data);
    })
    .catch((error) => {
      alert(error.response.data.message);
    });

    axios({
      method: "get",
      url: `${serverUrl}/live`,
    })
    .then((response) => {
      setLiveList(response.data.data);
    })
    .catch((error) => {
      alert(error.response.data.message);
    });
  }, [query, hashtagListString]);

  let contentView;

  if (query || hashtagListString) {
    let searchList;

    if (category === "melody") {
      searchList = contents.melodyList ? [...(contents.melodyList)].reverse() : [];
    } else if (category === "vocal") {
      searchList = contents.vocalList ? [...(contents.vocalList)].reverse() : [];
    } else {
      searchList = contents.soundTrackList ? [...(contents.soundTrackList)].reverse() : [];
    }

    contentView = (
      <div className="w-full h-full">
        <div className="flex flex-col gap-4 py-4">
          {query && <p className="text-primary text-2xl font-semibold">검색어 : {query}</p>}
          {hashtagIdList.length > 0 && <p className="text-xl">적용된 해시태그 갯수 : {hashtagIdList.length}개</p>}
        </div>
        <div role="tablist" className="tabs tabs-bordered my-8 tabs-lg">
          {tabs.map((tab) => (
            <button
              key={tab.value}
              role="tab"
              className={
                "tab w-1/2 translate-x-1/2" + (category === tab.value ? " tab-active" : "")
              }
              onClick={() => setCategory(tab.value)}
            >
              {tab.title}
            </button>
          ))}
        </div>

        <div className="grid grid-cols-4 gap-4 items-center">
          {searchList.map((content, index) => {
            return (
              <div key={"content" + content.id} className="flex justify-center">
                <ContentsItem
                  contentId={content.id}
                  size={150}
                  albumArt={content.image}
                  title={content.title}
                  artist={creatorListFormatWithoutLink(content.creatorList)}
                  showFavorite={false}
                />
              </div>
            );
          })}
        </div>
      </div>
    );
  } else {
    contentView = (
      <>
        <ItemContainerWithTitle title="컨텐츠" link="/board/contents">
          <ContentsRanking
            title="신규 멜로디"
            link="/board/contents"
            data={{ category: "melody" }}
            contentList={contents.melodyList ? [...(contents.melodyList)].reverse().slice(0, 5) : []}
          />
          <ContentsRanking
            title="신규 보컬"
            link="/board/contents"
            data={{ category: "vocal" }}
            contentList={contents.vocalList ? [...(contents.vocalList)].reverse().slice(0, 5) : []}
          />
          <ContentsRanking
            title="신규 음원"
            link="/board/contents"
            data={{ category: "music" }}
            contentList={contents.soundTrackList ? [...(contents.soundTrackList)].reverse().slice(0, 5) : []}
          />
        </ItemContainerWithTitle>
        <ItemContainerWithTitle title="라이브" link="/board/live" scrolled>
          {liveList.length > 0 ? 
            [...liveList].reverse().map((live, index) => {
              return (
                <div key={"live" + live.id} className="flex justify-center">
                  <div>
                    <LiveItem imgSrc={live.image} title={(live.title ? live.title : "No Title")} />
                  </div>
                </div>
              );
            }) :
            <div className="w-full h-[225px] flex justify-center place-items-center"><p>진행중인 라이브가 없습니다.</p></div>
          }
        </ItemContainerWithTitle>
      </>
    );
  }

  return (
    <>
      <div className="mt-16 mb-8 w-full min-w-112 px-16">
        <SearchBar initQuery={query} initHashtagListString={hashtagListString} />
      </div>
      {contentView}
    </>
  );
}
