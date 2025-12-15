package com.chamage.tempconverter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for temperature conversion")
public class ConversionRequest {

    @Schema(
            description = "Temperature value to convert",
            example = "25.5",
            required = true
    )
    private Double value;

    @Schema(
            description = "Unit of the input temperature. Accepts 'CELSIUS' or 'FAHRENHEIT'",
            example = "CELSIUS",
            required = true,
            allowableValues = {"CELSIUS", "FAHRENHEIT"}
    )
    private String fromUnit;
}





