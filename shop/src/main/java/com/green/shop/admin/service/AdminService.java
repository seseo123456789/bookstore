package com.green.shop.admin.service;

import com.green.shop.board.vo.Criteria;
import com.green.shop.buy.vo.BuyVo;
import com.green.shop.item.vo.ItemVo;

import java.util.List;
import java.util.Map;

public interface AdminService {

    // 상품등록 + 상품이미지 등록
    void insertItem(ItemVo itemVo);


    // 다음에 들어갈 ItemCode 조회
    int selectNextItemCode();

    //  구매이력내역 목록 조회 (관리자용)
    List<Map<String,Object>> selectBuyList(Criteria criteria);

    //총데이터갯수 세기
    int selectBuyCnt(Criteria criteria);


    //구매이력 상세내역 목록 조회 (관리자용)
     BuyVo selectBuyDetail(int buyCode);


    // 상품 정보 변경 화면에서 상품 목록 조회   //상품정보변경 쌤풀이
    List<ItemVo> selectItemList();
    ItemVo selectItemDetail(int itemCode);


    // 기본정보 변경
    void updateItem(ItemVo itemVo);


}
