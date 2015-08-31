/**
 * RandomCode.java
 * com.psy.util
 * author      date      	
 * ──────────────────────────────────
 * xiao    2015年7月20日 		
 * Copyright (c)2015, All Rights Reserved.
 * Java源代码,未经许可禁止任何人、任何组织通过任何
 * 渠道使用、修改源代码.
*/
package com.psy.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

 
/**
 * ClassName:RandomCode
 *
 * TODO(生成教师端随机数)
 *
 * @project ans
 *
 * @author xiao
 *
 * @date   2015年7月20日 上午10:25:04	
 *
 * @class com.psy.util.RandomCode
 *
 */ 
public class RandomCode {
	
	/**
	 * TODO(随机生成教师端随机数)
	 * @param min
	 * @param max
	 * @return
	*/
	public static String NextInt(final int min, final int max){
		Random rand = new Random();
	    int tmp = Math.abs(rand.nextInt());
	    int tm = tmp % (max - min + 1) + min;
	    String num = String.valueOf(tm);
	    if(!Constants.randList.contains(num)){
	    	Constants.randList.add(num);
	    	return num;
		}else{
			num = NextInt(min,max);
			return num;
		}
	}

	public static void main(String[] args) {
		List<String> randList = new ArrayList<String>();
		for (int i = 0; i < 10000; i++) {
			randList.add(NextInt(1000,9999));
		}
		System.out.println(randList);
		String temp = "";
		for (int i = 0; i < randList.size() - 1; i++)
        {
            temp = randList.get(i).toString();
            for (int j = i + 1; j < randList.size(); j++)
            {
                if (temp.equals(randList.get(j).toString()))
                {
                    System.out.println("第" + (i + 1) + "个跟第" + (j + 1) + "个重复，值是：" + temp);
                }
            }
        }
	}

}
