package net.idoun.photocategorizer;

import org.apache.sanselan.ImageParser;
import org.apache.sanselan.formats.jpeg.JpegImageParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        Directories dirs = new Directories();

        ArgumentsChecker argsChecker = new ArgumentsChecker(args, dirs);
        if (!argsChecker.isNormal()) {
            System.exit(-1);
            return;
        }

        File[] list = dirs.source.listFiles();
        if (list == null) {
            System.out.println("ERROR:NO_FILES");
            System.exit(-1);
        }

        ImageParser parser = new JpegImageParser();

        int listSize = list.length;
        for (File file : list) {
            System.out.println("\nfilename:" + file.getName());

            ImageExifExtractor extractor = new ImageExifExtractor(file, parser);
            // TODO : Customized format support
            String formatted = extractor.getCreateDate(ImageExifExtractor.DEFAULT_FORMAT);
            if (formatted == null) {
                continue;
            }

            Path targetChildPath = dirs.createTargetChildDirectory(formatted);

            if (targetChildPath == null) {
                System.out.println(file.getName() + "cannot be moved.");
                continue;
            }

            Path sourceFile = file.toPath();
            try {
                Path targetPath = targetChildPath.resolve(sourceFile.getFileName());
                if (targetPath != null) {
                    Files.move(sourceFile, targetPath);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File[] existedFiles = dirs.source.listFiles();
        if (existedFiles == null) {
            System.out.println("ERROR:NO_FILES_2");
            System.exit(-1);
        }
        int exists = existedFiles.length;
        System.out.println("End : moved:" + listSize + " exists:" + exists);

        // Remove empty source directory.
        // TODO : This should be optional function.
        if (exists == 0) {
            boolean deleted = dirs.source.delete();
            String message = dirs.source.getName() + " %s " + dirs.source.exists();
            System.out.println(String.format(message, deleted ? " deleted. " : " not deleted. "));
        }
    }
}
