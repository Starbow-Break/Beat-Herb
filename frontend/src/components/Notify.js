// notify page
export default function Notify(){
  const notifyNum = 4;

  return(
    <div>
      <div className="flex justify-center items-center text-slate-50 pt-6">
        <p className="text-5xl pt-6 pb-12">Notification</p>
      </div>
      <div>
      {
        Array(notifyNum).fill().map((v,i)=>i+1).map((value, index) => {
          return (
            <div key={index} className="flex justify-center">
              <div className='bg-opacity-15 bg-primary mb-10 max-w-2xl w-full rounded-2xl'>
                <img className='w-28 h-28' src={require('../assets/bell.svg').default} alt="bell"/>
                <div key={index} className="relative flex justify-center h-24">
                  <div>
                    <p className="pl-4 font-light text-sm text-left">2023-02-02</p>
                    <img src={require('../assets/chatBubble.svg').default} className='mt-1' alt="chat bubble"/>
                    <p className="absolute top-9 left-7 text-center py-1 px-2 font-medium">
                        notify 내용{index+1}~~
                    </p>
                  </div>
                </div>
              </div>
            </div>
          )
        })
      }
      </div>
    </div>
  );
}