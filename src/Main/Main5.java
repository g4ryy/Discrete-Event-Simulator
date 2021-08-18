import java.util.Scanner;
import cs2030.simulator.Server;
import cs2030.simulator.State;
import cs2030.simulator.Type;
import cs2030.simulator.DoneEvent;
import cs2030.simulator.ServeEvent;
import cs2030.simulator.Event;
import cs2030.simulator.RandomProcessor;
import cs2030.simulator.ArrivalEvent;
import cs2030.simulator.WaitEvent;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

class Main5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int numOfServers = sc.nextInt();
        int queueLength = sc.nextInt();
        int numOfCustomers = sc.nextInt();
        int seed = sc.nextInt();
        double arrivalRate = sc.nextDouble();
        double serviceRate = sc.nextDouble();
        double restRate = sc.nextDouble();
        double restProbability = sc.nextDouble();
        double greedProbability = sc.nextDouble();
        
        RandomProcessor randProcessor = new RandomProcessor(seed, 
                arrivalRate, serviceRate, restRate);

        Server.initialiseServers(numOfServers, queueLength);

        State state = new State();

        PriorityQueue<Event> eventQueue = new PriorityQueue<>();

        Queue<ArrivalEvent> arriveList = randProcessor.initialiseArrival(numOfCustomers, 
                greedProbability);

        Queue<Double> serviceTimes = randProcessor.genServiceTimes(numOfCustomers);

        while (!arriveList.isEmpty()) {
            eventQueue.add(arriveList.poll());
        }

        while (!eventQueue.isEmpty()) {
            Event currEvent = eventQueue.poll();

            Type eventType = currEvent.getType();

            if (eventType == Type.SERVE && !Server.isIdleAt(currEvent.getServer())) {
                eventQueue.add(((ServeEvent) currEvent).reschedule());
            } else if (eventType == Type.SERVE && 
                    currEvent.getCustomer().getServiceTime() == 0) {
                eventQueue.add(((ServeEvent) currEvent).reschedule(serviceTimes));
            } else {
                if (eventType != Type.REST && 
                            eventType != Type.DONEREST) {
                    System.out.println(currEvent);
                }
                
                state = state.nextState(currEvent);
                
                if (!currEvent.isLast()) {
                    Event nextEvent = currEvent.nextEvent();
                    
                    eventQueue.add(nextEvent);
                }
                
                if (eventType == Type.DONE) {       
                    boolean shouldRest = randProcessor.shouldRest(restProbability);

                    if (shouldRest) {
                        DoneEvent event = (DoneEvent) currEvent;
                        eventQueue.add(event.nextEvent(randProcessor.genRestDuration()));
                    }
                }
            }
        }
        
        System.out.println(state);
    }
}
