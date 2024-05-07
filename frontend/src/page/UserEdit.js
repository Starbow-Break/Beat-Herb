import { useState, useRef, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { useAuthStore } from "../store/AuthStore";
import defaultUser from "../assets/default_user.jpeg";
import { getHashtag } from "../api/getHashtag.js";
import HashTagList from "./HashTagList.js";
import produce from 'immer';

// 유저 정보 수정 로직
// 프로필 사진, 닉네임, DM 수신 여부
// 각 버튼을 눌렀을 때 정보를 State에 담아줌
// 수정하기, 취소 버튼을 누르면 메인 페이지로 redirect
// 이미지 업로드 기능도 구현

export default function MyPage() {
  const navigate = useNavigate();
  // 프로필 이미지
  const [image, setImage] = useState(defaultUser);
  const { accessToken, setNickname, userId } = useAuthStore();

  const [file, setFile] = useState(null);
  const fileInput = useRef(null);

  // 닉네임
  const nicknameRef = useRef(null);

  // DM 여부
  const [isActive, setIsActive] = useState(true); // 여기서 true 또는 false에 따라 버튼 활성화 여부 결정

  // 해시태그
  const [hashTagIdList, setHashTagIdList] = useState([]);
  const [showHashTag, setShowHashTag] = useState(false);

  console.log(hashTagIdList);

  const handleAddCategory = () => {
    setShowHashTag(!showHashTag);
  };

  useEffect(() => {
    // 서버에서 데이터를 가져오는 비동기 함수를 호출
    const fetchData = async () => {
      try {
        const response = await getHashtag();
        // 데이터를 가져온 후 각 데이터에 selected 속성 추가
        const initialData = response.data.data.map((item) => ({
          ...item,
          selected: false,
        }));
        setHashTagIdList(initialData); // 데이터를 상태에 저장
        console.log(initialData);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    // 컴포넌트가 마운트되었을 때 한 번만 데이터를 가져오도록 설정
    fetchData();
  }, []);

  useEffect(() => {
    const serverUrl = process.env.REACT_APP_TEST_SERVER_BASE_URL;

    axios({
      method: "get",
      url: `${serverUrl}/member/${userId}`,
    })
      .then((response) => {
        const data = response.data.data;
        setImage(data.image ? data.image : defaultUser);
        nicknameRef.current.value = data.nickname;

        data.hashTagList.forEach((interest) => {
          setHashTagIdList(
            produce(draft => {
              const hashtagIdItem = draft.find((hashtag) => hashtag.id == interest.id);
              if(hashtagIdItem) {
                hashtagIdItem.selected = true;
              }
            })
          )
        })
      })
      .catch((error) => {
        alert(error.response.data.message[0]);
        navigate(`/mypage/${userId}`);
      });
  }, [userId]);

  const onChangeImg = (e) => {
    if (e.target.files[0]) {
      setFile(e.target.files[0]);
    } else {
      // 업로드 취소할 시
      setImage(defaultUser);
      return;
    }

    // 화면에 프로필 사진 표시
    const reader = new FileReader();
    reader.onload = () => {
      if (reader.readyState === 2) {
        setImage(reader.result);
      }
    };
    reader.readAsDataURL(e.target.files[0]);
  };

  const handleToggle = () => {
    setIsActive(!isActive); // 상태값을 변경하여 버튼 활성화 여부 토글
  };

  // 수정하기 버튼 누를 때
  const onSubmit = async () => {
    const formData = new FormData();
    const serverUrl = process.env.REACT_APP_TEST_SERVER_BASE_URL;

    if(file) formData.append("picture", file);

    // 현재 닉네임에 입력된 값
    const nicknameValue = nicknameRef.current.value;

    // 관심사로 선택된 해시태그 
    const selectedHashTags = hashTagIdList
        .filter((item) => item.selected)
        .map((item) => item.id);

    console.log(selectedHashTags)

    formData.append("dmAgree", isActive);
    formData.append("nickname", nicknameValue);
    formData.append("hashTagIdList", selectedHashTags);

    console.log(formData);

    // 서버에서 file null 체크 필요
    // 프론트에서 file 없을 시 null로 넘어감을 확인
    await axios({
      method: "PUT",
      url: `${serverUrl}/member`,
      mode: "cors",
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
      data: formData, // data 전송시에 반드시 생성되어 있는 formData 객체만 전송 하여야 한다.
    })
    .then((response) => {
      setNickname(nicknameValue);
      navigate(-1);
    })
    .catch((error) => {
      alert("요청에 실패했습니다.");
    });
  };

  return (
    <div className="absolute w-full right-1/2 bottom-1/2 translate-x-1/2 translate-y-1/2 overflow-auto">
      <div className="flex flex-col w-full items-center justify-center flex-wrap">
        <div className="w-1/4 text-base-content pt-5 pb-50">
          <div className="text-4xl pb-10">회원 정보 수정</div>
          <div className="flex justify-center pb-5" width="200" height="200">
            <img
              src={image}
              alt="Profile"
              style={{
                margin: "20px",
                width: "200px",
                height: "200px",
                borderRadius: "50%",
              }}
              onClick={() => {
                fileInput.current.click();
              }}
              className="cursor-pointer"
            />

            <input
              type="file"
              style={{ display: "none" }}
              accept="image/jpg,impge/png,image/jpeg"
              name="profile_img"
              onChange={onChangeImg}
              ref={fileInput}
            />
          </div>
          <div className="flex items-center justify-center pb-10">
            <div className="text-left whitespace-nowrap pr-7 ml-6">닉네임</div>
            <input
              type="text"
              id="nickname"
              ref={nicknameRef}
              placeholder="닉네임을 입력해주세요."
              className="input input-ghost w-full max-w-xs px-3"
            />
          </div>
          <div className="flex items-center pb-2">
            <div className="flex pb-4 justify-between mx-6 items-top">
              <div className="text-left whitespace-nowrap pr-7">해시태그</div>
              <div className="w-full h-full text-left">
                {hashTagIdList &&
                  hashTagIdList.map(
                    (item, index) =>
                      item.selected && (
                        <div
                          key={index}
                          className="btn btn-xs btn-outline btn-primary mr-2 mb-2"
                        >
                          {item.name}
                        </div>
                      )
                  )}
              </div>
              <div
                className="btn btn-primary btn-xs"
                onClick={handleAddCategory}
              >
                + 추가하기
              </div>
            </div>
          </div>
          <div className="ml-28 pb-8">
            {showHashTag && (
              <HashTagList data={hashTagIdList} setData={setHashTagIdList} />
            )}
          </div>
          <div className="flex items-center pb-20">
            <div className="text-left whitespace-nowrap pr-16 ml-6">DM</div>
            <div role="tablist" className="tabs tabs-boxed tabs-sm">
              <button
                role="tab"
                className={`tab ${isActive ? "tab-active" : ""} mr-1`}
                onClick={handleToggle}
              >
                Yes
              </button>
              <button
                role="tab"
                className={`tab ${!isActive ? "tab-active" : ""}`}
                onClick={handleToggle}
              >
                No
              </button>
            </div>
          </div>
          <div className="flex justify-center">
            <div className="self-auto text-xl flex">
              {/* 파일 업로드 로직 구현 필요 */}
              {/* <Link to="/"> */}
              <div onClick={onSubmit} className="px-8 hover:cursor-pointer">
                수정하기
              </div>
              <div
                className="px-8 hover:cursor-pointer"
                onClick={() => navigate(-1)}
              >
                취소하기
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
