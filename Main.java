import java.util.Scanner;

public class Main {
    
    public static void main(String args[]){
        NLP nlp = new NLP();
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        
        nlp.Process(input);
    }


}
