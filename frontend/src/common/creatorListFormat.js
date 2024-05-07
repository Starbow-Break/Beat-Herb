import { Fragment } from "react";
import { Link } from "react-router-dom"; 

export const creatorListFormat = (creatorList) => {
  const creatorView = creatorList.map((creator, index) => {
    return (
      <Fragment key={"creator" + creator.id}>
        {index > 0 && <p>, </p>}
        <Link to={`/mypage/${creator.id}`}>{creator.nickname ? creator.nickname : "No Name"}</Link>
      </Fragment>
    )
  })

  return creatorView;
}

export const creatorListFormatWithoutLink = (creatorList) => {
  let creatorText = "";
  creatorList.forEach((creator, index) => {
    console.log(creator);
    if(index > 0) {
      creatorText += ", ";
    }
    creatorText += creator.nickname;
  })

  return creatorText;
}