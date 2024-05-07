import { useState, useRef } from "react";
// import { loadMusic } from "../api/upload.js";
import axios from "axios";
import SearchMusic from "../components/SearchMusic";
export default function LoadMusic({ getLoadMusic }) {
  const [files, setFiles] = useState([]);
  const [urls, setUrls] = useState([]);
  const fileInput = useRef(null);
  const [roots, setRoots] = useState([]);
  const [titles, setTitles] = useState([]);
  const childSearchMusicRef=useRef(null)

  const getChildSearchResult = (url, title, id) => {
    setUrls((urls) => [...urls, url]);
    setTitles((titles) => [...titles, title]);
    setRoots((roots) => [...roots, id]);
  };

  const handleFileUpload = async (event) => {
    //여러 파일업로드
    setFiles((files) => [...files, ...Array.from(event.target.files)]);
    console.log(files);
  };

  const loadMusic = () => {
    getLoadMusic([...files, ...urls], roots); // 루트도 같이 보내주기
    clearMusic();
  };

  const clearMusic = () => {
    setFiles([]);
    setUrls([]);
    setRoots([]);
    setTitles([]);
    childSearchMusicRef.current.clear()
  };

  return (
    <div className="w-full h-full">
      <div className="flex flex-col w-full items-center justify-center">
        <div className="text-base-content pt-5 pb-50 w-full">
          <div className="text-4xl pb-10">음원 불러오기</div>
          <div className="flex w-full pb-10 mx-6">
            <div className="w-3/4 text-left">
              <input
                type="file"
                onChange={handleFileUpload}
                style={{ display: "none" }}
                accept="audio/mp3"
                ref={fileInput}
                multiple="multiple"
              />
              <button
                className="btn btn-primary btn-s"
                onClick={() => {
                  fileInput.current.click();
                }}>
                +첨부하기
              </button>
            </div>
            <div
              className="w-1/4 text-right"
              style={{
                overflow: "hidden",
                textOverflow: "ellipsis",
                whiteSpace: "nowrap",
              }}>
              {files.map((value, index) => (
                <span key={index}>{value.name} </span> // 배열의 각 항목을 화면에 표시
              ))}
            </div>
          </div>
          <div className="w-full flex">
            <div className="w-3/4 text-left whitespace-nowrap pb-2 pr-10 mx-6">
              BeatHerb에서 가져오기
            </div>
            <div
              className="w-1/4 text-right"
              style={{
                overflow: "hidden",
                textOverflow: "ellipsis",
                whiteSpace: "nowrap",
              }}>
              {titles.map((value, index) => (
                <span key={index}>{value} </span> // 배열의 각 항목을 화면에 표시
              ))}
            </div>
          </div>

          <SearchMusic ref={(el) => (childSearchMusicRef.current = el)} getChildSearchResult={getChildSearchResult}></SearchMusic>

          <div className="flex justify-center">
            <div className="self-auto text-xl flex">
              <div className="modal-action px-3">
                <form method="dialog">
                  <button className="btn" onClick={loadMusic}>
                    확인
                  </button>
                </form>
              </div>
              <div className="modal-action px-3">
                <form method="dialog">
                  <button className="btn" onClick={clearMusic}>
                    취소
                  </button>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
