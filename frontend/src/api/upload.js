import axios from "axios";
// import { useAuthStore } from "../store/AuthStore";

// const [accessToken] = useAuthStore();

const apiClient = axios.create({
  baseURL: process.env.REACT_APP_TEST_SERVER_BASE_URL,
  mode: "cors",
  headers: {
    "Content-Type": "multipart/form-data",
    // Authorization: 
  },
});

export function uploadMusic(data) {
  console.log("axios", data);
  return apiClient.post("/content/upload", data);
}
// export function loadMusic(data) {
//   console.log("axios", data);
//   return apiClient.get("/content/load/" + data);
// }
export function uploadShorts(data) {
  console.log("axios", data);
  return apiClient.post("/shorts/upload", data);
}
