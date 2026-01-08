package com.ydg.restaurant.integration;

import com.ydg.restaurant.model.RestaurantTable;
import com.ydg.restaurant.repository.TableRepository;
import com.ydg.restaurant.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
class ReservationIntegrationIT {

    @Autowired private MockMvc mockMvc;
    @Autowired private TableRepository tableRepository;
    @Autowired private ReservationRepository reservationRepository;

    @Test
    void shouldCreateReservation() throws Exception {
        // DB'ye gerçek bir masa kaydediyoruz
        RestaurantTable table = new RestaurantTable();
        table.setCapacity(4);
        table.setAvailable(true);
        table.setTableNumber(1); // PostgreSQL için önemli
        tableRepository.save(table);

        // JSON gönderimini embed şekilde yapıyoruz
        String json = """
        {
          "restaurantTable": {
            "id": %d,
            "capacity": 4,
            "tableNumber": 1,
            "available": true
          },
          "reservationTime": "%s",
          "numberOfPeople": 2
        }
        """.formatted(
                table.getId(),
                LocalDateTime.now().plusHours(1).toString()
        );

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }
}
