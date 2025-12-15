package com.chamage.tempconverter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response object containing the result of temperature conversion")
public class ConversionResponse {

    @Schema(
            description = "Original temperature value that was converted",
            example = "25.5"
    )
    private Double inputValue;

    @Schema(
            description = "Unit of the input temperature",
            example = "CELSIUS"
    )
    private String inputUnit;

    @Schema(
            description = "Converted temperature value",
            example = "77.9"
    )
    private Double outputValue;

    @Schema(
            description = "Unit of the converted temperature",
            example = "FAHRENHEIT"
    )
    private String outputUnit;

    @Schema(
            description = "Mathematical formula used for the conversion",
            example = "°F = (°C × 9/5) + 32"
    )
    private String formula;
}



