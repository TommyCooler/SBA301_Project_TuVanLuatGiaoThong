package sba.project.tuvanluatgiaothong.thridparty;

import sba.project.tuvanluatgiaothong.dto.response.GeminiResponse;
import sba.project.tuvanluatgiaothong.dto.response.GeminiTrafficResponse;

import java.util.List;

public interface IGeminiApi {

    public GeminiResponse generateContentAsObject(String prompt, List<String> contexts, String geminiAlias);

    public GeminiTrafficResponse generateTrafficLawResponse(String prompt, List<String> contexts, String geminiAlias);

}
