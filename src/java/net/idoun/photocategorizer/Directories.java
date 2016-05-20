package net.idoun.photocategorizer;

import java.io.File;
import java.nio.file.Path;

public class Directories {
    public File target;
    public File source;

    public Path createTargetChildDirectory(String childDir) {
        File targetChildDir;
        if (target == null) {
            targetChildDir = new File(source.getParent() + "/" + childDir);
        } else {
            targetChildDir = new File(target.getPath() + "/" + childDir);
        }
        System.out.print("filename target:" + targetChildDir.getPath());

        if (!targetChildDir.exists()) {
            boolean result = targetChildDir.mkdir();

            if (!result) {
                System.out.println("Target directory cannot be created.");
                return null;
            }
            if (targetChildDir.exists()) {
                System.out.println(" created.");
            }
        } else {
            System.out.println(" exists.");
        }
        return targetChildDir.toPath();
    }
}
