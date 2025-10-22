package com.ktb.community.domain.enums;

import lombok.Getter;

@Getter
public enum PostType {

    BASIC(1, "일반 게시물"),
    NOTICE(2, "공지 게시물");

    private final int code;
    private final String type;

    PostType(int code, String type) {
        this.code = code;
        this.type = type;
    }
}
