//팔로우 팔로워 모달 컴포넌트
import { useState, useEffect } from "react";
import axios from "axios";
import { useAuthStore } from "../store/AuthStore";

const tabs = [
  { value: "follower", title: "팔로워" },
  { value: "following", title: "팔로잉" },
];

export default function Follow({ followType, followingList, followerList }) {
  const [follow, setFollow] = useState(followType);
  const { accessToken } = useAuthStore();

  const serverUrl = process.env.REACT_APP_TEST_SERVER_BASE_URL;

  useEffect(() => {
    setFollow(followType);
  }, [followType]);

  const handleDeleteFollowing = (id) => {
    axios({
      method: "delete",
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
      console.log(error);
      alert("오류가 발생했습니다.");
    })
  }

  const followList = (follow === "follower" ? followerList : followingList).map((followUser, index) => (
    <div key={"follow" + followUser.id} className="flex justify-center">
      <div className="flex">
        <div className="flex-shrink-0 h-10 w-10 rounded-full bg-gray-300">
          <img className="h-10 w-10 rounded-full" src={`${serverUrl}/member/image/${followUser.id}`}/>
        </div>
        <div className="w-52 h-12">{(followUser.nickname ? followUser.nickname : "No Name")}</div>
        {follow === "following" && (
          <button 
            className="flex px-3 md:px-4 py-1 md:py-2 bg-base-100 text-white rounded-lg hover:bg-base-200" 
            onClick={() => handleDeleteFollowing(followUser.id)}
          >
            삭제
          </button>
        )}
      </div>
    </div>
  ));

  return (
    <div>
      <div role="tablist" className="tabs tabs-bordered my-8 tabs-lg">
        {tabs.map((tab) => (
          <button
            key={tab.value}
            role="tab"
            className={"tab w-1/2 translate-x-1/2" + (follow === tab.value ? " tab-active" : "")}
            onClick={() => setFollow(tab.value)}
          >
            {tab.title}
          </button>
        ))}
      </div>
      <div className="flex flex-col gap-4 items-center scrollbar scrollbar-thin scrollbar-thumb-rounded-full scrollbar-track-rounded-full scrollbar-thumb-base-200 hover:scrollbar-thumb-primary overflow-y-scroll">
        {followList}
      </div>
    </div>
  );
}
