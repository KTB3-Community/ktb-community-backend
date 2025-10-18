package com.ktb.community.util.cursor;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;

public class CursorEncoder {

    public static String encode(LocalDateTime cursorTime) {
        if (cursorTime == null) return null;
        String raw = cursorTime.toString();
        return Base64.getUrlEncoder().encodeToString(raw.getBytes(StandardCharsets.UTF_8));
    }

    public static LocalDateTime decode(String encodedCursor) {
        if (encodedCursor == null || encodedCursor.isBlank()) return null;
        String decoded = new String(Base64.getUrlDecoder().decode(encodedCursor), StandardCharsets.UTF_8);
        return LocalDateTime.parse(decoded);
    }
}

