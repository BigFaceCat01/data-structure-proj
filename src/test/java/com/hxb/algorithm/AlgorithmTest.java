package com.hxb.algorithm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-16 13:47:04
 */
public class AlgorithmTest {
    /**
     * 题目 1
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度
     * 输入: "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     */
    public static void questionOne(String s){
        //最长子串
        String maxLengthSubString = "";
        //最长子串长度
        int maxLength = 0;

        int len = s.length();
        //子串左，右索引
        int left,right = 0,temp;
        //滑动窗口
        Set<Character> slideWindow = new HashSet<>(512);
        for(int i=0;i< len; i++){
            //如果剩余未验证子串长度比当前最长子串长度小，则直接退出
            if(len - i < maxLength){
                break;
            }
            //right使用上一次判断结果
            left = i;
            while (true){
                //如果right == 字符总长度
                if(right == len){
                    temp = slideWindow.size();
                    if(temp > maxLength){
                        maxLength = temp;
                        maxLengthSubString = s.substring(left,right);
                        System.out.println(maxLengthSubString);
                    }
//                    slideWindow.clear();
                    break;
                }

                char c = s.charAt(right);
                //判断当前滑动窗口是否包含该字符
                if(!slideWindow.contains(c)) {
                    slideWindow.add(c);
                    right++;
                }else {
                    temp = slideWindow.size();
                    if(temp > maxLength){
                        maxLength = temp;
                        maxLengthSubString = s.substring(left,right);
                        System.out.println(maxLengthSubString);
                    }
                    //清楚滑动窗口字符
                    slideWindow.remove(s.charAt(left));
                    break;
                }
            }
        }
        System.out.println("最长子串："+ maxLengthSubString);
        System.out.println("最长子串长度："+ maxLength);
    }

    /**
     * 题目 2
     * 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2
     * 请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
     * 你可以假设 nums1 和 nums2 不会同时为空。
     *
     * nums1 = [1, 2]
     * nums2 = [3, 4]
     * 则中位数是 (2 + 3)/2 = 2.5
     *
     * @param num1
     * @param num2
     */
    public static double questionTwo(int[] num1,int[] num2){
        //获得两个数组的长度
        int len = num1.length;
        int len2 = num2.length;
        boolean numLeft = false;
        boolean num2Left = false;
        //中位数索引
        int middle = (len + len2) / 2;
        //总长度是否是偶数
        boolean odd = (len + len2) % 2 == 0;
        //判断两个数组的有序性状态，从小到大，或者从大到小
        if(len == 0 || num1[0] <= num1[len -1]){
            //从小到大
            numLeft = true;
        }
        if(len2 == 0 || num2[0]<=num2[len2 - 1]){
            //从小到大
            num2Left = true;
        }
        //存储两个数组最终排序结果
        int[] temp = new int[len+len2];
        if(numLeft){
            //数组1从小到大
            if(num2Left){
                //数组2从小到大
                return fromLeftAndFromLeft(temp,num1,num2,len,len2,odd,middle);
            }else {
                //数组2从大到小
                return fromLeftAndFromRight(temp,num1,num2,len,len2,odd,middle);
            }
        }else {
            //数组1从大到小
            if(num2Left){
                //数组2从小到大
                return fromRightAndFromLeft(temp,num1,num2,len,len2,odd,middle);
            }else {
                //数组2从大到小
                return fromRightAndFromRight(temp,num1,num2,len,len2,odd,middle);
            }
        }
    }

    /**
     * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000
     * 输入: "babad"
     * 输出: "bab"
     * 注意: "aba" 也是一个有效答案。
     * @param s 源字符串
     * @return 回文子串
     */
    public static String questionThree(String s){
        //abb - > $#a#b#b#$
        //所以最终长度为s.length*2 + 3
        int len = (s.length() << 1) + 3;
        int  count = 0;
        char[] temp = new char[len];
        int[] redius = new int[len];
        char[] source = s.toCharArray();
        temp[count++] = '$';
        temp[len - 2] = '#';
        temp[len - 1] = '$';

        for (int i = 0; i < s.length(); i++) {
            temp[count++] = '#';
            temp[count++] = source[i];
        }

        int mx = 0, id = 0, resLen = 0, resCenter = 0;
        for (int i = 1; i < len-1; ++i) {
            redius[i] = mx > i ? Math.min(redius[2 * id - i], mx - i) : 1;
            while (temp[i + redius[i]] == temp[i - redius[i]]) {
                ++redius[i];
            }
            if (mx < i + redius[i]) {
                mx = i + redius[i];
                id = i;
            }
            if (resLen < redius[i]) {
                resLen = redius[i];
                resCenter = i;
            }
        }
        return s.substring((resCenter - resLen) / 2, resLen - 1);
    }

    /**
     *  给定字符串J 代表石头中宝石的类型，和字符串 S代表你拥有的石头。
     *  S 中每个字符代表了一种你拥有的石头的类型，你想知道你拥有的石头中有多少是宝石。
     * J 中的字母不重复，J 和 S中的所有字符都是字母。字母区分大小写，因此"a"和"A"是不同类型的石头。
     * 输入: J = "aA", S = "aAAbbbb"
     * 输出: 3
     * @param J
     * @param S
     * @return
     */
    public static int questionFour(String J, String S) {
        //判空
        if(Objects.isNull(J) || "".equals(J.trim())){
            return 0;
        }
        //判空
        if(Objects.isNull(S) || "".equals(S.trim())){
            return 0;
        }
        int total = 0;
        char[] jList = J.toCharArray();
        char[] sList = S.toCharArray();
        for(char cj: jList){
            for(char cs : sList){
                if(cj == cs){
                    total += 1;
                }
            }
        }
        return total;
    }

    /**
     * 递增整数数组，可能包含负数，求递增数组所有值平方后的递增数组
     *
     * @param A
     * @return
     */
    public static int[] questionFive(int[] A) {
        System.out.println(Arrays.toString(A));
        int len = A.length;

        //结果数组
        int[] result = new int[len];
        //情况1，A数组最大值小于等于0
        if(A[len - 1] <= 0){
            for(int j = 0;j<len;j++){
                result[j] = A[len-j-1] * A[len-j-1];
            }
            return result;
        }
        //情况2，A数组最小值大于0
        if(A[0]>=0){
            for(int j = 0;j<len;j++){
                result[j] = A[j] * A[j];
            }
            return result;
        }
        //情况3，A数组最大值大于0
        int left = 0;
        int right = len;
        //使用二分法找到数组中最小负数索引
        int nativeIndex = 0;
        for(;;){
            if(right - left == 1){
                break;
            }
            int middle = (left + right) / 2;
            if(A[middle] >= 0){
                right = middle;
                if(middle - left == 1){
                    nativeIndex = left;
                    System.out.println("最小负数为："+A[left]);
                    break;
                }
            }else{
                left = middle;
                if(right - middle == 1){
                    nativeIndex = middle;
                    System.out.println("最小负数为："+A[middle]);
                    break;
                }
            }
        }
        //最小负数往左平方后为递增，往右平方后也为递增，使用分治策略合并两个递增数组即为结果
        //定义指向两个递增数组的头部索引
        int nativeHead = nativeIndex;
        int positiveHead = nativeIndex + 1;
        int count = 0;
        for(;;){
            //负数已经循环完
            if(nativeHead < 0){
                result[count] = A[positiveHead] * A[positiveHead];
                positiveHead++;
                count++;
                if(count == len){
                    break;
                }
                continue;
            }
            //正数已经循环完
            if(positiveHead >= len){
                result[count] = A[nativeHead] * A[nativeHead];
                nativeHead--;
                count++;
                if(count == len){
                    break;
                }
                continue;
            }
            int num = A[nativeHead] * A[nativeHead];
            int num2 = A[positiveHead] * A[positiveHead];
            if(num <= num2){
                //第一个负数平方后小于等于第一个正数平方
                result[count] = num;
                nativeHead--;
            }else {
                result[count] = num2;
                positiveHead++;
            }
            count++;
            if(count == len){
                break;
            }
        }
        return result;
    }

    /**
     * 一个无序数组里有若干个正整数，范围从1到100，其中99个整数都出现了偶数次，
     * 只有一个整数出现了奇数次（比如1,1,2,2,3,3,4,5,5）
     * @return
     */
    public static int questionSix(int[] A){
        int result = 0;
        for (int i = 0;i  < A.length; i++) {
            result ^= A[i];
        }
        return result;
    }

    /**
     * 一个无序数组里有若干个正整数，范围从1到100，其中98个整数都出现了偶数次，
     * 只有两个整数出现了奇数次（比如1,1,2,2,3,4,5,5）
     * @param A
     * @return
     */
    public static int[] questionSeven(int[] A){
        int result = 0;
        for (int i = 0;i  < A.length; i++) {
            result ^= A[i];
        }
        String binaryResult = Integer.toBinaryString(result);
        int len = binaryResult.length();
        int offset = 0;
        for (; offset < len; offset++) {
            if(binaryResult.charAt(offset) == '1'){
                offset = (len - offset - 1);
                break;
            }
        }
        System.out.println(binaryResult);
        System.out.println(offset);
        int[] re = new int[2];
        for (int j = 0; j < A.length; j++) {

            if((A[j]>>offset & 1) == 1){
                re[0] ^= A[j];
            }else {
                re[1] ^= A[j];
            }
        }
        return re;
    }


    private static double fromRightAndFromRight(int[] temp, int[] num1, int[] num2, int len, int len2, boolean odd, int middle) {
        int tem ;
        for(int i = 0;i<len/2;i++){
            tem = num1[len-i-1];
            num1[len-i-1] = num1[i];
            num1[i] = tem;
        }
        for(int i = 0;i<len2/2;i++){
            tem = num2[len2-i-1];
            num2[len2-i-1] = num2[i];
            num2[i] = tem;
        }
        return fromLeftAndFromLeft(temp,num1,num2,len,len2,odd,middle);
    }

    private static double fromRightAndFromLeft(int[] temp, int[] num1, int[] num2, int len, int len2, boolean odd, int middle) {
        int tem ;
        for(int i = 0;i<len/2;i++){
            tem = num1[len-i-1];
            num1[len-i-1] = num1[i];
            num1[i] = tem;
        }
        return fromLeftAndFromLeft(temp,num1,num2,len,len2,odd,middle);
    }

    private static double fromLeftAndFromLeft(int[] temp,int[] num1,int[] num2,int len,int len2,boolean odd,int middle){
        int n1 = 0;
        int i = 0;
        int n2 = 0;
        while(true) {
            //判断是否某一数组已经循环结束
            if(n1 == len){
                //数组1循环结束,只需要循环数组2
                for(;n2<len2;n2++){
                    if(i == middle ){
                        if(odd){
                            //如果是偶数，中位数为middle,middle+1，两个索引位置的值得平均值
                            System.out.println("数组："+temp);
                            System.out.println("偶数时："+num2[n2]);
                            return (temp[i-1]+num2[n2]) / 2.0;
                        }else {
                            //如果两个数组长度和是奇数
                            System.out.println("数组："+temp.toString());
                            return num2[n2];
                        }
                    }
                    temp[i] = num2[n2];
                    i++;
                }
            }else if(n2 == len2){
                //数组2循环结束,只需要循环数组1
                for(;n1<len;n1++){
                    if(i == middle ){
                        if(odd){
                            //如果是偶数，中位数为middle,middle+1，两个索引位置的值得平均值
                            System.out.println("数组："+temp.toString());
                            System.out.println("偶数时："+num1[n1]);
                            return (temp[i-1]+num1[n1])/2.0;
                        }else {
                            //如果两个数组长度和是奇数
                            System.out.println("数组："+temp.toString());
                            return num1[n1];
                        }
                    }
                    temp[i] = num1[n1];
                    i++;
                }
            }
            if(len == 0){
                temp[i] = num2[n2];
                n2++;
            }else if(len2 == 0){
                temp[i] = num1[n1];
                n1++;
            }else if (num1[n1] > num2[n2]) {
                temp[i] = num2[n2];
                n2++;
            }else {
                temp[i] = num1[n1];
                n1++;
            }
            //如果已到中位数的索引位置
            if(i == middle ){
                if(odd){
                    //如果是偶数，中位数为middle,middle+1，两个索引位置的值得平均值
                    System.out.println("数组："+temp.toString());
                    System.out.println("偶数时："+temp[i]);
                    return (temp[i-1]+temp[i])/2.0;
                }else {
                    //如果两个数组长度和是奇数
                    System.out.println("数组："+temp.toString());
                    return temp[i];
                }
            }
            i++;
        }
    }

    private static double fromLeftAndFromRight(int[] temp,int[] num1,int[] num2,int len,int len2,boolean odd,int middle){
        int tem ;
        for(int i = 0;i<len2/2;i++){
            tem = num2[len2-i-1];
            num2[len2-i-1] = num2[i];
            num2[i] = tem;
        }
        return fromLeftAndFromLeft(temp,num1,num2,len,len2,odd,middle);
    }
}
