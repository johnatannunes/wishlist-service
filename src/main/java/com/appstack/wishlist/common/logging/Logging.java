package com.appstack.wishlist.common.logging;

import com.appstack.wishlist.config.MDCKey;
import org.slf4j.Logger;
import org.slf4j.MDC;

import java.util.Arrays;

public class Logging {

    private Logging(){}

    public static LoggerConfigurator logger(Logger logger) {
        return new LoggerConfigurator(logger);
    }

    public static class LoggerConfigurator {
        private final Logger logger;

        private LoggerConfigurator(Logger logger) {
            this.logger = logger;
        }

        public MDCConfigurator mdcKey(MDCKey mdcKey) {
            return new MDCConfigurator(mdcKey, logger);
        }
    }

    public static class MDCConfigurator {
        private final MDCKey mdcKey;
        private final Logger logger;

        private MDCConfigurator(MDCKey mdcKey, Logger logger) {
            this.mdcKey = mdcKey;
            this.logger = logger;
        }

        public void info(String params, Object... args) {
            logWithMDC("INFO", params, args);
        }

        public void debug(String params, Object... args) {
            logWithMDC("DEBUG", params, args);
        }

        public void error(String params, Object... args) {
            logWithMDC("ERROR", params, args);
        }

        public void error(String params, Throwable throwable) {
            logWithMDC(params, throwable);
        }

        private void logWithMDC(String level, String params, Object... args) {
            final String requestId = MDC.get(mdcKey.getKey());
            String formattedParams = "%s, %s: {}".formatted(params, mdcKey.name());
            Object[] argsFormated = concat(args, requestId);

            switch (level) {
                    case "INFO":
                        logger.info(formattedParams, argsFormated);
                        break;
                    case "DEBUG":
                        logger.debug(formattedParams, argsFormated);
                        break;
                    case "ERROR":
                        logger.error(formattedParams, argsFormated);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid log level: " + level);
                }
        }

        private void logWithMDC(String params, Throwable throwable) {
            final String requestId = MDC.get(mdcKey.getKey());
            String formattedMsg = requestId != null ? "%s %s: {}".formatted(params, mdcKey.name()) : params;
            logger.error(formattedMsg, throwable, requestId);
        }

        private static Object[] concat(Object[] array, Object newElement) {
            Object[] newArray = Arrays.copyOf(array, array.length + 1);
            newArray[array.length] = newElement;
            return newArray;
        }
    }
}
