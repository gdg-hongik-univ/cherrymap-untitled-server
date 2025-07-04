package com.untitled.cherrymap.security.jwt.refresh;

import com.untitled.cherrymap.common.dto.SuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReissueController {

    private final ReissueService reissueService;

    @PostMapping("/reissue")
    public ResponseEntity<SuccessResponse<?>> reissue(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(reissueService.reissue(request, response));
    }

}

