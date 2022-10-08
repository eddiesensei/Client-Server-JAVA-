import java.io.*;
import java.net.*;
import java.text.DecimalFormat;
import java.util.*;

public class Client {

    public static void main(String[] args) throws UnknownHostException, IOException {

        //Request server IP and port from client
        Scanner input = new Scanner(System.in);

        System.out.println("Please enter the Server IP: ");
        String IP = input.nextLine();

        System.out.println("Please enter the Server Port: ");
        int port = input.nextInt();

        //Use given information to establish connect with the server
        Socket server = new Socket(IP, port); //Establish connection
        System.out.println("Server connection established.");

        PrintWriter toServer = new PrintWriter(server.getOutputStream(), true); //Sends info to the server

        BufferedReader fromServer = new BufferedReader(new InputStreamReader(server.getInputStream())); //Receives info from the server

        //Present client with Menu

        int choice = -1;

        while (choice != 0) {

            System.out.println("\n~ ~ ~ ~ Menu ~ ~ ~ ~" +
                    "\n1: Date and Time - the date and time on the server" +
                    "\n2: Uptime - how long the server has been running since last boot-up" +
                    "\n3: Memory Use - the current memory usage on the server" +
                    "\n4: Netstat - lists network connections on the server" +
                    "\n5: Current Users - list of users currently connected to the server" +
                    "\n6: Running Processes - list of programs currently running on the server" +
                    "\n0: End Program");

            System.out.println("Please enter the integer corresponding to your choice: ");
            try {
                int  in = input.nextInt();
                if (in > 6 || in < 0) {
                    System.out.println("Please enter an integer 0-6.");
                }
                else {
                    choice = in;
                }

            }
            catch (Exception e){
                System.out.println("Please enter an integer 0-6.");
                System.out.println(e);
            }

            if (choice == 0) {
                break;
            }

            int requests = 0;
            int[] options = {1, 5, 10, 15, 20, 25};
            System.out.println("Please enter how many client requests you would like to generate."
                    + "\nYou may enter 1, 5, 10, 15, 20, or 25: ");
            int  in = input.nextInt();
            requests = in;

            ArrayList<Long> Time = new ArrayList<Long>();

            DecimalFormat decForm = new DecimalFormat();
            decForm.setMaximumFractionDigits(2);
            decForm.setMinimumFractionDigits(2);

            for (int i = 0; i < requests; i++) {
                Multithread myThing = new Multithread();
                myThing.start();

                long start = System.nanoTime();
                myThing.run(server, toServer, choice);

                System.out.println("Preparing to read response.");

                int stop = 0;

                System.out.println("Server response is as follows: \n");
                while (stop != 1) {
                    String answer = fromServer.readLine();

                    if(answer.contains("END")) {
                        stop = 1;
                    }
                    else {
                        System.out.println(answer);
                    }
                }    //end reading while

                long TatTime = (System.nanoTime() - start);
                System.out.println("\nThread Turn-Around-Time: " + decForm.format( (double) TatTime / 1000000.0) + " milliseconds.");
                Time.add(TatTime);

            }   //end multithread for loop

            long totalTime = 0;
            for (int i = 0; i < Time.size(); i++) {
                totalTime = totalTime + Time.get(i);
            }

            long AvgTime = (totalTime/Time.size());

            System.out.println("\nTotal Turn-Around-Time: " + decForm.format((double)totalTime/1000000.0) + " milliseconds.");

            System.out.println("\nAverage Turn-Around-Time: " + decForm.format((double)AvgTime/1000000.0) + " milliseconds.");

        } //end program while

        toServer.println(choice);
        input.close();
        server.close();
        System.out.println("Program terminated.");

    }

}