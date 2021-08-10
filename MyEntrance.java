package yg.test;

import java.util.LinkedHashMap;
import java.util.Map;

public class MyEntrance {

    public static void main(String[] args) {
        System.out.println(getChToNumber("九千六百万三千六百三亿四千二万九"));
        System.out.println(getChToNumber("九千六百二万三千六百三亿四千二万九"));
        System.out.println(getChToNumber("九千六百四十二万三千六百三亿四千二万九"));
//        System.out.println(getNumber("九亿六零三二七六三六八九二七六六五"));
    }


    public static Long getChToNumber(String chineseNumber){
        char[] number = new char[]{'零','一','二','三','四','五','六','七','八','九'};
        char[] unit = new char[]{'十','百','千','万','亿'};
        Long result = 0L; //最终结果
        Long temp = 1L; //临时
        Long temp1 = 0L; //存储亿以上
        boolean firstUnit = false;  //是否以单位开头
        int unitIndex = 0;//上次单位的下标
        boolean lastStr = false; //最后一个字符是否是number数组的
        boolean tempInNumber = false;
        for (int i=0;i<chineseNumber.length();i++){
            char ch = chineseNumber.charAt(i);
            boolean boo = true; // 是否需要遍历单位
//            boolean numRideUnit = false; //数字是否和单位相乘
            for (int j=0;j<number.length;j++){
                if (ch == number[j]){
                    temp = (long) j;
                    tempInNumber=true;
                    boo=false;
                    if(i == chineseNumber.length() - 1){
                        lastStr = true;
                    }
                    break;
                }
            }
            if(boo){
                for (int j=0;j<unit.length;j++){
                    if(i==0){
                        firstUnit=true;
                    }
                    if(ch == unit[j]){
                        if(j==4){
                            if(getBooleanByLastIndex(chineseNumber,i-1)){
                                temp1 = (result+temp)*100000000;
                                result = 0L;
                                temp = 1L;
                                continue;
                            }
                            temp1 = result*100000000;
                            result = 0L;
                            temp = 1L;
                            continue;
                        }
                        if(unitIndex<j){
                            if(!tempInNumber || !getBooleanByLastIndex(chineseNumber,i-1)){
                                temp=0L;
                            }
                            tempInNumber=false;
                            result+=temp;
                            temp=1L;
                        }
                        unitIndex=j;
                        switch (j){
                            case 0:
                                temp*=10;
                                break;
                            case 1:
                                temp*=100;
                                break;
                            case 2:
                                temp*=1000;
                                break;
                            case 3:
                                temp*=10000;
                                break;
                            case 4:
                                temp*=100000000;
                                break;
                            default:
                                break;
                        }
//                        numRideUnit = true;
                    }
                }
            }
                if(lastStr){
                    result += temp;
                    continue;
                }
                if(temp<10){
                    if(temp == 0L){
                        temp=1L;
                    }
                    if (firstUnit){
                        result*=temp;
                        temp=0L;
                        firstUnit=false;
                    }
                    continue;
                }
                if(result==0&&temp%10==0){
                    result+=temp;
                    temp=0L;
                    continue;
                }else if(result==0){
                    result+=temp;
                    temp=1L;
                    continue;
                }else if(result<temp){
                    result*=temp;
                    temp=1L;
                    continue;
                }else if(temp%100==0){
                    result+=temp;
                    temp=1L;
                    continue;
                }else{
                    result+=temp;
                    temp=0L;
                    continue;
                }
        }
        if(temp1>0){
            result +=temp1;
        }
        return result;
    }

    public static Boolean getBooleanByLastIndex(String chineseNumber,Integer index){
        if(index<0){
            return true;
        }
        char[] number = new char[]{'零','一','二','三','四','五','六','七','八','九'};
        Boolean boo = false;
        for (int i=0;i<number.length;i++){
            if(chineseNumber.charAt(index) == number[i]){
                boo = true;
                break;
            }
        }
        return boo;
    }

    public static Long getNumber(String chineseNumber){
        Map<Character,Character> map = new LinkedHashMap<>();
        map.put('一','1');
        map.put('二','2');
        map.put('三','3');
        map.put('四','4');
        map.put('五','5');
        map.put('六','6');
        map.put('七','7');
        map.put('八','8');
        map.put('九','9');
        map.put('零','0');

        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<chineseNumber.length();i++){
            char ch = chineseNumber.charAt(i);
            if(map.get(ch)!=null){
                stringBuilder.append(map.get(ch));
            }
        }
        return Long.parseLong(stringBuilder.toString());
    }
}
