package com.green.shop.cart.service;


import com.green.shop.cart.vo.CartViewVo;
import com.green.shop.cart.vo.CartVo;

import java.util.ArrayList;
import java.util.List;

public interface CartService {

    // 장바구니 정보 저장
    void insertCart(CartVo cartVo);

    // 장바구니 조회
    List<CartViewVo> cartViewList(String memberId);

    // 장바구니 삭제
    void deleteCart(int cartCode);

    // 장바구니  수량 수정
    void updateCart(CartVo cartVo);

    // 장바구니 선택삭제
    void deleteCarts(CartVo cartVo);

    //장바구니에 담긴 상품 구매를 위한 장바구니 목록 조회
    List<CartViewVo> selectCartListForBuy(CartVo cartVo);



}
