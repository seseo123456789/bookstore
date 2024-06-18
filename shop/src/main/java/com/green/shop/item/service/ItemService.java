package com.green.shop.item.service;

import com.green.shop.item.vo.CategoryVo;
import com.green.shop.item.vo.ItemVo;

import java.util.List;

public interface ItemService {


    // 카테고리 조회
    List<CategoryVo> selectCateGoryList();

    // 상품 목록 조회
    List<ItemVo> selectItemList();

    // 상품 상세 보기
    ItemVo itemDetail(int itemCode);
}
