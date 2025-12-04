**Wyatt Harris CSC325**  
**Threads Of Magic**  
- In this text-based adventure game, follow around the famous trio from the Harry Potter series in order to find and solve Horcruxes hidden at Hogwarts. Have chance encounters with dementors, traps, and potions that will put the fate of the wizarding and muggle worlds in danger!

**Features & Use Cases**     
- Use Case: Anytime someone needs some quick fun  
- Features: Varying potions, dementor encounters, Hogwarts exploration, traps
  
**Technologies Used**      
- Java, weather meteo API, Multithreading, Lambda Expressions
     
**Weather API**  
- A weather API is used in this game that enhances the experience by changing the mood based on the real weather in Scotland!
  
**How to run the project**    
- Make sure your machine can compile and run Java source code and download these files. After the download, run the file titled GameEngine   

**Contributors & Responsibilities**   
- Wyatt Harris - Game Developer
  
**⚙️Technical Overview**  
Concept	Implementation  
- Abstraction	Character is an abstract superclass defining core behavior.  
- Inheritance	Harry, Hermione, and Ron extend Character with unique traits.  
- Polymorphism	Each overrides performTurn() and handles events differently.  
- Arrays	Each character’s inventory is a fixed-size Item[] array.  
- Collections	Rooms use ConcurrentLinkedQueue<Item> and ConcurrentHashMap for flags.  
- Lambdas & Streams	Used for event handling (Consumer<Event>), inventory filtering, and logging.  
Parallelism / Threads	Each character, the event dispatcher, and the logger run in independent threads.
    
**Thread Synchronization Approach**  
The game uses several thread synchronization techniques to ensure safe concurrent access to shared resources:

- **ReentrantLock**: Each HorcruxFragment uses a ReentrantLock so only one character can examine (solve) it at a time, preventing race conditions.
- **Synchronized blocks**: When picking up items, code synchronizes on the room object to prevent multiple characters from modifying the room’s item list simultaneously.
- **Volatile fields**: The `alive` field in Character is volatile to ensure visibility of changes across threads.
These mechanisms prevent data corruption and ensure consistent game state during multithreaded gameplay.

**Harry	Fighter / Seeker**	Seeks Horcrux faster, uses defensive spells   
**Hermione	Scholar / Spellcaster**	Researches and unlocks Horcruxes  
**Ron	Rogue / Scout**	Disarms traps
