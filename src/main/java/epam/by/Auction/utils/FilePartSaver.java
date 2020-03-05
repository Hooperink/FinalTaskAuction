package epam.by.Auction.utils;

import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FilePartSaver {

    private FileNameGetterFromPart fileNameGetterFromPart;
    public static final String SAVE_FOLDER = "C:\\Users\\Вадим\\IdeaProjects\\FlowerAuction\\src\\main\\webapp\\images";
    public static final int MAX_FILE_SIZE = 1024;

    public FilePartSaver(FileNameGetterFromPart fileNameGetterFromPart) {
        this.fileNameGetterFromPart = fileNameGetterFromPart;
    }

    public String saveFile (Part part) throws IOException {
        String fileName = fileNameGetterFromPart.getFileName(part);
        try (OutputStream out = new FileOutputStream(new File(SAVE_FOLDER + File.separator + fileName))) {
            InputStream fileContent = part.getInputStream();
            int read = 0;
            final byte[] bytes = new byte[MAX_FILE_SIZE];
            while ((read = fileContent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            return fileName;
        }
    }
}
