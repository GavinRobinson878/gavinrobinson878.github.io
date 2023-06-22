import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
public class DictionaryCheck {

    public Map<Character, ArrayList<String>> dict;
    public Map<Integer, ArrayList<String>> dict_num;
    public Scanner myScanner = new Scanner(System.in);

    public DictionaryCheck(){
        //defining dict by while loop
        this.dict = new HashMap<>();
        char char1 = 'a';
        while (char1 < '{'){

            this.dict.put(char1, new ArrayList<String>());
            char1++;

        }

        //defining dict_num with for loop
        this.dict_num = new HashMap<>();
        int largestWord = 31;
        for (int i = 1; i <= largestWord; i++){
            this.dict_num.put(i, new ArrayList<String>());
        }


    }



    //Read in a file from computer to input to dict each word in english dictionary
    //the first character of the word being the key, then putting the string into the value ArrayList<String>
    public void fileReadIn(){
        System.out.println("Begin of fileReadIn");
        try{
            File obj = new File("C:\\Users\\Tami\\IdeaProjects\\DictionaryCheck\\words_alpha.txt");
            Scanner myReader = new Scanner(obj);
            while(myReader.hasNextLine()){
                String data = myReader.nextLine();
                this.dict.get(data.charAt(0)).add(data);
                this.dict_num.get(data.length()).add(data);
            }
            myReader.close();
        }catch(Exception e){
            System.out.println("Error");
        }

        System.out.println("fileReadIn complete");

    }

    public void beginInput(){

        //data structures
        //Scanner myScanner = new Scanner(System.in);
        ArrayList<String> strList = new ArrayList<>();
        ArrayList<String> correctionList = new ArrayList<>();
        String strInput = new String();
        String userChoice = new String();
        String decisionChoice = new String();

        //beginning of user input while loop
        //maybe make this into a different method depending on user input
        System.out.println("Beginning of decision choice:");
        System.out.println("How would you like to evaluate input?");
        System.out.println("\n1.)Terminal Input");
        System.out.println("2.)Input from .txt file");
        while(myScanner.hasNext()){
            decisionChoice = myScanner.next();
            if(decisionChoice.equalsIgnoreCase("1")) {
                this.terminalInput(strList, strInput);
                break;
            }
            else if(decisionChoice.equalsIgnoreCase("2")){
                //call this.txtFileInput(strList, strInput)
                this.txtFileInput(strList, strInput);
                break;
            }
            else{
                System.out.println("User input is incorrect, please try again.\n");
                System.out.println("How would you like to evaluate input?");
                System.out.println("\n1.)Terminal Input");
                System.out.println("2.)Input from .txt file");
            }
        }

        ArrayList<Integer> fallacyList_elementNum = this.evaluation_1(strList);
        ArrayList<String> fallacyList_String = new ArrayList<>();
        for(int i: fallacyList_elementNum){
            fallacyList_String.add(strList.get(i));
        }

        //beginning of user choice to evaluate or continue
        System.out.println("Beginning of user choice:");
        System.out.println("Would you like to evaluate potential fallacies?");
        System.out.println("\n1.)find possible solutions to potential fallacies");
        System.out.println("2.)Ignore and continue");

        while(myScanner.hasNext()){
            userChoice = myScanner.next();
            if(userChoice.equalsIgnoreCase("1")){
                System.out.println("choice 1 accepted");
                //method call to evaluation_2 which returns a list of corrections or not
                correctionList = this.evaluation_2(fallacyList_String);
                //myScanner.close();
                //call to this.continueInput_1(correctionList, strList);
                this.continueInput_1(correctionList, strList, fallacyList_elementNum);
                break;
            }
            else if(userChoice.equalsIgnoreCase("2")){
                System.out.println("choice 2 accepted");
                //call to this.continueInput_2();
                break;
            }
            else{
                System.out.println("Invalid input, please enter a 1 or 2 to determine your choice");
            }

            System.out.println("\nWould you like to evaluate potential fallacies?");
            System.out.println("\n1.)find possible solutions to potential fallacies");
            System.out.println("2.)Ignore and continue");
        }

    }

    public void terminalInput(ArrayList<String> strList, String strInput){
        System.out.println("input exitcode(910) to stop input and evaluate");
        System.out.println("Start user input below:");
        System.out.println(" ");
        while(myScanner.hasNext()){
            strInput = myScanner.next();
            if(strInput.equalsIgnoreCase("exitcode(910)")){
                break;
            }
            strList.add(strInput);

        }
    }

    public void txtFileInput(ArrayList<String> strList, String strInput){
        try {
            System.out.println("Beginning of txtFileInput");
            System.out.println("Please input the file directory for the file you would like to evaluate below: ");
            String fileName = myScanner.next();
            File inputFile = new File(fileName);

            Scanner fileInput = new Scanner(inputFile);
            while(fileInput.hasNext()){
                //System.out.print(fileInput.next() + " ");
                strList.add(fileInput.next());
            }
            fileInput.close();
        }catch(Exception e){
            System.out.println("An error occurred in txtFileInput");
            System.out.println("Could not recognize name or directory, please try again");
        }
        System.out.println("End of txtFileInput");
    }




    //Method responsible for evaluating string input
    //returns an ArrayList of integers of potentially incorrect positions
    public ArrayList<Integer> evaluation_1(ArrayList<String> strList){

        //print to designate beginning of evaluation
        System.out.println("\nBeginning of input evaluation_1\n");
        ArrayList<Integer> rtrnList = new ArrayList<>();

        //for loop to traverse strList for evaluation
        for(int i = 0; i < strList.size(); i++){
            String str = strList.get(i).toLowerCase();
            if(!this.dict.get(str.charAt(0)).contains(str)){
                System.out.println("word " + (i + 1) + " '" + str + "' has been determined incorrect");
                rtrnList.add(i);
            }
        }
        System.out.println("\nEnd of input evaluation_1");
        return rtrnList;
    }

    //inputs ArrayList of fallacies in order to try to find a potential correction
    //using dict_num
    public ArrayList<String> evaluation_2(ArrayList<String> fallacyList){
        System.out.println("Beginning of evaluation_2");
        ArrayList<String> rtrnList = new ArrayList<>();
        ArrayList<String> list_1;
        ArrayList<String> list_2;
        ArrayList<String> list_3;
        ArrayList<String> list_4;
        ArrayList<String> list_5;
        ArrayList<String> list_6;
        ArrayList<String> list_7;

        for(String str_eval: fallacyList){
            //Getting 6 lists from dict_num to compare to fallacyList element
            //declaring an array to store 7 of the most similar words
            //depending on the number of characters that they have in common
            int size = str_eval.length();
            //Where the first column refers to string input and second column the ranking
            //will have to use Integer.parseInt() to convert from string to integer and String.valueOf()
            //to turn back to int
            String[][] listArray = {{"","","","","",""},{"0","0","0","0","0","0"}};
            if(size <= 4){ //if size under 4 we can go ahead and just assign them manually
                list_1 = this.dict_num.get(2);
                list_2 = this.dict_num.get(3);
                list_3 = this.dict_num.get(4);
                list_4 = this.dict_num.get(5);
                list_5 = this.dict_num.get(6);
                list_6 = this.dict_num.get(7);
                list_7 = this.dict_num.get(8);
            }
            else if(size > 31){
                System.out.println("input '" + str_eval + "' contains too many characters");
                continue;
            }
            else if(size > 29){
                list_1 = this.dict_num.get(25);
                list_2 = this.dict_num.get(26);
                list_3 = this.dict_num.get(27);
                list_4 = this.dict_num.get(28);
                list_5 = this.dict_num.get(29);
                list_6 = this.dict_num.get(30);
                list_7 = this.dict_num.get(31);
            }
            else if(size >= 25){
                list_1 = this.dict_num.get(22);
                list_2 = this.dict_num.get(23);
                list_3 = this.dict_num.get(24);
                list_4 = this.dict_num.get(25);
                list_5 = this.dict_num.get(26);
                list_6 = this.dict_num.get(27);
                list_7 = this.dict_num.get(28);
            }
            else{
                list_1 = this.dict_num.get(size - 3);
                list_2 = this.dict_num.get(size - 2);
                list_3 = this.dict_num.get(size - 1);
                list_4 = this.dict_num.get(size);
                list_5 = this.dict_num.get(size + 1);
                list_6 = this.dict_num.get(size + 2);
                list_7 = this.dict_num.get(size + 3);
            }

            ArrayList<String> bigList = new ArrayList<>();
            bigList.addAll(list_1);
            bigList.addAll(list_2);
            bigList.addAll(list_3);
            bigList.addAll(list_4);
            bigList.addAll(list_5);
            bigList.addAll(list_6);
            bigList.addAll(list_7);

            //Method call to evaluation_3
            this.evaluation_3(bigList, listArray, str_eval);

            //Scanner myScanner = new Scanner(System.in);
            for(int i = 5; i >= 0; i--) {
                System.out.println("Did you mean '" + listArray[0][i] + "'" + " iteration " + i);
                System.out.println("\nInput 1 for yes and 2 for no: ");
                String userChoice = myScanner.next();

                if(userChoice.equalsIgnoreCase("1")){
                    System.out.println("Identified correct word");
                    rtrnList.add(listArray[0][i]);
                    break;
                } else if(userChoice.equalsIgnoreCase("2")){
                    System.out.println("No match, continuing");
                } else{
                    System.out.println("Incorrect user answer, please try again\n");
                    i++;
                }

                if(i == 0){
                    rtrnList.add(str_eval);
                }
            }

        }

        return rtrnList;
    }

    public void evaluation_3(ArrayList<String> bigList, String[][] listArray, String str_eval){
        System.out.println("Beginning of evaluation_3");
        for(int i = 0; i < bigList.size(); i++){
            int numInCommon = 0;
            String compare = bigList.get(i); //determining numInCommon first
            if(compare.length() >= str_eval.length()){
                int iter = 0;
                for(char letter: str_eval.toCharArray()){
                    if(letter == compare.charAt(iter)){
                        numInCommon++;
                    }
                    iter++;
                }
            }
            else{
                int iter = 0;
                for(char letter: compare.toCharArray()){
                    if(letter == str_eval.charAt(iter)){
                        numInCommon++;
                    }
                    iter++;
                }
            }
            //now going to call Array_sort in passing in numInCommon, compare, listArray[][]
            this.Array_sort(numInCommon, compare, listArray);

        }

    }

    public void Array_sort(int numInCommon, String compare, String[][] listArray){
        //checking to determine if numInCommon is large enough to enter the recommendation array (listArray)
        //if so, will then commence a for loop to determine where to place compare and numInCommon inside of
        //the array, with listArray[1][5] having the most characters in common
        if(numInCommon > Integer.parseInt(listArray[1][0])){
            for(int i = 5; i >= 0; i--){
                if(Integer.parseInt(listArray[1][i]) < numInCommon){
                    listArray[1][(i)] = String.valueOf(numInCommon);
                    listArray[0][(i)] = compare;
                    break;
                }
            }
        }
    }

    //user chose to find potential fallacies and using previous methods we may have
    //these potential corrections are located in correctionList and the index num
    //correlating to strList is held in fallacyList_elementNum
    //Method will ask the user if he wants to save to a file or not
    public void continueInput_1(ArrayList<String> correctionList, ArrayList<String> strList, ArrayList<Integer> fallacyList_elementNum){
        System.out.println("\nWould you like to save your changes?");
        System.out.println("1.)Save corrections");
        System.out.println("2.)Discard corrections");
        String userInput = new String();

        while(myScanner.hasNext()){
            userInput = myScanner.next();
            if(userInput.equalsIgnoreCase("1")){
                System.out.println("Saving changes...");
                this.correctionSave(correctionList, strList, fallacyList_elementNum);
                break;
            }
            else if(userInput.equalsIgnoreCase("2")){
                System.out.println("\nAre you sure you would like to discard corrections");
                System.out.println("1.)Yes I am sure I want to discard corrections");
                System.out.println("2.)No I am not sure that I want to discard corrections");
                userInput = myScanner.next();
                if(userInput.equalsIgnoreCase("1")){
                    System.out.println("Discarding corrections");
                    break;
                }
                else if(userInput.equalsIgnoreCase("2")){
                    System.out.println("Continuing\n");
                    continue;
                }
                else{
                    System.out.println("Invalid input, please try again");
                }
            }
            else{
                System.out.println("Invalid input, please try again");
            }
            System.out.println("\nWould you like to save your changes?");
            System.out.println("1.)Save corrections");
            System.out.println("2.)Discard corrections");
        }
    }

    //gets called from this.beginInput()
    //FIXME
    public void continueInput_2(){
        System.out.println("Would you like to save your input?");
        System.out.println("1.)Yes, save my input");
        System.out.println("2.)No, do not save my input");
    }

    //Inputting new correctionList elements into strList using fallacyList_elementNum as the element number
    public void correctionSave(ArrayList<String> correctionList, ArrayList<String> strList, ArrayList<Integer> fallacyList_elementNum){
        int fallacyInt = 0;
        String fileName = new String();
        for(String correction: correctionList){
            strList.set(fallacyList_elementNum.get(fallacyInt),correction);
            fallacyInt++;
        }
        System.out.println("\nNew save outputting below: ");
        for(String newSave: strList){
            System.out.print(newSave + " ");
        }

        //user input for name of file or directory (fileName)
        System.out.println("\n\nPlease input the file name or directory you wish to save to below:");
        fileName = myScanner.next();


        System.out.println("Continuing to fileSave");
        //call to this.fileSave(String fileName, ArrayList<String> strList);
        //might need to add a new parameter to this method called fileName or directory
        this.fileSave(fileName, strList);
    }

    //fileName can also be a file's path?
    public void fileSave(String fileName, ArrayList<String> strList){
        //creating a new file to write to
        try{
            File myObj = new File(fileName);
            if(myObj.createNewFile()){
                System.out.println("File created: " + myObj.getName());
            }else{
                System.out.println("File already exists");
            }
        } catch(IOException e){
            System.out.println("An error occurred");
            e.printStackTrace();
        }

        //writing to new file that was just created
        try{
            FileWriter myWriter = new FileWriter(fileName);
            for(String str: strList){
                myWriter.write(str + " ");
            }
            myWriter.close();
            System.out.println("Successfully wrote to file");
        } catch (IOException e){
            System.out.println("An error occurred");
            e.printStackTrace();
        }

    }




    public static void main(String args[]){
        System.out.println("Running main method");
        DictionaryCheck diction = new DictionaryCheck();
        diction.fileReadIn();
        diction.beginInput();



    }
}
