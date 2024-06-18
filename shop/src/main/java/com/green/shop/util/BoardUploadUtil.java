package com.green.shop.util;

import com.green.shop.board.vo.BoardImgVo;
import com.green.shop.item.vo.ImgVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//파일 첨부와 관련된 기능 모음집
public class BoardUploadUtil {


   // 원래명 이미지파일명 "." 분리
    public static String getExtension(String fileName){

        return fileName.substring(fileName.lastIndexOf("."));  // .jpg
    }


  // 선택한 이미지 랜덤명 // uuid(자동랜덤)
    public static String getUUID(){
        return UUID.randomUUID().toString(); // 랜덤명
    }



    // 단일 파일 업로드 메소드
    public static BoardImgVo uploadFile(MultipartFile uploadFile){

        BoardImgVo boardImgVo =null;

            //첨부파일 선택하기

            if(!uploadFile.isEmpty()){
                boardImgVo =new BoardImgVo();


                // 확장자 추출
                String extension = getExtension(uploadFile.getOriginalFilename()); //.jpg
                // 랜덤 파일명 생성
                String ImgFileName = getUUID()+ extension; // 랜덤명 + .jpg (확장자)

                //메인 파일 업로드
                try {
                    File file = new File(ConstantVariable.UPLOAD_PATH + ImgFileName); // 경로 + 파일명
                    uploadFile.transferTo(file);

                    boardImgVo.setAttachedFileName(ImgFileName); //랜덤파일명
                    boardImgVo.setOriginFileName(uploadFile.getOriginalFilename()); //원본파일명
                    boardImgVo.setIsMain("Y"); //메인파일인지
                } catch (IOException e) {
                    System.out.println("----파일 첨부 중 오류 발생----");
                    e.printStackTrace();
                }
            }
        return boardImgVo; // 랜덤명으로 된 파일 리턴
    }

    // 다중 첨부 메소드
    public static List<BoardImgVo> multiUploadFile(MultipartFile[] uploadFiles){

            List<BoardImgVo> imgList = new ArrayList<>();

            for(MultipartFile uploadFile :uploadFiles){
                 BoardImgVo boardImgsVo = uploadFile(uploadFile); // 이미지1장씩 저장

                    // 상세이미지 저장
                     if(boardImgsVo != null){
                         boardImgsVo.setIsMain("N");
                         imgList.add(boardImgsVo);
                     }
            }
       return imgList;
    }




}
