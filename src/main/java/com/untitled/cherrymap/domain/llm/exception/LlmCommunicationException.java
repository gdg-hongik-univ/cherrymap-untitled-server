package com.untitled.cherrymap.domain.llm.exception;

import com.untitled.cherrymap.common.exception.BaseErrorCode;
import com.untitled.cherrymap.common.exception.CherrymapCodeException;

public class LlmCommunicationException extends CherrymapCodeException {
    public static final LlmCommunicationException EXCEPTION = new LlmCommunicationException();
    
    private LlmCommunicationException() {
        super(LlmErrorCode.LLM_COMMUNICATION_ERROR);
    }
} 