package com.michalchmielewski;

public class State {

    private State previous;
    private Frame frame;
    private char move;
    private int emptyIndex;
    private int[] frameValues;

    public Frame getFrame() {
        return frame;
    }

    public int getFrameValue(int i) {
        return frameValues[i];
    }

    public int getEmptyIndex() {
        return emptyIndex;
    }

    public State getPrevious(){
        return previous;
    }

    public char getMove(){
        return move;
    }

    public void setEmptyIndex(int emptyIndex) {
        this.emptyIndex = emptyIndex;
    }

    public void setMove(char move) {
        this.move = move;
    }

    public State() {
    }

    public State(State state) {
        previous = state;
        frame = state.getFrame();
        emptyIndex = state.getEmptyIndex();
        frameValues = new int[frame.getWK()];
        for (int i = 0; i < frame.getWK(); i++) {
            frameValues[i] = state.getFrameValue(i);
        }
    }

    public State(Frame frame) {
        this.frame = frame;
        emptyIndex = frame.getEmptyIndex();
        frameValues = new int[frame.getWK()];
        for (int i = 0; i < frame.getWK(); i++) {
            frameValues[i] = frame.getFrameValue(i);
        }
    }

    public boolean checkIfIsSolved() {
        for (int i = 0; i < frameValues.length; i++) {
            if (frameValues[i] != frame.getCorrectValue(i)) {
                return false;
            }
        }
        return true;
    }

    private void replace(int i, int j) {
        int temp = frameValues[i];
        frameValues[i] = frameValues[j];
        frameValues[j] = temp;
    }

    public static State left(State previousState) {
        if (previousState.getEmptyIndex() % previousState.getFrame().getW() == 0)//check if free space is in first column
            return null;
        State state = new State(previousState);
        state.replace(state.getEmptyIndex(), state.getEmptyIndex() - 1);
        state.setEmptyIndex(state.getEmptyIndex() - 1);
        state.setMove('L');
        return state;
    }

    public static State right(State previousState) {
        if (previousState.getEmptyIndex() % previousState.getFrame().getW() == previousState.getFrame().getW() - 1)//check if free space is in last column
            return null;
        State state = new State(previousState);
        state.replace(state.getEmptyIndex(), state.getEmptyIndex() + 1);
        state.setEmptyIndex(state.getEmptyIndex() + 1);
        state.setMove('R');
        return state;
    }

    public static State up(State previousState) {
        if (previousState.getEmptyIndex() < previousState.getFrame().getW())//check if free space is in first row
            return null;
        State state = new State(previousState);
        state.replace(state.getEmptyIndex(), state.getEmptyIndex() - state.getFrame().getW());
        state.setEmptyIndex(state.getEmptyIndex() - previousState.getFrame().getW());
        state.setMove('U');
        return state;
    }

    public static State down(State previousState) {
        if (previousState.getEmptyIndex() >= previousState.getFrame().getWK() - previousState.getFrame().getW())//check if free space is in last row
            return null;
        State state = new State(previousState);
        state.replace(state.getEmptyIndex(), state.getEmptyIndex() + state.getFrame().getW());
        state.setEmptyIndex(state.getEmptyIndex() + previousState.getFrame().getW());
        state.setMove('D');
        return state;
    }
}
