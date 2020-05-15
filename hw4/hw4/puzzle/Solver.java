package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;

public class Solver {
    private int curMoves;
    private SearchNode goalNode;

    /**
     * Create a SearchNode class to store a word with some info
     * to be compared in MinPQ, should override compareTo() method
     */
    private class SearchNode implements Comparable {
        private WorldState thisWord;
        private int moveNum; //has moved
        private SearchNode prev; //points to the previous SearchNode
        private int toGoalNum; //will move

        public SearchNode(WorldState ws, int m, SearchNode p) {
            thisWord = ws;
            moveNum = m;
            prev = p;
            toGoalNum = thisWord.estimatedDistanceToGoal();
        }

        public int moves() {
            return moveNum;
        }

        public int totalMoves() {
            return moveNum + toGoalNum;
        }

        public SearchNode prev() {
            return this.prev;
        }

        @Override
        public int compareTo(Object o) {
            //override the comparator which will be used in MinPQ
            int thisSum = this.totalMoves();
            SearchNode other = (SearchNode) o;
            int otherSum = other.totalMoves();
            return thisSum - otherSum;
        }
    }


    /**
     * Constructor which solves the puzzle, computing
     * everything necessary for moves() and solution() to
     * not have to solve the problem again. Solves the
     * puzzle using the A* algorithm. Assumes a solution exists.
     *
     * @param initial
     */
    public Solver(WorldState initial) {
        curMoves = 0;
        MinPQ<SearchNode> pq = new MinPQ<>(); //may should override this MinPQ
        //the first node put in MinPQ
        SearchNode firstNode = new SearchNode(initial, curMoves, null);
        pq.insert(firstNode);

        while (!pq.isEmpty()) {

            //just move the SearchNode with lowest priority
            SearchNode nowNode = pq.delMin();
            //moves increase by 1
            if (nowNode.thisWord.isGoal()) {
                //reach the goal
                goalNode = nowNode;
                break;
            }
            //find and put this Word's neighbor
            Iterable<WorldState> neighbs = nowNode.thisWord.neighbors();
            for (WorldState newWord : neighbs) {
                //lowest priority: M+E is minimized!!!
                String newStr = newWord.toString();
                String prevStr = null; // do not access the null pointer
                if (nowNode.prev() != null) {
                    prevStr = nowNode.prev().thisWord.toString();
                    if (newStr.equals(prevStr)) {
                        //do not go on a loop
                        //do not add it if it's equal to nowNode's parent
                        //do not enqueue its grandfather
                        continue;
                    }
                }
                SearchNode newNode = new SearchNode(newWord, nowNode.moves() + 1, nowNode);
                pq.insert(newNode);
            }
        }
    }

    /**
     * Returns the minimum number of moves to solve the puzzle starting
     * at the initial WorldState.
     *
     * @return
     */
    public int moves() {
        return goalNode.moves();
    }

    /**
     * Returns a sequence of WorldStates from the initial WorldState
     * to the solution.
     *
     * @return
     */
    public Iterable<WorldState> solution() {
        ArrayList<WorldState> ret = new ArrayList<>();
        SearchNode cur = goalNode;
        while (cur != null) {
            ret.add(cur.thisWord);
            cur = cur.prev;
        }
        int len = ret.size();
        for (int i = 0; i < len / 2; i++) {
            WorldState tmp = ret.get(i);
            ret.set(i, ret.get(len - 1 - i));
            ret.set(len - 1 - i, tmp);
        }
        return ret;
    }
}
