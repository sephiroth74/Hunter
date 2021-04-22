package com.quinn.hunter.plugin.debug;

/**
 * Hunter
 *
 * @author Alessandro Crugnola on 2019-12-09 - 14:39
 */
public class Constants {
    public static final int ARGUMENTS_NONE = 0;

    public static final int ARGUMENTS_SHORT = 1;

    public static final int ARGUMENTS_FULL = 2;

    public static boolean DEBUG_RESULT = true;

    public static int DEBUG_ARGUMENTS = ARGUMENTS_FULL;

    public static int LOG_LEVEL = 4; // Log.INFO

    public static final String PARAM_PRINTER_CLASS = "com/hunter/library/debug/ParameterPrinter";

    public static final String RESULT_PRINTER_CLASS = "com/hunter/library/debug/ResultPrinter";

    public static final String PLUGIN_LIBRARY = "com.hunter.library.debug";
}
