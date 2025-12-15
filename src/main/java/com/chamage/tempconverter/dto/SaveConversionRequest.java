package com.chamage.tempconverter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for saving a temperature conversion to history")
public class SaveConversionRequest {

    @Schema(
            description = "Temperature value that was converted",
            example = "25.5",
            required = true
    )
    private Double inputValue;

    @Schema(
            description = "Unit of the input temperature",
            example = "CELSIUS",
            required = true,
            allowableValues = {"CELSIUS", "FAHRENHEIT"}
    )
    private String inputUnit;

    @Schema(
            description = "Converted temperature value",
            example = "77.9",
            required = true
    )
    private Double outputValue;

    @Schema(
            description = "Unit of the converted temperature",
            example = "FAHRENHEIT",
            required = true,
            allowableValues = {"CELSIUS", "FAHRENHEIT"}
    )
    private String outputUnit;

    @Schema(
            description = "Optional nickname for this conversion",
            example = "Summer temperature"
    )
    private String nickname;
}

