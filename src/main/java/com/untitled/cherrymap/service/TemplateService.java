package com.untitled.cherrymap.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class TemplateService {

    public Map<String, String> getTemplate(String mode) {
        Map<String, String> template = new HashMap<>();

        if ("subway".equalsIgnoreCase(mode)) {
            template.put("beforeBoarding", "다음 역이 '{nextStation}'인지 확인하고 탑승하세요.");
            template.put("beforeExit", "다음 역인 '{endStation}'에서 하차하셔야 합니다. 내릴 준비하세요!!");
            template.put("onExit", "'{endStation}'도착입니다! 지금 당장 내리세요!");
        }
        else if ("bus".equalsIgnoreCase(mode)) {
            template.put("beforeBoarding", "버스 기사님께 '{nextStation}'쪽으로 가는 버스인지 여쭤보고 탑승하세요.");
            template.put("beforeExit", "다음 정류장인 '{endStation}'에서 하차하셔야 합니다. 내릴 준비하세요!!");
            template.put("onExit", "'{endStation}' 도착입니다! 지금 당장 내리세요!");
        }

        return template;
    }
}
