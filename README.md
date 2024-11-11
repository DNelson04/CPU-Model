# CPU Scheduling model
OVERVIEW:
This project simulates a round robin CPU scheduling algorithim. this includes many processes that have different priorities and burst times. We use the round robin technique. Giving each process a certain amount of time before pausing and moving to the next process. Our Work mainly includes Clock,CPU,Scheduler,process and Queue. Also the interfaces ClockObserver and ProcessObserver.
________________________________
1. Main class:

The Main class sets up the CPU and processes. The CPU is initialized with a specific time quantum, and a sample process is created.
After the cpu starts, the clock initiates and the round robin scheduling cycle starts.

2. Clock Class:

The clock class represents the timing mechanism for the CPU. It tracks the passage of time. It notifies the scheduler when a time quantum has elapsed.

3. CPU class:

CPU class manages process execution. It focuses on setting up the scheduler with a list of processes to read from. It also starts the clock to manage the scheduler.

4. scheduler class(inner class in CPU):

The scheduler is responsible for the queue mangament. It stores processes based on priority in order to efficiently retrive and execute these processes.

5. Process class:

This class is responsible for every process in the system. It would give a unique Identifier for the process. It would also provide the time the process becomes available, and the total execution time that was required for the process. We created a process() method, so that it runs in a loop and decrements the burst time every second.

6. Queue class:

For this class, a priority-based queue was implemented to know which process goes first. The processes get added to queue based on their priority level, so high priority processes would go first.
7. Statistics class:

This class keeps track of the statistics for the simulation
8. Observer Interfaces:

The clock observer is responsible for enabling the clock to notify the scheduler every second.
Process observer allows Process objects to inform the CPU when they have finished execution.


This setup models a Round Robin scheduler, balancing fairness and efficiency by distributing CPU time equally among processes.