# Thread-Pool
Thread pool demo


## Getting Started
git clone https://github.com/xuchengyun/Thread-Pool.git

## Description
This project is demo for thread pool creation. Generate a thread pool with 3 thread and start 5 threads simultaneous.
First 3 threads will be run. When thread gets complete, put remain threads in the thread pool. 
Done!

## Summary
To implement a thread pool, we need a class to generate thread first. After that, an Executors is needed to start a thread pool,
call the method *Executors.newFixedThreadPool()* with a fixed size of thread pool.
