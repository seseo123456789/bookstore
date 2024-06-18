package com.green.shop.item.service;

import com.green.shop.item.vo.CategoryVo;
import com.green.shop.item.vo.ItemVo;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("itemService")
public class ItemServiceImpl implements ItemService{

    @Autowired
    private SqlSessionTemplate sqlSession;


    // 카테고리 조회
    @Override
    public List<CategoryVo> selectCateGoryList() {
        return sqlSession.selectList("itemMapper.selectCateGoryList");
    }

    //상품목록 조회
    @Override
    public List<ItemVo> selectItemList() {
        return sqlSession.selectList("itemMapper.selectItemList");
    }

    // 상품 상세보기
    @Override
    public ItemVo itemDetail(int itemCode) {
        return sqlSession.selectOne("itemMapper.itemDetail",itemCode);
    }
}
