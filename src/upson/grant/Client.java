package upson.grant;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
    private final String hostname;
    private final int port;

    public Client(String hostname, int port)
    {
        this.hostname = hostname;
        this.port = port;
    }

    public void connect()
    {
        try(Socket connection = new Socket(hostname, port))
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            BufferedWriter writer =  new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            Scanner clientReader = new Scanner(System.in);
            String command = "";

            while(true)
            {
                retrieveMessage(reader);
                command = clientReader.nextLine();
                writer.write(command + "\r\n");
                writer.flush();
                retrieveMessage(reader);
            }
        }
        catch(IOException ioException)
        {
            System.out.println("Error: " + ioException.getLocalizedMessage());
        }
        catch(NullPointerException npException)
        {
            System.out.println("Closing connection..");
        }
    }

    public void retrieveMessage(BufferedReader reader)
    {
        try
        {
            String[] serverMessage = reader.readLine().split("/");

            for(String message : serverMessage)
            {
                System.out.println(message);
            }
        }
        catch(IOException ioException)
        {
            System.out.println("Error: " + ioException.getMessage());
        }
    }

    public static void main(String[] args)
    {
        if(args.length != 2)
        {
            System.out.println("Error: Invalid number of parameters. Usage: java -jar Client.jar <hostname> <port>");
        }
        else
        {
            Client client = new Client(args[0], Integer.parseInt(args[1]));
            client.connect();
        }
    }
}
