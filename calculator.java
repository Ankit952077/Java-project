 import java.util.Scanner;

public class calculator {
    public static void main(String[] args) {
    float num,num2;
    System.out.println("Enter Frist num");
    Scanner scan=new Scanner(System.in);
      num=scan.nextFloat();
      
      System.out.println("Enter Second num");
      num2=scan.nextFloat();
      System.out.println("You have Entered");
      System.out.println(num);
      System.out.println("And");
      System.out.println(num2);
       
     

      String prom=" Enter 0 for add, 1 for subtraction, 2 for multiplication, 3 for division ";
      System.out.println(prom);
      int   input=scan.nextInt();
      switch (input) {
        case 0:
            System.out.println("Adding num");
            System.out.println("Result is ");
            System.out.println(num+num2);
            break;
            case 1:
            System.out.println("subtraction num");
            System.out.println("result is");
            System.out.println(num-num2);
             case 2:
            System.out.println("multiplication num");
            System.out.println("result is");
            System.out.println(num*num2);
             case 3:
            System.out.println("division  num");
            System.out.println("result is");
            System.out.println(num/num2);
            
         break;
      
      default:
        System.out.println("invalid number");
        break;
        
      }

    }
  }
