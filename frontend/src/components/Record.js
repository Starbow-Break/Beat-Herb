import { useRef, useState, useEffect, useCallback } from "react";
import WaveSurfer from "wavesurfer.js";
import RecordPlugin from "wavesurfer.js/dist/plugins/record.js";
import WaveSurferPlayer from "./WaveSurferPlayer";

export default function AudioRecorder({ getRecordResult }) {
  const wavesurfer = useRef(null);
  const [recordedUrl, setRecordedUrl] = useState(null);
  const record = useRef(null);
  const [devices, setDevices] = useState([]);
  const [selectedDevice, setSelectedDevice] = useState("");
  const micRef = useRef(null);
  const progressRef = useRef(null);
  const pauseButtonRef = useRef(null);
  const recordButtonRef = useRef(null);
  const resultRef = useRef(null);
  const [playStatus, setPlayStatus] = useState(false);

  useEffect(() => {
    createWaveSurfer();
    RecordPlugin.getAvailableAudioDevices().then(setDevices);
  }, []);

  useEffect(() => {
    if (!record.current) return;
    record.current.on("record-progress", updateProgress);
    return () => {
      record.current.un("record-progress", updateProgress);
    };
  }, []);

  // WaveSurfer 생성 (녹음 중인 소리의 파형을 보여주는 WaveSurfer)
  const createWaveSurfer = useCallback(() => {
    // 이미 만들어진 WaveSurfer가 있다면 제거
    if (wavesurfer.current) {
      wavesurfer.current.destroy();
      wavesurfer.current = null;
    }

    // 새로운 WaveSurfer 생성
    const ws = WaveSurfer.create({
      container: micRef.current,
      waveColor: "rgb(200, 0, 200)",
      progressColor: "rgb(100, 0, 100)",
      minPxPerSec: 10,
    });

    // record plugin 설정
    const rec = ws.registerPlugin(
      RecordPlugin.create({ scrollingWaveform: true, renderRecordedAudio: false })
    );
    rec.on("record-end", renderRecordedAudio);

    // 새로만든 wavesurfer와 record 추가
    wavesurfer.current = ws;
    record.current = rec;
  }, []);

  // 녹음 progress를 업데이트
  const updateProgress = (time) => {
    const formattedTime = [
      Math.floor((time % 3600000) / 60000), // minutes
      Math.floor((time % 60000) / 1000), // seconds
    ]
      .map((v) => (v < 10 ? "0" + v : v))
      .join(":");

    progressRef.current.textContent = formattedTime;
  };

  // 녹음된 오디오를 렌더링
  const renderRecordedAudio = (webmBlob) => {
    const recordedUrl = URL.createObjectURL(webmBlob);
    console.log(webmBlob);
    setRecordedUrl(recordedUrl);
  };

  // 녹음 일시정지 버튼 클릭 시
  const handlePauseClick = () => {
    if (record.current.isPaused()) {
      record.current.resumeRecording();
      pauseButtonRef.current.textContent = "Pause";
      return;
    }

    record.current.pauseRecording();
    pauseButtonRef.current.textContent = "Resume";
  };

  // 녹음 버튼 클릭 시
  const handleRecordClick = () => {
    if (record.current.isRecording() || record.current.isPaused()) {
      //녹음 종료
      record.current.stopRecording();
      recordButtonRef.current.textContent = "Record";
      pauseButtonRef.current.style.display = "none";
      progressRef.current.style.display = "none";
      resultRef.current.style.display = "inline";
      pauseButtonRef.current.textContent = "Pause";
      micRef.current.style.display = "none";
      return;
    }

    recordButtonRef.current.disabled = true;

    record.current.startRecording({ deviceId: selectedDevice }).then(() => {
      //녹음시작
      recordButtonRef.current.textContent = "Stop";
      recordButtonRef.current.disabled = false;
      pauseButtonRef.current.style.display = "inline";
      progressRef.current.style.display = "inline";
      resultRef.current.style.display = "none";
      micRef.current.style.display = "inline";
    });
  };
  const uploadRecording = () => {
    getRecordResult(recordedUrl);
    console.log("upload");
  };

  return (
    <div className="p-5">
      <div id="mic" ref={micRef}></div>
      <div>
        <div ref={resultRef} style={{ display: "none" }}>
          {recordedUrl && <WaveSurferPlayer url={recordedUrl} />}
          <button className="mx-3 btn btn-primary" onClick={uploadRecording}>
            작업실로 올리기
          </button>
        </div>
      </div>

      <div>
        <div id="progress" ref={progressRef}></div>
        <div className="flex place-content-center gap-4">
          <button
            className="btn btn-primary"
            id="record"
            ref={recordButtonRef}
            onClick={handleRecordClick}
          >
            Record
          </button>
          <button
            className="btn btn-primary"
            id="pause"
            ref={pauseButtonRef}
            onClick={handlePauseClick}
            style={{ display: "none" }}
          >
            Pause{" "}
          </button>
          <select
            id="mic-select"
            className="select select-ghost w-full max-w-xs text-base-content"
            value={selectedDevice}
            onChange={(e) => setSelectedDevice(e.target.value)}
          >
            {devices.map((device) => (
              <option key={device.deviceId} value={device.deviceId}>
                {device.label || device.deviceId}
              </option>
            ))}
          </select>
        </div>
      </div>
    </div>
  );
}
