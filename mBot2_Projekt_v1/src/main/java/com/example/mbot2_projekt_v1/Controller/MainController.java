package com.example.mbot2_projekt_v1.Controller;

import com.example.mbot2_projekt_v1.classes.Sensordata;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class MainController {
    //IP Adresse des aktiven mBots
    private String mBotIP = "kein mBot ausgewählt";

    //Mainpage mBot Website
    @GetMapping("/homepage")
    public String mainpage(Model model) {
        return "homepage";
    }

    @GetMapping("/mBot")
    public String choosePC(Model model) {
        model.addAttribute("ipAdresse", mBotIP);
        return "index";
    }


    @GetMapping("/sendCommand")
    public String sendCommand() {
        return "LoginPage";
    }

    @GetMapping("/getDevice")
    public String getDevice(@RequestParam("ipAdresse") String ipAdresseMbot, Model model){
        System.out.println("Ausgewähltes Gerät: " + ipAdresseMbot);
        mBotIP = ipAdresseMbot;
        sendConnected();
        return "redirect:/mBot";
    }

    @PostMapping("/sendConnected")
    public void sendConnected(){
        try {
            System.out.println(mBotIP);
            String connected = "TRUE";
            //System.out.println(connected);
            //Befeht in byte-Array konvertieren
            byte[] sendData = connected.getBytes();

            try (DatagramSocket socket = new DatagramSocket()) {
                DatagramPacket packet = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(mBotIP), 4000);

                socket.send(packet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/suizidePrevention")
    public String sendSuizidePrevention(HttpServletRequest request){
        try {
            String prevention = request.getParameter("prevention");
            //System.out.println(prevention);
            //Befeht in byte-Array konvertieren
            byte[] sendData = prevention.getBytes();

            try (DatagramSocket socket = new DatagramSocket()) {
                DatagramPacket packet = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(mBotIP), 4000);

                socket.send(packet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/mBot";
    }


    //Button Befehle an mBots senden
    @PostMapping("/buttonControl")
    public String buttonControl(HttpServletRequest request){
        try {
            String direction = request.getParameter("direction");
            //System.out.println(direction);
            //Befeht in byte-Array konvertieren
            byte[] sendData = direction.getBytes();

            try (DatagramSocket socket = new DatagramSocket()) {
                //Button Befehl direkt an den mBot senden
                DatagramPacket packet = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(mBotIP), 4000);

                socket.send(packet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //zurück zur Mainpage navigieren
        return "redirect:/mBot";
    }

    @PostMapping("/arrowControl")
    public String arrowControl(HttpServletRequest request){
        try {
            String direction = request.getParameter("direction");

            byte[] sendData = direction.getBytes();

            try (DatagramSocket socket = new DatagramSocket()) {
                //Button Befehl direkt an den mBot senden
                DatagramPacket packet = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(mBotIP), 4000);

                socket.send(packet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //zurück zur Mainpage navigieren
        return "redirect:/mBot";
    }

    @PostMapping("/joystickControl")
    public String joystickControl(HttpServletRequest request){
        try {
           if(mBotIP!="kein mBot ausgewählt") {
               String xy = request.getParameter("direction");
               //Befeht in byte-Array konvertieren
               byte[] sendDataxy = xy.getBytes();


               try (DatagramSocket socket = new DatagramSocket()) {
                   //Button Befehl direkt an den mBot senden
                   DatagramPacket packet = new DatagramPacket(sendDataxy, sendDataxy.length, InetAddress.getByName(mBotIP), 4000);

                   socket.send(packet);
               }
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //zurück zur Mainpage navigieren
        return "redirect:/mBot";
    }


    @PostMapping("/getSensordata")
    public void receiveData(@RequestParam("command") String command) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            try (DatagramSocket socket = new DatagramSocket(1234)) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                // Sende Befehl zum Abrufen von Sensorwerten
                byte[] sendData = command.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(mBotIP), 4000);
                socket.send(sendPacket);

                // Empfange die Antwort vom mBot
                socket.receive(packet);
                byte[] data = packet.getData();
                System.out.println("Data: " + data);

                String sensorDataJSON = new String(data, 0, packet.getLength(), StandardCharsets.UTF_8);

                //System.out.println("Empfangene Sensorwerte:\n" + sensorDataJSON);

                //Auslesen
                Gson gson = new Gson();

                // JSON-String in ein Objekt deserialisieren
                Sensordata sensordata = gson.fromJson(sensorDataJSON, Sensordata.class);
                System.out.println(sensordata.getMbotid() +"\t" +sensordata.getLine());

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        //executor.shutdown();
    }
}