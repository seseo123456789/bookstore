package com.green.shop.cart.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartViewVo {
    private int cartCode;
    private int itemCode;
    private String memberId;
    private int cartCnt;
    private String cartDate;

    private String itemName;
    private int itemPrice;
    private String itemIntro;
    private int totalPrice;

    private String memberName;
    private String memberTel;
    private String address;


    private int imgCode;
    private String originFileName;
    private String attachedFileName;
    private String isMain;

    private int cateCode;
    private String cateName;
}
