package com.example.rgt.order.service;

import com.example.rgt.order.controller.request.OrderRequestDto;
import com.example.rgt.order.domain.TbOrder;
import com.example.rgt.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public String createOrder(OrderRequestDto orderRequestDto, HttpServletRequest request) {
        try {
            // 0. orderRequestDto Validation , Return 객체 (OrderResponseDto) 생성
            orderRequestDto.validateProductName();

            // 1. 클라이언트에서 저장 요청한 PK 추출
            String requestOrderId = orderRequestDto.getOrder_id();

            // 2. 클라이언트에서 저장 요청한 PK로 TbOrder 찾기
            Optional<TbOrder> findTbOrder = orderRepository.findById(requestOrderId);

            // 3. Insert
            String validateProductName = "카페테리아";
            Optional<TbOrder> validateTargetTbOrder = orderRepository.findByProductName(validateProductName);
            if (!validateTargetTbOrder.isEmpty()){
                TbOrder updateTargetTbOrder = validateTargetTbOrder.get();
                updateTargetTbOrder.validateProductName("카페라떼");
            }

            if (findTbOrder.isEmpty()){
                // 4. null이면 TbOrder Save , Repsonse Dto Setting 후 Return
                TbOrder savedTbOrder = orderRepository.save(new TbOrder().toEntity(orderRequestDto));
                return "주문번호 " + savedTbOrder.getOrderId() + ": 수신";
            }else {
                // 5. null이 아니면 TbOrder Update
                TbOrder savedTbOrder = findTbOrder.get();
                savedTbOrder.update(orderRequestDto);
                return "주문번호 " + savedTbOrder.getOrderId() + ": 수신";
            }

        }catch (Exception e){
            log.debug(e.getMessage());
            return "Error";
        }
    }
}
