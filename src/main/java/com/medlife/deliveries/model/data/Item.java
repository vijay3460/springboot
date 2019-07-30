package com.medlife.deliveries.model.data;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Item{

    @NotNull
    @PositiveOrZero
    private Double price;

    @NotNull
    @PositiveOrZero
    private Long quantity;

    @NotBlank
    private String name;

    private String description;

}
