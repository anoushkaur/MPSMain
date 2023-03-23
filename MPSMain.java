import java.util.Scanner;
public class MPSMain {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        // Declare objects and variables to be used in the main 
        final String STU_IMPORT_FILENAME = "StudentList.txt";
        final String FOOD_IMPORT_FILENAME = "FoodList.txt";
        MPS mps = new MPS(STU_IMPORT_FILENAME, FOOD_IMPORT_FILENAME);
        //sentinel value to end the loop
        boolean exit = false;
        int mpsOption = 0, accountOption = 0, studentID = 0, status = 0, receiver = 0, sender = 0, foodID = 0;
        double amt;
        // While loop that loops throughout until the user enters "3"
        while (exit != true) {
            //Main Screen where the user enters an integer and is taken to their option
            System.out.println("============================================");
            System.out.println("|              MEAL PLAN SYSTEM            |");
            System.out.println("============================================");
            System.out.println("1. Order Food");
            System.out.println("2. Account Options");
            System.out.println("3. Exit");
            System.out.print("Your Option: ");
            mpsOption = input.nextInt();
            /*Account option needs to be set to 0 so the user may re-enter main after 
            exiting prior*/
            accountOption = 0;
            /*IF the user enters option 1, they will be presented with a list of all 
            the available foods and be made to enter their student ID and food ID of choice*/
            if (mpsOption == 1) {
               mps.DisplayAllFoodInfo();
               System.out.print("Enter Student ID: ");
               studentID = input.nextInt();
               System.out.print("\nEnter Food ID: ");
               foodID = input.nextInt();
               status = mps.PurchaseFood(studentID, foodID);
               /*switch statment that handles the return values from the method "PurchaseFood" in mps
               and prints messages accordingly*/
               switch (status) {
                  case MPS.FOOD_NOT_EXIST :
                     System.out.println("The food ID doesn't exist in the database!");
                     break;
                  case MPS.SUCCESSFUL :
                     System.out.println("Purchased! Your new balance is $" + mps.GetStuBal(studentID));
                     break;
                  case MPS.STUDENT_NOT_EXIST :
                     System.out.println("The student ID doesn't exist in the database!");
                     break;
                  case -4 :
                     System.out.println("You don't have enough money to purchase this item!");
                     break;
               }
               
            }
            //IF the user enters 2 they will be presented with an account options screen
            else if (mpsOption == 2) {
                /*In order to stay in the account options screen, a loop is necessary. 
                Then, when they enter 5, the program will not enter the loop and return 
                back to the MPS main screen*/
                while (accountOption != 5) {
                    System.out.println("=============================================");
                    System.out.println("|              ACCOUNT OPTIONS              |");
                    System.out.println("============================================");
                    System.out.println("| 1. List all Students | 4. Transfer Money  |");
                    System.out.println("| 2. Account Info      | 5. Return to Main  |");
                    System.out.println("| 3. Top Up Money      |                    |");
                    System.out.println("=============================================");
                    System.out.print("Your Option: ");
                    accountOption = input.nextInt();
                    /* If statement that handles all the invalid values before it proceeds
                    and displays an appropriate warning message*/
                    if (accountOption < 1 || accountOption > 5){
                     System.out.println("Enter a valid option!");
                    }
                    /*Else if accountOption is 1 they will be presented with all the information
                     for every student. This is done by calling the method "DisplayAllStudentInfo"*/
                    else if (accountOption == 1) {
                        System.out.println("---------------------------------------------");
                        System.out.println("|             All Students Info             |");
                        System.out.println("---------------------------------------------");
                        mps.DisplayAllStudentInfo();
                        System.out.println("");
                    }
                    /*Else if the user enters 2 they will be presented with an account info screen,
                     where they will be prompted to enter their student id and will then be able to view
                      their history*/
                    else if (accountOption == 2) {
                        System.out.println("---------------------------------------------");
                        System.out.println("|                Account Info               |");
                        System.out.println("---------------------------------------------");
                        System.out.print("Student ID: ");
                        studentID = input.nextInt();
                        //If statement that checks if the student ID is valid
                        if (mps.IsStuExist(studentID)) {
                            System.out.printf("\n%-10s:%18s\n", "Student Name", mps.GetStuNameById(studentID));
                            System.out.printf("%-10s:%15s\n", "Student Balance", mps.GetStuBal(studentID));
                            System.out.println("---------------------------------------------");
                            System.out.printf("\n%-10s:\n", "History");
                            mps.DisplayHistory(studentID);
                            System.out.println("");
                        //Warning message for if they enter an invalid ID    
                        } else {
                            System.out.println("Enter a valid Student ID!");
                        }
                       /* If the user enters option 3, they are prompted to enter their 
                       student ID and top up amount*/ 
                     } else if (accountOption == 3){
                        System.out.println("---------------------------------------------");
                        System.out.println("|                   Top Up                  |");
                        System.out.println("---------------------------------------------");
                        System.out.print("Student ID   : ");
                        studentID = input.nextInt();
                        System.out.print("Top Up Amount: ");
                        amt = input.nextDouble();
                        status = mps.StuBalTopup(studentID, amt);
                        //Switch case that deals with return values for the method "StuBalTopup" in MPS
                        switch (status) {
                           case MPS.SUCCESSFUL :
                              System.out.println("Top Up Successful! Your new balance is $" + mps.GetStuBal(studentID));
                              break;
                           case MPS.STUDENT_NOT_EXIST :
                              System.out.println("Enter a valid student ID!");
                              break;
                           case MPS.INVALID_AMT :
                              System.out.println("Enter a valid amount");
                              break;
                        }
                      //IF the user enters 4, they will be allowed to transfer money from one account to another  
                    } else if (accountOption == 4) {
                      System.out.print("Transfer from Student ID: ");
                      sender = input.nextInt();
                      System.out.print("Transfer to Student ID: ");
                      receiver = input.nextInt();
                      System.out.print("Amount:");
                      amt = input.nextDouble();
                      status = mps.TransMoney(sender, receiver, amt);
                      //Switch case that deals with return values for the method "TransMoney" in MPS
                      switch (status) {
                         case MPS.SUCCESSFUL :
                         System.out.println("Transfer Successful! Your new balance is $" + mps.GetStuBal(sender));
                         break;
                         case MPS.STUDENT_NOT_EXIST :
                         System.out.println("Sender student ID doesn't exist in the database!");
                         break;
                         case MPS.INVALID_AMT :
                         System.out.println("Enter a valid amount!");
                         break;
                         case -4 :
                         System.out.println("The sender does not have enough money!");
                         break;
                         case -5 :
                         System.out.println("The sending and receiving IDs are the same!");
                         break;
                         case -6 :
                         System.out.println("The receiving student ID doesn't exist in the database!");
                         break;
                      }
                    }
                }
              //If the user enters 3, the program will not enter the loop
            } else if (mpsOption == 3) {
                exit = true;
              //If the user enters an invalid value, the program will print an error message
            } else if (mpsOption < 1 || mpsOption > 3) {
                System.out.println("Enter a valid option!");
            }
        }
        System.out.println("Thank you for using MPS!");
    }
}
