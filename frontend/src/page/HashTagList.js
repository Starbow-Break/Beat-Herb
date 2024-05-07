import React, { useState, useEffect } from "react";
import axios from "axios";

// 카테고리 선택 기능 완료되었음. 이거 유저 수정 페이지랑 음악 업로드에 넣어보기

export default function HashTagList({ data, setData }) {
  // const [data, setData] = useState(null);
  const [categories, setCategories] = useState([]);
  // const [showCategories, setShowCategories] = useState(false);

  // const handleAddCategory = () => {
  //   setShowCategories(!showCategories);
  // };

  const handleCategoryClick = (categoryId, index) => {
    // 카테고리가 이미 선택된 상태인지 확인
    const categoryIndex = categories.findIndex((cat) => cat === categoryId);

    if (categoryIndex === -1) {
      // 배열에 카테고리가 없으면 추가
      setCategories([...categories, categoryId]);
    } else {
      // 배열에 카테고리가 이미 있으면 제거
      const newCategories = [...categories];
      newCategories.splice(categoryIndex, 1);
      setCategories(newCategories);
    }

    // 클릭된 버튼의 색상을 변경
    const newData = data.map((value, i) => {
      if(i === index) {
        return {...value, selected: !value.selected};
      } else {
        return value;
      }
    });
    
    setData(newData);
  };

  // useEffect(() => {
  //   // 서버에서 데이터를 가져오는 비동기 함수를 호출
  //   const fetchData = async () => {
  //     try {
  //       const serverURL = process.env.REACT_APP_GETHASHTAG_SERVER_URL;
  //       console.log(serverURL);
  //       const response = await axios({
  //         method: "GET",
  //         url: `${serverURL}`,
  //         mode: "cors",
  //         params: {
  //           name: null,
  //         },
  //       });
  //       // 데이터를 가져온 후 각 데이터에 selected 속성 추가
  //       const initialData = response.data.data.map((item) => ({
  //         ...item,
  //         selected: false,
  //       }));
  //       setData(initialData); // 데이터를 상태에 저장
  //     } catch (error) {
  //       console.error("Error fetching data:", error);
  //     }
  //   };

  //   // 컴포넌트가 마운트되었을 때 한 번만 데이터를 가져오도록 설정
  //   fetchData();
  // }, []);

  return (
    <div>
      {/* <button onClick={handleAddCategory}>+ 추가하기</button> */}

      <div className="flex flex-wrap">
        {data.map((value, index) => (
          <div key={index} className="p-1">
            <button
              className={`btn ${
                data[index].selected ? "btn-primary" : "btn-secondary"
              } btn-xs py-0`}
              onClick={() => handleCategoryClick(value.id, index)}
            >
              {value.name}
            </button>
          </div>
        ))}
      </div>

      {/* <div>
        <h3>추가된 카테고리들:</h3>
        <div className="flex flex-wrap">
          {categories.map((value, index) => (
            <div key={index} className="p-1">
              <button className="btn btn-secondary btn-xs">{value}</button>
            </div>
          ))}
        </div>
      </div> */}
    </div>
  );
}
