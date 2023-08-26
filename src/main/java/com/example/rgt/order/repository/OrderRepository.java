package com.example.rgt.order.repository;

import com.example.rgt.order.domain.TbOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<TbOrder, String> {

    Optional<TbOrder> findByProductName(String productName);

}
