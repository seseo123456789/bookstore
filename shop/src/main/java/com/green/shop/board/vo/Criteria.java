package com.green.shop.board.vo;


import lombok.ToString;

@ToString
public class Criteria{

    private int page; //현재 페이지 번호
    private int perPageNum; // 한 페이지당 보여줄 게시글 갯수

    private String searchType;
    private String searchName;

    private String fromDate;
    private String toDate;



    public Criteria(){
        this.page=1;
        this.perPageNum= 3;
    }
    public Criteria(int page, int perPageNum){
        this.page=page;
        this.perPageNum=perPageNum;
    }

    // int getPageStart() : 특정 페이지의 게시글 시작번호/ 시작행 번호

    public int getPageStart(){
        // 페이지에 표현되는 데이터 누적 수 = (현재페이지번호 -1) * 페이지당 보여줄 게시글 갯수
        return(this.page-1)*perPageNum;
    }

    public int getPage() {
        return page;
    }

    // 음수가 되면 1페이지 표시
    public void setPage(int page) {
        if(page<=0){
            this.page = 1;
        } else {
            this.page= page;
        }
    }

    public int getPerPageNum() {
        return perPageNum;
    }

    // 페이지당 보여줄 게시글 수 고정
    public void setPerPageNum(int pageCount) {
        int cnt = this.perPageNum;
        if(pageCount != cnt){
            this.perPageNum = cnt;
        }else {
            this.perPageNum = pageCount;
        }
    }

    public String getSearchType() {
        return searchType;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }


    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
