package view.shipDrawers;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class SubmarinoDrawer implements ShipDrawer {

    @Override
    public void draw(Group parent, double width, double height, boolean isHorizontal) {
        if (isHorizontal) {
            drawHorizontal(parent, width, height);
        } else {
            drawVertical(parent, width, height);
        }
    }

    private void drawHorizontal(Group parent, double width, double height) {
        double svgWidth = 300.0;
        double svgHeight = 100.0;
        Rectangle body = new Rectangle(35.441/svgWidth*width,27.93/svgHeight*height,263.856/svgWidth*width,71.848/svgHeight*height);
        body.setFill(Color.web("#575656"));
        body.setStroke(Color.web("#322727"));
        body.setStrokeWidth(1.5);
        body.setArcWidth(42.217/svgWidth*width);
        body.setArcHeight(42.217/svgHeight*height);
        parent.getChildren().add(body);
        Path proa = new Path();
        proa.getElements().addAll(new MoveTo(46.582/svgWidth*width,40.892/svgHeight*height),new LineTo(41.18/svgWidth*width,32.037/svgHeight*height),new LineTo(3.794/svgWidth*width,32.036/svgHeight*height),new LineTo(28.369/svgWidth*width,65.155/svgHeight*height),new LineTo(5.189/svgWidth*width,99.779/svgHeight*height),new LineTo(43.283/svgWidth*width,99.254/svgHeight*height),new LineTo(48.537/svgWidth*width,89.033/svgHeight*height));
        proa.setFill(Color.web("#999999"));
        proa.setStroke(Color.BLACK);
        proa.setStrokeWidth(1.5);
        parent.getChildren().add(proa);
        Polygon torre = new Polygon();
        torre.getPoints().addAll(124.512/svgWidth*width,27.862/svgHeight*height,133.475/svgWidth*width,5.834/svgHeight*height,197.339/svgWidth*width,1.114/svgHeight*height,186.695/svgWidth*width,28.385/svgHeight*height);
        torre.setFill(Color.web("#999999"));
        torre.setStroke(Color.BLACK);
        torre.setStrokeWidth(1.5);
        parent.getChildren().add(torre);
        Rectangle window = new Rectangle(90.34/svgWidth*width,57.299/svgHeight*height,138.369/svgWidth*width,12.062/svgHeight*height);
        window.setFill(Color.web("#999999"));
        window.setStroke(Color.BLACK);
        window.setStrokeWidth(1);
        window.setArcWidth(6.031/svgWidth*width);
        window.setArcHeight(6.031/svgHeight*height);
        parent.getChildren().add(window);
        Line line1 = new Line(106.586/svgWidth*width,28.456/svgHeight*height,106.586/svgWidth*width,56.776/svgHeight*height);
        line1.setStroke(Color.BLACK);
        line1.setStrokeWidth(1);
        parent.getChildren().add(line1);
        Line line2 = new Line(213.023/svgWidth*width,56.25/svgHeight*height,213.024/svgWidth*width,27.93/svgHeight*height);
        line2.setStroke(Color.BLACK);
        line2.setStrokeWidth(1);
        parent.getChildren().add(line2);
        Line line3 = new Line(106.026/svgWidth*width,69.36/svgHeight*height,105.466/svgWidth*width,99.778/svgHeight*height);
        line3.setStroke(Color.BLACK);
        line3.setStrokeWidth(1);
        parent.getChildren().add(line3);
        Line line4 = new Line(213.025/svgWidth*width,69.885/svgHeight*height,213.025/svgWidth*width,99.254/svgHeight*height);
        line4.setStroke(Color.BLACK);
        line4.setStrokeWidth(1);
        parent.getChildren().add(line4);
        Ellipse periscope = new Ellipse(264.843/svgWidth*width,55.463/svgHeight*height,17.646/svgWidth*width,15.995/svgHeight*height);
        periscope.setFill(Color.web("#607E81"));
        periscope.setStroke(Color.BLACK);
        periscope.setStrokeWidth(1);
        parent.getChildren().add(periscope);
        Line line5 = new Line(264.562/svgWidth*width,70.411/svgHeight*height,264.562/svgWidth*width,39.469/svgHeight*height);
        line5.setStroke(Color.BLACK);
        line5.setStrokeWidth(1);
        parent.getChildren().add(line5);
        Line line6 = new Line(247.196/svgWidth*width,55.725/svgHeight*height,282.49/svgWidth*width,55.201/svgHeight*height);
        line6.setStroke(Color.BLACK);
        line6.setStrokeWidth(1);
        parent.getChildren().add(line6);
        Polygon base = new Polygon();
        base.getPoints().addAll(24.047/svgWidth*width,59.413/svgHeight*height,0.0/svgWidth*width,59.413/svgHeight*height,0.0/svgWidth*width,69.861/svgHeight*height,25.093/svgWidth*width,69.534/svgHeight*height,28.581/svgWidth*width,65.616/svgHeight*height,24.396/svgWidth*width,59.738/svgHeight*height);
        base.setFill(Color.web("#575656"));
        base.setStroke(Color.BLACK);
        base.setStrokeWidth(1.5);
        parent.getChildren().add(base);
    }

    private void drawVertical(Group parent, double width, double height) {
        double svgWidth = 100.0;
        double svgHeight = 300.0;
        Rectangle body = new Rectangle(27.93/svgWidth*width,35.441/svgHeight*height,71.848/svgWidth*width,263.856/svgHeight*height);
        body.setFill(Color.web("#575656"));
        body.setStroke(Color.web("#322727"));
        body.setStrokeWidth(1.5);
        body.setArcWidth(42.217/svgWidth*width);
        body.setArcHeight(42.217/svgHeight*height);
        parent.getChildren().add(body);
        Path proa = new Path();
        proa.getElements().addAll(new MoveTo(40.892/svgWidth*width,46.582/svgHeight*height),new LineTo(32.037/svgWidth*width,41.18/svgHeight*height),new LineTo(32.036/svgWidth*width,3.794/svgHeight*height),new LineTo(65.155/svgWidth*width,28.369/svgHeight*height),new LineTo(99.779/svgWidth*width,5.189/svgHeight*height),new LineTo(99.254/svgWidth*width,43.283/svgHeight*height),new LineTo(89.033/svgWidth*width,48.537/svgHeight*height));
        proa.setFill(Color.web("#999999"));
        proa.setStroke(Color.BLACK);
        proa.setStrokeWidth(1.5);
        parent.getChildren().add(proa);
        Polygon torre = new Polygon();
        torre.getPoints().addAll(27.862/svgWidth*width,124.512/svgHeight*height,5.834/svgWidth*width,133.475/svgHeight*height,1.114/svgWidth*width,197.339/svgHeight*height,28.385/svgWidth*width,186.695/svgHeight*height);
        torre.setFill(Color.web("#999999"));
        torre.setStroke(Color.BLACK);
        torre.setStrokeWidth(1.5);
        parent.getChildren().add(torre);
        Rectangle window = new Rectangle(57.299/svgWidth*width,90.34/svgHeight*height,12.062/svgWidth*width,138.369/svgHeight*height);
        window.setFill(Color.web("#999999"));
        window.setStroke(Color.BLACK);
        window.setStrokeWidth(1);
        window.setArcWidth(6.031/svgWidth*width);
        window.setArcHeight(6.031/svgHeight*height);
        parent.getChildren().add(window);
        Line line1 = new Line(28.456/svgWidth*width,106.586/svgHeight*height,56.776/svgWidth*width,106.586/svgHeight*height);
        line1.setStroke(Color.BLACK);
        line1.setStrokeWidth(1);
        parent.getChildren().add(line1);
        Line line2 = new Line(56.25/svgWidth*width,213.023/svgHeight*height,27.93/svgWidth*width,213.024/svgHeight*height);
        line2.setStroke(Color.BLACK);
        line2.setStrokeWidth(1);
        parent.getChildren().add(line2);
        Line line3 = new Line(69.36/svgWidth*width,106.026/svgHeight*height,99.778/svgWidth*width,105.466/svgHeight*height);
        line3.setStroke(Color.BLACK);
        line3.setStrokeWidth(1);
        parent.getChildren().add(line3);
        Line line4 = new Line(69.885/svgWidth*width,213.025/svgHeight*height,99.254/svgWidth*width,213.025/svgHeight*height);
        line4.setStroke(Color.BLACK);
        line4.setStrokeWidth(1);
        parent.getChildren().add(line4);
        Ellipse periscope = new Ellipse(55.463/svgWidth*width,264.843/svgHeight*height,15.995/svgWidth*width,17.646/svgHeight*height);
        periscope.setFill(Color.web("#607E81"));
        periscope.setStroke(Color.BLACK);
        periscope.setStrokeWidth(1);
        parent.getChildren().add(periscope);
        Line line5 = new Line(70.411/svgWidth*width,264.562/svgHeight*height,39.469/svgWidth*width,264.562/svgHeight*height);
        line5.setStroke(Color.BLACK);
        line5.setStrokeWidth(1);
        parent.getChildren().add(line5);
        Line line6 = new Line(55.725/svgWidth*width,247.196/svgHeight*height,55.201/svgWidth*width,282.49/svgHeight*height);
        line6.setStroke(Color.BLACK);
        line6.setStrokeWidth(1);
        parent.getChildren().add(line6);
        Polygon base = new Polygon();
        base.getPoints().addAll(59.413/svgWidth*width,24.047/svgHeight*height,59.413/svgWidth*width,0.0/svgHeight*height,69.861/svgWidth*width,0.0/svgHeight*height,69.534/svgWidth*width,25.093/svgHeight*height,65.616/svgWidth*width,28.581/svgHeight*height,59.738/svgWidth*width,24.396/svgHeight*height);
        base.setFill(Color.web("#575656"));
        base.setStroke(Color.BLACK);
        base.setStrokeWidth(1.5);
        parent.getChildren().add(base);
    }
}
