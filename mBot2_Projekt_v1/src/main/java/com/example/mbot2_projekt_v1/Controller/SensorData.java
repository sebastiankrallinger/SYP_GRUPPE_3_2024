package com.example.mbot2_projekt_v1.Controller;

public class SensorData {
    private double ultrasonic;
    private Leds leds;
    private Motors motors;
    private double[] accelerometer;
    private String display;
    private String joystick;

    public static class Leds {
        private int red;
        private int green;
        private int blue;

        // Getter und Setter für die LEDs
        // ...
    }

    public static class Motors {
        private int leftSpeed;
        private int rightSpeed;

        // Getter und Setter für die Motors
        // ...
    }

    // Getter und Setter für die Sensor-Daten
    // ...
}
