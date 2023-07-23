public class Sorter {
    public static Integer[][] mergeSort(Integer[][] a, String sortMode) {
        if (a.length < 2) {
            return a;
        }
            int mid = a.length / 2;
            Integer[][] l = new Integer[mid][];
            Integer[][] r = new Integer[a.length - mid][];
            System.arraycopy(a, 0, l, 0, mid);
            if (a.length - mid >= 0) System.arraycopy(a, mid, r, 0, a.length - mid);
            mergeSort(l, sortMode);
            mergeSort(r, sortMode);
            merge(a, l, r, mid, a.length - mid, sortMode);
            return a;
    }

    public static void merge(Integer[][] a, Integer[][] l, Integer[][] r, int left, int right, String sortMode) {
        if(sortMode.equals("-a")) {
            int i = 0, j = 0, k = 0;
            while (i < left && j < right) {
                if (l[i][0] <= r[j][0]) {
                    a[k++] = l[i++];
                } else {
                    a[k++] = r[j++];
                }
            }
            while (i < left) {
                a[k++] = l[i++];
            }
            while (j < right) {
                a[k++] = r[j++];
            }
        }
        else{
            int i = 0, j = 0, k = 0;
            while (i < left && j < right) {
                if (l[i][0] >= r[j][0]) { // Здесь изменено условие сравнения
                    a[k++] = l[i++];
                } else {
                    a[k++] = r[j++];
                }
            }
            while (i < left) {
                a[k++] = l[i++];
            }
            while (j < right) {
                a[k++] = r[j++];
            }
        }
    }
}
