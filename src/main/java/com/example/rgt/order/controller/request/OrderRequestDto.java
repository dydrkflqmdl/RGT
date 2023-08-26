package com.example.rgt.order.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class OrderRequestDto {

    //고유 아이디
    private String order_id;

    //메뉴이름
    private String product_name;

    //옵션
    private String options;

    //테이블번호
    private int table_no;

    //양
    private int quantity;

    //주문날짜
    private String order_date;

    //주문시간
    private String order_time;

    //로봇상태
    private String robot_status;

    //날짜
    private String date_time;

    //위치
    private String seq;

    //동
    private String dong;

    //호
    private String ho;

    //주문자
    private String orderer_name;

    public void validateProductName(){
        if (this.product_name.equals("카페테리아")) this.product_name = "카페라떼";
    }
}
