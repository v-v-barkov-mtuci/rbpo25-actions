package ru.mtuci.vuln.controllers.xss;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Controller
@AllArgsConstructor
@RequestMapping(path = "/xss/reflected")
public class ReflectedXssController {
    private final TemplateEngine mHtmlTemplateEngine;

    @GetMapping("/")
    public ResponseEntity<String> indexSecure(
            @RequestParam(name = "data", required = false) String data
    ) {
        Context context = new Context();
        context.setVariable("data", data);

        return ResponseEntity.status(HttpStatus.OK)
                .body(mHtmlTemplateEngine.process("xss/reflected.html", context));
    }

    @GetMapping("/set-cookie")
    public ResponseEntity<String> setCookie(
            @RequestParam(name = "key") String key,
            @RequestParam(name = "value") String value,
            @RequestParam(name = "secure") boolean isSecure,
            @RequestParam(name = "http_only") boolean isHttpOnly) {
        StringBuilder params = new StringBuilder();
        if (isSecure) {
            params.append("; Secure");
        }
        if (isHttpOnly) {
            params.append("; HttpOnly");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .header("Set-Cookie", String.format("%s=%s%s", key, value, params.toString()))
                .body(mHtmlTemplateEngine.process("xss/reflected.html", new Context()));
    }
}
