package com.globetrotter.bff.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.*;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/travel")
public class TravelController {

    private final static Logger LOG = LoggerFactory.getLogger(TravelController.class);

    private final ObjectMapper objectMapper;
    private final ChatClient chatClient;

    public TravelController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
        this.objectMapper = new ObjectMapper();
    }

    @GetMapping("/flights")
    public ResponseEntity<String> getFlights(@RequestParam String customerSearchQuery) {
        LOG.info("Customer search query: " + customerSearchQuery);

        try {
            // First, use ChatClient to extract flight parameters from the query
            Map<String, Object> flightParams = new HashMap<>();

            String template = """
                    You are a professional and friendly travel agent helping customers plan flights. Your goal is to extract travel intent from natural language queries (via a parameter called `customerSearchQuery`) and have a realistic back-and-forth conversation to complete any missing details.

                    1. Start by analyzing the query.
                    2. If the query includes origin and destination cities, extract them.
                    3. If dates are missing:
                       - Do NOT assume a default date.
                       - Use the local system's current date, add 14 days, and use that as `fromDate` and add 14 days to `fromDate` as `toDate`.
                    4. If either origin (`from`) or destination (`to`) is missing, respond conversationally and ask the customer for missing details.
                    5. Maintain context of previous answers from the user (remember previous responses).
                    6. Once all required fields are collected:
                       - Map origin and destination cities to IATA airport codes.
                       - Honor customer-provided number of passengers and travel class
                    7. Return a Java-style HashMap with keys: from, to, fromDate, toDate, numberOfPassengers, travelClass

                    Example:
                    Input: customerSearchQuery = "Can you find flights from San Francisco to Vijayawada?"
                    Output:
                    {
                      from: "SFO",
                      to: "VGA",
                      fromDate: "2024-07-05",
                      toDate: "2024-07-05",
                      numberOfPassengers: 1,
                      travelClass: "Economy"
                    }

                    If information is missing, respond like this:
                    {
                      "message": "Sure! Can you tell me your departure city and travel dates? Also, how many passengers and what travel class?"
                    }

                    Now use the customerSearchQuery below:
                    """;

            String fullPrompt = template + "\ncustomerSearchQuery: " + customerSearchQuery;
            Prompt prompt = new Prompt(fullPrompt);
            String output = chatClient.prompt(prompt).call().chatResponse().getResult().getOutput().getText();

            flightParams = objectMapper.readValue(output, HashMap.class);

            LOG.info("Extracted parameters: " + flightParams);

            LOG.info("MCP Tool Response: " + flightParams);
            return ResponseEntity.ok(objectMapper.writeValueAsString(flightParams));

        } catch (IllegalArgumentException e) {
            LOG.error("Invalid input: " + e.getMessage(), e);
            return ResponseEntity.badRequest()
                    .body("Invalid input: " + e.getMessage());
        } catch (NullPointerException e) {
            LOG.error("Missing required parameters: " + e.getMessage(), e);
            return ResponseEntity.badRequest()
                    .body("Missing required parameters: " + e.getMessage());
        } catch (Exception e) {
            LOG.error("Error processing flight search: " + e.getMessage(), e);
            return ResponseEntity.status(500)
                    .body("Error processing request: " + e.getMessage());
        }
    }

    private Map<String, Object> parseFlightParameters(String extractionResponse) throws Exception {
        Map<String, Object> flightParams = new HashMap<>();

        try {
            // Clean up the response to extract JSON (remove markdown code blocks if
            // present)
            String jsonString = extractionResponse.trim();
            if (jsonString.startsWith("```json")) {
                jsonString = jsonString.substring(7);
            }
            if (jsonString.startsWith("```")) {
                jsonString = jsonString.substring(3);
            }
            if (jsonString.endsWith("```")) {
                jsonString = jsonString.substring(0, jsonString.length() - 3);
            }
            jsonString = jsonString.trim();

            // Parse the JSON
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            // Extract parameters
            flightParams.put("from", jsonNode.get("from").asText());
            flightParams.put("to", jsonNode.get("to").asText());
            flightParams.put("fromDate", jsonNode.get("fromDate").asText());
            flightParams.put("toDate", jsonNode.get("toDate").asText());
            flightParams.put("numberOfPassengers", jsonNode.get("numberOfPassengers").asInt());
            flightParams.put("travelClass", jsonNode.get("travelClass").asText());

            LOG.info("Parsed flight parameters: " + flightParams);

        } catch (Exception e) {
            LOG.error("Error parsing flight parameters: " + e.getMessage(), e);
            // Fallback to default parameters if parsing fails
            flightParams.put("from", "LAX");
            flightParams.put("to", "JFK");
            flightParams.put("fromDate", "2025-01-15");
            flightParams.put("toDate", "2025-01-15");
            flightParams.put("numberOfPassengers", 1);
            flightParams.put("travelClass", "ECONOMY");
            LOG.info("Using fallback parameters: " + flightParams);
        }

        return flightParams;
    }

    // private String callMcpTool(Map<String, Object> flightParams) {
    // try {
    // LOG.info("Calling MCP tool get_flight_info with parameters: " +
    // flightParams);

    // // Create the tool call request
    // var toolRequest = new McpSchema.CallToolRequest("get_flight_info",
    // flightParams);

    // // Call the tool through MCP client (reactive)
    // Mono<McpSchema.CallToolResult> resultMono =
    // mcpAsyncClient.callTool(toolRequest);

    // // Block to get the result (for simplicity, in production you'd want to
    // handle
    // // this reactively)
    // var result = resultMono.block();

    // if (result != null && result.content() != null &&
    // !result.content().isEmpty()) {
    // // Convert the result to JSON string
    // String jsonResult = objectMapper.writeValueAsString(result.content().get(0));
    // LOG.info("MCP tool response: " + jsonResult);
    // return jsonResult;
    // } else {
    // LOG.warn("MCP tool returned null or empty result");
    // return createMockFlightResponse(flightParams);
    // }
    // } catch (Exception e) {
    // LOG.error("Error calling MCP tool: " + e.getMessage(), e);
    // // Return a mock response for now
    // return createMockFlightResponse(flightParams);
    // }
    // }

    // private String createMockFlightResponse(Map<String, Object> flightParams) {
    // try {
    // Map<String, Object> mockResponse = new HashMap<>();
    // mockResponse.put("meta", Map.of("count", 1));
    // mockResponse.put("data", List.of(Map.of(
    // "id", "mock-flight-1",
    // "type", "flight-offer",
    // "source", "MOCK",
    // "price", Map.of(
    // "currency", "USD",
    // "total", "299.99"),
    // "itineraries", List.of(Map.of(
    // "duration", "PT5H30M",
    // "segments", List.of(Map.of(
    // "departure",
    // Map.of("iataCode", flightParams.get("from"), "at",
    // flightParams.get("fromDate") + "T10:00:00"),
    // "arrival",
    // Map.of("iataCode", flightParams.get("to"), "at",
    // flightParams.get("fromDate") + "T15:30:00"),
    // "carrierCode", "AA",
    // "number", "123")))))));

    // return objectMapper.writeValueAsString(mockResponse);
    // } catch (Exception e) {
    // LOG.error("Error creating mock response: " + e.getMessage(), e);
    // return "{\"error\": \"Failed to create response\"}";
    // }
    // }
}
