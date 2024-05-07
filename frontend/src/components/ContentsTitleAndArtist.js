// 각 컨텐츠의 제목과 아티스트

export default function ContentsTitleAndArtist({ title, artist }) {
  return (
    <>
      <p className="w-full text-base truncate my-2 text-base-content">{ title }</p>
      <p className="w-full text-sm truncate text-base-content">{ artist }</p>
    </>
  );
}