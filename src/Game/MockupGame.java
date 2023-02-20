package Game;

import AST.Node.*;
import Parser.*;
import Region.Region;
import Game.MockupGameException.*;
import Tokenizer.IterateTokenizer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import Player.*;

public class MockupGame implements Game {
    private static final Map<String, Long> identifiers = new HashMap<>();
    private static final List<Region> territory = GameUtils.createTerritory();
    private static final Player player1 = GameUtils.createPlayer("player1");
    private static final Player player2 = GameUtils.createPlayer("player2");

    @Override
    public Map<String, Long> getIdentifiers() {
        return identifiers;
    }

    @Override
    public Map<String, Long> getSpecialIdentifiers() {
        Map<String, Long> map = new HashMap<>();
        map.put("rows", GameUtils.getRows());
        map.put("cols", GameUtils.getCols());
        map.put("currow", 0L);
        map.put("curcol", 0L);
        map.put("budget", getBudget());
        map.put("deposit", 0L);
        map.put("int", GameUtils.getInterestPercentage());
        map.put("maxdeposit", GameUtils.getMaxDeposit());
        map.put("random", new Random().nextLong(1000));
        return map;
    }

    @Override
    public void collect(long value) {
        throw new CollectCalled();
    }

    @Override
    public void invest(long eval) {
        throw new InvestCalled();
    }

    @Override
    public void relocate() {
        throw new RelocateCalled();
    }

    @Override
    public long opponent() {
        return 69;
    }

    @Override
    public long getCurrentPlayerID() {
        return 96;
    }

    @Override
    public void submitPlan(String constructionPlan) {
        Parser parser = new GrammarParser(new IterateTokenizer(constructionPlan));
        ExecNode node = parser.parse();
        while (node != null) {
            node = node.execute(this);
        }
    }

    @Override
    public List<Region> getTerritory() {
        return territory;
    }

    @Override
    public Region getRegion(int index) {
        return territory.get(index);
    }

    @Override
    public long getBudget() {
        return 0L;
    }

    @Override
    public void endTurn() {
        throw new EndTurnCalled();
    }

    @Override
    public boolean move(Direction direction) {
        throw new MoveCalled();
    }

    @Override
    public long nearby(Direction direction) {
        return switch (direction) {
            case Up -> 111;
            case Down -> 222;
            case UpLeft -> 333;
            case UpRight -> 444;
            case DownLeft -> 555;
            case DownRight -> 666;
        };
    }

    @Override
    public void attack(Direction direction, long value) {
        throw new AttackCalled();
    }
}
