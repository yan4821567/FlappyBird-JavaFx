
package flappybird;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author tareq
 */
public class TwoTubes extends Group {

    public Rectangle topHead, lowerHead;
    public Rectangle topBody, lowerBody;
    double GAP = 100.0;
    Color c = new Color(85 / 255.0, 128 / 255.0, 34 / 255.0, 1.0);
    Color c2 = new Color(84 / 255.0, 56 / 255.0, 71 / 255.0, 1.0);

    public TwoTubes(double y) {
        topBody = new Rectangle(65, y);
        topHead = new Rectangle(70, 25);
        topBody.setX(2.5);
        topHead.setY(y);

        lowerHead = new Rectangle(70, 25);
        lowerBody = new Rectangle(65, 300 - GAP - y - 50);
        lowerBody.setX(2.5);
        lowerHead.setY(y + 25 + GAP);
        lowerBody.setY(GAP + y + 50);
        lowerHead.setFill(c);
        lowerBody.setFill(c);
        topHead.setFill(c);
        topBody.setFill(c);
        lowerHead.setStroke(c2);
        lowerBody.setStroke(c2);
        topHead.setStroke(c2);
        topBody.setStroke(c2);
        lowerBody.setOpacity(0.5);
        lowerHead.setOpacity(0.5);
        topBody.setOpacity(0.5);
        topHead.setOpacity(0.5);

        getChildren().addAll(topBody, topHead, lowerBody, lowerHead);
    }
}
