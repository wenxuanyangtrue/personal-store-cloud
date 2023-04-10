package top.xsword.system_service.config;

import java.util.*;

/**
 * Author: ywx
 * Create Time: 2023/3/7
 * Description:
 */
public class Test01 {
    public static void main(String[] args) {
        int[] list = getRandomList(5);
        System.out.println(Arrays.toString(list));
    }

    public static int[] getRandomList(int maxNumber) {
        int[] list = new int[maxNumber];
        for (int i = 0; i < maxNumber; i++) {
            list[i] = i + 1;
        }

        Random random = new Random();
        for (int i = list.length - 1; i > 0; i--) {
            int num = random.nextInt(i);
            swap(list, i, num);
        }
        return list;
    }

    public static void swap(int[] arr, int i, int j) {
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }
}
