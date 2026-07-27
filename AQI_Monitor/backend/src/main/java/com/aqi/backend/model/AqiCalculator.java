package com.aqi.backend.model;

import java.util.*;

public class AqiCalculator {

    public static class Breakpoint {
        public double cLow;
        public double cHigh;
        public int iLow;
        public int iHigh;


        Breakpoint(double cLow, double cHigh, int iLow, int iHigh) {
            this.cHigh = cHigh;
            this.cLow = cLow;
            this.iHigh = iHigh;
            this.iLow = iLow;
        }
    }

    public static final Map<String, List<Breakpoint>> BREAKPOINTS = new HashMap<>();

    static {

        // PM2.5
        BREAKPOINTS.put("PM2.5", List.of(
                new Breakpoint(0, 30, 0, 50),
                new Breakpoint(31, 60, 51, 100),
                new Breakpoint(61, 90, 101, 200),
                new Breakpoint(91, 120, 201, 300),
                new Breakpoint(121, 250, 301, 400),
                new Breakpoint(251, 350, 401, 500)
        ));

        // PM10
        BREAKPOINTS.put("PM10", List.of(
                new Breakpoint(0, 50, 0, 50),
                new Breakpoint(51, 100, 51, 100),
                new Breakpoint(101, 250, 101, 200),
                new Breakpoint(251, 350, 201, 300),
                new Breakpoint(351, 430, 301, 400),
                new Breakpoint(431, 500, 401, 500)
        ));

        // NO2
        BREAKPOINTS.put("NO2", List.of(
                new Breakpoint(0, 40, 0, 50),
                new Breakpoint(41, 80, 51, 100),
                new Breakpoint(81, 180, 101, 200),
                new Breakpoint(181, 280, 201, 300),
                new Breakpoint(281, 400, 301, 400),
                new Breakpoint(401, 500, 401, 500)
        ));

        // SO2
        BREAKPOINTS.put("SO2", List.of(
                new Breakpoint(0, 40, 0, 50),
                new Breakpoint(41, 80, 51, 100),
                new Breakpoint(81, 380, 101, 200),
                new Breakpoint(381, 800, 201, 300),
                new Breakpoint(801, 1600, 301, 400),
                new Breakpoint(1601, 2100, 401, 500)
        ));

        // CO (mg/m³)
        BREAKPOINTS.put("CO", List.of(
                new Breakpoint(0, 1, 0, 50),
                new Breakpoint(1.1, 2, 51, 100),
                new Breakpoint(2.1, 10, 101, 200),
                new Breakpoint(10.1, 17, 201, 300),
                new Breakpoint(17.1, 34, 301, 400),
                new Breakpoint(34.1, 50, 401, 500)
        ));

        // O3
        BREAKPOINTS.put("OZONE", List.of(
                new Breakpoint(0, 50, 0, 50),
                new Breakpoint(51, 100, 51, 100),
                new Breakpoint(101, 168, 101, 200),
                new Breakpoint(169, 208, 201, 300),
                new Breakpoint(209, 748, 301, 400),
                new Breakpoint(749, 1000, 401, 500)
        ));

        // NH3
        BREAKPOINTS.put("NH3", List.of(
                new Breakpoint(0, 200, 0, 50),
                new Breakpoint(201, 400, 51, 100),
                new Breakpoint(401, 800, 101, 200),
                new Breakpoint(801, 1200, 201, 300),
                new Breakpoint(1201, 1800, 301, 400),
                new Breakpoint(1801, 3000, 401, 500)
        ));
    }

}
