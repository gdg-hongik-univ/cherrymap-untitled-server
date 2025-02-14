package com.untitled.cherrymap.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class TemplateService {

    public Map<String, String> getTemplate(String mode) {
        Map<String, String> template = new HashMap<>();

        if ("subway".equalsIgnoreCase(mode)) {
            template.put("beforeBoarding", "다음 역이 '{nextStation}'인지 확인하세요.");
            template.put("beforeExit", "다음 역에서 내릴 준비하세요!!");
            template.put("onExit", "{endStation}역입니다! 내리세요!");
        }
        else if ("bus".equalsIgnoreCase(mode)) {
            template.put("beforeBoarding", "버스 기사님께 '{endStation}'로 가는 버스인지 여쭤보세요.");
            template.put("beforeExit", "다음 정류장에서 내릴 준비하세요!!");
            template.put("onExit", "{endStation} 도착입니다! 내리세요!");
        }

        return template;
    }
}
