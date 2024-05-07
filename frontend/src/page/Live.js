//라이브 페이지
import { useEffect, useState, useRef } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { OpenVidu } from "openvidu-browser";
import { useStateCallback } from "../hook/useStateCallback";
import axios from "axios";
import { useAuthStore } from "../store/AuthStore";
import { Link } from "react-router-dom";

function OpenViduVideoComponent({ streamManager, width, height }) {
  const videoRef = useRef();

  useEffect(() => {
    if (streamManager && !!videoRef) {
      streamManager.addVideoElement(videoRef.current);
    }
  }, [])

  useEffect(() => {
    if (streamManager && !!videoRef) {
      streamManager.addVideoElement(videoRef.current);
    }
  })

  return <video autoPlay={true} ref={videoRef} width={width} height={height} className="object-fill" />;
}

function UserVideoComponent({ streamManager, width, height, mainVideo = false }) {
  function getNicknameTag() {
    // Gets the nickName of the user
    return JSON.parse(streamManager.stream.connection.data).clientData;
  }

  return (
    <div>
      {streamManager !== undefined ? (
        <div className="relative" style={{ width, height }}>
          <OpenViduVideoComponent streamManager={streamManager} width={width} height={height} />
          {mainVideo ? (
            <div className="absolute top-[20px] left-[20px]">
              <svg width="100" height="35" viewBox="0 0 100 35" fill="none" xmlns="http://www.w3.org/2000/svg">
                <rect width="100" height="35" rx="17.5" fill="#FF0000"/>
                <path d="M23.5 23.3125C25.125 23.3125 26.5 22.75 27.625 21.625C28.75 20.5 29.3125 19.125 29.3125 17.5C29.3125 15.875 28.75 14.5 27.625 13.375C26.5 12.25 25.125 11.6875 23.5 11.6875C21.875 11.6875 20.5 12.25 19.375 13.375C18.25 14.5 17.6875 15.875 17.6875 17.5C17.6875 19.125 18.25 20.5 19.375 21.625C20.5 22.75 21.875 23.3125 23.5 23.3125ZM23.5 30C21.7917 30 20.1771 29.6719 18.6562 29.0156C17.1354 28.3594 15.8073 27.4635 14.6719 26.3281C13.5365 25.1927 12.6406 23.8646 11.9844 22.3438C11.3281 20.8229 11 19.2083 11 17.5C11 15.7708 11.3281 14.1458 11.9844 12.625C12.6406 11.1042 13.5365 9.78125 14.6719 8.65625C15.8073 7.53125 17.1354 6.64062 18.6562 5.98438C20.1771 5.32812 21.7917 5 23.5 5C25.2292 5 26.8542 5.32812 28.375 5.98438C29.8958 6.64062 31.2188 7.53125 32.3438 8.65625C33.4688 9.78125 34.3594 11.1042 35.0156 12.625C35.6719 14.1458 36 15.7708 36 17.5C36 19.2083 35.6719 20.8229 35.0156 22.3438C34.3594 23.8646 33.4688 25.1927 32.3438 26.3281C31.2188 27.4635 29.8958 28.3594 28.375 29.0156C26.8542 29.6719 25.2292 30 23.5 30ZM23.5 28.125C26.4583 28.125 28.9688 27.0885 31.0312 25.0156C33.0938 22.9427 34.125 20.4375 34.125 17.5C34.125 14.5417 33.0938 12.0312 31.0312 9.96875C28.9688 7.90625 26.4583 6.875 23.5 6.875C20.5625 6.875 18.0573 7.90625 15.9844 9.96875C13.9115 12.0312 12.875 14.5417 12.875 17.5C12.875 20.4375 13.9115 22.9427 15.9844 25.0156C18.0573 27.0885 20.5625 28.125 23.5 28.125Z" fill="white"/>
                <path d="M49.4091 23V11.3636H50.8182V21.75H56.2273V23H49.4091ZM59.8182 11.3636V23H58.4091V11.3636H59.8182ZM63.1207 11.3636L66.5753 21.1591H66.7116L70.1662 11.3636H71.6435L67.3707 23H65.9162L61.6435 11.3636H63.1207ZM73.456 23V11.3636H80.4787V12.6136H74.8651V16.5455H80.1151V17.7955H74.8651V21.75H80.5696V23H73.456Z" fill="white"/>
              </svg>
            </div>) : null}
          <div className="absolute bg-white left-0 bottom-0 px-2">
            <p className="text-lg text-black">{getNicknameTag()}</p>
          </div>
        </div>
      ) : null}
    </div>
  );
}

export default function Live() {
  const location = useLocation();
  const token = location.state?.token || undefined;
  const role = location.state?.role || undefined;
  const title = location.state?.title || "No title";
  const describe = location.state?.describe || "";
  const contentList = location.state?.contentList || [];
  const guestList = location.state?.contentList || [];

  const navigate = useNavigate();

  const { accessToken, nickname } = useAuthStore();

  // OpenVidu 관련 상태 값
  const ov = useRef(null); 
  const [session, setSessionCallback] = useStateCallback();
  const [mainStreamManager, setMainStreamManager] = useState();  // Main video of the page. Will be the 'publisher' or one of the 'subscribers'
  const [publisher, setPublisher] = useState();
  const [subscribers, setSubscribers] = useState([]);
  const [currentVideoDevice, setCurrentVideoDevice] = useState();

  useEffect(() => {
    window.addEventListener('beforeunload', onbeforeunload);
    return () =>{
      window.removeEventListener('beforeunload', onbeforeunload);
      if(session) leaveSession();
    } 
  }, [session])

  const onbeforeunload = (e) => {
    e.preventDefault();
    leaveSession();
  }

  const handleMainVideoStream = (stream) => {
    if (mainStreamManager !== stream) {
      setMainStreamManager(stream);
    }
  }

  const deleteSubscriber = (streamManager) => {
    let newSubscribers = [...subscribers];
    let index = newSubscribers.indexOf(streamManager, 0);
    if (index > -1) {
      newSubscribers.splice(index, 1);
      setSubscribers(newSubscribers);
    }
  }

  const leaveSession = () => {
    session?.disconnect();

    // 모든 상태 값 해제
    ov.current = null;
    setSessionCallback(undefined);
    setSubscribers([]);
    setMainStreamManager(undefined);
    setPublisher(undefined);

    // 세션을 끊는 사람이 Owner이면 해당 라이브는 파기
    if (role === "OWNER") {
      const serverUrl = process.env.REACT_APP_TEST_SERVER_BASE_URL;
      axios({
        method: "delete",
        url: `${serverUrl}/live`,
        headers: {
          Authorization: `Bearer ${accessToken}`
        }
      })
      .then((response) => {
        console.log(response.data);
      })
      .catch((error) => {
        console.log(error.message);
      })
    }
  }

  const joinSession = () => {
    console.log(role);

    const openVidu = new OpenVidu(); // OpenVidu 객체 생성
    ov.current = openVidu;

    const initSession = ov.current.initSession();
    
    // 세션 초기화 및 연결
    setSessionCallback(initSession, (s) => {
      const mySession = s;

      // On every new Stream received...
      mySession.on('streamCreated', (event) => {
        // Subscribe to the Stream to receive it. Second parameter is undefined
        // so OpenVidu doesn't create an HTML video by its own
        var subscriber = mySession.subscribe(event.stream, undefined);
        let newSubscribers = [...subscribers];
        newSubscribers.push(subscriber);
        setSubscribers(newSubscribers);
      });

      // On every Stream destroyed...
      mySession.on('streamDestroyed', (event) => {
        // Remove the stream from 'subscribers' array
        deleteSubscriber(event.stream.streamManager);
      });

      // On every asynchronous exception...
      mySession.on('exception', (exception) => {
        console.warn(exception);
      });

      // 세션에 연결
      mySession.connect(token, { clientData: (nickname ? nickname : "No Name") })
        .then(async () => {
          // 세션에 연결하는 클라이언트의 역할이 Publisher 또는 Owner이면 if문 안의 작업을 수행
          if (role === "GUEST" || role === "OWNER") {
            // 퍼블리셔 생성
            let publisher = await ov.current.initPublisherAsync(undefined, {
              audioSource: undefined, // 오디오 소스
              videoSource: undefined, // 비디오 소스
              publishAudio: true, // 오디오 퍼블리시 여부
              publishVideo: true, // 비디오 퍼블리시 여부
              resolution: '640x480', // 비디오 해상도
              frameRate: 30, // 비디오 주사율
              insertMode: 'APPEND', // How the video is inserted in the target element 'video-container'
              mirror: false, // 당사자 로컬 비디오의 거울 반전 여부
            });
            // 생성된 퍼블리셔를 퍼블리싱
            mySession.publish(publisher);

            // 현재 사용중인 비디오 장치를 획득
            var devices = await ov.current.getDevices();
            var videoDevices = devices.filter(device => device.kind === 'videoinput');
            var currentVideoDeviceId = publisher.stream.getMediaStream().getVideoTracks()[0].getSettings().deviceId;
            var currentVideoDevice = videoDevices.find(device => device.deviceId === currentVideoDeviceId);

            // Set the main video in the page to display our webcam and store our Publisher
            setCurrentVideoDevice(currentVideoDevice);
            setMainStreamManager(publisher);
            setPublisher(publisher);
          }
        })
        .catch((error) => {
          console.log(error);
          alert("에러가 발생했습니다")
          navigate(-1); 
        });
    })
  }

  useEffect(() => {
    if(!session) {
      joinSession();
    }
  }, [session]);

  return (
    <>
      <div className="pt-4 grid grid-cols-10">
        <div className="col-start-3 col-end-9">
          <div className="mt-6 mb-4 flex justify-center items-center">
            {mainStreamManager !== undefined ? (
              <UserVideoComponent streamManager={mainStreamManager} width={640} height={480} mainVideo />
            ) : null}
            {(mainStreamManager === undefined && subscribers.length > 0) ? (
              <div className="inline-block mr-4 relavive">
                <UserVideoComponent streamManager={subscribers[0]} width={640} height={480} mainVideo />
              </div>
            ) : null}
          </div>
          <div style={{ paddingLeft: '180px' }}>
            <p className="text-white font-bold text-2xl text-left">{title}</p>
            {contentList.length > 0 ? (
              contentList.map((content) => {
                <Link to={`/content/${content.id}`}>
                  <p className="text-white text-semibold text-left">@{content.title}</p>
                </Link>
              })) : (
                <p className="text-white text-semibold text-left">사용중인 컨텐츠가 없습니다.</p>
              )
            }
            <p className="text-white text-left">{describe}</p>
          </div>
        </div>
      </div>
    </>
  );
}