package com.e1i6.notionable.domain.template.entity;

import com.e1i6.notionable.domain.template.data.dto.TemplateDetailDto;
import com.e1i6.notionable.domain.template.data.dto.TemplateDto;
import com.e1i6.notionable.domain.user.entity.User;
import com.e1i6.notionable.global.common.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Template extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long templateId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private String title;

    @Lob
    private String content;

    @NotNull
    private String thumbnail;

    @NotNull
    private String category;

    @NotNull
    private Integer price;

    @NotNull
    private String notionUrl;

    @ElementCollection
    private List<String> images = new ArrayList<>();

    public static TemplateDto toTemplateDto(Template template) {
        return TemplateDto.builder()
                .templateId(template.getTemplateId())
                .nickName(template.user.getNickName())
                .profile(template.user.getProfile())
                .title(template.getTitle())
                .category(template.getCategory())
                .thumbnail(template.getThumbnail())
                .price(template.getPrice())
                .createdAt(template.getCreatedAt().toString())
                .build();
    }

    public static TemplateDetailDto toDetailTemplateDto(Template template, List<String> imageUrlList) {
        return TemplateDetailDto.builder()
                .templateId(template.getTemplateId())
                .nickName(template.user.getNickName())
                .profile(template.user.getProfile())
                .title(template.getTitle())
                .category(template.getCategory())
                .thumbnail(template.getThumbnail())
                .price(template.getPrice())
                .content(template.getContent())
                .imageUrls(imageUrlList)
                .createdAt(template.getCreatedAt().toString())
                .build();
    }
}
