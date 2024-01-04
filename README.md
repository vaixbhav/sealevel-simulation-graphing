
# Sealevel Simulation Using Graphing

Graphs implemented to model terrains and predict the impact of sea level rise. Terrains represented as two-dimensional grids of `double`s, with each `double` value representing elevations (altitudes) of a particular region on earth. Higher values indicate higher altitude so that a model of the flow of water in these terrains can be achieved. 

Water sources are represented by the `GridLocation` type (essentially, row and column indices for the water sources) and the water level represents the height of the water at the sources (identical across all the sources in that terrain).
Graph abstraction data type implemented to determine minimum water level that would flood a cell in the terrain/grid, applying Dijkstra's algorithm.
You should use the graph abstraction you have developed earlier to achieve this goal.

