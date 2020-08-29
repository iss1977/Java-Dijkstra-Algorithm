import java.util.ArrayList;

// this class is used to store a found route with the found route.
class PointsDistancesAndRoute extends PointsAndDistances {
    String route;

    PointsDistancesAndRoute (String pctFrom, String pctTo, Integer distance, String route) {
        super(pctFrom, pctTo, distance);
        this.route = route;
    }
}

// this class is used to represint an element in the temporaray array used in dijkstra class.
class PointsDistancesAndCreator extends PointsAndDistances{
    String erzeuger; // speichert einen "PointsAndDistances" datensatz samt erzeuger
    PointsDistancesAndCreator(String pctFrom, String pctTo, Integer distance, String creator) {
        super(pctFrom, pctTo, distance);
        erzeuger=creator;
    }
}


// this classis a blueprint for  an element in the array pathArray
class PointsAndDistances {
    String pctFrom;
    String pctTo;
    Integer distance;

    // constructor
    public PointsAndDistances(String pctFrom, String pctTo, Integer distance){
        this.pctFrom=pctFrom;
        this.pctTo=pctTo;
        this.distance = distance;
    }
}

public class Dijkstra {

    static final String depthsign="░▒▓▒░  ";
    static  boolean switchDisplayCurrArray = false;
    
    static ArrayList<PointsAndDistances> pathArray = new ArrayList<PointsAndDistances>();
    static ArrayList<PointsDistancesAndRoute> foundPathes = new ArrayList<PointsDistancesAndRoute>();

    static final String startingPoint ="Z";
    static final String arriveAtDestination="D";


    // if a route is found, this method adds it to the foundPathes Array.
    public static void addElementToFoundArray(ArrayList<PointsDistancesAndCreator> arr){
    Integer totalDistance=0;
    String routeDescription="";
        StringBuilder sb1 = new StringBuilder();

        for (int i=1; i<=arr.size()-1;i++){  // not using the first row. it contains only starting data for recursive dijkstra.
        //for (PointsDistancesAndCreator arrElement:arr) {
            PointsDistancesAndCreator arrElement = arr.get(i);
            totalDistance+=arrElement.distance;
            sb1.append(arrElement.pctFrom).append("-").append(arrElement.pctTo).append(":").append(arrElement.distance).append((i==arr.size()-1)?" ":"; ");
           // routeDescription+=routeDescription.concat().pctFrom+arrElement.pctTo+arrElement.distance;

    }
Dijkstra.foundPathes.add(new PointsDistancesAndRoute(arr.get(1).pctFrom,arr.get(arr.size()-1).pctTo,totalDistance,sb1.toString()));
}



    // method for initialising pathArray variable
    private static void initPathArray(){
        pathArray.add(new PointsAndDistances("Z","A",2));

        pathArray.add(new PointsAndDistances("A","B",5));
        pathArray.add(new PointsAndDistances("A","C",7));
        pathArray.add(new PointsAndDistances("B","C",1));
        pathArray.add(new PointsAndDistances("C","D",3));
        pathArray.add(new PointsAndDistances("D","E",1));


//        pathArray.add(new PointsAndDistances("A","B",4));
//        pathArray.add(new PointsAndDistances("A","C",3));
//        pathArray.add(new PointsAndDistances("A","D",3));
//        pathArray.add(new PointsAndDistances("B","A",4));
//        pathArray.add(new PointsAndDistances("B","E",2));
//        pathArray.add(new PointsAndDistances("C","A",3));
//        pathArray.add(new PointsAndDistances("C","F",4));
//        pathArray.add(new PointsAndDistances("D","A",3));
//        pathArray.add(new PointsAndDistances("D","E",1));
//        pathArray.add(new PointsAndDistances("D","F",1));
//        pathArray.add(new PointsAndDistances("E","B",2));
//        pathArray.add(new PointsAndDistances("E","D",1));
//        pathArray.add(new PointsAndDistances("E","F",2));
//        pathArray.add(new PointsAndDistances("F","C",4));
//        pathArray.add(new PointsAndDistances("F","E",2));
//        pathArray.add(new PointsAndDistances("F","D",1));
    }

    // display the array with points and distances
    private static void displayPathArray(){
        System.out.println("Array of pathes and distances :");
        System.out.println("-".repeat(44));
        System.out.println("| Starting point | Ending point | Distance |" );
        System.out.println("-".repeat(44));
        for (PointsAndDistances element:pathArray) {
            System.out.printf("|%9s       ",element.pctFrom);
            System.out.printf("|%8s      ",element.pctTo);
            System.out.printf("|%6d    |",element.distance);
            System.out.println();
        }
        System.out.println("-".repeat(44));
    }

    // ------------- MAIN  ----------------------------------------------------------------------
    public static void main(String[] args) {
        initPathArray();
        displayPathArray();
        System.out.println("We are searching for the shortest route from "+startingPoint+" to "+ arriveAtDestination+" using some dijkstra like algorithm :-)\r\n");
        System.out.println("The \" "+depthsign+" \" indentation represents the depth of the recursion. main() is level 0.\r\n");
        ArrayList<PointsDistancesAndCreator> arr = new ArrayList<PointsDistancesAndCreator>();
        arr.add( new PointsDistancesAndCreator("*",startingPoint,0,"*") ) ; // set this at the starting point of the class
        dijkstra(arr,(byte)1);
        displayFoundArray(foundPathes);


    } // ------------- end MAIN  ----------------------------------------------------------------

    // display the array with the found path
    private static void displayArray(ArrayList<PointsDistancesAndCreator> arr, int recLevel){
        System.out.println(depthsign.repeat(recLevel)+"─".repeat(25));
        System.out.println(depthsign.repeat(recLevel)+"START| END |DIST |PARENT");
        for (PointsDistancesAndCreator element: arr) {
            System.out.println(depthsign.repeat(recLevel)+"  "+element.pctFrom + "  |  "+element.pctTo+"  |  "+element.distance+"  |  "+element.erzeuger);
        }
        System.out.println(depthsign.repeat(recLevel)+"̅̅̅̅̅̅̅̅̅̅̅̅̅̅̅̅̅̅̅̅̅̅̅̅");
        switchDisplayCurrArray=false;
    }

    // display the foundarray with the found path
    private static void displayFoundArray(ArrayList<PointsDistancesAndRoute> arr){
        System.out.println("\r\nFound the following routes from "+startingPoint+" to "+arriveAtDestination+" :\r\nSTART| END | TOT DIST |  ROUTE");
        for (PointsDistancesAndRoute element: arr) {
            System.out.println("  "+element.pctFrom + "  |  "+element.pctTo+"  |    "+element.distance+"    |  "+element.route);
        }
        System.out.println("̅̅̅̅̅̅̅̅̅̅̅̅̅̅̅̅̅̅̅̅̅̅̅̅");
    }

    // calculate distance between 2 points
    private static Integer getDistance(String pct_a, String pct_b){
        int toReturn =-1;
        for (PointsAndDistances arrElement: pathArray ) {
            if ((pct_a.equals(arrElement.pctFrom) && pct_b.equals(arrElement.pctTo)) ||
                    (pct_b.equals(arrElement.pctFrom) && pct_a.equals(arrElement.pctTo)))
                toReturn= arrElement.distance;
        }
        return toReturn;
    }

    // ------- The dijkstra thing -------------------------------------------
    private static void dijkstra(ArrayList<PointsDistancesAndCreator> arr, byte recLevel){
    // ----------------------------------------------------------------------

        // first, we check if we arrived at destination
        // the last point of arrival ist the last element in the arr array
        String lastValidPoint =  arr.get(arr.size()-1).pctTo;
        System.out.println(depthsign.repeat(recLevel)+"START dijikstra Parent="+lastValidPoint+" current array:");
        System.out.println(depthsign.repeat(recLevel)+"Last valid point ( last element in array is :"+lastValidPoint);

        // log
        displayArray(arr,recLevel);
        if (lastValidPoint.equals(arriveAtDestination)){
            // write the path to foundPathes array
                addElementToFoundArray(arr);
                System.out.println(depthsign.repeat(recLevel)+"►►►"+"!!! ROUTE FOUND !!!  Added to foundArray. Skiping route search from last point.. ("+lastValidPoint+")");
                switchDisplayCurrArray=true;
                // we end dijkstra. We skip searching for other routes
        }else {

            // get all possible points starting from "lastPoint" using the pathArray class member
            System.out.print(depthsign.repeat(recLevel) +"->Following routes are posible from point "+lastValidPoint+" :");
            for (PointsAndDistances arrElement : pathArray) {
                if (arrElement.pctFrom.equals(lastValidPoint)) {
                    //String nextPossiblePoint = arrElement.pctTo;
                    System.out.print("("+arrElement.pctFrom+"-"+arrElement.pctTo+":"+arrElement.distance+")");
                }
            }
            System.out.println("\r\n"+depthsign.repeat(recLevel) +"->Looping through the possible routes. Each next possible route is checked if already used :  ");
            for (int i=0;i<= pathArray.size()-1;i++){
            //for (PointsAndDistances arrElement : pathArray) {
                PointsAndDistances arrElement=pathArray.get(i);

                if (arrElement.pctFrom.equals(lastValidPoint)) {
                    String nextPossiblePoint = arrElement.pctTo;
                    System.out.print(depthsign.repeat(recLevel) + "Found a next possible point:" + nextPossiblePoint);
                    // now we have the next  possible element, we must check if the route is already used.
                    if (switchDisplayCurrArray) displayArray(arr, recLevel);

                    boolean nextPointPossible = true;
                    for (PointsAndDistances elementOfArray : arr) {
                        if (elementOfArray.pctFrom.equals(nextPossiblePoint) && elementOfArray.pctTo.equals(lastValidPoint)) {
                            // if true, the combination lastValidPoint -> nextPoint ist not possible , while already used.
//                        System.out.println("* route not possible:"+elementOfArray.pctFrom + "-" + elementOfArray.pctTo+" already used");
                            nextPointPossible = false;
//                        break; // IntelliJ told me so ...
                        }
                    }

                    System.out.println((!nextPointPossible) ? " - route  already used." : " - OK. route was never used. (" + lastValidPoint + "->" + nextPossiblePoint + ") Ready to be used to call dijkstra.");
                    // we know now, if nextPointPossible is true, the new point is OK.
                    // calculate the distance and the new element to the array

                    if (nextPointPossible) {
                        System.out.println(depthsign.repeat(recLevel) + "Following element will be added to the current path array : " + lastValidPoint + "->" + nextPossiblePoint + ":" + getDistance(lastValidPoint, nextPossiblePoint) + " parent:" + lastValidPoint);
                        arr.add(new PointsDistancesAndCreator(lastValidPoint, nextPossiblePoint, getDistance(lastValidPoint, nextPossiblePoint), lastValidPoint));

                        // call dijkstra
                        System.out.println(depthsign.repeat(recLevel) + "Calling dijkstra ->");
                        recLevel++;
                        dijkstra(new ArrayList<PointsDistancesAndCreator>(arr), (recLevel));
                        recLevel--;
                        arr.remove(arr.size()-1);
                        System.out.println(depthsign.repeat(recLevel) + "info: recursive method just ended.");
                        displayArray(arr,recLevel);
                        //System.out.println(depthsign.repeat(recLevel) + "Current arrElement is:"+arrElement.pctFrom+"-"+arrElement.pctTo);


                    }
                }
            }
        }
    System.out.println(depthsign.repeat(recLevel) + "exiting Dijkstra (end of method).");

    } // end  methode dijkstra

}// end class Dijkstra
