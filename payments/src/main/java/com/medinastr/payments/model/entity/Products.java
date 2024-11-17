package com.medinastr.payments.model.entity;

import com.medinastr.payments.model.dto.ProductsDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    private String name;

    private String description;

    private Float price;

    @Column(name = "stripe_id")
    private String stripeId;

    public Products(ProductsDTO productsDTO) {
        this.id = productsDTO.getId();
        this.name = productsDTO.getName();
        this.description = productsDTO.getDescription();
        this.price = productsDTO.getPrice();
        this.stripeId = productsDTO.getStripeId();
    }
}
