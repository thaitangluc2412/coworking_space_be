package com.coworkingspace.backend.common.utils;

import java.util.regex.Pattern;

public class PathUtils {
    private static final String REGULAR_EXPRESSION = "^([a-zA-Z0-9_-]+/)+$";

    public static String decoratePath(String path) {
        while (path.startsWith("/")) {
            path = path.substring(1);
        }
        while (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        path += "/";
        return path;
    }

    public static boolean validatePath(String path) {
        return Pattern.matches(REGULAR_EXPRESSION, path);
    }

    public static String getParentFolder(String originPath){
        String prefRegex="^https?:\\/\\/res.cloudinary.com\\/dpom2eaqn\\/image\\/upload\\/([a-zA-Z0-9])+\\/";
        //http://res.cloudinary.com/dpom2eaqn/image/upload/v1666022260/coworking-spacerooms/3b876076-1050-4ccf-bb64-15150718c53c/image_1.jpg
        String suffRegex="\\/image_([0-9]+).([a-z]+)$";
        originPath=originPath.replaceFirst(prefRegex, "");
        originPath=originPath.replaceAll(suffRegex, "");
        return originPath;
    }
}
