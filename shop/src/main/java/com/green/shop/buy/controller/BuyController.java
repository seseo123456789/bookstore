package com.green.shop.buy.controller;

import com.green.shop.buy.serviece.BuyService;
import com.green.shop.buy.serviece.BuyServiceImpl;
import com.green.shop.buy.vo.BuyDetailVo;
import com.green.shop.buy.vo.BuyVo;
import com.green.shop.cart.service.CartServiceImpl;
import com.green.shop.cart.vo.CartViewVo;
import com.green.shop.cart.vo.CartVo;
import com.green.shop.member.vo.MemberVo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/buy")
public class BuyController {

    @Resource(name = "buyService")
    private BuyServiceImpl buyService;

    @Resource(name= "cartService")
    private CartServiceImpl cartService;



    //장바구니에서 선택 구매 버튼 눌렀을때, 선택상품 구매정보 저장
           @GetMapping("/buyCarts")

            public String buyCarts(CartVo cartVo, HttpSession session){
                // cartvo 에  cartCodeList 받아서
                // cartCodeList 에서 상품 하나씩 꺼내서 상세정보 조회하여 리스트
               List<CartViewVo> cartViewList = cartService.selectCartListForBuy(cartVo);


               // 구매 정보 테이블에 들어갈 buy_price 상품들 구매총가격
               int buyPrice=0;
               for( CartViewVo e : cartViewList){
                   buyPrice = buyPrice + e.getTotalPrice();
               }
               //  구매 정보 테이블에 들어갈 구매자 ID 가져오기
               MemberVo loginInfo =(MemberVo) session.getAttribute("loginInfo");
               String memberId= loginInfo.getMemberId();

               // 구매 정보 테이블 &  상세 테이블에  다음에 들어갈 BUY_CODE 결정
               int buyCode = buyService.selectNextBuyCode();



               // 조회한 상세정보를  구매정보에 저장
               BuyVo buyVo1 = new BuyVo();
             
               // buyVo1 에  buyCode 넣기
               buyVo1.setBuyCode(buyCode);
               // buyVo1 에  memberId 넣기
               buyVo1.setMemberId(memberId);
               // buyVo1 에  buyPrice 넣기
               buyVo1.setBuyPrice(buyPrice);


               // 조회한 회원정보&상세정보를  구매상세정보에 저장
               List<BuyDetailVo> buyDetailVoList = new ArrayList<>();

               //상세정보 리스트를 구매상세정보에 리스트에 저장
                for( CartViewVo viewVo : cartViewList ){
                   BuyDetailVo DetailVo = new BuyDetailVo();
                        DetailVo.setItemCode(viewVo.getItemCode());
                        DetailVo.setBuyCnt(viewVo.getCartCnt());
                        DetailVo.setTotalPrice(viewVo.getTotalPrice());

                   buyDetailVoList.add(DetailVo);
               }
                buyVo1.setBuyDetailList(buyDetailVoList);
                System.out.println(buyVo1);

               buyService.insertBuys(buyVo1, cartVo);

               return "redirect:/cart/list";
            }
            
            
            //  상품상세페이지 에서 바로구매 하기
            @PostMapping ("/buyDirect")
            public String buyDirect(BuyVo buyVo, HttpSession session, BuyDetailVo buyDetailVo){

               // 다음에 들어가야 하는 buy_code 값을 조회
                int buyCode = buyService.selectNextBuyCode();
               // MEMBER_ID 조회
               MemberVo loginInfo = (MemberVo)session.getAttribute("loginInfo");
                String memberId = loginInfo.getMemberId();



                // buyVo 에 조회한 데이터 저장하기
                buyVo.setBuyCode(buyCode);
                buyVo.setMemberId(loginInfo.getMemberId());
                buyVo.setBuyPrice(buyDetailVo.getTotalPrice());
                buyDetailVo.setBuyCode(buyCode);


                buyService.insertBuyDirect(buyVo, buyDetailVo);
              return "redirect:/";
           }

           // 구매 이력 페이지
           @GetMapping("/history")
            public String history(HttpSession session, Model model, @RequestParam(name="page", required=false, defaultValue="history") String page){
               //String page= "history";
               model.addAttribute("page", page);

               // 아이디 호출
               MemberVo loginInfo =(MemberVo)session.getAttribute("loginInfo");
               //구매목록 조회
               List<BuyVo> buyList = buyService.selecetBuyList(loginInfo.getMemberId());
                for(BuyVo e : buyList){
                    System.out.println(e);} //  출력해서 BuyVo 내용물 확인하기
               model.addAttribute("buyList",buyList);

                // 구매상품 갯수
               BuyVo buyItem =  buyService.totalBuyCNT(loginInfo.getMemberId());
               model.addAttribute("totalBuyCNT", buyItem);
               return "/content/buy/history";
           }

}

