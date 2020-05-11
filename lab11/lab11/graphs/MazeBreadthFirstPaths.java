package lab11.graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
//    private int sX;
//    private int sY;
//    private int tX;
//    private int tY;
//    private int N;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /**
     * Conducts a breadth first search of the maze starting at the source.
     */
    private void bfs() {
        // DONE: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        announce();
        Queue<Integer> q = new LinkedList<>();
        q.offer(s);
        while (!q.isEmpty()) {
            int v = q.poll();
            for (int w : maze.adj(v)) {
                if (!marked[w]&&!targetFound) {
                    edgeTo[w] = v;
                    marked[w] = true;
                    announce();
                    distTo[w] = distTo[v] + 1;
                    q.offer(w);
                    if (w == t) {
                        targetFound = true;
                    }
                }
            }
        }
    }

    @Override
    public void solve() {
        bfs();
    }
}

