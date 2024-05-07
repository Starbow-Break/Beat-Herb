//Dm 컴포넌트
import { useState } from "react";
import DmDetail from "./DmDetail";

export default function Dm() {
  const [personNum] = useState(5);
  const [selectedUser, setSelectedUser] = useState(null);

  const handleUserSelect = (user) => {
    setSelectedUser(user + 1);
  };

  const handleBackClick = () => {
    setSelectedUser(null);
  };

  if (selectedUser) {
    return (
      <div>
        <button onClick={handleBackClick} className="flex">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            strokeWidth="1.5"
            stroke="currentColor"
            className="w-6 h-6"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              d="M9 15 3 9m0 0 6-6M3 9h12a6 6 0 0 1 0 12h-3"
            />
          </svg>
        </button>
        <DmDetail user={selectedUser} />
      </div>
    );
  }

  return (
    <div>
      <div className="flex mx-1.5 justify-center items-center">
        <p className="text-5xl text-white">DM</p>
      </div>
      {Array(personNum)
        .fill()
        .map((v, index) => {
          return (
            <div
              className="w-1/2 mx-auto border-b border-base-cotent hover:bg-base-100"
              key={index}
              onClick={() => handleUserSelect(index)}
            >
              <div key={index} className="flex pt-6 items-center justify-between">
                <div className="inline-block">
                  <img
                    src="https://img.freepik.com/free-psd/3d-icon-social-media-app_23-2150049569.jpg?w=740&t=st=1706664646~exp=1706665246~hmac=3f0eac27cb02e203eb35c307d60daf8dd9edced316e689b461b9a8a67fb9b31b"
                    width="70"
                    height="70"
                    className="inline-block mr-2"
                    alt=""
                  />
                  <div className="inline-block">
                    <p className="text-white text-left mb-2">user{index + 1}</p>
                    <p className="text-primary text-left mb-2">dm message</p>
                  </div>
                </div>
                <div className="inline-block">
                  <svg width="100" height="100">
                    <circle cx="50" cy="50" r="10" fill="red" />
                    <text
                      x="50"
                      y="50"
                      textAnchor="middle"
                      dominantBaseline="middle"
                      fill="white"
                      fontSize="12"
                    >
                      10
                    </text>
                  </svg>
                </div>
              </div>
            </div>
          );
        })}
    </div>
  );
}
