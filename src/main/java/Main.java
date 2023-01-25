import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.awt.*;
import java.awt.event.InputEvent;
import java.nio.charset.StandardCharsets;

/*
* UnoMauso
* Controlls mouse pointer using the "UnoMauso" mouse
* Authors: Hermann Witte, Jakub Korona
* */

public class Main {
    static boolean mousePressed = false;
    public static void main(String[] args) {
        Robot robot;

        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }

        SerialPort[] ports = SerialPort.getCommPorts();
        for (int i = 0; i < ports.length; i++) {
            System.out.println(ports[i].getSystemPortPath());
        }
        SerialPort sp = SerialPort.getCommPort("\\\\.\\COM6");
        sp.setComPortParameters(9600,  Byte.SIZE, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
        sp.openPort();
        if (!sp.isOpen()){
            System.out.println("ne");
        } else {
            System.out.println("Offe");
        }
        sp.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
            }
            @Override
            public void serialEvent(SerialPortEvent serialPortEvent) {
                if (serialPortEvent.getEventType() == SerialPort.LISTENING_EVENT_DATA_RECEIVED) {
                    byte[] bytes = serialPortEvent.getReceivedData();
                    String s = new String(bytes, StandardCharsets.UTF_8);
                    String[] lines = s.split("\r?\n");
                    int y = MouseInfo.getPointerInfo().getLocation().y;
                    int x = MouseInfo.getPointerInfo().getLocation().x;
                    final int pressed = 0;
                    int press = Integer.valueOf(lines[0]);
                    switch (press){
                        case pressed :
                            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    }

                    if (Integer.valueOf(lines[1]) > 600){
                        int z = 1030 - Integer.valueOf(lines[1]);
                        x += 15 - z / 30;
                    } else if (Integer.valueOf(lines[1]) < 500){
                        int z = 500 - Integer.valueOf(lines[1]);
                        x -= z / 34;
                    }
                    if (Integer.valueOf(lines[2]) > 600){
                        int z = 1030 - Integer.valueOf(lines[2]);
                        y += 15 - z / 30;
                    } else if (Integer.valueOf(lines[2]) < 500){
                        int z = 500 - Integer.valueOf(lines[2]);
                        y -= z / 34;
                    }


                    robot.mouseMove(x, y);
                    /*System.out.println("Click: " + lines[0]);
                    System.out.println("X-Axis: " + lines[1]);
                    System.out.println("Y-Axis: " + lines[2]);*/
                }
            }
        });
    }

}