package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public abstract class Tile {



    protected final int tileCoordinate;
    private static final Map<Integer,EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();

    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer,EmptyTile> emptyTileMap = new HashMap<Integer, EmptyTile>();

            for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
                emptyTileMap.put(i, new EmptyTile(i));
            }
        return ImmutableMap.copyOf(emptyTileMap);
    }

    private Tile(final int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }

    public static Tile createTile(final int tileCoordinate, final Piece piece) {
        return piece != null ? new OccupiedTile(tileCoordinate,  piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
    }

    public abstract boolean isTileOccupied();

    public abstract Piece getPieceOnTile();

    public int getTileCoordinate() {
        return this.tileCoordinate;
    }

    public static final class EmptyTile extends Tile {

        private EmptyTile(final int tileCoordinate) {
            super(tileCoordinate);
        }

        @Override
        public String toString() {
            return "-";
        }

        @Override
        public boolean isTileOccupied() {
            return false;
        }

        @Override
        public Piece getPieceOnTile() {
            return null;
        }
    }

    public static final class OccupiedTile extends Tile {

        private final Piece pieceOnTile;

        private OccupiedTile(final int tileCoordinate, final Piece piece) {
            super(tileCoordinate);
            this.pieceOnTile = piece;
        }

        @Override
        public String toString() {
            return getPieceOnTile().getPieceAlliance().isBlack() ? getPieceOnTile().toString().toLowerCase() : getPieceOnTile().toString();
        }

        @Override
        public boolean isTileOccupied() {
            return true;
        }
        @Override
        public Piece getPieceOnTile() {
            return this.pieceOnTile;
        }
    }
}