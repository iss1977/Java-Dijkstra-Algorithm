import java.util.ArrayList;

// this classis a blueprint for  an element in the array pathArray
class PointsAndDistances{
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

    static ArrayList<PointsAndDistances> pathArray = new ArrayList<PointsAndDistances>();

    static String arriveAtDestination="D";

    // method for initialising pathArray variable
    private static void initPathArray(){
        pathArray.add(new PointsAndDistances("A","B",4));
        pathArray.add(new PointsAndDistances("A","C",3));
        pathArray.add(new PointsAndDistances("A","D",3));
        pathArray.add(new PointsAndDistances("B","A",4));
        pathArray.add(new PointsAndDistances("B","E",2));
        pathArray.add(new PointsAndDistances("C","A",3));
        pathArray.add(new PointsAndDistances("C","F",4));
        pathArray.add(new PointsAndDistances("D","A",3));
        pathArray.add(new PointsAndDistances("D","E",1));
        pathArray.add(new PointsAndDistances("D","F",1));
        pathArray.add(new PointsAndDistances("E","B",2));
        pathArray.add(new PointsAndDistances("E","D",1));
        pathArray.add(new PointsAndDistances("E","F",2));
        pathArray.add(new PointsAndDistances("F","C",4));
        pathArray.add(new PointsAndDistances("F","E",2));
        pathArray.add(new PointsAndDistances("F","D",1));
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
        System.out.println("We are searching for the shortest route to the element "+ arriveAtDestination);
        ArrayList<PointsAndDistances> arr = new ArrayList<PointsAndDistances>();
        arr.add( new PointsAndDistances("-","A",0) ) ; // wir starten mit punkt "A"
        dijkstra(arr);


    } // ------------- end MAIN  ----------------------------------------------------------------

    // display the array with the found path
    private static void displayArray(ArrayList<PointsAndDistances> arr){
        for (PointsAndDistances element: arr) {
            System.out.println(element.pctFrom + "-"+element.pctTo+",d:"+element.distance);
        }
        System.out.println("------");
    }


    // calculate distance between 2 points
    private static Integer getDistance(String pct_a, String pct_b){
        int toReturn =-1;
        for (PointsAndDistances arrElement: pathArray ) {
            if ((pct_a.equals(arrElement.pctFrom) && pct_b.equals(arrElement.pctTo)) ||
                    (pct_b.equals(arrElement.pctFrom) && pct_a.equals(arrElement.pctTo)))
                toReturn= arrElement.distance;
        }
        System.out.println("Distance calculated:"+pct_a+"-"+pct_b+"="+toReturn);
        return toReturn;
    }

    // ------- The dijkstra thing --------------------------
    private static void dijkstra(ArrayList<PointsAndDistances> arr){

        displayArray(arr);



        // get the last point from the array
        String lastValidPoint =  arr.get(arr.size()-1).pctTo;
        System.out.println("*last valid point in dijkstra:"+lastValidPoint);

        // get all possible points starting from "lastPoint"
        for (PointsAndDistances arrElement:pathArray ) {
            if (arrElement.pctFrom.equals(lastValidPoint)){
                String nextPossiblePoint = arrElement.pctTo;
                // now we have the next  possible element, we must check if the route is already used.
                System.out.print("Next posible value is:"+nextPossiblePoint);

                boolean nextPointPossible = true;
                for (   PointsAndDistances elementOfArray : arr) {
                    if      ((elementOfArray.pctFrom.equals(lastValidPoint) && elementOfArray.pctTo.equals(nextPossiblePoint)) ||
                            (elementOfArray.pctFrom.equals(nextPossiblePoint) && elementOfArray.pctTo.equals(lastValidPoint))) {
                        // if true, the combination lastValidPoint -> nextPoint ist not possible , while already used.
//                        System.out.println("* route not posible:"+elementOfArray.pctFrom + "-" + elementOfArray.pctTo+" already used");
                        nextPointPossible = false;
                    }
                }

                System.out.println((!nextPointPossible)?" - not suitable":" - point usable");
                // we know now, if nextPointPossible is true, the new point is OK.
                // calculate the distance and the new element to the array

                if (nextPointPossible) {


                    // check if we have already arrived or not :
                    if (nextPossiblePoint.equals(arriveAtDestination)) {


                        System.out.println("\r\n---------------------\r\nArrived to destination. Route is:");
                        displayArray(arr);
                        System.out.println("+" + lastValidPoint + "->" + nextPossiblePoint);

                    }else{

                        System.out.println("Added to current arr:" + lastValidPoint + "->" + nextPossiblePoint);
                        arr.add(new PointsAndDistances(lastValidPoint, nextPossiblePoint, getDistance(lastValidPoint, nextPossiblePoint)));

                    }

                    // call dijkstra again, other routes are maybe possible
                    dijkstra(arr);

                }

            }
        }

    } // end  methode dijkstra


}// end class Dijkstra
