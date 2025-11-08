package ru.mtuci.vuln.controllers.xxe;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;
import org.dom4j.io.SAXReader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

@Controller
@RequestMapping(path = "/xxe")
public class XxeController {

    private String mData;

    @GetMapping(path = "upload")
    public String uploadXmlForm() {
        return "xxe/upload-form.html";
    }

    @PostMapping(path = "upload")
    public String uploadXml(
            @RequestParam(value = "data") String xml,
            @RequestParam(value = "secure", required = false) Boolean secure
            ) throws ParserConfigurationException, IOException, SAXException, DocumentException {
        SAXReader reader = new SAXReader();
        if (secure != null && secure) {
            reader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            reader.setFeature("http://xml.org/sax/features/external-general-entities", false);
            reader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        }
        try {
            Document document = reader.read(new StringReader(xml));
            mData = document.asXML();
            return "redirect:/xxe/view";
        } catch (Exception e) {
            mData = "";
            return "redirect:/xxe/view";
        }
    }

    @GetMapping(value = "view")
    public ResponseEntity<String> view() {
        return ResponseEntity.status(200)
                .contentType(MediaType.TEXT_XML)
                .body(mData);
    }

}
