package com.aqi.backend.service;

import com.aqi.backend.model.AqiCalculator;
import com.aqi.backend.model.LocationData;
import com.aqi.backend.model.Result;
import java.util.List;
import java.util.Optional;

import static com.aqi.backend.model.AqiCalculator.BREAKPOINTS;

public class AqiService {
    public static Optional<Double> calculateSubIndex(LocationData.Pollutant pollutant){
        List<AqiCalculator.Breakpoint> range = BREAKPOINTS.get(pollutant.getPollutantId());
        try {

            int concentration = Integer.parseInt(pollutant.getAvgValue());
            for (AqiCalculator.Breakpoint bp : range) {
                if(pollutant.getPollutantId().equals("CO")) {
                    concentration = (int) (concentration/1000.0);
                }
                if (concentration >= bp.cLow && concentration <= bp.cHigh) {
                    return Optional.of((((bp.iHigh - bp.iLow) / (bp.cHigh - bp.cLow)) * (concentration - bp.cLow) + bp.iLow));
                }
            }
        }
        catch (Exception e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    public static Result calculateAQI(LocationData locationData) {

        int maxAqi = 0;
        String dominant = null;

        for (LocationData.Pollutant entry : locationData.getPollutants()) {

            Optional<Double> subIndex = calculateSubIndex(entry);
            if(!subIndex.isEmpty()) {
                if (subIndex.get() > maxAqi) {
                    maxAqi = (int) Math.round(subIndex.get());
                    dominant = entry.getPollutantId();
                }
            }

        }

        return new Result(maxAqi,dominant);
    }
}
