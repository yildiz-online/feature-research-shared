package be.yildizgames.engine.feature.research.protocol.mapper;

import be.yildizgames.common.exception.business.BusinessException;

class ResearchMappingException extends BusinessException {
    ResearchMappingException(String message) {
        super(message);
    }

    ResearchMappingException(Exception cause) {
        super(cause);
    }

    ResearchMappingException(String message, Exception cause) {
        super(message, cause);
    }
}
