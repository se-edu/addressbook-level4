package seedu.address.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents Java version
 */
public class JavaVersion implements Comparable<JavaVersion> {

    private static final Pattern JAVA_8_VERSION_PATTERN =
            Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)\\_(\\d+)(-b(\\d+))?");

    private static final String EXCEPTION_STRING_NOT_JAVA_VERSION = "String is not a valid Java Version. %s";

    /**
     * Java version in String is formatted as "<discard>.<major>.<minor>_<update>" with optional suffix of
     * "-b<build>". Discard is not used because all Java versions (up to Java 8) will start with "1.".
     * This might change in Java 9.
     */
    private final int discard, major, minor, update, build;

    public JavaVersion(int discard, int major, int minor, int update, int build) {
        this.discard = discard;
        this.major = major;
        this.minor = minor;
        this.update = update;
        this.build = build;
    }

    public static JavaVersion fromString(String javaVersion) throws IllegalArgumentException {
        Matcher javaVersionMatcher = JAVA_8_VERSION_PATTERN.matcher(javaVersion);

        if (!javaVersionMatcher.find()) {
            throw new IllegalArgumentException(String.format(EXCEPTION_STRING_NOT_JAVA_VERSION, javaVersion));
        }

        return new JavaVersion(Integer.parseInt(javaVersionMatcher.group(1)),
                Integer.parseInt(javaVersionMatcher.group(2)),
                Integer.parseInt(javaVersionMatcher.group(3)),
                Integer.parseInt(javaVersionMatcher.group(4)),
                Integer.parseInt((javaVersionMatcher.group(6) != null ? javaVersionMatcher.group(6)
                        : "0")));
    }

    public String toString() {
        return String.format("%d.%d.%d_%d-b%d", discard, major, minor, update, build);
    }

    @Override
    public int compareTo(JavaVersion other) {
        return this.discard != other.discard ? this.discard - other.discard :
                this.major != other.major ? this.major - other.major :
                        this.minor != other.minor ? this.minor - other.minor :
                                this.update != other.update ? this.update - other.update :
                                        this.build != other.build ? this.build - other.build : 0;
    }

    @Override
    public int hashCode() {
        String hash = String.format("%1$d%2$d%3$d%4$03d%5$03d", discard, major, minor, update, build);

        return Integer.parseInt(hash);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof JavaVersion)) {
            return false;
        }
        final JavaVersion other = (JavaVersion) obj;

        return compareTo(other) == 0;
    }

    public static boolean isJavaVersionLower(JavaVersion runtime, JavaVersion required) {
        return runtime.compareTo(required) < 0;
    }
}
