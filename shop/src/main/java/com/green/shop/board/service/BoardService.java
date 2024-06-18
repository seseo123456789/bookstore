package com.green.shop.board.service;

import com.green.shop.board.vo.BoardVo;
import com.green.shop.board.vo.Criteria;
import com.green.shop.item.vo.ItemVo;

import java.util.List;
import java.util.Map;

public interface BoardService{

    // 다음에 들어갈 ItemCode 조회
    int selectNextBoardNum();

    //게시글 조회
    List<Map<String,Object>> selectList(Criteria cri);

    //글 저장
    void insertWrite(BoardVo boardVo);

    // 글 상세보기
    BoardVo boardDetail(int boardNum);


    // 조회수 증가
    void updateReadCnt(int boardNum);

    //총데이터갯수 세기
    int selectBoardCnt(Criteria cri);



}
