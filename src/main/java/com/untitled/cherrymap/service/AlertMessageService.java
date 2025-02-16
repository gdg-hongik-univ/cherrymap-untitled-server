package com.untitled.cherrymap.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AlertMessageService {

    private final TemplateService templateService;

    public AlertMessageService(TemplateService templateService) {
        this.templateService = templateService;
    }

    public String getAlertMessage(String mode, String userLocation, String startLocation, String endLocation, List<String> stationList) {
        List<String> stationNames = new ArrayList<>(stationList);

        // 메시지 템플릿 불러오기
        Map<String, String> template = templateService.getTemplate(mode);

        if (stationNames.isEmpty()) {
            return "잘 가고 있습니다!";
        }

        // 현재 위치가 경로 내에 있는지 확인
        int userIndex = stationNames.indexOf(userLocation);
        if (userIndex == -1) {
            return "경로를 벗어났습니다. 올바른 경로로 돌아가세요!!";
        }

        // 메시지 적용
        if (userLocation.equals(startLocation)) {
            return template.get("beforeBoarding").replace("{nextStation}", stationNames.get(1));
        }
        else if (userIndex == stationNames.size() - 2) {
            return template.get("beforeExit").replace("{endStation}", endLocation);
        }
        else if (userLocation.equals(endLocation)) {
            return template.get("onExit").replace("{endStation}", endLocation);
        }

        return "잘 가고 있습니다!";
    }
}
