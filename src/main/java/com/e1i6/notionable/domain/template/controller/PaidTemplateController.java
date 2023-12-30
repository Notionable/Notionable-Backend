package com.e1i6.notionable.domain.template.controller;

import com.e1i6.notionable.domain.template.data.dto.FreeTemplateDetailDto;
import com.e1i6.notionable.domain.template.data.dto.PaidTemplateDetailDto;
import com.e1i6.notionable.domain.template.data.dto.PaidTemplateDto;
import com.e1i6.notionable.domain.template.data.dto.UploadPaidTemplateReqDto;
import com.e1i6.notionable.domain.template.service.PaidTemplateService;
import com.e1i6.notionable.global.common.response.BaseResponse;
import com.e1i6.notionable.global.common.response.ResponseCode;
import com.e1i6.notionable.global.common.response.ResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/paid-template")
@RequiredArgsConstructor
public class PaidTemplateController {

    private final PaidTemplateService paidTemplateService;

    @PostMapping("")
    public BaseResponse<String> uploadPaidTemplate(
            @RequestPart UploadPaidTemplateReqDto reqDto,
            @RequestPart("files") List<MultipartFile> multipartFiles) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(authentication.getName());

        String message = paidTemplateService.uploadPaidTemplate(userId, multipartFiles, reqDto);
        return new BaseResponse<>(message);
    }

    @GetMapping("/recommend")
    public BaseResponse<List<PaidTemplateDto>> getRecommendedPaidTemplate() {
        List<PaidTemplateDto> recommendedPaidTemplates = paidTemplateService.getRecommendTemplate();
        return new BaseResponse<>(recommendedPaidTemplates);
    }

    @GetMapping("")
    public BaseResponse<List<PaidTemplateDto>> getPaidTemplateListWithFilter (
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "category", required = false, defaultValue = "all") String category,
            @RequestParam(value = "criteria", required = false, defaultValue = "createdAt") String criteria) {

        return new BaseResponse<>(paidTemplateService.getPaidTemplatesWithCriteria(page, category, criteria));
    }

    @GetMapping("/detail/{templateId}")
    public BaseResponse<PaidTemplateDetailDto> getFreeTemplateDetail(@PathVariable Long templateId) {
        try {
            PaidTemplateDetailDto resDto = paidTemplateService.getPaidTemplateDetail(templateId);
            return new BaseResponse<>(resDto);
        } catch (ResponseException e) {
            return new BaseResponse<>(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            return new BaseResponse<>(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}
