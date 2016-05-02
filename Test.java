import java.io.File;

public class Test {

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
        for (int i = 1; i <= 492; i++) {
            System.out.print(str(i));
            if (existBest(i)) {
                System.out.print("BEST ");
            } else {
                System.out.print("     ");
            }
            if (existRecord(i)) {
                System.out.print("RECORD\n");
            } else {
                System.out.print("      \n");
            }
            if (existBest(i) && existRecord(i)) {
                System.out.println("REDUNDANT ENTRY ABOVE!");
            }
        }
    }

}
