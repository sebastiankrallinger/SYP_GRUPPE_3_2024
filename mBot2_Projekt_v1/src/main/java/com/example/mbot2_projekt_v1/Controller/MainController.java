package com.example.mbot2_projekt_v1.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class MainController {
    //IP Adresse des aktiven mBots
    private String mBotIP = "10.10.1.28";
    //Mainpage mBot Website
    @GetMapping("/mBot")
    public String mainpage(Model model) {
        return "index";
    }

    @GetMapping("/selectDevice")
    public String selectDevice(Model model) {
        List<String> devices = scanNetwork();
        model.addAttribute("devices", devices);
        return "selectDevice";
    }
    private List<String> scanNetwork() {
        List<String> devices = new ArrayList<>();
        try {
            Process process = Runtime.getRuntime().exec("arp -a");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null && (line = reader.readLine()) != "") {
                if(line != null){
                    line = line.trim();
                    System.out.println(line);
                    String[] array = line.split("\\.");

                    if(Objects.equals(array[0], "10") && Objects.equals(array[1], "10") && (Objects.equals(array[2], "1") || Objects.equals(array[2], "2") ||Objects.equals(array[2], "3")) ) {
                        String ip = array[0] +"." + array[1] +"." + array[2] +"." +(array[3].substring(0, 3)).trim();
                        String[] ip1 = ip.split("\\t");
                        ip = ip1[0];

                        devices.add(ip);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return devices;
    }
    @PostMapping("/device")
    public String sendCommand(@RequestParam("selectedDevice") String selectedDevice, Model model) {

        System.out.println("Ausgewähltes Gerät: " + selectedDevice);
        // Füge das ausgewählte Gerät dem Model hinzu, um es auf der hello.html anzuzeigen
        model.addAttribute("selectedDevice", selectedDevice);
        // Hier könntest du eine Rückmeldung an die Benutzeroberfläche senden.
        return "redirect:/mBot";
    }

    //Button Befehle an mBots senden
    @PostMapping("/buttonControl")
    public String buttonControl(HttpServletRequest request){
        try {
            String direction = request.getParameter("direction");
            System.out.println(direction);
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
            System.out.println(direction);
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


}
