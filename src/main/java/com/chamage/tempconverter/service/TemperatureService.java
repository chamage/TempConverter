package com.chamage.tempconverter.service;

import com.chamage.tempconverter.dto.ConversionRequest;
import com.chamage.tempconverter.dto.ConversionResponse;
import com.chamage.tempconverter.dto.SaveConversionRequest;
import com.chamage.tempconverter.model.Conversion;
import com.chamage.tempconverter.repository.ConversionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
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

    public String generateCsvReport() {
        List<Conversion> conversions = getHistory();
        StringBuilder csv = new StringBuilder();

        // CSV Header
        csv.append("ID,Nickname,Input Value,Input Unit,Output Value,Output Unit,Timestamp\n");

        // CSV Data
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (Conversion c : conversions) {
            csv.append(c.getId()).append(",");
            csv.append(c.getNickname() != null ? "\"" + c.getNickname() + "\"" : "").append(",");
            csv.append(c.getInputValue()).append(",");
            csv.append(c.getInputUnit()).append(",");
            csv.append(c.getOutputValue()).append(",");
            csv.append(c.getOutputUnit()).append(",");
            csv.append(c.getTimestamp().format(formatter)).append("\n");
        }

        return csv.toString();
    }

    public String generateJsonReport() {
        try {
            List<Conversion> conversions = getHistory();
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            return mapper.writeValueAsString(conversions);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate JSON report", e);
        }
    }

    public String generateHtmlReport() {
        List<Conversion> conversions = getHistory();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html lang=\"en\">\n");
        html.append("<head>\n");
        html.append("    <meta charset=\"UTF-8\">\n");
        html.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        html.append("    <title>Temperature Conversion History Report</title>\n");
        html.append("    <style>\n");
        html.append("        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 20px; background: #f5f5f5; }\n");
        html.append("        .container { max-width: 1200px; margin: 0 auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }\n");
        html.append("        h1 { color: #667eea; text-align: center; margin-bottom: 10px; }\n");
        html.append("        .subtitle { text-align: center; color: #666; margin-bottom: 30px; }\n");
        html.append("        .stats { display: flex; justify-content: space-around; margin-bottom: 30px; }\n");
        html.append("        .stat-box { text-align: center; padding: 20px; background: #f8f9fa; border-radius: 8px; flex: 1; margin: 0 10px; }\n");
        html.append("        .stat-number { font-size: 2em; font-weight: bold; color: #667eea; }\n");
        html.append("        .stat-label { color: #666; margin-top: 5px; }\n");
        html.append("        table { width: 100%; border-collapse: collapse; margin-top: 20px; }\n");
        html.append("        th { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 15px; text-align: left; }\n");
        html.append("        td { padding: 12px 15px; border-bottom: 1px solid #e0e0e0; }\n");
        html.append("        tr:hover { background: #f8f9fa; }\n");
        html.append("        .nickname { color: #667eea; font-weight: 600; }\n");
        html.append("        .conversion { font-weight: 500; }\n");
        html.append("        .timestamp { color: #888; font-size: 0.9em; }\n");
        html.append("        .footer { text-align: center; margin-top: 30px; color: #888; font-size: 0.9em; }\n");
        html.append("        @media print { body { background: white; } .container { box-shadow: none; } }\n");
        html.append("    </style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("    <div class=\"container\">\n");
        html.append("        <h1>🌡️ Temperature Conversion History Report</h1>\n");
        html.append("        <p class=\"subtitle\">Generated on " + java.time.LocalDateTime.now().format(formatter) + "</p>\n");

        // Statistics
        html.append("        <div class=\"stats\">\n");
        html.append("            <div class=\"stat-box\">\n");
        html.append("                <div class=\"stat-number\">").append(conversions.size()).append("</div>\n");
        html.append("                <div class=\"stat-label\">Total Conversions</div>\n");
        html.append("            </div>\n");

        long celsiusCount = conversions.stream().filter(c -> "CELSIUS".equals(c.getInputUnit())).count();
        html.append("            <div class=\"stat-box\">\n");
        html.append("                <div class=\"stat-number\">").append(celsiusCount).append("</div>\n");
        html.append("                <div class=\"stat-label\">°C to °F</div>\n");
        html.append("            </div>\n");

        html.append("            <div class=\"stat-box\">\n");
        html.append("                <div class=\"stat-number\">").append(conversions.size() - celsiusCount).append("</div>\n");
        html.append("                <div class=\"stat-label\">°F to °C</div>\n");
        html.append("            </div>\n");
        html.append("        </div>\n");

        // Table
        html.append("        <table>\n");
        html.append("            <thead>\n");
        html.append("                <tr>\n");
        html.append("                    <th>ID</th>\n");
        html.append("                    <th>Nickname</th>\n");
        html.append("                    <th>Conversion</th>\n");
        html.append("                    <th>Timestamp</th>\n");
        html.append("                </tr>\n");
        html.append("            </thead>\n");
        html.append("            <tbody>\n");

        for (Conversion c : conversions) {
            String inputSymbol = "CELSIUS".equals(c.getInputUnit()) ? "°C" : "°F";
            String outputSymbol = "CELSIUS".equals(c.getOutputUnit()) ? "°C" : "°F";

            html.append("                <tr>\n");
            html.append("                    <td>").append(c.getId()).append("</td>\n");
            html.append("                    <td class=\"nickname\">")
                .append(c.getNickname() != null ? c.getNickname() : "-")
                .append("</td>\n");
            html.append("                    <td class=\"conversion\">")
                .append(String.format("%.2f%s → %.2f%s",
                    c.getInputValue(), inputSymbol, c.getOutputValue(), outputSymbol))
                .append("</td>\n");
            html.append("                    <td class=\"timestamp\">")
                .append(c.getTimestamp().format(formatter))
                .append("</td>\n");
            html.append("                </tr>\n");
        }

        html.append("            </tbody>\n");
        html.append("        </table>\n");
        html.append("        <div class=\"footer\">\n");
        html.append("            <p>Temperature Converter Application | Built with Spring Boot</p>\n");
        html.append("        </div>\n");
        html.append("    </div>\n");
        html.append("</body>\n");
        html.append("</html>");

        return html.toString();
    }
}
