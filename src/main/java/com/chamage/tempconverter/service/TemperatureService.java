package com.chamage.tempconverter.service;

import com.chamage.tempconverter.dto.ConversionRequest;
import com.chamage.tempconverter.dto.ConversionResponse;
import com.chamage.tempconverter.dto.SaveConversionRequest;
import com.chamage.tempconverter.model.Conversion;
import com.chamage.tempconverter.repository.ConversionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemperatureService {

    @Autowired
    private ConversionRepository conversionRepository;

    public ConversionResponse convert(ConversionRequest request) {
        Double inputValue = request.getValue();
        String fromUnit = request.getFromUnit().toUpperCase();
        Double outputValue;
        String outputUnit;
        String formula;

        if ("CELSIUS".equals(fromUnit)) {
            outputValue = celsiusToFahrenheit(inputValue);
            outputUnit = "FAHRENHEIT";
            formula = "°F = (°C × 9/5) + 32";
        } else if ("FAHRENHEIT".equals(fromUnit)) {
            outputValue = fahrenheitToCelsius(inputValue);
            outputUnit = "CELSIUS";
            formula = "°C = (°F - 32) × 5/9";
        } else {
            throw new IllegalArgumentException("Invalid unit: " + fromUnit);
        }

        return new ConversionResponse(inputValue, fromUnit, outputValue, outputUnit, formula);
    }

    public Conversion saveConversion(SaveConversionRequest request) {
        Conversion conversion = new Conversion();
        conversion.setInputValue(request.getInputValue());
        conversion.setInputUnit(request.getInputUnit().toUpperCase());
        conversion.setOutputValue(request.getOutputValue());
        conversion.setOutputUnit(request.getOutputUnit().toUpperCase());
        conversion.setNickname(request.getNickname());
        return conversionRepository.save(conversion);
    }

    private Double celsiusToFahrenheit(Double celsius) {
        return (celsius * 9.0 / 5.0) + 32.0;
    }

    private Double fahrenheitToCelsius(Double fahrenheit) {
        return (fahrenheit - 32.0) * 5.0 / 9.0;
    }

    public List<Conversion> getHistory() {
        return conversionRepository.findAllByOrderByTimestampDesc();
    }

    public void deleteHistory(Long id) {
        conversionRepository.deleteById(id);
    }

    public void clearAllHistory() {
        conversionRepository.deleteAll();
    }
}

