import { useNavigate } from 'react-router-dom';
import axios from "axios";
import { useState, useRef, useEffect } from "react";
import { uploadMusic } from "../api/upload.js";
import { getHashtag } from "../api/getHashtag.js";
import { FFmpeg } from "@ffmpeg/ffmpeg";
import HashTagList from "../page/HashTagList.js";
import { useAuthStore } from "../store/AuthStore";
// 음원 업로드 모달
// 기존 코드에서 수정 사항
// 1. 창작자 id리스트는 부모로부터 넘어오므로 prop으로 넘겨줌
// 2. uploadMusic.then()으로 clear 해줌

// 부모 페이지로부터 넘어오는 요소들 : 음악 파일, 진입차수 id 리스트
export default function UploadMusic({ music, rootContentIdList, closeUploadModal }) {
  const [isProcessing, setIsProcessing] = useState(false);
  const [image, setImage] = useState(null);
  const selectImgFile = useRef(null);

  const [lyrics, setLyrics] = useState(null);
  const selectLyricsFile = useRef(null);

  const [hashTagIdList, setHashTagIdList] = useState([]);
  const [showHashTag, setShowHashTag] = useState(false);

  const titleRef = useRef(null);
  const describeRef = useRef(null);
  const [type, setType] = useState(null);
  const types = ["VOCAL", "MELODY", "SOUNDTRACK"];

  const { accessToken } = useAuthStore();
  const serverURL = process.env.REACT_APP_TEST_SERVER_BASE_URL;
  const navigate= useNavigate();

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
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };
    // 컴포넌트가 마운트되었을 때 한 번만 데이터를 가져오도록 설정
    fetchData();
  }, [music]);

  const handleAddCategory = () => {
    setShowHashTag(!showHashTag);
  };

  const convertMedia = async (blob) => {
    const ffmpeg = new FFmpeg();
    await ffmpeg.load();
    const arrayBuffer = await blob.arrayBuffer();
    await ffmpeg.writeFile("input", new Uint8Array(arrayBuffer));

    await ffmpeg.exec(["-i", "input", "output.mp3"]);
    const data = await ffmpeg.readFile("output.mp3");
    const mp3Blob = new Blob([data.buffer], { type: "audio/mp3" });
    console.log("mp3", mp3Blob);
    return mp3Blob;
  };

  const readLyrics = async (lyrics) => {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.onload = (event) => {
        const lyricsValue = event.target.result;
        resolve(lyricsValue);
      };
      reader.onerror = reject;
      reader.readAsText(lyrics);
    });
  };

  const onSubmit = async () => {
    const selectedHashTags = hashTagIdList.filter((item) => item.selected).map((item) => item.id);
    if (
      !titleRef.current.value ||
      !type ||
      hashTagIdList.filter((item) => item.selected).length === 0
    ) {
      alert("제목, 타입, 해시태그는 필수 입력 사항입니다.");
      setIsProcessing(false);
      return;
    }
    setIsProcessing(true);
    const formData = new FormData();
    formData.append("title", titleRef.current.value);
    if (lyrics) {
      formData.append("lyrics", await readLyrics(lyrics));
    }
    formData.append("describe", describeRef.current.value);
    if (rootContentIdList) {
      formData.append("rootContentIdList", rootContentIdList);
    }
    formData.append("hashTagIdList", selectedHashTags);
    if (image) {
      formData.append("image", image);
    }
    formData.append("music", await convertMedia(music), titleRef.current.value + ".mp3");
    formData.append("type", type);
    // console.log(formData);
    // 서버로 요청을 보냄
    await axios({
      method: "post",
      url: `${serverURL}/content/upload`,
      headers: {
        "Content-Type": "multipart/form-data",
        Authorization: `Bearer ${accessToken}`,
      },
      data:formData,
    })
      .then((response) => {
        console.log(response.data.data.id)
        clear()
        navigate("/content/"+response.data.data.id)
      })
      .catch((error) => {
        console.log(error.message);
        alert("음악 업로드에 실패했습니다");
        clear();
        navigate("/")
      });
  };
  const clear = async () => {
    setImage(null);
    selectImgFile.current.value = "";
    setLyrics(null);
    selectImgFile.current.value = "";
    setHashTagIdList([]);
    setShowHashTag(false);
    describeRef.current.value = "";
    titleRef.current.value = "";
    setType(null);
    setIsProcessing(false);
  };

  const onChangeImg = (e) => {
    if (!e.target.files[0]) {
      return;
    }
    const file = e.target.files[0];
    setImage(file);
  };

  const onChangeLyrics = (e) => {
    if (!e.target.files[0]) {
      return;
    }
    const file = e.target.files[0];
    setLyrics(file);
  };

  return (
    <div className="w-full h-full">
      <div className="flex flex-col w-full items-center justify-center">
        <div className="text-base-content pt-5 pb-50 w-full">
          <div className="text-4xl pb-10">음원 등록하기</div>
          <div className="flex items-center pb-10 mx-6">
            <div className="text-left whitespace-nowrap pr-14">제목</div>
            <input
              type="text"
              placeholder="제목을 입력해주세요."
              className="input input-ghost w-full px-3"
              ref={titleRef}
            />
          </div>

          <div className="flex pb-10 justify-between mx-6">
            <div className="text-left whitespace-nowrap pr-10">앨범표지</div>
            {image && <div className="w-full h-full text-left">{image.name}</div>}
            <input
              type="file"
              accept="image/*"
              style={{ display: "none" }}
              ref={selectImgFile}
              onChange={onChangeImg}
            />
            <div
              className="btn btn-primary btn-xs"
              onClick={() => {
                selectImgFile.current.click();
              }}
            >
              첨부하기
            </div>
          </div>

          <div className="flex pb-10 justify-between mx-6">
            <div className="text-left whitespace-nowrap pr-10">가사</div>
            {lyrics && <div className="w-full h-full text-left pl-8">{lyrics.name}</div>}
            <input
              type="file"
              style={{ display: "none" }}
              ref={selectLyricsFile}
              onChange={onChangeLyrics}
            />
            <div
              className="btn btn-primary btn-xs"
              onClick={() => {
                selectLyricsFile.current.click();
              }}
            >
              첨부하기
            </div>
          </div>

          <div className="flex pb-4 justify-between mx-6 items-top">
            <div className="text-left whitespace-nowrap pr-10">해시태그</div>
            <div className="w-full h-full text-left">
              {hashTagIdList &&
                hashTagIdList.map(
                  (item, index) =>
                    item.selected && (
                      <div key={index} className="btn btn-xs btn-outline btn-primary mr-2">
                        {item.name}
                      </div>
                    )
                )}
            </div>
            <div className="btn btn-primary btn-xs" onClick={handleAddCategory}>
              + 추가하기
            </div>
          </div>
          <div className="ml-32 pb-8">
            {showHashTag && <HashTagList data={hashTagIdList} setData={setHashTagIdList} />}
          </div>

          <div className="flex pb-10 justify-between mx-6 items-top">
            <div className="text-left whitespace-nowrap pr-8">음원 타입</div>
            <div className="w-full h-full text-left">
              {types.map((item, index) => (
                <button
                  key={index}
                  className={`btn btn-xs ${
                    type === item ? "btn-primary" : "btn-secondary"
                  } mr-2 py-0`}
                  onClick={() => {
                    setType(item);
                  }}
                >
                  {item}
                </button>
              ))}
            </div>
          </div>

          <div className="flex pb-10 mx-6">
            <div className="text-left whitespace-nowrap pr-9">음원 설명</div>
            <textarea
              className="textarea textarea-bordered w-full"
              rows={8}
              placeholder="내용을 입력해주세요."
              ref={describeRef}
            ></textarea>
          </div>

          <div className="flex justify-center">
            {isProcessing ? (
              <div className="flex gap-3">
                <p>음악을 올리는 중입니다. 잠시만 기다려 주세요</p>
                <span className="loading loading-spinner text-success"></span>
              </div>
            ) : (
                <div className="self-auto text-xl flex">
                <div className="modal-action px-3">
                  <button className="btn" onClick={onSubmit}>
                    작성하기
                  </button>
                  </div>
                <div className="modal-action px-3">
                  <form method="dialog">
                    <button className="btn" onClick={clear}>
                      취소하기
                    </button>
                  </form>
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
