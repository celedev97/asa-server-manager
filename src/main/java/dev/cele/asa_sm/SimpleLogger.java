package dev.cele.asa_sm;

import java.io.PrintWriter;
import java.io.StringWriter;

public interface SimpleLogger {
    void info(String message);
    default void info (String message, Throwable err){
        var sb = new StringBuilder();
        sb.append(message);
        sb.append("\n");
        sb.append(throwableToString(err));
        info(sb.toString());
    }
    void error(String message);

    default void error(String message, Throwable err){
        var sb = new StringBuilder();
        sb.append(message);
        sb.append("\n");
        sb.append(throwableToString(err));
        error(sb.toString());
    }

    private String throwableToString(Throwable error){
        var sw = new StringWriter();
        var printWriter = new PrintWriter(sw);
        error.printStackTrace(printWriter);
        return sw.toString();
    }
}
