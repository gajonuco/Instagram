
package com.sistema_boft.service;

import com.sistema_boft.model.Photo;
import com.sistema_boft.model.PrintOrder;
import com.sistema_boft.model.User;
import com.sistema_boft.repository.PrintOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrintOrderService {

    private final PrintOrderRepository printOrderRepository;

    public PrintOrder createOrder(User user, List<Photo> photos) {
        PrintOrder order = PrintOrder.builder()
                .user(user)
                .photos(photos)
                .orderDate(LocalDateTime.now())
                .completed(false)
                .build();
        return printOrderRepository.save(order);
    }

    public void completeOrder(PrintOrder order) {
        order.setCompleted(true);
        printOrderRepository.save(order);
    }
}
