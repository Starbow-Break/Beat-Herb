// 유저 마이페이지

import NavBar from "../components/NavBar";
import ContentsItem from "../components/ContentsItem";
import { useRef, useState, useEffect } from "react";
import LiveItem from "../components/LiveItem";
import Dm from "../components/Dm";
import Follow from "../components/Follow";
import { Link, useParams, useNavigate } from "react-router-dom";
import { useAuthStore } from "../store/AuthStore";
import axios from "axios";
import defaultUser from "../assets/default_user.jpeg"

// 탭 리스트
const tabs = [
  { value: "melody", title: "멜로디" },
  { value: "vocal", title: "보컬" },
  { value: "music", title: "음원" },
  { value: "live", title: "라이브" },
];

export default function MyPage() {
  const params = useParams();
  const navigate = useNavigate();
  const [category, setCategory] = useState("melody");
  const [followType, setFollowType] = useState("follower");
  const followModalRef = useRef();
  const { userId, accessToken } = useAuthStore();

  // 해당 유저가 업로드한 콘텐츠 목록
  const [melodyList, setMelodyList] = useState([]);
  const [vocalList, setVocalList] = useState([]);
  const [musicList, setMusicList] = useState([]);
  const [liveList, setLiveList] = useState([]);

  // 유저 정보
  const [nickname, setNickname] = useState(null);
  const [hashtagList, setHashtagList] = useState([]);
  const [followerList, setFollowerList] = useState([]);
  const [followingList, setFollowingList] = useState([]);

  const id = Number(params.id);
  const serverUrl = process.env.REACT_APP_TEST_SERVER_BASE_URL;

  useEffect(() => {
    axios({
      method: "get",
      url: `${serverUrl}/member/${id}`
    })
    .then((response) => {
      const data = response.data.data;

      console.log(data);

      setNickname(data.nickname);
      setHashtagList((data.hashTagList ? data.hashTagList : []))
      setFollowingList((data.followerList ? data.followerList : []));
      setFollowerList((data.followingList ? data.followingList : []));

      setMelodyList((data.melodyList ? data.melodyList : []));
      setVocalList((data.vocalList ? data.vocalList : []));
      setMusicList((data.soundTrackList ? data.soundTrackList : []));
      setLiveList((data.liveList ? data.liveList : []));

      const userLiveList = []
      if (data.live) {
        userLiveList.push(data.live);
      }
      userLiveList.push(...data.guestList);
      setLiveList(userLiveList);
    })
    .catch((error) => {
      alert(error.response.data.message);
      navigate(-1);
    })
  }, [id]);

  const onErrorImg = (e) => {
    e.target.src = defaultUser;
  };

  const toggleFollow = () => {
    const method = (followerList.findIndex((follower) => follower.id === userId) !== -1 ? "delete" : "post")

    axios({
      method,
      url: `${serverUrl}/follower`,
      headers: {
        Authorization: `Bearer ${accessToken}`
      },
      data: {
        id
      }
    })
    .then((response) => {
      window.location.reload();
    })
    .catch((error) => {
      alert(error.response.data.message);
    })
  }

  const handleClickFollower = () => {
    setFollowType("follower");
    followModalRef.current?.showModal();
  };

  const handleClickFollowing = () => {
    setFollowType("following");
    followModalRef.current?.showModal();
  };

  const creatorListFormat = (creatorList) => {
    let creatorText = "";
    creatorList.forEach((creator, index) => {
      if (creatorText !== "") {
        creatorText += ", ";
      }
      creatorText += creator.nickname;
    })

    return (creatorText !== "" ? creatorText : "creator");
  }

  const dmModalRef = useRef();

  let itemList = null;

  if (category === "melody" || category === "vocal" || category === "music") {
    let contentList = [];
    if (category === "melody") {
      contentList = melodyList;
    } else if (category === "vocal") {
      contentList = vocalList;
    } else {
      contentList = musicList;
    }
    
    itemList = contentList.map((content, index) => {
      return (
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
      )
    });
  } else if (category === "live") {
    itemList = liveList.map((live, index) => (
      <div key={"live" + live.id} className="flex justify-center">
        <LiveItem imgSrc={live.image} title={live.title} />
      </div>
    ));
  }

  return (
    <>
      <div>
        <div className="fixed w-full top-0 z-10">
          <NavBar />
        </div>
        <div className="pt-20 mx-8">
          <div className="mx-4 flex place-content-between">
            <div className="flex gap-4">
              <div className="flex place-items-center">
                <div className="w-32 h-32 rounded-md">
                  <img
                    className="w-32 h-32 rounded-md object-cover"
                    src={`${serverUrl}/member/image/${id}`}
                    onError={onErrorImg}
                    alt="Profile"
                  />
                </div>
              </div>
              <div className="flex flex-col justify-center space-y-4">
                <p className="text-base-content text-left text-3xl font-semibold">
                  {(nickname ? nickname : "No Name")}
                </p>
                <div className="flex gap-1 flex-wrap">
                  {
                    hashtagList.length
                      ? hashtagList.map((hashtag) => (
                          <div key={"hashtag" + hashtag.id} className="badge badge-lg badge-primary text-primary-content hover:cursor-pointer" onClick={() => navigate(`/board/all?hashtagList=${hashtag.id}`)}>
                            {hashtag.name}
                          </div>
                        ))
                      : <p className="text-base-content">지정된 관심사가 없습니다.</p>
                  }
                </div>
              </div>
            </div>
            <div className="flex gap-4">
              <div className="stats shadow">
                <div
                  className="stat place-items-center cursor-pointer hover:bg-base-200"
                  onClick={(userId === id ? handleClickFollower : () => {})}
                >
                  <div className="stat-title">팔로워</div>
                  <div className="stat-value font-semibold text-3xl">
                    {followerList.length}
                  </div>
                </div>
                <div
                  className="stat place-items-center cursor-pointer hover:bg-base-200"
                  onClick={(userId === id ? handleClickFollowing : () => {})}
                >
                  <div className="stat-title">팔로잉</div>
                  <div className="stat-value text-secondary font-semibold text-3xl">
                    {followingList.length}
                  </div>
                </div>
              </div>
              { userId === id ? (
                <div className="flex place-items-center">
                  <Link to="/useredit" className="fill-primary hover:fill-primary">
                    <button
                      className="btn btn-ghost"
                    >
                      <svg
                        className="fill-primary"
                        xmlns="http://www.w3.org/2000/svg"
                        height="32"
                        viewBox="0 -960 960 960"
                        width="32"
                      >
                        <path d="M200-200h57l391-391-57-57-391 391v57Zm-80 80v-170l528-527q12-11 26.5-17t30.5-6q16 0 31 6t26 18l55 56q12 11 17.5 26t5.5 30q0 16-5.5 30.5T817-647L290-120H120Zm640-584-56-56 56 56Zm-141 85-28-29 57 57-29-28Z"/>
                      </svg>
                    </button>
                  </Link>
                </div>) : null}
              { userId && userId !== id ? (
                <div className="flex place-items-center">
                  <button
                    className="btn btn-ghost text-primary hover:text-primary"
                    onClick={toggleFollow}
                  >
                    {followerList.findIndex(follower => follower.id === userId) === -1 ? "Follow" : "UnFollow"}
                  </button>
                </div>) : null}
            </div>
          </div>
          <div role="tablist" className="tabs tabs-bordered my-8 tabs-lg">
            {tabs.map((tab) => (
              <button
                key={tab.value}
                role="tab"
                className={
                  "tab w-1/2 translate-x-1/2" +
                  (category === tab.value ? " tab-active" : "")
                }
                onClick={() => setCategory(tab.value)}
              >
                {tab.title}
              </button>
            ))}
          </div>
          <div className="h-[350px] grid grid-cols-4 gap-4 items-center scrollbar scrollbar-thin scrollbar-thumb-rounded-full scrollbar-track-rounded-full scrollbar-thumb-base-200 hover:scrollbar-thumb-primary overflow-y-scroll">
            {itemList}
          </div>
        </div>
      </div>
      <dialog className="modal" ref={dmModalRef}>
        <div className="modal-box w-11/12 max-w-5xl bg-base-200">
          <Dm />
        </div>
        <form method="dialog" className="modal-backdrop">
          <button>close</button>
        </form>
      </dialog>
      <dialog className="modal" ref={followModalRef}>
        <div className="modal-box w-11/12 max-w-5xl bg-base-200">
          <Follow followType={followType} followingList={followingList} followerList={followerList}/>
        </div>
        <form method="dialog" className="modal-backdrop">
          <button>close</button>
        </form>
      </dialog>
    </>
  );
}
