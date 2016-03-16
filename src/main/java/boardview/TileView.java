package boardview;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import model.Position;

/**
 * View class for a tile on a chess board
 * A tile should be able to display a chess piece
 * as well as highlight itself during the game.
 *
 * @author Courtney Branson
 */
public class TileView implements Tile {
    private Position p;
    private StackPane node = new StackPane();
    private Label symbol;
    private Color highlight;
    private Color defaultColor;

    /**
     * Creates a TileView with a specified position
     * @param p
     */
    private Rectangle rectangleHighlight = new Rectangle(75, 75, highlight);
    public Rectangle getRectangleHighlight() {
        return rectangleHighlight;
    }
    public TileView(Position p) {
        this.p = p;
        symbol = new Label("");
        if ((p.getRow() + p.getCol()) % 2 == 0) {
            defaultColor = Color.WHITE;
        } else {
            defaultColor = Color.MISTYROSE;
        }
        Rectangle rectangle = new Rectangle(75, 75, defaultColor);
        Rectangle rectangleHighlight = new Rectangle(75, 75, highlight);
        rectangleHighlight.setOpacity(1);
        node.getChildren().addAll(rectangle, rectangleHighlight, symbol);
    }


    @Override
    public Position getPosition() {
        return p;
    }


    @Override
    public Node getRootNode() {
        return node;
    }

    @Override
    public void setSymbol(String symbols) {
        symbol.setText(symbols);
    }


    @Override
    public String getSymbol() {
        return symbol.getText(); //ChessController.getSymbolForPieceAt(p);
    }

    @Override
    public void highlight(Color color) {
        node.getChildren().clear();
        rectangleHighlight.setOpacity(0.25);
        Color opaqueColor = new Color(color.getRed(), color.getGreen(),
            color.getBlue(), 0.5);
        node.getChildren().addAll(new Rectangle(75, 75, opaqueColor),
            rectangleHighlight, symbol);
    }

    @Override
    public void clear() {
        node.getChildren().clear();
        rectangleHighlight.setOpacity(1);
        node.getChildren().addAll(new Rectangle(75, 75, defaultColor),
            rectangleHighlight, symbol);

    }
}
