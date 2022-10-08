import java.io.*;
import java.net.*;
import java.util.*;

public class Multithread extends Thread{

    public void run(Socket server, PrintWriter toServer, int choice){
        System.out.println("Sending out: " + choice);
        toServer.println(choice); //Write choice to server
    }
}