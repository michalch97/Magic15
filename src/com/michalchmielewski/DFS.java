package com.michalchmielewski;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class DFS {
    private Frame frame;
    private State correctState;
    private String order;
    private Queue<State> statesQueue;
    private HashSet<State> statesHashes;
    private int depth;
    private int nodes;
    private final int maxDepth = 20;
    private long timeElapsed;
    private int i = 0;

    public DFS(Frame frame, String order) {
        this.frame = frame;
        this.order = order;
        statesQueue = new LinkedList<State>();
        statesHashes = new HashSet<State>();
        depth = 0;
    }

    public void solve() {
        State firstState = new State(frame);
        long startTime = System.nanoTime();
        start(firstState, maxDepth);
        long endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
    }

    private void start(State state, int d) {
        if (maxDepth - d >= depth) {
            depth = maxDepth - d;
        }
        if (d == 0) {
            return;
        }
        nodes++;
        if (state.checkIfIsSolved()) {
            correctState = state;
            return;
        }
        State newState = new State();
        for (int i = 0; i < order.length(); i++) {
            switch (order.charAt(i)) {
                case 'L':
                    newState = State.left(state);
                    break;
                case 'R':
                    newState = State.right(state);
                    break;
                case 'U':
                    newState = State.up(state);
                    break;
                case 'D':
                    newState = State.down(state);
                    break;
            }
            if (newState != null && !statesHashes.contains(newState)) {
                statesHashes.add(newState);
                start(newState, d - 1);
                if (correctState != null) {
                    return;
                }
            }
        }
    }

    public String getSolution() {
        //System.out.println(depth);
        StringBuilder s = new StringBuilder();
        State currentState = correctState;
        State parent;
        while (true) {
            parent = currentState.getPrevious();
            if (parent == null) {
                break;
            }
            s.append(currentState.getMove());
            currentState = parent;
        }
        StringBuilder solution = s.reverse();
        return solution.toString();
    }

    public String[] getStatistic() {
        String[] statistic = new String[4];
        statistic[0] = Integer.toString(statesHashes.size());
        statistic[1] = Integer.toString(nodes);
        statistic[2] = Integer.toString(depth);
        float time = (((float)timeElapsed)/((float)1000000));
        statistic[3] = String.format("%.3f",time);
        return statistic;
    }
}
