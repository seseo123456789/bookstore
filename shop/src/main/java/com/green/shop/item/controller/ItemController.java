package com.green.shop.item.controller;

import com.green.shop.cart.service.CartServiceImpl;
import com.green.shop.item.service.ItemServiceImpl;
import com.green.shop.item.vo.CategoryVo;
import com.green.shop.item.vo.ItemVo;
import com.green.shop.member.vo.MemberVo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// 상품과 관련된 모든 페이지 이동 처리 컨트롤러
@Controller
@RequestMapping("/item")
public class ItemController {
        @Resource(name = "itemService")
        private ItemServiceImpl itemService;



    //상품 목록 페이지로 이동
    @RequestMapping ("/list")
    public String list(Model model){
        //상품목록 조회
        List<ItemVo> itemList=itemService.selectItemList();
        model.addAttribute("itemList", itemList);



        return "content/item/item_list";
    }

    // 상품 상세보기
    @GetMapping("/itemDetail")
    public String itemDetail(@RequestParam(name="itemCode") int itemCode, Model model){
       ItemVo itemVo = itemService.itemDetail(itemCode);
       model.addAttribute("itemVo", itemVo);
        System.out.println(itemVo);
        return "content/item/item_detail";
    }


}
