// 상태 변경 후 콜백을 수행하는 커스텀 훅
import { useState, useRef, useCallback, useEffect } from "react";

export function useStateCallback(initialState) {
  const [state, setState] = useState(initialState);
  const cbRef = useRef(null);

  // 상태 변경시 해당 함수를 사용
  // 새로운 상태값과 콜백 함수를 인자로 받음
  // setter 함수를 사용해서 상태를 변경하면 변경 후 콜백 함수가 동작함
  const setStateCallback = useCallback((state, cb) => {
    cbRef.current = cb; 
    setState(state);
  }, []) 

  // 콜백 함수가 동작
  // state가 변하면 최근 값을 사용해서 콜백 함수가 실행된다.
  useEffect(() => {
    if (state && cbRef.current) {
      cbRef.current(state);
      cbRef.current = null;
    }
  }, [state]);

  return [state, setStateCallback];
}