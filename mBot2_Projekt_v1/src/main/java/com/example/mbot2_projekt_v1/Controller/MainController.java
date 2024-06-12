package com.example.mbot2_projekt_v1.Controller;

import com.example.mbot2_projekt_v1.classes.Sensordata;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.net.*;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class MainController{
    //IP Adresse des aktiven mBots
    private String mBotIP = "kein mBot ausgewählt";
    //IP Adressen aller verbundenen mBots
    private HashMap<String, String> ips = new HashMap<>();
    //IP Adressen zum anzeigen auf der Webseite
    private List<String> s = new ArrayList<>();
    //Id fuer mBot festlegen
    private int mbotId = 1;
    //Geschwindigkeit des mBots
    private int speed = 100;
    //aktuellen Sensordaten
    private Sensordata sensordata;
    //Variable fuer Sensorthread
    private boolean start = false;
    //Nachrichten an Websocket Clients
    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    //mBot Id und IP Adressen anzeigen
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

    //mBot IP Adresse zurueckgeben
    @GetMapping("/mBotIP")
    public String sendCommand() {
        return "mBotIP";
    }

    //eingegebene mBot IP Adresse verarbeiten
    @GetMapping("/getDevice")
    public String getDevice(@RequestParam("ipAdresse") String ipAdresseMbot){
        System.out.println("Ausgewähltes Gerät: " + ipAdresseMbot);
        if (!ips.containsKey(ipAdresseMbot)){
            ips.put(ipAdresseMbot, "mBot " + mbotId);
            mbotId++;
        }
        mBotIP = ipAdresseMbot;
        sendConnected();
        receiveData();
        return "redirect:/mBot#controller";
    }

    //Signal an mBot senden
    public void sendConnected(){
        try {
            //System.out.println(mBotIP);
            String connected = "TRUE";
            //System.out.println(connected);
            byte[] sendData = connected.getBytes();

            try (DatagramSocket socket = new DatagramSocket()) {
                DatagramPacket packet = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(mBotIP), 4000);
                socket.send(packet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Suicide Prevention aktivieren/deaktivieren
    @PostMapping("/suicidePrevention")
    @ResponseBody
    public void sendSuicidePrevention(@RequestParam("prevention") String prevention){
        try {
            if(mBotIP!="kein mBot ausgewählt") {
                //System.out.println(prevention);
                byte[] sendData = prevention.getBytes();

                try (DatagramSocket socket = new DatagramSocket()) {
                    DatagramPacket packet = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(mBotIP), 4000);
                    socket.send(packet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Button Steuerung
    @PostMapping("/buttonControl")
    @ResponseBody
    public void buttonControl(@RequestParam("direction") String direction){
        try {
            if(mBotIP!="kein mBot ausgewählt") {
                //System.out.println(direction);
                byte[] sendData = direction.getBytes();
                try (DatagramSocket socket = new DatagramSocket()) {
                    DatagramPacket packet = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(mBotIP), 4000);
                    socket.send(packet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Pfeiltasten Steuerung
    @PostMapping("/arrowControl")
    @ResponseBody
    public void arrowControl(HttpServletRequest request){
        try {
            if(mBotIP!="kein mBot ausgewählt") {
                String direction = request.getParameter("direction");
                byte[] sendData = direction.getBytes();
                try (DatagramSocket socket = new DatagramSocket()) {
                    DatagramPacket packet = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(mBotIP), 4000);
                    socket.send(packet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Joystick Steuerung
    @PostMapping("/joystickControl")
    @ResponseBody
    public void joystickControl(HttpServletRequest request){
        try {
           if(mBotIP!="kein mBot ausgewählt") {
               String xy = request.getParameter("direction");
               byte[] sendDataxy = xy.getBytes();
               try (DatagramSocket socket = new DatagramSocket()) {
                   DatagramPacket packet = new DatagramPacket(sendDataxy, sendDataxy.length, InetAddress.getByName(mBotIP), 4000);
                   socket.send(packet);
               }
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //HashMap Werte in eine Liste konvertieren
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

    //Geschwindigkeit verwalten
    @PostMapping("/speedControl")
    @ResponseBody
    public void speedControl(HttpServletRequest request){
        try {
            if(mBotIP!="kein mBot ausgewählt") {
                int input = Integer.parseInt(request.getParameter("speed"));
                if (input - speed > 5 || input - speed < -5) {
                    speed = input;
                    //System.out.println(speed);
                    //Befeht in byte-Array konvertieren
                    byte[] sendData = String.valueOf(speed).getBytes();

                    try (DatagramSocket socket = new DatagramSocket()) {
                        DatagramPacket packet = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(mBotIP), 4000);
                        socket.send(packet);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Farebn des mBot verwalten
    @PostMapping("/sendColor")
    @ResponseBody
    public void sendColor(HttpServletRequest request/*, @RequestParam("color") String colorHEX>*/){
        try {
            if (mBotIP != "kein mBot ausgewählt") {
                String colorHEX = request.getParameter("color");
                byte[] sendData = String.valueOf(colorHEX).getBytes();
                try (DatagramSocket socket = new DatagramSocket()) {
                    DatagramPacket packet = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(mBotIP), 4000);
                    socket.send(packet);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Sensordaten empfangen
    public Sensordata receiveData() {
        sendServerIP();
        try (DatagramSocket socket = new DatagramSocket()) {
            String send = "SENSOR";
            // Sende Befehl zum Abrufen von Sensorwerten
            byte[] sendData = send.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(mBotIP), 4000);
            socket.send(sendPacket);
            Thread thread = new Thread(new SensorThread());
            thread.start();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return sensordata;
    }

    //IP Adresse des Servers an mBot senden
    public void sendServerIP() {
        try (DatagramSocket socket = new DatagramSocket()) {
            String serverip = "";
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (!address.isLoopbackAddress() && address instanceof Inet4Address) {
                        if (address.getHostAddress().contains("10.10.")) {
                            serverip = address.getHostAddress();
                            //System.out.println("Local IP Address: " + address.getHostAddress());
                        }
                    }
                }
            }
            byte[] sendAddress = serverip.getBytes();
            DatagramPacket sendIp = new DatagramPacket(sendAddress, sendAddress.length, InetAddress.getByName(mBotIP), 4000);
            socket.send(sendIp);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Sensordaten an Websocket senden
    @MessageMapping("/sensorData")
    public Sensordata sendSensorData() {
        if (sensordata != null) {
            //log.info("Data: " + sensordata);
            messagingTemplate.convertAndSend("/topic/sensorData", sensordata);
        }
        return null;
    }

    //Thread fuer die Sensordaten
    class SensorThread implements Runnable {
        @Override
        public void run() {
            try (DatagramSocket socket2 = new DatagramSocket(4001)) {
                while(true) {
                    byte[] buffer = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    // Empfange die Antwort vom mBot
                    socket2.receive(packet);
                    byte[] data = packet.getData();
                    //System.out.println("Data: " + data);

                    String sensorDataJSON = new String(data, 0, packet.getLength(), StandardCharsets.UTF_8);

                    //System.out.println("Empfangene Sensorwerte:\n" + sensorDataJSON);

                    //Auslesen
                    Gson gson = new Gson();
                    // JSON-String in ein Objekt deserialisieren
                    sensordata = gson.fromJson(sensorDataJSON, Sensordata.class);
                    sendSensorData();
                    //System.out.println(sensordata.getMbotid() + "\t" + sensordata.getQuadRGB() + "\t" + sensordata.getUltrasonic());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Linienfolgen Modus aktivieren/deaktivieren
    @PostMapping("/lineFollower")
    @ResponseBody
    public void lineFollower(@RequestParam("follower") String follower) {
        //System.out.println("LINE-FOLLOWER: " + follower);
        if (follower.equals("ON")) {
            start = true;
            // Starte den Line-Follower-Modus
            Thread thread2 = new Thread(new LineFollowerThread());
            thread2.start();
        } else if (follower.equals("OFF")){
            // Stoppe den Line-Follower-Modus
            start = false;
        }
    }

    //Thread fuer den Line Follower
    class LineFollowerThread implements Runnable {
        @Override
        public void run() {
            log.info("Linefollower");
            String previousCommand = "";
            try (DatagramSocket socket = new DatagramSocket()) {
                while (start) {
                    String[] quadRGB;
                    quadRGB = sensordata.getQuadRGB();

                    String[] s1 = quadRGB[0].split("x");
                    String lv = s1[1];
                    String[] s2 = quadRGB[1].split("x");
                    String liv = s2[1];
                    String[] s3 = quadRGB[2].split("x");
                    String riv = s3[1];
                    String[] s4 = quadRGB[3].split("x");
                    String rv = s4[1];

                    log.info(lv + "," + liv + "," + riv + "," + rv);

                    //Gerade aus fahren
                    if ((Objects.equals(lv, "ffffff") && Objects.equals(rv, "ffffff")) && !previousCommand.equals("UP")) {
                        //System.out.println("Geradeaus fahre");
                        previousCommand = "UP";
                        byte[] sendData = previousCommand.getBytes();

                        DatagramPacket packet = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(mBotIP), 4000);
                        socket.send(packet);
                    }
                    //STOP
                    else if ((Objects.equals(lv, "ffffff") && Objects.equals(rv, "ffffff") && Objects.equals(riv, "ffffff") && Objects.equals(lv, "ffffff")) && !previousCommand.equals("STOP")) {
                        //System.out.println("Stoppen");
                        previousCommand = "STOP";
                        byte[] sendData = previousCommand.getBytes();
                        DatagramPacket packet = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(mBotIP), 4000);
                        socket.send(packet);
                    }
                    //Nach Rechts fahren
                    else if (sensordata.getLine() == 7 || sensordata.getLine() == 3 || sensordata.getLine() == 1) {
                        //System.out.println("Nach Rechts fahren");
                        try {
                            //Befeht in byte-Array konvertieren
                            String s = "TURN_RIGHT";
                            byte[] sendData = s.getBytes();

                            DatagramPacket packet = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(mBotIP), 4000);
                            socket.send(packet);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    //Nach Links fahren
                    else if (sensordata.getLine() == 14 || sensordata.getLine() == 12 || sensordata.getLine() == 8) {
                        //System.out.println("Nach Links fahren");
                        try {
                            //Befeht in byte-Array konvertieren
                            String s = "TURN_LEFT";
                            byte[] sendData = s.getBytes();
                            DatagramPacket packet = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(mBotIP), 4000);
                            socket.send(packet);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    //System.out.println(previousCommand);
                    Thread.sleep(500);
                }
            } catch (Exception e) {
                log.error("FEHLER IM LINE-FOLLOWER THREAD");
            }
        }
    }
}