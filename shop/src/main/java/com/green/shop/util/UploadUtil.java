package com.green.shop.util;

import com.green.shop.buy.vo.BuyDetailVo;
import com.green.shop.item.vo.ImgVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//파일 첨부와 관련된 기능 모음집
public class UploadUtil {


    // 내가선택한 파일명 ex) 'abcd.jpg' 을 매개변수로  fileName으로 받기
    // 파일의 확장자를 문자열로 리턴하는 메소드 // 확장명' .jpg ' 을 선택하기
    public static String getExtension(String fileName){
        //fileName : 원래파일명
        return fileName.substring(fileName.lastIndexOf("."));  // .jpg
    }

    // uuid(자동랜덤)를 통한 파일명 생성
    // toString() 문자열로 리턴할게요 라는 뜻
    public static String getUUID(){

        // String uuid= UUID.randomUUID().toString();
        // returen uuid;
        return UUID.randomUUID().toString(); // 랜덤명
    }


                // 파일 선택하기 'imgMain':  html input name
                // imgMain( 선택한 첨부할 파일) 을 매개변수로 uploadFile로 설정

    // 단일 파일 업로드 메소드
    public static ImgVo uploadFile(MultipartFile uploadFile){
            //String fileName = null; 랜덤명파일명을 리턴시켜주기 위해서 초기화선언
            // 하지만 우리는 랜덤명 파일과 원본파일명 을 리턴시켜줘야되서 ImgVo 초기화 선언
        ImgVo imgVo =null;


        // uploadFile.isEmpty() = true  빈값이라는 의미
        // !uploadFile.isEmpty() = false  빈값이 아닐때

            //첨부파일 선택하기
            // 첨부한 파일이 존재할 때만 실행
            if(!uploadFile.isEmpty()){
                imgVo =new ImgVo();


                // 확장자 추출
                String extension1 = getExtension(uploadFile.getOriginalFilename()); //.jpg //getExtension(String fileName)
                // 중복되지 않는 파일명 생성
                 String fileName = getUUID()+ extension1; // 랜덤명 + .jpg (확장자) / 푸바오오오.jpg

                //메인 파일 업로드
                try {
                    File file1 = new File(ConstantVariable.UPLOAD_PATH + fileName); // 경로 + 푸바오오오.jpg
                    uploadFile.transferTo(file1);

                    imgVo.setAttachedFileName(fileName); //랜덤파일명
                    imgVo.setOriginFileName(uploadFile.getOriginalFilename()); //원본파일명
                    imgVo.setIsMain("Y"); //메인파일인지
                } catch (IOException e) {
                    System.out.println("----파일 첨부 중 오류 발생----");
                    e.printStackTrace();
                }
            }
        return imgVo; // 랜덤명으로 된 파일 리턴
    }

    // 다중 첨부 메소드
    public static List<ImgVo> multiUploadFile(MultipartFile[] uploadFiles){

            List<ImgVo> imgList = new ArrayList<>();
            for(MultipartFile uploadFile :uploadFiles){
                 ImgVo vo = uploadFile(uploadFile); // 이미지1장(uploadFile)당의 imgVo 정보들이 담겨있다

                // 상세이미지 안넣어도 등록됨
                 if(vo != null){
                     vo.setIsMain("N");
                     imgList.add(vo);
                 }
            }
       return imgList;
    }


}
