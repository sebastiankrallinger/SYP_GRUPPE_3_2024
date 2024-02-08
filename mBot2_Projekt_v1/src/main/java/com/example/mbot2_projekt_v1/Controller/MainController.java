package com.example.mbot2_projekt_v1.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Controller
public class MainController {
    //IP Adresse des aktiven mBots
    private String mBotIP = "10.10.1.28";
    //Mainpage mBot Website
    @GetMapping("/mBot")
    public String mainpage(Model model) {
        return "index";
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
