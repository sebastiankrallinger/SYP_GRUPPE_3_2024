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

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getYaw() {
        return yaw;
    }

    public void setYaw(int yaw) {
        this.yaw = yaw;
    }

    public String getMbotid() {
        return mbotid;
    }

    public void setMbotid(String mbotid) {
        this.mbotid = mbotid;
    }

    public int getLoudness() {
        return loudness;
    }

    public void setLoudness(int loudness) {
        this.loudness = loudness;
    }

    public int getRotX() {
        return rotX;
    }

    public void setRotX(int rotX) {
        this.rotX = rotX;
    }

    public int getRotY() {
        return rotY;
    }

    public void setRotY(int rotY) {
        this.rotY = rotY;
    }

    public int getGyroY() {
        return gyroY;
    }

    public void setGyroY(int gyroY) {
        this.gyroY = gyroY;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public double getAccZ() {
        return accZ;
    }

    public void setAccZ(double accZ) {
        this.accZ = accZ;
    }

    public int getPitch() {
        return pitch;
    }

    public void setPitch(int pitch) {
        this.pitch = pitch;
    }

    public int getShakeval() {
        return shakeval;
    }

    public void setShakeval(int shakeval) {
        this.shakeval = shakeval;
    }

    public int getWaveSpeed() {
        return waveSpeed;
    }

    public void setWaveSpeed(int waveSpeed) {
        this.waveSpeed = waveSpeed;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public double getAccX() {
        return accX;
    }

    public void setAccX(double accX) {
        this.accX = accX;
    }

    public int getWaveAngle() {
        return waveAngle;
    }

    public void setWaveAngle(int waveAngle) {
        this.waveAngle = waveAngle;
    }

    public double getAccY() {
        return accY;
    }

    public void setAccY(double accY) {
        this.accY = accY;
    }

    public int getGyroX() {
        return gyroX;
    }

    public void setGyroX(int gyroX) {
        this.gyroX = gyroX;
    }

    public double getUltrasonic() {
        return ultrasonic;
    }

    public void setUltrasonic(double ultrasonic) {
        this.ultrasonic = ultrasonic;
    }

    public int getGyroZ() {
        return gyroZ;
    }

    public void setGyroZ(int gyroZ) {
        this.gyroZ = gyroZ;
    }

    public int getRotZ() {
        return rotZ;
    }

    public void setRotZ(int rotZ) {
        this.rotZ = rotZ;
    }

    public String[] getQuadRGB() {
        return quadRGB;
    }

    public void setQuadRGB(String[] quadRGB) {
        this.quadRGB = quadRGB;
    }

    public void setSpeed(int speed){this.speed=speed;}
    public int getSpeed(){return speed;}
}
