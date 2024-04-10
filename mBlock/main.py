import time
import usocket
import ujson
import os
import mbuild
import cyberpi
import _thread

thread_flag = False
distance = 50
suicide_prevention = False
slider_speed=0
direction_before="STOP"
selected_speed=50

def define_speed(speed_value):
    global slider_speed
    slider_speed = speed_value * 2
    #cyberpi.console.println(slider_speed)

def connect():
    cyberpi.led.on(0,255,0)
    cyberpi.console.clear()
    cyberpi.console.println("Mbot bereit zum Steuern")
    time.sleep(3)

def ultrasonic_thread():
    global distance
    global thread_flag
    global suicide_prevention
    while thread_flag:
        distance = cyberpi.ultrasonic2.get(index=1)
        if distance < 50:
            suicide_prevention = True
            turn()
        time.sleep(1)
        suicide_prevention = False
        
    
def turn():
    cyberpi.led.on(255, 0, 0)
    #cyberpi.console.println("Stop")
    cyberpi.mbot2.EM_stop(port = "all")
    time.sleep(3)
    cyberpi.mbot2.turn_left(speed = 25, run_time = 1.8)
    cyberpi.led.off() #No Lights

def drive(message):
    global direction_before
    #cyberpi.console.println("%s" % message)
    if message == "UP":
        cyberpi.mbot2.forward(speed = slider_speed)
        direction_before="UP"
    if message == "DOWN":
        cyberpi.mbot2.backward(speed = slider_speed)
        direction_before="DOWN"
    if message == "LEFT":
        cyberpi.mbot2.drive_power(40, -60)
        direction_before="LEFT"
    if message == "RIGHT":
        cyberpi.mbot2.drive_power(60, -40)
        direction_before="RIGHT"
    if message == "LEFT_B":
        cyberpi.mbot2.drive_power(-40, 60)
        direction_before="LEFT_B"
    if message == "RIGHT_B":
        cyberpi.mbot2.drive_power(-60, 40)
        direction_before="RIGHT_B"
    if message == "STOP":
        cyberpi.mbot2.EM_stop(port="all")
        direction_before="STOP"

cyberpi.led.on(255, 0, 0)  # Red Lights
cyberpi.network.config_sta("htljoh-public", "joh12345")  # Wlan Name & Password

while True:
    b = cyberpi.network.is_connect()
    if b == False:
        cyberpi.led.on(255, 0, 0)
    else:
        cyberpi.console.println("IP-Adresse auf Webseite eingeben:")
        sockaddr = cyberpi.network.get_ip()
        cyberpi.console.println(sockaddr)
        s = usocket.socket(usocket.AF_INET, usocket.SOCK_DGRAM)
        local_addr = ("0.0.0.0", 4000)  # Ändern Sie den Port entsprechend Ihrer Konfiguration
        # Socket an die Adresse und den Port binden
        s.bind(local_addr)
        
        while True:
            cyberpi.led.on(0, 0, 255)  # Blue Lights
            #Daten empfangen (maximal 1024 Bytes)
            data, addr = s.recvfrom(1024)
            #empfangenen Daten verarbeiten
            recieved_message = data.decode('utf-8')
            if recieved_message == "TRUE":
                connect()
                break
        
        cyberpi.led.off() #No Lights
        cyberpi.console.clear()        
        break
    
while True:
    # Daten empfangen (maximal 1024 Bytes)
    data, addr = s.recvfrom(1024)
    #empfangenen Daten verarbeiten
    received_message = data.decode('utf-8')
    if received_message.isdigit():
        selected_speed = int(received_message)
    if suicide_prevention == False:
        if received_message=="ON":
            thread_flag = True
            _thread.start_new_thread(ultrasonic_thread, ())
            cyberpi.console.println("Suicide-Prevention aktiv!")
            time.sleep(3)
            cyberpi.console.clear()
        elif received_message=="OFF":    
            thread_flag = False
            cyberpi.console.println("Suicide-Prevention deaktiviert!")
            time.sleep(3)
            cyberpi.console.clear()
        elif received_message=="UP" or received_message=="DOWN" or received_message=="RIGHT" or received_message=="RIGHT_B" or received_message=="LEFT" or received_message=="LEFT_B" or received_message=="STOP":
            drive(received_message)
        if 0 <= selected_speed <= 100:
            define_speed(selected_speed)
            drive(direction_before)
