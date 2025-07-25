package com.untitled.cherrymap.domain.emotion.exception;

import com.untitled.cherrymap.common.exception.CherrymapCodeException;

public class EmotionNotFoundException extends CherrymapCodeException {
    public static final CherrymapCodeException EXCEPTION = new EmotionNotFoundException();

    private EmotionNotFoundException() {
        super(MemberErrorCode.MEMBER_NOT_FOUND);
    }
}