import { useRef, useState, useEffect } from "react";
import { produce } from "immer";
import axios from "axios";

export default function Admin() {
  const deleteModalRef = useRef();
  const addModalRef = useRef();
  const [hashtagList, setHashtagList] = useState([]);
  const [addModalInput, setAddModalInput] = useState("");
  const [selectedHashtagId, setSelectedHashtagId] = useState(null);

  useEffect(() => {
    const serverUrl = process.env.REACT_APP_TEST_SERVER_BASE_URL;

    axios({
      method: "get",
      url: `${serverUrl}/content/hashtag`
    })
    .then((response) => {
      setHashtagList(response.data.data);
    })
    .catch((error) => {
      alert("오류가 발생했습니다.")
    })
  }, []);

  // 해시태그 추가
  // 추후 백엔드랑 연동해야 함
  const addHashtag = () => {
    const serverUrl = process.env.REACT_APP_TEST_SERVER_BASE_URL;

    axios({
      method: "post",
      url: `${serverUrl}/content/hashtag`,
      data: {
        name: addModalInput
      }
    })
    .then((response) => {
      setHashtagList(
        produce(draft => {
          draft.push(response.data.data);
        })
      );
      setAddModalInput("");
    })
    .catch((error) => {
      alert("오류가 발생했습니다.")
    })
  }

  // 해시태그 삭제
  // 추후 백엔드랑 연동해야 함
  const deleteHashtag = () => {
    const serverUrl = process.env.REACT_APP_TEST_SERVER_BASE_URL;

    axios({
      method: "delete",
      url: `${serverUrl}/content/hashtag`,
      data: {
        id: selectedHashtagId
      }
    })
    .then((response) => {
      setHashtagList(
        produce(draft => {
          const hashtag_index = hashtagList.findIndex(hashtag => hashtag.id === selectedHashtagId);
          draft.splice(hashtag_index, 1);
        })
      );
      setSelectedHashtagId(null);
    })
    .catch((error) => {
      alert(error.response.data.message);
    })
  }

  // 해시태그 추가 버튼을 클릭하면 해시태그 추가 모달이 뜬다
  const handleOnClickAddHashtag = (e) => {
    addModalRef.current?.showModal();
  }

  // 해시태크 토큰을 클릭하면 삭제 모달이 뜬다
  const handleOnClickHashtagToken = (e) => {
    const TokenId = e.target.id;
    const hashtagId = Number(TokenId.slice(7));
    setSelectedHashtagId(hashtagId);
    deleteModalRef.current?.showModal();
  }

  return (
    <>
      <div>
        <p className="text-4xl text-primary text-semibold my-8">관리자 페이지</p>
        <div className="flex gap-12">
          <p className="text-2xl text-primary text-semibold mx-4 mb-4 text-left">해시태그 목록</p>
          <button
            className="btn btn-sm btn-ghost text-base-content"
            onClick={handleOnClickAddHashtag}
          >
            + 해시태그 추가
          </button>
        </div>
        <div className="flex flex-wrap m-4 gap-4">
          {
            hashtagList.map((hashtag) => (
              <div 
                key={"hashtag" + hashtag.id} 
                className="badge badge-lg badge-primary text-primary-content cursor-pointer" 
                id={"hashtag" + hashtag.id}
                onClick={handleOnClickHashtagToken}
              >
                {hashtag.name}
              </div>
            ))
          }
        </div>
      </div>

      <dialog className="modal" ref={deleteModalRef}>
        <div className="modal-box bg-base-200">
          <p className="py-4">선택한 해시태그를 지우시겠습니까?</p>
          <div className="modal-action">
            <form method="dialog">
            <button className="btn btn-outline btn-error" onClick={deleteHashtag}>Delete</button>
            </form>
          </div>
        </div>
        <form method="dialog" className="modal-backdrop">
          <button>close</button>
        </form>
      </dialog>

      <dialog className="modal" ref={addModalRef}>
        <div className="modal-box bg-base-200">
          <p className="py-4">추가할 해시태그를 입력해주세요</p>
          <input
            type="text"
            placeholder="Type here"
            className="input input-bordered w-full max-w-xs"
            value={addModalInput}
            onChange={(e) => setAddModalInput(e.target.value)}
          />
          <div className="modal-action">
            <form method="dialog">
            <button 
              className="btn btn-outline btn-primary"
              onClick={addHashtag}
            >
              Add
            </button>
            </form>
          </div>
        </div>
        <form method="dialog" className="modal-backdrop">
          <button>close</button>
        </form>
      </dialog>
    </>
  );
}