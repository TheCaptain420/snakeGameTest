package sample;
import javafx.collections.ObservableList;
import javafx.animation.*;
import javafx.collections.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.*;
import javafx.scene.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.shape.*;


public class Main extends Application {

    public enum Direction{
        UP, DOWN, LEFT, RIGHT
    }

    public static final int BLOCK_SIZE = 40;
    public static final int APP_W = 20 * BLOCK_SIZE;
    public static final int APP_H = 15 * BLOCK_SIZE;

    private Direction direction = Direction.RIGHT;
    private boolean moved = false;
    private boolean running = false; // app running

    private Timeline timeline = new Timeline();

    private ObservableList<Node> snake;

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(APP_W, APP_H);

        Group snakeBody = new Group();
        snake = snakeBody.getChildren();

        Rectangle food = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
        food.setFill(Color.BLUE);
        food.setTranslateX((int) (Math.random() * (APP_W - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);
        food.setTranslateY((int) (Math.random() * (APP_H - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);

        KeyFrame frame = new KeyFrame(/* KeyFrame is like a single frame in animation!!!*/ Duration.seconds(0.2)), event ->{
            if (!running) {
                return; //if not running just simple return
            }

            boolean toRemove = snake.size() > 1; // at least two blocks in the snake body;

            Node tail = toRemove ? snake.remove(snake.size() - 1) : snake.get(0); //

            double tailX = tail.getTranslateX();
            double tailY = tail.getTranslateY();

            switch (direction) {
                case UP:
                    tail.setTranslateX(snake.get(0).getTranslateX());
                    tail.setTranslateY(snake.get(0).getTranslateY() - BLOCK_SIZE);
                    break;
                case DOWN:
                    tail.setTranslateX(snake.get(0).getTranslateX());
                    tail.setTranslateY(snake.get(0).getTranslateY() + BLOCK_SIZE);
                    break;
                case LEFT:
                    tail.setTranslateX(snake.get(0).getTranslateX() - BLOCK_SIZE);
                    tail.setTranslateY(snake.get(0).getTranslateY());
                    break;
                case RIGHT:
                    tail.setTranslateX(snake.get(0).getTranslateX() + BLOCK_SIZE);
                    tail.setTranslateY(snake.get(0).getTranslateY());
                    break;
            }

            moved = true;

            if (toRemove){
                snake.add(0,tail);
            }

            //collision detection
            for (Node rect: snake){
                if (rect != tail && tail.getTranslateX()==rect.getTranslateX() && tail.getTranslateY() == tail.getTranslateY()){
                    restartGame();
                    break;
                }

            }
            //collision mod væg
            if (tail.getTranslateX() <0 || tail.getTranslateX() >= APP_W || tail.getTranslateY()<0 ||tail.getTranslateY() >= APP_H){
                restartGame();
            }
            //adder mad
            if (tail.getTranslateX()==food.getTranslateX() && tail.getTranslateY() == food.getTranslateY()){
                food.setTranslateX((int) Math.random() *(APP_W - BLOCK_SIZE ) / BLOCK_SIZE*BLOCK_SIZE);
                food.setTranslateY((int) Math.random() *(APP_H - BLOCK_SIZE ) / BLOCK_SIZE*BLOCK_SIZE);
            }

            Rectangle rect = new Rectangle();
            rect.setTranslateY(tailY);
            rect.setTranslateX(tailX);

            snake.add(rect);



            return root;

        }


    }



    @Override
    public void start(Stage primaryStage) throws Exception{
        Scene scene = new Scene(createContent());
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Snake World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
