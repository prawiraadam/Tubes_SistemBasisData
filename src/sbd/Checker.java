package sbd;

import java.util.Stack;

/**
 *
 * @author ASUS
 */
public class Checker {
    private Stack<Character> temp = new Stack<>();
    private Stack<Character> stackCek = new Stack<>();
    private boolean stmt = true;
    private boolean end = false;
    private int currentState = 0;
    
    //Parsing perintah 'SELECT', return boolean
    public boolean cekSelect(String kata){
        for(int i = 0; i < kata.length(); i++){
            temp.push(kata.charAt(i));
        }
        stackCek.push('#');
        while(!temp.isEmpty()){
            stackCek.push(temp.peek());
            temp.pop();
        }
        while(end == false){
            switch(currentState){
                case 0 : 
                    if(stackCek.peek() ==  's' || stackCek.peek() == 'S'){
                        stackCek.pop();
                        currentState = 1;
                    }
                    else{
                        stmt = false;
                        end = true;
                    }
                    break;
                case 1 : 
                    if(stackCek.peek() ==  'e' || stackCek.peek() == 'E'){
                        stackCek.pop();
                        currentState = 2;
                    }
                    else{
                        stmt = false;
                        end = true;
                    }
                    break;
                case 2 : 
                    if(stackCek.peek() ==  'l' || stackCek.peek() == 'L'){
                        stackCek.pop();
                        currentState = 3;
                    }
                    else{
                        stmt = false;
                        end = true;
                    }
                    break;
                case 3 : 
                    if(stackCek.peek() ==  'e' || stackCek.peek() == 'E'){
                        stackCek.pop();
                        currentState = 4;
                    }
                    else{
                        stmt = false;
                        end = true;
                    }
                    break;
                case 4 : 
                    if(stackCek.peek() ==  'c' || stackCek.peek() == 'C'){
                        stackCek.pop();
                        currentState = 5;
                    }
                    else{
                        stmt = false;
                        end = true;
                    }
                    break;
                case 5 : 
                    if(stackCek.peek() ==  't' || stackCek.peek() == 'T'){
                        stackCek.pop();
                        currentState = 6;
                    }
                    else{
                        stmt = false;
                        end = true;
                    }
                    break;
                case 6 :
                    stmt = true;
                    end = true;
                    break;
            }
        }
        return stmt;
    }
    
    //Parsing perintah 'FROM', return boolean
    public boolean cekFrom(String kata){
        for(int i = 0; i < kata.length(); i++){
            temp.push(kata.charAt(i));
        }
        stackCek.push('#');
        while(!temp.isEmpty()){
            stackCek.push(temp.peek());
            temp.pop();
        }
        while(end == false){
            switch(currentState){
                case 0 : 
                    if(stackCek.peek() ==  'f' || stackCek.peek() == 'F'){
                        stackCek.pop();
                        currentState = 1;
                    }
                    else{
                        stmt = false;
                        end = true;
                    }
                    break; 
                case 1 : 
                    if(stackCek.peek() ==  'r' || stackCek.peek() == 'R'){
                        stackCek.pop();
                        currentState = 2;
                    }
                    else{
                        stmt = false;
                        end = true;
                    }
                    break;
                case 2 : 
                    if(stackCek.peek() ==  'o' || stackCek.peek() == 'O'){
                        stackCek.pop();
                        currentState = 3;
                    }
                    else{
                        stmt = false;
                        end = true;
                    }
                    break;
                case 3 : 
                    if(stackCek.peek() ==  'm' || stackCek.peek() == 'M'){
                        stackCek.pop();
                        currentState = 4;
                    }
                    else{
                        stmt = false;
                        end = true;
                    }
                    break;
                case 4 :
                    stmt = true;
                    end = true;
                    break;
            }
        }
        return stmt;
    }
}
