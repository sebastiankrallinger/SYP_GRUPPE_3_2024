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
        break

sockaddr = cyberpi.network.get_ip()
cyberpi.console.println(sockaddr)

gateway = cyberpi.network.get_gateway()
cyberpi.console.println(gateway)

s = usocket.socket(usocket.AF_INET, usocket.SOCK_DGRAM)

i = 1

name = "SUPERPI"
while True:
    s.sendto(name, ("10.10.1.184", 1234))
    i += 1
    time.sleep(1)
    cyberpi.led.on(255, 255, 255)  # White Lights
    time.sleep(0.1)
    cyberpi.led.on(0, 0, 0)  # No Lights
    b, adr = s.recvfrom(1024)
    txt = str(b, "utf-8")
    cyberpi.console.println(txt)
    cyberpi.console.println(str(adr))
    data = ujson.loads(txt)

    cyberpi.led.on(data['R'], data['G'], data['B'])