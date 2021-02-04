package bzh.strawberry.strawbattle.utils;

import java.io.*;

/*
 * This file WorldUtil is part of a project StrawBattle.StrawBattle.
 * It was created on 28/01/2021 11:53 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */
public class WorldUtil {

    public static boolean deleteWorldFolder(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteWorldFolder(file);
                } else {
                    file.delete();
                }
            }
        }
        return (path.delete());
    }

    public static void copyWorld(File source, File target) {
        try {
            if (source.isDirectory()) {
                copyDirectory(source, target);
            } else {
                copyFile(source, target);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyDirectory(File source, File target) throws IOException {
        if (!target.exists()) {
            target.mkdir();
        }

        for (String f : source.list()) {
            copyWorld(new File(source, f), new File(target, f));
        }
    }

    private static void copyFile(File source, File target) throws IOException {
        try (
                InputStream in = new FileInputStream(source);
                OutputStream out = new FileOutputStream(target)
        ) {
            byte[] buf = new byte[1024];
            int length;
            while ((length = in.read(buf)) > 0) {
                out.write(buf, 0, length);
            }
        }
    }

}