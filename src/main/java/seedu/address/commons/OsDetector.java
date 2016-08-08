package seedu.address.commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Detect what Operating System (OS) the application is running in
 */
public class OsDetector {
    private static final String osName = System.getProperty("os.name");

    public enum Os {
        WINDOWS, MAC, LINUX32, LINUX64, ANY, UNKNOWN
    }

    public enum Architecture {
        UNKNOWN, X86_64, I386, I686
    }

    public static boolean isOnWindows() {
        return osName.startsWith("Windows");
    }

    public static boolean isOnMac() {
        return osName.startsWith("Mac OS");
    }

    public static boolean isOn64BitsLinux() {
        if (!isOnLinux()) {
            return false;
        }

        return getLinuxKernelArchitecture() == Architecture.X86_64;
    }

    public static boolean isOn32BitsLinux() {
        if (!isOnLinux()) {
            return false;
        }

        Architecture architecture = getLinuxKernelArchitecture();

        return architecture == Architecture.I386 || architecture == Architecture.I686;
    }

    public static boolean isOnLinux() {
        return osName.startsWith("Linux");
    }

    public static Os getOs() {
        if (isOnWindows()) {
            return Os.WINDOWS;
        } else if (isOnMac()) {
            return Os.MAC;
        } else if (isOn32BitsLinux()) {
            return Os.LINUX32;
        } else if (isOn64BitsLinux()) {
            return Os.LINUX64;
        } else {
            return Os.UNKNOWN;
        }
    }

    private static Architecture unknownArchitecture(Exception e) {
        return Architecture.UNKNOWN;
    }

    private static Architecture getLinuxKernelArchitecture() {
        try {
            Process process = Runtime.getRuntime().exec("uname -m");
            process.waitFor();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"))) {
                StringBuilder output = new StringBuilder();
                String nextOutputLine = reader.readLine();

                while (nextOutputLine != null) {
                    output.append(nextOutputLine).append(' ');
                    nextOutputLine = reader.readLine();
                }

                return getArchitectureFromString(output.toString());
            }

        } catch (IOException | InterruptedException e) {
            return unknownArchitecture(e);
        }
    }

    /**
     * Finds a relevant sub-string that characterizes an os architecture and
     * return the corresponding Architecture enum
     *
     * @param architectureDescription a string description of an os architecture
     * @return the corresponding Architecture enum
     */
    public static Architecture getArchitectureFromString(String architectureDescription) {
        if (architectureDescription == null) {
            return Architecture.UNKNOWN;
        } else if (architectureDescription.contains("x86_64")) {
            return Architecture.X86_64;
        } else if (architectureDescription.contains("i386")) {
            return Architecture.I386;
        } else if (architectureDescription.contains("i686")) {
            return Architecture.I686;
        } else {
            return Architecture.UNKNOWN;
        }
    }
}
