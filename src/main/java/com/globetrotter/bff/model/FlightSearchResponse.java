package com.globetrotter.bff.model;

import java.util.List;
import java.util.Map;

public class FlightSearchResponse {
    private Meta meta;
    private List<FlightOffer> data;
    private Dictionaries dictionaries;

    public static class Meta {
        private int count;
        private Links links;

        public static class Links {
            private String self;

            // Getters and setters
            public String getSelf() {
                return self;
            }

            public void setSelf(String self) {
                this.self = self;
            }
        }

        // Getters and setters
        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public Links getLinks() {
            return links;
        }

        public void setLinks(Links links) {
            this.links = links;
        }
    }

    public static class FlightOffer {
        private String type;
        private String id;
        private String source;
        private boolean instantTicketingRequired;
        private boolean nonHomogeneous;
        private boolean oneWay;
        private boolean isUpsellOffer;
        private String lastTicketingDate;
        private String lastTicketingDateTime;
        private int numberOfBookableSeats;
        private List<Itinerary> itineraries;
        private Price price;
        private PricingOptions pricingOptions;
        private List<String> validatingAirlineCodes;
        private List<TravelerPricing> travelerPricings;

        public static class Itinerary {
            private String duration;
            private List<Segment> segments;

            public static class Segment {
                private DepartureArrival departure;
                private DepartureArrival arrival;
                private String carrierCode;
                private String number;
                private Aircraft aircraft;
                private Operating operating;
                private String duration;
                private String id;
                private int numberOfStops;
                private boolean blacklistedInEU;

                public static class DepartureArrival {
                    private String iataCode;
                    private String terminal;
                    private String at;

                    // Getters and setters
                    public String getIataCode() {
                        return iataCode;
                    }

                    public void setIataCode(String iataCode) {
                        this.iataCode = iataCode;
                    }

                    public String getTerminal() {
                        return terminal;
                    }

                    public void setTerminal(String terminal) {
                        this.terminal = terminal;
                    }

                    public String getAt() {
                        return at;
                    }

                    public void setAt(String at) {
                        this.at = at;
                    }
                }

                public static class Aircraft {
                    private String code;

                    // Getters and setters
                    public String getCode() {
                        return code;
                    }

                    public void setCode(String code) {
                        this.code = code;
                    }
                }

                public static class Operating {
                    private String carrierCode;

                    // Getters and setters
                    public String getCarrierCode() {
                        return carrierCode;
                    }

                    public void setCarrierCode(String carrierCode) {
                        this.carrierCode = carrierCode;
                    }
                }

                // Getters and setters
                public DepartureArrival getDeparture() {
                    return departure;
                }

                public void setDeparture(DepartureArrival departure) {
                    this.departure = departure;
                }

                public DepartureArrival getArrival() {
                    return arrival;
                }

                public void setArrival(DepartureArrival arrival) {
                    this.arrival = arrival;
                }

                public String getCarrierCode() {
                    return carrierCode;
                }

                public void setCarrierCode(String carrierCode) {
                    this.carrierCode = carrierCode;
                }

                public String getNumber() {
                    return number;
                }

                public void setNumber(String number) {
                    this.number = number;
                }

                public Aircraft getAircraft() {
                    return aircraft;
                }

                public void setAircraft(Aircraft aircraft) {
                    this.aircraft = aircraft;
                }

                public Operating getOperating() {
                    return operating;
                }

                public void setOperating(Operating operating) {
                    this.operating = operating;
                }

                public String getDuration() {
                    return duration;
                }

                public void setDuration(String duration) {
                    this.duration = duration;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public int getNumberOfStops() {
                    return numberOfStops;
                }

                public void setNumberOfStops(int numberOfStops) {
                    this.numberOfStops = numberOfStops;
                }

                public boolean isBlacklistedInEU() {
                    return blacklistedInEU;
                }

                public void setBlacklistedInEU(boolean blacklistedInEU) {
                    this.blacklistedInEU = blacklistedInEU;
                }
            }

            // Getters and setters
            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public List<Segment> getSegments() {
                return segments;
            }

            public void setSegments(List<Segment> segments) {
                this.segments = segments;
            }
        }

        public static class Price {
            private String currency;
            private String total;
            private String base;
            private List<Fee> fees;
            private String grandTotal;

            public static class Fee {
                private String amount;
                private String type;

                // Getters and setters
                public String getAmount() {
                    return amount;
                }

                public void setAmount(String amount) {
                    this.amount = amount;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }
            }

            // Getters and setters
            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getBase() {
                return base;
            }

            public void setBase(String base) {
                this.base = base;
            }

            public List<Fee> getFees() {
                return fees;
            }

            public void setFees(List<Fee> fees) {
                this.fees = fees;
            }

            public String getGrandTotal() {
                return grandTotal;
            }

            public void setGrandTotal(String grandTotal) {
                this.grandTotal = grandTotal;
            }
        }

        public static class PricingOptions {
            private List<String> fareType;
            private boolean includedCheckedBagsOnly;

            // Getters and setters
            public List<String> getFareType() {
                return fareType;
            }

            public void setFareType(List<String> fareType) {
                this.fareType = fareType;
            }

            public boolean isIncludedCheckedBagsOnly() {
                return includedCheckedBagsOnly;
            }

            public void setIncludedCheckedBagsOnly(boolean includedCheckedBagsOnly) {
                this.includedCheckedBagsOnly = includedCheckedBagsOnly;
            }
        }

        public static class TravelerPricing {
            private String travelerId;
            private String fareOption;
            private String travelerType;
            private Price price;
            private List<FareDetailsBySegment> fareDetailsBySegment;

            public static class FareDetailsBySegment {
                private String segmentId;
                private String cabin;
                private String fareBasis;
                private String brandedFare;
                private String brandedFareLabel;
                private String class_;
                private Baggage includedCheckedBags;
                private Baggage includedCabinBags;
                private List<Amenity> amenities;

                public static class Baggage {
                    private int quantity;

                    // Getters and setters
                    public int getQuantity() {
                        return quantity;
                    }

                    public void setQuantity(int quantity) {
                        this.quantity = quantity;
                    }
                }

                public static class Amenity {
                    private String description;
                    private boolean isChargeable;
                    private String amenityType;
                    private AmenityProvider amenityProvider;

                    public static class AmenityProvider {
                        private String name;

                        // Getters and setters
                        public String getName() {
                            return name;
                        }

                        public void setName(String name) {
                            this.name = name;
                        }
                    }

                    // Getters and setters
                    public String getDescription() {
                        return description;
                    }

                    public void setDescription(String description) {
                        this.description = description;
                    }

                    public boolean isChargeable() {
                        return isChargeable;
                    }

                    public void setChargeable(boolean chargeable) {
                        isChargeable = chargeable;
                    }

                    public String getAmenityType() {
                        return amenityType;
                    }

                    public void setAmenityType(String amenityType) {
                        this.amenityType = amenityType;
                    }

                    public AmenityProvider getAmenityProvider() {
                        return amenityProvider;
                    }

                    public void setAmenityProvider(AmenityProvider amenityProvider) {
                        this.amenityProvider = amenityProvider;
                    }
                }

                // Getters and setters
                public String getSegmentId() {
                    return segmentId;
                }

                public void setSegmentId(String segmentId) {
                    this.segmentId = segmentId;
                }

                public String getCabin() {
                    return cabin;
                }

                public void setCabin(String cabin) {
                    this.cabin = cabin;
                }

                public String getFareBasis() {
                    return fareBasis;
                }

                public void setFareBasis(String fareBasis) {
                    this.fareBasis = fareBasis;
                }

                public String getBrandedFare() {
                    return brandedFare;
                }

                public void setBrandedFare(String brandedFare) {
                    this.brandedFare = brandedFare;
                }

                public String getBrandedFareLabel() {
                    return brandedFareLabel;
                }

                public void setBrandedFareLabel(String brandedFareLabel) {
                    this.brandedFareLabel = brandedFareLabel;
                }

                public String getClass_() {
                    return class_;
                }

                public void setClass_(String class_) {
                    this.class_ = class_;
                }

                public Baggage getIncludedCheckedBags() {
                    return includedCheckedBags;
                }

                public void setIncludedCheckedBags(Baggage includedCheckedBags) {
                    this.includedCheckedBags = includedCheckedBags;
                }

                public Baggage getIncludedCabinBags() {
                    return includedCabinBags;
                }

                public void setIncludedCabinBags(Baggage includedCabinBags) {
                    this.includedCabinBags = includedCabinBags;
                }

                public List<Amenity> getAmenities() {
                    return amenities;
                }

                public void setAmenities(List<Amenity> amenities) {
                    this.amenities = amenities;
                }
            }

            // Getters and setters
            public String getTravelerId() {
                return travelerId;
            }

            public void setTravelerId(String travelerId) {
                this.travelerId = travelerId;
            }

            public String getFareOption() {
                return fareOption;
            }

            public void setFareOption(String fareOption) {
                this.fareOption = fareOption;
            }

            public String getTravelerType() {
                return travelerType;
            }

            public void setTravelerType(String travelerType) {
                this.travelerType = travelerType;
            }

            public Price getPrice() {
                return price;
            }

            public void setPrice(Price price) {
                this.price = price;
            }

            public List<FareDetailsBySegment> getFareDetailsBySegment() {
                return fareDetailsBySegment;
            }

            public void setFareDetailsBySegment(List<FareDetailsBySegment> fareDetailsBySegment) {
                this.fareDetailsBySegment = fareDetailsBySegment;
            }
        }

        // Getters and setters
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public boolean isInstantTicketingRequired() {
            return instantTicketingRequired;
        }

        public void setInstantTicketingRequired(boolean instantTicketingRequired) {
            this.instantTicketingRequired = instantTicketingRequired;
        }

        public boolean isNonHomogeneous() {
            return nonHomogeneous;
        }

        public void setNonHomogeneous(boolean nonHomogeneous) {
            this.nonHomogeneous = nonHomogeneous;
        }

        public boolean isOneWay() {
            return oneWay;
        }

        public void setOneWay(boolean oneWay) {
            this.oneWay = oneWay;
        }

        public boolean isUpsellOffer() {
            return isUpsellOffer;
        }

        public void setUpsellOffer(boolean upsellOffer) {
            isUpsellOffer = upsellOffer;
        }

        public String getLastTicketingDate() {
            return lastTicketingDate;
        }

        public void setLastTicketingDate(String lastTicketingDate) {
            this.lastTicketingDate = lastTicketingDate;
        }

        public String getLastTicketingDateTime() {
            return lastTicketingDateTime;
        }

        public void setLastTicketingDateTime(String lastTicketingDateTime) {
            this.lastTicketingDateTime = lastTicketingDateTime;
        }

        public int getNumberOfBookableSeats() {
            return numberOfBookableSeats;
        }

        public void setNumberOfBookableSeats(int numberOfBookableSeats) {
            this.numberOfBookableSeats = numberOfBookableSeats;
        }

        public List<Itinerary> getItineraries() {
            return itineraries;
        }

        public void setItineraries(List<Itinerary> itineraries) {
            this.itineraries = itineraries;
        }

        public Price getPrice() {
            return price;
        }

        public void setPrice(Price price) {
            this.price = price;
        }

        public PricingOptions getPricingOptions() {
            return pricingOptions;
        }

        public void setPricingOptions(PricingOptions pricingOptions) {
            this.pricingOptions = pricingOptions;
        }

        public List<String> getValidatingAirlineCodes() {
            return validatingAirlineCodes;
        }

        public void setValidatingAirlineCodes(List<String> validatingAirlineCodes) {
            this.validatingAirlineCodes = validatingAirlineCodes;
        }

        public List<TravelerPricing> getTravelerPricings() {
            return travelerPricings;
        }

        public void setTravelerPricings(List<TravelerPricing> travelerPricings) {
            this.travelerPricings = travelerPricings;
        }
    }

    public static class Dictionaries {
        private Map<String, Location> locations;
        private Map<String, String> aircraft;
        private Map<String, String> currencies;
        private Map<String, String> carriers;

        public static class Location {
            private String cityCode;
            private String countryCode;

            // Getters and setters
            public String getCityCode() {
                return cityCode;
            }

            public void setCityCode(String cityCode) {
                this.cityCode = cityCode;
            }

            public String getCountryCode() {
                return countryCode;
            }

            public void setCountryCode(String countryCode) {
                this.countryCode = countryCode;
            }
        }

        // Getters and setters
        public Map<String, Location> getLocations() {
            return locations;
        }

        public void setLocations(Map<String, Location> locations) {
            this.locations = locations;
        }

        public Map<String, String> getAircraft() {
            return aircraft;
        }

        public void setAircraft(Map<String, String> aircraft) {
            this.aircraft = aircraft;
        }

        public Map<String, String> getCurrencies() {
            return currencies;
        }

        public void setCurrencies(Map<String, String> currencies) {
            this.currencies = currencies;
        }

        public Map<String, String> getCarriers() {
            return carriers;
        }

        public void setCarriers(Map<String, String> carriers) {
            this.carriers = carriers;
        }
    }

    // Getters and setters
    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<FlightOffer> getData() {
        return data;
    }

    public void setData(List<FlightOffer> data) {
        this.data = data;
    }

    public Dictionaries getDictionaries() {
        return dictionaries;
    }

    public void setDictionaries(Dictionaries dictionaries) {
        this.dictionaries = dictionaries;
    }
}
