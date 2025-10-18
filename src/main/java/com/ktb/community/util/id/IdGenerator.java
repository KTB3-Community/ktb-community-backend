package com.ktb.community.util.id;

public interface IdGenerator {
    long next(String domain);
    long peek(String domain);
}
