import Record from "../components/Record";
import Piano from "../components/Piano";
import Synth from "../components/Synth";
import Drum from "../components/Drum";
import MusicWave from "../components/MusicWave";
import { useState, useRef } from "react";
import { useLocation } from "react-router-dom";
import NavBar from "../components/NavBar";
export default function WorkPlace() {
  const location = useLocation();
  const childMusicWaveRef = useRef(null);
  const getRecordResult = (url) => {
    // setRecordResult(url)
    console.log(url);
    console.log("childMusic", childMusicWaveRef.current);

    fetch(url)
      .then((response) => response.blob())
      .then((blob) =>  new File([blob], instrument + ".webm", {
          type: "audio/webm",
          lastModified: new Date(),
      }))
      .then((file) => childMusicWaveRef.current.handleRecordingUpload(file))
      .catch((e) => {
        console.error(e);
      });
    
    // let resurl = URL.createObjectURL(file);
    // childMusicWaveRef.current.handleRecordingUpload(resurl);
  };
  const tabs = [
    {
      value: "record",
      title: "Record",
      img: (
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
            d="M12 18.75a6 6 0 0 0 6-6v-1.5m-6 7.5a6 6 0 0 1-6-6v-1.5m6 7.5v3.75m-3.75 0h7.5M12 15.75a3 3 0 0 1-3-3V4.5a3 3 0 1 1 6 0v8.25a3 3 0 0 1-3 3Z"
          />
        </svg>
      ),
    },
    {
      value: "piano",
      title: "Piano",
      img: (
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
            d="M13.5 16.875h3.375m0 0h3.375m-3.375 0V13.5m0 3.375v3.375M6 10.5h2.25a2.25 2.25 0 0 0 2.25-2.25V6a2.25 2.25 0 0 0-2.25-2.25H6A2.25 2.25 0 0 0 3.75 6v2.25A2.25 2.25 0 0 0 6 10.5Zm0 9.75h2.25A2.25 2.25 0 0 0 10.5 18v-2.25a2.25 2.25 0 0 0-2.25-2.25H6a2.25 2.25 0 0 0-2.25 2.25V18A2.25 2.25 0 0 0 6 20.25Zm9.75-9.75H18a2.25 2.25 0 0 0 2.25-2.25V6A2.25 2.25 0 0 0 18 3.75h-2.25A2.25 2.25 0 0 0 13.5 6v2.25a2.25 2.25 0 0 0 2.25 2.25Z"
          />
        </svg>
      ),
    },
    {
      value: "synth",
      title: "Synth",
      img: (
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
            d="M13.5 16.875h3.375m0 0h3.375m-3.375 0V13.5m0 3.375v3.375M6 10.5h2.25a2.25 2.25 0 0 0 2.25-2.25V6a2.25 2.25 0 0 0-2.25-2.25H6A2.25 2.25 0 0 0 3.75 6v2.25A2.25 2.25 0 0 0 6 10.5Zm0 9.75h2.25A2.25 2.25 0 0 0 10.5 18v-2.25a2.25 2.25 0 0 0-2.25-2.25H6a2.25 2.25 0 0 0-2.25 2.25V18A2.25 2.25 0 0 0 6 20.25Zm9.75-9.75H18a2.25 2.25 0 0 0 2.25-2.25V6A2.25 2.25 0 0 0 18 3.75h-2.25A2.25 2.25 0 0 0 13.5 6v2.25a2.25 2.25 0 0 0 2.25 2.25Z"
          />
        </svg>
      ),
    },
    {
      value: "drum",
      title: "Drum",
      img: (
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
            d="M13.5 16.875h3.375m0 0h3.375m-3.375 0V13.5m0 3.375v3.375M6 10.5h2.25a2.25 2.25 0 0 0 2.25-2.25V6a2.25 2.25 0 0 0-2.25-2.25H6A2.25 2.25 0 0 0 3.75 6v2.25A2.25 2.25 0 0 0 6 10.5Zm0 9.75h2.25A2.25 2.25 0 0 0 10.5 18v-2.25a2.25 2.25 0 0 0-2.25-2.25H6a2.25 2.25 0 0 0-2.25 2.25V18A2.25 2.25 0 0 0 6 20.25Zm9.75-9.75H18a2.25 2.25 0 0 0 2.25-2.25V6A2.25 2.25 0 0 0 18 3.75h-2.25A2.25 2.25 0 0 0 13.5 6v2.25a2.25 2.25 0 0 0 2.25 2.25Z"
          />
        </svg>
      ),
    },
  ];
  const [instrument, setInstrument] = useState("record");
  let content;

  switch (instrument) {
    case "record":
      content = <Record getRecordResult={getRecordResult} />;
      break;
    case "piano":
      content = <Piano getRecordResult={getRecordResult} />;
      break;
    case "synth":
      content = <Synth getRecordResult={getRecordResult} />;
      break;
    case "drum":
      content = <Drum getRecordResult={getRecordResult} />;
      break;
  }

  return (
    <div>
      <div className="fixed top-0 w-full z-20">
        <NavBar />
      </div>
      <div className="h-full">
        <div className="flex w-full h-4/6 pt-20">
          <MusicWave ref={(el) => (childMusicWaveRef.current = el)}></MusicWave>
        </div>
        <div className="flex w-full h-2/6 my-7">
          <div className="w-2/12 bg-base-200 order-gray-300 p-5">
            <div className="grid grid-cols-1 gap-3">
              {tabs.map((tab) => (
                <button
                  key={tab.value}
                  role="tab"
                  onClick={() => setInstrument(tab.value)}
                  style={{
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center",
                    padding: "10px",
                    backgroundColor: instrument === tab.value ? "#070707" : "#202020",
                  }}
                >
                  {tab.img}
                  {tab.title}
                </button>
              ))}
            </div>
          </div>
          <div className="rounded-md bg-base-300 w-10/12 mx-5">{content}</div>
        </div>
      </div>
    </div>
  );
}
