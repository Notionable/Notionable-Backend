package com.e1i6.notionable.domain.comment.controller;

import com.e1i6.notionable.domain.comment.data.TemplateCommentDto;
import com.e1i6.notionable.domain.comment.data.TemplateCommentReqDto;
import com.e1i6.notionable.domain.comment.service.TemplateCommentService;
import com.e1i6.notionable.global.common.response.BaseResponse;
import com.e1i6.notionable.global.common.response.ResponseCode;
import com.e1i6.notionable.global.common.response.ResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/template/comment")
public class TemplateCommentController {

    private final TemplateCommentService templateCommentService;

    @PostMapping("/{templateId}")
    public BaseResponse<TemplateCommentDto> createComment(
            @PathVariable Long templateId,
            @RequestPart TemplateCommentReqDto reqDto,
            @RequestPart("files") List<MultipartFile> multipartFiles) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(authentication.getName());

        TemplateCommentDto resDto = templateCommentService.createComment(userId, templateId, reqDto, multipartFiles);
        return new BaseResponse<>(resDto);
    }

    @PatchMapping("/{commentId}")
    public BaseResponse<TemplateCommentDto> updateComment(
            @PathVariable Long commentId,
            @RequestPart TemplateCommentDto reqDto,
            @RequestPart("files") List<MultipartFile> multipartFiles) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(authentication.getName());

        try {
            TemplateCommentDto resDto = templateCommentService.updateComment(userId, commentId, reqDto, multipartFiles);
            return new BaseResponse<>(resDto);
        } catch (ResponseException e) {
            return new BaseResponse<>(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            return new BaseResponse<>(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{commentId}")
    public BaseResponse<String> deleteComment(@PathVariable Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(authentication.getName());

        try {
            String result = templateCommentService.deleteComment(userId, commentId);
            return new BaseResponse<>(result);
        } catch (Exception e) {
            return new BaseResponse<>(e.getMessage());
        }
    }
}