package org.example;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Problem: An airport has four runways, each pointing in a different direction.
// Planes are assigned to a runway based on both the time they come in and their point of departure.
// 70% of planes can only go in one direction
// 20% of planes can leave in one of TWO directions and will always choose whichever has the shorter line
// 10% of planes can leave in one of THREE directions and will always choose the shorter line.
// The “airport man” wants to have TWO systems displayed before him, one that shows all four departure runways and their planes and another system that shows one departure calendar that combines all four runways.
// Create and model this with an example running in main.


class Plane { // Creating a plane class to store the time of departure and the departure ways.
    int timeOfDeparture;
    List<Integer> waysOfDeparture;

    public Plane(int timeOfDeparture, List<Integer> waysOfDeparture) { // Constructor for the plane class that takes in the time of departure and the departure ways.
        this.timeOfDeparture = timeOfDeparture; // Setting the time of departure to the time of departure passed in.
        this.waysOfDeparture = waysOfDeparture; // Setting the departure ways to the departure ways passed in.
    }
}

class AirportSimulation { // Creating a class to simulate the airport.
    public static List<Plane> generatePlanes(int numOfPlanes) { // Creating a method to generate planes.
        List<Plane> planes = new ArrayList<>(); // List of planes to store the generated planes.
        Random random = new Random(); // Random object

        for (int i = 0; i < numOfPlanes; i++) {
            int timeOfDeparture = random.nextInt(24) + 1; // Assuming a 24-hour clock and that planes can depart at any hour. (+1 to avoid 0 hours)
            List<Integer> waysOfDeparture;
            double probability = random.nextDouble(); // Random double between 0 and 1 which represents the probability of the plane departure options.
            if (probability < 0.7) {
                // 70% of planes can only go in one direction
                waysOfDeparture = List.of(random.nextInt(4) + 1); // We generate a random integer [0,4) and +1 to make it [0,4] (4 Runways).
                // List.of is used to make an immutable list, it will only have 1 element, and it represents the departure option.
            }
            else if (probability < 0.9) {  // probability is greater than 0.7 and smaller than last 10%
                // 20% of planes can leave in one of TWO directions
                int direction1 = random.nextInt(4) + 1; // we declare direction variable to represent the random direction, it contains an integer [0,4]
                int direction2 = (direction1 + random.nextInt(3)+1) % 4 + 1; // direction 2 follows the same logic, but it calculates the value based on the direction1.
                waysOfDeparture = List.of(direction1, direction2); // same immutable list, but now we take 2 direction in. One is random, and the other is different from the first.

            }
            else {
                // 10% of planes can leave in one of THREE directions
                int direction1 = random.nextInt(4) + 1;
                int direction2 = (direction1 + random.nextInt(3)+1) % 4 + 1;
                int direction3 = (direction2 + random.nextInt(3)+1) % 4 + 1;
                waysOfDeparture = List.of(direction1, direction2, direction3); // same as previous to cases, but now we have 3 directions.
            }

            planes.add(new Plane(timeOfDeparture, waysOfDeparture)); // creates a new instance of the plane with directions, stored in the planes list.
        }

        return planes; // returns planes list
    }

    // displayRunways method iterates through each runway and for each goes over all the planes. If the plane has a departure it prints the departure time.
    public static void displayRunways(List<Plane> planes) {
        for (int runway = 1; runway <= 4; runway++) { // iterates through each 1-4 runways.
            System.out.println(" -Runway " + runway + " "); // prints runway number.
            for (Plane plane : planes) { // iterates through each plane in the list of planes.
                if (plane.waysOfDeparture.contains(runway)) { // condition for the departure (checks if plane is scheduled to depart
                    System.out.println("Departure Time: " + plane.timeOfDeparture + " hours"); // output the runway and the departure time of the plane scheduled.
                }
            }
        }
    }

    // A method to print a combined departure calendar, it groups planes based on the hour and runway they are scheduled to depart from, for each hour.
    // This is the second program that airport man wants to see, to understand the schedule.
    public static void displayCalendarRunways(List<Plane> planes) {
        System.out.println("Combined Departure Calendar:");
        for (int hour = 1; hour <= 24; hour++) { // iterate through 24 hours in the day.
            List<Plane> departCurrPlanes = new ArrayList<>(); // Creating a new list of planes to store those that depart at the current hour.
            for (Plane plane : planes) { // iterate through each plane in the list of planes.
                if (plane.timeOfDeparture == hour) { // condition: check if plane is scheduled to depart at this hour.
                    // If so, add to the new list we initialized.
                    departCurrPlanes.add(plane);
                }
            }

            System.out.println("Hour " + hour + ":"); // Print to indicate the current hour.
            for (Plane plane : departCurrPlanes) { // Iterate through the plane in the list of current planes that are scheduled to depart at this hour
                System.out.println("  Runways: " + plane.waysOfDeparture.toString()); // Output the runway at which it is scheduled to depart.
            }
        }
    }

    public static void main(String[] args) {
        int numPlanes = 15; // Number of planes.
        List<Plane> planes = generatePlanes(numPlanes);
        System.out.println("Runways Schedule:");
        displayRunways(planes); // Displays the schedule for runways & calls the method.
        System.out.println(" ");
        displayCalendarRunways(planes); // Displays the Calendar schedule & calls the method.
    }
}
