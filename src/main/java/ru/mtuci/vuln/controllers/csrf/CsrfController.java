package ru.mtuci.vuln.controllers.csrf;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/csrf")
public class CsrfController {
    private String mData = "Initial data";

    @GetMapping(path = "/view")
    public String view(Model model) {
        model.addAttribute("data", mData);
        return "csrf/view.html";
    }

    @GetMapping(path = "/evil")
    public String evil() {
        return "csrf/evil.html";
    }

    @GetMapping(path = "/add")
    public @ResponseBody String add(
            @RequestParam("data") String data
    ) {
        mData = data;
        return "Ok";
    }

    @PostMapping(path = "/add")
    public String add2(
            //@RequestBody MultiValueMap<String, String> formData
            @RequestParam("data") String data
    ) {
        //mData = formData.get("data").getFirst();
        mData = data;
        return "redirect:/csrf/view";
    }
}
