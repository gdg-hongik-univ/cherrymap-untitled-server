/*package com.untitled.cherrymap.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

public class StationExtractorService {
    public static List<String> extractStationNames(String jsonResponse) {
        List<String> stationNames = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode stationListNode = rootNode.path("passStopList").path("stationList");

            // JSON 배열을 순회하면서 stationName만 리스트에 추가
            for (JsonNode stationNode : stationListNode) {
                stationNames.add(stationNode.path("stationName").asText());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return stationNames;
    }

}
 */
