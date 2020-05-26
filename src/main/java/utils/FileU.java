package utils;

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileU {
    public static void copyFile(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath(),  StandardCopyOption.REPLACE_EXISTING);
    }

    public static String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    public static String imgUpload(Part filePart) throws IOException, ServletException {
        final String equipmentImgFolderPath = "D:\\My Documents\\Java\\TeamProjects\\EEP\\web\\assets\\img\\equipment\\";
        final String targetPath = "D:\\My Documents\\Java\\TeamProjects\\EEP\\target\\EEP-1.0-SNAPSHOT\\assets\\img\\equipment\\";
        final String fileName = getFileName(filePart);

        try (OutputStream fOut = new FileOutputStream(new File(equipmentImgFolderPath + fileName));
             OutputStream fTargetOut = new FileOutputStream(new File(targetPath + fileName));
             InputStream fileContent = filePart.getInputStream();
        ) {
            int read;
            final byte[] bytes = new byte[1024];

            while ((read = fileContent.read(bytes)) != -1) {
                fOut.write(bytes, 0, read);
                fTargetOut.write(bytes, 0, read);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return fileName;
    }
}
