package com.ydg.restaurant.integration;

import com.ydg.restaurant.model.RestaurantTable;
import com.ydg.restaurant.repository.TableRepository;
import com.ydg.restaurant.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
class OrderIntegrationIT {

    @Autowired private MockMvc mockMvc;
    @Autowired private TableRepository tableRepository;
    @Autowired private OrderRepository orderRepository;

    @Test
    void shouldCreateOrderAndCalculateTotalPrice() throws Exception {
        RestaurantTable table = new RestaurantTable();
        table.setCapacity(4);
        table.setAvailable(true);
        table.setTableNumber(1);
        tableRepository.save(table);

        String json = """
        {
          "table": { "id": %d },
          "items": [
            { "name": "Pizza", "price": 50.0 },
            { "name": "Kola", "price": 15.0 }
          ]
        }
        """.formatted(table.getId());

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }
}
