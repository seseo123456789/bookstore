

// 제목 줄 체크박스
function checkAll(){

    // 제목줄 체크박스 선택
    const chkAll = document.querySelector('#chkAll');
    // 체크박스 체크 여부판단
   const isChecked = chkAll.checked;
   // 본문에 있는 체크박스
   const chks = document.querySelectorAll('.chk');

   
   //체크 됬을때 뭐할꺼야?  안됬을때
        if(isChecked){
                    for(const chk of chks){
                        chk.checked= true;
                    }
                
        }
        else{
                    for(const chk of chks){
                        chk.checked = false;
                    }
                
        }
        setFinalPice()
}


setFinalPice();

//장바구니에 담긴 체크된 상품들의 총가격 계산 함수 
function setFinalPice(){
    //체크된 장바구니 상품의 총가격을 모두 더해서 계산 

    // 본문에 있는 .chk:checked : 체크가 된 태그만 선택함
   const chks = document.querySelectorAll('.chk:checked');

   let finalPrice = 0;
   chks.forEach(function(chk, i){
        // chk 각각의 같은 행에 있는 총 가격 데이터 찾기 // closest chk 에서 가장 가까운 tr 태그로 이동. 4번째 자식. 태그사이 감싸있는 text
        const strPrice = chk.closest('tr').children[5].textContent;

        // 정규식을 사용해서 쉼표와 원화표시를 제거
        const regex = /[^0-9]/g;
        const price = parseInt (strPrice.replace(regex,'')); // ￦20,000 -> 20000
        // 총가격 구하기 
        finalPrice = finalPrice + price;


   });
   //.toLocaleString   : 천단위 원화 , 표시하기
   document.querySelector('#finalPrice-span').textContent= finalPrice.toLocaleString();
   


}


// 삭제버튼 클릭시 장바구니에서 삭제
function deleteCart(cartCode){
 
    if(confirm('선택한 상품을 장바구니에서 삭제할까요?')){
        location.href=`/cart/deleteCart?cartCode=${cartCode}`;
    }
}


// ---------------- 변경버튼 클릭시 장바구니에 있는 cartCnt 변경하기 --------------------
function goChange(change, cartCode, itemPrice){

    const cartCnt = change.closest('.row').querySelector('input[type="number"]').value;
    // const cartCnt = document.querySelector('input[type="number"]').value; 내가푼것


    // ------------------- 첫번째 방식 ---------------//
fetch('/cart/updateCart', { //요청경로
    method: 'POST',
    cache: 'no-cache',
    headers: {
        'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
    },
    //컨트롤러로 전달할 데이터
    body: new URLSearchParams({
       // 데이터명 : 데이터값
    cartCode :cartCode, 
    cartCnt : cartCnt     

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
   
   //console.log(data);
      
// 수량이 변경 될때마다 총 가격을 세팅
    const totalPrice = parseInt(itemPrice) * parseInt(cartCnt);
    change.closest('tr').querySelector('.totalPrice-span').textContent = '￦' + totalPrice.toLocaleString();
  
    setFinalPice();
    alert('수량이 변경되었습니다!');

})
//fetch 통신 실패 시 실행 영역
.catch(err=>{
    alert('fetch error!\nthen 구문에서 오류가 발생했습니다.\n콘솔창을 확인하세요!');
    console.log(err);
});

}


// ---------------------- 선택한 상품 삭제하기 -------------------------
function deleteCarts(){
        
        const chks = document.querySelectorAll('.chk:checked');
        
        if(chks.length == 0){
           alert('삭세할 상품을 선택하세요!');
           return ;
        }
// 컨트롤러로 넘겨 줄 cartCode들 
// 체크된 체크박스들에서 cartCode값.
      
        const cartCodes = [];
        for(const chk of chks){
            cartCodes.push(chk.value);
        }
    location.href =`/cart/deleteCarts?cartCodeList=${cartCodes}`;    
}




// ---------------- 선택한 상품 구매하기 -----------------------------
function buys(){

    const chks = document.querySelectorAll('.chk:checked');
   

    if(chks.length == 0){
        alert('구매할 상품을 선택하세요!');
        return ;
     }


     const cartCodeList = [];
     for(const chk of chks){
        cartCodeList.push(chk.value);
     }
     location.href =`/buy/buyCarts?cartCodeList=${cartCodeList}`; 

    


     

}

