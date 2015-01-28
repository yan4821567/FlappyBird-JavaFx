package flappybird;

import flappybird.res.Res;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author tareq
 */
public class FlappyBird extends Application {

    Res res = new Res();
    double FPS_30 = 30;
    int counter_30FPS = 0, counter_3FPS = 0;
    Bird bird;
    TranslateTransition jump;
    TranslateTransition fall;

    RotateTransition rotator;
    ArrayList<TwoTubes> listOfTubes = new ArrayList<>();
    Timeline gameLoop;
    Pane root;
    boolean gameOver = false;
    StatusUpdater statusUpdater = new StatusUpdater(590, 10);
    int score = 0;
    private boolean incrementOnce = true;

    @Override
    public void start(Stage primaryStage) {
        root = new Pane();
        root.setStyle("-fx-background-color: #4EC0CA");
        Scene scene = new Scene(root, 800, 300);

        primaryStage.setTitle("Flappy bird");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        bird = new Bird(res.birdImgs);
        rotator = new RotateTransition(Duration.millis(500), bird.getGraphics());
        jump = new TranslateTransition(Duration.millis(500), bird.getGraphics());
        fall = new TranslateTransition(Duration.millis(1800), bird.getGraphics());
        fall.setByY(320);
        rotator.setCycleCount(1);
        bird.getGraphics().setRotationAxis(Rotate.Z_AXIS);
        bird.getGraphics().setTranslateX(100);
        bird.getGraphics().setTranslateY(150);
        root.getChildren().addAll(bird.getGraphics(), statusUpdater);
        for (int i = 0; i <= 4; i++) {
            TwoTubes tube = new TwoTubes(Math.random() * 150);
            tube.setTranslateX(i * 210 + 400);
            listOfTubes.add(tube);
            root.getChildren().add(tube);
        }
        gameLoop = new Timeline(new KeyFrame(Duration.millis(1000 / FPS_30), e -> {
            updateCounters();
            for (int i = 0; i < listOfTubes.size(); i++) {
                checkCollisions();
                if (listOfTubes.get(i).getTranslateX() <= -65) {
                    listOfTubes.remove(i);
                    TwoTubes tube = new TwoTubes(Math.random() * 150);
                    tube.setTranslateX(listOfTubes.get(listOfTubes.size() - 1).getTranslateX() + 210);
                    listOfTubes.add(tube);
                    incrementOnce = true;
                    root.getChildren().remove(i + 2);
                    root.getChildren().add(tube);
                }
                listOfTubes.get(i).setTranslateX(listOfTubes.get(i).getTranslateX() - 2);
            }
        }));

        gameLoop.setCycleCount(-1);
        bird.getGraphics().setRotate(40);
        fall.play();
        gameLoop.play();
        root.setPrefSize(800, 300);
        root.setOnMouseClicked(e -> {
            if (!gameOver)
                jumpflappy();
            else {
                retry();

            }
        });
        root.setOnTouchPressed(e -> {
            if (!gameOver)
                jumpflappy();
            else {
                retry();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void updateCounters() {
        if (counter_30FPS % 4 == 0) {
            bird.refreshBird();
            counter_30FPS = 1;
        }
        counter_30FPS++;
    }

    private void jumpflappy() {
        rotator.setDuration(Duration.millis(100));
        rotator.setToAngle(-40);
        rotator.stop();
        rotator.play();
        jump.setByY(-50);
        jump.setCycleCount(1);
        bird.jumping = true;
        fall.stop();
        jump.stop();
        jump.play();
        jump.setOnFinished((finishedEvent) -> {
            rotator.setDuration(Duration.millis(500));
            rotator.setToAngle(40);
            rotator.stop();
            rotator.play();
            bird.jumping = false;
            fall.play();
        });
    }

    private void checkCollisions() {
        TwoTubes tube = listOfTubes.get(0);
        if (tube.getTranslateX() < 35 && incrementOnce) {
            score++;
            statusUpdater.setText("Score: " + score);

            incrementOnce = false;
        }
        tube.lowerBody.setOpacity(1.0);
        tube.lowerHead.setOpacity(1.0);
        tube.topBody.setOpacity(1.0);
        tube.topHead.setOpacity(1.0);
        Path p1 = (Path) Shape.intersect(bird.getBounds(), tube.topBody);
        Path p2 = (Path) Shape.intersect(bird.getBounds(), tube.topHead);
        Path p3 = (Path) Shape.intersect(bird.getBounds(), tube.lowerBody);
        Path p4 = (Path) Shape.intersect(bird.getBounds(), tube.lowerHead);
        boolean intersection = !(p1.getElements().isEmpty()
                && p2.getElements().isEmpty()
                && p3.getElements().isEmpty()
                && p4.getElements().isEmpty());
        if (bird.getBounds().getCenterY() + bird.getBounds().getRadiusY() > 300 || bird.getBounds().getCenterY() - bird.getBounds().getRadiusY() < 0) {
            intersection = true;
        }
        if (intersection) {
            statusUpdater.setText("Score: " + score + "\nGame Over");
            statusUpdater.setOpacity(1);
            StatusUpdater su = new StatusUpdater(300, 150);
            su.setText("Tap to retry\nScore: " + score);
            root.getChildren().add(su);
            root.getChildren().get(1).setOpacity(0);
            gameOver = true;
            gameLoop.stop();
        }

    }

    private void retry() {
        listOfTubes.clear();
        root.getChildren().clear();
        bird.getGraphics().setTranslateX(100);
        bird.getGraphics().setTranslateY(150);
        statusUpdater.setOpacity(0.8);
        statusUpdater.setText("Score: 0");
        root.getChildren().addAll(bird.getGraphics(), statusUpdater);
        for (int i = 0; i <= 4; i++) {
            TwoTubes tube = new TwoTubes(Math.random() * 150);
            tube.setTranslateX(i * 210 + 400);
            listOfTubes.add(tube);
            root.getChildren().add(tube);
        }
        score = 0;
        incrementOnce = true;
        gameOver = false;
        bird.jumping = false;
        fall.stop();
        fall.play();
        gameLoop.play();
    }
}
