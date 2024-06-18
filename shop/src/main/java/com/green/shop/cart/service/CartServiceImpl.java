package com.green.shop.cart.service;

import com.green.shop.cart.vo.CartViewVo;
import com.green.shop.cart.vo.CartVo;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("cartService")
public class CartServiceImpl implements CartService{

    @Autowired
    private SqlSessionTemplate sqlSession;

    // 장바구니 정보 저장
    @Override
    public void insertCart(CartVo cartVo) {

        //2. 현재 내 장바구니에 지금 추가하려는 상품이 있는지 확인 select
        int cnt= sqlSession.selectOne("cartMapper.selectCart", cartVo);


        //3. 존재하지 않으면 장바구니에 추가 insert
        if(cnt == 0){
            // 1. 장바구니 정보저장
             sqlSession.insert("cartMapper.insertCart", cartVo);
        }
        // 4.존재 하면 수량만 변경(update)
        else{
            sqlSession.update("cartMapper.plusCartCnt", cartVo);
        }
    }

    //장바구니 목록조회
    @Override
    public List<CartViewVo> cartViewList(String memberId) {
        return sqlSession.selectList("cartMapper.cartViewList", memberId);
    }

    // 장바구니 삭제
    @Override
    public void deleteCart(int cartCode) {
        sqlSession.delete("cartMapper.deleteCart", cartCode);
    }

    // 장바구니 수량 수정
    @Override
    public void updateCart(CartVo cartVo) {
        sqlSession.update("cartMapper.updateCart", cartVo);
    }

    //장바구니 선택삭제
    @Override
    public void deleteCarts(CartVo cartVo) {
        sqlSession.delete("cartMapper.deleteCarts", cartVo);
    }

    //장바구니에 담긴 상품 구매를 위한 장바구니 목록 조회
    @Override
    public List<CartViewVo> selectCartListForBuy(CartVo cartVo) {
        return sqlSession.selectList("cartMapper.selectCartListForBuy", cartVo);
    }


}
