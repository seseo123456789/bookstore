package com.green.shop.admin.controller;

import com.green.shop.admin.service.AdminServiceImpl;
import com.green.shop.board.vo.Criteria;
import com.green.shop.board.vo.PageMaker;
import com.green.shop.buy.vo.BuyVo;
import com.green.shop.item.service.ItemServiceImpl;
import com.green.shop.item.vo.CategoryVo;
import com.green.shop.item.vo.ImgVo;
import com.green.shop.item.vo.ItemVo;
import com.green.shop.util.UploadUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Resource(name = "adminService")
    private AdminServiceImpl adminService;
    @Resource(name = "itemService")
    private ItemServiceImpl itemService;


    //상품등록 페이지 이동
    @GetMapping("/regItemForm")
    public String regItemForm(Model model, CategoryVo categoryVo) {
        List<CategoryVo> categoryList = itemService.selectCateGoryList();
         model.addAttribute("categoryList", categoryList);
        model.addAttribute("page", 2);

        return "content/admin/reg_item_form";
    }

    //상품등록 하기
    @PostMapping ("/regItem")
    public String regItem(ItemVo itemVo, HttpSession session
                            , @RequestParam(name ="imgMain")MultipartFile imgMain  // 첨부파일받는 매개변수
                            , @RequestParam(name="imgDetails")MultipartFile[] imgDetails){


            //상품 사진 파일 업로드
           ImgVo mainImgVo = UploadUtil.uploadFile(imgMain);

            // 상세 이미지들 업로드
           List<ImgVo> imgList = UploadUtil.multiUploadFile(imgDetails);

            // 다음에 들어갈 Item_code 조회
             int itemCode = adminService.selectNextItemCode();

        // 상품  등록
        itemVo.setItemCode(itemCode); //itemCode 내가 직접 지정하기
                //adminService.insertItem(itemVo); // 상품정보만 등록하는 실행문 주석처리함


        //상품 이미지 등록
        mainImgVo.setItemCode(itemCode);

        // 이제   mainImgVo 에 원래파일명, 랜덤파일명. 메인파일.아이템코드 들어가있음
        for( ImgVo img : imgList){
            img.setItemCode(itemCode);
        }
        imgList.add(mainImgVo);
        itemVo.setImgList(imgList);


        adminService.insertItem(itemVo); // itemVo


      return "redirect:/admin/regItemForm";
    }
//-------------------------------------------------------------------------------------------



    //  관리자 메뉴>구매이력 클릭시 구매 목록 조회 (관리자용)
        // searchBuyVo 커멘드객체(변수를 가지고 있는 클래스) 라서 html에 바로 쓸수 있다.
    @RequestMapping("/adminHistory")
    public String adminHistory(HttpSession session, Model model , Criteria criteria){

        //MemberVo loginInfo = (MemberVo) session.getAttribute("loginInfo");
        PageMaker pageMaker= new PageMaker();
        // 페이징 정보 저장
        pageMaker.setCri(criteria);
        //  총 게시물 갯수

        int tCnt= adminService.selectBuyCnt(criteria);
        pageMaker.setTotalCount(tCnt);

        // 게시글 목록 조회
        List<Map<String,Object>> list = adminService.selectBuyList(criteria);

        model.addAttribute("tCnt" , tCnt);
        model.addAttribute("buyList" ,list);
        model.addAttribute("pageMaker", pageMaker);

        model.addAttribute("page",1);


        return "content/admin/admin_history";
    }



    // 비동기 구매상세 내역 조회 (관리자용)
    @ResponseBody
    @PostMapping("/selectBuyDetail")
    public BuyVo selectBuyDetail(Model model, @RequestParam(name="buyCode")int buyCode){

          BuyVo buyVo =adminService.selectBuyDetail(buyCode);

        return buyVo;
    }


    // 상품정보 클릭시  "상품목록조회" (관리자용)
    @GetMapping("/updateItemInfo")
    public String updateItemInfo(Model model, @RequestParam(name = "itemCode", required = false, defaultValue ="0" ) int itemCode){

        model.addAttribute("itemList",adminService.selectItemList());
        model.addAttribute("page",4);
        // (/updateItem)에서 넘어오는 getItemCode();를 받아서 html 로 넘기기
        model.addAttribute("updateItemCode", itemCode);

        return "content/admin/update_item";
    }

    // 비동기 , Map
    // 상품 기본 정보 조회
    @ResponseBody
    @PostMapping("/selectItemDetail")
    public Map<String, Object> updateItemInfoDetail(@RequestParam(name = "itemCode") int itemCode){

            // 상품 상세 조회
            ItemVo itemDetail =adminService.selectItemDetail(itemCode);
            System.out.println(itemDetail);
            // 카테고리 조회
            List<CategoryVo> cateList = itemService.selectCateGoryList();

            // 위 두 데이터를 담을 수 있는 Map 객체 생성
                Map<String, Object> map = new HashMap<>();
                map.put("itemDetail", itemDetail);
                map.put("cateList", cateList);

        return map;
    }

    //  상품기본정보 업데이트
        @PostMapping("/updateItem")
        public String updateItem(ItemVo itemVo){
            adminService.updateItem(itemVo);
            return "redirect:/admin/updateItemInfo?itemCode=" + itemVo.getItemCode();
        }


}
