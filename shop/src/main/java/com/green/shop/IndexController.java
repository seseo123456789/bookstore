package com.green.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 프로젝트 시작시 가장 처음 실행되는 컨트롤러
@Controller
public class IndexController {

    //프로젝트 실행시 상품 목록 페이지로 이동
    @GetMapping("/")
    public String main(){
        return "redirect:/item/list";
    }
}
