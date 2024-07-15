package com.ohigraffers.practice.post.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PostCreateRequest {

    @ApiModelProperty(value = "포스트 제목", required = true)
    @NotBlank(message = "제목은 필수 입력 항목입니다.")
    private String title;

    @ApiModelProperty(value = "포스트 내용", required = true)
    @NotBlank(message = "내용은 필수 입력 항목입니다.")
    private String content;

    @ApiModelProperty(value = "작성자", required = true)
    @NotBlank(message = "작성자는 필수 입력 항목입니다.")
    private String writer;

}
