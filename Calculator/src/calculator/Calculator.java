/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author davidmunro
 */
public class Calculator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("enter two numbers");
        int a = scan.nextInt();
        int b = scan.nextInt();
    }
    
    public int add(int [] numbers){
        int sum = 0;
        
        for (int i = 0; i < numbers.length; i++) {
            sum = sum + numbers[i];
        }
        
        return sum;
    }
    
    public int multiply(int a, int b){
        int sum = 0;
        
        sum = a*b;
        
        return sum;
    }
        
    
}
