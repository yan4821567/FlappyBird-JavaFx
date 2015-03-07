/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flappybird;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

public class TwoTubes extends Group {

    public Rectangle topHead, lowerHead;
    public Rectangle topBody, lowerBody;
    double GAP = 120;
    Stop[] stops = new Stop[]{new Stop(0, Color.LIGHTGREEN), new Stop(1, Color.DARKGREEN)};
    LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
    Color c2 = new Color(84 / 255.0, 56 / 255.0, 71 / 255.0, 1.0);

    public TwoTubes(SimpleDoubleProperty gapLocation, Pane root, boolean animate) {
        topBody = new Rectangle();
        topBody.widthProperty().bind(root.widthProperty().divide(12.3));
        topBody.heightProperty().bind(gapLocation);
        topHead = new Rectangle();
        topHead.widthProperty().bind(root.widthProperty().divide(11.4));
        topHead.heightProperty().bind(root.heightProperty().divide(12));
        topBody.setX(2.5);
        topHead.yProperty().bind(gapLocation);
        lowerHead = new Rectangle();
        lowerHead.widthProperty().bind(root.widthProperty().divide(11.4));
        lowerHead.heightProperty().bind(root.heightProperty().divide(12));
        lowerBody = new Rectangle();
        lowerBody.widthProperty().bind(root.widthProperty().divide(12.3));
        lowerBody.heightProperty().bind(root.heightProperty().add(-GAP - 50).subtract(gapLocation));
        lowerBody.setX(2.5);
        lowerHead.yProperty().bind(gapLocation.add(GAP).add(root.heightProperty().divide(12)));
        lowerBody.yProperty().bind(gapLocation.add(GAP).add(root.heightProperty().divide(6)));
        lowerHead.setFill(lg1);
        lowerBody.setFill(lg1);
        topHead.setFill(lg1);
        topBody.setFill(lg1);
        lowerHead.setStroke(c2);
        lowerBody.setStroke(c2);
        topHead.setStroke(c2);
        topBody.setStroke(c2);
        getChildren().addAll(topBody, topHead, lowerBody, lowerHead);
    }
}
