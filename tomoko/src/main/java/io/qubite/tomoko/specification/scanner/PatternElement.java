package io.qubite.tomoko.specification.scanner;

public class PatternElement {

    private final String name;
    private final boolean isParameter;
    private final String regex;

    PatternElement(String name, boolean isParameter, String regex) {
        this.name = name;
        this.isParameter = isParameter;
        this.regex = regex;
    }

    public static PatternElement fixed(String name) {
        return new PatternElement(name, false, null);
    }

    public static PatternElement parameter(String name, String regex) {
        return new PatternElement(name, true, regex);
    }

    public static PatternElement wildcardParameter(String name) {
        return new PatternElement(name, true, null);
    }

    public boolean isWildcard() {
        return isParameter && regex == null;
    }

    public boolean isParameter() {
        return isParameter;
    }

    public boolean isFixed() {
        return !isParameter();
    }

    public String getName() {
        return name;
    }

    public String getRegex() {
        return regex;
    }

    @Override
    public String toString() {
        return isParameter ? "{" + name + (isWildcard() ? "" : ":" + regex) + "}" : name;
    }
}
