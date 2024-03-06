import time
import usocket
import ujson
import os
import mbuild
import cyberpi

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
            #Verarbeiten Sie die empfangenen Daten
            recieved_message = data.decode('utf-8')
            if recieved_message == "TRUE":
                cyberpi.led.on(0,255,0)
                cyberpi.console.clear()
                cyberpi.console.println("Mbot bereit zum Steuern")
                time.sleep(3)
                break
            
        cyberpi.led.off() # No Lights
        cyberpi.console.clear()
        break


while True:
    # Daten empfangen (maximal 1024 Bytes)
    data, addr = s.recvfrom(1024)

    # Verarbeiten Sie die empfangenen Daten
    received_message = data.decode('utf-8')
    if received_message != "":
        cyberpi.console.println("%s" % received_message)
        if received_message == "UP":
            cyberpi.mbot2.forward(speed=60)
        if received_message == "DOWN":
            cyberpi.mbot2.backward(speed=60)
        if received_message == "LEFT":
            cyberpi.mbot2.drive_power(40, -60)
        if received_message == "RIGHT":
            cyberpi.mbot2.drive_power(60, -40)
        if received_message == "STOP":
            cyberpi.mbot2.EM_stop(port="all")