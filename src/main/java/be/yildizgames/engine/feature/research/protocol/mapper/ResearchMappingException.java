package be.yildizgames.engine.feature.research.protocol.mapper;

import be.yildizgames.common.exception.business.BusinessException;

/**
 * @author Grégory Van den Borre
 */
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
