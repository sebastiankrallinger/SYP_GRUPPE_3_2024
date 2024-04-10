package com.example.mbot2_projekt_v1.Controller;

import org.springframework.web.bind.annotation.RestController;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class SensordataReceiver implements Runnable {

    private volatile boolean running;
    String mBotIP;
    public SensordataReceiver(String mBotIP){
        this.mBotIP = mBotIP;
    }

    @Override
    public void run() {
        //IP SENDEN
        running = true;
        try {
            if(mBotIP != "kein mBot ausgewählt") {
                // Ermittle die lokale IP-Adresse des Systems
                InetAddress localhost = InetAddress.getLocalHost();
                String ip = localhost.getHostAddress();

                System.out.println("Server IP-Adresse ist: " + ip);

                byte[] sendDataxy = ip.getBytes();

                try (DatagramSocket socket = new DatagramSocket()) {
                    //Button Befehl direkt an den mBot senden
                    DatagramPacket packet = new DatagramPacket(sendDataxy, sendDataxy.length, InetAddress.getByName(mBotIP), 4000);
                    socket.send(packet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //SENSORDATEN EMPFANGEN
        try {
            System.out.println("DATEN EMPFANGEN");
            int port = 4000;
            DatagramSocket socket = new DatagramSocket(port);

            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            //Daten bekommen
            String receivedData = new String(packet.getData(), 0, packet.getLength());
            processSensorData(receivedData);

            socket.close();
            //SOCKET CLOSE
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Methode zur Verarbeitung der empfangenen Sensor-Daten
    public void processSensorData(String jsonData) {
        System.out.println("Empfangene Sensor-Daten:");
        System.out.println(jsonData);
    }

    // Methode zum Stoppen des Threads
    public void stop() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}
