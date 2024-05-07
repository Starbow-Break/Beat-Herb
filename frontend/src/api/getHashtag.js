import axios from "axios";

export function getHashtag() {
  return axios({
    method: "GET",
    url: process.env.REACT_APP_TEST_SERVER_BASE_URL +"/content/hashtag",
    mode: "cors",
    params: {
      name: null,
    },
  });
}
