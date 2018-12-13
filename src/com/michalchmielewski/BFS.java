package com.michalchmielewski;

import java.util.*;

public class BFS {

    private Frame frame;
    private State correctState;
    private String order;
    private Queue<State> statesQueue;
    private HashSet<State> statesHashes;
    private int depth;
    private int layer;
    private int nextLayer;
    private int temp;
    private int nodes;
    private long timeElapsed;

    public BFS(Frame frame, String order) {
        this.frame = frame;
        this.order = order;
        statesQueue = new LinkedList<State>();
        statesHashes = new HashSet<State>();
        depth = 0;
        layer = 0;
        nextLayer = 1;
        temp = 0;
        nodes = 0;
    }

    public void solve() {
        State firstState = new State(frame);
        long startTime = System.nanoTime();
        start(firstState);
        long endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
    }

    private void start(State firstState) {
        statesQueue.clear();
        statesQueue.add(firstState);
        statesHashes.clear();
        statesHashes.add(firstState);
        State state;
        State newState = new State();

        while (statesQueue.size() != 0) {

            if (layer == 0) {
                depth++;
                layer = nextLayer;
                nextLayer = 0;

            }
            temp = statesHashes.size();
            nodes++;

            state = statesQueue.poll();
            if (state.checkIfIsSolved()) {
                correctState = state;
                break;
            }

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
                    statesQueue.add(newState);
                }
            }
            nextLayer += statesHashes.size() - temp;
            layer--;
        }
    }

    public String getSolution() {
        StringBuilder s = new StringBuilder();
        State currentState = correctState;
        State parent;
        while (true) {
            parent = currentState.getPrevious();
            if (parent == null){
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
