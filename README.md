This is a thread pool implementation written from scratch in Java and does not use any classes from java.util.Concurrent. The assignment was for the CS455 Distributed Systems course at CSU. 
It works by having a server start up a thread pool on a port with a given number of threads, and then clients will connect to it and start sending messages.
The messages are 8kb of junk data that the client hashes the messages and keeps in a list. The server will create tasks for the worker threads in the thread pool to handle.
The worker threads will read the junk data then hash and send it back to the client. The client will compare the hash received and hash sent to see if it is the same message.
It was tested with up to 200 clients each sending 5 messages per second.
