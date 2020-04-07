import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        final File file = new File(".");
        final File file1 = new File("./web/assets/img/");
        String relativePath1 = file1.getPath();
        String absolutePath1 = file1.getAbsolutePath();
        String canonicalPath1 = file1.getCanonicalPath();
        System.out.println("PATHS");
        System.out.println(relativePath1);
        System.out.println(canonicalPath1);
        System.out.println(absolutePath1);
        /*FileOutputStream fout = new FileOutputStream(new File("out.txt"));
        FileOutputStream fout1 = new FileOutputStream(new File("./web/assets/img/out.txt"));*/
    }
}
