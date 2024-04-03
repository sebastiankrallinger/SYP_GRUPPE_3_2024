
import time
import usocket
import ujson
import os
import mbuild
import cyberpi
import _thread

thread_flag = False
distance = 50
server_ip = "10.10.2.91"    #Server IP-Adresse (nur vorübergehend hard-codiert)

def ultrasonic_thread():
    global distance
    global thread_flag
    while thread_flag:
        distance = cyberpi.ultrasonic2.get(index=1)
        if distance < 35:
            turn()
        time.sleep(1)
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
    if message == "LEFT_B":
        cyberpi.mbot2.drive_power(-40, 60)
    if message == "RIGHT_B":
        cyberpi.mbot2.drive_power(-60, 40)
    if message == "STOP":
        cyberpi.mbot2.EM_stop(port="all")
        

def send_sensor_data_to_server():
    try:
        sensor_data = {
        "ultrasonic": cyberpi.ultrasonic2.get(index=1),
        "leds": {
            "red": cyberpi.led.red,
            "green": cyberpi.led.green,
            "blue": cyberpi.led.blue
        },
        "motors": {
            "left_speed": cyberpi.mbot2.get_motor_speed(port="M1"),
            "right_speed": cyberpi.mbot2.get_motor_speed(port="M2")
        },
        "accelerometer": cyberpi.motion.get_acceleration(),
        "display": cyberpi.display.read(),
        "joystick": cyberpi.joystick.read()
        }
    
        sensor_data_json = ujson.dumps(sensor_data)
        
        s = usocket.socket(usocket.AF_INET, usocket.SOCK_DGRAM)
    
        #Ip setzten
        server_addr = (server_ip, 4000)
        cyberpi.console.println("Sensordaten wurden gesendet an:")
        cyberpi.console.println(server_ip)

        
        s.sendto(sensor_data_json.encode('utf-8'), server_addr)
        s.close()
    except Exception as e:
        print("Error sending sensor data:", e)




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
        thread_flag = True
        _thread.start_new_thread(ultrasonic_thread, ())
        cyberpi.console.println("Suizide-Prevention aktiv!")
        time.sleep(3)
        cyberpi.console.clear()
    elif received_message=="OFF":    
        thread_flag = False
        cyberpi.console.println("Suizide-Prevention deaktiviert!")
        time.sleep(3)
        cyberpi.console.clear()
    elif received_message=="UP" or received_message=="DOWN" or received_message=="RIGHT" or received_message=="RIGHT_B" or received_message=="LEFT" or received_message=="LEFT_B" or received_message=="STOP":
        drive(received_message)
    elif received_message == "TRUE" or received_message == "FALSE":
        cyberpi.led.on(0,255,0)
    else:
        server_ip = received_message
        cyberpi.console.println("Server IP:")
        cyberpi.console.println(server_ip)
        send_sensor_data_to_server()