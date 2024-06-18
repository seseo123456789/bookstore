package com.green.shop.cart.controller;

import com.green.shop.cart.service.CartServiceImpl;
import com.green.shop.cart.vo.CartViewVo;
import com.green.shop.cart.vo.CartVo;
import com.green.shop.member.vo.MemberVo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Resource(name= "cartService")
    private CartServiceImpl cartService;




    // 비동기 장바구니 저장
    @ResponseBody
    @PostMapping("/fetchCart")
    public void fetchCart(CartVo cartVo, HttpSession session){

        MemberVo loginInfo1 = (MemberVo) session.getAttribute("loginInfo");
        cartVo.setMemberId(loginInfo1.getMemberId());

        cartService.insertCart(cartVo);
    }

    // 장바구니 목록 조회
    @GetMapping("/list")
    public String listCart(HttpSession session, Model model, @RequestParam(name = "page", required = false, defaultValue = "cartList") String page
    ){
        // 멤버아이디 들고 오기 (로그인했을시 조회를 위해서)
        MemberVo loginInfo2 = (MemberVo) session.getAttribute("loginInfo");
        List<CartViewVo> cartList = cartService.cartViewList(loginInfo2.getMemberId());

        //총 가격을 계산한 후
        int sum =0;
        for( CartViewVo e  : cartList){
            sum= sum + e.getTotalPrice();
        }

        model.addAttribute("cartList",cartList);

        // user_side 의 목록 색 선택화면 기능
        model.addAttribute("page", page);

        return "content/cart/cart_list";
    }
        //장바구니 삭제하기
    @GetMapping("/deleteCart")
    public String deleteCart(@RequestParam(name = "cartCode") int cartCode){

        cartService.deleteCart(cartCode);
        return "redirect:/cart/list";
    }

    // 비동기 장바구니 수량 업데이트
    @ResponseBody
    @PostMapping("/updateCart")
    public void updateCart(CartVo cartVo){
        cartService.updateCart(cartVo);

    }

    // 장바구니에서 선택한 상품 삭제하기
    @GetMapping("/deleteCarts")
    public String deleteCarts(CartVo cartVo){
        cartService.deleteCarts(cartVo);
        return "redirect:/cart/list";
    }

}
