package Game;

public abstract class MockupGameException extends RuntimeException {
    public static class CollectCalled extends MockupGameException {}
    public static class InvestCalled extends MockupGameException {}
    public static class RelocateCalled extends MockupGameException {}
    public static class MoveCalled extends MockupGameException {}
    public static class AttackCalled extends MockupGameException {}
    public static class EndTurnCalled extends MockupGameException {}
}
