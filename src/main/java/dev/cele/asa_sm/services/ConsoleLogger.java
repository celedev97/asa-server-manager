package dev.cele.asa_sm.services;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsoleLogger implements org.slf4j.Logger {

    @Override
    public String getName() {
        return log.getName();
    }

    @Override
    public boolean isTraceEnabled() {
        return log.isTraceEnabled();
    }

    @Override
    public void trace(String s) {
        log.trace(s);
    }

    @Override
    public void trace(String s, Object o) {
        log.trace(s,o);
    }

    @Override
    public void trace(String s, Object o, Object o1) {
        log.trace(s,o,o1);
    }

    @Override
    public void trace(String s, Object... objects) {
        log.trace(s,objects);
    }

    @Override
    public void trace(String s, Throwable throwable) {
        log.trace(s,throwable);
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return log.isTraceEnabled(marker);
    }

    @Override
    public void trace(Marker marker, String s) {
        log.trace(marker,s);
    }

    @Override
    public void trace(Marker marker, String s, Object o) {
        log.trace(marker,s,o);
    }

    @Override
    public void trace(Marker marker, String s, Object o, Object o1) {
        log.trace(marker,s,o,o1);
    }

    @Override
    public void trace(Marker marker, String s, Object... objects) {
        log.trace(marker,s,objects);
    }

    @Override
    public void trace(Marker marker, String s, Throwable throwable) {
        log.trace(marker,s,throwable);
    }

    @Override
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    @Override
    public void debug(String s) {
        log.debug(s);
    }

    @Override
    public void debug(String s, Object o) {
        log.debug(s,o);
    }

    @Override
    public void debug(String s, Object o, Object o1) {
        log.debug(s,o,o1);
    }

    @Override
    public void debug(String s, Object... objects) {
        log.debug(s,objects);
    }

    @Override
    public void debug(String s, Throwable throwable) {
        log.debug(s,throwable);
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return log.isDebugEnabled(marker);
    }

    @Override
    public void debug(Marker marker, String s) {
        log.debug(marker,s);
    }

    @Override
    public void debug(Marker marker, String s, Object o) {
        log.debug(marker,s,o);
    }

    @Override
    public void debug(Marker marker, String s, Object o, Object o1) {
        log.debug(marker,s,o,o1);
    }

    @Override
    public void debug(Marker marker, String s, Object... objects) {
        log.debug(marker,s,objects);
    }

    @Override
    public void debug(Marker marker, String s, Throwable throwable) {
        log.debug(marker,s,throwable);
    }

    @Override
    public boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

    @Override
    public void info(String s) {
        log.info(s);
    }

    @Override
    public void info(String s, Object o) {
        log.info(s,o);
    }

    @Override
    public void info(String s, Object o, Object o1) {
        log.info(s,o,o1);
    }

    @Override
    public void info(String s, Object... objects) {
        log.info(s,objects);
    }

    @Override
    public void info(String s, Throwable throwable) {
        log.info(s,throwable);
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return log.isInfoEnabled(marker);
    }

    @Override
    public void info(Marker marker, String s) {
        log.info(marker,s);
    }

    @Override
    public void info(Marker marker, String s, Object o) {
        log.info(marker,s,o);
    }

    @Override
    public void info(Marker marker, String s, Object o, Object o1) {
        log.info(marker,s,o,o1);
    }

    @Override
    public void info(Marker marker, String s, Object... objects) {
        log.info(marker,s,objects);
    }

    @Override
    public void info(Marker marker, String s, Throwable throwable) {
        log.info(marker,s,throwable);
    }

    @Override
    public boolean isWarnEnabled() {
        return log.isWarnEnabled();
    }

    @Override
    public void warn(String s) {
        log.warn(s);
    }

    @Override
    public void warn(String s, Object o) {
        log.warn(s,o);
    }

    @Override
    public void warn(String s, Object... objects) {
        log.warn(s,objects);
    }

    @Override
    public void warn(String s, Object o, Object o1) {
        log.warn(s,o,o1);
    }

    @Override
    public void warn(String s, Throwable throwable) {
        log.warn(s,throwable);
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return log.isWarnEnabled(marker);
    }

    @Override
    public void warn(Marker marker, String s) {
        log.warn(marker,s);
    }

    @Override
    public void warn(Marker marker, String s, Object o) {
        log.warn(marker,s,o);
    }

    @Override
    public void warn(Marker marker, String s, Object o, Object o1) {
        log.warn(marker,s,o,o1);
    }

    @Override
    public void warn(Marker marker, String s, Object... objects) {
        log.warn(marker,s,objects);
    }

    @Override
    public void warn(Marker marker, String s, Throwable throwable) {
        log.warn(marker,s,throwable);
    }

    @Override
    public boolean isErrorEnabled() {
        return log.isErrorEnabled();
    }

    @Override
    public void error(String s) {
        log.error(s);
    }

    @Override
    public void error(String s, Object o) {
        log.error(s,o);
    }

    @Override
    public void error(String s, Object o, Object o1) {
        log.error(s,o,o1);
    }

    @Override
    public void error(String s, Object... objects) {
        log.error(s,objects);
    }

    @Override
    public void error(String s, Throwable throwable) {
        log.error(s,throwable);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return log.isErrorEnabled(marker);
    }

    @Override
    public void error(Marker marker, String s) {
        log.error(marker,s);
    }

    @Override
    public void error(Marker marker, String s, Object o) {
        log.error(marker,s,o);
    }

    @Override
    public void error(Marker marker, String s, Object o, Object o1) {
        log.error(marker,s,o,o1);
    }

    @Override
    public void error(Marker marker, String s, Object... objects) {
        log.error(marker,s,objects);
    }

    @Override
    public void error(Marker marker, String s, Throwable throwable) {
        log.error(marker,s,throwable);
    }
}
