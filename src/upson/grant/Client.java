package upson.grant;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
    private String hostname;
    private int port;

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

            while(!command.equalsIgnoreCase("exit"))
            {
                String[] serverMessage = reader.readLine().split("/");

                for(String message : serverMessage)
                {
                    System.out.println(message);
                }

                command = clientReader.nextLine();
                writer.write(command + "\r\n");
                writer.flush();
            }

            reader.close();
            writer.close();
        }
        catch(IOException ioException)
        {
            System.out.println("Error: " + ioException.getLocalizedMessage());
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
