package com.chamage.tempconverter.controller;

import com.chamage.tempconverter.dto.ConversionRequest;
import com.chamage.tempconverter.dto.ConversionResponse;
import com.chamage.tempconverter.dto.SaveConversionRequest;
import com.chamage.tempconverter.model.Conversion;
import com.chamage.tempconverter.service.TemperatureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/temperature")
@Tag(name = "Temperature Conversion", description = "API endpoints for temperature conversion operations between Celsius and Fahrenheit")
public class TemperatureController {

    @Autowired
    private TemperatureService temperatureService;

    @Operation(
            summary = "Convert temperature",
            description = "Converts a temperature value from Celsius to Fahrenheit or vice versa."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Temperature successfully converted",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ConversionResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input provided (e.g., invalid unit type or missing value)",
                    content = @Content
            )
    })
    @PostMapping("/convert")
    public ResponseEntity<ConversionResponse> convert(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Temperature conversion request containing value and unit",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ConversionRequest.class))
            )
            @RequestBody ConversionRequest request) {
        try {
            ConversionResponse response = temperatureService.convert(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Save conversion to history",
            description = "Saves a temperature conversion to the database with an optional nickname for later reference."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Conversion successfully saved to history",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Conversion.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input provided",
                    content = @Content
            )
    })
    @PostMapping("/save")
    public ResponseEntity<Conversion> saveConversion(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Conversion data to save including input/output values and optional nickname",
                    required = true,
                    content = @Content(schema = @Schema(implementation = SaveConversionRequest.class))
            )
            @RequestBody SaveConversionRequest request) {
        try {
            Conversion saved = temperatureService.saveConversion(request);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Get conversion history",
            description = "Retrieves all saved temperature conversion records from the database, " +
                    "ordered by timestamp in descending order (most recent first)."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved conversion history",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Conversion.class)
                    )
            )
    })
    @GetMapping("/history")
    public ResponseEntity<List<Conversion>> getHistory() {
        return ResponseEntity.ok(temperatureService.getHistory());
    }

    @Operation(
            summary = "Delete a specific conversion",
            description = "Deletes a single conversion record from the history by its unique ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Conversion successfully deleted",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Conversion with specified ID not found",
                    content = @Content
            )
    })
    @DeleteMapping("/history/{id}")
    public ResponseEntity<Void> deleteHistory(
            @Parameter(description = "ID of the conversion to delete", required = true, example = "1")
            @PathVariable Long id) {
        temperatureService.deleteHistory(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Clear all conversion history",
            description = "Deletes all conversion records from the database. This action cannot be undone."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "All conversion history successfully cleared",
                    content = @Content
            )
    })
    @DeleteMapping("/history")
    public ResponseEntity<Void> clearAllHistory() {
        temperatureService.clearAllHistory();
        return ResponseEntity.noContent().build();
    }
}



