import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ReadMapData {



        private static byte[] allBytes; // why if I move it into "readFileContentToArray()" it doesn't work?

        private static String[] wordsArray = new String[0]; // Initialisierung ist nötig, um wordsArray.add() aufrufen zu können. Wenn Anzahl der elemente ist zero, array wird überschrieben.

        // read the file "dict.txt" to an array of String with unique words, all in lowercase.
        private static String[] readFileContentToArray(){

//        -- Test. Working...
//        Path currentDir = Paths.get("dict.txt");
//        System.out.println(currentDir.toAbsolutePath());
//        --



            Path inputFile     = Paths.get(".\\map_data.txt");

            // display the dictionary file
            System.out.println("Reading dictionary :"+inputFile.toAbsolutePath().toString());

            try {
                long start = System.currentTimeMillis();

                allBytes = Files.readAllBytes(inputFile);

                long end = System.currentTimeMillis();
                System.out.println("File read in " + (end - start) + " ms");

            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Error on reading from file.");
            }

            // convert bytes[] to string :
            String s = new String(allBytes, StandardCharsets.UTF_8);
            System.out.println("\n\rContent of file : \n\r" + s);

            String[] arrWords = createWordsArray(s);

            return arrWords;
        }

        // initiates the static Array arrWords. only used in the readFileContentToArray() method.
        private  static String[] createWordsArray(String s){
            ArrayList<String> temp_arr = new ArrayList<>();

            //do a loop through the string "s" to get the words
            String currentWord="";
            for (int i = 0; i <= s.length()-1; i++) {
                char c=s.charAt(i);

                if ((c>=65&&c<=90)||(c>=97&&c<=122)){  // nur Buchstaben A-Z und a-z werden berücksichtigt.
                    // we found a usable character. add it to the current word.

                    currentWord= currentWord.concat(Character.toString(c));
                } else {
                    //we found an unusable character, close the current word and reinit.
                    if (!currentWord.isEmpty()) { // to check if a word exist or not. if not, it could be a second unusable character ... ( avoid creating empty words)

                        // checking if the word already exists, if not add it to "temp_arr"
                        if (!temp_arr.contains(currentWord.toLowerCase())) temp_arr.add(currentWord.toLowerCase());
                        currentWord = "";
                    }
                }
            }
            // return the array of words - conversion is ugly ...
            String[] array2Send = new String[temp_arr.size()];
            array2Send = temp_arr.toArray(array2Send);
            return array2Send;
        }

        // gibt ein random wort zurück :
        // verwendung:
        //      getWord(char minCharacter, char maxCharacter) - gibt ein zufälliges wort zurück mit einen Anzahl an Buchstaben zwischen  minCharacter und  maxCharacter
        //      getWord(char minCharacter) - gibt ein  zufälliges wort zurück mit einen minimalen  Anzahl an Buchstaben von minCharacter
        //      getWord() - gibt ein  zufälliges wort zurück
        //          solte kein Treffer vorhanden sein, wird ein empty String zurückgegeben.

        private  static String getWord(int minCharNumber, int maxCharNumber){
            // wenn  wordsArray noch keine Elemente hat, muss es erst initialisiet werden
            if(wordsArray.length==0)   wordsArray=readFileContentToArray();
            ArrayList<String> filterWordsArray= new ArrayList<String>();
            for (String word: wordsArray){
                if (word.length()>=minCharNumber && word.length()<=maxCharNumber) filterWordsArray.add(word);
            }
            int randomWordIndex = (int) Math.floor(Math.random() * filterWordsArray.size() );
            return ((filterWordsArray.size() > 0) ?  filterWordsArray.get(randomWordIndex) : "");

        }
        private  static String getWord(int minCharNumber){
            return  getWord(minCharNumber,40);
        }
        private  static String getWord(){
            return getWord(1,40);
        }


        //  ------------    MAIN    ----------------
        public static void main(String[] args) {

            wordsArray =  readFileContentToArray();
            System.out.println("\n\rUsable words:");
            for (String x: wordsArray) {
                System.out.print(x+" ");
            }
            System.out.println("\r\nTotal usable words:"+wordsArray.length);
            System.out.print("\r\nRandom words:");
            for (int i = 0; i<50;i++)
                System.out.print(getWord(4,10)+" ");
        }


}
