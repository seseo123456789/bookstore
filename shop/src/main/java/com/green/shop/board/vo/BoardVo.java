package com.green.shop.board.vo;

import com.green.shop.item.vo.ImgVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class BoardVo {

    private int boardNum;
    private String title;
    private String bookName;
    private String content;
    private String writer;
    private String createDate;
    private int readCnt;
    private List<BoardImgVo> boardImgList;


}
