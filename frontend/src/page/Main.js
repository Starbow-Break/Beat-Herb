// 메인 페이지
import MainButton from '../components/MainButton';
import { Link } from 'react-router-dom';
import SearchBar from '../components/SearchBar';
import UserButton from '../components/UserButton';
import contentsIcon from "../assets/contents.png";
import liveIcon from "../assets/live.png";
import workplaceIcon from "../assets/workplace.png";

export default function Main() {
  return (
    <div>
      <div className="fixed top-2 right-2 z-10">
        <UserButton />  
      </div>
      
      <div className="absolute w-full right-1/2 bottom-1/2 translate-x-1/2 translate-y-1/2 flex flex-col items-center">
        <div>
          <p className="text-8xl text-primary">BeatHerb</p>
        </div>

        <div className="my-16 w-1/2 min-w-96">
          <SearchBar />
        </div>
        
        <div className="flex flex-row justify-center">
          <Link to="/board/contents" className="text-primary-content hover:text-primary-content">
            <div className="flex mx-1.5">
              <MainButton imgSrc={contentsIcon} text="컨텐츠" />
            </div>
          </Link>
          <Link to="/board/live" className="text-primary-content hover:text-primary-content">
            <div className="flex mx-1.5">
              <MainButton imgSrc={liveIcon} text="Live" />
            </div>
          </Link>
          <Link to="/workplace" className="text-primary-content hover:text-primary-content">
            <div className="flex mx-1.5">
              <MainButton imgSrc={workplaceIcon} text="작업실" />
            </div>
          </Link>
        </div>
      </div>
    </div>
  );
}