// 라이브 항목
export default function LiveItem({ imgSrc, title = "Title" }) {
  return (
    <div className="w-[300px] relative">
      <img className="w-[300px] h-[225px] rounded-md" src={imgSrc} alt="" />
      <p className="w-full text-base truncate my-2 text-base-content">{ title }</p>
    </div>
  );
}