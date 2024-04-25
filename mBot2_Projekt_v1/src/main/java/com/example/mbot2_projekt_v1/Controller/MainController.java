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
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;
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
    private HashMap<String, String> ips = new HashMap<>();
    private List<String> s = new ArrayList<>();
    private int mbotId = 1;
    private int speed=100;


    @GetMapping("/mBot")
    public String ips(Model model) {
        if (ips.get(mBotIP) != null) {
            model.addAttribute("ipAdresses", mapToString());
            //System.out.println("ipAdresses: " + mapToString());
        }
        if (ips.get(mBotIP) != null){
            model.addAttribute("ipAdresse", mBotIP + " - " + ips.get(mBotIP));
            //System.out.println("ipAdresse: " + mBotIP + " - " + ips.get(mBotIP));
        }else {
            model.addAttribute("ipAdresse", mBotIP);
        }
        return "index";
    }

    @GetMapping("/mBotIP")
    public String sendCommand() {
        return "mBotIP";
    }

    @GetMapping("/getDevice")
    public String getDevice(@RequestParam("ipAdresse") String ipAdresseMbot){
        System.out.println("Ausgewähltes Gerät: " + ipAdresseMbot);
        if (!ips.containsKey(ipAdresseMbot)){
            ips.put(ipAdresseMbot, "mBot " + mbotId);
            mbotId++;
        }
        mBotIP = ipAdresseMbot;
        sendConnected();
        receiveData("SENSOR");
        return "redirect:/mBot#controller";
    }

    public void sendConnected(){
        try {
            //System.out.println(mBotIP);
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

    @PostMapping("/suicidePrevention")
    @ResponseBody
    public void sendSuicidePrevention(@RequestParam("prevention") String prevention){
        try {
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
    }


    //Button Befehle an mBots senden
    @PostMapping("/buttonControl")
    @ResponseBody
    public void buttonControl(@RequestParam("direction") String direction){
        try {
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
    }

    @PostMapping("/arrowControl")
    @ResponseBody
    public void arrowControl(HttpServletRequest request){
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
    }

    @PostMapping("/joystickControl")
    @ResponseBody
    public void joystickControl(HttpServletRequest request){
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
    }

    private List<String> mapToString(){
        for (Map.Entry<String, String> m : ips.entrySet()) {
            String key = m.getKey();
            String value = m.getValue();
            if (!s.contains(key + " - " + value)) {
                s.add(key + " - " + value);
            }
        }
        return s;
    }

    @PostMapping("/speedControl")
    @ResponseBody
    public void speedControl(HttpServletRequest request){
        try {
            int input = Integer.parseInt(request.getParameter("speed"));
            if (input - speed > 5 || input - speed < -5 ){
                speed = input;
                //System.out.println(speed);
                //Befeht in byte-Array konvertieren
                byte[] sendData = String.valueOf(speed).getBytes();

                try (DatagramSocket socket = new DatagramSocket()) {
                    //Button Befehl direkt an den mBot senden
                    DatagramPacket packet = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(mBotIP), 4000);

                    socket.send(packet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @PostMapping("/getSensordata")
    @ResponseBody
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
                //System.out.println("Data: " + data);

                String sensorDataJSON = new String(data, 0, packet.getLength(), StandardCharsets.UTF_8);

                //System.out.println("Empfangene Sensorwerte:\n" + sensorDataJSON);

                //Auslesen
                Gson gson = new Gson();

                // JSON-String in ein Objekt deserialisieren
                Sensordata sensordata = gson.fromJson(sensorDataJSON, Sensordata.class);
                System.out.println(sensordata.getMbotid() +"\t" + sensordata.getLine() +"\t" + sensordata.getBrightness() +"\t" + sensordata.getUltrasonic());

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        executor.shutdown();
    }
}