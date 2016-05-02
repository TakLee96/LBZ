import java.io.File;

/** Test redundancy between best and record.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class TestRecord {

    private static boolean existFile(String path) {
        File f = new File(path);
        return f.exists() && !f.isDirectory();
    }

    private static boolean existBest(int i) {
        return existFile("best/" + i + ".out");
    }

    private static boolean existRecord(int i) {
        return existFile("record/" + i + ".out");
    }

    private static String str(int i) {
        if (i < 10) return "  " + i + " ";
        if (i < 100) return " " + i + " ";
        return i + " ";
    }

    public static void main(String[] args) {
        int best = 0;
        int record = 0;
        for (int i = 1; i <= 492; i++) {
            System.out.print(str(i));
            if (existBest(i)) {
                System.out.print("BEST ");
                best++;
            } else {
                System.out.print("     ");
            }
            if (existRecord(i)) {
                System.out.print("RECORD\n");
                record++;
            } else {
                System.out.print("      \n");
            }
            if (existBest(i) && existRecord(i)) {
                System.out.println("REDUNDANT ENTRY ABOVE!");
            }
        }
        System.out.println("TOT " + str(best) + " " + str(record));
    }

}
