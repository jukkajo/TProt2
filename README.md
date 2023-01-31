Different approaches, simple GUIs and libraries used as means to achieve better knowledge with Java.

##Virtual Socket

### Ver 1
 
Application for testing purposes, e.g. client sends data to server and server
displays it on screen. Data send only in one direction.
 
### Ver 2

Previous + virtual socket that randomly drops a packet.

### Ver 3

Previous + virtual socket randomly delays a packet, before passing it forward.

### Ver 4

Previous + virtual socket randomly generates bit errors on the data of the packet.

##Positive and Negative ACKs

+Detection for bit errors
+Reliable data transfer with positive and negative ACKs
+Reliable data transfer with only positive ACKs
+Reliable data transfer with only negative ACKs

##Reliable Transport protocol

Reliable data transfer on top of UDP that can handle packet loss, packet delay and
bit errors on some level.
