package com.green.shop.board.vo;



import lombok.ToString;

@ToString
public class PageMaker {
    private Criteria cri;
    private int totalCount; // 총 게시글 수
    private int startPage;
    private int endPage; //화면에 보여질 마지막 페이지 번호. 끝페이지번호
    private boolean prev;
    private boolean next;
    private int displayPageNum = 3; // 화면하단 보여지는 페이지버튼의 수



    public Criteria getCri() {
        return cri;
    }

    public void setCri(Criteria cri) {
        this.cri = cri;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        pageData();
    }

    // cri.getPage(): 현재 페이지 번호
    // cri.getPerPageNum() : 한 페이지당 보여줄 게시글 갯수

    private void pageData(){
        // 끝 페이지 번호 = (현재 페이지 번호 / 화면에 보여질 번호의 갯수) * 화면에 보여질 페이지번호 갯수
        endPage= (int)(Math.ceil(cri.getPage()/ (double)displayPageNum)* displayPageNum);
        // 시작 페이지 번호 = (끝 페이지 번호 - 화면에 보여질 페이지 번호의 갯수) +1
        startPage= (endPage-displayPageNum)+1;
        if(startPage <=0) startPage =1;

        // 마지막 페이지 번호 = 총 게시글 수 / 한 페이지당 보여줄 게시글의 갯수
        int tempEndPage = (int)(Math.ceil(totalCount / (double) cri.getPerPageNum()));
        if (endPage>tempEndPage){
            endPage=tempEndPage;
        }
        // 이전 버튼 생성 여부 = 시작 페이지 번호 ==1?false:true
        prev= startPage == 1? false: true;
        // 다음 버튼 생성 여부 = 끝 페이지 번호 * 한페이지당 보여줄 게시글의 갯수< 총 게시글의 수?
        next= endPage * cri.getPerPageNum()< totalCount ? true : false;
    }

    public int getStartPage() {
        return startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public boolean isPrev() {
        return prev;
    }

    public boolean isNext() {
        return next;
    }

    public int getDisplayPageNum() {
        return displayPageNum;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public void setPrev(boolean prev) {
        this.prev = prev;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public void setDisplayPageNum(int displayPageNum) {
        this.displayPageNum = displayPageNum;
    }
}
