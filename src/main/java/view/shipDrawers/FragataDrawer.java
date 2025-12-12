package view.shipDrawers;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class FragataDrawer implements ShipDrawer {

    @Override
    public void draw(Group parent, double width, double height, boolean isHorizontal) {
        if (isHorizontal) {
            drawHorizontal(parent, width, height);
        } else {
            drawVertical(parent, width, height);
        }
    }

    private void drawHorizontal(Group parent, double width, double height) {
        double svgWidth = 100.0;
        double svgHeight = 100.0;
        Polygon hull = new Polygon();
        hull.getPoints().addAll(0.0/svgWidth*width,54.038/svgHeight*height,100.469/svgWidth*width,54.038/svgHeight*height,80.672/svgWidth*width,100.066/svgHeight*height,0.0/svgWidth*width,100.066/svgHeight*height,0.248/svgWidth*width,54.038/svgHeight*height);
        hull.setFill(Color.web("#A07756"));
        hull.setStroke(Color.BLACK);
        hull.setStrokeWidth(1.5);
        parent.getChildren().add(hull);
        Rectangle mast = new Rectangle(45.532/svgWidth*width,0.091/svgHeight*height,3.217/svgWidth*width,53.699/svgHeight*height);
        mast.setFill(Color.web("#A07756"));
        mast.setStroke(Color.BLACK);
        mast.setStrokeWidth(1);
        parent.getChildren().add(mast);
        Polygon flag1 = new Polygon();
        flag1.getPoints().addAll(45.533/svgWidth*width,1.082/svgHeight*height,45.533/svgWidth*width,33.747/svgHeight*height,17.57/svgWidth*width,33.747/svgHeight*height);
        flag1.setFill(Color.WHITE);
        flag1.setStroke(Color.BLACK);
        flag1.setStrokeWidth(1);
        parent.getChildren().add(flag1);
        Polygon flag2 = new Polygon();
        flag2.getPoints().addAll(48.749/svgWidth*width,1.081/svgHeight*height,48.749/svgWidth*width,33.746/svgHeight*height,76.712/svgWidth*width,33.746/svgHeight*height);
        flag2.setFill(Color.web("#EEEEEE"));
        flag2.setStroke(Color.BLACK);
        flag2.setStrokeWidth(1);
        parent.getChildren().add(flag2);
        Rectangle chimney = new Rectangle(92.55/svgWidth*width,45.377/svgHeight*height,2.97/svgWidth*width,8.414/svgHeight*height);
        chimney.setFill(Color.web("#A07756"));
        chimney.setStroke(Color.BLACK);
        chimney.setStrokeWidth(1);
        parent.getChildren().add(chimney);
        Ellipse smoke = new Ellipse(94.158/svgWidth*width,44.139/svgHeight*height,3.836/svgWidth*width,3.713/svgHeight*height);
        smoke.setFill(Color.web("#A07756"));
        smoke.setStroke(Color.BLACK);
        smoke.setStrokeWidth(1);
        parent.getChildren().add(smoke);
        Polygon base = new Polygon();
        base.getPoints().addAll(0.0/svgWidth*width,75.814/svgHeight*height,91.065/svgWidth*width,75.567/svgHeight*height,80.919/svgWidth*width,99.818/svgHeight*height,0.0/svgWidth*width,100.066/svgHeight*height,0.0/svgWidth*width,75.814/svgHeight*height);
        base.setFill(Color.web("#6A4E38"));
        base.setStroke(Color.BLACK);
        base.setStrokeWidth(1.5);
        parent.getChildren().add(base);
    }

    private void drawVertical(Group parent, double width, double height) {
        double svgWidth = 100.0;
        double svgHeight = 100.0;
        Polygon hull = new Polygon();
        hull.getPoints().addAll(54.038/svgWidth*width,0.0/svgHeight*height,54.038/svgWidth*width,100.469/svgHeight*height,100.066/svgWidth*width,80.672/svgHeight*height,100.066/svgWidth*width,0.0/svgHeight*height,54.038/svgWidth*width,0.248/svgHeight*height);
        hull.setFill(Color.web("#A07756"));
        hull.setStroke(Color.BLACK);
        hull.setStrokeWidth(1.5);
        parent.getChildren().add(hull);
        Rectangle mast = new Rectangle(0.091/svgWidth*width,45.532/svgHeight*height,53.699/svgWidth*width,3.217/svgHeight*height);
        mast.setFill(Color.web("#A07756"));
        mast.setStroke(Color.BLACK);
        mast.setStrokeWidth(1);
        parent.getChildren().add(mast);
        Polygon flag1 = new Polygon();
        flag1.getPoints().addAll(1.082/svgWidth*width,45.533/svgHeight*height,33.747/svgWidth*width,45.533/svgHeight*height,33.747/svgWidth*width,17.57/svgHeight*height);
        flag1.setFill(Color.WHITE);
        flag1.setStroke(Color.BLACK);
        flag1.setStrokeWidth(1);
        parent.getChildren().add(flag1);
        Polygon flag2 = new Polygon();
        flag2.getPoints().addAll(1.081/svgWidth*width,48.749/svgHeight*height,33.746/svgWidth*width,48.749/svgHeight*height,33.746/svgWidth*width,76.712/svgHeight*height);
        flag2.setFill(Color.web("#EEEEEE"));
        flag2.setStroke(Color.BLACK);
        flag2.setStrokeWidth(1);
        parent.getChildren().add(flag2);
        Rectangle chimney = new Rectangle(45.377/svgWidth*width,92.55/svgHeight*height,8.414/svgWidth*width,2.97/svgHeight*height);
        chimney.setFill(Color.web("#A07756"));
        chimney.setStroke(Color.BLACK);
        chimney.setStrokeWidth(1);
        parent.getChildren().add(chimney);
        Ellipse smoke = new Ellipse(44.139/svgWidth*width,94.158/svgHeight*height,3.713/svgWidth*width,3.836/svgHeight*height);
        smoke.setFill(Color.web("#A07756"));
        smoke.setStroke(Color.BLACK);
        smoke.setStrokeWidth(1);
        parent.getChildren().add(smoke);
        Polygon base = new Polygon();
        base.getPoints().addAll(75.814/svgWidth*width,0.0/svgHeight*height,75.567/svgWidth*width,91.065/svgHeight*height,99.818/svgWidth*width,80.919/svgHeight*height,100.066/svgWidth*width,0.0/svgHeight*height,75.814/svgWidth*width,0.0/svgHeight*height);
        base.setFill(Color.web("#6A4E38"));
        base.setStroke(Color.BLACK);
        base.setStrokeWidth(1.5);
        parent.getChildren().add(base);
    }
}
