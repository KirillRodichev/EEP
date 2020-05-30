package utils;

import exceptions.IllegalImageRelation;

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static constants.DispatchAttrs.EQUIPMENT;
import static constants.DispatchAttrs.GYM;
import static constants.ErrorMsg.ILLEGAL_IMG_REL;

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

    public static String imgUpload(Part filePart, String relation) throws IOException, IllegalImageRelation {

        String clientFolderPath;
        String serverFolderPath;

        switch (relation) {
            case GYM:
                clientFolderPath = "D:\\My Documents\\Java\\TeamProjects\\EEP\\web\\assets\\img\\gyms_logos\\";
                serverFolderPath = "D:\\My Documents\\Java\\TeamProjects\\EEP\\target\\EEP-1.0-SNAPSHOT\\assets\\img\\gyms_logos\\";
                break;
            case EQUIPMENT:
                clientFolderPath = "D:\\My Documents\\Java\\TeamProjects\\EEP\\web\\assets\\img\\equipment\\";
                serverFolderPath = "D:\\My Documents\\Java\\TeamProjects\\EEP\\target\\EEP-1.0-SNAPSHOT\\assets\\img\\equipment\\";
                break;
            default:
                throw new IllegalImageRelation(ILLEGAL_IMG_REL);
        }

        final String fileName = getFileName(filePart);

        try (OutputStream fOut = new FileOutputStream(new File(clientFolderPath + fileName));
             OutputStream fTargetOut = new FileOutputStream(new File(serverFolderPath + fileName));
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

    public static String getImgName(Part filePart, String relation) throws IOException, ServletException {
        String imgName;
        try {
            imgName = imgUpload(filePart, relation);
        } catch (RuntimeException e) {
            imgName = null;
        }
        return imgName;
    }
}
