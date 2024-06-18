package com.green.shop.buy.serviece;

import com.green.shop.buy.vo.BuyDetailVo;
import com.green.shop.buy.vo.BuyVo;
import com.green.shop.cart.vo.CartVo;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("buyService")
public class BuyServiceImpl implements BuyService {
        @Autowired
        private SqlSessionTemplate sqlSession;



    // 장바구니 상품 구매정보 테이블 저장 및 상세테이블 저장 ,선택하여 삭제하기
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertBuys(BuyVo buyVo, CartVo cartVo) {
        sqlSession.insert("buyMapper.insertBuys", buyVo);
        sqlSession.insert("buyMapper.insertBuyDetails", buyVo);
        sqlSession.delete("cartMapper.deleteCarts", cartVo);
    }

    // buyCode 지정해주기 1,2,3,4,...
    @Override
    public int selectNextBuyCode() {
        return sqlSession.selectOne("buyMapper.selectNextBuyCode");
    }

    // 상세페이지에서 바로구매 클릭시 저장
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertBuyDirect(BuyVo buyVo, BuyDetailVo buyDetailVo) {
        sqlSession.insert("buyMapper.insertBuys", buyVo);
        sqlSession.insert("buyMapper.insertBuyDirect", buyDetailVo);

    }

    // 내정보 클릭시 구매 목록 조회 (사용자용)
    @Override
    public List<BuyVo> selecetBuyList(String memberId) {
        return sqlSession.selectList("buyMapper.selectBuyList", memberId);
    }

    // 구매이력 총구매상품 갯수
    @Override
    public BuyVo totalBuyCNT(String memberId) {
        return sqlSession.selectOne("buyMapper.totalBuyCNT", memberId);
    }


}
