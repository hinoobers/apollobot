package org.hinoob.apollo.util;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class FileUtils {

    public static boolean directoryExists(String path) {
        File f = new File(path);
        return f.exists() && f.isDirectory();
    }

    public static boolean fileExists(String path) {
        File f = new File(path);
        return f.exists() && f.isFile();
    }

    public static String readRaw(String path) {
        try {
            Scanner scanner = new Scanner(new File(path));
            StringBuilder sb = new StringBuilder();
            while(scanner.hasNextLine()) {
                sb.append(scanner.nextLine());
            }
            scanner.close();

            return sb.toString();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeRaw(String path, String content) {
        try {
            File f = new File(path);
            if(!f.exists()) create(path);

            FileWriter writer = new FileWriter(f);
            writer.write(content);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void create(String path) {
        try {
            File f = new File(path);
            if(f.exists()) {
                System.out.println(f.getName() + " already exists!");
                return;
            }
            f.createNewFile();

            System.out.println(f.getName() + " has been created!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
