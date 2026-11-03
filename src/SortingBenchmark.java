 
import java.util.Random;

public class SortingBenchmark {

    static final int N = 100000;

public static void main(String[] args) {

    int[] random = createRandomArray();

    int[] reverse = createReverseArray();

    int[] partial = createPartialArray();

    int[] sorted = createSortedArray();

    int[][] testCases = {

        random, reverse, partial, sorted

    };

    String[] caseNames = {

        "Random", "Reverse", "Partial", "Sorted"

    };

    String[] algorithmNames = {

        "Selection Sort",

        "Insertion Sort",

        "Bubble Sort",

        "Merge Sort",

        "Quick Sort"

    };

    Sorter[] algorithms = {

        array -> selectionSort(array),

        array -> insertionSort(array),

        array -> bubbleSort(array),

        array -> mergeSort(array),

        array -> quickSort(array)

    };

    System.out.println("SORTING ALGORITHMS BENCHMARK");

    System.out.println("Array Size: " + N);

    System.out.println();

    System.out.printf("%-18s", "Algorithm");

    for (String name : caseNames) {

        System.out.printf("%15s", name);

    }

    System.out.println();

    for (int i = 0; i < algorithms.length; i++) {

        System.out.printf("%-18s", algorithmNames[i]);

        for (int j = 0; j < testCases.length; j++) {

            System.out.println(

                "\nRunning " + algorithmNames[i]

                + " - " + caseNames[j]

            );

            long time = measureTime(

                testCases[j], algorithms[i]

            );

            System.out.printf("%15d", time);

            System.out.flush();

        }

        System.out.println();

    }

    System.out.println("\nAll Tests Completed!");

}


    

    public static int[] createRandomArray() {

        int[] array = new int[N];

        Random random = new Random();

        for (int i = 0; i < N; i++) {

            array[i] = random.nextInt(100000);

        }

        return array;

    }
// Create a sorted array

public static int[] createSortedArray() {

    int[] array = new int[N];

    for (int i = 0; i < N; i++) {

        array[i] = i;

    }

    return array;

}

// Create a reverse sorted array

public static int[] createReverseArray() {

    int[] array = new int[N];

    for (int i = 0; i < N; i++) {

        array[i] = N - i;

    }

    return array;

}

// Create a partially sorted array

public static int[] createPartialArray() {

    int[] array = createSortedArray();

    Random random = new Random();

    int start = (N * 8) / 10;

    // Keep the first 80% sorted

    // Shuffle the remaining 20%

    for (int i = N - 1; i > start; i--) {
   

        int j = start + random.nextInt(i - start + 1);

        int temp = array[i];

        array[i] = array[j];

        array[j] = temp;

    }

    return array;

}
public static void selectionSort(int[] array) {

    int n = array.length;

    for (int i = 0; i < n - 1; i++) {

        int minIndex = i;

        for (int j = i + 1; j < n; j++) {

            if (array[j] < array[minIndex]) {

                minIndex = j;

            }

        }

        int temp = array[i];

        array[i] = array[minIndex];

        array[minIndex] = temp;

    }

}
public static void insertionSort(int[] array) {

    for (int i = 1; i < array.length; i++) {

        int key = array[i];

        int j = i - 1;

        while (j >= 0 && array[j] > key) {

            array[j + 1] = array[j];

            j--;

        }

        array[j + 1] = key;

    }

}
public static void bubbleSort(int[] array) {

    int n = array.length;

    for (int i = 0; i < n - 1; i++) {

        boolean swapped = false;

        for (int j = 0; j < n - 1 - i; j++) {

            if (array[j] > array[j + 1]) {

                int temp = array[j];

                array[j] = array[j + 1];

                array[j + 1] = temp;

                swapped = true;

            }

        }

        if (!swapped) {

            break;

        }

    }

}
public static void mergeSort(int[] array) {

    int[] temp = new int[array.length];

    mergeSort(array, temp, 0, array.length - 1);

}

public static void mergeSort(int[] array, int[] temp,

                             int left, int right) {

    if (left >= right) {

        return;

    }

    int middle = left + (right - left) / 2;

    mergeSort(array, temp, left, middle);

    mergeSort(array, temp, middle + 1, right);

    merge(array, temp, left, middle, right);

}

public static void merge(int[] array, int[] temp,

                         int left, int middle, int right) {

    int i = left;

    int j = middle + 1;

    int k = left;

    while (i <= middle && j <= right) {

        if (array[i] <= array[j]) {

            temp[k++] = array[i++];

        } else {

            temp[k++] = array[j++];

        }

    }

    while (i <= middle) {

        temp[k++] = array[i++];

    }

    while (j <= right) {

        temp[k++] = array[j++];

    }

    for (int x = left; x <= right; x++) {

        array[x] = temp[x];

    }

} 
public static void quickSort(int[] array) {

    quickSort(array, 0, array.length - 1);

}

public static void quickSort(int[] array, int low, int high) {

    if (low < high) {

        int pivotIndex = low +

                new java.util.Random().nextInt(high - low + 1);

        int temp = array[pivotIndex];

        array[pivotIndex] = array[high];

        array[high] = temp;

        int partitionIndex = partition(array, low, high);

        quickSort(array, low, partitionIndex - 1);

        quickSort(array, partitionIndex + 1, high);

    }

}

public static int partition(int[] array, int low, int high) {

    int pivot = array[high];

    int i = low - 1;

    for (int j = low; j < high; j++) {

        if (array[j] <= pivot) {

            i++;

            int temp = array[i];

            array[i] = array[j];

            array[j] = temp;

        }

    }

    int temp = array[i + 1];

    array[i + 1] = array[high];

    array[high] = temp;

    return i + 1;

}
interface Sorter {

    void sort(int[] array);

}

// Measure execution time

public static long measureTime(int[] original, Sorter sorter) {

    int[] testArray = java.util.Arrays.copyOf(

            original, original.length

    );

    long startTime = System.nanoTime();

    sorter.sort(testArray);

    long endTime = System.nanoTime();

    long time = (endTime - startTime) / 1000;

    return time;

}
}
