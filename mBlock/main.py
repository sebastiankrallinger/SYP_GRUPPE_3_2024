import time
import usocket
import ujson
import os
import mbuild
import cyberpi
import _thread

thread_flag = False
distance = 50

def ultrasonic_thread():
    global distance
    global thread_flag
    while thread_flag:
        distance = cyberpi.ultrasonic2.get(index=1)
        if distance < 35:
            turn()
        time.sleep(2)
def turn():
    cyberpi.led.on(255, 0, 0)
    #cyberpi.console.println("Stop")
    cyberpi.mbot2.EM_stop(port = "all")
    time.sleep(3)
    cyberpi.mbot2.turn_left(speed = 25, run_time = 1.8)
    cyberpi.led.off() #No Lights

def drive(message):
    #cyberpi.console.println("%s" % message)
    if message == "UP":
        cyberpi.mbot2.forward(speed=85)
    if message == "DOWN":
        cyberpi.mbot2.backward(speed=60)
    if message == "LEFT":
        cyberpi.mbot2.drive_power(40, -60)
    if message == "RIGHT":
        cyberpi.mbot2.drive_power(60, -40)
    if message == "STOP":
        cyberpi.mbot2.EM_stop(port="all")

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
                cyberpi.led.on(0,255,0)
                cyberpi.console.clear()
                cyberpi.console.println("Mbot bereit zum Steuern")
                time.sleep(3)
                break
            
        cyberpi.led.off() #No Lights
        cyberpi.console.clear()
        break
    
while True:
    # Daten empfangen (maximal 1024 Bytes)
    data, addr = s.recvfrom(1024)
    #empfangenen Daten verarbeiten
    received_message = data.decode('utf-8')
    if received_message=="ON":
        cyberpi.console.println("Suizide-Prevention aktiv!")
        thread_flag = True
        _thread.start_new_thread(ultrasonic_thread, ())
        time.sleep(3)
        cyberpi.console.clear()
    elif received_message=="OFF":        
        cyberpi.console.println("Suizide-Prevention deaktiviert!")
        thread_flag = False
        time.sleep(3)
        cyberpi.console.clear()
    elif received_message=="UP" or received_message=="DOWN" or received_message=="RIGHT" or received_message=="LEFT" or received_message=="STOP":
        drive(received_message)