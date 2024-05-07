import React, { forwardRef, useEffect, useRef, useState, useImperativeHandle } from "react";
import WaveformPlaylist from "waveform-playlist";
import "../assets/timeline.css";
import LoadMusic from "../page/LoadMusic.js";
import UploadMusic from "../page/UploadMusic.js";
import { useAuthStore } from "../store/AuthStore";

const MusicWave = forwardRef(({}, ref) => {
  // var ee = playlist.getEventEmitter();
  // var $container = $("body");
  // var $timeFormat = $container.find('.time-format');
  // var $audioStart = $container.find('.audio-start');
  // var $audioEnd = $container.find('.audio-end');
  // var $time = $container.find('.audio-pos');
  const [isSelected, setIsSelected] = useState(false);
  const { accessToken } = useAuthStore();
  const [cnt, setCnt] = useState(0);
  const loadMusicModalRef = useRef();
  const uploadMusicModalRef = useRef();
  const [rootContentIdList, setRootContentIdList] = useState([]);
  const playlistRef = useRef();
  const eeRef = useRef(null);
  const audios = [];
  const [audioData, setAudioData] = useState(audios);
  const [format, setFormat] = useState("hh:mm:ss.uuu");
  const [startTime, setStartTime] = useState(0);
  const [endTime, setEndTime] = useState(0);
  const [audioPos, setAudioPos] = useState(0);
  const [downloadData, setDownloadData] = useState(null);
  const [isLooping, setIsLooping] = useState(false);
  const [playoutPromises, setPlayoutPromises] = useState(null);
  const ee = useRef(null);
  const container = useRef(null);
  const timeFormat = useRef(null);
  const audioStart = useRef(null);
  const audioEnd = useRef(null);
  const time = useRef(null);
  const trackDrop = useRef(null);
  useImperativeHandle(ref, () => ({
    handleRecordingUpload,
  }));
  const getLoadMusic = (files, roots) => {
    console.log(files);
    files.map((file) => {
      console.log("file", file);
      handleFileUpload(file);
    });
    setRootContentIdList((rootContentIdList) => [...rootContentIdList, ...roots]);
    setCnt(cnt + files.length);
  };
  const handleFileUpload = (file) => {
    //파일업로드
    // const file = event.target.files[0];
    //   const url = URL.createObjectURL(file);
    //   console.log(audioData)
    eeRef.current.emit("newtrack", file);
  };
  const handleRecordingUpload = (url) => {
    //악기랑 음성녹음 업로드
    // setAudioData((prevAudios) => [...prevAudios, { id: cnt, src: url, x: 1,y:0 }]);
    // setCnt((cnt) => cnt + 1);
    eeRef.current.emit("newtrack", url);
    setCnt(cnt + 1);
    console.log("handle", url);
  };

  const cueFormatters = (format) => {
    const clockFormat = (seconds, decimals) => {
      const hours = parseInt(seconds / 3600, 10) % 24;
      const minutes = parseInt(seconds / 60, 10) % 60;
      const secs = (seconds % 60).toFixed(decimals);
      const res =
        (hours < 10 ? "0" + hours : hours) +
        ":" +
        (minutes < 10 ? "0" + minutes : minutes) +
        ":" +
        (secs < 10 ? "0" + secs : secs);
      return res;
    };
    const formats = {
      seconds: (seconds) => {
        return seconds.toFixed(0);
      },
      thousandths: (seconds) => {
        return seconds.toFixed(3);
      },
      "hh:mm:ss": (seconds) => {
        return clockFormat(seconds, 0);
      },
      "hh:mm:ss.u": (seconds) => {
        return clockFormat(seconds, 1);
      },
      "hh:mm:ss.uu": (seconds) => {
        return clockFormat(seconds, 2);
      },
      "hh:mm:ss.uuu": (seconds) => {
        return clockFormat(seconds, 3);
      },
    };
    return formats[format];
  };

  const updateSelect = (start, end) => {
    console.log("start", start);
    console.log("end", end);
    setStartTime(start);
    setEndTime(end);
    // setAudioStart(cueFormatters(format)(start));
    // setAudioEnd(cueFormatters(format)(end));
  };

  const updateTime = (time) => {
    // setTimeout(cueFormatters(format)(time));
    setAudioPos(time);
    console.log("time", time);
    console.log(cueFormatters(format)(time));
  };

  // updateSelect(startTime, endTime);
  // updateTime(audioPos);
  useEffect(() => {
    let userMediaStream;
    let playlist;
    const constraints = { audio: true };
    //녹음 동그라미 버튼 없애기
    // const gotStream = (stream) => {
    //   userMediaStream = stream;
    //   playlist.initRecorder(userMediaStream);
    //   // Update UI to enable recording button
    // };

    const logError = (err) => {
      console.error(err);
    };

    playlist = WaveformPlaylist({
      samplesPerPixel: 3000,
      waveHeight: 100,
      container: playlistRef.current,
      state: "cursor",
      colors: {
        waveOutlineColor: "#005BBB",
        timeColor: "grey",
        fadeColor: "black",
      },
      timescale: true,
      controls: {
        show: true,
        width: 200,
        widgets: {
          // Mute & solo button widget
          muteOrSolo: false,
          // Volume slider
          volume: true,
          // Stereo pan slider
          stereoPan: false,
          // Collapse track button
          collapse: false,
          // Remove track button
          remove: true,
        },
      },
      barGap: 0,
      seekStyle: "line",
      zoomLevels: [500, 1000, 3000, 5000],
    });

    playlist.load(audioData).then(function () {
      playlist.initExporter();
      eeRef.current = playlist.getEventEmitter();
      //녹음 동그라미 버튼 없애기
      // if (navigator.mediaDevices) {
      //   navigator.mediaDevices.getUserMedia(constraints).then(gotStream).catch(logError);
      // } else if (navigator.getUserMedia && "MediaRecorder" in window) {
      //   navigator.getUserMedia(constraints, gotStream, logError);
      // }
    });
    return () => {
      if (eeRef.current) {
        eeRef.current.emit("clear");
      }
    };
  }, []);
  if (eeRef.current) {
    eeRef.current.on("mute", function (track) {
      console.log("Mute button pressed for " + track.name);
    });
  }
  if (eeRef.current) {
    eeRef.current.on("removeTrack", function (track) {
      setCnt(cnt - 1);
      console.log("Remove button pressed for " + track.name);
    });
  }
  const playBtn = () => {
    eeRef.current.emit("play");
  };
  const pauseBtn = () => {
    eeRef.current.emit("pause");
  };
  const stopBtn = () => {
    eeRef.current.emit("stop");
  };

  const audiorenderingfinished = (type, data) => {
    if (type == "wav") {
      // setDownloadData(window.URL.createObjectURL(data));
      setDownloadData(data);
    }
  };

  const trimBtn = () => {
    if (!isSelected) {
      alert("자르실 영역과 음악을 선택해 주세요");
      return;
    }
    eeRef.current.emit("trim");
    setIsSelected(false);
  };
  const selectBtn = () => {
    eeRef.current.emit("statechange", "select");
    eeRef.current.on("select", updateSelect);
    setIsSelected(true);
    // eeRef.current.on("timeupdate",updateTime)
  };
  const cursorBtn = () => {
    eeRef.current.emit("statechange", "cursor");
  };
  const shiftAudioBtn = () => {
    eeRef.current.emit("statechange", "shift");
  };

  const openLoadMusicModal = () => {
    loadMusicModalRef.current.showModal();
  };

  const openUploadMusicModal = async () => {
    if (cnt === 0) {
      alert("음악을 하나 이상 만들어 주세요.");
      return;
    }
    if (!accessToken) {
      alert("로그인 후 이용 가능한 서비스입니다.");
      return;
    }
    await eeRef.current.emit("startaudiorendering", "wav");
    await eeRef.current.on("audiorenderingfinished", audiorenderingfinished);
    await uploadMusicModalRef.current.showModal();
  };
  const closeUploadModal = async () => {
    await uploadMusicModalRef.current.close();
  };

  return (
    <div className="flex w-full h-full">
      <div className="w-2/12 bg-base-200 border-gray-300 p-5">
        <dialog ref={loadMusicModalRef} className="modal">
          <div className="modal-box w-11/12 max-w-5xl">
            <LoadMusic getLoadMusic={getLoadMusic} />
          </div>
        </dialog>
        <div className="grid grid-cols-1 gap-5">
          <button
            style={{
              display: "flex",
              alignItems: "between",
              justifyContent: "center",
              padding: "5px",
            }}
            onClick={openLoadMusicModal}
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              strokeWidth={1.5}
              stroke="currentColor"
              className="w-6 h-6"
            >
              <path strokeLinecap="round" strokeLinejoin="round" d="M12 4.5v15m7.5-7.5h-15" />
            </svg>{" "}
            음악추가
          </button>
          <button
            style={{
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              padding: "5px",
            }}
            onClick={playBtn}
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              strokeWidth={1.5}
              stroke="currentColor"
              className="w-6 h-6"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="M5.25 5.653c0-.856.917-1.398 1.667-.986l11.54 6.347a1.125 1.125 0 0 1 0 1.972l-11.54 6.347a1.125 1.125 0 0 1-1.667-.986V5.653Z"
              />
            </svg>{" "}
            재생
          </button>
          <button
            style={{
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              padding: "5px",
            }}
            onClick={pauseBtn}
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              strokeWidth={1.5}
              stroke="currentColor"
              className="w-6 h-6"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="M15.75 5.25v13.5m-7.5-13.5v13.5"
              />
            </svg>{" "}
            일시정지
          </button>
          <button
            style={{
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              padding: "5px",
            }}
            onClick={stopBtn}
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              strokeWidth={1.5}
              stroke="currentColor"
              className="w-6 h-6"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="M5.25 7.5A2.25 2.25 0 0 1 7.5 5.25h9a2.25 2.25 0 0 1 2.25 2.25v9a2.25 2.25 0 0 1-2.25 2.25h-9a2.25 2.25 0 0 1-2.25-2.25v-9Z"
              />
            </svg>{" "}
            정지
          </button>

          <button
            style={{
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              padding: "5px",
            }}
            onClick={cursorBtn}
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              strokeWidth={1.5}
              stroke="currentColor"
              className="w-6 h-6"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="M15.042 21.672 13.684 16.6m0 0-2.51 2.225.569-9.47 5.227 7.917-3.286-.672ZM12 2.25V4.5m5.834.166-1.591 1.591M20.25 10.5H18M7.757 14.743l-1.59 1.59M6 10.5H3.75m4.007-4.243-1.59-1.59"
              />
            </svg>{" "}
            커서이동
          </button>
          <button
            style={{
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              padding: "5px",
            }}
            onClick={shiftAudioBtn}
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              strokeWidth={1.5}
              stroke="currentColor"
              className="w-6 h-6"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="M7.5 21 3 16.5m0 0L7.5 12M3 16.5h13.5m0-13.5L21 7.5m0 0L16.5 12M21 7.5H7.5"
              />
            </svg>{" "}
            음악이동
          </button>
          <button
            style={{
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              padding: "5px",
            }}
            onClick={selectBtn}
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              strokeWidth={1.5}
              stroke="currentColor"
              className="w-6 h-6"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="M9 4.5v15m6-15v15m-10.875 0h15.75c.621 0 1.125-.504 1.125-1.125V5.625c0-.621-.504-1.125-1.125-1.125H4.125C3.504 4.5 3 5.004 3 5.625v12.75c0 .621.504 1.125 1.125 1.125Z"
              />
            </svg>{" "}
            영역선택
          </button>
          <button
            style={{
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              padding: "5px",
            }}
            onClick={trimBtn}
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              strokeWidth={1.5}
              stroke="currentColor"
              className="w-6 h-6"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="m7.848 8.25 1.536.887M7.848 8.25a3 3 0 1 1-5.196-3 3 3 0 0 1 5.196 3Zm1.536.887a2.165 2.165 0 0 1 1.083 1.839c.005.351.054.695.14 1.024M9.384 9.137l2.077 1.199M7.848 15.75l1.536-.887m-1.536.887a3 3 0 1 1-5.196 3 3 3 0 0 1 5.196-3Zm1.536-.887a2.165 2.165 0 0 0 1.083-1.838c.005-.352.054-.695.14-1.025m-1.223 2.863 2.077-1.199m0-3.328a4.323 4.323 0 0 1 2.068-1.379l5.325-1.628a4.5 4.5 0 0 1 2.48-.044l.803.215-7.794 4.5m-2.882-1.664A4.33 4.33 0 0 0 10.607 12m3.736 0 7.794 4.5-.802.215a4.5 4.5 0 0 1-2.48-.043l-5.326-1.629a4.324 4.324 0 0 1-2.068-1.379M14.343 12l-2.882 1.664"
              />
            </svg>{" "}
            자르기
          </button>
          <button
            style={{
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              padding: "5px",
            }}
            onClick={openUploadMusicModal}
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              strokeWidth={1.5}
              stroke="currentColor"
              className="w-6 h-6"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="M3 16.5v2.25A2.25 2.25 0 0 0 5.25 21h13.5A2.25 2.25 0 0 0 21 18.75V16.5m-13.5-9L12 3m0 0 4.5 4.5M12 3v13.5"
              />
            </svg>{" "}
            업로드
          </button>
          <dialog ref={uploadMusicModalRef} className="modal">
            <div className="modal-box w-11/12 max-w-5xl">
              <UploadMusic
                music={downloadData}
                rootContentIdList={rootContentIdList}
                closeUploadModal={closeUploadModal}
              />
            </div>
          </dialog>
        </div>
      </div>
      <div
        className="rounded-md bg-base-300 w-10/12 mx-5"
        style={{ overflow: "scroll", overflowX: "hidden", maxHeight: "500px" }}
      >
        <div className="p-5" id="playlist" ref={playlistRef}></div>
        <div>
          <button onClick={openLoadMusicModal}>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              strokeWidth={1.5}
              stroke="currentColor"
              className="w-6 h-6"
            >
              <path strokeLinecap="round" strokeLinejoin="round" d="M12 4.5v15m7.5-7.5h-15" />
            </svg>
          </button>
        </div>
      </div>
    </div>
  );
});

export default MusicWave;
