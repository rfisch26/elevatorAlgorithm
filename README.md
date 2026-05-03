# Efficient Elevator Routing Algorithm

A Java simulation of an optimized elevator scheduling algorithm for multistory hotel buildings, developed as an algorithmic research project. The algorithm minimizes passenger wait time by using separate priority queues for up and down requests, allowing elevators to skip floors with no pending requests.

Built by Rachel Fischmar, Katie Butts, and Annie Dima.

## Overview

Standard elevator algorithms (SCAN, FCFS, SSTF) each have tradeoffs — starvation, unnecessary floor visits, or poor wait time distribution. This project implements a dual-queue priority approach that:

- Maintains a **min-heap** for upward requests (serves lowest floors first)
- Maintains a **max-heap** for downward requests (serves highest floors first)
- Skips floors with no pending requests, reducing unnecessary elevator travel
- Supports **multiple elevators** operating concurrently in a building

**Time complexity: O(n log n)**, where n is the number of passenger requests.

## Algorithm Design

The scheduler uses three core methods:

**`floorRequest()`** — Entry point. Determines current elevator direction and delegates to the appropriate queue processor.

**`processUpRequest()`** — Polls the min-heap up-queue, moving the elevator to each requested floor in ascending order. Switches to down direction when the up-queue is empty.

**`processDownRequest()`** — Polls the max-heap down-queue, moving the elevator to each requested floor in descending order. Switches to idle when both queues are empty.

```
Elevator states: UP | DOWN | IDLE
Up queue:   min-heap → serves floor 2 before floor 7
Down queue: max-heap → serves floor 9 before floor 3
```

## Test Cases & Results

The algorithm was benchmarked across 7 test cases measuring total execution time in nanoseconds, varying the number of elevators (e), passengers (p), floors (f), and direction (d).

| Test | e | p | f | Direction | Avg Time (ns) |
|------|---|---|---|-----------|---------------|
| 1 | 1 | 1 | 2 | Up | ~15,664 |
| 2 | 1 | 1 | 2 | Down | ~5,696 |
| 3 | 1 | 6 | 5 | Up | ~15,219 |
| 4 | 1 | 6 | 5 | Down | ~15,179 |
| 5 | 1 | 6 | 10 | Both | ~11,763 |
| 6 | 2 | 6 | 10 | Both (split) | ~7,405 |
| 7 | 2 | 6 | 10 | Both (any) | ~6,361 |

**Key findings:**
- Adding a second elevator reduced average time by ~46% compared to single-elevator scenarios
- Direction (up vs. down) had minimal impact on average time with more passengers and floors
- Two elevators serving requests in both directions (Test 7) yielded the best overall performance

## Build & Run

**Requirements:** Java JDK 8+

```bash
# Compile
javac -d bin src/elevator/*.java

# Run
java -cp bin elevator.Main
```

## Project Structure

```
elevatorAlgorithm/
├── src/
│   └── elevator/       # Java source files (Elevator, Person, queue logic)
├── bin/
│   └── elevator/       # Compiled .class files
└── README.md
```

## Concepts Demonstrated

- **Data structures:** Min-heap and max-heap via Java `PriorityQueue` for efficient floor scheduling
- **Algorithm design:** Dual-queue SCAN variant with starvation prevention
- **Complexity analysis:** Heap sort O(n log n) with `buildHeap` O(n) and `heapify` O(log n)
- **Benchmarking:** Nanosecond-precision timer across multiple parameterized test cases
- **OOP in Java:** Encapsulated `Person` and `Elevator` objects, enum-based state management
