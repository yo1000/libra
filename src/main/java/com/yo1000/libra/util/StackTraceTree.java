package com.yo1000.libra.util;

import java.util.regex.Pattern;

/**
 * @author yo1000
 */
public class StackTraceTree {
    private int indent;
    private String joinLine;
    private Pattern includes;
    private Pattern excludes;

    public StackTraceTree() {
        this(2, "|_", null, Pattern.compile(
                "\\Q" + StackTraceTree.class.getName() + ".\\E|" +
                "\\Qjava.lang.Thread.getStackTrace\\E|" +
                "\\Qorg.junit\\E|" +
                "\\Qorg.apache.maven\\E|" +
                "\\Qjava.lang.reflect.Method.invoke\\E|" +
                "\\Qsun.reflect.NativeMethodAccessorImpl.invoke\\E|" +
                "\\Qsun.reflect.DelegatingMethodAccessorImpl.invoke\\E"
        ));
    }

    public StackTraceTree(int indent, String joinLine, Pattern includes, Pattern excludes) {
        this.indent = indent;
        this.joinLine = joinLine;
        this.includes = includes;
        this.excludes = excludes;
    }

    public String getJoinLine() {
        return joinLine;
    }

    public void setJoinLine(String joinLine) {
        this.joinLine = joinLine;
    }

    public int getIndent() {
        return indent;
    }

    public void setIndent(int indent) {
        this.indent = indent;
    }

    public Pattern getIncludes() {
        return includes;
    }

    public void setIncludes(Pattern includes) {
        this.includes = includes;
    }

    public Pattern getExcludes() {
        return excludes;
    }

    public void setExcludes(Pattern excludes) {
        this.excludes = excludes;
    }

    @Override
    public String toString() {
        StringBuilder spacesBuilder = new StringBuilder();
        for (int i = 0; i < getIndent(); i++) {
            spacesBuilder.append(" ");
        }
        String spaces = spacesBuilder.toString();

        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        StringBuilder stackTraceBuilder = new StringBuilder();
        StringBuilder indentsBuilder = new StringBuilder();
        for (StackTraceElement element : elements) {
            String elementString = element.getClassName() +
                    "." + element.getMethodName() +
                    "(" + element.getFileName() +
                    ":" + element.getLineNumber() +
                    ")\n";

            if (getIncludes() != null && !getIncludes().matcher(elementString).find()) {
                continue;
            }

            if (getExcludes() != null && getExcludes().matcher(elementString).find()) {
                continue;
            }

            stackTraceBuilder.append(indentsBuilder.toString());

            if (stackTraceBuilder.length() > 0) {
                stackTraceBuilder.append(getJoinLine());
                indentsBuilder.append(spaces);
            }

            stackTraceBuilder.append(elementString);

//            indentsBuilder.append(spaces);
        }

        return stackTraceBuilder.toString();
    }

    public static String getStackTraceString() {
        return new StackTraceTree().toString();
    }

    public static String getStackTraceString(int indent, String joinLine, Pattern includes, Pattern excludes) {
        return new StackTraceTree(indent, joinLine, includes, excludes).toString();
    }

    public static void printStackTrace() {
        System.out.println(getStackTraceString());
    }

    public static void printStackTrace(int indent, String joinLine, Pattern includes, Pattern excludes) {
        System.out.println(getStackTraceString(indent, joinLine, includes, excludes));
    }
}
