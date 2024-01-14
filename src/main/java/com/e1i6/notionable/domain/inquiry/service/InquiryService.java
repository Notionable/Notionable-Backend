package com.e1i6.notionable.domain.inquiry.service;

import com.e1i6.notionable.domain.inquiry.dto.InquiryDto;
import com.e1i6.notionable.domain.inquiry.entity.Inquiry;
import com.e1i6.notionable.domain.inquiry.repository.InquiryRepository;
import com.e1i6.notionable.domain.template.entity.Template;
import com.e1i6.notionable.domain.template.repository.TemplateRepository;
import com.e1i6.notionable.domain.user.entity.User;
import com.e1i6.notionable.domain.user.repository.UserRepository;
import com.e1i6.notionable.global.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class InquiryService {

    private final InquiryRepository inquiryRepository;
    private final UserRepository userRepository;
    private final AwsS3Service awsS3Service;

    public InquiryDto writeInquiry(Long userId, InquiryDto inquiryDto, MultipartFile file) {
        Optional<User> optionalUser = userRepository.findById(userId);
        // Optional<Template> optionalTemplate = templateRepository.findById(template_id);

        User user = null;
        // Template template = null;
        if(optionalUser.isPresent()){
            user = optionalUser.get();
            // template = optionalTemplate.get();
        }

        String fileUrl = awsS3Service.getUrlFromFileName(awsS3Service.uploadFile(file));

        Inquiry inquiry = Inquiry.builder()
                .title(inquiryDto.getTitle())
                .content(inquiryDto.getContent())
                .fileUrl(fileUrl)
                .createdAt(LocalDateTime.now())
                .status("No")
                .template_id(inquiryDto.getTemplate_id())
                // .template(template)
                .user(user)
                .build();

        inquiryRepository.save(inquiry);

        return InquiryDto.toInquiryDto(inquiry);
    }
}
