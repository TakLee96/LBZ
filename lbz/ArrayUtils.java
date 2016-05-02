package lbz;

/** Array Utility Functions.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class ArrayUtils {

    private static int circularGet(int[] a, int i) {
        return a[i % a.length];
    }

    public static boolean shiftEquals(int[] a, int[] b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null || a.length != b.length) {
            return false;
        }
        int i = 0;
        for (i = 0; i < b.length; i++) {
            if (b[i] == a[0]) {
                break;
            }
        }
        if (i == b.length) {
            return false;
        }
        for (int j = 0; j < a.length; j++) {
            if (a[j] != circularGet(b, j + i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean intersect(int[] a, int[] b) {
        for (int e : a) {
            if (contains(b, e)) {
                return true;
            }
        }
        return false;
    }

    public static int[] extend(int[] a, int[] b) {
        int[] c = new int[a.length+b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    public static boolean contains(int[] set, int elem) {
        for (int e : set) {
            if (e == elem) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(int[] path, int elem, int depth) {
        for (int i = 0; i < depth; i++) {
            if (path[i] == elem) {
                return true;
            }
        }
        return false;
    }

    public static int[] build(int[] path, int depth) {
        int[] result = new int[depth + 1];
        System.arraycopy(path, 0, result, 0, depth + 1);
        return result;
    }

    public static int[] buildReverse(int[] path, int depth) {
        if (depth + 1 == path.length) return null;
        int[] result = new int[path.length - (depth + 1)];
        System.arraycopy(path, depth + 1, result, 0, path.length - (depth + 1));
        return result;
    }

    public static String toString(int[] arr) {
        if (arr == null) return "null";
        String result = "[";
        for (int a : arr) {
            result += a + ",";
        }
        return result + "]";
    }

    public static void main(String[] args) {
        System.out.println(shiftEquals(
            new int[]{ 1, 2, 3 },
            new int[]{ 2, 3, 1 }
        ));
    }


}
