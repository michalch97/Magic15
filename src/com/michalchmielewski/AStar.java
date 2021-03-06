package com.michalchmielewski;

import java.util.*;

//https://algorithmsinsight.wordpress.com/graph-theory-2/a-star-in-general/implementing-a-star-to-solve-n-puzzle/
public class AStar {
    private Frame frame;
    private AState correctState;
    private PriorityQueue<AState> statesQueue;
    private HashSet<AState> statesHashes;
    private String heuristic;
    private int nodes;
    private int depth;
    private long timeElapsed;

    public AStar(Frame frame, String heuristic){
        this.frame = frame;
        statesQueue = new PriorityQueue<AState>(Comparator.comparing(AState::getF));
        statesHashes = new HashSet<AState>();
        this.heuristic = heuristic;
        nodes = 0;
        depth = 0;
    }

    public void solve(){
        AState firstAState = new AState(frame,heuristic);
        long startTime = System.nanoTime();
        start(firstAState);
        long endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
    }

    private void start(AState firstState){
        statesQueue.clear();
        statesHashes.clear();
        statesQueue.add(firstState);
        AState state;
        AState newState;
        while(statesQueue.size() != 0){
            state = statesQueue.poll();
            depth = getDepth(state);
            if(state.checkIfIsSolved()){
                correctState = state;
                break;
            }
            if(statesHashes.contains(state)){
                continue;
            }
            statesHashes.add(state);
            newState = AState.up(state);
            if(newState != null && !statesHashes.contains(newState)){
                statesQueue.add(newState);
                nodes++;
            }
            newState = AState.down(state);
            if(newState != null && !statesHashes.contains(newState)){
                statesQueue.add(newState);
                nodes++;
            }
            newState = AState.left(state);
            if(newState != null && !statesHashes.contains(newState)){
                statesQueue.add(newState);
                nodes++;
            }
            newState = AState.right(state);
            if(newState != null && !statesHashes.contains(newState)){
                statesQueue.add(newState);
                nodes++;
            }
        }
    }

    private int getDepth(AState state) {
        int i=0;
        AState temp = state;
        while(temp.getPreviousAState()!=null){
            temp = temp.getPreviousAState();
            i++;
        }
        return i;
    }

    public String getSolution() {
        StringBuilder s = new StringBuilder();
        AState currentState = correctState;
        AState parent;
        while (true) {
            parent = currentState.getPreviousAState();
            if(parent == null){
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
