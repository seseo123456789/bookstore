package com.green.shop.buy.serviece;

import com.green.shop.buy.vo.BuyDetailVo;
import com.green.shop.buy.vo.BuyVo;
import com.green.shop.cart.vo.CartVo;
import com.green.shop.item.vo.ItemVo;

import java.util.List;

public interface BuyService {

    // 장바구니 상품 구매정보 테이블 저장 및 상세테이블 저장 ,선택하여 삭제하기
    void insertBuys(BuyVo buyVo, CartVo cartVo);

    // 다음에 들어갈 BUY_CODE 조회
    int selectNextBuyCode();

    // 상세페이지에서 바로구매 클릭시 저장
    void insertBuyDirect(BuyVo buyVo , BuyDetailVo buyDetailVo);

    // 내정보 클릭시 구매 목록 조회 (사용자용)
    List<BuyVo> selecetBuyList(String memberId);

    // 구매이력 총구매상품 갯수
    BuyVo totalBuyCNT(String memberId);



}
