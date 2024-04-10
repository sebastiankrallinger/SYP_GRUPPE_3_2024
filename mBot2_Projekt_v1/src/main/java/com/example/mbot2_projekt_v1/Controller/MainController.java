package com.example.mbot2_projekt_v1.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;

@Controller
public class MainController {
    //IP Adresse des aktiven mBots
    private String mBotIP = "kein mBot ausgewählt";
    private HashMap<String, String> ips = new HashMap<>();
    private List<String> s = new ArrayList<>();
    private int mbotId = 1;

    //Mainpage mBot Website
    @GetMapping("/homepage")
    public String mainpage(Model model) {
        return "homepage";
    }

    @GetMapping("/mBot")
    public String ips(Model model) {
        if (ips.get(mBotIP) != null) {
            model.addAttribute("ipAdresses", mapToString());
        }
        if (ips.get(mBotIP) != null){
            model.addAttribute("ipAdresse", mBotIP + " - " + ips.get(mBotIP));
        }else {
            model.addAttribute("ipAdresse", mBotIP);
        }
        return "index";
    }

    @GetMapping("/sendCommand")
    public String sendCommand() {
        return "LoginPage";
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
        return "redirect:/mBot";
    }

    @PostMapping("/sendConnected")
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
}