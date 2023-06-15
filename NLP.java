import java.io.File;
import java.io.FileNotFoundException;
import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class NLP {

    ArrayList<String> intentArr = new ArrayList<String>();
    ArrayList<String> intentWeights = new ArrayList<String>();
    ArrayList<String> entityArr = new ArrayList<String>();
    

    public static String[] Split(String str, String x) {
        String[] ar = str.split(x);
        return ar;
    }

    public void Process(String in) {
        System.out.println(ReadTextIntent(in));
        //ReadTextWeights(intentArr.get(0));
        //NER();
        Pipeline(intentArr.get(0), in);
        
        
       

    }

    public void Pipeline(String intent, String str){
        String filePath = "pipeline.txt";
        try {
            File file = new File(filePath);

            Scanner scanner = new Scanner(file);
            Random rand = new Random();
            boolean m = false;

            while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            String[] pipe = Split(s, ":");
            
            if(intent.equals(pipe[0])){
                
                    String p = scanner.nextLine();
                    String[] entit = Split(Split(p,":")[1],",");
                    for (int i = 0; i < entit.length; i++) {
                        NER(pipe[1],entit[i], str);
                        System.out.println(pipe[1] + " " + entit[i] + " " + str);
                    }
                    
                        //System.out.println(entityArr.get(0));
                    
                        String p2 = scanner.nextLine();
                       //String[] p3 = Split(Split(p,"_")[1],":");
                        //System.out.println(p3);
                    
                   
                
                String response = scanner.nextLine();
                String r = Split(response,": ")[1];
                System.out.println(Split(r, ";")[rand.nextInt(Split(r, ";").length)]);

            }
            
            
            
                

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        

    }
    
    

    public void NER(String entit, String en, String str) {
        for (int index = 0; index < intentArr.size(); index++) {
            ReadTextWeights(intentArr.get(index));
        }

        String filePath = "Entity.txt";
        

        try {
            File file = new File(filePath);

            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
            String entity = scanner.nextLine();
            String[] ename = Split(Split(entity, "_")[1],":");
            String[] ent = Split(Split(Split(entity, "_")[1],":")[1],",");
            System.out.println(ent[0] + " "+ ename[1] + " " + str );
            if(ename[0].equals(entit) && ename[1].equals(en)){
                System.out.println("asd");
                WordMatch(ent,Split(str," "),ename[1]);
            }
            
            
            
                

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        

    }
    public void WordMatch(String[] ex, String[] str, String ename){
        for (int i = 0; i < ex.length; i++) {

            int n = 0;

            for (int j = 0; j < str.length; j++) {
                
                
                if(ex[i].length() >= str[j].length()){
                    for (int j2 = 0; j2 < str[j].length(); j2++) {
           
                        char e = ex[i].charAt(j2);
                        char s = str[j].charAt(j2);
                    
                       
                        if(s == e){
                            n++;
                            
                                if(n > (str[j].length() * 0.9)){
                                entityArr.add(ename);
                                
                                break;
                                
                            }
                            
                        }
                }
                if(ex[i].length() <= str[j].length()){
                    for (int j2 = 0; j2 < ex[i].length(); j2++) {
           
                        char e = ex[i].charAt(j2);
                        char s = str[j].charAt(j2);

                        if(s == e){
                            n++;
                                
                                    if(n > (ex[i].length() * 0.9) ){
                                        entityArr.add(ename);
                                        break;
                                
                               
                            } 
                        } 
                }
                }
                
    }  
    }
    
} 

    }
    

    public ArrayList ReadTextIntent(String str) {

        String intent, exline;
        String[] ex;
        String filePath = "properties.txt";
        ArrayList<String> r = new ArrayList<String>();

        try {
            File file = new File(filePath);

            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                
                String inline = scanner.nextLine();
                intent = inline.substring(8, inline.length());
                exline = scanner.nextLine();
                ex = Split(exline.substring(4, exline.length()), ", ");
                r = ArrayCompare(ex, intent, Split(str, " "));

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return r;
    }
    
    public ArrayList ArrayCompare(String[] ex, String intent, String[] str){

        for (int i = 0; i < ex.length; i++) {

            int n = 0;

            for (int j = 0; j < str.length; j++) {
                
                
                if(ex[i].length() >= str[j].length()){
                    for (int j2 = 0; j2 < str[j].length(); j2++) {
           
                        char e = ex[i].charAt(j2);
                        char s = str[j].charAt(j2);
                    
                       
                        if(s == e){
                            n++;
                            
                                if(n > (str[j].length() * 0.9)){
                                
                                if(!intentArr.contains(intent)){
                                    intentArr.add(intent);
                                }
                                break;
                                
                            }
                            
                        }
                }
                if(ex[i].length() <= str[j].length()){
                    for (int j2 = 0; j2 < ex[i].length(); j2++) {
           
                        char e = ex[i].charAt(j2);
                        char s = str[j].charAt(j2);

                        if(s == e){
                            n++;
                                
                                    if(n > (ex[i].length() * 0.9) ){
                                        if(!intentArr.contains(intent)){
                                            intentArr.add(intent);
                                        }
                                        break;
                                
                               
                            } 
                        } 
                }
                }
                
    }  
    }
    
} 
return intentArr;
}

    public void ReadTextWeights(String str){
        String filePath = "config.txt";
        try {
            File file = new File(filePath);

            Scanner scanner = new Scanner(file);
            boolean match = false;
            while(scanner.hasNextLine() && !match){
                String scn = scanner.nextLine();
                String [] r = Split(scn, ": ");
                    if(r[0].equals(str)){
                        match = true;
                        intentWeights.add(r[1]);
                    }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
    }
}




