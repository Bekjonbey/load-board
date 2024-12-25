package com.example.transaction2.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Car {
    private String type;
    private String indexType;
    private String freeSeats;
    private Tariffs tariffs;
    private Seats seats;
    private String typeShow;

    // Getters and Setters
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Tariffs {
        private Tariff[] tariff;

        // Getters and Setters
        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Tariff {
            private Owner owner;
            private Carrier carrier;
            private String tariff;
            private Object elRegPossible;
            private String tariffService;
            private String comissionFee;
            private ClassService classService;
            private Seats seats;

            // Getters and Setters
            @Data
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Owner {
                private String type;
                private Country country;
                private Railway railway;

                // Getters and Setters
                @Data
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Country {
                    private String name;
                    private String code;

                    // Getters and Setters
                }

                @Data
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Railway {
                    private String name;
                    private String code;

                    // Getters and Setters
                }
            }

            @Data
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Carrier {
                private String name;

                // Getters and Setters
            }

            @Data
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class ClassService {
                private String type;

                // Getters and Setters
            }
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Seats {
        private String seatsUndef;
        private String seatsDn;
        private String seatsUp;
        private String seatsLateralDn;
        private String seatsLateralUp;
        private String freeComp;

        // Getters and Setters
    }
}

