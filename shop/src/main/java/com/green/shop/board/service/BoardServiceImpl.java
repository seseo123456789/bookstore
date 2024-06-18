package com.green.shop.board.service;

import com.green.shop.board.vo.BoardVo;
import com.green.shop.board.vo.Criteria;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("boardService")
public class BoardServiceImpl implements BoardService {

    @Autowired
    private SqlSessionTemplate sqlSession;




    // 다음에 들어갈 boardNum 조회
    @Override
    public int selectNextBoardNum() {
        return sqlSession.selectOne("boardMapper.selectNextBoardNum");
    }

    // 글목록 조회 및 검색
    @Override
    public List<Map<String,Object>> selectList(Criteria cri) {

        return sqlSession.selectList("boardMapper.selectList", cri);
    }

    // 글저장
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertWrite(BoardVo boardVo) {
         sqlSession.insert("boardMapper.insertWrite",boardVo);
        // 상품이미지 등록하기
         sqlSession.insert("boardMapper.insertImgs", boardVo);
    }

    // 글보기
    @Override
    public BoardVo boardDetail(int boardNum) {
        return sqlSession.selectOne("boardMapper.boardDetail", boardNum);
    }


    //조회증가
    @Override
    public void updateReadCnt(int boardNum) {
        sqlSession.update("boardMapper.updateReadCnt",boardNum);
    }

    // 페이징 총데이터 갯수 조회
    @Override
    public int selectBoardCnt(Criteria cri) {
        return sqlSession.selectOne("boardMapper.selectBoardCnt", cri);
    }


}
