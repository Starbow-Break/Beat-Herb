// 컨텐츠 게시판 페이지 항목

import { useState, useEffect } from "react"
import ContentsItem from "../components/ContentsItem";
import { useLocation } from "react-router-dom";
import axios from "axios";
import { useAuthStore } from "../store/AuthStore";
import { creatorListFormatWithoutLink } from "../common/creatorListFormat.js";

// 탭 리스트
const tabs = [
  { value: "melody", title: "멜로디" },
  { value: "vocal", title: "보컬" },
  { value: "music", title: "음원" },
]

export default function ContentsBoard() {
  const location = useLocation();
  const { accessToken } = useAuthStore();
  const [category, setCategory] = useState(location.state?.category || "melody");
  const [contentList, setContentList] = useState([]);
  const [favoriteList, setFavoriteList] = useState([]);

  useEffect(() => {
    const serverUrl = process.env.REACT_APP_TEST_SERVER_BASE_URL;

    axios({
      method: "get",
      url: `${serverUrl}/content/search?title=`
    })
    .then((response) => {

      //새롭게 화면에 띄울 컨텐트 리스트
      let newContentList = []

      if (category === "melody") {
        newContentList = [...response.data.data.melodyList].reverse();
      } else if (category === "vocal") {
        newContentList = [...response.data.data.vocalList].reverse();
      } else {
        newContentList = [...response.data.data.soundTrackList].reverse();
      }

      setContentList(newContentList);

      if (accessToken && newContentList.length > 0) {
        // 새롭게 띄울 컨텐트들의 아이디만 모아놓은 리스트
        const contentIdList = newContentList.map((content, index) => content.id);
        let url = `${serverUrl}/content/star`;
        contentIdList.forEach((id, index) => { url += (index ? "&" : "?") + `contentId=${id}` })
        console.log(url);
        
        axios({
          method: "get",
          url,
          headers: {
            Authorization: `Bearer ${accessToken}`
          }
        })
        .then((response) => {
          setFavoriteList(response.data.data);
        })
        .catch((error) => {
          alert(error.response.data.message ? error.response.data.message : error.response.data.error)
        })
      }
    })
    .catch((error) => {
      alert(error.response.data.message);
    })

    return () => {
      setContentList([]);
      setFavoriteList([]);
    }
  }, [accessToken, category]);

  return (
    <div className="w-full h-full">
      <div role="tablist" className="tabs tabs-bordered my-8 tabs-lg">
        {
          tabs.map((tab) => (
            <button
              key={tab.value}
              role="tab"
              className={"tab w-1/2 translate-x-1/2" + (category === tab.value ? " tab-active" : "")}
              onClick={() => setCategory(tab.value)}
            >
              {tab.title}
            </button>
          ))
        }
      </div>

      <div className="grid grid-cols-4 gap-4 items-center">
        {
          contentList.map((content, index) => {
            const isFavorite = favoriteList.findIndex((favorite) => favorite.id === content.id) !== -1;
            console.log(favoriteList);
            return (
              <div key={"content" + content.id} className="flex justify-center">
                <ContentsItem 
                  contentId={content.id} 
                  size={150} 
                  albumArt={content.image} 
                  title={content.title} 
                  artist={creatorListFormatWithoutLink(content.creatorList)}
                  isFavorite={isFavorite}
                  showFavorite={accessToken !== null}
                />
              </div>
            )
          })
        }
      </div>
    </div>
  );
}