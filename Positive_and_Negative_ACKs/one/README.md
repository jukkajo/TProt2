This is an application for testing purposes, e.g. client sends data to server and server displays it on screen. Data is only send in one direction.
+Virtual socket that randomly drops a packet (i.e. does not pass it to UDP socket or to the application)
+Virtual socket randomly delays a packet, before passing it forward
+Bit errors are randomly generated on the data of the packet(single bit)
+Detection for a single bit error -> CRC8
