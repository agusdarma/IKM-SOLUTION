package com.jakarta.software;

public class test {
	public static void main(String[] args) {
		for (int i = 1; i <= 10; i++) {
			int pembagi = 0;
			for (int j = 1; j <= i; j++) {
				if(i % j == 0)
					pembagi = pembagi + 1;
			}
			if(pembagi == 2){
				System.out.print(i);
				System.out.print(" ");
			}
		}
	}
}
