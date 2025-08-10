package rasmote.github.io.ai_diary.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono; //Mono는 비동기 방식의 통신 결과를 담는 클래스

@Service
@RequiredArgsConstructor
public class AiApiService {
    
    private final WebClient webClient;

    @Value("${gemini.api.key}")
    private String gemniAiKey;

    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent";

    public Mono<String> getAiFeedback(String diaryContent) {
        String prompt = "다음 일기 내용에 대하여, 심리학자처럼 따뜻하고 통찰력 있는 짧은 피드백을 작성해주세요. 일기의 전체 내용을 간략하게 요약한 뒤, 잘한점 혹은 조언할 점을 말해주세요. \n\n"
                + diaryContent;

        Map<String, Object> requestBody = Map.of( //api가 요구하는 request body 형식에 맞춰 작성
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt)))));

        return webClient.post()
                .uri(GEMINI_API_URL + "?key=" + gemniAiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .map(this::extractTextFromResponse);
    }
    
    private String extractTextFromResponse(Map<String, Object> response) {
        try {
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
            if (candidates == null || candidates.isEmpty()) {
                return "AI 피드백을 생성하지 못했습니다.";
            }
            Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
            return (String) parts.get(0).get("text");
        } catch (Exception e) {
            e.printStackTrace();
            return "AI 응답을 파싱하는 중 오류가 발생했습니다.";
        }
    }
}
