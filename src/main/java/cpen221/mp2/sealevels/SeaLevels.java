package cpen221.mp2.sealevels;

import cpen221.mp2.graph.ALGraph;
import cpen221.mp2.graph.Edge;
import cpen221.mp2.graph.Vertex;

import java.util.ArrayList;
import java.util.Map;



public class SeaLevels {


     //
     // Rep Invariants:
     //
     //  Valid Water Sources and Terrain Grids:
     //    - The terrain grid and waterSources array must not be null.
     //    - The terrain grid must be a non-empty rectangular 2D array.
     //    - The waterSources array must be a non-empty array of GridLocation objects.
     //

     //
     // Abstraction Function:
     // Represents a system for determining submerged locations and calculating danger levels on a terrain.
     // Operates on a grid of terrain heights, water sources, and a water level.
     //

    /**
     * Determines what locations of a given terrain are submerged at a given water level
     *
     * @param terrain a grid of the heights of locations on a terrain
     * @param waterSources a grid of locations on a terrain
     * @param waterLevel the height of water
     * @return a grid determining if each location is submerged or not
     */
    public static boolean[][] isSubmerged(double[][] terrain,
                                          GridLocation[] waterSources,
                                          double waterLevel) {

        ALGraph<Vertex, Edge<Vertex>> terrainGraph = new ALGraph<>();

        ArrayList<GridLocation> Grid = new ArrayList<>();

        for(int i = 0; i < terrain.length; i++){
            for(int j = 0; j < terrain[i].length; j++){
                Grid.add(new GridLocation(i, j));
                System.out.print(terrain[i][j] + "  ");
            }
            System.out.println();
        }

        for( GridLocation cell : Grid) {
            terrainGraph.addVertex(new Vertex(cell.hashCode(), terrain[cell.row][cell.col], cell.toString()));
        }

        for (GridLocation cell : Grid) {
            for(GridLocation otherCell : Grid) {
                if (!(cell.equals(otherCell)) && (Math.abs(otherCell.col - cell.col) <= 1) && (Math.abs(otherCell.row - cell.row) <= 1)) {
                    Vertex v1 = new Vertex(cell.hashCode(), terrain[cell.row][cell.col], cell.toString());
                    Vertex v2 = new Vertex(otherCell.hashCode(), terrain[otherCell.row][otherCell.col], otherCell.toString());
                    terrainGraph.addEdge(new Edge<>(v1, v2));
                }
            }
        }


        ArrayList<Vertex> submergedList = new ArrayList<>();

        for (GridLocation source : waterSources) {
            submergedList.add(new Vertex(source.hashCode(), terrain[source.row][source.col], source.toString()));
        }
        

        ArrayList<Vertex> submergedTerrain = nextSubmergedList(submergedList, terrainGraph, waterLevel);
        int rows = terrain.length;
        int cols = terrain[0].length;
        boolean[][] isSubmerged = new boolean[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                for( Vertex v : submergedTerrain) {
                    if (v.id() == new GridLocation(row, col).hashCode()) {
                        isSubmerged[row][col] = true;
                    }
                }
                System.out.print(isSubmerged[row][col] + " ");
            }
            System.out.println();
        }
        return isSubmerged;
    }

    /**
     * Returns a list of all the locations that are submerged at a given water level and a list of already submerged locations
     *
     * @param submergedList A list of all submerged locations on a terrain
     * @param terrainGraph  A graph of the locations on the terrain
     * @param waterLevel the height of water
     * @return A list containing all the locations that are submerged
     */
    private static ArrayList<Vertex> nextSubmergedList(ArrayList<Vertex> submergedList, ALGraph<Vertex, Edge<Vertex>> terrainGraph, double waterLevel) {
        ArrayList<Vertex> nextSubmergedList = new ArrayList<>(submergedList);

        for (Vertex v1 : submergedList) {
            Map<Vertex, Edge<Vertex>> neighbours = terrainGraph.getNeighbourhood(v1);
            for (Map.Entry<Vertex, Edge<Vertex>> v2 : neighbours.entrySet()) {
                if(!nextSubmergedList.contains(v2.getKey())) {
                    if (v2.getKey().value() <= waterLevel) {
                        nextSubmergedList.add(v2.getKey());
                    }
                }

            }
        }

        if(!(submergedList.size() == (nextSubmergedList.size()))) {
            return nextSubmergedList(nextSubmergedList, terrainGraph, waterLevel);
        } else {
            return submergedList;
        }
    }


    /**
     * Determines the danger level for each location on the terrain.
     * The danger leve of a location is the water level
     * required for that location to be submerged
     * @param terrain a grid of the heights of locations on a terrain
     * @param waterSources a grid of locations on a terrain
     * @return a grid of the danger level of each location
     */
    public static double[][] dangerLevel(double[][] terrain,
                                         GridLocation[] waterSources) {

        int waterLevel;

        int rows = terrain.length;
        int cols = terrain[0].length;

        double[][] dangerLevel = new double[rows][cols];


        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                waterLevel = -1;
                boolean[][] submergedCells = new boolean[rows][cols];
                while (!submergedCells[i][j]) {
                    waterLevel ++;
                    submergedCells = isSubmerged(terrain, waterSources, waterLevel);
                }

                dangerLevel[i][j] = waterLevel;
            }
        }

        return dangerLevel;
    }
}
