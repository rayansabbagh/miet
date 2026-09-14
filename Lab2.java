import java.util.ArrayList;
import java.util.Scanner;

enum DroneType {
    LONG_RANGE,
    INTERCEPTOR,
    RECONNAISSANCE,
    CARGO,
    MULTIPURPOSE
}

enum TargetType {
    TANK,
    AIR_DEFENSE_PICKUP,
    SUPPLY_PICKUP,
    SOLDIER,
    EARTH_SHELTER,
    CONCRETE_SHELTER,
    STRIKE_DRONE
}


// ==================== DRONE ====================

class Drone {

    private String name;
    private DroneType modelType;

    // 12 parameters
    private double cruiseSpeed;
    private double maxSpeed;
    private double fuelRange;
    private double controlRange;
    private double flightTime;
    private double droneCost;
    private double preparationCost;
    private boolean reusable;
    private double normalPayload;
    private double maxPayload;
    private double maxAltitude;
    private double preparationTime;

    // Maximum 3 priorities
    private DroneType firstPriority;
    private DroneType secondPriority;
    private DroneType thirdPriority;

    public Drone(
            String name,
            DroneType modelType,
            double cruiseSpeed,
            double maxSpeed,
            double fuelRange,
            double controlRange,
            double flightTime,
            double droneCost,
            double preparationCost,
            boolean reusable,
            double normalPayload,
            double maxPayload,
            double maxAltitude,
            double preparationTime) {

        this.name = name;
        this.modelType = modelType;
        this.cruiseSpeed = cruiseSpeed;
        this.maxSpeed = maxSpeed;
        this.fuelRange = fuelRange;
        this.controlRange = controlRange;
        this.flightTime = flightTime;
        this.droneCost = droneCost;
        this.preparationCost = preparationCost;
        this.reusable = reusable;
        this.normalPayload = normalPayload;
        this.maxPayload = maxPayload;
        this.maxAltitude = maxAltitude;
        this.preparationTime = preparationTime;
    }

    public String getName() {
        return name;
    }

    public DroneType getModelType() {
        return modelType;
    }

    public double getCruiseSpeed() {
        return cruiseSpeed;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public double getFuelRange() {
        return fuelRange;
    }

    public double getControlRange() {
        return controlRange;
    }

    public double getFlightTime() {
        return flightTime;
    }

    public double getDroneCost() {
        return droneCost;
    }

    public double getPreparationCost() {
        return preparationCost;
    }

    public boolean isReusable() {
        return reusable;
    }

    public double getNormalPayload() {
        return normalPayload;
    }

    public double getMaxPayload() {
        return maxPayload;
    }

    public double getMaxAltitude() {
        return maxAltitude;
    }

    public double getPreparationTime() {
        return preparationTime;
    }

    public DroneType getFirstPriority() {
        return firstPriority;
    }

    public DroneType getSecondPriority() {
        return secondPriority;
    }

    public DroneType getThirdPriority() {
        return thirdPriority;
    }

    public void setPriorities(
            DroneType first,
            DroneType second,
            DroneType third) {

        firstPriority = first;
        secondPriority = second;
        thirdPriority = third;
    }


    // Range decreases when speed and payload increase
    public double getEffectiveRange(
            double speed,
            double payload) {

        double speedPart = 0;

        if (maxSpeed > cruiseSpeed) {

            speedPart =
                    (speed - cruiseSpeed)
                    / (maxSpeed - cruiseSpeed);
        }

        if (speedPart < 0) {
            speedPart = 0;
        }

        if (speedPart > 1) {
            speedPart = 1;
        }


        double payloadPart = 0;

        if (maxPayload > normalPayload) {

            payloadPart =
                    (payload - normalPayload)
                    / (maxPayload - normalPayload);
        }

        if (payloadPart < 0) {
            payloadPart = 0;
        }

        if (payloadPart > 1) {
            payloadPart = 1;
        }


        double speedFactor =
                1.0 - 0.40 * speedPart;

        double payloadFactor =
                1.0 - 0.30 * payloadPart;

        return fuelRange
                * speedFactor
                * payloadFactor;
    }


    // Flight time also decreases
    public double getEffectiveFlightTime(
            double speed,
            double payload) {

        double speedPart = 0;

        if (maxSpeed > cruiseSpeed) {

            speedPart =
                    (speed - cruiseSpeed)
                    / (maxSpeed - cruiseSpeed);
        }

        if (speedPart < 0) {
            speedPart = 0;
        }

        if (speedPart > 1) {
            speedPart = 1;
        }


        double payloadPart = 0;

        if (maxPayload > normalPayload) {

            payloadPart =
                    (payload - normalPayload)
                    / (maxPayload - normalPayload);
        }

        if (payloadPart < 0) {
            payloadPart = 0;
        }

        if (payloadPart > 1) {
            payloadPart = 1;
        }


        double speedFactor =
                1.0 - 0.35 * speedPart;

        double payloadFactor =
                1.0 - 0.25 * payloadPart;

        return flightTime
                * speedFactor
                * payloadFactor;
    }


    // Cost criterion for each parameter

    public double getCruiseSpeedCost() {
        return cruiseSpeed * 10;
    }

    public double getMaxSpeedCost() {
        return maxSpeed * 15;
    }

    public double getFuelRangeCost() {
        return fuelRange * 5;
    }

    public double getControlRangeCost() {
        return controlRange * 4;
    }

    public double getFlightTimeCost() {
        return flightTime * 100;
    }

    public double getDroneParameterCost() {
        return droneCost;
    }

    public double getPreparationParameterCost() {
        return preparationCost;
    }

    public double getReusableCost() {

        if (reusable) {
            return 5000;
        }

        return 0;
    }

    public double getNormalPayloadCost() {
        return normalPayload * 50;
    }

    public double getMaxPayloadCost() {
        return maxPayload * 80;
    }

    public double getAltitudeCost() {
        return maxAltitude * 2;
    }

    public double getPreparationTimeCost() {
        return preparationTime * 20;
    }


    public double calculateTechnicalCost() {

        return getCruiseSpeedCost()
                + getMaxSpeedCost()
                + getFuelRangeCost()
                + getControlRangeCost()
                + getFlightTimeCost()
                + getDroneParameterCost()
                + getPreparationParameterCost()
                + getReusableCost()
                + getNormalPayloadCost()
                + getMaxPayloadCost()
                + getAltitudeCost()
                + getPreparationTimeCost();
    }


    public void printParameterCosts() {

        System.out.println("Parameter costs:");

        System.out.println(
                "Cruise speed cost: "
                        + getCruiseSpeedCost());

        System.out.println(
                "Max speed cost: "
                        + getMaxSpeedCost());

        System.out.println(
                "Fuel range cost: "
                        + getFuelRangeCost());

        System.out.println(
                "Control range cost: "
                        + getControlRangeCost());

        System.out.println(
                "Flight time cost: "
                        + getFlightTimeCost());

        System.out.println(
                "Drone cost: "
                        + getDroneParameterCost());

        System.out.println(
                "Preparation cost: "
                        + getPreparationParameterCost());

        System.out.println(
                "Reusable cost: "
                        + getReusableCost());

        System.out.println(
                "Normal payload cost: "
                        + getNormalPayloadCost());

        System.out.println(
                "Max payload cost: "
                        + getMaxPayloadCost());

        System.out.println(
                "Altitude cost: "
                        + getAltitudeCost());

        System.out.println(
                "Preparation time cost: "
                        + getPreparationTimeCost());
    }


    @Override
    public String toString() {

        return "\nDrone: " + name
                + "\nModel type: " + modelType
                + "\nCruise speed: " + cruiseSpeed
                + "\nMax speed: " + maxSpeed
                + "\nFuel range: " + fuelRange
                + "\nControl range: " + controlRange
                + "\nFlight time: " + flightTime
                + "\nDrone cost: " + droneCost
                + "\nPreparation cost: " + preparationCost
                + "\nReusable: " + reusable
                + "\nNormal payload: " + normalPayload
                + "\nMax payload: " + maxPayload
                + "\nMax altitude: " + maxAltitude
                + "\nPreparation time: " + preparationTime
                + "\nPriority 1: " + firstPriority
                + "\nPriority 2: " + secondPriority
                + "\nPriority 3: " + thirdPriority;
    }
}


// ==================== CLASSIFIER ====================

class DroneClassifier {

    public static void classify(Drone drone) {

        DroneType[] types =
                DroneType.values();

        double[] scores =
                new double[types.length];

        for (int i = 0;
             i < types.length;
             i++) {

            scores[i] =
                    getScore(
                            drone,
                            types[i]);
        }


        // Simple sorting
        for (int i = 0;
             i < scores.length - 1;
             i++) {

            for (int j = i + 1;
                 j < scores.length;
                 j++) {

                if (scores[j]
                        > scores[i]) {

                    double tempScore =
                            scores[i];

                    scores[i] =
                            scores[j];

                    scores[j] =
                            tempScore;


                    DroneType tempType =
                            types[i];

                    types[i] =
                            types[j];

                    types[j] =
                            tempType;
                }
            }
        }


        drone.setPriorities(
                types[0],
                types[1],
                types[2]);
    }


    private static double getScore(
            Drone drone,
            DroneType type) {

        double score = 0;


        switch (type) {

            case LONG_RANGE:

                score +=
                        drone.getFuelRange()
                                / 10;

                score +=
                        drone.getControlRange()
                                / 10;

                score +=
                        drone.getFlightTime()
                                * 10;

                if (drone.isReusable()) {
                    score += 10;
                }

                break;


            case INTERCEPTOR:

                score +=
                        drone.getMaxSpeed()
                                / 5;

                score +=
                        drone.getCruiseSpeed()
                                / 10;

                if (drone.getPreparationTime()
                        < 20) {

                    score += 20;
                }

                break;


            case RECONNAISSANCE:

                score +=
                        drone.getFlightTime()
                                * 15;

                score +=
                        drone.getMaxAltitude()
                                / 100;

                score +=
                        drone.getControlRange()
                                / 10;

                if (drone.getNormalPayload()
                        < 20) {

                    score += 10;
                }

                break;


            case CARGO:

                score +=
                        drone.getMaxPayload()
                                * 5;

                score +=
                        drone.getFuelRange()
                                / 20;

                if (drone.isReusable()) {
                    score += 20;
                }

                break;


            case MULTIPURPOSE:

                score +=
                        drone.getCruiseSpeed()
                                / 20;

                score +=
                        drone.getFuelRange()
                                / 20;

                score +=
                        drone.getFlightTime()
                                * 5;

                score +=
                        drone.getMaxPayload();

                if (drone.isReusable()) {
                    score += 15;
                }

                break;
        }

        return score;
    }
}


// ==================== DATABASE ====================

class DroneDatabase {

    private ArrayList<Drone> drones;

    public DroneDatabase() {

        drones =
                new ArrayList<Drone>();
    }


    public void addDrone(
            Drone drone) {

        DroneClassifier.classify(drone);

        drones.add(drone);

        System.out.println(
                "Drone "
                        + drone.getName()
                        + " added.");

        System.out.println(
                "Priority 1: "
                        + drone.getFirstPriority());

        System.out.println(
                "Priority 2: "
                        + drone.getSecondPriority());

        System.out.println(
                "Priority 3: "
                        + drone.getThirdPriority());
    }


    public int size() {
        return drones.size();
    }


    public int countByModelType(
            DroneType type) {

        int count = 0;

        for (Drone drone : drones) {

            if (drone.getModelType()
                    == type) {

                count++;
            }
        }

        return count;
    }


    public int countByFirstPriority(
            DroneType type) {

        int count = 0;

        for (Drone drone : drones) {

            if (drone.getFirstPriority()
                    == type) {

                count++;
            }
        }

        return count;
    }


    public void printDatabase() {

        for (Drone drone : drones) {

            System.out.println(drone);

            System.out.println(
                    "Technical cost: "
                            + drone
                            .calculateTechnicalCost());

            System.out.println(
                    "-----------------------");
        }
    }
}


// ==================== TARGET ====================

class Target {

    private TargetType type;

    private int x;
    private int y;

    private boolean moving;
    private boolean active;


    public Target(
            TargetType type,
            int x,
            int y,
            boolean moving) {

        this.type = type;
        this.x = x;
        this.y = y;
        this.moving = moving;
        this.active = true;
    }


    public TargetType getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isMoving() {
        return moving;
    }

    public boolean isActive() {
        return active;
    }


    public void deactivate() {
        active = false;
    }


    public double distanceToBase() {

        int baseX = 50;
        int baseY = 1;

        int dx =
                x - baseX;

        int dy =
                y - baseY;

        return Math.sqrt(
                dx * dx
                        + dy * dy);
    }


    public void moveToBase() {

        if (!moving
                || !active) {

            return;
        }


        if (x < 50) {

            x++;

        } else if (x > 50) {

            x--;
        }


        if (y > 1) {
            y--;
        }
    }


    @Override
    public String toString() {

        return type
                + " ("
                + x
                + ", "
                + y
                + ") distance="
                + String.format(
                "%.2f",
                distanceToBase());
    }
}


// ==================== FIELD ====================

class BattleField {

    public static final int SIZE = 100;

    public static final int BASE_X = 50;
    public static final int BASE_Y = 1;

    private ArrayList<Target> targets;


    public BattleField() {

        targets =
                new ArrayList<Target>();
    }


    public ArrayList<Target>
    getTargets() {

        return targets;
    }


    public void generateTargets() {

        targets.clear();

        // From 5 to 30 targets
        int count =
                5
                        + (int)
                        (Math.random()
                                * 26);


        for (int i = 0;
             i < count;
             i++) {

            TargetType[] types =
                    TargetType.values();

            TargetType type =
                    types[
                            (int)
                                    (Math.random()
                                            * types.length)
                            ];


            int x;
            int y;


            do {

                x =
                        1
                                + (int)
                                (Math.random()
                                        * 100);

                /*
                 * More targets are generated
                 * farther from the base.
                 */

                double random =
                        Math.sqrt(
                                Math.random());

                y =
                        10
                                + (int)
                                (random * 90);

                if (y > 100) {
                    y = 100;
                }

            } while (
                    distanceToBase(
                            x,
                            y) < 10
            );


            boolean moving =
                    type
                            == TargetType.TANK

                            || type
                            == TargetType.SOLDIER

                            || type
                            == TargetType.STRIKE_DRONE;


            Target target =
                    new Target(
                            type,
                            x,
                            y,
                            moving);

            targets.add(target);
        }
    }


    private double distanceToBase(
            int x,
            int y) {

        int dx =
                x - BASE_X;

        int dy =
                y - BASE_Y;

        return Math.sqrt(
                dx * dx
                        + dy * dy);
    }


    public void moveTargets() {

        for (Target target :
                targets) {

            target.moveToBase();
        }
    }


    public void printTargets() {

        System.out.println();

        System.out.println(
                "ACTIVE OBJECTS:");

        for (Target target :
                targets) {

            if (target.isActive()) {

                System.out.println(
                        target);
            }
        }
    }
}


// ==================== SCOUT ====================

class ScoutDrone {

    private boolean active;

    private int lifeSteps;

    private int restartTime;


    public ScoutDrone() {

        active = true;

        lifeSteps =
                3
                        + (int)
                        (Math.random()
                                * 4);

        restartTime = 0;
    }


    public boolean isActive() {
        return active;
    }


    public void nextStep() {

        if (active) {

            lifeSteps--;


            if (lifeSteps <= 0) {

                active = false;

                restartTime = 2;

                System.out.println(
                        "Scout drone is unavailable.");
            }

        } else {

            restartTime--;


            if (restartTime <= 0) {

                active = true;

                lifeSteps =
                        3
                                + (int)
                                (Math.random()
                                        * 4);

                System.out.println(
                        "New scout drone started.");
            }
        }
    }
}


// ==================== OBJECT PROCESSOR ====================

class TargetProcessor {

    public static DroneType
    selectDroneType(
            Target target) {

        switch (target.getType()) {

            case TANK:

                return DroneType.LONG_RANGE;


            case AIR_DEFENSE_PICKUP:

                return DroneType.INTERCEPTOR;


            case SUPPLY_PICKUP:

                return DroneType.MULTIPURPOSE;


            case SOLDIER:

                return DroneType.RECONNAISSANCE;


            case EARTH_SHELTER:

                return DroneType.MULTIPURPOSE;


            case CONCRETE_SHELTER:

                return DroneType.LONG_RANGE;


            case STRIKE_DRONE:

                return DroneType.INTERCEPTOR;
        }


        return DroneType.MULTIPURPOSE;
    }


    public static int requiredDrones(
            TargetType type) {

        /*
         * Abstract values for simulation.
         */

        switch (type) {

            case TANK:
                return 3;

            case AIR_DEFENSE_PICKUP:
                return 2;

            case SUPPLY_PICKUP:
                return 1;

            case SOLDIER:
                return 1;

            case EARTH_SHELTER:
                return 2;

            case CONCRETE_SHELTER:
                return 3;

            case STRIKE_DRONE:
                return 2;
        }

        return 1;
    }
}


// ==================== SIMULATION ====================

class Simulation {

    private BattleField field;

    private ScoutDrone scout;

    private DroneDatabase database;

    private int step;

    private int baseHealth;


    public Simulation(
            DroneDatabase database) {

        this.database =
                database;

        field =
                new BattleField();

        scout =
                new ScoutDrone();

        step = 0;

        baseHealth = 100;
    }


    public void run(
            int steps) {

        field.generateTargets();


        for (int i = 0;
             i < steps;
             i++) {

            step++;


            System.out.println();

            System.out.println(
                    "========================");

            System.out.println(
                    "STEP " + step);

            System.out.println(
                    "========================");


            scout.nextStep();


            field.moveTargets();


            if (scout.isActive()) {

                System.out.println(
                        "Scout information: CURRENT");

                processTargets();

            } else {

                System.out.println(
                        "Scout information: OUTDATED");

                System.out.println(
                        "Objects are not processed.");
            }


            field.printTargets();


            checkBase();


            System.out.println(
                    "Base health: "
                            + baseHealth);


            if (baseHealth <= 0) {

                System.out.println(
                        "Simulation finished.");

                break;
            }


            // New group every 3 steps
            if (step % 3 == 0) {

                System.out.println();

                System.out.println(
                        "New objects appeared.");

                field.generateTargets();
            }
        }
    }


    private void processTargets() {

        for (Target target :
                field.getTargets()) {

            if (!target.isActive()) {
                continue;
            }


            DroneType type =
                    TargetProcessor
                            .selectDroneType(
                                    target);


            int required =
                    TargetProcessor
                            .requiredDrones(
                                    target.getType());


            int available =
                    database
                            .countByFirstPriority(
                                    type);


            System.out.println();

            System.out.println(
                    "Object: "
                            + target.getType());

            System.out.println(
                    "Position: "
                            + target.getX()
                            + ":"
                            + target.getY());

            System.out.println(
                    "Selected drone type: "
                            + type);

            System.out.println(
                    "Required drones: "
                            + required);

            System.out.println(
                    "Available drones: "
                            + available);


            if (available >= required) {

                target.deactivate();

                System.out.println(
                        "Object processed.");

            } else {

                System.out.println(
                        "Not enough drones.");
            }
        }
    }


    private void checkBase() {

        for (Target target :
                field.getTargets()) {

            if (!target.isActive()) {
                continue;
            }


            double distance =
                    target.distanceToBase();


            if (distance <= 5) {

                System.out.println(
                        "WARNING: "
                                + target.getType()
                                + " is near the base.");

                // Abstract simulation damage
                baseHealth -= 10;

                target.deactivate();
            }
        }
    }
}


// ==================== MAIN ====================

public class Main {

    public static void main(
            String[] args) {

        DroneDatabase database =
                new DroneDatabase();


        createInitialDatabase(
                database);


        System.out.println();

        System.out.println(
                "Initial database: "
                        + database.size()
                        + " drones.");


        printModelStatistics(
                database);


        Scanner scanner =
                new Scanner(System.in);


        System.out.println();

        System.out.println(
                "========================");

        System.out.println(
                "ADD NEW DRONE");

        System.out.println(
                "========================");


        System.out.print(
                "Name: ");

        String name =
                scanner.nextLine();


        System.out.print(
                "Cruise speed: ");

        double cruiseSpeed =
                scanner.nextDouble();


        System.out.print(
                "Max speed: ");

        double maxSpeed =
                scanner.nextDouble();


        System.out.print(
                "Fuel range: ");

        double fuelRange =
                scanner.nextDouble();


        System.out.print(
                "Control range: ");

        double controlRange =
                scanner.nextDouble();


        System.out.print(
                "Flight time: ");

        double flightTime =
                scanner.nextDouble();


        System.out.print(
                "Drone cost: ");

        double droneCost =
                scanner.nextDouble();


        System.out.print(
                "Preparation cost: ");

        double preparationCost =
                scanner.nextDouble();


        System.out.print(
                "Reusable (true/false): ");

        boolean reusable =
                scanner.nextBoolean();


        System.out.print(
                "Normal payload: ");

        double normalPayload =
                scanner.nextDouble();


        System.out.print(
                "Max payload: ");

        double maxPayload =
                scanner.nextDouble();


        System.out.print(
                "Max altitude: ");

        double maxAltitude =
                scanner.nextDouble();


        System.out.print(
                "Preparation time: ");

        double preparationTime =
                scanner.nextDouble();


        /*
         * New drone does not have
         * a fixed model type.
         */

        Drone newDrone =
                new Drone(
                        name,
                        null,
                        cruiseSpeed,
                        maxSpeed,
                        fuelRange,
                        controlRange,
                        flightTime,
                        droneCost,
                        preparationCost,
                        reusable,
                        normalPayload,
                        maxPayload,
                        maxAltitude,
                        preparationTime);


        System.out.println();

        database.addDrone(
                newDrone);


        System.out.println();

        System.out.println(
                "CLASSIFICATION RESULT:");

        System.out.println(
                newDrone);


        System.out.println();

        newDrone.printParameterCosts();


        double rangeAtMax =
                newDrone
                        .getEffectiveRange(
                                maxSpeed,
                                maxPayload);


        double timeAtMax =
                newDrone
                        .getEffectiveFlightTime(
                                maxSpeed,
                                maxPayload);


        System.out.println();

        System.out.println(
                "Range at max speed and payload: "
                        + rangeAtMax);


        System.out.println(
                "Flight time at max speed and payload: "
                        + timeAtMax);


        System.out.println(
                "Total technical cost: "
                        + newDrone
                        .calculateTechnicalCost());


        System.out.println();

        System.out.println(
                "========================");

        System.out.println(
                "SIMULATION");

        System.out.println(
                "========================");


        Simulation simulation =
                new Simulation(
                        database);


        simulation.run(10);


        scanner.close();
    }


    // Create minimum 10 drones of each type

    private static void
    createInitialDatabase(
            DroneDatabase database) {


        // LONG RANGE

        for (int i = 1;
             i <= 10;
             i++) {

            Drone drone =
                    new Drone(
                            "LongRange-" + i,
                            DroneType.LONG_RANGE,
                            120,
                            180,
                            1200,
                            1000,
                            15,
                            50000,
                            2000,
                            true,
                            15,
                            25,
                            7000,
                            30);

            database.addDrone(
                    drone);
        }


        // INTERCEPTOR

        for (int i = 1;
             i <= 10;
             i++) {

            Drone drone =
                    new Drone(
                            "Interceptor-" + i,
                            DroneType.INTERCEPTOR,
                            250,
                            450,
                            400,
                            300,
                            4,
                            60000,
                            2500,
                            true,
                            10,
                            15,
                            6000,
                            10);

            database.addDrone(
                    drone);
        }


        // RECONNAISSANCE

        for (int i = 1;
             i <= 10;
             i++) {

            Drone drone =
                    new Drone(
                            "Recon-" + i,
                            DroneType.RECONNAISSANCE,
                            100,
                            150,
                            800,
                            700,
                            20,
                            40000,
                            1500,
                            true,
                            8,
                            12,
                            9000,
                            20);

            database.addDrone(
                    drone);
        }


        // CARGO

        for (int i = 1;
             i <= 10;
             i++) {

            Drone drone =
                    new Drone(
                            "Cargo-" + i,
                            DroneType.CARGO,
                            90,
                            130,
                            600,
                            400,
                            8,
                            70000,
                            3000,
                            true,
                            80,
                            150,
                            4000,
                            40);

            database.addDrone(
                    drone);
        }


        // MULTIPURPOSE

        for (int i = 1;
             i <= 10;
             i++) {

            Drone drone =
                    new Drone(
                            "Multi-" + i,
                            DroneType.MULTIPURPOSE,
                            150,
                            250,
                            700,
                            600,
                            10,
                            55000,
                            2200,
                            true,
                            35,
                            60,
                            6500,
                            25);

            database.addDrone(
                    drone);
        }
    }


    private static void
    printModelStatistics(
            DroneDatabase database) {

        System.out.println();

        System.out.println(
                "DRONE DATABASE:");

        for (DroneType type :
                DroneType.values()) {

            System.out.println(
                    type
                            + ": "
                            + database
                            .countByModelType(
                                    type));
        }
    }
}
