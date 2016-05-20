package net.idoun.photocategorizer;

import java.io.File;

public class ArgumentsChecker {
    private final String[] args;
    private final Directories dirs;

    public ArgumentsChecker(String[] args, Directories dirs) {
        this.args = args;
        this.dirs = dirs;
    }

    public boolean isNormal() {
        if (args.length == 0) {
            System.out.println("No source directory.");
            return false;
        }
        System.out.println("Source directory:" + args[0]);

        dirs.source = new File(args[0]);

        if (args.length > 1) {
            System.out.println("Target directory:" + args[1]);

            dirs.target = new File(args[1]);
        }

        if (!dirs.source.exists()) {
            System.out.println("Source is not exist.");
            return false;
        }

        if (!dirs.source.isDirectory()) {
            System.out.println("Source is not directory.");
            return false;
        }

        File[] list = dirs.source.listFiles();
        if (list == null || list.length == 0) {
            System.out.println("Source directory is empty.");
            return false;
        }

        return true;
    }
}
