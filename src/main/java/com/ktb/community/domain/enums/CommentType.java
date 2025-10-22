package com.ktb.community.domain.enums;

import lombok.Getter;

@Getter
public enum CommentType {

    BASIC(1, "일반 댓글"),
    PRIVATE(2, "비밀 댓글"),
    REPLY(3, "대댓글");

    private final int code;
    private final String type;


    CommentType(int code, String type) {
        this.code = code;
        this.type = type;
    }
}
