
// 기본정보변경화면 띄위기 위해서 ItemCode 받아오기
const updateItemCode = document.querySelector('#updateItemCode').value;

if(updateItemCode != 0){
    getDetail(updateItemCode);
}


// 상품 목록 테이블의 행 클릭 시 상품의 상세 정보 조회
function getDetail(itemCode) {



    // ------------------- 첫번째 방식 ---------------//
    fetch('/admin/selectItemDetail', { //요청경로
        method: 'POST',
        cache: 'no-cache',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
        },
        //컨트롤러로 전달할 데이터
        body: new URLSearchParams({
            // 데이터명 : 데이터값
            'itemCode': itemCode
        })
    })
        .then((response) => {
            if (!response.ok) {
                alert('fetch error!\n컨트롤러로 통신중에 오류가 발생했습니다.');
                return;
            }

            //return response.text(); //컨트롤러에서 return하는 데이터가 없거나 int, String 일 때 사용
            return response.json(); //나머지 경우에 사용
        })
        //fetch 통신 후 실행 영역
        .then((data) => {//data -> controller에서 리턴되는 데이터!
            console.log(data);
            console.log(data.cateList);


            const detail_div = document.querySelector('.detail-div');
            detail_div.innerHTML = '';

                let str='';
                
                str +=`
                
                <div class="row">
                    <div class="col">
                        <h3>상품 기본정보</h3>
                    </div>
                </div>

            <form action="/admin/updateItem" method="post">

            <input type="hidden" name="itemCode" value="${data.itemDetail.itemCode}">
            
                <div class="row">
                    <div class="col">
                        <table class="table table-warning table-borderless text-center" style="font-size:18px;">
                            <colgroup>
                                <col width="30%">
                                <col width="*%">

                            </colgroup>

                            <tr>
                                <td>카테고리</td>
                                <td class="text-start">
                                    <select class="form-select" name="cateCode">`;
                                    
                                    for(const category of data.cateList){

                                        if(category.cateCode == data.itemDetail.cateCode){
                                            str += `<option value="${category.cateCode}" selected>${category.cateName}</option>`;
                                        }
                                        else{
                                            str += `<option value="${category.cateCode}">${category.cateName}</option>`;
                                        }
                                        
                                    }
                                    
                    str +=          `</select>                      
                                </td>
                            </tr>

                            <tr>
                                <td>상품명</td>
                                <td class="text-start">
                                    <input type="text" class="form-control" id="input-itemName" name="itemName" value="${data.itemDetail.itemName}">
                                </td>
                            </tr>

                            <tr>
                                <td>상품수량</td>
                                <td class="text-start">
                                    <input type="text" class="form-control" id="input-itemStock" name="currentStock" value="${data.itemDetail.currentStock}">
                                </td>
                            </tr>

                            <tr>
                                <td>상품상태</td>
                                <td class="text-start">
                                    <div class="form-check form-check-inline">`;

                                        if(data.itemDetail.itemStatus ==1){
                                            str +='<input checked class="form-check-input" type="radio" name="itemStatus" value="1">';
                                        }
                                        else{
                                            str +='<input class="form-check-input" type="radio" name="itemStatus" value="1">';
                                        }
                                        

                                str+=       `<label class="form-check-label">준비 중</label>
                                    </div>
                                    <div class="form-check form-check-inline">`;

                                        if(data.itemDetail.itemStatus ==2){
                                            str +=`<input checked class="form-check-input" type="radio" name="itemStatus" value="2">`;
                                        }
                                        else{
                                            str +=`<input class="form-check-input" type="radio" name="itemStatus" value="2">`;
                                        }
                                        
                                str+=       `<label class="form-check-label">판매 중</label>
                                    </div>
                                    <div class="form-check form-check-inline">`;
                                        
                                        if(data.itemDetail.itemStatus ==3){
                                            str +=`<input checked class="form-check-input" type="radio" name="itemStatus" value="3">`;
                                        }
                                        else{
                                            str +=`<input class="form-check-input" type="radio" name="itemStatus"" value="3">`;
                                        }
                                        
                                str+=       `<label class="form-check-label">매진</label>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>

                <div class="row mt-3">
                    <div class="col">
                        <h3>상품 이미지정보</h3>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <table class="table table-warning table-borderless text-center" style="font-size:18px;">
                            <tr>
                                <td class="text-start">&nbsp;&nbsp;&nbsp;메인 이미지</td>
                                <td class="text-start">`
                                
                                for(const img of data.itemDetail.imgList){
                                    if(img.isMain == 'Y'){
                                        str +=`<span onclick="showModalImg('${img.attachedFileName}')">${img.originFileName}</span>`;
                                    }

                                }  
                            
                    str +=      `</td>
                            </tr>
                            <tr>
                                <td class="text-start">&nbsp;&nbsp;&nbsp;상세 이미지</td>
                                <td class="text-start">`
                                    let cnt=0;

                                    data.itemDetail.imgList.forEach(function(img, idx){
                                        if(img.isMain == 'N'){
                                            if(cnt == 0){
                                                str += `<p onclick="showModalImg('${img.attachedFileName}')">${img.originFileName}</p>`;
                                                cnt++;
                                            }
                                            else{
                                                str += `<p onclick="showModalImg('${img.attachedFileName}')">${img.originFileName}</p>`;
                                            }                                          
                                        }
                                    });                                  
                                                                                    
                    str +=      `</td>

                            </tr>
                        </table>
                    </div>
                </div>

                <div class="row text-start">
                    <div class="d-grid gap-2 col-4 mx-auto">
                        <input type="submit" class="btn btn-primary" value="변경">
                    </div>
                </div>
            </form>
                
            `;

                detail_div.insertAdjacentHTML('afterbegin', str);




        })
        //fetch 통신 실패 시 실행 영역
        .catch(err => {
            alert('fetch error!\nthen 구문에서 오류가 발생했습니다.\n콘솔창을 확인하세요!');
            console.log(err);
        });

}




function showModalImg(attachedFileName){


// 부트스트랩이 제공하는 모달 태그를 선택하는 방법(기능을 포함한것)
const img_modal = new bootstrap.Modal('#img-modal');  

   const img_tag = document.querySelector('#img-modal img');
   img_tag.src = `/upload/${attachedFileName}`;

   img_modal.show(); 


    

}