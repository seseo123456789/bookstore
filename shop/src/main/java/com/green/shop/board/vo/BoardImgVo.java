package com.green.shop.board.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardImgVo {
        private int imgCode;
        private String originFileName;
        private String attachedFileName;
        private String isMain;
        private int boardNum;


}
