package com.michalchmielewski;

public class AState extends State {
    private int g;
    private int h;
    private static String heuristic;
    private AState previousAState;

    public AState getPreviousAState() {
        return previousAState;
    }

    public void setPreviousAState(AState previousAState) {
        this.previousAState = previousAState;
    }

    public int getG(){
        return g;
    }

    public void setG(int g){
        this.g = g;
    }

    public void setH(int h){
        this.h = h;
    }

    public int getF(){return this.g+this.h;}

    public AState(State state, String heuristic){
        if(state.getPrevious() != null) {
            this.previous = state.getPrevious();
        }
        this.frame = state.getFrame();
        this.emptyIndex = state.getEmptyIndex();
        this.move = state.getMove();
        this.frameValues = new int[state.getFrame().getWK()];
        for(int i = 0; i < state.getFrame().getWK(); i++){
            this.frameValues[i] = state.getFrameValue(i);
        }
        this.heuristic = heuristic;
    }

    public AState(Frame frame, String heuristic){
        this.frame = frame;
        this.emptyIndex = frame.getEmptyIndex();
        this.frameValues = new int[frame.getWK()];
        for(int i = 0; i < frame.getWK(); i++){
            this.frameValues[i] = frame.getFrameValue(i);
        }
        this.heuristic = heuristic;
    }

    public int hamming(){
        int result = 0;
        for(int i=0;i<frame.getWK();i++){
            if(frameValues[i] != frame.getCorrectValue(i)){
                result++;
            }
        }
        return result;
    }

    public int manhattan(){
        int result = 0;
        for(int i=0;i<frame.getWK();i++){
            int a = frameValues[i] - 1;
            int x1 = i / frame.getW();
            int x2 = a / frame.getW();
            int y1 = i % frame.getK();
            int y2 = a % frame.getK();
            result+=Math.abs(x2-x1);
            result+=Math.abs(y2-y1);
        }
        return result;
    }

    public int getHeuristicValue(){
        switch (heuristic){
            case "hamm":
                return hamming();
            case "manh":
                return manhattan();
        }
        return 0;
    }

    public static AState left(AState previousState){
        State temp = State.left(previousState);
        return initAState(previousState,temp);
    }

    public static AState right(AState previousState){
        State temp = State.right(previousState);
        return initAState(previousState,temp);
    }

    public static AState up(AState previousState){
        State temp = State.up(previousState);
        return initAState(previousState,temp);
    }

    public static AState down(AState previousState){
        State temp = State.down(previousState);
        return initAState(previousState,temp);
    }

    private static AState initAState(AState previousState, State temp){
        AState state = null;
        if(temp != null){
            state = new AState(temp, heuristic);
            state.setPreviousAState(previousState);
            state.setG(previousState.getG() + 1);
            state.setH(state.getHeuristicValue());
        }
        return state;
    }
}
