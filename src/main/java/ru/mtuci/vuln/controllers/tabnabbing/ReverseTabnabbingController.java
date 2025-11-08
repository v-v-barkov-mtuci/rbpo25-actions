package ru.mtuci.vuln.controllers.tabnabbing;

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
@RequestMapping(path = "/tabnabbing/reverse")
@AllArgsConstructor
public class ReverseTabnabbingController {
    private final TemplateEngine mHtmlTemplateEngine;

    private ResponseEntity<String> sendData(Boolean useCOOP, String template) {
        Context context = new Context();
        var re = ResponseEntity.status(HttpStatus.OK);
        if (useCOOP != null && useCOOP) {
            re = re.header("Cross-Origin-Opener-Policy", "same-origin");
        }
        return re.body(mHtmlTemplateEngine.process(template, context));

    }

    @GetMapping("/legal-index")
    public ResponseEntity<String> indexSecure(
            @RequestParam(name = "coop", required = false) Boolean useCOOP
    ) {
        return sendData(useCOOP, "tabnabbing/reverse/legal_index.html");
    }

    @GetMapping("/evil-site")
    public ResponseEntity<String> legalSecure(
            @RequestParam(name = "coop", required = false) Boolean useCOOP
    ) {
        return sendData(useCOOP, "tabnabbing/reverse/evil_site.html");
    }
}
