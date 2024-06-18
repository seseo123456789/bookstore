//비동기 통신 로그인 기능

function login(){
    const memberId = document.querySelector('#login-form').querySelector('#memberId').value;
    const memberPw = document.querySelector('#login-form').querySelector('#memberPw').value;

    // ------------------- 첫번째 방식 ---------------//
fetch('/member/loginFetch', { //요청경로
    method: 'POST',
    cache: 'no-cache',
    headers: {
        'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
    },
    //컨트롤러로 전달할 데이터
    body: new URLSearchParams({
       // 데이터명 : 데이터값
       memberId: memberId,
       memberPw: memberPw
    })
})
.then((response) => {
    if(!response.ok){
        alert('fetch error!\n컨트롤러로 통신중에 오류가 발생했습니다.');
        return ;
    }

    return response.text(); //컨트롤러에서 return하는 데이터가 없거나 int, String 일 때 사용
    //return response.json(); //나머지 경우에 사용
})
//fetch 통신 후 실행 영역
.then((data) => {//data -> controller에서 리턴되는 데이터!
  
    if(data == ''){
        alert('ID 혹은 PW를 확인하세요!');
            document.querySelector('#login-form').reset();
    }
    else{
        alert(`${data}님 반갑습니다~`);
            location.href='/item/list';
    }
   
})
//fetch 통신 실패 시 실행 영역
.catch(err=>{
    alert('fetch error!\nthen 구문에서 오류가 발생했습니다.\n콘솔창을 확인하세요!');
    console.log(err);
});
}