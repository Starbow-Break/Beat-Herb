// 상세페이지

import NavBar from "../components/NavBar";
import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import ContentsItem from "../components/ContentsItem.js";
import axios from "axios";
import LiveItem from "../components/LiveItem.js";
import MusicPlayer from "../components/MusicPlayer.js";
import { creatorListFormat } from "../common/creatorListFormat.js";
import { Link } from "react-router-dom";
import { useAuthStore } from "../store/AuthStore.js";

const tabs = [
  { value: "melody", title: "멜로디" },
  { value: "vocal", title: "보컬" },
  { value: "music", title: "음원" },
  { value: "shorts", title: "Shorts" },
  { value: "live", title: "라이브" },
];

const commenttabs = [
  { value: "comment", title: "댓글" },
  { value: "lyrics", title: "가사" },
];

export default function ContentDetail() {
  const navigate = useNavigate();
  const { id } = useParams();
  const [category, setCategory] = useState("melody");
  const [comment, setComment] = useState("comment");
  const [commentMessage, setCommentMessage] = useState("");

  // 컨텐츠 관련 정보
  const [imageSrc, setImageSrc] = useState("");
  const [title, setTitle] = useState("");
  const [describe, setDescribe] = useState("");
  const [creatorList, setCreatorList] = useState([]);
  const [hashtagList, setHashtagList] = useState([]);
  const [inOrderList, setInOrderList] = useState([]);
  const [outOrder, setOutOrder] = useState({});
  const [commentList, setCommentList] = useState([]);
  const [lyrics, setLyrics] = useState("");

  const [showPlayer, setShowPlayer] = useState(false);

  const { accessToken } = useAuthStore();
  const serverUrl = process.env.REACT_APP_TEST_SERVER_BASE_URL;

  useEffect(() => {
    const serverUrl = process.env.REACT_APP_TEST_SERVER_BASE_URL;

    axios({
      method: "get",
      url: `${serverUrl}/content/${id}`,
    })
    .then((response) => {
      const data = response.data.data;
      console.log(data);
      
      setImageSrc(data.image);
      setTitle(data.title);
      setDescribe(data.describe);
      setCreatorList(data.creatorList);
      setHashtagList(data.hashTagList);
      setInOrderList(data.inOrderList); 
      setOutOrder(data.outOrder);
      setCommentList(data.commentList);
      setLyrics(data.lyrics);
    })
    .catch((error) => {
      alert("데이터를 받는 도중 문제가 발생했습니다.");
      navigate(-1);
    })
  }, [id]);

  const sendCommentMessage = () => {
    axios({
      method: "post",
      url: `${serverUrl}/comment`,
      headers: {
        Authorization: `Bearer ${accessToken}`
      },
      data: {
        contentId: id,
        body: commentMessage
      }
    })
      .then((response) => {
        setCommentMessage("");
        window.location.reload();
      })
      .catch((error) => {
        console.log(error)
        alert("데이터를 보내는 도중 문제가 발생했습니다.");
      });
  };

  const initPlay = () => {
    setShowPlayer(true);
  };

  window.onpopstate=()=>{setShowPlayer(false)}

  const inOrderListFormat = (inOrderList) => {
    const inOrderView = inOrderList.map((inOrder, index) => {
      return <Link to={`/content/${inOrder.id}`} onClick={() => setShowPlayer(false)}>@{inOrder.title}</Link>;
    })
    return inOrderView;
  };

  let itemView = null;
  let commentView = null;
  let inputField = null;

  // out order
  if (category === "melody" || category === "vocal" || category === "music") {
    let contentList;

    if (category === "melody") {
      contentList = outOrder.melodyList;
    } else if (category === "vocal") {
      contentList = outOrder.vocalList;
    } else {
      contentList = outOrder.soundTrackList;
    }

    itemView = contentList?.map((content, index) => (
      <div key={"content" + content.id} className="flex justify-center">
        <ContentsItem
          contentId={content.id}
          size={150}
          albumArt={content.image}
          title={content.title}
          artist={creatorListFormat(content.creatorList)}
          showFavorite={false}
        />
      </div>
    ));
  } else if (category === "live") {
    itemView = outOrder.liveList?.map((live, index) => (
      <div key={"live" + live.id} className="flex justify-center">
        <LiveItem title={live.title} />
      </div>
    ));
  }

  if (comment === "comment") {
    commentView = commentList.map((comment, index) => (
      <div>
        <div
          key={"comment" + comment.id}
          className="flex justify-center mx-6 my-2 flex-grow"
        >
          <div className="flex w-full mt-2 space-x-3">
            <div>
              <div className="flex-shrink-0 h-10 w-10 rounded-full bg-gray-300">
                <img className="h-10 w-10 rounded-full" src={comment.member.image} />
              </div>
              <div className="text-xs text-gray-500 leading-none">{comment.member.nickname}</div>
            </div>
            <div>
              <div className="bg-primary p-3 rounded-r-lg rounded-bl-lg">
                <p className="text-sm text-primary-content">{comment.body}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    ));

    inputField = (
      <div className="relative flex flex-shrink-0 mt-auto">
        <span className="absolute inset-y-0 flex items-center"></span>
        <input
          type="text"
          placeholder="Write your message!"
          className="w-full focus:outline-none focus:placeholder-gray-400 text-gray-600 placeholder-gray-600 pl-12 bg-gray-200 rounded-md py-3"
          onChange={(e) => setCommentMessage(e.target.value)}
          value={commentMessage}
        />
        <div className="absolute right-0 items-center inset-y-0 hidden sm:flex">
          <button
            type="button"
            className="inline-flex items-center justify-center rounded-lg px-4 py-3 transition duration-500 ease-in-out text-white bg-primary hover:bg-base-100 focus:outline-none"
            onClick={sendCommentMessage}
          >
            <span className="font-bold">Send</span>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 20 20"
              fill="currentColor"
              className="h-6 w-6 ml-2 transform rotate-90"
            >
              <path d="M10.894 2.553a1 1 0 00-1.788 0l-7 14a1 1 0 001.169 1.409l5-1.429A1 1 0 009 15.571V11a1 1 0 112 0v4.571a1 1 0 00.725.962l5 1.428a1 1 0 001.17-1.408l-7-14z"></path>
            </svg>
          </button>
        </div>
      </div>
    );
  } else if (comment === "lyrics") {
    commentView = <div className="flex justify-center">{lyrics}</div>;
  }

  return (
    <>
      <div className="h-full">
        <div className="fixed top-0 w-full z-10">
          <NavBar />
        </div>
        <div className="pt-[76px] mx-8">
          <div className="mx-3 flex place-content-between">
            <div className="flex gap-4 w-7/12">
              <div className="flex flex-col">
                <div className="w-52 h-52 rounded-md">
                  <img
                    className="w-52 h-52  w-full rounded-md object-cover"
                    src={imageSrc}
                    alt="Album Art"
                  />
                </div>
                <div className="flex items-center justify-center w-52 h-16 rounded-md">
                  {!showPlayer && (
                    <button
                      className="flex px-3 md:px-4 py-1 bg-base-100 text-white rounded-lg hover:bg-base-200"
                      onClick={initPlay}
                    >
                      <div className="flex place-items-center gap-1">
                        <p className="text text-2xl text-semibold">Play</p>
                        <svg
                          xmlns="http://www.w3.org/2000/svg"
                          fill="none"
                          viewBox="0 0 24 24"
                          strokeWidth="1.5"
                          stroke="currentColor"
                          width="28"
                          hleight="28"
                        >
                          <path
                            strokeLinecap="round"
                            strokeLinejoin="round"
                            d="M5.25 5.653c0-.856.917-1.398 1.667-.986l11.54 6.347a1.125 1.125 0 0 1 0 1.972l-11.54 6.347a1.125 1.125 0 0 1-1.667-.986V5.653Z"
                          />
                        </svg>
                      </div>
                    </button>
                  )}
                </div>
              </div>
              <div className="flex flex-col h-52 place-content-center place-content-evenly">
                <div className="space-y-2">
                  <p className="text-base-content text-left text-3xl font-semibold">
                    {title ? title : "Title"}
                  </p>
                  <p className="text-base-content text-left">{creatorListFormat(creatorList)}</p>
                </div>
                <div className="space-y-2">
                  <div className="flex gap-1 flex-wrap">
                    {
                      hashtagList.map((hashtag, index) => (
                        <div
                          key={"hashtag" + hashtag.id}
                          className="badge badge-lg badge-primary text-primary-content hover:cursor-pointer"
                          onClick={() => navigate(`/board/all?hashtagList=${hashtag.id}`)}
                        >
                          {hashtag.name}
                        </div>
                      ))
                    }
                  </div>
                  <div className="text-left">진입차수 : <>{inOrderListFormat(inOrderList)}</></div>
                </div>
              </div>
            </div>
            <div className="flex w-5/12 h-auto rounded-md bg-base-200 px-4 py-8 items-center text-left m-auto">
              {describe}
            </div>
          </div>
          <div className="flex">
            <div className="w-7/12 bg-base-200 rounded-md">
              <div className="flex h-12 items-bottom justify-center items-end text-xl">
                이 허브에 사용된
              </div>
              <div role="tablist" className="tabs tabs-bordered mb-8 mt-4">
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
              <div
                className={`grid gap-4 items-center scrollbar scrollbar-thin scrollbar-thumb-rounded-full scrollbar-track-rounded-full scrollbar-thumb-base-200 hover:scrollbar-thumb-primary overflow-y-scroll  h-[32rem] overflow-auto ${
                  category === "live"
                    ? "grid-cols-2"
                    : category === "shorts"
                    ? "grid-cols-3"
                    : "grid-cols-4"
                }`}
              >
                {itemView}
              </div>
            </div>
            <div className="w-5/12 rounded-md bg-primary">
              <div role="tablist" className="tabs tabs-bordered my-8 tabs-lg">
                {commenttabs.map((tab2) => (
                  <button
                    key={tab2.value}
                    role="tab"
                    className={
                      "tab w-1/2 translate-x-1/2 text-primary-content" +
                      (comment === tab2.value ? " tab-active" : "")
                    }
                    style={comment === tab2.value ? { borderBottom: "2px solid #070707" } : {}}
                    onClick={() => setComment(tab2.value)}
                  >
                    {tab2.title}
                  </button>
                ))}
              </div>
              <div className="bg-base-200 rounded-md m-4 h-[33rem] overflow-auto relative">
                {commentView}
                <div className="absolute bottom-0 w-full">{inputField}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
      {showPlayer && (
        <MusicPlayer music={{ id, title, creator: creatorListFormat(creatorList), imageSrc }} />
      )}
    </>
  );
}
