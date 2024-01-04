package cpen221.mp2;

import cpen221.mp2.sealevels.GridLocation;
import cpen221.mp2.sealevels.SeaLevels;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class seaLevelTests {
    
    @Test
    public void testSubmerged(){
        double[][] terrain = {{0, 1, 2, 3},
                              {-1, 2, -1, 4},
                              {-1, 2, -1, 3},
                              {-1, 0, 0, -1}
                            };


        GridLocation loc1 = new GridLocation(0, 0);
        GridLocation loc2 = new GridLocation(3, 1);
        GridLocation[] waterSources = {loc1, loc2}; 

        double waterLevel = 0; 

        boolean[][] exp = {{true, false, false, false},
                           {true, false, true, false},
                           {true, false, true, false},
                           {true, true,  true, true},
                           };

        
        double[][] exp2 = {{0, 1, 2, 3},
                           {0, 2, 0, 4},
                           {0, 2, 0, 3},
                           {0, 0, 0, 0},
                           };
        
        assertArrayEquals(exp, SeaLevels.isSubmerged(terrain, waterSources, waterLevel));
        assertArrayEquals(exp2, SeaLevels.dangerLevel(terrain, waterSources));
    }
}
