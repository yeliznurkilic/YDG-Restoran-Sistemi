package com.ydg.restaurant.integration;

import com.ydg.restaurant.model.RestaurantOrder;
import com.ydg.restaurant.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OrderPaymentIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private OrderRepository orderRepository;

    @Test
    void shouldPayOrder() throws Exception {
        RestaurantOrder order = new RestaurantOrder();
        order.setPaid(false);
        orderRepository.save(order);

        mockMvc.perform(patch("/api/orders/" + order.getId() + "/pay")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        RestaurantOrder updated = orderRepository.findById(order.getId()).orElseThrow();
        assertThat(updated.isPaid()).isTrue();
    }
}
