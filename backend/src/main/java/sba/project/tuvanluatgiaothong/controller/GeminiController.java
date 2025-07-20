//package sba.project.tuvanluatgiaothong.controller;
//
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import sba.project.tuvanluatgiaothong.service.GeminiService;
//
//@RestController
//public class GeminiController {
//
//    private final GeminiService geminiService;
//
//    public GeminiController(GeminiService geminiService) {
//        this.geminiService = geminiService;
//    }
//
//    @PostMapping("/generate")
//    public String generate(@RequestBody String prompt) {
//        return geminiService.generateContent(prompt);
//    }
//
//}
