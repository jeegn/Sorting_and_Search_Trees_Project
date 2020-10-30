public class SortMerge {

    public static void sort(int[] arr) {
	//TO BE IMPLEMENTED
        Queue<Integer> subarrays = getSubarrays(arr);
        int[] mergeArr = new int[arr.length];
        while (subarrays.size() != 2) {
            int l1 = subarrays.dequeue();
            int l2 = subarrays.dequeue();
            int r1 = subarrays.dequeue();
            int r2 = subarrays.dequeue();
            int i = l1;
            int j = r1;
            int sorted = 0;
            while ((i <= l2) && (j <= r2)) {
                if (arr[i] <= arr[j]) {
                    mergeArr[sorted] = arr[i];
                    i++;
                }
                else {
                    mergeArr[sorted] = arr[j];
                    j++;
                }
                sorted++;
            }
            while (i <= l2) {
                mergeArr[sorted] = arr[i];
                i++;
                sorted++;
            }
            while (j <= r2) {
                mergeArr[sorted] = arr[j];
                j++;
                sorted++;
            }
            int temp = 0;
            if (l2 + 1 == r1) {
                for (int k = l1; k <= r2; k++) {
                    arr[k] = mergeArr[temp];
                    temp++;
                }
                subarrays.enqueue(l1);
                subarrays.enqueue(r2);
            }
            else {
                int adjust = l2 - l1 + 1;
                int adjustor = sorted;
                for (int k = (r2 + 1); k < (arr.length - adjust); k++) {
                    mergeArr[sorted] = arr[k];
                    sorted++;
                }
                for (int k = (r2 + 1); k < (arr.length - adjust); k++) {
                    arr[k + adjust] = mergeArr[adjustor];
                    adjustor++;
                }
                for (int k = r1; k <= (r2 + adjust); k++) {
                    arr[k] = mergeArr[temp];
                    temp++;
                }
                for (int k = 0; k < subarrays.size(); k++) {
                    subarrays.enqueue(subarrays.dequeue() + adjust);
                }
                subarrays.enqueue(r1);
                subarrays.enqueue(r2 + adjust);
            }
        }
    }
    private static Queue<Integer> getSubarrays(int[] arr) {
        Queue<Integer> Q = new Queue<>();
        Q.enqueue(0);
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] >= arr[i - 1])
                continue;
            Q.enqueue(i - 1);
            Q.enqueue(i);
        }
        Q.enqueue((arr.length - 1));
        return Q;
    }
}
	