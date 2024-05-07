import { useRef, useState, useEffect } from "react";
import Hls from 'hls.js';
export default function MusicPlayer({ music }) {
  const videoRef = useRef(null);
  // const [music,setMusic] = useState(props.music ? props.music : {
  //   "title": "Title",
  //   "creator": "Artist",
  //   "src_link": "https://node5.wookoo.shop/api/content/play/0",
  // })

  const [duration, setDuration] = useState(0);
  const [percentage,setPercentage] = useState(0);
  const [play, setPlay] = useState(false);

  const serverUrl = process.env.REACT_APP_TEST_SERVER_BASE_URL;
  const src_link = `${serverUrl}/content/play/${music.id}`;
  
  const backward = () => {
    console.log("이전 곡으로 이동")
    console.log(videoRef.current.duration)
  }

  const forward = () => {
    console.log("다음 곡으로 이동")
    console.log(videoRef.current.duration)
  }

  const updateProgress = (e) => {
    console.log("프로그래스바 선택",videoRef.current)
    const newPercentage = ((e.clientX - e.currentTarget.getBoundingClientRect().x) / e.currentTarget.getBoundingClientRect().width) * 100
    videoRef.current.currentTime=duration*newPercentage/100
    setPercentage(newPercentage)
  }

  const current = duration * (percentage / 100)

  const buttonClick = () => {
    play ? videoRef.current.pause():videoRef.current.play()
    setPlay((prev) => !prev)
  }

  const formatTime = (seconds) => {
    seconds = Math.floor(seconds / 1);
    const minutes = Math.floor(seconds / 60);
    const remainingSeconds = seconds % 60;
    return minutes+(minutes===0?'0':'') + ":" + (remainingSeconds < 10 ? '0' : '') + remainingSeconds;
  }

  useEffect(() => {
    if (Hls.isSupported()) {
      const hls = new Hls();
      hls.loadSource(src_link);
      hls.attachMedia(videoRef.current);
    }

    var intervalID;
    const handleLoadedMetadata = () => {
      setDuration(videoRef.current.duration)
      console.log('Video duration:', videoRef.current.duration);
    };

    const playTime = () => {
    intervalID = setInterval(function() {
      setPercentage((videoRef.current.currentTime/videoRef.current.duration)*100)
      }, 1000);
    }
    
    const pauseMusic = () => {
      clearInterval(intervalID);
    }

    const endMusic = () => {      
      clearInterval(intervalID);
      setPercentage(100)
      setPlay((prev) => !prev)
    }

    const updateTime = () => {
      console.log("시간변경")
    }

    if (videoRef.current) {
      videoRef.current.addEventListener('loadedmetadata', handleLoadedMetadata);
      videoRef.current.addEventListener('play', playTime)
      videoRef.current.addEventListener('pause', pauseMusic)
      videoRef.current.addEventListener('ended', endMusic)
      videoRef.current.addEventListener('timeUpdate',updateTime)
    }

    return () => {
      if (videoRef.current) {
        videoRef.current.removeEventListener('loadedmetadata', handleLoadedMetadata);
        videoRef.current.removeEventListener('play', playTime);
        videoRef.current.removeEventListener('pause', pauseMusic);
        videoRef.current.removeEventListener('ended', endMusic);
      }
    };
  }, [videoRef.current]);


  return (
    <div className="bg-base-content bg-opacity-50 rounded-t-xl text-base-100 pt-1 pb-1 fixed bottom-0 w-full">
      <div className="grid grid-cols-9">
        <div className="col-span-1 flex justify-end">
          <video ref={videoRef} visibility="hidden" src={src_link} className="w-1">
          </video>
          <div className="pe-3">
            <img className="h-20 w-15" src={music.imageSrc} alt="" />
          </div>
        </div>
        <div className="col-span-3 pe-4">
          <div className="flex justify-start pb-2">
            <span className="pe-4 text-lg">{music.title}</span>
            <span className="pe-2">{ music.creator}</span>
          </div>
          <div>
            <progress onClick={updateProgress} className="flex justify-start progress progress-primary w-full" value={ percentage} max="100"></progress>
          </div>
          <div className="flex justify-between text-sm leading-6 ps-1 pt-3">
            <div className="text-base-100">{formatTime(current)}</div>
            <div>
              <span className="text-neutral-400">{formatTime(duration)}</span>
              <span className="ps-3">
                <button>
                  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.8} stroke="currentColor" className="w-4 h-4">
                    <path strokeLinecap="round" strokeLinejoin="round" d="M19.114 5.636a9 9 0 0 1 0 12.728M16.463 8.288a5.25 5.25 0 0 1 0 7.424M6.75 8.25l4.72-4.72a.75.75 0 0 1 1.28.53v15.88a.75.75 0 0 1-1.28.53l-4.72-4.72H4.51c-.88 0-1.704-.507-1.938-1.354A9.009 9.009 0 0 1 2.25 12c0-.83.112-1.633.322-2.396C2.806 8.756 3.63 8.25 4.51 8.25H6.75Z" />
                  </svg>
                </button>
              </span>
            </div>
          </div>
        </div>
        <div className="flex">
            <div className="px-3 pt-3">
              <button onClick={backward}>
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.8} stroke="currentColor" className="w-6 h-6">
                <path strokeLinecap="round" strokeLinejoin="round" d="m18.75 4.5-7.5 7.5 7.5 7.5m-6-15L5.25 12l7.5 7.5" />
              </svg>
              </button>
            </div>
            <div>
            <button onClick={buttonClick} type="button" className="bg-primary text-slate-900 flex-none -my-4 mx-auto w-20 h-20 rounded-full ring-1 ring-slate-900/5 shadow-md flex items-center justify-center">
              {
                play
                  ?<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={2} stroke="currentColor" className="w-10 h-10">
                  <path strokeLinecap="round" strokeLinejoin="round" d="M15.75 5.25v13.5m-7.5-13.5v13.5" />
                </svg>
                  :<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={2} stroke="currentColor" className="w-10 h-10">
              <path strokeLinecap="round" strokeLinejoin="round" d="M5.25 5.653c0-.856.917-1.398 1.667-.986l11.54 6.347a1.125 1.125 0 0 1 0 1.972l-11.54 6.347a1.125 1.125 0 0 1-1.667-.986V5.653Z" />
            </svg>
            }
            </button>
            </div>
            <div className="px-3 pt-3">
              <button onClick={forward}>
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.8} stroke="currentColor" className="w-6 h-6">
                <path strokeLinecap="round" strokeLinejoin="round" d="m5.25 4.5 7.5 7.5-7.5 7.5m6-15 7.5 7.5-7.5 7.5" />
              </svg>
              </button>
            </div>
        </div>
      </div>
    </div>
  )
}

