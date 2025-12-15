package com.chamage.tempconverter.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "conversions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Database entity representing a saved temperature conversion record")
public class Conversion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Unique identifier for the conversion record",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Column(nullable = false)
    @Schema(
            description = "Original temperature value before conversion",
            example = "100.0"
    )
    private Double inputValue;

    @Column(nullable = false)
    @Schema(
            description = "Unit of the input temperature (CELSIUS or FAHRENHEIT)",
            example = "CELSIUS"
    )
    private String inputUnit;

    @Column(nullable = false)
    @Schema(
            description = "Converted temperature value",
            example = "212.0"
    )
    private Double outputValue;

    @Column(nullable = false)
    @Schema(
            description = "Unit of the converted temperature (CELSIUS or FAHRENHEIT)",
            example = "FAHRENHEIT"
    )
    private String outputUnit;

    @Column
    @Schema(
            description = "Optional nickname for this conversion",
            example = "Summer temperature"
    )
    private String nickname;

    @Column(nullable = false)
    @Schema(
            description = "Timestamp when the conversion was saved",
            example = "2025-12-13T10:15:30",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        timestamp = LocalDateTime.now();
    }
}


