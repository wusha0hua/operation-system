
[TOC]

# os

the service system provided 
- program develpment : editor and debugger
- program running : load to memory and initialize IO device
- access to IO device : hide the specific instructions
- control the access to file : hide storage medium
- access to system : provide API 
- detect error and respone : 
- bookkeeping : monite resource 

resource manager 
- computer is a set of resource to move , store , and process
- os is charge of this set of resource 
- dispatch memory
- control on access to IO device
- control and use file
- dispatch processors

os machanism
- os execute by processor 
- os will release control and recover by processor

An operating system (OS) is system software that manages computer hardware and software resources and provides common services for computer programs.


*serial processing*

mechanism
- no os
- operate by control table 
-programs loaded by input device 
- users access to computer by order 

*easy batch processing*

mechansim
- autoly process a batch of program 
- only one process in the memory at the same time 

monitor
- automatically and continously work
- protect program's memory
- timer
- privileged instruction 
- interrupt

mode 
- user mode : can not execute privilege instruction
- kernel mode : can execute privilege instruction and access to protected momery

feature : automatical , order , single-way , always being idle


*multiple processing*

feature : multple-way , dispatch , disorder , not interactive , cocurrent


*time-sharing system*

feature : interactive , mutiple-way , sharing processor , access system at the same time , timely , independent


*CTSS*

the first time-sharing system

there is a monitor to dispatch all process , load the process after monitor memory ervery time 


*real-time system*

feature : 


# process

- executing program 
- running program 
- dispatched and executed by processor

desciption
- a serial thread
- current state
- related resource

compose 
- a executable program
- related data
- execution context 


*modern system*
- microkernel architecture
- multithreading 
- symmetric multiprocessing
- distributed operating system
- object-oriented design


*mircokernel architecture*

feature : just arrange some basic function for kernel including searching address , communication between processes , basic scheduling

advantage : simplified , flexible , adapt to distributed system


*symmetric multiprocessing*

feature : threadings and processes are in parallel , multiprocessors share memory and IO devices , all processor can process a work , os is in charge of synchronization

advantage : high performance , usable , scalability




