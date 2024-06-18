package com.green.shop.cart.vo;

import com.green.shop.item.vo.ImgVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class CartVo {
    private int cartCode;
    private int itemCode;
    private String memberId;
    private int cartCnt;
    private String cartDate;

    private List<Integer> cartCodeList; // cartCode 10,11,13 들이 저장됨

}
