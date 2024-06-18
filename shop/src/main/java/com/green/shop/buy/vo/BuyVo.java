package com.green.shop.buy.vo;


import com.green.shop.item.vo.ImgVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class BuyVo {

    private int buyCode;
    private String memberId;
    private int buyPrice;
    private String buyDate;
    private int itemCnt;

    private int num;
    private List<BuyDetailVo> buyDetailList;
}
