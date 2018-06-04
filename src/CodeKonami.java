/**
 * Created by Alphonse on 04/06/2018.
 */


public class CodeKonami {

    private static int[] history = new int[10];
    private static int front = 0;
    private static int size = 0;

    // Here is the Code they must enter (ascii vals for konami).
    private static int[] code = {38, 38, 40, 40, 37, 39, 37, 39, 66, 65};

    // Static class. No constructor.
    private CodeKonami(){}

    // Adds key-press into history buffer. If code is matched, return true.
    public static boolean add(int e){

        // Write the value into our key history.
        history[(front + size) % 10] = e;

        // Stop growing at length 10 and overwrite the oldest value instead.
        if (size < 10){
            size++;
        } else {
            front = front + 1 % 10;
        }

        // Compare our history (from the current front) to the code (from 0)
        for(int i = front; i < front + size; i++){
            if (history[i % 10] != code[i-front]){
                // Any 1 mismatch will abort
                return false;
            }
        }
        // Make sure we've logged enough keystrokes so it doesn't fire off
        // if your first key press matches the code.
        if (size < 10){
            return false;
        }
        return true;
    }
}