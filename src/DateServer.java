import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class DateServer {

    private static final int PORT = 9090;

    public static void main(String[] args) throws IOException {
        //socket DateServer object
        ServerSocket listener = new ServerSocket(PORT);
        //allows the listener socket DateServer make a connection; returns a socket object
        System.out.println("[SERVER] is waiting for client connection...");
        Socket client = listener.accept();
        System.out.println("[SERVER] is connected to client");

        boolean loop = true;
        while(loop == true) {
            BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println("Preparing to read command.");
            String command = input.readLine();
            System.out.println("COMMAND SENT FROM SERVER IS: " + command);

            PrintWriter out = new PrintWriter(client.getOutputStream(), true);

            switch (command) {
                case "0":
                    System.out.println("TERMINAL EXIT");
                    client.close();
                    listener.close();
                    loop = false;
                    break;
                case "1":
                    String date = ((new Date()).toString());
                    System.out.println("[SERVER] Sending date " + date);
                    out.println(date);
                    System.out.println("[SERVER] Sending END");
                    out.println(" END ");
                    break;
                case "2":
                    Process uptimeProcess = Runtime.getRuntime().exec("uptime");
                    BufferedReader utreader = new BufferedReader(new InputStreamReader(uptimeProcess.getInputStream()));
                    String uptime;
                    while ((uptime = utreader.readLine()) != null){
                        System.out.println("[SERVER] Sending how long the server has been running since last boot-up: " + uptime);
                        out.println(uptime);
                    }
                    System.out.println("[SERVER] Sending END");
                    out.println(" END ");
                    break;
                case "3":
                    long total = Runtime.getRuntime().totalMemory();
                    long used  = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                    System.out.println("[SERVER] Sending TOTAL MEMORY: " + total);
                    System.out.println("[SERVER] Sending TOTAL MEMORY USED: " + used);
                    out.println(used + " used out of: " + total + " total");
                    System.out.println("[SERVER] Sending END");
                    out.println(" END ");
                    break;
                case "4":
                    Process netProcess = Runtime.getRuntime().exec("netstat");
                    BufferedReader uReader = new BufferedReader(new InputStreamReader(netProcess.getInputStream()));
                    String netstat;
                    System.out.println("[SERVER] Sending lists of network connections on the server: ");
                    while ((netstat = uReader.readLine()) != null){
                        System.out.println(netstat);
                        out.println(netstat);
                    }
                    System.out.println("[SERVER] Sending END");
                    out.println(" END ");
                    break;
                case "5":
                    Process userProcess = Runtime.getRuntime().exec("who");
                    BufferedReader userReader = new BufferedReader(new InputStreamReader(userProcess.getInputStream()));
                    String numUser;
                    System.out.println("[SERVER] Sending list of users currently connected to the server: ");
                    while ((numUser = userReader.readLine()) != null){
                        System.out.println(numUser);
                        out.println(numUser);
                    }
                    System.out.println("[SERVER] Sending END");
                    out.println(" END ");
                    break;
                case "6":
                    Process runProcess = Runtime.getRuntime().exec("ps -ef");
                    BufferedReader runReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                    String runTime;
                    while((runTime = runReader.readLine()) != null){
                        System.out.println("[SERVER] Sending list of programs currently running on the server: " + runTime);
                        out.println(runTime);
                    }
                    System.out.println("[SERVER] Sending END");
                    out.println(" END ");
                    break;
            }//end switch
        }//end while loop
    }//end main
}//end class
