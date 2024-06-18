package com.green.shop.board.controller;

import com.green.shop.board.service.BoardServiceImpl;
import com.green.shop.board.vo.*;
import com.green.shop.buy.serviece.BuyServiceImpl;
import com.green.shop.item.vo.ImgVo;
import com.green.shop.member.vo.MemberVo;
import com.green.shop.util.BoardUploadUtil;
import com.green.shop.util.UploadUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RequestMapping("/board")
@Controller
public class BoardController {

    @Resource(name = "boardService")
    private BoardServiceImpl boardService;



    // 도서 리뷰 목록 페이지 이동
    @RequestMapping("/reviewList")
    public String reviewList(Criteria cri, Model model){


        System.out.println("##############"+ cri.getPageStart());
        PageMaker pageMaker= new PageMaker();
        // 페이징 정보 저장
        pageMaker.setCri(cri);
       //  총 게시물 갯수
        int tCnt= boardService.selectBoardCnt(cri);
        pageMaker.setTotalCount(tCnt);

        // 게시글 목록 조회
        List<Map<String,Object>> list = boardService.selectList(cri);

        model.addAttribute("tCnt" , tCnt);
        model.addAttribute("bookReviewList" ,list);
        model.addAttribute("pageMaker", pageMaker);


        return "/content/member/review_list";
    }



    // 리뷰작성글 페이지 이동
    @GetMapping("/writeReivew")
    public String writeReivew(HttpSession session){
            MemberVo loginInfo =(MemberVo) session.getAttribute("loginInfo");
        return "/content/member/review_write";
    }


    // 글저장
    @PostMapping("/writeBook")
    public String writeBook(BoardVo boardVo, HttpSession session,
                            @RequestParam(name="bookImgMain") MultipartFile bookImgMain,
                            @RequestParam(name="bookImgDetails") MultipartFile[] bookImgDetails
                            ){
            // 아이디, 이름, 관리자 정보 저장
            MemberVo loginInfo =(MemberVo) session.getAttribute("loginInfo");

            //지정한 boardNum 조회
            int boardNum = boardService.selectNextBoardNum();

            // 리뷰 글번호 등록
            boardVo.setBoardNum(boardNum);

            // 메인 이미지 업로드
            BoardImgVo mainImgVo = BoardUploadUtil.uploadFile(bookImgMain);
            // 상세 이미지들 업로드
            List<BoardImgVo> imgList = BoardUploadUtil.multiUploadFile(bookImgDetails);

                if(mainImgVo == null){
                    System.out.println("mainImgVo 가 null 이에요. 첨부파일을 등록해야됩니다. ");
                }

                if(mainImgVo != null ) {
                    MemberVo loginInfo1 =(MemberVo) session.getAttribute("loginInfo");
                    // 메인 이미지 글번호 등록
                    mainImgVo.setBoardNum(boardNum);
                    // 상세 이미지 글번호 등록
                    for (BoardImgVo img : imgList) {
                        img.setBoardNum(boardNum);
                    }
                    // 상세이미지 리스트에 메인이미지 넣어서
                    imgList.add(mainImgVo);
                    // 전체 이미지들을 글데이터에 저장
                    boardVo.setBoardImgList(imgList);

                    //boardVo에 작성자 데이터 저장
                    boardVo.setWriter(loginInfo1.getMemberId());

                    // 모든 데이터정보 저장
                    boardService.insertWrite(boardVo);
                }
        return "redirect:/board/reviewList";
    }

    // 리뷰글 상세글 보기
    @GetMapping("/reivewDetail")
    public String reivewDetail(@RequestParam(name = "boardNum") int boardNum, Model model){


        //조회수 증가
        boardService.updateReadCnt(boardNum);
        //상세보기
        BoardVo reviewBoard = boardService.boardDetail(boardNum);
        model.addAttribute("reviewBoard", reviewBoard);

        return "/content/member/review_detail";
    }

}
