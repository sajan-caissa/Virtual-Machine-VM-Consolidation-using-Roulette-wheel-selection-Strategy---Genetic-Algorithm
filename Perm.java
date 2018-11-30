package org.cloudbus.cloudsim.examples;

import java.util.HashSet;
import java.util.Set;

public class Perm {
	
	public static void main(String arr[])
	{
		Set<String> pp=permutationFinder("1234");
		System.out.println(pp);
	}
	
	 public static Set<String> permutationFinder(String str) {
	        Set<String> perm = new HashSet<String>();
	        //Handling error scenarios
	        if (str == null) {
	            return null;
	        } else if (str.length() == 0) {
	            perm.add("");
	            return perm;
	        }
	        char initial = str.charAt(0); // first character
	        String rem = str.substring(1); // Full string without first character
	        Set<String> words = permutationFinder(rem);
	        for (String strNew : words) {
	            for (int i = 0;i<=strNew.length();i++){
	                perm.add(charInsert(strNew, initial, i));
	            }
	        }
	        return perm;
	 }

	 public static String charInsert(String str, char c, int j) {
	        String begin = str.substring(0, j);
	        String end = str.substring(j);
	        return begin + c + end;
	    }	

}
