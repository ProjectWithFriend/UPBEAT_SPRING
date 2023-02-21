package AST;

import Game.*;
import AST.Node.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Region.Region;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ASTTest {
    private static class FunctionCalled extends RuntimeException {
        public static class AttackCalled extends FunctionCalled {}
        public static class CollectCalled extends FunctionCalled {}
        public static class InvestCalled extends FunctionCalled {}
        public static class RelocateCalled extends FunctionCalled {}
    }
    private Game mockGame() {
        return new Game() {
            public final Map<String, Long> identifiers = new HashMap<>();

            @Override
            public Map<String, Long> getIdentifiers() {
                return identifiers;
            }

            @Override
            public Map<String, Long> getSpecialIdentifiers() {
                Map<String, Long> map = new HashMap<>();
                map.put("rows", 123L);
                map.put("cols", 234L);
                map.put("currow", 345L);
                map.put("curcol", 456L);
                map.put("budget", 567L);
                map.put("deposit", 789L);
                map.put("int", 890L);
                map.put("maxdeposit", 901L);
                map.put("random", 12L);
                return map;
            }

            @Override
            public void attack(Direction direction, long value) {
                throw new FunctionCalled.AttackCalled();
            }

            @Override
            public boolean collect(long value) {
                throw new FunctionCalled.CollectCalled();
            }

            @Override
            public void invest(long value) {
                throw new FunctionCalled.InvestCalled();
            }

            @Override
            public void relocate() {
                throw new GameException.NotImplemented();
            }

            @Override
            public long nearby(Direction direction) {
                throw new GameException.NotImplemented();
            }

            @Override
            public boolean move(Direction direction) {
                throw new GameException.NotImplemented();
            }

            @Override
            public long opponent() {
                throw new GameException.NotImplemented();
            }

            @Override
            public long getCurrentPlayerID() {
                throw new GameException.NotImplemented();
            }

            @Override
            public void submitPlan(String constructionPlan) {
                throw new GameException.NotImplemented();
            }

            @Override
            public List<Region> getTerritory() {
                throw new GameException.NotImplemented();
            }

            @Override
            public Region getRegion(int index) {
                throw new GameException.NotImplemented();
            }

            @Override
            public long getBudget() {
                throw new GameException.NotImplemented();
            }

            @Override
            public void endTurn() {
                throw new GameException.NotImplemented();
            }
        };
    }

    @Test
    public void testIfElse() {
        Game game;
        ExprNode expression;
        ExecNode node, trueNode, falseNode;
        game = mockGame();
        trueNode = new DoneNode();
        falseNode = new DoneNode();

        expression = new AtomicNode(0);
        node = new IfElseNode(expression, trueNode, falseNode);
        assertEquals(falseNode, node.execute(game));

        expression = new AtomicNode(1);
        node = new IfElseNode(expression, trueNode, falseNode);
        assertEquals(trueNode, node.execute(game));

        expression = new AtomicNode(2);
        node = new IfElseNode(expression, trueNode, falseNode);
        assertEquals(trueNode, node.execute(game));

        expression = new AtomicNode(-1);
        node = new IfElseNode(expression, trueNode, falseNode);
        assertEquals(falseNode, node.execute(game));

        expression = new AtomicNode(-2);
        node = new IfElseNode(expression, trueNode, falseNode);
        assertEquals(falseNode, node.execute(game));
    }

    @Test
    public void testWhile() {
        Game game;
        ExprNode expression;
        ExecNode node, trueNode, nextNode;
        game = mockGame();
        trueNode = new DoneNode();
        nextNode = new DoneNode();

        expression = new AtomicNode(0);
        node = new WhileNode(expression, trueNode);
        node.next = nextNode;
        assertEquals(nextNode, node.execute(game));

        expression = new AtomicNode(1);
        node = new WhileNode(expression, trueNode);
        assertEquals(trueNode, node.execute(game));

        expression = new AtomicNode(2);
        node = new WhileNode(expression, trueNode);
        assertEquals(trueNode, node.execute(game));

        expression = new AtomicNode(-1);
        node = new WhileNode(expression, trueNode);
        assertNull(node.execute(game));

        expression = new AtomicNode(-2);
        node = new WhileNode(expression, trueNode);
        assertNull(node.execute(game));

        node = new WhileNode(new AtomicNode(1), null);
        node.next = new DoneNode();
        while (node != null) {
            node = node.execute(game);
            if (!(node instanceof WhileNode) && node != null)
                assertInstanceOf(DoneNode.class, node);
        }
    }

    @Test
    public void testAssignment() {
        String key;
        Game game = mockGame();
        ExecNode node;
        Map<String, Long> map = game.getIdentifiers();

        key = "x";
        node = new AssignmentNode(key, new AtomicNode(-1));
        node.execute(game);
        assertEquals(-1L, map.get(key));

        key = "y";
        node = new AssignmentNode(key, new AtomicNode(0));
        node.execute(game);
        assertEquals(0, map.get(key));

        key = "z";
        node = new AssignmentNode(key, new AtomicNode(1));
        node.execute(game);
        assertEquals(1, map.get(key));
    }
}
