package kr.idoun.photocategorizer;

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

        ImageParser parser = new JpegImageParser();

        int listSize = list.length;
        for (File file : list) {
            System.out.println("\nfilename:" + file.getName());

            ImageExifExtractor extractor = new ImageExifExtractor(file, parser);
            // TODO : Customized format support
            String formatted = extractor.getCreateDate(ImageExifExtractor.DEFAULT_FORMAT);

            Path targetChildPath = dirs.createTargetChildDirectory(formatted);

            if (targetChildPath == null) {
                System.out.println(file.getName() + "cannot be moved.");
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

        int exists = dirs.source.listFiles().length;
        System.out.println("End : moved:" + listSize + " exists:" + exists);

        // Remove empty source directory.
        // TODO : This should be optional function.
        if (exists == 0) {
            dirs.source.delete();
            System.out.println(dirs.source.getName() + " deleted. " + dirs.source.exists());
        }
    }
}
