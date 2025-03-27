# CS445-File-System

## Credit

### Devs
- Michael Seavers
- Raja Umar Khattab

### Notice
Some code has been generated by generative AI tools. Additional help, such as reminders of structures and questions
have also been directed through the use of these tools.

Any code that has been generated has been commented / labeled (only 1, in Byte Converter)

## About 

This project is meant for the CS 445 project at Western Kentucky University under the direction of Dr. Qi Li.
In this project, we created a File System simulation, similar to the ones found on linux.

We used Java for the entirety of the project and have created various pieces, such as an FCB, VCB, DataBlock,
System and Per-process open file table, and much more!

Users are able to edit the simulation, via the simulation script, and create your own processes! We used a
builder in the processes class so that it allows quick and easy creation of processes. Each process is a thread,
which helps simulate many processes running on a PC.

A process has the following functions:
- Create(filename, size): Creates a new file with a size in bytes
- Dir(): List all files on the directory
- Delete(filename): Delete a file
- Open(filename): Opens an existing file
- Close(handler): Closes a file within a process
- Write(handler, content): Either write a byte[] or String into a file
- Read(handler): Reads the byte[] of a file
- ReadString(handler): Reads the String of a file

Deletion uses the same logic in current OS where it pends deletion of a file if other processes are using that file.
All test cases are visible in the simulation script.

## How to Use
For running the test cases, go to the Simulation class under the misc package. There are currently 5 full tests available.
Due to the nature of threads, only run one test at a time by commenting out all the other tests except for one.
The test will print out all things happening, including instructions and input/output.

You can make your own processes by following the other test cases. The commands are displayed above but instead
of handlers on many of them, they take filename instead. All size measurements are in bytes.
