// 컨텐츠(멜로디/보컬.음반) 항목

import { useState, useEffect } from "react";
import ContentsArt from "./ContentsArt.js";
import ContentsTitleAndArtist from "./ContentsTitleAndArtist.js";
import axios from "axios";
import { useAuthStore } from "../store/AuthStore.js";
import { Link } from "react-router-dom";

export default function ContentsItem({
  contentId,
  size,
  albumArt,
  title = "Title",
  artist = "artist",
  showFavorite = true,
  isFavorite = false,
}) {
  const [favorite, setFavorite] = useState(false);
  const { accessToken } = useAuthStore();

  useEffect(() => {
    setFavorite(isFavorite);
  }, [isFavorite])

  const handleOnClickFavorite = (e) => {
    e.preventDefault();

    const serverUrl = process.env.REACT_APP_TEST_SERVER_BASE_URL;

    axios({
      method: favorite ? "delete" : "post",
      url: `${serverUrl}/content/star`,
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
      data: {
        contentId: contentId,
      },
    })
      .then((response) => {
        setFavorite(!favorite);
      })
      .catch((error) => {
        alert(error.response.data.message);
      });
  };

  return (
    <Link
      to={`/content/${contentId}`}
      style={{ width: `${size}px` }}
      className="items-center hover:shadow-lg hover:shadow-base-content rounded-md group"
    >
      <div className="group-hover:shadow-sm group-hover:shadow-base-content rounded-md">
        <ContentsArt
          size={size}
          contentId={contentId}
          albumArt={albumArt}
          isFavorite={favorite}
          onClickFavorite={handleOnClickFavorite}
          showFavorite={showFavorite}
        />
      </div>
      <ContentsTitleAndArtist title={title} artist={artist} />
    </Link>
  );
}
