package be.yildizgames.engine.feature.research.protocol.mapper;

/**
 * @author Grégory Van den Borre
 */
class ResearchMappingException extends IllegalArgumentException {

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
