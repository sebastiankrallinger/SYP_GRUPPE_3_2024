import time
import usocket
import ujson
import os
import mbuild
import cyberpi

cyberpi.led.on(0, 0, 255)  # Blue Lights
cyberpi.network.config_sta("htljoh-public", "joh12345")  # Wlan Name & Password

while True:
    b = cyberpi.network.is_connect()
    if b == False:
        cyberpi.led.on(255, 0, 0)  # Red Lights
        time.sleep(1)
    else:
        cyberpi.led.on(0, 255, 0)  # Green Lights
        time.sleep(2)
        cyberpi.led.on(255, 255, 255)  # White Lights
        time.sleep(0.1)
        cyberpi.led.on(0, 0, 0)  # No Lights
        break

sockaddr = cyberpi.network.get_ip()
cyberpi.console.println(sockaddr)

gateway = cyberpi.network.get_gateway()

s = usocket.socket(usocket.AF_INET, usocket.SOCK_DGRAM)

local_addr = ("0.0.0.0", 4000)  # Ändern Sie den Port entsprechend Ihrer Konfiguration

# Socket an die Adresse und den Port binden
s.bind(local_addr)

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
