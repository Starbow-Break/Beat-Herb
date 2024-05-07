import * as Tone from "tone";
import React, { useEffect, useState } from "react";

const Drum = ({ getRecordResult }) => {
  const [isRecording, setIsRecording] = useState(false);
  const [isPlaying, setIsPlaying] = useState(false);
  const [recorder, setRecorder] = useState(null);
  const [playKick, setPlayKick] = useState(null);
  const [playSnare, setPlaySnare] = useState(null);
  const [playHiHat, setPlayHiHat] = useState(null);
  const [result, setResult] = useState(null);
  const [audio, setAudio] = useState(null);
  const [showInstrument,setShowInstrument]=useState(false)

  useEffect(() => {
    Tone.loaded().then(() => {
      console.log("Audio is loaded");
      
      const newRecorder = new Tone.Recorder();
      setRecorder(newRecorder);
      const kick = new Tone.MembraneSynth().toDestination().connect(newRecorder);
      const snare = new Tone.NoiseSynth().toDestination().connect(newRecorder);
      const hiHat = new Tone.MetalSynth().toDestination().connect(newRecorder);
      setPlayKick(kick);
      setPlaySnare(snare);
      setPlayHiHat(hiHat);
    }).then(setShowInstrument(true));
  }, []);

  const playNote = (event) => {
    switch (event) {
      case "kick":
        playKick.triggerAttackRelease("C2", "8n");
        break;
      case "snare":
        playSnare.triggerAttackRelease("16n");
        break;
      case "hiHat":
        playHiHat.triggerAttackRelease("32n");
        break;
    }
  };

  const startRecording = async () => {
    setIsRecording(true);
    await recorder.start();
    setAudio(null);
  };

  const stopRecording = async () => {
    if (isRecording) {
      setIsRecording(false);
      const recording = await recorder.stop();
      if (recording.size === 0) return;
      setResult(recording);
      setAudio(new Audio(URL.createObjectURL(recording)));
      // const url = URL.createObjectURL(recording);//blob객체
      // const audio = new Audio(url)
    }
  };

  const playRecording = () => {
    // const url = URL.createObjectURL(result);//blob객체
    // const audio = new Audio(url)
    setIsPlaying(true);
    audio.play();
  };
  const stopPlaying = () => {
    setIsPlaying(false);
    audio.pause();
  };

  const uploadRecording = () => {
    stopPlaying()
    const url = URL.createObjectURL(result); //blob객체
    getRecordResult(url);
    setAudio(null);
    console.log("upload");
  };

  return (
    showInstrument?
    <div className="pb-5">
      <div className="flex p-5 gap-10" style={{ justifyContent: "center", alignItems: "center" }}>
        <div onClick={() => playNote("kick")} className="text-xl border p-20 rounded-xl">
          Kick
        </div>
        <div onClick={() => playNote("snare")} className="text-xl border p-20 rounded-xl">
          Snare
        </div>
        <div onClick={() => playNote("hiHat")} className="text-xl border p-20 rounded-xl">
          {" "}
          HiHat
        </div>
      </div>
      <div className="flex" style={{ justifyContent: "center", alignItems: "center" }}>
        {isRecording ? (
          <button className="mx-3 btn btn-primary" onClick={stopRecording}>
            Stop
          </button>
        ) : (
          <button className="mx-3 btn btn-primary" onClick={startRecording}>
            Record
          </button>
        )}
        {audio ? (
          isPlaying ? (
            <div>
              <button className="mx-3 btn btn-primary" onClick={stopPlaying}>
                Pause
              </button>
              <button className="mx-3 btn btn-primary" onClick={uploadRecording}>
                작업실로 올리기
              </button>
            </div>
          ) : (
            <div>
              <button className="mx-3 btn btn-primary" onClick={playRecording}>
                Play
              </button>
              <button className="mx-3 btn btn-primary" onClick={uploadRecording}>
                작업실로 올리기
              </button>
            </div>
          )
        ) : (
          <div></div>
        )}
      </div>
    </div>:<div></div>
  );
};

export default Drum;
