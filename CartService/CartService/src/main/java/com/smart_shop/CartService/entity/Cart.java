package com.smart_shop.CartService.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.smart_shop.CartService.enums.CartStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Cart {

    @Id
    private String id;
    private String userId;
    @Enumerated(EnumType.STRING)
    private CartStatus status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamps;
}
