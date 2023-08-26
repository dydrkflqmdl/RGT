package com.example.rgt.order.controller;

import com.example.rgt.order.controller.request.OrderRequestDto;
import com.example.rgt.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RequestMapping("/codingTest")
@RestController
public class OrderController {

    private final OrderService orderService;

    // 주문 작성
    @PostMapping(value = "/post")
    public String createOrder(@RequestBody OrderRequestDto orderRequestDto, HttpServletRequest request) {
        return orderService.createOrder(orderRequestDto, request);
    }
}
