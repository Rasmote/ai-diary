package rasmote.github.io.ai_diary.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping; //@Get()
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody; //@Controller()
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController; //클래스 생성자 생략.

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import rasmote.github.io.ai_diary.dto.CommonResponseDto;
import rasmote.github.io.ai_diary.dto.DiaryRequestDto;
import rasmote.github.io.ai_diary.dto.DiaryResponseDto;
import rasmote.github.io.ai_diary.dto.DiaryUpdateRequestDto;
import rasmote.github.io.ai_diary.service.DiaryService;

@Tag(name = "Diary API", description = "일기 관련 API")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService; // final 덕분에 생성자 생략 가능
    // public DiaryController(DiaryService diaryService) { ... } 생략

    @Operation(summary = "Hello API", description = "테스트용 메세지 반환")
    @GetMapping("/api/hello")
    public String hello() {
        return diaryService.getHelloMessage();
    }

    // C
    @Operation(summary = "일기 생성", description = "새로운 일기를 생성하고, AI 피드백을 요청합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "일기 생성 성공"),
            @ApiResponse(responseCode = "400", description = "입력값 유효성 검증 실패 (E001)")
    })
    @PostMapping("/diaries")
    public ResponseEntity<CommonResponseDto<DiaryResponseDto>> createDiary(@Valid @RequestBody DiaryRequestDto diaryRequestDto,
            @AuthenticationPrincipal UserDetails userDetails) { // UserDetails는 현재 로그인한 사용자의 정보를 담고 있음

        DiaryResponseDto createdDiary = diaryService.createDiary(diaryRequestDto, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponseDto.success(createdDiary));
    }

    // R
    @Operation(summary = "전체 일기 목록 조회", description = "모든 사용자의 일기 목록을 조회합니다. (향후 '내 일기 조회'로 변경 예정)")
    @ApiResponse(responseCode = "200", description = "전체 일기 조회 성공")
    @GetMapping("/diaries")
    public ResponseEntity<CommonResponseDto<List<DiaryResponseDto>>> getAllDiaries() {
        List<DiaryResponseDto> diaries = diaryService.getAllDiaries(); // 1. Service에서 전체 일기 목록을 조회
        return ResponseEntity.ok(CommonResponseDto.success(diaries)); // 2. 조회된 일기 목록을 응답으로 반환
    }

    @Operation(summary = "특정 일기 상세 조회", description = "ID로 특정 일기를 상세 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "특정 일기 조회 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 일기 (E005)")
    })
    @GetMapping("/diaries/{id}")
    public ResponseEntity<CommonResponseDto<DiaryResponseDto>> getDiaryById(@PathVariable("id") Long id) { // @pathVariable은 URL 경로에서 변수를 추출
        DiaryResponseDto diary = diaryService.getDiaryById(id); // 1. Service에서 특정 일기를 조회
        return ResponseEntity.ok(CommonResponseDto.success(diary)); // 2. 조회된 일기를 응답으로 반환
    }

    // U
    @Operation(summary = "일기 수정", description = "ID로 특정 일기를 수정합니다. 작성자 본인만 수정할 수 있습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일기 수정 성공"),
            @ApiResponse(responseCode = "403", description = "수정 권한 없음 (FORBIDDEN_ACCESS)"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 일기 (E005)")
    })
    @PutMapping("/diaries/{id}") // PutMapping : 업데이트 요청 처리
    public ResponseEntity<CommonResponseDto<DiaryResponseDto>> updateDiary(
            @PathVariable("id") Long id,
            @RequestBody DiaryUpdateRequestDto dto,
            @AuthenticationPrincipal UserDetails userDetails) {

        DiaryResponseDto diaryResponseDto = diaryService.updateDiary(id, dto, userDetails);
        return ResponseEntity.ok(CommonResponseDto.success(diaryResponseDto));
    }

    // D
    @Operation(summary = "일기 삭제", description = "ID로 특정 일기를 삭제합니다. 작성자 본인만 삭제할 수 있습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일기 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "삭제 권한 없음 (FORBIDDEN_ACCESS)"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 일기 (E005)")
    })
    @DeleteMapping("/diaries/{id}")
    public ResponseEntity<CommonResponseDto<Object>> deleteDiary(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        diaryService.deleteDiary(id, userDetails);
        return ResponseEntity.ok(CommonResponseDto.success(null));
    }
}