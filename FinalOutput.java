import lbz.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

/** Driver class for LBZ searching algorithm.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class FinalOutput {

    private static boolean exists(String path) {
        File f = new File(path);
        return f.exists() && !f.isDirectory();
    }

    public static void main(String[] args) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("solutions.out"));
            BufferedReader br = null;
            for (int i = 1; i <= 492; i++) {
                if (exists("best/" + i + ".out")) {
                    br = new BufferedReader(new FileReader("best/" + i + ".out"));
                } else if (exists("record/" + i + ".out")) {
                    br = new BufferedReader(new FileReader("record/" + i + ".out"));
                } else {
                    throw new RuntimeException("no best or record found for " + i);
                }
                String line = br.readLine().trim();
                br.close();
                line += "\n";
                bw.write(line, 0, line.length());
            }
            bw.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return;
    }

}
