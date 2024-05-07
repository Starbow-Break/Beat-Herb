import * as Tone from "tone";
import React, { useEffect, useState } from "react";

const Synth = ({ getRecordResult }) => {
  const [isRecording, setIsRecording] = useState(false);
  const [isPlaying, setIsPlaying] = useState(false);
  const [recorder, setRecorder] = useState(null);
  const [playSynth, setPlaySynth] = useState(null);
  const [result, setResult] = useState(null);
  const [audio, setAudio] = useState(null);
  const [showInstrument,setShowInstrument]=useState(false)

  useEffect(() => {
    Tone.loaded().then(() => {
      console.log("Audio is loaded");
      const newRecorder = new Tone.Recorder();
      setRecorder(newRecorder);

      const newPlaySynth = new Tone.Synth({
        urls: {
          C4: "C4.mp3",
          "D#4": "Ds4.mp3",
          "F#4": "Fs4.mp3",
          A4: "A4.mp3",
        },
        release: 1,
        baseUrl: "https://tonejs.github.io/audio/salamander/",
      })
        .toDestination()
        .connect(newRecorder);
      setPlaySynth(newPlaySynth);
    }).then(setShowInstrument(true));
  }, []);

  const playNote = (note) => {
    playSynth.triggerAttackRelease(note, 4);
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

  const whiteKey = {
    border: "1px solid #000000",
    width: "50px",
    height: "180px",
    backgroundColor: "#FFFFFF",
    borderBottomLeftRadius: "10px",
    borderBottomRightRadius: "10px",
  };
  const blackKey = {
    border: "1px solid #000000",
    width: "50px",
    height: "100px",
    marginLeft: "-25px",
    marginRight: "-25px",
    backgroundColor: "#000000",
    borderBottomLeftRadius: "10px",
    borderBottomRightRadius: "10px",
  };

  return (
    showInstrument?
    <div>
      <div className="flex p-5" style={{ justifyContent: "center" }}>
        <div style={whiteKey} onClick={() => playNote("C3")}></div>
        <div style={blackKey} onClick={() => playNote("C#3")}></div>
        <div style={whiteKey} onClick={() => playNote("D3")}></div>
        <div style={blackKey} onClick={() => playNote("D#3")}></div>
        <div style={whiteKey} onClick={() => playNote("E3")}></div>
        <div style={whiteKey} onClick={() => playNote("F3")}></div>
        <div style={blackKey} onClick={() => playNote("F#3")}></div>
        <div style={whiteKey} onClick={() => playNote("G3")}></div>
        <div style={blackKey} onClick={() => playNote("G#3")}></div>
        <div style={whiteKey} onClick={() => playNote("A3")}></div>
        <div style={blackKey} onClick={() => playNote("A#3")}></div>
        <div style={whiteKey} onClick={() => playNote("B3")}></div>
        <div style={whiteKey} onClick={() => playNote("C4")}></div>
        <div style={blackKey} onClick={() => playNote("C#4")}></div>
        <div style={whiteKey} onClick={() => playNote("D4")}></div>
        <div style={blackKey} onClick={() => playNote("D#4")}></div>
        <div style={whiteKey} onClick={() => playNote("E4")}></div>
        <div style={whiteKey} onClick={() => playNote("F4")}></div>
        <div style={blackKey} onClick={() => playNote("F#4")}></div>
        <div style={whiteKey} onClick={() => playNote("G4")}></div>
        <div style={blackKey} onClick={() => playNote("G#4")}></div>
        <div style={whiteKey} onClick={() => playNote("A4")}></div>
        <div style={blackKey} onClick={() => playNote("A#4")}></div>
        <div style={whiteKey} onClick={() => playNote("B4")}></div>
        <div style={whiteKey} onClick={() => playNote("C5")}></div>
        <div style={blackKey} onClick={() => playNote("C#5")}></div>
        <div style={whiteKey} onClick={() => playNote("D5")}></div>
        <div style={blackKey} onClick={() => playNote("D#5")}></div>
        <div style={whiteKey} onClick={() => playNote("E5")}></div>
        <div style={whiteKey} onClick={() => playNote("F5")}></div>
        <div style={blackKey} onClick={() => playNote("F#5")}></div>
        <div style={whiteKey} onClick={() => playNote("G5")}></div>
        <div style={blackKey} onClick={() => playNote("G#5")}></div>
        <div style={whiteKey} onClick={() => playNote("A5")}></div>
        <div style={blackKey} onClick={() => playNote("A#5")}></div>
        <div style={whiteKey} onClick={() => playNote("B5")}></div>
        <div style={whiteKey} onClick={() => playNote("C6")}></div>
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

export default Synth;
