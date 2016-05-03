import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

public class TestMerge {

    public static void main(String[] args) {
        System.out.println("BEGIN");

        File otherBest = new File("other/best");
        File[] otherBests = otherBest.listFiles();

        for (File ob : otherBests) {
            processBest(ob);
        }

        File otherRecord = new File("other/record");
        File[] otherRecords = otherRecord.listFiles();

        for (File or : otherRecords) {
            processRecord(or);
        }

        System.out.println("DONE");
    }

    private static void processBest(File best) {
        int number = Integer.parseInt(best.getPath().split("/")[2].split(".out")[0]);
        File my = new File("best/" + number + ".out");
        if (my.exists() && !my.isDirectory()) {
            return;
        } else {
            System.out.println("new best for instance #" + number);
            best.renameTo(my);
            File record = new File("record/" + number + ".out");
            assert record.exists() && !record.isDirectory();
            record.delete();
        }
    }

    private static int readFile(File in) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(in));
            String line = null;
            for (int i = 0; i < 6; i++) {
                line = br.readLine();
            }
            return Integer.parseInt(line.split(":")[1].trim());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static void processRecord(File record) {
        int number = Integer.parseInt(record.getPath().split("/")[2].split(".out")[0]);
        File myBest = new File("best/" + number + ".out");
        if (myBest.exists() && !myBest.isDirectory()) {
            return;
        } else {
            File myRecord = new File("record/" + number + ".out");
            assert myRecord.exists() && !myRecord.isDirectory();
            int myscore = readFile(myRecord);
            int otherscore = readFile(record);
            if (myscore < otherscore) {
                System.out.println("new record for instance #" + number);
                myRecord.delete();
                record.renameTo(myRecord);
            }
        }
    }

}
