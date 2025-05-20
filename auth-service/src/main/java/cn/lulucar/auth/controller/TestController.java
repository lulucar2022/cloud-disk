package cn.lulucar.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wxl
 * @date 2025/4/29 20:02
 * @description
 */
@RestController
@RequestMapping("/auth")
public class TestController {
    @GetMapping("/nacos")
    public String nacos() {
        return "auth-service in nacos!";
    }
}
