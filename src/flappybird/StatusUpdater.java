package flappybird;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Tareq Si Salem
 */
public class StatusUpdater extends Pane {

    Text status = new Text("Score: 0");

    public StatusUpdater(double x, double y) {
        setTranslateX(x);
        setTranslateY(y);
        setStyle("-fx-background-color: #CCC;"
                + "-fx-background-radius:20px");
        setPrefHeight(70);
        setPrefWidth(200);
        setOpacity(0.8);
        status.setTranslateY(30);
        status.setTranslateX(20);

        getChildren().addAll(status);
        status.setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD, 17));
        status.setFill(new Color(107 / 255.0, 162 / 255.0, 252 / 255.0, 1.0));
    }

    public void setText(String message) {
        status.setText(message);

    }
}
