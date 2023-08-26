package com.example.rgt.order.domain;

import com.example.rgt.order.controller.request.OrderRequestDto;
import lombok.*;
import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class TbOrder {

    //오더번호
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private String orderId;

    //메뉴이름
    @Column(nullable = false)
    private String productName;

    //옵션
    @Column(nullable = false)
    private String options;

    //테이블번호
    @Column(nullable = false)
    private int tableNo;

    //양
    @Column(nullable = false)
    private int quantity;

    //주문날짜
    @Column(nullable = false)
    private String orderDate;

    //주문시간
    @Column(nullable = false)
    private String orderTime;

    //로봇상태
    @Column(nullable = false)
    private String robotStatus;

    //날짜
    @Column(nullable = false)
    private String dateTime;

    //위치
    @Column(nullable = false)
    private String seq;

    //동
    @Column(nullable = false)
    private String dong;

    //호
    @Column(nullable = false)
    private String ho;

    //주문자
    @Column(nullable = false)
    private String ordererName;

    public TbOrder toEntity(OrderRequestDto orderRequestDto) {
        return  TbOrder.builder()
                .orderId(orderRequestDto.getOrder_id())
                .productName(orderRequestDto.getProduct_name())
                .options(orderRequestDto.getOptions())
                .tableNo(orderRequestDto.getTable_no())
                .quantity(orderRequestDto.getQuantity())
                .orderDate(orderRequestDto.getOrder_date())
                .orderTime(orderRequestDto.getOrder_time())
                .robotStatus(orderRequestDto.getRobot_status())
                .dateTime(orderRequestDto.getDate_time())
                .seq(orderRequestDto.getSeq())
                .dong(orderRequestDto.getDong())
                .ho(orderRequestDto.getHo())
                .ordererName(orderRequestDto.getOrderer_name())
                .build();
    }

    public TbOrder update(OrderRequestDto orderRequestDto) {
        this.productName = orderRequestDto.getProduct_name();
        this.options = orderRequestDto.getOptions();
        this.tableNo = orderRequestDto.getTable_no();
        this.quantity = orderRequestDto.getQuantity();
        this.orderDate = orderRequestDto.getOrder_date();
        this.orderTime = orderRequestDto.getOrder_time();
        this.robotStatus = orderRequestDto.getRobot_status();
        this.dateTime = orderRequestDto.getDate_time();
        this.seq = orderRequestDto.getSeq();
        this.dong = orderRequestDto.getDong();
        this.ho = orderRequestDto.getHo();
        this.ordererName = orderRequestDto.getOrderer_name();

        return this;
    }

    public void validateProductName(String productName){
        this.productName = productName;
    }
}
