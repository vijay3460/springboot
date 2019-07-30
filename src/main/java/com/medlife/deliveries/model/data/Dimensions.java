package com.medlife.deliveries.model.data;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author : Harshit Singh Chauhan
 */
@Data
public class Dimensions {

    @NotNull
    @Positive
    private Integer length;

    @NotNull
    @Positive
    private Integer breadth;

    @NotNull
    @Positive
    private Integer height;

    @NotNull
    @Positive
    private Integer weight;
}
