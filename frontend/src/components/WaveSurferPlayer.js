import { useRef } from "react";
import { useWavesurfer } from '@wavesurfer/react';

export default function WaveSurferPlayer({ url }) {
  const recordedRef = useRef(null);
  
  const { wavesurfer, isPlaying } = useWavesurfer({
    container: recordedRef,
    height: 100,
    waveColor: 'hsl(161, 87%, 49%)',
    progressColor: 'rgb(100, 0, 100)',
    minPxPerSec: 10,
    url: url,
  });
  
  const onPlayPause = () => {
    wavesurfer && wavesurfer.playPause()
  };

  return(
    <>
      <div ref={recordedRef}></div>
      <button className="btn btn-primary" onClick={onPlayPause}>
        {isPlaying ? 'Pause' : 'Play'}
      </button>
    </>
  )
}