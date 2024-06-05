package com.example.mbot2_projekt_v1.classes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import jdk.jfr.DataAmount;
import lombok.Builder;
import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@Data
@Builder
public class Sensordata {

    @JsonProperty("line")
    private int line;

    @JsonProperty("yaw")
    private int yaw;

    @JsonProperty("mbotid")
    private String mbotid;

    @JsonProperty("loudness")
    private int loudness;

    @JsonProperty("rot_x")
    private int rotX;

    @JsonProperty("rot_y")
    private int rotY;

    @JsonProperty("gyro_y")
    private int gyroY;

    @JsonProperty("brightness")
    private int brightness;

    @JsonProperty("acc_z")
    private double accZ;

    @JsonProperty("pitch")
    private int pitch;

    @JsonProperty("shakeval")
    private int shakeval;

    @JsonProperty("wave_speed")
    private int waveSpeed;

    @JsonProperty("roll")
    private int roll;

    @JsonProperty("acc_x")
    private double accX;

    @JsonProperty("wave_angle")
    private int waveAngle;

    @JsonProperty("acc_y")
    private double accY;

    @JsonProperty("gyro_x")
    private int gyroX;

    @JsonProperty("ultrasonic")
    private double ultrasonic;

    @JsonProperty("gyro_z")
    private int gyroZ;

    @JsonProperty("rot_z")
    private int rotZ;

    @JsonProperty("quad_rgb")
    @SerializedName("quad_rgb")
    private String[] quadRGB;

    @JsonProperty("speed")
    private int speed;

    public synchronized int getLine() {
        return line;
    }

    public synchronized void setLine(int line) {
        this.line = line;
    }

    public synchronized int getYaw() {
        return yaw;
    }

    public synchronized void setYaw(int yaw) {
        this.yaw = yaw;
    }

    public synchronized String getMbotid() {
        return mbotid;
    }

    public synchronized void setMbotid(String mbotid) {
        this.mbotid = mbotid;
    }

    public synchronized int getLoudness() {
        return loudness;
    }

    public synchronized void setLoudness(int loudness) {
        this.loudness = loudness;
    }

    public synchronized int getRotX() {
        return rotX;
    }

    public synchronized void setRotX(int rotX) {
        this.rotX = rotX;
    }

    public synchronized int getRotY() {
        return rotY;
    }

    public synchronized void setRotY(int rotY) {
        this.rotY = rotY;
    }

    public synchronized int getGyroY() {
        return gyroY;
    }

    public synchronized void setGyroY(int gyroY) {
        this.gyroY = gyroY;
    }

    public synchronized int getBrightness() {
        return brightness;
    }

    public synchronized void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public synchronized double getAccZ() {
        return accZ;
    }

    public synchronized void setAccZ(double accZ) {
        this.accZ = accZ;
    }

    public synchronized int getPitch() {
        return pitch;
    }

    public synchronized void setPitch(int pitch) {
        this.pitch = pitch;
    }

    public synchronized int getShakeval() {
        return shakeval;
    }

    public synchronized void setShakeval(int shakeval) {
        this.shakeval = shakeval;
    }

    public synchronized int getWaveSpeed() {
        return waveSpeed;
    }

    public synchronized void setWaveSpeed(int waveSpeed) {
        this.waveSpeed = waveSpeed;
    }

    public synchronized int getRoll() {
        return roll;
    }

    public synchronized void setRoll(int roll) {
        this.roll = roll;
    }

    public synchronized double getAccX() {
        return accX;
    }

    public synchronized void setAccX(double accX) {
        this.accX = accX;
    }

    public synchronized int getWaveAngle() {
        return waveAngle;
    }

    public synchronized void setWaveAngle(int waveAngle) {
        this.waveAngle = waveAngle;
    }

    public synchronized double getAccY() {
        return accY;
    }

    public synchronized void setAccY(double accY) {
        this.accY = accY;
    }

    public synchronized int getGyroX() {
        return gyroX;
    }

    public synchronized void setGyroX(int gyroX) {
        this.gyroX = gyroX;
    }

    public synchronized double getUltrasonic() {
        return ultrasonic;
    }

    public synchronized void setUltrasonic(double ultrasonic) {
        this.ultrasonic = ultrasonic;
    }

    public synchronized int getGyroZ() {
        return gyroZ;
    }

    public synchronized void setGyroZ(int gyroZ) {
        this.gyroZ = gyroZ;
    }

    public synchronized int getRotZ() {
        return rotZ;
    }

    public synchronized void setRotZ(int rotZ) {
        this.rotZ = rotZ;
    }

    public synchronized String[] getQuadRGB() {
        return quadRGB;
    }

    public synchronized void setQuadRGB(String[] quadRGB) {
        this.quadRGB = quadRGB;
    }

    public synchronized void setSpeed(int speed){this.speed=speed;}
    public synchronized int getSpeed(){return speed;}
}
